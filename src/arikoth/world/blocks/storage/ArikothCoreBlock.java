package arikoth.world.blocks.storage;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import arikoth.content.effects.ArikothMiscFx;
import arikoth.graphics.ArikothDrawf;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.storage.*;
import arc.graphics.*;
import arc.scene.ui.layout.*;

import static mindustry.Vars.renderer;
import static mindustry.Vars.tilesize;

public class ArikothCoreBlock extends CoreBlock{
    public float spawnCooldown = 2f * 60f;
    public float lenf = 6, oy = -9, radius;


    public ArikothCoreBlock(String name){
        super(name);
    }

    public class DelayedSpawnCoreBuild extends CoreBuild{
        public float timer = 0f;
        public boolean requested = false;
        public float heat, progress, time;
        public Player spawnPlayer;
        public boolean animating = false;
        public boolean justSpawned = false;

        @Override
        public void draw(){
            super.draw();

            if((heat > 0.001f || animating) && !justSpawned){
              drawRespawn();
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if(timer > 0) timer -= Time.delta;

            if(spawnPlayer != null || animating){
                heat = Mathf.lerpDelta(heat, 1f, 0.1f);
                time += Time.delta;
                progress += 1f / spawnCooldown * Time.delta;

                if(progress >= 1f){
                    if(spawnPlayer != null && spawnPlayer.dead()){
                        playerSpawn(tile, spawnPlayer);
                        justSpawned = true;
                    }
                    animating = false;
                    spawnPlayer = null;
                    requested = false;
                }
            }else{
                heat = Mathf.lerpDelta(heat, 0f, 0.1f);
                if(justSpawned && heat <= 0.001f){
                    justSpawned = false;
                }
            }
        }

        @Override
        public void requestSpawn(Player player){
            if(Vars.state.isEditor()){
                spawnPlayer = player;
                playerSpawn(tile, spawnPlayer);
            }else if(!requested && player.dead() && !justSpawned){
                timer = spawnCooldown;
                requested = true;
                spawnPlayer = player;
                progress = 0f;
                time = 0f;
                heat = 0f;
                animating = true;
            }
        }

        void drawRespawn(){

            if(spawnPlayer != null){
                TextureRegion region = spawnPlayer.icon();

                Draw.color(0f, 0f, 0f, 0.4f * progress);
                Draw.rect("circle-shadow", x, y, region.width / 3f, region.height / 3f);
                Draw.color();

                Draw.draw(Draw.z(), () -> ArikothDrawf.construct(this, region, 0, progress, progress, timer));

                Draw.reset();
            }

            Draw.draw(Layer.blockBuilding, () -> {
                Draw.color(Pal.accent, progress);

                Shaders.blockbuild.region = spawnPlayer.icon();
                Shaders.blockbuild.time = Time.time;
                Shaders.blockbuild.progress = Mathf.clamp(progress() + 0.05f);

                Draw.rect(spawnPlayer.icon(), x, y);

                Draw.flush();
                Draw.color();
            });

            Lines.stroke(1.5f);
            Draw.alpha(heat);
            Draw.z(Layer.bullet);
            Draw.mixcol(Pal.remove, Pal.accent, progress);
            Lines.square(x, y, radius * heat + 10, 135 * progress);
            Lines.square(x, y, radius * heat + 10, -135 * progress);
            Drawf.light(x, y, radius * heat + 10, Pal.accent, 10);

            Draw.reset();
            Draw.color();
        }
    }
}
package arikoth.world.blocks.storage;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import arc.util.Tmp;
import arikoth.content.effects.ArikothMiscFx;
import arikoth.content.effects.palettes.ArikothPal;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.blocks.LaunchAnimator;
import mindustry.world.blocks.storage.CoreBlock;

import static mindustry.Vars.renderer;
import static mindustry.Vars.tilesize;

public class ModCore extends CoreBlock {
    TextureRegion glow;

    public ModCore(String name){
        super(name);
    }
    @Override
    public void load() {
        super.load();
        glow = Core.atlas.find(region + "-glow");
    }

    public class ModCoreBuild extends CoreBuild implements LaunchAnimator {
        public void drawLanding(float x, float y){
            float fin = renderer.getLandTimeIn();
            float fout = 1f - fin;

            float scl = Scl.scl(4f) / renderer.getDisplayScale();
            float shake = 0f;
            float s = region.width * region.scl() * scl * 3.6f * Interp.pow2Out.apply(fout);
            float rotation = 0;

            x += Mathf.range(shake);
            y += Mathf.range(shake);
            float thrustOpen = 0.10f;
            float thrusterFrame = fin >= thrustOpen ? 1f : fin / thrustOpen;
            float thrusterSize = Mathf.sample(thrusterSizes, fin);

            float ex = x + Mathf.absin(1.5f, 2.5f);
            float ey = y + Mathf.absin(1.5f, 2.5f);

            //when launching, thrusters stay out the entire time.
            if(renderer.isLaunching()){
                Interp i = Interp.pow10Out;
                thrusterFrame = i.apply(Mathf.clamp(fout*13f));
                thrusterSize = i.apply(Mathf.clamp(fout*9f));
            }

            //draw thruster flame
            float strength = (1f + (size - 3)/2.5f) * scl * thrusterSize * (0.95f + Mathf.absin(2f, 0.1f));
            float offset = (size - 3) * 3f * scl;

            float rot = Interp.pow2In.apply(fout) * 135f;

            for(int i = 0; i < 4; i++){
                Tmp.v1.trns(i * 90 + rot, 1f);

                Draw.z(Layer.effect - 1);
                Draw.blend(Blending.additive);
                Tmp.v1.setLength((size * tilesize/2.5f + 1f)*scl + strength*2f + offset);
                Draw.color(ArikothPal.blackbody5);
                Drawf.tri(ex, ey, 20f,  40f * strength, i * 90f + 45 + rot);
                Draw.color();

                Draw.blend();
            }

            // im confused how on earth this works

            Draw.z(Layer.effect);
            Draw.color(ArikothPal.blackbody3);
            Draw.rect(glow, ex, ey, s, s);
            Draw.rect("circle-shadow", ex, ey, s + 58, s + 58);
            Draw.rect("circle-shadow", ex, ey, s + 58, s + 58);
            // what if i remove this?
            Draw.blend(Blending.additive);
            Draw.scl(scl);

            drawLandingThrusters(ex, ey, rotation, Interp.pow5Out.apply(thrusterFrame));
            Draw.blend(Blending.normal);
            Draw.alpha(Interp.pow5Out.apply(thrusterFrame));
            Draw.blend(Blending.normal);
            drawLandingThrusters(ex, ey, rotation, Interp.pow5Out.apply(thrusterFrame));
            Draw.blend(Blending.normal);
            Effect.shake(4, Interp.pow5In.apply(thrusterFrame), this);
            Draw.alpha(1f);

            Drawf.shadow(ex - Mathf.lerp(100, 0, thrusterFrame), ey - Mathf.lerp(100, 0, thrusterFrame), 1, 0.65f);

            Draw.z(Layer.flyingUnit);
            Draw.rect(region, ex, ey);

            if(teamRegions[team.id] == teamRegion) Draw.color(team.color);

            Draw.blend();
            Draw.color();
            Draw.scl();
            Draw.reset();
        }

        protected void drawLandingThrusters(float x, float y, float rotation, float frame){
            float length = thrusterLength * (frame - 1f) - 1f/4f;
            float alpha = Draw.getColorAlpha();

            //two passes for consistent lighting
            for(int j = 0; j < 2; j++){
                for(int i = 0; i < 4; i++){
                    var reg = i >= 2 ? thruster2 : thruster1;
                    float rot = (i * 90) + rotation % 90f;
                    Tmp.v1.trns(rot, length * Draw.xscl);
                    //second pass applies extra layer of shading
                    if(j == 1){
                        Tmp.v1.rotate(-90f);
                        Draw.alpha((rotation % 90f) / 90f * alpha);
                        rot -= 90f;
                        Draw.rect(reg, x + Tmp.v1.x, y + Tmp.v1.y, rot);
                    }else{
                        Draw.alpha(alpha);
                        Draw.rect(reg, x + Tmp.v1.x, y + Tmp.v1.y, rot);
                    }
                }
            }
            Draw.alpha(1f);
        }

        public void drawThrusters(float frame){
            float length = thrusterLength * (frame - 1f) - 1f/4f;
            for(int i = 0; i < 4; i++){
                var reg = i >= 2 ? thruster2 : thruster1;
                float dx = Geometry.d4x[i] * length, dy = Geometry.d4y[i] * length;
                Draw.rect(reg, x + dx, y + dy, i * 90);
            }
        }

        @Override
        public void updateLaunch(){
            float in = renderer.getLandTimeIn() * launchDuration();
            float tsize = Mathf.sample(thrusterSizes, (in + 35f) / launchDuration());

            landParticleTimer += tsize * Time.delta;
            if(landParticleTimer >= 1f){
                tile.getLinkedTiles(t -> {
                    if(Mathf.chance(0.4f)){
                        ArikothMiscFx.coreLandDust.at(t.worldx(), t.worldy(), angleTo(t.worldx(), t.worldy()) + Mathf.range(30f), Tmp.c1.set(t.floor().mapColor).mul(1.5f + Mathf.range(0.15f)));
                        ArikothMiscFx.coreLandSparks.at(t.worldx(), t.worldy());
                    }
                });

                landParticleTimer = 0f;
            }
        }
    }
}

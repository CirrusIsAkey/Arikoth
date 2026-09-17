package arikoth.world.blocks.env;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arikoth.content.effects.ArikothFxf;
import mindustry.content.Blocks;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;
public class CrackedBlock extends EffectFloor {
    /**
     * credits to: @Sh1penfire
     */

    public Effect glowEffect = ArikothFxf.glowEffect;
    public float blinkTimeRange = 35, maxBlinkTime = 105;
    public float minAlpha = 1, innerLightRad;
    public Floor cracked = (Floor) Blocks.slag;
    public TextureRegion[] topVariantRegions;
    Color innerLightCol;

    public CrackedBlock(String name) {
        super(name);
        lightColor = Color.valueOf("ffb04a");
        innerLightCol = lightColor.cpy().a(0.5f);
        lightRadius = 33;
        innerLightRad = 10;
        //make sure this is true all the timer
        emitLight = true;
    }

    public TextureRegion[] topVariantRegions() {
        return topVariantRegions == null ? (topVariantRegions = new TextureRegion[]{fullIcon}) : variantRegions;
    }

    @Override
    public void load() {
        super.load();
        if (variants != 0) {
            topVariantRegions = new TextureRegion[variants];

            for(int i = 0; i < variants; ++i) {
                topVariantRegions[i] = Core.atlas.find(name + "-top" + (i + 1));
            }
        }
    }

    public float crackTime = 100;

    @Override
    public boolean updateRender(Tile tile) {
        return true;
    }

    @Override
    public void renderUpdate(UpdateRenderState tile) {
        if (Mathf.chanceDelta(effectChance) && tile.tile.block() == Blocks.air) {
            effect.at(tile.tile.worldx(), tile.tile.worldy(), effectColor);
        }
    }

    public float delay(int x, int y){
        return Mathf.mod(Mathf.sin(Mathf.cos(x * y + x + y + Time.time * 5.2f) + y * 9.4f) * 18.29f, 1) * maxBlinkTime;
    }

    @Override
    public void drawEnvironmentLight(Tile tile){
       int index = Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1));
       float lava = maxBlinkTime - delay(tile.x, tile.y) * blinkTimeRange;

       Draw.blend(Blending.additive);
       Draw.alpha(Mathf.absin(lava, 1));
       Draw.rect(topVariantRegions[index], tile.x, tile.y);

       Mathf.randomSeed(tile.data);
       float rand = Mathf.random(0.5f, 1);

       Drawf.light(tile.worldx(), tile.worldy(), topVariantRegions[index], 0, lightColor, Mathf.absin(maxBlinkTime * rand, 1));
       Drawf.light(tile.worldx(), tile.worldy(), innerLightRad, innerLightCol, innerLightCol.a);
       Drawf.light(tile.worldx(), tile.worldy(), lightRadius, lightColor, Mathf.absin(maxBlinkTime * rand, 1));
       Draw.blend();
    }
}

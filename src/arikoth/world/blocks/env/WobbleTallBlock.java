package arikoth.world.blocks.env;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.Time;
import mindustry.graphics.*;
import mindustry.world.*;

//I don't know what else to call this. It's not a prop, it's not a tree.
public class WobbleTallBlock extends Block{
    public float shadowOffset = -3f;
    public float layer = Layer.power + 1;
    public float shadowLayer = Layer.power - 1;
    public float rotationRand = 20f;
    public float shadowAlpha = 0.6f;
    public float wobbleScl = 100, wobbleMag = 5;

    public WobbleTallBlock(String name){
        super(name);
        solid = true;
        clipSize = 90;
        customShadow = true;
    }

    @Override
    public void init(){
        super.init();
        hasShadow = true;
    }

    @Override
    public void drawBase(Tile tile){
        float wobble = Mathf.sin(Time.time, wobbleScl, wobbleMag);
        float rot = Mathf.randomSeedRange(tile.pos() + 1, rotationRand);

        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
                tile.worldx() + shadowOffset, tile.worldy() + shadowOffset, rot * wobble);

        Draw.color();

        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region,
                tile.worldx(), tile.worldy(), rot * wobble);
    }

    @Override
    public void drawShadow(Tile tile){

    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }
}

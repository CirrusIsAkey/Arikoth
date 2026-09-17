package arikoth.world.blocks.env;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import arikoth.graphics.Pseudo3D;
import mindustry.graphics.Layer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;

public class FlatTree extends Prop {
    //values that dictate how the thingy behaves, i.e: height, segments or how wobbly it is
    public float minHeight = 0.5f, maxHeight = 1.5f;
    public float rootLayer = Layer.blockProp + 1, leafLayer = Layer.light - 1.5f, layer = Layer.light - 1;
    public int segments = 8;
    public float wobbleScl = 100, wobbleMag = 5;

    //textures again
    public TextureRegion rootRegion, trunkRegion, topRegion;

    //not too sure
    public FlatTree(String name) {
        super(name);
        variants = 0;
        solid = true;
        alwaysReplace = breakable = false;
        clipSize = 180;
    }

    //texture regions
    @Override
    public void load(){
        super.load();
        rootRegion = Core.atlas.find(name+"-root");
        trunkRegion = Core.atlas.find(name+"-trunk");
        topRegion = Core.atlas.find(name+"-top");
    }

    //self-explanatory, renders ingame icons
    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, topRegion};
    }

    //idk what this does tbh
    public float getHeight(Tile tile){
        return Mathf.randomSeed(tile.x * tile.y, minHeight, maxHeight);
    }

    @Override
    public void drawBase(Tile tile) {
        //x y coords
        float x = tile.drawx(), y = tile.drawy();
        //makes the leaves and stuff "wobble and sway"
        float wobble = Mathf.sin(Time.time, wobbleScl, wobbleMag);
        //height
        float height = getHeight(tile);

        //draws the root, nothing too special
        Draw.z(rootLayer);
        Draw.rect(rootRegion, x, y);

        for(int i = 0; i < segments; i++){
            //height calculations
            //height currently = current height
            //next height = next height in the sequence
            float heightCurrently = (height / segments) * i;
            float nextHeight = (height / segments) * (i + 1);
            //Draws the trunk
            Draw.z(leafLayer);
            Lines.stroke(trunkRegion.height / 4f);
            Pseudo3D.line(trunkRegion, x, y, heightCurrently, x, y, nextHeight, false);
        }

        Draw.z(layer);
        Pseudo3D.rect(topRegion, x, y, height, -wobble * Draw.scl);

        super.drawBase(tile);
    }
}

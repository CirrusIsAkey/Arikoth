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

public class Pseudo3DFern extends Prop {
    public float minHeight = 0.5f, maxHeight = 1.5f;
    public float rootLayer = Layer.blockProp + 1, leafLayer = Layer.groundUnit + 1.5f, layer = Layer.groundUnit + 1;
    public int segments = 8;
    public float wobbleScl = 100, wobbleMag = 5;

    //textures
    public TextureRegion rootRegion, trunkRegion, veryBottomLeavesRegion, bottomLeavesRegion, leavesRegion, canopyRegion, leavesHighRegion;

    //unsure
    public Pseudo3DFern(String name) {
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
        bottomLeavesRegion = Core.atlas.find(name+"-leaves-bottom");
        leavesRegion = Core.atlas.find(name+"-leaves");
        veryBottomLeavesRegion = Core.atlas.find(name+"-leaves-bottomest");
        leavesHighRegion = Core.atlas.find(name+"-leaves-top");
    }

    //self-explanatory, renders ingame icons
    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{veryBottomLeavesRegion, bottomLeavesRegion, leavesRegion, leavesHighRegion};
    }

    //this makes the sizes random
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
            // checks the current height
            float currentHeight = (height / segments) * i;
            float nextHeight = (height / segments) * (i + 1);
            //Draws the trunk
            Draw.z(leafLayer);
            Lines.stroke(trunkRegion.height / 4f);
            Pseudo3D.line(trunkRegion, x, y, currentHeight, x, y, nextHeight, false);
        }

        //draws in the order: veryBottomLeaves - bottomLeaves - leaves - leaves - default.
        Draw.z(layer);
        Pseudo3D.rect(veryBottomLeavesRegion, x, y, height - 0.025f, -wobble * Draw.scl);
        Pseudo3D.rect(bottomLeavesRegion, x, y, height - 0.02f, wobble * Draw.scl);
        Pseudo3D.rect(leavesRegion, x, y, height - 0.015f, wobble * Draw.scl);
        Pseudo3D.rect(leavesRegion, x, y, height - 0.01f, -wobble * Draw.scl);
        Pseudo3D.rect(leavesHighRegion, x, y, height - 0.005f, -wobble * Draw.scl);

        super.drawBase(tile);
    }
}

package arikoth.world.drawers;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.draw.DrawBlock;

/** multipurpose thing for drawing rotatable regions with light and dark shades*/
public class DrawRotateShadeRegion extends DrawBlock {
    public TextureRegion[] rotateRegions;
    public int rotationOffset = 0;
    public String regionName = "top-shade";

    @Override
    public void draw(Building build){
        int realRot = (rotationOffset + build.rotation) % 4;
        Draw.rect(rotateRegions[realRot > 1 ? 1 : 0], build.x, build.y, realRot * 90);
    }

    @Override
    public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list){
        int realRot = (rotationOffset + plan.rotation) % 4;
        Draw.rect(rotateRegions[realRot > 1 ? 1 : 0], plan.drawx(), plan.drawy(), realRot * 90);
    }

    @Override
    public void load(Block block){
        rotateRegions = new TextureRegion[2];
        for(int j = 1; j <= 2; j++){
            rotateRegions[j - 1] = Core.atlas.find(block.name + "-" + regionName + j);
        }
    }
}
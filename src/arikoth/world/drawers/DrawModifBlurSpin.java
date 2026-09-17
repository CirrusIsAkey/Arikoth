package arikoth.world.drawers;

import arc.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.DrawBlock;

public class DrawModifBlurSpin extends DrawBlock {
    public TextureRegion region, blurRegion;
    public String suffix = "";
    public float layer;
    public float rotateSpeed = 1f, x, y, blurThresh = 0.7f, rotation = 0;

    public DrawModifBlurSpin(String suffix, float speed){
        this.suffix = suffix;
        rotateSpeed = speed;
    }

    public DrawModifBlurSpin(){
    }

    @Override
    public void draw(Building build){
        if(layer > 0) {
            Draw.z(layer);
        }
        Drawf.spinSprite(build.warmup() > blurThresh ? blurRegion : region, build.x + x, build.y + y, build.totalProgress() * rotateSpeed + rotation);
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{region};
    }

    @Override
    public void load(Block block){
        region = Core.atlas.find(block.name + suffix);
        blurRegion = Core.atlas.find(block.name + suffix + "-blur");
    }
}
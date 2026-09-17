package arikoth.world.drawers;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.Interp;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.draw.DrawBlock;

public class DrawStartupRegion extends DrawBlock {
    public String suffix;
    TextureRegion suf;
    Interp interp = Interp.pow2;

    public boolean blending = false;
    public float layer = -1, alpha = 1;
    public Color color = Color.white.cpy();

    @Override
    public void draw(Building build){
        if(layer > -1){{
            Draw.z(layer);
        }}
        if(blending)Draw.blend(Blending.additive);
        Draw.color(color);
        Draw.alpha(alpha * build.warmup());
        Draw.rect(suf, build.x, build.y);
        Draw.color();

        Draw.blend();
        Draw.reset();
    }

    public DrawStartupRegion(String suffix, Color color){
        this.suffix = suffix;
        this.color = color;
    }

    @Override
    public void load(Block block){
        suf = Core.atlas.find(block.name + suffix);
    }
}

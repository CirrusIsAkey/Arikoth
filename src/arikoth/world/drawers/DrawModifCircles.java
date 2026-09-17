package arikoth.world.drawers;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.draw.DrawBlock;

public class DrawModifCircles extends DrawBlock {
    public Color color = Color.valueOf("7457ce");

    public int amount = 5, sides = 15;
    public float strokeMin = 0.2f, strokeMax = 2f, timeScl = 160f;
    public float radius = 12f, radiusOffset = 0f, x = 0f, y = 0f, lightAlpha = 0.3f;
    public Interp strokeInterp = Interp.pow3In;
    public boolean invert = false, light = true;

    public DrawModifCircles(Color color){
        this.color = color;
    }

    public DrawModifCircles(){
    }

    @Override
    public void draw(Building build){
        if(build.warmup() <= 0.001f) return;

        Draw.color(color, build.warmup() * color.a);

        for(int i = 0; i < amount; i++){
            float life = ((Time.time / timeScl + i/(float)amount) % 1f);
            if(invert) life = 1f - life;
            Lines.stroke(build.warmup() * strokeInterp.apply(strokeMax, strokeMin, life));
            Lines.poly(build.x + x, build.y + y, sides, radiusOffset + life * radius);
            if(light){{
                Fill.light(build.x + x, build.y + y, sides, radius, color.cpy().a(lightAlpha * build.warmup()), color.cpy().a(lightAlpha * build.warmup()));
            }}
        }

        Draw.reset();
    }
}
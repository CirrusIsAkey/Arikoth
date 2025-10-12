package arikoth.world.drawers;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.world.draw.DrawBlock;

public class DrawRingSmelt extends DrawBlock {
    public Color ringColor1 = Color.valueOf("f58349"), ringColor2 = Color.valueOf("f56949"), ringColor3 = Color.valueOf("f54949"), flameColor = Color.valueOf("f2d585");
    public float flameRad = 1f, ringSpace1 = 1.5f, ringSpace2 = 3f, ringSpace3 = 4.5f, flameRadiusScl = 3f, flameRadiusMag = 0.3f, circleStroke = 1f;
    public float x = 0, y = 0;
    public float alpha = 0.68f;
    public int particles = 25;
    public float particleLife = 40f, particleRad = 7f, particleStroke = 1.1f, particleLen = 3f;
    public boolean drawCircles = false, drawParticles = true, drawRing1 = true, drawRing2 = true, drawRing3 = true;
    public Blending blending = Blending.additive;

    @Override
    public void draw(Building build){
        if(build.warmup() > 0f && flameColor.a > 0.001f){
            Lines.stroke(circleStroke * build.warmup());

            float si = Mathf.absin(flameRadiusScl, flameRadiusMag);
            float a = alpha * build.warmup();
            Draw.blend(blending);

            Draw.color(flameColor, a);
            Fill.circle(build.x + x, build.y + y, flameRad + si);

            Draw.color(ringColor3, a);
            if(drawRing3)if(drawCircles)Drawf.circles(build.x + x, build.y + y, (flameRad + ringSpace3 + si) * build.warmup());
            else Lines.circle(build.x + x, build.y + y, (flameRad + ringSpace3 + si) * build.warmup());

            Draw.color(ringColor2, a);
            if(drawRing2)if(drawCircles)Drawf.circles(build.x + x, build.y + y, (flameRad + ringSpace2 + si) * build.warmup());
            else Lines.circle(build.x + x, build.y + y, (flameRad + ringSpace2 + si) * build.warmup());

            Draw.color(ringColor1, a);
            if(drawRing1)if(drawCircles)Drawf.circles(build.x + x, build.y + y, (flameRad + ringSpace1 + si) * build.warmup());
            else Lines.circle(build.x + x, build.y + y, (flameRad + ringSpace1 + si) * build.warmup());

            Lines.stroke(particleStroke * build.warmup());

            float base = (Time.time / particleLife);
            rand.setSeed(build.id);
            if(drawParticles)for(int i = 0; i < particles; i++){
                float fin = (rand.random(1f) + base) % 1f, fout = 1f - fin;
                float angle = rand.random(360f);
                float len = particleRad * Interp.pow2Out.apply(fin);
                Lines.lineAngle(build.x + Angles.trnsx(angle, len) + x, build.y + Angles.trnsy(angle, len) + y, angle, particleLen * fout * build.warmup());
            }

            Draw.blend();
            Draw.reset();
        }
    }
}

package arikoth.world.weapons.parts;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.entities.part.DrawPart;
import mindustry.graphics.*;

import static arc.graphics.g2d.Lines.circleVertices;

public class ImprovedFlarePart extends DrawPart{
    public int sides = 4;
    public float radius = 100f, radiusTo = -1f, stroke = 6f, innerScl = 0.5f, innerRadScl = 0.33f;
    public float x, y, rotation, rotMove, spinSpeed;
    public boolean followRotation;
    public Color color1 = Color.valueOf("acacac"), color2 = Color.white;
    public boolean clampProgress = true;
    public PartProgress progress = PartProgress.warmup;
    public float layer = Layer.effect;
    public float lightRadius = 55;

    @Override
    public void draw(PartParams params){
        float z = Draw.z();
        if(layer > 0) Draw.z(layer);

        float prog = progress.getClamp(params, clampProgress);
        int i = params.sideOverride == -1 ? 0 : params.sideOverride;

        float sign = (i == 0 ? 1 : -1) * params.sideMultiplier;
        Tmp.v1.set(x * sign, y).rotate(params.rotation - 90);

        float
                rx = params.x + Tmp.v1.x,
                ry = params.y + Tmp.v1.y,
                rot = (followRotation ? params.rotation : 0f) + rotMove * prog + rotation + Time.time * spinSpeed,
                rad = radiusTo < 0 ? radius : Mathf.lerp(radius, radiusTo, prog);

        Draw.blend(Blending.additive);
        Draw.color(color1);
        for(int j = 0; j < sides; j++){
            Drawf.tri(rx, ry, stroke, rad, j * 360f / sides + rot);
        }

        Draw.color(color2);
        for(int j = 0; j < sides; j++){
            Drawf.tri(rx, ry, stroke * innerScl, rad * innerRadScl, j * 360f / sides + rot);
        }

        Fill.light(rx, ry, circleVertices(rad), rad, color2, color1.cpy().a(0));
        Drawf.light(rx, ry, rad, color2, 0.75f);

        Draw.color();
        Draw.blend();
        Draw.z(z);
    }

    @Override
    public void load(String name){

    }
}
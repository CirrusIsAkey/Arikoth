package arikoth.content.effects;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Time;
import mindustry.entities.*;

import static arc.graphics.Color.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Draw.rect;
import static arc.math.Angles.randLenVectors;

public class ArikothCraftFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Vec2 temp = new Vec2();

    public static Effect
    end;

    public static Effect steamVapor(Color colFrom, Color colTo, float lifetime, float length, int num, float rad) {
        return new Effect(lifetime, e -> {
            color(colFrom, colTo, e.fout());
            alpha(e.fout());

            randLenVectors(e.id, num, 0.2f + e.finpow() * length, e.rotation + 32, 25f, 25f, (x, y) -> {
                Fill.circle(e.x + x, e.y + y, 0.5f + e.fin(Interp.slope) * rad);
            });
        });
    }
}
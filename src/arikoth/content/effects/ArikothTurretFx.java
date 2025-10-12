package arikoth.content.effects;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.*;
import arc.math.geom.*;
import mindustry.entities.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

public class ArikothTurretFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Vec2 temp = new Vec2();

    public static final Effect

    shootSmokeGyre = new Effect(70f, e -> {
        rand.setSeed(e.id);
        for(int i = 0; i < 8; i++){
            v.trns(e.rotation + rand.range(20f), rand.random(e.finpow() * 40f));
            e.scaled(e.lifetime * rand.random(0.2f, 0.8f), b -> {
                color(e.color, Pal.lightishGray, b.fin());
                Fill.circle(e.x + v.x, e.y + v.y, b.fout() * 2.4f + 0.3f);
            });
        }
    }),
    shootSmokeSquareSmall = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        rand.setSeed(e.id);
        for(int i = 0; i < 6; i++){
            float rot = e.rotation + rand.range(30f);
            v.trns(rot, rand.random(e.finpow() * 17f));
            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 3.8f + 0.2f, rand.random(360f));
        }
    }),
    shootSmokeSquareSmallSparse = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        rand.setSeed(e.id);
        for(int i = 0; i < 3; i++){
            float rot = e.rotation + rand.range(30f);
            v.trns(rot, rand.random(e.finpow() * 17f));
            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 3.8f + 0.2f, rand.random(360f));
        }
    }),
    shootSmokeHexagon = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        rand.setSeed(e.id);
        for(int i = 0; i < 9; i++){
            float rot = e.rotation + rand.range(30f);
            v.trns(rot, rand.random(e.finpow() * 21f));
            Fill.poly(e.x + v.x, e.y + v.y, 6, e.fout() * 4.8f + 0.2f, rand.random(360f));
        }
    }),
    shootSmokeHexagonSparse = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        rand.setSeed(e.id);
        for(int i = 0; i < 6; i++){
            float rot = e.rotation + rand.range(30f);
            v.trns(rot, rand.random(e.finpow() * 21f));
            Fill.poly(e.x + v.x, e.y + v.y, 6, e.fout() * 4.8f + 0.2f, rand.random(360f));
        }
    }),
    shootSmokeHexagonLargeSparse = new Effect(30f, e -> {
        color(Color.white, e.color, e.fin());

        rand.setSeed(e.id);
        for(int i = 0; i < 8; i++){
            float rot = e.rotation + rand.range(30f);
            v.trns(rot, rand.random(e.finpow() * 31f));
            Fill.poly(e.x + v.x, e.y + v.y, 6, e.fout() * 5.8f + 0.2f, rand.random(360f));
        }
    }),
    hitLargeBulletColor = new Effect(14, e -> {
        color(Color.white, e.color, e.fin());

        e.scaled(7f, s -> {
            stroke(0.5f + s.fout());
            Lines.circle(e.x, e.y, s.fin() * 15f);
        });

        stroke(0.75f + e.fout());

        randLenVectors(e.id, 12, e.fin() * 28f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 2f);
        });

        Drawf.light(e.x, e.y, 20f, e.color, 0.6f * e.fout());
    }),
    shootSparkSmall = new Effect(25f, e -> {
        color(Color.white, e.color, e.fin());
        stroke(e.fout() * 1.5f + 0.5f);

        rand.setSeed(e.id);
        for(int i = 0; i < 3; i++){
            float ang = e.rotation + rand.range(9f), len = rand.random(20f * e.finpow());
            e.scaled(e.lifetime * rand.random(0.5f, 1f), p -> {
                v.trns(ang, len);
                lineAngle(e.x + v.x, e.y + v.y, ang, p.fout() * 7f + 0.5f);
            });
        }
    }),
    shootSparkLarge = new Effect(25f, e -> {
        color(Color.white, e.color, e.fin());
        stroke(e.fout() * 1.5f + 0.5f);

        rand.setSeed(e.id);
        for(int i = 0; i < 6; i++){
            float ang = e.rotation + rand.range(12f), len = rand.random(20f * e.finpow());
            e.scaled(e.lifetime * rand.random(0.5f, 1f), p -> {
                v.trns(ang, len);
                lineAngle(e.x + v.x, e.y + v.y, ang, p.fout() * 7f + 0.5f);
            });
        }
    }),
    shootLargeColor = new Effect(25, e -> {
        color(Color.white, e.color, e.fin());
        float w = 2f + 3 * e.fout();
        Drawf.tri(e.x, e.y, w, 38f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180);
    }),
    shootMassiveColor = new Effect(25, e -> {
        color(Color.white, e.color, e.fin());
        float w = 4f + 3 * e.fout();
        Drawf.tri(e.x, e.y, w, 48f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 9f * e.fout(), e.rotation + 180);
    }),
    hitLaserAccent = new Effect(8, e -> {
        color(Color.white, Pal.accent, e.fin());
        stroke(0.5f + e.fout());
        Lines.circle(e.x, e.y, e.fin() * 5f);

        Drawf.light(e.x, e.y, 23f, Pal.accent, e.fout() * 0.7f);
    }),
    verySmallStarFour = new Effect(30, e -> {
        color(e.color);
        e.rotation = e.fin() * 200;
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3, e.fout(Interp.circle) * 12, e.rotation + (90 * i));
        }
        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 1.2f, e.fout(Interp.circle) * 6, e.rotation + (90 * i));
        }
    }),
    smallStarFour = new Effect(30, e -> {
        color(e.color);
        e.rotation = e.fin() * 200;
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 4f, e.fout(Interp.circle) * 30, e.rotation + (90 * i));
        }
        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 2.2f, e.fout(Interp.circle) * 20, e.rotation + (90 * i));
        }
    }),
    starFour = new Effect(60, e -> {
        color(e.color);
        e.rotation = e.fin() * 200;
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 5, e.fout(Interp.circle) * 40, e.rotation + (90 * i));
        }
        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3.2f, e.fout(Interp.circle) * 30, e.rotation + (90 * i));
        }
    }),
    largeStarFour = new Effect(60, e -> {
        color(e.color);
        e.rotation = e.fin() * 200;
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 6, e.fout(Interp.circle) * 70, e.rotation + (90 * i));
        }
        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 4.2f, e.fout(Interp.circle) * 60, e.rotation + (90 * i));
        }
    });
}
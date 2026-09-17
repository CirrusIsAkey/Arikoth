package arikoth.content.effects;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Vec2;
import arikoth.content.effects.palettes.ArikothPal;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.*;

public class ArikothShootFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Vec2 temp = new Vec2();

    public static Effect
            punctureShot = new Effect(15, e -> {
                color(Color.white, Color.valueOf("a88fc7"), e.color, e.fin());
                float w = 1.2f + 22 * e.fout();
                Draw.z(110);
                Drawf.tri(e.x, e.y, w, 120f * e.fout(), e.rotation);
                Drawf.tri(e.x, e.y, w, 20f * e.fout(), e.rotation + 180f);

                Drawf.tri(e.x, e.y, w - 4, 120f * e.fout(), e.rotation + 90f);
                Drawf.tri(e.x, e.y, w - 4, 120f * e.fout(), e.rotation + -90f);

                Drawf.tri(e.x, e.y, w - 8, 80f * e.fout(), e.rotation +45f);
                Drawf.tri(e.x, e.y, w - 8, 80f * e.fout(), e.rotation -45f);
                Drawf.tri(e.x, e.y, w - 8, 80f * e.fout(), e.rotation +135f);
                Drawf.tri(e.x, e.y, w - 8, 80f * e.fout(), e.rotation -135f);
            }),
            viceShot = new Effect(9, e -> {
                color(Color.white, Color.valueOf("c7c7c7"), e.fin());
                float w = 1.2f + 6 * e.fout();
                Draw.blend(Blending.additive);
                Drawf.tri(e.x, e.y, w, 60f * e.fout(), e.rotation);
                Drawf.tri(e.x, e.y, w, 10f * e.fout(), e.rotation + 180f);
                Drawf.tri(e.x, e.y, w - 2, 30f * e.fout(), e.rotation + 45f);
                Drawf.tri(e.x, e.y, w - 2, 30f * e.fout(), e.rotation - 45f);
                Draw.blend();
            }),
            viceChemicalShot = new Effect(9, e -> {
                color(ArikothPal.chemicalAmmoFront, ArikothPal.chemicalAmmo, e.fin());
                float w = 1.2f + 6 * e.fout();
                Draw.blend(Blending.additive);
                Drawf.tri(e.x, e.y, w, 60f * e.fout(), e.rotation);
                Drawf.tri(e.x, e.y, w - 1, 40f * e.fout(), e.rotation);
                Drawf.tri(e.x, e.y, w, 10f * e.fout(), e.rotation + 180f);
                Drawf.tri(e.x, e.y, w - 1, 10f * e.fout(), e.rotation + 180f);
                Draw.blend();
            }),
            sulfurShot = new Effect(60f * 1.5f, 80f, e -> {
                color(ArikothPal.sulfurSmoke, ArikothPal.sulfurSmokeDark.cpy().a(0.5f), e.fin());
                Draw.z(Mathf.lerp(65, 95, e.fin()));

                randLenVectors(e.id, 3, 20 * e.fin(Interp.pow5Out), e.rotation, 20, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 6f * e.fslope());
                });
            }),
            punctureSparks = new Effect(30f, e -> {
                color(Color.valueOf("a88fc7").a(0), Color.valueOf("a88fc7"), e.fslope());
                Lines.stroke(0.5f + 1.5f * e.fout());
                float spread = 16f;

                rand.setSeed(e.id);
                for(int i = 0; i < 20; i++){
                    float ang = e.rotation + rand.range(8f);
                    v.trns(ang, rand.random(e.fin() * 55f));
                    Lines.lineAngle(e.x + v.x + rand.range(2), e.y + v.y + rand.range(spread), ang, e.fout() * 7f * rand.random(1f) + 1f);
                }
            }),
            punctureSmoke = new Effect(60f, 80f, e -> {
                color(Color.valueOf("a88fc7").a(0), Color.valueOf("a88fc7"), e.fslope());

                Draw.z(110);
                blend(Blending.additive);

                rand.setSeed(e.id);
                randLenVectors(e.id, 12, 70 * e.fin(Interp.pow5Out), e.rotation, 1, 8, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, rand.random(8, 12) * e.fin());
                });
                blend();
            }),
            punctureTrailSmoke = new Effect(60f * 2f, 80f, e -> {
                color(Pal.vent.cpy().a(0.5f), Pal.vent2.cpy().a(0), e.fin());

                Draw.z(111);

                rand.setSeed(e.id);
                randLenVectors(e.id + 1, rand.random(1, 3), Interp.circleOut.apply(e.finpow()) * 10, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 3 + 3f * rand.random(0.5f, 1.2f) * Interp.circleOut.apply(e.fin()));
                });
            }),
    //broken, dont feel like fixing
            punctureCasing = new Effect(60f, e -> {
                color(Pal.lightOrange, Color.lightGray, Pal.lightishGray, e.fin());
                alpha(e.fout(0.3f));
                float rot = Math.abs(e.rotation) - 90;
                int i = -Mathf.sign(e.rotation);

                float lenX = (2f + e.finpow() * -12f) * i;
                float lenY = (2f + e.finpow() * -6f) * i;
                float lr = rot + e.fin() * 15f * i;
                Draw.rect(
                        "arikoth-artillery-emplacement-powderCharge",
                        e.x + trnsx(lr, lenX) + Mathf.randomSeedRange(e.id + i - 2, 3f * e.fin()),
                        e.y + trnsy(lr, lenY) + Mathf.randomSeedRange(e.id + i - 8, 1.5f * e.fin()),
                        rot + e.fin() * -30f * i
                );

            }).layer(50),
    end;

    public static Effect shootSpike(float lifetime, float startWidth, float width, float length, float rearLength) {
        return new Effect(lifetime, e -> {
            color(Color.white, e.color, e.fin());
            float w = 1.2f + 7 * e.fout();
            Drawf.tri(e.x, e.y, w, 25f * e.fout(), e.rotation);
            Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
        });
    }
}

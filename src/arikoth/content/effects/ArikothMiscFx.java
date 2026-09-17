package arikoth.content.effects;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import arikoth.math.Parallax;
import arikoth.content.effects.palettes.ArikothPal;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;
import static arikoth.content.effects.ArikothHitFx.rand;
import static arikoth.content.effects.ArikothHitFx.temp;

public class ArikothMiscFx {
    public static Effect
    ventSteam = new Effect(120f, e -> {
        rand.setSeed(e.id);

        randLenVectors(e.id, rand.random(1, 3), 10*e.fin(), (x, y) -> {
            Parallax.getParallaxFrom(
                    temp.set(e.x + x, e.y + y),
                    Core.camera.position,
                    rand.random(2f, 3f) * e.finpow()
            );
            Draw.color(ArikothPal.blackbody3, ArikothPal.blackbody3.cpy().lerp(Color.valueOf("292821"), 0.75f), e.fin(Interp.circleIn));
            Draw.alpha(e.foutpowdown());
            Fill.circle(temp.x, temp.y, rand.random(6f, 8f) * e.fin(Interp.circleIn));
            Draw.blend(Blending.additive);
            Drawf.light(temp.x, temp.y, rand.random(8f, 12f), ArikothPal.blackbody2, 1 * e.fout(Interp.circleOut));
        });
        Draw.blend();
    }).layer(80),
            coreLandSparks = new Effect(100f, e -> {
                color(ArikothPal.blackbody3, ArikothPal.blackbody5, e.fout());
                float w = 0.5f + 2 * e.fout();

                Angles.randLenVectors(e.id, 1, 1f + e.fin(Interp.pow5Out) * 90f, (x, y) -> {
                    float angle = Mathf.angle(x, y);

                    Draw.blend(Blending.additive);
                    Parallax.getParallaxFrom(temp.set(x, y).scl(e.foutpowdown()).add(e.x, e.y), Core.camera.position, e.fin() * rand.random(2f, 5f));
                    Drawf.tri(e.x + x, e.y + y, w, 4f * e.fout(), angle);
                    Drawf.tri(e.x + x, e.y + y, w, 18f * e.fout(), 180f + angle);
                    Draw.blend();
                });
            }).layer(Layer.debris + 1f),
            coreLandDust = new Effect(100f, e -> {
                color(e.color, Color.gray.cpy().a(0), e.fin(Interp.pow2Out));
                rand.setSeed(e.id);
                Tmp.v1.trns(e.rotation, e.finpow() * 90f * rand.random(0.2f, 1f));
                Parallax.getParallaxFrom(temp.set(Tmp.v1.x, Tmp.v1.y).scl(e.foutpowdown()).add(e.x, e.y), Core.camera.position, e.fin() * rand.random(2f, 5f));
                Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 4 + 8f * e.fin(Interp.pow2Out));
            }).layer(Layer.groundUnit + 1f),
    end;
}

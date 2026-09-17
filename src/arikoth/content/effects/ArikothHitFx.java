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
import arc.util.Tmp;
import arikoth.content.ArikothItems;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class ArikothHitFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Vec2 temp = new Vec2();

    public static Effect
            sulfurExplosionSmall = new Effect(60f * 1.5f, 80f, e -> {
                color(ArikothItems.sulfur.color.cpy().a(0.5f), e.fin());
                Draw.z(Mathf.lerp(Layer.groundUnit - 1, Layer.flyingUnitLow + 4.5f, e.fin()));
                Draw.blend(Blending.additive);

                randLenVectors(e.id, 5, 16f * e.fin(Interp.pow5Out), (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 6f * e.fslope());
                });

                Draw.blend();
            }),
    end;

    public static Effect reduxColorSpark(float lifetime, float startStroke, float stroke, float len, float length, float cone, int num) {
        return new Effect(lifetime, e -> {
            color(Color.white, e.color, e.fin());
            stroke(e.fout() * stroke + startStroke);

            randLenVectors(e.id, num, length * e.fin(), e.rotation, cone, (x, y) -> {
                lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * len + 0.5f);
            });
        });
    }

    public static Effect spikeExplosion(Interp interp, float lifetime, float clipsize, float radius, float stroke, float width, float length, int num) {
        return new Effect(lifetime, clipsize, e -> {
            color(e.color);
            stroke(e.fout() * stroke);
            float circleRad = 6f + e.fin(interp) * radius;
            Lines.circle(e.x, e.y, circleRad);

            rand.setSeed(e.id);
            for(int i = 0; i < num; i++){
                float angle = rand.random(360f);
                float lenRand = rand.random(0.5f, 1f);
                Tmp.v1.trns(angle, circleRad);

                for(int s : Mathf.signs){
                    Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * width, e.fout() * length * lenRand + 6f, angle + 90f + s * 90f);
                }
            }
        });
    }

    public static Effect explosionLight(Interp interp, float lifetime, float clipsize, float radius, float opacity) {
        return new Effect(lifetime, clipsize, e -> {
            float circleRad = 6f + e.fin(interp) * radius;

            color(e.color, e.foutpow());
            Fill.light(e.x, e.y, circleVertices(circleRad), circleRad, Color.clear, Tmp.c1.set(Draw.getColor()).a(opacity * e.fout(Interp.pow10Out)));
        }).layer(Layer.bullet + 2f);
    }

    public static Effect sparkExplosion(Interp interp, float lifetime, float clipsize, float circleStroke, float stroke, float len, float length, int num) {
        return new Effect(lifetime, clipsize, e -> {
            color(e.color);
            stroke(e.fin(interp) * circleStroke);
            float circleRad = 6f + e.finpow() * length;
            Lines.circle(e.x, e.y, circleRad);

            rand.setSeed(e.id);
            for(int i = 0; i < num; i++){
                float angle = rand.random(360f);
                float lenRand = rand.random(0.5f, 1f);
                stroke(e.fout() * stroke);
                Lines.lineAngle(e.x, e.y, angle, e.foutpow() * len * rand.random(1f, 0.6f) + 2f, e.finpow() * length * lenRand + 6f);
            }
        });
    }

    public static Effect smokeExplosion(Interp interp, Color colorFrom, Color colorTo, boolean additive, float lifetime, float radMin, float radMax, float length, int num) {
        return new Effect(lifetime, b -> {
            rand.setSeed(b.id);
            for(int i = 0; i < num; i++){

            int fi = i;
            b.scaled(b.lifetime * rand.random(0.1f, 1), e -> {
                if(additive){
                    Draw.blend(Blending.additive);
                }
                color(colorFrom, colorTo, e.fin());
                Draw.alpha(e.fslope() * 0.8f);

                rand.setSeed(e.id);
                randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), 1, length * e.fin(interp), (x, y, in, out) ->  {
                    Fill.circle(e.x + x, e.y + y, rand.random(radMin, radMax) * e.fslope());
                });
                Draw.blend();
            });
            };
        }).layer(Layer.bullet - 1f);
    }

    public static Effect spikeBoom(Interp interp, float lifetime, float clipsize, float width, float radius, int num) {
        return new Effect(lifetime, clipsize, e -> {
            color(e.color);

            rand.setSeed(e.id);
            for(int i = 0; i < num; i++){
                float angle = rand.random(360f);
                float lenRand = rand.random(0.5f, 1f);
                Tmp.v1.trns(angle, 0);

                float triWidth = (6 + width * lenRand) * e.fslope();

                for(int s : Mathf.signs){
                    Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, triWidth, e.fout(interp) * radius * lenRand + 6f, angle + 90);
                }
            }
        });
    }
}

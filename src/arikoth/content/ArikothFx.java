package arikoth.content;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Tmp;
import arikoth.math.Parallax;
import arikoth.palettes.ArikothTurretPal;
import arikoth.palettes.ArikothUnitPal;
import mindustry.entities.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;

public class ArikothFx {
    public static final Rand rand = new Rand();
    public static final Vec2 v = new Vec2();
    public static final Vec2 temp = new Vec2();

    public static final Effect

            spikyBoom = new Effect(60f, 160f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        float circleRad = 1f + e.finpow() * 15f;
        Lines.circle(e.x, e.y, circleRad);

        rand.setSeed(e.id);
        for(int i = 0; i < 4; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 5, e.fout() * 8f * lenRand + 3f, angle + 90f + s * 90f);
            }
        }
    }),

            spikyBoomMedium = new Effect(60f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 2f);
                float circleRad = 6f + e.finpow() * 20f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 8; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 8, e.fout() * 16f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),

    spikyBoomLarge = new Effect(90f, 160f, e -> {
        color(e.color);
        stroke(e.fout() * 2f);
        float circleRad = 6f + e.finpow() * 70f;
        Lines.circle(e.x, e.y, circleRad);

        rand.setSeed(e.id);
        for(int i = 0; i < 12; i++){
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for(int s : Mathf.signs){
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 12, e.fout() * 24f * lenRand + 6f, angle + 90f + s * 90f);
            }
        }
    }),

            spikeShoot = new Effect(35f, 96f, e -> {
                color(e.color);
                stroke(e.fout() * 0f);
                float circleRad = 5f + e.finpow() * 15f;
                Lines.circle(e.x, e.y, circleRad);
                float angle = e.rotation;

                rand.setSeed(e.id);
                for(int i = 0; i < 6; i++){
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 5, e.fout() * 5f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            starExplodeSmall = new Effect(60f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 4f);
                float circleRad = 10f + e.finpow() * 10f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 18; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 4, e.fout() * 8f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            sparkExplode = new Effect(35, e -> {
                color(e.color, Color.white, e.fout() * 0.3f);
                stroke(e.fout() * 2f);

                rand.setSeed(e.id);
                randLenVectors(e.id, 35, e.finpow() * 40f, (x, y) -> {
                    float ang = Mathf.angle(x, y);
                    lineAngle(e.x + x, e.y + y, ang, e.fout() * rand.random(6, 9) + 3f);
                });
            }),
            starExplodeMedium = new Effect(60f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 4f);
                float circleRad = 20f + e.finpow() * 20f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 18; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 6, e.fout() * 10f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            starExplodeLarge = new Effect(120f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 6f);
                float circleRad = 60f + e.finpow() * 60f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 24; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 8, e.fout() * 18f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            hitSquaresFade = new Effect(15f, e -> {
                color(e.color);
                Angles.randLenVectors(e.id, 4, e.fin() * 30f, (x, y) -> Fill.square(e.x + x, e.y + y, 3f * e.fout()));

                Tmp.c1.set(e.color).a(e.fout(Interp.pow3In));
            }),
            spikeShootBig = new Effect(30f, 96f, e -> {
                color(e.color);
                stroke(e.fout() * 0f);
                float circleRad = 5f + e.finpow() * 15f;
                Lines.circle(e.x, e.y, circleRad);
                float angle = e.rotation;

                rand.setSeed(e.id);
                for(int i = 0; i < 3; i++){
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for(int s : Mathf.signs){
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 5, e.fout() * 16f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            tinyStarFour = new Effect(60, e -> {
                color(e.color);
                e.rotation = e.fin() * 200;
                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3, e.fout(Interp.circle) * 20, e.rotation + (90 * i));
                }
                color();
                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 1.2f, e.fout(Interp.circle) * 10, e.rotation + (90 * i));
                }
            }),
            tinyStarFive = new Effect(60, e -> {
                color(e.color);
                e.rotation = e.fin() * 200;
                for (int i = 0; i < 5; i++) {
                    Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3, e.fout(Interp.circle) * 20, e.rotation + (72 * i));
                }
                color();
                for (int i = 0; i < 5; i++) {
                    Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 1.2f, e.fout(Interp.circle) * 10, e.rotation + (72 * i));
                }
            }),
    //code From exogenisis
    smallStarFour = new Effect(60, e -> {
        color(e.color);
        e.rotation = e.fin() * 200;
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3, e.fout(Interp.circle) * 40, e.rotation + (90 * i));
        }
        color();
        for (int i = 0; i < 4; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 1.2f, e.fout(Interp.circle) * 30, e.rotation + (90 * i));
        }
    }),
    smallStarFive = new Effect(60, e -> {
        color(e.color);
        e.rotation = e.fin() * 200;
        for (int i = 0; i < 5; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3, e.fout(Interp.circle) * 40, e.rotation + (72 * i));
        }
        color();
        for (int i = 0; i < 5; i++) {
            Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 1.2f, e.fout(Interp.circle) * 30, e.rotation + (72 * i));
        }
    }),
            mediumStarFour = new Effect(60, e -> {
                color(e.color);
                e.rotation = e.fin() * 200;
                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 3.5f, e.fout(Interp.circle) * 80, e.rotation + (90 * i));
                }
                color();
                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, e.fout(Interp.circle) * 2f, e.fout(Interp.circle) * 60, e.rotation + (90 * i));
                }
            }),
            largeShockWave = new Effect(60F, 192f, e -> {
                color(e.color);

                z(Layer.effect + 1f);
                blend(Blending.additive);
                Fill.light(e.x, e.y, 360, 80f * e.fin(Interp.pow3Out), Color.clear, Tmp.c1);
                blend();
            }),
            mediumShockWave = new Effect(60F, 128f, e -> {
                color(e.color);

                z(Layer.effect + 1f);
                blend(Blending.additive);
                Fill.light(e.x, e.y, 360, 40f * e.fin(Interp.pow3Out), Color.clear, Tmp.c1);
                blend();
            }),
            smallShockWave = new Effect(60F, 96f, e -> {
                color(e.color);

                z(Layer.effect + 1f);
                blend(Blending.additive);
                Fill.light(e.x, e.y, 360, 20f * e.fin(Interp.pow3Out), Color.clear, Tmp.c1);
                blend();
            }),
            dynamicSpikesModif1 = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 6f + e.finpow() * e.rotation;
                Lines.circle(e.x, e.y, circleRad);

                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 6f, e.rotation * 1.5f * e.fout(), i*90);
                }
                for(int i = 0; i < 8; i++){
                    Drawf.tri(e.x, e.y, 6f, e.rotation * 1f * e.fout(), i*45);
                }

                color();
                for(int i = 0; i < 4; i++){
                    Drawf.tri(e.x, e.y, 3f, e.rotation * 1.45f / 3f * e.fout(), i*90);
                }
                for(int i = 0; i < 8; i++){
                    Drawf.tri(e.x, e.y, 3f, e.rotation * 0.8f / 3f * e.fout(), i*90);
                }

                Drawf.light(e.x, e.y, circleRad * 1f, ArikothUnitPal.mechOrange, e.fout());
            }),
            calefexSmoke = new Effect(90f, 200f, b -> {
                float intensity = 1.5f;

                color(b.color, 0.6f);
                for(int i = 0; i < 3; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 6f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            calefexSmokeLarge = new Effect(90f, 200f, b -> {
                float intensity = 2f;

                color(b.color, 0.6f);
                for(int i = 0; i < 3; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            calefexExplosion = new Effect(30f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 6f + e.finpow() * 15f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 4; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 5f * rand.random(1f, 0.6f) + 2f, e.finpow() * 25f * lenRand + 6f);
                }
            }),
            calefexExplosionLarge = new Effect(30f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 6f + e.finpow() * 25f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 4; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 5f * rand.random(1f, 0.6f) + 2f, e.finpow() * 35f * lenRand + 6f);
                }
            }),
            augmentExplosion = new Effect(120f, 200f, b -> {
                float intensity = 3f;

                color(b.color, 0.5f);
                for(int i = 0; i < 3; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            augmentExplosionLarge = new Effect(120f, 200f, b -> {
                float intensity = 4f;

                color(b.color, 0.5f);
                for(int i = 0; i < 3; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            augmentExplosionWave = new Effect(45f, 224f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 6f + e.finpow() * 60f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for(int i = 0; i < 8; i++){
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 10f * rand.random(1f, 0.6f) + 2f, e.finpow() * 60f * lenRand + 6f);
                }
            }),
            pyroclastSmoke = new Effect(90f, 200f, b -> {
                float intensity = 0.75f;

                color(b.color, 0.6f);
                for(int i = 0; i < 2; i++){
                    rand.setSeed(b.id*2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int)(2.9f * intensity), 6f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            largeFireballsmoke = new Effect(25f, e -> {
                color(Color.gray);

                randLenVectors(e.id, 1, 6f + e.fin() * 7f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.6f + e.fout() * 1.5f);
                });
            }),
            largeFireHit = new Effect(35f, e -> {
                color(Pal.lightFlame, Pal.darkFlame, e.fin());

                randLenVectors(e.id, 4, 6f + e.fin() * 10f, (x, y) -> {
                    Fill.circle(e.x + x, e.y + y, 0.6f + e.fout() * 1.6f);
                });

                color();
            }),
            icherRegenParticle = new Effect(120f, e -> {
                color(ArikothTurretPal.icherLight);

                Fill.poly(e.x, e.y, 6,  e.fslope() * 2f + 0.14f, 0);
            });
}
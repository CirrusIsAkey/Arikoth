package arikoth.content.effects;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Tmp;
import arikoth.content.ArikothLiquids;
import arikoth.palettes.ArikothLifeformPal;
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
        float circleRad = 1f + e.finpow() * 30f;
        Lines.circle(e.x, e.y, circleRad);

        rand.setSeed(e.id);
        for (int i = 0; i < 4; i++) {
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for (int s : Mathf.signs) {
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
        for (int i = 0; i < 8; i++) {
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for (int s : Mathf.signs) {
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
        for (int i = 0; i < 12; i++) {
            float angle = rand.random(360f);
            float lenRand = rand.random(0.5f, 1f);
            Tmp.v1.trns(angle, circleRad);

            for (int s : Mathf.signs) {
                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 12, e.fout() * 24f * lenRand + 6f, angle + 90f + s * 90f);
            }
        }
    }),

    craftSmoke = new Effect(180f, e -> {
        color(Pal.lightPyraFlame);
        alpha(0.4f);

        rand.setSeed(e.id);
        for(int i = 0; i < 3; i++){
            float len = rand.random(4f), rot = rand.range(120f) + e.rotation;

            e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                v.trns(rot, len * b.finpow());
                Fill.circle(e.x + v.x, e.y + v.y, 3.3f * b.fslope() + 0.2f);
            });
        }
    }),

            starExplodeMedium = new Effect(60f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 4f);
                float circleRad = 20f + e.finpow() * 20f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 18; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for (int s : Mathf.signs) {
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
                for (int i = 0; i < 24; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for (int s : Mathf.signs) {
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
                for (int i = 0; i < 3; i++) {
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for (int s : Mathf.signs) {
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
            dynamicSpikesModif1 = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 6f + e.finpow() * e.rotation;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 6f, e.rotation * 1.5f * e.fout(), i * 90);
                }
                for (int i = 0; i < 8; i++) {
                    Drawf.tri(e.x, e.y, 6f, e.rotation * 1f * e.fout(), i * 45);
                }

                color();
                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 3f, e.rotation * 1.45f / 3f * e.fout(), i * 90);
                }
                for (int i = 0; i < 8; i++) {
                    Drawf.tri(e.x, e.y, 3f, e.rotation * 0.8f / 3f * e.fout(), i * 90);
                }

                Drawf.light(e.x, e.y, circleRad * 1f, e.color, e.fout());
            }),
            calefexSmoke = new Effect(90f, 200f, b -> {
                float intensity = 1.5f;

                color(b.color, 0.5f);
                for (int i = 0; i < 1; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
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

                color(b.color, 0.5f);
                for (int i = 0; i < 1; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
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
                float circleRad = 6f + e.finpow() * 10f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 6; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 7f * rand.random(1f, 0.6f) + 2f, e.finpow() * 15f * lenRand + 6f);
                }
            }),
            calefexExplosionLarge = new Effect(30f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 6f + e.finpow() * 15f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 6; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 7f * rand.random(1f, 0.6f) + 2f, e.finpow() * 25f * lenRand + 6f);
                }
            }),
            augmentExplosion = new Effect(120f, 200f, b -> {
                float intensity = 1.5f;

                color(b.color, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            augmentExplosionLarge = new Effect(120f, 200f, b -> {
                float intensity = 3f;

                color(b.color, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
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
                float circleRad = 6f + e.finpow() * 28f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 8; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Lines.lineAngle(e.x, e.y, angle, e.foutpow() * 10f * rand.random(1f, 0.6f) + 2f, e.finpow() * 28f * lenRand + 6f);
                }
            }),
            augmentExplosionWaveLarge = new Effect(90f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 2f);
                float circleRad = 6f + e.finpow() * 60f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 8; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for (int s : Mathf.signs) {
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 12, e.fout() * 20f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            augmentExplosionFrag = new Effect(35f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 2f);
                float circleRad = 6f + e.finpow() * 15f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 8; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for (int s : Mathf.signs) {
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 6, e.fout() * 10f * lenRand + 3f, angle + 90f + s * 90f);
                    }
                }
            }),
            augmentExplosionLight = new Effect(90f, 160f, e -> {
                float circleRad = 6f + e.finpow() * 60f;
                color(ArikothTurretPal.vanadiumLight, e.foutpow());
                Fill.circle(e.x, e.y, circleRad);
            }).layer(Layer.bullet + 2f),
            augmentExplosionFragLight = new Effect(35f, 160f, e -> {
                float circleRad = 6f + e.finpow() * 15f;
                color(ArikothTurretPal.vanadiumLight, e.foutpow());
                Fill.circle(e.x, e.y, circleRad);
            }).layer(Layer.bullet + 2f),
            pyroclastSmoke = new Effect(90f, 200f, b -> {
                float intensity = 0.75f;

                color(b.color, 0.6f);
                for (int i = 0; i < 2; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 6f * intensity, (x, y, in, out) -> {
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
            plasmaLaser = new Effect(40, e -> {
                color(e.color, Color.lightGray, e.fin());
                randLenVectors(e.id, 8, 5f + e.fin() * 10f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.5f, 45);
                });
            }),
            icherRegenParticle = new Effect(120f, e -> {
                color(ArikothTurretPal.icherLight);

                Fill.poly(e.x, e.y, 6, e.fslope() * 2f + 0.14f, 0);
            }),
            crossKaboom = new Effect(60f, 120f, e -> {
                color(e.color);
                stroke(e.fout() * 2f);
                float circleRad = 4f + e.finpow() * e.rotation;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 10 * (e.fout() * 3f + 1) / 4 * (e.fout(Interp.pow3In) + 0.5f) / 1.5f, 10 + rand.random(25, 30) * Mathf.curve(e.fin(), 0, 0.05f) * e.fout(Interp.pow3), i * 90);
                }

                color();
                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 3f, e.rotation * 1.45f / 3f * e.fout(), i * 90);
                }

                Drawf.light(e.x, e.y, circleRad * 1.6f, Pal.heal, e.fout());
            }),
            crossExplode = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 12f, 80 * e.fout(Interp.pow3In), i * 90);
                }
            }),
            crossExplode45 = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 12f, 80 * e.fout(Interp.pow3In), i * 90 + 45);
                }
            }),
            crossExplodeSpin = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);
                e.rotation = e.fin() * 360;

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 12f, 80 * e.fout(Interp.pow3In), i * 90);
                }
            }),
            crossExplodeSmall = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 6f, 60 * e.fout(Interp.pow3In), i * 90);
                }
            }),
            crossExplodeSmall45 = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 6f, 60 * e.fout(Interp.pow3In), i * 90 + 45);
                }
            }),
            CrossExplodeSmallSpin = new Effect(60f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);
                e.rotation = e.fin() * 360;

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 6f, 60 * e.fout(Interp.pow3In), i * 90);
                }
            }),
            crossExplodeVerySmall = new Effect(35f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 6f, 40 * e.fout(Interp.pow3In), i * 90);
                }
            }),
            crossExplodeVerySmall45 = new Effect(35f, 100f, e -> {
                color(e.color);
                stroke(e.fout() * 3f);
                float circleRad = 28;
                Lines.circle(e.x, e.y, circleRad);

                for (int i = 0; i < 4; i++) {
                    Drawf.tri(e.x, e.y, 6f, 40 * e.fout(Interp.pow3In), i * 90 + 45);
                }
            }),
            frameExplosion = new Effect(90f, 160f, e -> {
                color(e.color);
                stroke(e.fout() * 2f);
                float circleRad = 6f + e.finpow() * 40f;
                Lines.circle(e.x, e.y, circleRad);

                rand.setSeed(e.id);
                for (int i = 0; i < 8; i++) {
                    float angle = rand.random(360f);
                    float lenRand = rand.random(0.5f, 1f);
                    Tmp.v1.trns(angle, circleRad);

                    for (int s : Mathf.signs) {
                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 12, e.fout() * 28f * lenRand + 3f, angle + 180);
                    }
                }
            }),
            frameExplosionLight = new Effect(90f, 160f, e -> {
                float circleRad = 6f + e.finpow() * 40f;
                color(ArikothUnitPal.assaultGold, e.foutpow());
                Fill.circle(e.x, e.y, circleRad);
            }).layer(Layer.bullet + 2f),

            strafeExplosionLight = new Effect(90f, 160f, e -> {
            float circleRad = 6f + e.finpow() * 28f;
            color(ArikothTurretPal.explosiveTrail, e.foutpow());
            Fill.circle(e.x, e.y, circleRad);
            }).layer(Layer.bullet + 2f),

            smallDeathSmoke = new Effect(190f, 200f, b -> {
                float intensity = 1.5f;

                color(ArikothLifeformPal.lifeformMid, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            deathSmoke = new Effect(210f, 200f, b -> {
                float intensity = 3.5f;

                color(ArikothLifeformPal.lifeformMid, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            largeDeathSmoke = new Effect(240f, 200f, b -> {
                float intensity = 4.5f;

                color(ArikothLifeformPal.lifeformMid, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            massiveDeathSmoke = new Effect(260f, 200f, b -> {
                float intensity = 6.5f;

                color(ArikothLifeformPal.lifeformMid, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),

            tinyExplosion = new Effect(120f, 200f, b -> {
                float intensity = 1f;

                color(b.color, 0.5f);
                for (int i = 0; i < 2; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            }),
            lifeformMissile = new Effect(40, e -> {
                color(ArikothLifeformPal.lifeformLight, ArikothLifeformPal.lifeformMid, e.fin());
                    randLenVectors(e.id, 3, 6f + e.fin() * 8f, (x, y) -> {
                    Fill.square(e.x + x, e.y + y, e.fout() * 7f + 0.2f, 45);
               });
            }),

            lifeformGlow = new Effect(90f, 160f, e -> {
                float circleRad = 6f + e.finpow() * 10f;
                color(ArikothLifeformPal.lifeformLight, e.foutpow());
                Fill.circle(e.x, e.y, circleRad);
                Drawf.light(e.y, e.x, circleRad, ArikothLifeformPal.lifeformLight, 0.8f);
            }).layer(Layer.bullet + 2f),

            lifeformSmoke = new Effect(180f, e -> {
                color(ArikothLifeformPal.lifeformMid);
                alpha(0.6f);

                rand.setSeed(e.id);
                for(int i = 0; i < 3; i++){
                    float len = rand.random(6f), rot = rand.range(120f) + e.rotation;

                    e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                        v.trns(rot, len * b.finpow());
                        Fill.circle(e.x + v.x, e.y + v.y, 3.3f * b.fslope() + 0.2f);
                    });
                }
            }),

            cerethDeathSmoke = new Effect(190f, 200f, b -> {
                float intensity = 1.5f;

                color(ArikothLiquids.cerethGoop.color, 0.5f);
                for (int i = 0; i < 3; i++) {
                    rand.setSeed(b.id * 2 + i);
                    float lenScl = rand.random(0.5f, 1f);
                    int fi = i;
                    b.scaled(b.lifetime * lenScl, e -> {
                        randLenVectors(e.id + fi - 1, e.fin(Interp.pow10Out), (int) (2.9f * intensity), 11f * intensity, (x, y, in, out) -> {
                            float fout = e.fout(Interp.pow5Out) * rand.random(0.5f, 1f);
                            float rad = fout * ((2f + intensity) * 2.35f);

                            Fill.circle(e.x + x, e.y + y, rad);
                            Drawf.light(e.x + x, e.y + y, rad * 2.5f, b.color, 0.5f);
                        });
                    });
                }
            });
}
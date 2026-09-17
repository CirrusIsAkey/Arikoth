package arikoth.content.blocks;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import arikoth.content.ArikothLiquids;
import arikoth.content.ArikothSounds;
import arikoth.content.ArikothStatusEffects;
import arikoth.content.effects.ArikothHitFx;
import arikoth.content.effects.ArikothShootFx;
import arikoth.math.ArikothInterps;
import arikoth.content.effects.palettes.ArikothPal;
import arikoth.world.blocks.turrets.AccelTurret;
import arikoth.world.entities.bullets.AccelBulletType;
import arikoth.world.entities.bullets.RRRailBulletType;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.effect.SoundEffect;
import mindustry.entities.part.EffectSpawnerPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.draw.DrawTurret;

import static arc.graphics.g2d.Draw.blend;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static arikoth.content.ArikothLiquids.*;
import static arikoth.content.effects.ArikothShootFx.*;
import static mindustry.type.ItemStack.*;
import static arikoth.content.ArikothItems.*;

public class ArikothDefence {
    public static Block
            vector, nail, artilleryEmplacement, FBVice, chainCannonEmplacment, SAMEmplacement,
    end;

    public static void load(){
        String def = "arikoth-arikoth-bullet";
        String pointyDef = "arikoth-arikoth-pointy-bullet";
        String shell = "arikoth-pointy-shell";
        String grenade = "arikoth-explosive";

        vector = new AccelTurret("vector"){{
           requirements(Category.turret, with(tantalum, 25, vanadium, 10, aSilicon, 35));
           size = 1;
           scaledHealth = 120;
           reload = 60;
           rotateSpeed = 3.5f;
           outlineRadius = 0;
           shootY = 4f;

           shootSound = Sounds.shootStell;
           heatColor = ArikothPal.orangeEmber;

           maxAccel = 2;
           speedUpPerShoot = 0.25f;
           recoilTime = 20;
           cooldownTime = 40;

           ammoPerShot = 1;
           range = 160f;
           shoot.shots = 3;
           drawer = new DrawTurret(){{
               iconOverride = new String[]{"-icon"};
               parts.addAll(
                       new RegionPart("-barrel"){{
                           moveY = -1.5f;
                           progress = PartProgress.recoil;
                           layer = Layer.groundUnit + 0.001f;
                           children.addAll(
                                   new RegionPart("-barrel-heat"){{
                                       color = Color.clear;
                                       colorTo = heatColor;
                                       progress = PartProgress.heat;
                                       blending = Blending.additive;
                                       outline = false;
                                       layer = Layer.groundUnit + 0.002f;
                                   }},new RegionPart("-barrel-heat"){{
                                       color = Color.clear;
                                       colorTo = heatColor;
                                       progress = PartProgress.heat;
                                       blending = Blending.additive;
                                       outline = false;
                                       layer = Layer.groundUnit + 0.002f;
                                   }}
                           );
                       }},
                       /*
                       new RegionPart("-round"){{
                           progress = PartProgress.reload;
                           colorTo = Color.clear;
                           color = Color.valueOf("cccccc");
                           x = 12/4f;
                       }},
                       new RegionPart("-round"){{
                           progress = PartProgress.reload;
                           moveX = 12/4f;
                           colorTo = Color.valueOf("cccccc");
                           color = Color.valueOf("eaeaea");
                       }},
                       new RegionPart("-round"){{
                           progress = PartProgress.reload;
                           moveX = 12/4f;
                           x = -12/4f;
                       }},
                        */
                       new RegionPart(""){{
                           layer = 60.003f;
                       }}
               );
           }};
           ammo(
                   aSilicon, new LaserBoltBulletType(12, 20){{
                       inaccuracy = 4;
                       createChance = 0.8f;
                       velocityScaleRandMax = 1.2f;
                       velocityScaleRandMin = 0.9f;
                       lifeScaleRandMin = 0.9f;
                       width = 1;
                       height = 4.5f;
                       shootEffect = new SoundEffect(){{
                           sound = Sounds.shootReign;
                           maxPitch = 1.5f;
                           minPitch = 1.2f;
                           effect = Fx.shootBigColor;
                           minVolume = maxVolume = 0.5f;
                       }};
                       smokeEffect = Fx.shootSmokeSquareSparse;
                       lifetime = 6.66f * 2;

                       trailWidth = 1;
                       trailLength = 4;

                       despawnHit = true;
                       despawnEffect = Fx.none;
                       hitEffect = Fx.hitBulletColor;

                       frontColor = hitColor = ArikothPal.redShellFront;
                       backColor = trailColor = ArikothPal.redShell;
                       lightRadius = 120;
                   }},
                   anthracite, new LaserBoltBulletType(12, 30){{
                       splashDamage = 25;
                       splashDamageRadius = 16;
                       inaccuracy = 4;
                       createChance = 0.8f;
                       velocityScaleRandMax = 1.2f;
                       velocityScaleRandMin = 0.9f;
                       lifeScaleRandMin = 0.9f;
                       width = 1;
                       height = 4.5f;
                       shootEffect = new SoundEffect(){{
                           sound = Sounds.shootReign;
                           maxPitch = 1.5f;
                           minPitch = 1.2f;
                           effect = Fx.shootBigColor;
                           minVolume = maxVolume = 0.5f;
                       }};
                       smokeEffect = Fx.shootSmokeSquareSparse;
                       lifetime = 6.66f * 2;

                       trailWidth = 1;
                       trailLength = 4;

                       despawnHit = true;
                       despawnEffect = Fx.none;
                       hitEffect = ArikothHitFx.sparkExplosion(Interp.pow5Out, 25, 120, 0, 2, 4, 8, 8);

                       trailEffect = Fx.burning;
                       trailInterval = 3;
                       trailChance = 0.008f;

                       fragBullets = 1;
                       fragBullet = new FireBulletType(4, 4);

                       frontColor = hitColor = Pal.lightPyraFlame;
                       backColor = trailColor = Pal.darkPyraFlame;
                       lightRadius = 120;
                   }},
                   sulfur, new LaserBoltBulletType(12, 30){{
                       splashDamage = 10;
                       splashDamageRadius = 16;
                       inaccuracy = 4;
                       createChance = 0.8f;
                       velocityScaleRandMax = 1.2f;
                       velocityScaleRandMin = 0.9f;
                       lifeScaleRandMin = 0.9f;
                       width = 1;
                       height = 4.5f;
                       shootEffect = new SoundEffect(){{
                           sound = Sounds.shootReign;
                           maxPitch = 1.5f;
                           minPitch = 1.2f;
                           effect = Fx.shootBigColor;
                           minVolume = maxVolume = 0.5f;
                       }};
                       smokeEffect = Fx.shootSmokeSquareSparse;
                       lifetime = 6.66f * 2;

                       trailWidth = 1;
                       trailLength = 4;

                       despawnHit = true;
                       despawnEffect = Fx.none;
                       hitEffect = Fx.vapor;

                       trailEffect = Fx.vaporSmall;
                       trailInterval = 3;
                       trailChance = 0.0001f;

                       status = StatusEffects.corroded;
                       statusDuration = 60;

                       frontColor = hitColor = ArikothPal.chemicalAmmoFront;
                       backColor = trailColor = ArikothPal.chemicalAmmo;
                       lightRadius = 120;
                   }}
           );
        }};

        FBVice = new ItemTurret("FB-vice"){{
            requirements(Category.turret, with(vanadium, 80, tantalum, 250, aTungsten, 220, aSilicon, 60));
            size = 4;
            scaledHealth = 200;
            reload = 60;
            rotateSpeed = 2f;
            outlineRadius = 0;
            shootY = 28f;
            elevation = 3;
            shake = 12;

            shootSound = Sounds.explosionArtilleryShockBig;
            shootSoundVolume = 0.4f;
            heatColor = Color.valueOf("ede480");;

            cooldownTime = 180;
            recoilTime = 90;

            shootWarmupSpeed = 0.0005f;
            minWarmup = 0f;

            ammoPerShot = 20;
            maxAmmo = 200;
            range = 780;
            ammoEjectBack = 0;
            ammoUseEffect = ArikothShootFx.punctureCasing;

            shoot = new ShootAlternate(6){{
                shots = 2;
                firstShotDelay = 30;
            }};

            consumeLiquid(ArikothLiquids.icher, 1);

            drawer = new DrawTurret(){{
                iconOverride = new String[]{"-icon"};
                parts.addAll(
                        new RegionPart("-barrel"){{
                            moveY = -12f;
                            progress = PartProgress.recoil.curve(Interp.pow5In);
                            layer = 61.001f;
                            children.addAll(
                                    new RegionPart("-charge"){{
                                        color = Color.clear;
                                        colorTo = Color.white;
                                        progress = PartProgress.charge;
                                        blending = Blending.additive;
                                        outline = false;
                                        layer = 61.002f;
                                    }},new RegionPart("-glow"){{
                                        color = Color.clear;
                                        colorTo = Color.white;
                                        progress = PartProgress.charge;
                                        blending = Blending.additive;
                                        outline = false;
                                        layer = 61.002f;
                                    }}
                            );
                        }},new RegionPart("-upper"){{
                            layer = 62.001f;
                        }},new RegionPart("-flare"){{
                            color = Color.clear;
                            colorTo = Color.valueOf("ffe285");
                            progress = PartProgress.charge;
                            blending = Blending.additive;
                            outline = false;
                            layer = 64;
                        }},
                        new RegionPart("-flare2"){{
                            progress = PartProgress.charge;
                            growProgress = PartProgress.charge;
                            growX = -5f;
                            layer = 64;
                            blending = Blending.additive;
                            color = Color.clear;
                            colorTo = Color.valueOf("ffe285");
                            outline = false;
                        }}
                );
            }};
            ammo(
                    aTungsten, new RailBulletType(){{
                        fragBullets = 1;
                        fragRandomSpread = 0;
                        collidesAir = true;
                        damage = 400;
                        armorMultiplier = 2.8f;

                        shootEffect = new SoundEffect(){{
                            sound = Sounds.shootReign;
                            maxVolume = minVolume = 2.5f;
                            minPitch = 0.6f; maxPitch = 0.8f;
                            effect = new SoundEffect(){{
                                sound = ArikothSounds.plasmaCannon;
                                maxVolume = minVolume = 0.8f;
                                effect = new SoundEffect(){{
                                    sound = ArikothSounds.plasma;
                                    minPitch = 1.2f; maxPitch = 1.4f;
                                    effect = viceShot;
                                }};
                            }};
                        }};
                        length = 780;

                        smokeEffect = new Effect(30, e -> {
                            Draw.z(Layer.effect);
                            Draw.color(Color.white, Color.valueOf("c7c7c7").a(0), e.fin());
                            Tmp.v1.trns(e.rotation, e.fin(Interp.pow5Out) * 20f);
                            Lines.ellipse(Tmp.v1.x + e.x, Tmp.v1.y + e.y, 0.5f * e.fin() + 0.1f, 8, 16, e.rotation);
                            Tmp.v2.trns(e.rotation, e.fin(Interp.pow5Out) * 10f);
                            Lines.ellipse(Tmp.v2.x + e.x, Tmp.v2.y + e.y, 0.7f * e.fin() + 0.1f, 8f * 1.25f, 24, e.rotation);
                            Lines.stroke(6f * e.fin(Interp.pow2Out));
                        });

                        lineEffect = new Effect(20f, 780 * 2 + 10, e -> {
                            if(!(e.data instanceof Vec2 v)) return;

                            color(Color.white, Color.valueOf("c7c7c7"), e.fin());
                            stroke(e.fout() * 0.9f + 0.6f);

                            Fx.rand.setSeed(e.id);
                            for(int i = 0; i < 7; i++){
                                Fx.v.trns(e.rotation, Fx.rand.random(8f, v.dst(e.x, e.y) - 8f));
                                Lines.lineAngleCenter(e.x + Fx.v.x, e.y + Fx.v.y, e.rotation + e.finpow(), e.foutpowdown() * 20f * Fx.rand.random(0.5f, 1f) + 0.3f);
                            }

                            e.scaled(14f, b -> {
                                stroke(b.fout() * 3f);
                                color(e.color);
                                Lines.line(e.x, e.y, v.x, v.y);
                            });
                        });
                    }}
            );
        }};

        artilleryEmplacement = new ItemTurret("artillery-emplacement"){{
            requirements(Category.turret, with(vanadium, 80, tantalum, 250, aTungsten, 220, aSilicon, 60));
            size = 4;
            scaledHealth = 200;
            reload = 180;
            rotateSpeed = 1.2f;
            outlineRadius = 0;
            shootY = 4f;

            shake = 12;

            targetAir = false;

            shootSound = Sounds.shootConquer;
            shootSoundVolume = 0.4f;
            heatColor = Color.valueOf("a88fc7");;

            cooldownTime = 300;
            recoilTime = 90;


            shootWarmupSpeed = 0.0005f;
            minWarmup = 0f;

            elevation = 3;

            ammoPerShot = 20;
            maxAmmo = 200;
            range = 576;
            ammoEjectBack = 0;
            ammoUseEffect = ArikothShootFx.punctureCasing;

            consumeLiquid(flux, 5);

            drawer = new DrawTurret(){{
                iconOverride = new String[]{"-icon"};
                parts.addAll(
                        new RegionPart("-barrel"){{
                            moveY = -12f;
                            progress = PartProgress.recoil.curve(Interp.pow5In);
                            layer = 61.001f;
                            children.addAll(
                                    new RegionPart("-barrel-glow"){{
                                        color = Color.clear;
                                        colorTo = heatColor;
                                        progress = PartProgress.heat;
                                        blending = Blending.additive;
                                        outline = false;
                                        layer = 61.002f;
                                    }},new RegionPart("-barrel-glow"){{
                                        color = Color.clear;
                                        colorTo = heatColor;
                                        progress = PartProgress.heat;
                                        blending = Blending.additive;
                                        outline = false;
                                        layer = 61.002f;
                                    }}
                            );
                        }},
                        new RegionPart("-round"){{
                           progress = PartProgress.smoothReload.compress(0f, 0.25f);
                           colorTo = Color.clear;
                           color = Color.valueOf("cccccc");
                           x = 12/4f;
                            layer = 62f;
                        }},
                        new RegionPart("-round"){{
                           progress = PartProgress.smoothReload.compress(0f, 0.25f);
                           moveX = 12/4f;
                           colorTo = Color.valueOf("cccccc");
                           color = Color.valueOf("eaeaea");
                            layer = 62f;
                        }},
                        new RegionPart("-round"){{
                           progress = PartProgress.smoothReload.compress(0f, 0.25f);
                           moveX = 12/4f;
                           x = -12/4f;
                            layer = 62f;
                        }},

                        new RegionPart("-upper"){{
                            layer = 62.001f;
                        }},
                        new RegionPart("-charge"){{
                            growProgress = PartProgress.smoothReload.compress(0.25f, 0.5f);
                            growX = 5f;
                            layer = 62.002f;
                        }},
                        new RegionPart("-charge-glow"){{
                            progress = PartProgress.smoothReload.compress(0.5f, 0.75f);
                            growProgress = PartProgress.smoothReload.compress(0.25f, 0.5f);
                            growX = -5f;
                            layer = 62.002f;
                            blending = Blending.additive;
                            color = Color.white;
                            colorTo = Color.clear;
                            outline = false;
                        }},
                        new RegionPart("-glow"){{
                            color = Color.clear;
                            colorTo = heatColor;
                            progress = PartProgress.warmup;
                            blending = Blending.additive;
                            outline = false;
                            layer = 64;
                        }},new RegionPart("-glow"){{
                            color = Color.clear;
                            colorTo = heatColor;
                            progress = PartProgress.warmup;
                            blending = Blending.additive;
                            outline = false;
                            layer = 64;
                        }},new RegionPart("-rearGlow"){{
                            color = Color.clear;
                            colorTo = Color.valueOf("a88fc7");
                            progress = PartProgress.heat;
                            blending = Blending.additive;
                            outline = false;
                            layer = 61.003f;
                        }},
                        new EffectSpawnerPart(){{
                            y = -59/4f;
                            x = 0;
                            effect = Fx.turbinegenerate;
                            width = 16;
                            height = 45/4f;
                        }},
                        new EffectSpawnerPart(){{
                            y = -59/4f;
                            x = 0;
                            effectColor = Color.valueOf("a88fc7");
                            effect = ArikothHitFx.sparkExplosion(Interp.pow2Out, 25, 160, 0, 1.5f, 6, 10, 3);
                            width = 16;
                            height = 45/4f;
                        }},
                        new EffectSpawnerPart(){{
                            y = -34/4f;
                            x = 0;
                            effectColor = Color.valueOf("a88fc7");
                            effect = new RadialEffect(new Effect(15f, e -> {
                                color(Color.white, Color.valueOf("a88fc7"), e.fin());
                                Lines.stroke(0.5f + 1.5f * e.fout());
                                blend(Blending.additive);

                                rand.setSeed(e.id);
                                randLenVectors(e.id, 3, 40f * e.fout(), e.rotation, 12, (x, y) -> {
                                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 3f + 0.5f);
                                });

                                blend();
                            }), 2, 180, 2){{
                                rotationOffset = 90;
                            }};
                            width = 16;
                            height = 0;
                            progress = PartProgress.smoothReload.inv();
                        }},
                        new EffectSpawnerPart(){{
                            y = -48/4f;
                            x = 0;
                            effectColor = Color.valueOf("a88fc7");
                            effect = new RadialEffect(new Effect(15f, e -> {
                                color(Color.white, Color.valueOf("a88fc7"), e.fin());
                                Lines.stroke(0.5f + 1.5f * e.fout());
                                blend(Blending.additive);

                                rand.setSeed(e.id);
                                randLenVectors(e.id, 3, 40f * e.fout(), e.rotation, 12, (x, y) -> {
                                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 3f + 0.5f);
                                });

                                blend();
                            }), 2, 180, 2){{
                                rotationOffset = 90;
                            }};
                            width = 16;
                            height = 0;
                            progress = PartProgress.smoothReload.inv();
                        }},
                        new EffectSpawnerPart(){{
                            y = -76/4f;
                            x = 0;
                            effectColor = Color.valueOf("a88fc7");
                            effect = new Effect(15f, e -> {
                                color(Color.white, Color.valueOf("a88fc7"), e.fin());
                                Lines.stroke(0.5f + 1.5f * e.fout());
                                blend(Blending.additive);

                                rand.setSeed(e.id);
                                randLenVectors(e.id, 3, 40f * e.fin(), e.rotation, 12, (x, y) -> {
                                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 3f + 0.5f);
                                });

                                blend();
                            });
                            effectRot = 180;
                            effectChance = 1;
                            width = 16;
                            height = 0;
                            progress = PartProgress.recoil;
                        }}
                );
            }};
            ammo(
                    aTungsten, new BasicBulletType(30, 400, "shell"){{
                        collidesAir = false;
                        width = 12;
                        height = 90f;

                        knockback = 12;

                        shrinkInterp = ArikothInterps.parabola;
                        shrinkX = 0.15f;
                        shrinkY = 0.7f;

                        fragBullets = 30;
                        fragLifeMax = 1.5f;
                        fragLifeMin = 0;
                        fragRandomSpread = 360;
                        fragOffsetMax = 120;
                        fragOffsetMin = 60;
                        fragBullet = new EmptyBulletType(){{
                            lifetime = 120;
                            despawnEffect = Fx.circleColorSpark;
                            fragRandomSpread = 360;
                            fragBullets = 2;
                            fragBullet = new RRRailBulletType(){{
                               damage = 20;
                               createChance = 0.8f;
                               despawnEffect = new SoundEffect(){{
                                  sound = Sounds.shockBullet;
                                  effect = new SoundEffect(){{
                                     sound = Sounds.shootScepterSecondary;
                                     effect = Fx.colorSparkBig;
                                  }};
                               }};
                               status = ArikothStatusEffects.massDischarge;
                               statusDuration = 60;
                               length = 80;
                               lifetime = 15;
                               lineEffect = Fx.chainLightning;
                               hitColor = trailColor = flux.color;
                        }};}};

                        trailEffect = Fx.chainLightning;
                        trailRotation = true;
                        trailInterval = 1;
                        trailWidth = 2.5f;
                        trailLength = 18;

                        shootEffect = new SoundEffect(){{
                            sound = Sounds.shootReign;
                            minVolume = maxVolume = 2.5f;
                            minPitch = 0.5f;
                            maxPitch = 0.6f;
                            effect = new SoundEffect(){{
                                sound = Sounds.shootNavanax;
                                effect = new SoundEffect(){{
                                    sound = ArikothSounds.plasmaCannon;
                                    effect = ArikothShootFx.punctureSparks;
                                    minVolume = 1f;
                                    maxVolume = 1f;
                                    maxPitch = 0.4f;
                                    minPitch = 0.5f;
                                }};
                                minPitch = 0.75f;
                                maxPitch = 0.8f;
                            }};
                            minVolume = maxVolume = 0.75f;
                        }};
                        smokeEffect = ArikothShootFx.punctureSmoke;
                        lifetime = 19.2f;

                        despawnHit = false;

                        hitShake = 4;
                        despawnSound = Sounds.shootMerui;

                        frontColor = Color.white;
                        backColor = hitColor = Color.valueOf("a88fc7");
                        trailColor = hitColor.cpy().a(0.5f);

                        intervalBullets = 1;
                        bulletInterval = 2;
                        intervalRandomSpread = 0;
                        intervalBullet = new RailBulletType(){{
                            damage = 20;
                            status = ArikothStatusEffects.massDischarge;
                            statusDuration = 60;
                            lineEffect = new MultiEffect(Fx.chainLightning, new Effect(20f, 300f, e -> {
                                if(!(e.data instanceof Position p)) return;
                                float tx = p.getX(), ty = p.getY(), dst = Mathf.dst(e.x, e.y, tx, ty);
                                Tmp.v1.set(p).sub(e.x, e.y).nor();

                                float normx = Tmp.v1.x, normy = Tmp.v1.y;
                                float range = 8f;
                                int links = Mathf.ceil(dst / range);
                                float spacing = dst / links;

                                Lines.stroke(2.5f * e.fout());
                                Draw.color(Color.white, e.color, e.fin());

                                Lines.beginLine();

                                Lines.linePoint(e.x, e.y);

                                rand.setSeed(e.id);

                                for(int i = 0; i < links; i++){
                                    float nx, ny;
                                    if(i == links - 1){
                                        nx = tx;
                                        ny = ty;
                                    }else{
                                        float len = (i + 1) * spacing / 2f;
                                        Tmp.v1.setToRandomDirection(rand).scl(range);
                                        nx = e.x + normx * len + Tmp.v1.x;
                                        ny = e.y + normy * len + Tmp.v1.y;
                                    }

                                    Lines.linePoint(nx, ny);
                                }

                                Lines.endLine();
                            }).followParent(false).rotWithParent(false));
                            frontColor = hitColor = trailColor = Color.valueOf("a88fc7");
                        }};
                        lightRadius = 120;

                        hitEffect = despawnEffect = new MultiEffect(
                                Fx.titanSmokeLarge.wrap(hitColor.cpy().a(0.5f)),
                                ArikothHitFx.spikeExplosion(Interp.pow3Out, 80, 400, 120, 4, 12, 42, 18),
                                ArikothHitFx.explosionLight(Interp.pow3Out, 80, 400, 120, 0.8f)
                        );
                    }}
            );
        }};

        chainCannonEmplacment = new AccelTurret("light-flak-battery"){{
            requirements(Category.turret, with(tantalum, 330, vanadium, 180, rhenium, 380, aSilicon, 120));
            size = 4;
            scaledHealth = 120;
            reload = 50;
            maxAccel = 7.5f;
            speedUpPerShoot = 1;
            rotateSpeed = 3.5f;
            targetInterval = 120;
            newTargetInterval = 120;
            outlineRadius = 0;
            shake = 1;
            shootY = 0;
            shootSound = Sounds.shootReign;
            heatColor = ArikothPal.orangeEmber;
            cooldownTime = 5;
            shootWarmupSpeed = 0.08f;
            minWarmup = 0f;
            inaccuracy = 1;
            range = 632.202f;
            ammoPerShot = 2;
            maxAmmo = 120;
            recoils = 4;
            recoilTime = 20;
            shoot = new ShootBarrel(){{
                barrels = new float[]{
                        -22/4f, 49/4f, 0,
                        22/4f, 49/4f, 0,
                        -22/4f, 67/4f, 0,
                        22/4f, 67/4f, 0
                };
            }};
            drawer = new DrawTurret(){{
                parts.addAll(

                        new RegionPart("-barrel"){{
                            moveY = -8;
                            y = 18/4f;
                            progress = PartProgress.recoil;
                            recoilIndex = 2;
                            layer = 62.101f;
                            children.add(new RegionPart("-barrel-glow"){{
                                color = Color.clear;
                                colorTo = heatColor;
                                blending = Blending.additive;
                                outline = false;
                                recoilIndex = 2;
                                progress = PartProgress.recoil;
                            }});
                        }},
                        new RegionPart("-barrel"){{
                            moveY = -8;
                            x = 44/4f;
                            y = 18/4f;
                            progress = PartProgress.recoil;
                            recoilIndex = 3;
                            layer = 62.101f;
                            children.add(new RegionPart("-barrel-glow"){{
                                color = Color.clear;
                                colorTo = heatColor;
                                blending = Blending.additive;
                                outline = false;
                                recoilIndex = 3;
                                progress = PartProgress.recoil;
                            }});
                        }},
                        new RegionPart("-top"){{
                            layer = 62.201f;
                        }},
                        new RegionPart("-barrel"){{
                            moveY = -8;
                            progress = PartProgress.recoil;
                            recoilIndex = 0;
                            layer = 62.201f;
                            children.add(new RegionPart("-barrel-glow"){{
                                color = Color.clear;
                                colorTo = heatColor;
                                blending = Blending.additive;
                                outline = false;
                                recoilIndex = 0;
                                progress = PartProgress.recoil;
                            }});
                        }},
                        new RegionPart("-barrel"){{
                            moveY = -8;
                            x = 44/4f;
                            progress = PartProgress.recoil;
                            recoilIndex = 1;
                            layer = 62.201f;
                            children.add(new RegionPart("-barrel-glow"){{
                                color = Color.clear;
                                colorTo = heatColor;
                                blending = Blending.additive;
                                outline = false;
                                recoilIndex = 1;
                                progress = PartProgress.recoil;
                            }});
                        }},
                        new RegionPart("-topest"){{
                            layer = 62.501f;
                        }},
                        new RegionPart("-laser"){{
                            layer = 63;
                            color = Color.valueOf("8cdba9").a(0.5f);
                            colorTo = Color.valueOf("8cdba9");
                            blending = Blending.additive;
                            outline = false;
                            progress = PartProgress.warmup;
                        }}
                );
            }};
            ammo(
                    rhenium, new FlakBulletType(18, 140){{
                        splashDamage = 90;
                        splashDamageRadius = 32;

                        explodeRange = 10;

                        fragBullets = 3;
                        fragRandomSpread = 4;
                        fragBullet = new RailBulletType(){{
                            damage = 30;
                            pierceArmor = true;
                            length = 48;
                            pierceEffect = Fx.none;
                        }};

                        width = 6;
                        height = 48;
                        pierceCap = 4;
                        pierce = true;
                        shootEffect = new MultiEffect(
                                ArikothShootFx.viceShot,
                                new ParticleEffect(){{
                                    line = true;
                                    length = 60;
                                    lenFrom = 12;
                                    lenTo = 12;
                                    strokeFrom = 4;
                                    strokeTo = 0;
                                    lifetime = 25;
                                    interp = Interp.circleOut;
                                    cone = 10;
                                    particles = 5;
                                    colorFrom = ArikothPal.redShellFront;
                                    colorTo = ArikothPal.redShell;
                                }}
                        );
                        knockback = 2;
                        lifetime = 60;
                        drag = 0.02f;
                        despawnHit = true;
                        despawnShake = 0.5f;
                        hitSound = Sounds.shootStell;
                        hitSoundVolume = 0.8f;
                        hitSoundPitch = 0.9f;
                        despawnEffect = Fx.hitBulletColor;
                        splashDamage = 80;
                        splashDamageRadius = 16;
                        hitEffect = new MultiEffect(
                                ArikothHitFx.spikeExplosion(Interp.pow2Out, 25, 60, 16, 2, 2, 10, 8),
                                ArikothHitFx.explosionLight(Interp.pow2Out, 25, 60, 16, 0.5f)
                        );
                        frontColor = hitColor = ArikothPal.redShellFront;
                        backColor = trailColor = ArikothPal.redShell;
                        lightRadius = 120;
                        trailWidth = 0.75f;
                        trailLength = 8;
                    }},
                    sulfur, new BasicBulletType(18, 140, "shell"){{
                        width = 4;
                        height = 48;
                        pierceCap = 4;
                        pierce = true;
                        shootEffect = new MultiEffect(
                                ArikothShootFx.viceChemicalShot,
                                ArikothShootFx.sulfurShot
                        );
                        knockback = 2;
                        lifetime = 60;
                        drag = 0.02f;
                        despawnHit = true;
                        despawnShake = 0.5f;
                        hitSound = Sounds.explosion;
                        hitSoundVolume = 0.8f;
                        hitSoundPitch = 0.9f;
                        despawnEffect = Fx.hitBulletColor;
                        splashDamage = 80;
                        splashDamageRadius = 16;
                        trailEffect = Fx.corrosionVapor;
                        trailInterval = 3;
                        hitEffect = new MultiEffect(
                                ArikothHitFx.sparkExplosion(Interp.pow2Out, 25, 60, 0, 2.5f, 14, 32, 8),
                                ArikothHitFx.explosionLight(Interp.pow2Out, 25, 60, 32, 0.5f),
                                ArikothHitFx.sulfurExplosionSmall
                        );
                        frontColor = hitColor = ArikothPal.chemicalAmmoFront;
                        backColor = trailColor = ArikothPal.chemicalAmmo;
                        lightRadius = 120;
                        trailWidth = 2;
                        trailLength = 8;
                        status = StatusEffects.corroded;
                        statusDuration = 180;
                    }}
            );
        }};

        SAMEmplacement = new ItemTurret("SAM-emplacement"){{
            requirements(Category.turret, with(tantalum, 440, vanadium, 220, rhenium, 230, aSilicon, 80));
            size = 4;
            scaledHealth = 120;
            reload = 180;
            rotateSpeed = 3.5f;
            targetInterval = 120;
            newTargetInterval = 120;
            outlineRadius = 0;
            shake = 1;
            shootY = 0;
            shootSound = Sounds.shootMissileSmall;
            heatColor = ArikothPal.orangeEmber;
            cooldownTime = 5;
            shootWarmupSpeed = 0.0005f;
            minWarmup = 0f;
            inaccuracy = 0;
            range = 720f;
            consumeAmmoOnce = false;
            ammoPerShot = 1;
            maxAmmo = 72;
            shoot = new ShootBarrel(){{
                barrels = new float[]{
                        13/4f, -19/4f, 0,
                        32/4f, -2, 0,
                        13/4f, 1, 0,

                        -13/4f, -19/4f, 0,
                        -32/4f, -2, 0,
                        -13/4f, 1, 0
                };
                shots = 6;
                shotDelay = 5;
            }};
            targetGround = false;
            recoils = 6;
            drawer = new DrawTurret(){{
                parts.addAll(
                        new RegionPart(""){{
                            children.addAll(new RegionPart("-glow"){{
                                color = Color.clear;
                                colorTo = ArikothPal.orangeEmber.cpy().a(0.5f);
                                blending = Blending.additive;
                                outline = false;
                            }}, new RegionPart("-recoil"){{
                                recoilIndex = 0;
                                x = 13/4f;
                                y = -19/4f;
                            }}, new RegionPart("-recoil"){{
                                recoilIndex = 1;
                                x = 32/4f;
                                y = -2/4f;
                            }}, new RegionPart("-recoil"){{
                                recoilIndex = 2;
                                x = 13/4f;
                                y = 1/4f;
                            }}, new RegionPart("-recoil"){{
                                recoilIndex = 3;
                                x = -13/4f;
                                y = -1;
                            }}, new RegionPart("-recoil"){{
                                recoilIndex = 4;
                                x = -32/4f;
                                y = -2/4f;
                            }}, new RegionPart("-recoil"){{
                                recoilIndex = 5;
                                x = -13/4f;
                                y = -19/4f;
                            }});
                        }}
                );
            }};
            ammo(
                    aSilicon, new AccelBulletType(8, 180, "arikoth-rocket"){{
                        collidesGround = false;
                        splashDamage = damage;
                        splashDamageRadius = 32;
                        velocityBegin = 1;
                        velocityIncrease = 8;
                        lifeScaleRandMax = 1.2f;
                        lifeScaleRandMin = 0.8f;
                        velocityScaleRandMax = 1.2f;
                        followAimSpeed = 1f;
                        accelInterp = Interp.pow2Out;
                        frontColor = ArikothPal.yellowEmber;
                        hitColor = trailColor = backColor = ArikothPal.orangeEmber;
                        width = 16;
                        height = 24;
                        trailWidth = 2;
                        trailLength = 12;
                        weaveMag = 10f;
                        weaveScale = 4;
                        lifetime = 90;
                        hitSound = despawnSound = Sounds.blockExplode2Alt;
                        homingPower = 0.25f;
                        homingRange = 300;
                        homingDelay = 5;
                        smokeEffect = new MultiEffect(
                                Fx.shootSmokeDisperse
                        );
                        hitEffect = despawnEffect = new SoundEffect(){{effect = new MultiEffect(
                                ArikothHitFx.sparkExplosion(Interp.pow2Out, 48, 160, 0, 2, 8, 32, 12),
                                ArikothHitFx.spikeExplosion(Interp.pow2Out, 48, 160, 32, 2, 6, 22, 8),
                                ArikothHitFx.explosionLight(Interp.pow2Out, 48, 160, 32, 1)
                        ); sound = Sounds.blockExplodeExplosiveAlt;}};
                    }}
            );
        }};
    }
}

package arikoth.content;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.util.Tmp;
import arikoth.Arikoth;
import arikoth.math.Parallax;
import arikoth.palettes.ArikothTurretPal;
import arikoth.palettes.ArikothUnitPal;
import arikoth.palettes.VanillaPal;
import arikoth.world.entities.bullets.*;
import arikoth.world.type.LightEngine;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.abilities.SpawnDeathAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.unit.MissileUnitType;
import mindustry.type.unit.TankUnitType;
import mindustry.type.weapons.RepairBeamWeapon;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import static arikoth.content.ArikothFx.rand;
import static arikoth.content.ArikothFx.temp;
import static mindustry.content.Fx.*;
import static mindustry.gen.Sounds.boom;
import static mindustry.gen.Sounds.explosionbig;

public class ArikothUnitTypes {

    public static UnitType

            //Core
            vision, seeker, overseer,
            //Assault
            bolt, hasp, frame, edifice, cathedral,
            //spider-assault
            cavalier, knight, sabreur, phalanx, dragoon,
            //hover cruisers
            kindle, calxel, redox, mesa, sahara,
            //scout copters
            messenger, nomad, outrider, vanguard, oracle,
            //scout tanks
            draft, zephyr, squall, derecho, hurricane,
            //specialist spider thingy
            react, decay, fissile, ionize, nucleon, positron,
            //hybrids
            //airships
            iracund, aggravate, cantankerous,
            //Tx units
            prototype;

    public static void load() {
        bolt = new UnitType("bolt"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = ArikothUnitPal.unitOutline;
            constructor = MechUnit::create;
            speed = 0.9f;
            hitSize = 12f;
            mechStepParticles = true;
            health = 820;
            armor = 6;
            weapons.add(new Weapon(
                "arikoth-bolt-weapon"){{
                reload = 10;
                x = -6f;
                y = 0f;
                top = false;
                shootSound = Sounds.shootSnap;
                ejectEffect = casing1;
                bullet = new BasicBulletType(6, 20){{
                    speed = 6;
                    damage = 30;
                    width = 8;
                    height = 12;
                    trailWidth = 2;
                    trailLength = 10;
                    trailColor = backColor = hitColor = lightColor = frontColor = ArikothUnitPal.assaultGold;
                    lifetime = 15;
                    hitSound = Sounds.none;
                    shootEffect = shootSmallColor;
                    smokeEffect = shootSmallSmoke;
                    hitEffect = hitBulletColor;
                    despawnEffect = none;
                    despawnHit = true;
                }};
            }});
        }};

        hasp = new UnitType("hasp"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = ArikothUnitPal.unitOutline;
            constructor = MechUnit::create;
            speed = 0.9f;
            hitSize = 18f;
            mechStepParticles = true;
            health = 1450;
            armor = 8;
            weapons.add(new Weapon(
                    "arikoth-hasp-weapon"){{
                reload = 15;
                alternate = true;
                x = 8f;
                y = 0f;
                shootY = 12;
                inaccuracy = 4;
                top = false;
                shootSound = Sounds.flame;
                ejectEffect = Fx.none;
                for(int i = 0; i < 5; i++){
                    int fi = i;
                    parts.add(new RegionPart("-spine"){{
                        x = -2f;
                        y = 21f / 4f - 45f / 4f * fi / 4f;
                        moveX = 21f / 4f + Mathf.slope(fi / 4f) * 1.25f;
                        moveRot = 10f - fi * 14f;
                        float fin = fi  / 4f;
                        progress = PartProgress.reload.inv().mul(1.3f).add(0.1f).sustain(fin * 0.34f, 0.14f, 0.14f);
                        layerOffset = -0.001f;
                        mirror = false;
                    }});
                }
                bullet = new ContinuousFlameBulletType(){{
                    length = 30;
                    damage = 40;
                    width = 4;
                    flareRotSpeed = 0;
                    flareColor = hitColor = ArikothUnitPal.assaultGoldDark;
                    lifetime = 13f;
                    pierce = true;
                    pierceArmor = true;
                    pierceBuilding = true;
                    pierceCap = 2;
                    colors = new Color[]{ArikothUnitPal.assaultGoldDark, ArikothUnitPal.assaultGold, Color.white};
                    shootEffect = new MultiEffect(
                    new Effect(25, e -> {
                        color(e.color, Color.gray, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 12; i++){
                            float rot = e.rotation + rand.range(30f);
                            v.trns(rot, rand.random(e.finpow() * 42f));
                            Fill.poly(e.x + v.x, e.y + v.y, 6, e.fout() * 6f + 0.2f, rand.random(360f));
                        };
                    }),
                    new Effect(25, e -> {
                        color(e.color, ArikothUnitPal.assaultGoldDark, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 12; i++){
                            float rot = e.rotation + rand.range(30f);
                            v.trns(rot, rand.random(e.finpow() * 42f));
                            Fill.poly(e.x + v.x, e.y + v.y, 6, e.fout() * 3f + 0.2f, rand.random(360f));
                        };
                    })
                    );
                    hitEffect = Fx.hitFlameSmall;
                    despawnEffect = Fx.none;
                    status = StatusEffects.burning;
                    statusDuration = 60f * 2;
                    keepVelocity = false;
                    hittable = false;
                }};
            }});
        }};

        frame = new UnitType("frame"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = ArikothUnitPal.unitOutline;
            constructor = MechUnit::create;
            speed = 0.7f;
            hitSize = 24f;
            mechStepParticles = true;
            health = 2300;
            armor = 12;
            weapons.add(new Weapon(
                    "arikoth-frame-weapon"){{
                reload = 120f;
                x = 11f;
                y = 0f;
                shake = 6;
                shootY = 12;
                top = false;
                shootSound = Sounds.shootBig;
                ejectEffect = Fx.casing4;
                inaccuracy = 4;
                velocityRnd = 0.4f;
                shoot = new ShootSpread(8,2.5f);
                bullet = new BasicBulletType(8, 20){{
                    speed = 6;
                    damage = 30;
                    width = 12;
                    height = 12;
                    trailWidth = 2.5f;
                    trailLength = 10;
                    trailColor = backColor = hitColor = lightColor = frontColor = ArikothUnitPal.assaultGold;
                    lifetime = 15;
                    hitSound = Sounds.none;
                    shootEffect = shootSmallColor;
                    smokeEffect = shootSmallSmoke;
                    hitEffect = hitBulletColor;
                    despawnEffect = none;
                    despawnHit = true;
                }};
            }});
        }};

        edifice = new UnitType("edifice"){{
            researchCostMultiplier = 0.5f;
            outlineRadius = 4;
            outlineColor = mechLegColor = ArikothUnitPal.unitOutline;
            constructor = MechUnit::create;
            speed = 0.4f;
            stepShake = 4.5f;
            hitSize = 28f;
            mechStepParticles = true;
            health = 9800;
            armor = 18;
            weapons.add(new Weapon(
                    "arikoth-edifice-weapon"){{
                reload = 160;
                x = 12f;
                y = 0f;
                shake = 6;
                shootY = 18;
                top = false;
                shootSound = Sounds.mediumCannon;
                ejectEffect = Fx.casing4;
                shoot = new ShootSpread(){{
                   spread = 8;
                   shots = 4;
                }};
                bullet = new BasicBulletType(6f, 60, "shell"){{
                    lifetime = 40;
                    hitEffect = new MultiEffect(
                      new ExplosionEffect(){{
                      lifetime = 20f;
                      waveStroke = 2f;
                      waveColor = smokeColor = sparkColor = trailColor;
                      waveRad = 12f;
                      smokeSize = 5f;
                      smokeSizeBase = 0f;
                      sparks = 15;
                      sparkRad = 40f;
                      sparkLen = 8f;
                      sparkStroke = 1.5f;
                      }},
                      Fx.flakExplosion
                    );
                    despawnHit = true;
                    width = 18;
                    height = 24;
                    homingPower = 0.05f;
                    homingDelay = 10;
                    shrinkY = 0.2f;
                    frontColor = Color.white;
                    backColor = hitColor = lightColor = trailColor = ArikothUnitPal.mechOrange.lerp(Pal.redLight, 0.5f);;
                    hitSound = Sounds.dullExplosion;
                    hitSoundPitch = 0.9f;
                    trailLength = 18;
                    trailWidth = 3;
                    trailSinMag = 0.03f;
                    trailSinScl = 6;
                }};
            }});
        }};

        cathedral = new UnitType("cathedral"){{
            researchCostMultiplier = 0.5f;
            outlineRadius = 4;
            outlineColor = mechLegColor = ArikothUnitPal.unitOutline;
            constructor = MechUnit::create;
            speed = 0.2f;
            stepShake = 6f;
            hitSize = 35f;
            mechStepParticles = true;
            health = 23400;
            armor = 24;
            weapons.add(new Weapon("arikoth-cathedral-artillery"){{
                reload = 70;
                x = 50f / 4f;
                y = -44f / 4f;
                shootY = 5.5f;
                recoil = 2f;
                rotate = true;
                mirror = true;
                rotateSpeed = 1.5f;
                layerOffset = 0.05f;
                inaccuracy = 6f;
                shoot.shots = 3;
                shoot.shotDelay = 8;
                shootSound = Sounds.shootAlt;
                bullet = new ArtilleryBulletType(4.5f, 25){{
                    width = 12f;
                    height = 16f;
                    lifetime = 90f;
                    shootEffect = Fx.sparkShoot;
                    smokeEffect = Fx.shootBigSmoke;
                    trailEffect = Fx.none;
                    hitColor = backColor = trailColor = ArikothUnitPal.mechOrange;
                    frontColor = Color.white;
                    trailWidth = 2.5f;
                    trailLength = 6;
                    hitEffect = despawnEffect = Fx.colorSparkBig;
                }};
            }});
            weapons.add(new Weapon(
                    "arikoth-cathedral-weapon"){{
                reload = 120;
                x = 22.5f;
                y = 0f;
                recoil = 4.5f;
                shake = 8;
                shootY = 12;
                top = false;
                shootSound = Sounds.largeCannon;
                ejectEffect = Fx.none;
                bullet = new BasicBulletType(8f, 420, "shell"){{
                    lifetime = 40;
                    fragBullets = 1;
                    splashDamage = 120;
                    splashDamageRadius = 7 * 4;
                    fragBullet = new BasicBulletType(0f, 18){{
                        lifetime = 60;
                        collides = false;
                        hitColor = ArikothUnitPal.mechOrange;
                        despawnEffect = Fx.none;
                        height = 0;
                        width = 0;
                        bulletInterval = 2.5f;
                        intervalBullets = 2;
                        intervalBullet = new FireBulletType(6f,20) {{
                            lifetime = 30;
                            radius = 4;
                            collides = true;
                            absorbable = false;
                            hitEffect = Fx.fireHit;
                            drag = 0.0001f;
                            colorFrom = ArikothUnitPal.fireOrangeLight;
                            colorMid = ArikothUnitPal.fireOrangeMid;
                            colorTo = ArikothUnitPal.fireOrange;
                        }};
                    }};
                    despawnEffect = new MultiEffect(ArikothFx.starExplodeMedium, ArikothFx.tinyStarFour);
                    fragOnAbsorb = true;
                    fragOnHit = false;
                    pierceCap = 6;
                    width = 18;
                    height = 32;
                    shrinkY = 0.2f;
                    status = StatusEffects.burning;
                    statusDuration = 12f * 60f;
                    frontColor = Color.white;
                    backColor = hitColor = lightColor = trailColor = ArikothUnitPal.mechOrange.lerp(Pal.redLight, 0.5f);;
                    hitShake = 8f;
                    hitSound = Sounds.largeExplosion;
                    trailLength = 28;
                    trailWidth = 3.5f;
                    int count = 10;
                    for(int j = 0; j < count; j++){
                        int s = j;
                        for(int i : Mathf.signs){
                            float fin = 0.1f + (j + 4) / (float)count;
                            float spd = speed;
                            float life = lifetime / Mathf.lerp(fin, 1f, 0.5f);
                            spawnBullets.add(new BasicBulletType(spd * fin, 30){{
                                drag = 0.01f;
                                width = 12f;
                                height = 14f;
                                lifetime = life + 5f;
                                weaveRandom = false;
                                hitSize = 5f;
                                hitColor = backColor = trailColor = ArikothUnitPal.mechOrange;
                                frontColor = Color.white;
                                trailWidth = 2.5f;
                                trailLength = 7;
                                weaveScale = (3f + s/2f) / 1.2f;
                                weaveMag = i * (4f - fin * 2f);

                                splashDamage = 65f;
                                splashDamageRadius = 30f;
                                despawnEffect = ArikothFx.spikyBoom;
                            }});
                        }
                    }
                }};
            }});
        }};

        kindle = new UnitType("kindle"){{
            constructor = ElevationMoveUnit::create;
            hovering = true;
            canDrown = false;
            shadowElevation = 0.1f;
            outlineColor = ArikothUnitPal.unitOutline;

            drag = 0.03f;
            speed = 2.25f;
            rotateSpeed = 3.5f;

            accel = 0.08f;
            health = 600f;
            armor = 1f;
            hitSize = 11f;
            engineSize = 0f;
            itemCapacity = 0;
            useEngineElevation = false;
            researchCostMultiplier = 0f;
            engineColor = ArikothUnitPal.assaultGold;

            abilities.add(new MoveEffectAbility(0f, -10, ArikothUnitPal.assaultGold, Fx.missileTrailShort, 2f));

            parts.add(new HoverPart(){{
                y = -10;
                radius = 6f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});
            engines.add(new LightEngine(-0, -10, 6.5f, -90, 0.45f, 0.6f, 2.6f));

            weapons.add(new Weapon("placeholder weapon"){{
                shootSound = Sounds.blaster;
                y = 6f;
                x = 0f;
                top = true;
                mirror = false;
                reload = 20f;
                shootCone = 20f;
                shoot = new ShootAlternate(6);

                bullet = new BasicBulletType(){{
                    damage = 20;
                    speed = 4;
                    width = 8f;
                    height = 12;
                    trailWidth = 2;
                    trailLength = 12;
                    lifetime = 30f;
                    shootEffect = Fx.colorSparkBig;
                    smokeEffect = Fx.shootSmokeSquareSparse;
                    hitColor = trailColor = backColor = ArikothUnitPal.assaultGold;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 6; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 21f));
                            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 4f + 0.2f, rand.random(360f));
                        };
                    });
                }};
            }});
        }};

        calxel = new UnitType("calxel"){{
            constructor = ElevationMoveUnit::create;
            hovering = true;
            canDrown = false;
            shadowElevation = 0.1f;
            outlineColor = ArikothUnitPal.unitOutline;

            drag = 0.03f;
            speed = 1.8f;
            rotateSpeed = 3f;

            accel = 0.08f;
            health = 2100;
            armor = 2f;
            hitSize = 16f;
            engineSize = 0f;
            itemCapacity = 0;
            useEngineElevation = false;
            researchCostMultiplier = 0f;
            engineColor = ArikothUnitPal.assaultGold;

            abilities.add(new MoveEffectAbility(0f, -14, ArikothUnitPal.assaultGold, Fx.missileTrail, 2f));

            parts.add(new HoverPart(){{
                y = -14;
                radius = 9f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});
            parts.add(new HoverPart(){{
                y = 6;
                x = 8;
                mirror = true;
                radius = 6f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});
            engines.add(new LightEngine(-0, -14, 7f, -90, 0.45f, 0.6f, 2.6f));

            weapons.add(new Weapon("placeholder weapon"){{
                shootSound = Sounds.cannon;
                chargeSound = Sounds.lasercharge2;
                y = 6f;
                x = 0f;
                top = true;
                mirror = false;
                reload = 120f;
                shootCone = 20f;
                shake = 6;
                shootStatus = StatusEffects.unmoving;
                shootStatusDuration = 60;
                shoot = new ShootSpread(3, 20){{
                    firstShotDelay = 90;
                }};

                bullet = new AimBulletType(4, 60){{
                    sprite = "large-orb";
                    damage = 60;
                    maxRange = 270;
                    homingRange = 270;
                    homingPower = 0.18f;
                    homingDelay = 1.5f;
                    hitSound = despawnSound = Sounds.plasmaboom;
                    splashDamage = 60;
                    splashDamageRadius = 16;
                    speed = 6;
                    width = 14;
                    height = 18;
                    trailWidth = 3;
                    trailLength = 12;
                    lifetime = 30f;
                    shootEffect = new MultiEffect(
                    new Effect(25f, e -> {
                        color(Color.white, e.color, e.fin());
                        stroke(e.fout() * 1.3f + 0.7f);

                        randLenVectors(e.id, 16, 41f * e.fin(), e.rotation, 20f, (x, y) -> {
                            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 0.5f);
                        });
                    }),
                    new Effect(25f, 160f, e -> {
                        color(e.color);
                        stroke(e.fout() * 5f);
                        float circleRad = 0f + e.finpow() * 0f;
                        Lines.circle(e.x, e.y, circleRad);

                        rand.setSeed(e.id);
                        for(int i = 0; i < 3; i++){
                            float angle = rand.random(360f);
                            float lenRand = rand.random(0.5f, 1f);
                            Tmp.v1.trns(angle, circleRad);

                            for(int s : Mathf.signs){
                                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 20f, e.fout() * 30f * lenRand + 6f, angle + 90f + s * 90f);
                            }
                        }
                    }),
                    new Effect(25, e -> {
                        color(e.color, Color.gray, e.fin());
                        float w = 2.4f + 9 * e.fout();
                        Drawf.tri(e.x, e.y, w, 64f * e.fout(), e.rotation);
                        Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
                    })
                    );
                    smokeEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 12; i++){
                            float rot = e.rotation + rand.range(30f);
                            v.trns(rot, rand.random(e.finpow() * 42f));
                            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 6f + 0.2f, rand.random(360f));
                        };
                    });
                    hitColor = trailColor = backColor = ArikothUnitPal.assaultGold;
                    frontColor = Color.white;
                    chargeEffect = new MultiEffect(
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 3;
                        lifetime = 35;
                        baseLength = -40;
                        length = 40;
                        interp = Interp.swingIn;
                        colorTo = ArikothUnitPal.assaultGold;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 3;
                        lifetime = 40;
                        baseLength = -40;
                        length = 40;
                        interp = Interp.swingIn;
                        colorTo = ArikothUnitPal.assaultGold;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 3;
                        lifetime = 45;
                        baseLength = -40;
                        length = 40;
                        interp = Interp.swingIn;
                        colorTo = ArikothUnitPal.assaultGold;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 3;
                        lifetime = 50;
                        baseLength = -40;
                        length = 40;
                        interp = Interp.swingIn;
                        colorTo = ArikothUnitPal.assaultGold;
                        sizeInterp = Interp.linear;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 3;
                        lifetime = 55;
                        baseLength = -40;
                        length = 40;
                        interp = Interp.swingIn;
                        colorTo = ArikothUnitPal.assaultGold;
                        sizeInterp = Interp.linear;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 3;
                        lifetime = 60;
                        baseLength = -40;
                        length = 40;
                        interp = Interp.swingIn;
                        colorTo = ArikothUnitPal.assaultGold;
                        sizeInterp = Interp.linear;
                    }},
                    new WaveEffect(){{
                        sizeFrom = 30;
                        sizeTo = 0;
                        lifetime = 60;
                        strokeFrom = 0;
                        strokeTo = 4;
                        interp = Interp.linear;
                        colorTo = ArikothUnitPal.assaultGold;
                    }}
                    );
                    hitEffect = despawnEffect = new MultiEffect(
                    new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 8; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 30f));
                            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 6f + 0.2f, rand.random(360f));
                        };
                    }),
                    new Effect(25f, 160f, e -> {
                        color(e.color);
                        stroke(e.fout() * 5f);
                        float circleRad = 0f + e.finpow() * 0f;
                        Lines.circle(e.x, e.y, circleRad);

                        rand.setSeed(e.id);
                        for(int i = 0; i < 3; i++){
                            float angle = rand.random(360f);
                            float lenRand = rand.random(0.5f, 1f);
                            Tmp.v1.trns(angle, circleRad);

                            for(int s : Mathf.signs){
                                Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 20f, e.fout() * 30f * lenRand + 6f, angle + 90f + s * 90f);
                            }
                        }
                    })
                    );
                }};
            }});
        }};

        redox = new UnitType("redox"){{
            constructor = ElevationMoveUnit::create;
            hovering = true;
            canDrown = false;
            shadowElevation = 0.15f;
            outlineColor = ArikothUnitPal.unitOutline;

            drag = 0.03f;
            speed = 1.25f;
            rotateSpeed = 2f;

            accel = 0.07f;
            health = 4450;
            armor = 8f;
            hitSize = 26f;
            engineSize = 0f;
            itemCapacity = 0;
            useEngineElevation = false;
            researchCostMultiplier = 0f;
            engineColor = ArikothUnitPal.assaultGold;

            abilities.add(new MoveEffectAbility(48f / 4f, -70f / 4f, ArikothUnitPal.assaultGold, Fx.missileTrail, 4f));
            abilities.add(new MoveEffectAbility(-48f / 4f, -70f / 4f, ArikothUnitPal.assaultGold, Fx.missileTrail, 4f));
            abilities.add(new MoveEffectAbility(0, -76f / 4f, ArikothUnitPal.assaultGold, Fx.missileTrail, 4f));

            parts.add(new HoverPart(){{
                x = 0;
                y = -76f / 4f;
                radius = 10f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});
            parts.add(new HoverPart(){{
                x = -48f / 4f;
                y = -70f / 4f;
                mirror = true;
                radius = 8f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});
            parts.add(new HoverPart(){{
                x = 49f / 4f;
                y = 11f / 4f;
                mirror = true;
                radius = 6f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});
            parts.add(new HoverPart(){{
                x = 0f;
                y = 38f / 4f;
                mirror = true;
                radius = 8f;
                phase = 60f;
                stroke = 2f;
                sides = 360;
                layerOffset = -0.001f;
                color = ArikothUnitPal.assaultGold;
            }});

            engines.add(new LightEngine(0, -76f / 4f, 12f, -90, 0.45f, 0.6f, 2.6f));
            engines.add(new LightEngine(48f / 4f, -70f / 4f, 7f, -90, 0.45f, 0.6f, 2.6f));
            engines.add(new LightEngine(-48f / 4f, -70f / 4f, 7f, -90, 0.45f, 0.6f, 2.6f));

            weapons.add(new Weapon(name + "-howitzer"){{
                shootSound = Sounds.missileSmall;
                y = -6f;
                x = 8f;
                top = true;
                layerOffset = 0.0001f;
                mirror = true;
                reload = 90f;
                shootCone = 20f;
                shake = 6;

                bullet = new BasicBulletType(){{
                    int count = 1;
                    for(int j = 0; j < count; j++){
                        int s = j;
                        for(int i : Mathf.signs){
                            float fin = 0.1f + (j + 8) / (float)count;
                            float spd = speed;
                            spawnBullets.add(new BasicBulletType(4, 30){{
                                drag = 0.01f;
                                width = 14f;
                                height = 14f;
                                lifetime = 60;
                                weaveRandom = false;
                                hitSize = 5f;
                                hitColor = backColor = trailColor = ArikothUnitPal.assaultGold;
                                frontColor = Color.white;
                                trailWidth = 3f;
                                trailLength = 18;
                                weaveScale = (6f + s/4f) / 2f;
                                weaveMag = i * (8f - fin * 3f);

                                damage = 20;
                                splashDamage = 25f;
                                splashDamageRadius = 12f;
                                despawnEffect = ArikothFx.spikyBoom;
                            }});
                        }
                    }
                    sprite = "arikoth-rocket";
                    homingPower = 0.08f;
                    homingRange = 90;
                    hitSound = despawnSound = Sounds.plasmaboom;
                    splashDamage = 90;
                    splashDamageRadius = 16;

                    damage = 90;
                    speed = 3;
                    width = 14;
                    height = 18;
                    trailWidth = 3;
                    trailLength = 18;
                    lifetime = 60;
                    trailSinScl = 2.5f;
                    trailSinMag = 0.5f;
                    shootEffect = new MultiEffect(
                            new ParticleEffect(){{
                                strokeFrom = 4;
                                strokeTo = 0;
                                lenFrom = 12;
                                lenTo = 0;
                                lifetime = 25;
                                baseLength = 2;
                                length = 41;
                                interp = Interp.fastSlow;
                                sizeInterp = Interp.linear;
                                colorTo = ArikothUnitPal.assaultGold;
                            }},
                            new Effect(25f, e -> {
                                color(Color.white, e.color, e.fin());
                                stroke(e.fout() * 1.3f + 0.7f);

                                randLenVectors(e.id, 16, 41f * e.fin(), e.rotation, 20f, (x, y) -> {
                                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 0.5f);
                                });
                            }),
                            new Effect(50f, e -> {
                                color(Color.white, e.color, e.fin());
                                stroke(e.fout() * 0.9f + 0.7f);

                                randLenVectors(e.id, 8, 60f * e.fin(), e.rotation, 20f, (x, y) -> {
                                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 6f + 0.5f);
                                });
                            }),
                            new Effect(25f, 160f, e -> {
                                color(e.color);
                                stroke(e.fout() * 5f);
                                float circleRad = 0f + e.finpow() * 0f;
                                Lines.circle(e.x, e.y, circleRad);

                                rand.setSeed(e.id);
                                for(int i = 0; i < 3; i++){
                                    float angle = rand.random(360f);
                                    float lenRand = rand.random(0.5f, 1f);
                                    Tmp.v1.trns(angle, circleRad);

                                    for(int s : Mathf.signs){
                                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 22f, e.fout() * 38f * lenRand + 6f, angle + 90f + s * 90f);
                                    }
                                }
                            }),
                            new Effect(35, e -> {
                                color(Color.white, e.color, e.fin());
                                float w = 6f + 9 * e.fout();
                                Drawf.tri(e.x, e.y, w, 120f * e.fout(), e.rotation);
                                Drawf.tri(e.x, e.y, w, 6f * e.fout(), e.rotation + 180f);
                            })
                    );
                    smokeEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 12; i++){
                            float rot = e.rotation + rand.range(30f);
                            v.trns(rot, rand.random(e.finpow() * 42f));
                            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 6f + 0.2f, rand.random(360f));
                        };
                    });
                    hitColor = trailColor = backColor = ArikothUnitPal.assaultGold;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = new MultiEffect(
                            ArikothFx.spikyBoomMedium,
                            ArikothFx.tinyStarFour,
                            new Effect(60f, 160f, e -> {
                                float circleRad = 6f + e.finpow() * 20f;

                                color(e.color, e.foutpow());
                                Fill.circle(e.x, e.y, circleRad);
                            }).layer(Layer.bullet + 2f),

                            new Effect(25, e -> {
                                color(Color.white, e.color, e.fin());

                                rand.setSeed(e.id);
                                for(int i = 0; i < 8; i++){
                                    float rot = e.rotation + rand.range(360f);
                                    v.trns(rot, rand.random(e.finpow() * 30f));
                                    Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 6f + 0.2f, rand.random(360f));
                                };
                            }),
                            new Effect(25f, 160f, e -> {
                                color(e.color);
                                stroke(e.fout() * 5f);
                                float circleRad = 0f + e.finpow() * 0f;
                                Lines.circle(e.x, e.y, circleRad);

                                rand.setSeed(e.id);
                                for(int i = 0; i < 6; i++){
                                    float angle = rand.random(360f);
                                    float lenRand = rand.random(0.5f, 1f);
                                    Tmp.v1.trns(angle, circleRad);

                                    for(int s : Mathf.signs){
                                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 6f, e.fout() * 40f * lenRand + 6f, angle + 90f + s * 90f);
                                    }
                                }
                            })
                    );
                }};
            }});
        }};

        messenger = new UnitType("messenger"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            drag = 0.015f;
            speed = 4f;
            rotateSpeed = 3.5f;
            accel = 0.025f;
            itemCapacity = 0;
            health = 120f;
            hitSize = 12f;
            fallSpeed = 0.006f;
            outlineColor = ArikothUnitPal.unitOutline;
            range = 120;
            abilities.add(new MoveEffectAbility(0, -12, ArikothUnitPal.scoutBlue, Fx.missileTrail, 4f));

            weapons.add(new Weapon(name+"-laser"){{
                shootSound = Sounds.blaster;
                x = 14 / 4;
                y = 24 / 4;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                reload = 20f;
                shootCone = 20f;

                bullet = new PosLightningType(20){{
                    maxRange = rangeOverride = 120;
                    boltNum = 1;
                    lightningDamage = 5;
                    lightningColor = ArikothUnitPal.scoutBlue;
                    lightning = 2;
                    lightningLengthRand = 3;
                    lightningLength = 3;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 6; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 21f));
                            Fill.poly(e.x + v.x, e.y + v.y, 3, e.fout() * 4f + 0.2f, rand.random(360f));
                        };
                    });
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 0;
                y = 0;
                top = true;
                layerOffset = 0.05f;
                mirror = false;
                alwaysShooting = true;
                controllable = false;
                reload = 60f;
                recoil = 0;
                shootCone = 20f;
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-messenger-rotor";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-messenger-rotor";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                    rotation = 45;
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-messenger-rotor-shade";
                    layerOffset = 0.001f;
                    color = Color.valueOf("#ffffff80");
                    outline = false;
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-messenger-rotor-glow";
                    layerOffset = 0.001f;
                    outline = false;
                }});
                parts.add(new RegionPart(){{
                    name = "arikoth-messenger-rotor-top";
                    layerOffset = 0.002f;
                    outline = false;
                }});

                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
        }};

        nomad = new UnitType("nomad"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            drag = 0.02f;
            speed = 3.8f;
            rotateSpeed = 3.2f;
            accel = 0.025f;
            engineSize = 0;
            itemCapacity = 0;
            health = 1200f;
            hitSize = 17f;
            fallSpeed = 0.006f;
            range = 100;
            faceTarget = true;
            outlineColor = ArikothUnitPal.unitOutline;
            abilities.add(new MoveEffectAbility(0, -12, ArikothUnitPal.scoutBlue, Fx.missileTrail, 4f));

            weapons.add(new Weapon(name+"-weapon"){{
                shootSound = ArikothSounds.weakGunTwo;
                x = 22 / 4;
                y = 38 / 4;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                reload = 10f;
                shootCone = 20f;

                bullet = new BasicBulletType(5, 90){{
                    lifetime = 20;
                    recoil = 0.25f;
                    trailRotation = true;
                    trailEffect = vapor;
                    width = 12;
                    height = 12;
                    trailWidth = 2.5f;
                    trailLength = 12;
                    pierceCap = 4;
                    pierceArmor = true;
                    trailColor = backColor = hitColor = ArikothUnitPal.scoutBlue;
                    frontColor = Color.white;

                    shootEffect = new MultiEffect(
                    colorSparkBig,
                    new Effect(25, e -> {
                        color(Color.white, ArikothUnitPal.scoutBlue, e.fin());
                        float w = 2f + 3 * e.fout();
                        Drawf.tri(e.x, e.y, w, 36f * e.fout(), e.rotation);
                        Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + 45f);
                        Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + -45f);
                        Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + -72f);
                        Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + 72f);
                    })
                    );
                    smokeEffect = shootSmokeSquareSparse;
                    hitColor = ArikothUnitPal.scoutBlueDark;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 6; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 16));
                            Fill.poly(e.x + v.x, e.y + v.y, 3, e.fout() * 4f + 0.2f, rand.random(360f));
                        };
                    });
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 0;
                y = 8;
                top = true;
                layerOffset = 0.05f;
                mirror = false;
                alwaysShooting = true;
                controllable = false;
                reload = 30f;
                recoil = 0;
                shootCone = 20f;
                //rotor1
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor1";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor1";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                    rotation = 45;
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor1-shade";
                    layerOffset = 0.001f;
                    color = Color.valueOf("#ffffff90");
                    outline = false;
                }});
                parts.add(new RegionPart(){{
                    name = "arikoth-nomad-rotor1-top";
                    layerOffset = 0.002f;
                    outline = false;
                }});
                //rotor2
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor2";
                    y = -20;
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor2";
                    y = -20;
                    rotation = 45;
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor2-shade";
                    y = -20;
                    layerOffset = 0.01f;
                    outline = false;
                    color = Color.valueOf("#ffffff80");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-nomad-rotor2-glow";
                    y = -20;
                    layerOffset = 0.02f;
                    outline = false;
                    color = Color.valueOf("#ffffff90");
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 0;
                y = 8;
                top = true;
                layerOffset = 0.05f;
                mirror = true;
                alternate = false;
                alwaysShooting = true;
                controllable = false;
                reload = 180f;
                recoil = 0;
                shootCone = 20f;
                //rotor-shine
                parts.add(new RegionPart(){{
                    color = Color.valueOf("dfd3ce70");
                    progress = PartProgress.recoil;
                    moveRot = -360;
                    name = "arikoth-nomad-rotor1-glow";
                    x = 0;
                    y = 0;
                    mirror = false;
                    layerOffset = 0.001f;
                    outline = false;
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
        }};

        outrider = new UnitType("outrider"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            drag = 0.02f;
            speed = 2.25f;
            rotateSpeed = 2.25f;
            accel = 0.025f;
            engineSize = 0;
            itemCapacity = 0;
            health = 2130f;
            hitSize = 20f;
            fallSpeed = 0.006f;
            range = 270;
            wobble = true;
            loopSound = ArikothSounds.rotorWhir;
            flyingLayer = Layer.flyingUnitLow + 0.1f;

            outlineColor = ArikothUnitPal.unitOutline;
            abilities.add(new MoveEffectAbility(81 / 4f, 10 / 4f, Color.darkGray.a(0.5f), new ParticleEffect(){{
                followParent = true;
                length = 16;
                baseLength = 2;
                layer = Layer.debris;
                particles = 3;
                lifetime = 60;
                interp = Interp.pow3Out;
                sizeInterp = Interp.slope;
                sizeFrom = 0;
                sizeTo = 3;
                colorFrom = Color.valueOf("787878");
                colorTo = Color.valueOf("78787888");
            }}, 4f));
            abilities.add(new MoveEffectAbility(-81 / 4f, 10 / 4f, Color.darkGray.a(0.5f), new ParticleEffect(){{
                followParent = true;
                length = 16;
                baseLength = 2;
                layer = Layer.debris;
                particles = 3;
                lifetime = 60;
                interp = Interp.pow3Out;
                sizeInterp = Interp.slope;
                sizeFrom = 0;
                sizeTo = 3;
                colorFrom = Color.valueOf("787878");
                colorTo = Color.valueOf("78787888");
            }}, 4f));
            weapons.add(new Weapon("arikoth-nomad-weapon"){{
                shootSound = Sounds.shootAlt;
                x = 12;
                y = 8;
                shoot = new ShootSpread(3, 2){{
                    shotDelay = 3;
                }};
                inaccuracy = 2;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                reload = 20f;
                shootCone = 20;

                bullet = new BasicBulletType(6, 20){{
                    lifetime = 40;
                    recoil = 0.25f;
                    frontColor = ArikothUnitPal.scoutBlue;
                    backColor = trailColor = hitColor = ArikothUnitPal.scoutBlueDark;
                    trailWidth = 2;
                    trailLength = 18;
                    maxRange = 270;
                    homingRange = 270;
                    homingPower = 0.18f;
                    width = 8;
                    height = 18;
                    pierceCap = 2;
                    pierceArmor = true;
                    trailInterval = 4;
                    trailEffect = new Effect(15, e -> {
                        color(Color.white, e.color, e.fin());
                        stroke(0.2f + e.fout() * 1.7f);
                        rand.setSeed(e.id);

                        for(int i = 0; i < 6; i++){
                            float rot = e.rotation + rand.range(15f) + 180f;
                            v.trns(rot, rand.random(e.fin() * 27f));
                            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(4f, 22f) + 1.5f);
                        }
                    });
                    trailRotation = true;
                    shootEffect = new MultiEffect(
                            colorSparkBig,
                            new Effect(25, e -> {
                                color(Color.white, ArikothUnitPal.scoutBlue, e.fin());
                                float w = 2f + 3 * e.fout();
                                Drawf.tri(e.x, e.y, w, 36f * e.fout(), e.rotation);
                                Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + 45f);
                                Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + -45f);
                                Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + -72f);
                                Drawf.tri(e.x, e.y, w - 3, 18f * e.fout(), e.rotation + 72f);
                            })
                    );
                    smokeEffect = shootSmokeSquareSparse;
                    hitColor = ArikothUnitPal.scoutBlueDark;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 6; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 16));
                            Fill.poly(e.x + v.x, e.y + v.y, 3, e.fout() * 4f + 0.2f, rand.random(360f));
                        };
                    });
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 81 / 4f;
                y = 12 / 4f;
                top = true;
                alternate = false;
                layerOffset = 0.05f;
                mirror = true;
                alwaysShooting = true;
                controllable = false;
                reload = 15f;
                recoil = 0;
                shootCone = 20f;
                //rotor1
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-outrider-rotor";
                    x = 0;
                    y = 0;
                    mirror = false;
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-outrider-rotor";
                    x = 0;
                    y = 0;
                    mirror = false;
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                    rotation = 45;
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-outrider-rotor-shade";
                    x = 0;
                    y = 0;
                    mirror = false;
                    layerOffset = 0.001f;
                    color = Color.valueOf("#ffffff90");
                    outline = false;
                }});
                parts.add(new RegionPart(){{
                    name = "arikoth-outrider-rotor-top";
                    x = 0;
                    y = 0;
                    mirror = false;
                    layerOffset = 0.002f;
                    outline = false;
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});

        weapons.add(new Weapon("placeholder"){{
            shootSound = Sounds.none;
            x = 81 / 4f;
            y = 12 / 4f;
            top = true;
            layerOffset = 0.05f;
            mirror = true;
            alternate = false;
            alwaysShooting = true;
            controllable = false;
            reload = 180f;
            recoil = 0;
            shootCone = 20f;
            //rotor-shine
            parts.add(new RegionPart(){{
                color = Color.valueOf("dfd3ce70");
                progress = PartProgress.recoil;
                moveRot = -360;
                name = "arikoth-outrider-rotor-glow";
                x = 0;
                y = 0;
                mirror = false;
                layerOffset = 0.001f;
                outline = false;
            }});
            parts.add(new RegionPart(){{
                color = Color.valueOf("dfd3ce70");
                progress = PartProgress.recoil;
                moveRot = 360;
                name = "arikoth-outrider-rotor-glow";
                x = 0;
                y = 0;
                mirror = false;
                layerOffset = 0.001f;
                outline = false;
            }});
            bullet = new BasicBulletType(){{
                instantDisappear = true;
                despawnEffect = hitEffect = shootEffect = smokeEffect = none;
            }};
        }});
    }};

        draft = new TankUnitType("draft"){{
            hitSize = 12f;
            constructor = TankUnit::create;
            treadPullOffset = 3;
            speed = 3f;
            rotateSpeed = 4.5f;
            health = 340;
            armor = 4f;
            outlineColor = ArikothUnitPal.unitOutline;
            itemCapacity = 0;
            treadFrames = 6 * 6;
            treadRects = new Rect[] {
                    new Rect(12f, -27f, 16, 48)
            };
            researchCostMultiplier = 0f;

            weapons.add(new Weapon(name + "-weapon"){{
                layerOffset = 0.0001f;
                reload = 5f;
                shootY = 4.5f;
                recoil = 1f;
                rotate = true;
                rotateSpeed = 3.2f;
                mirror = false;
                shootSound = Sounds.bolt;
                soundPitchMax = 3;
                soundPitchMin = 2.8f;
                shoot = new ShootAlternate(2.5f);
                x = 0f;
                y = -0.75f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                cooldownTime = 30f;

                bullet = new LightningBulletType(){{
                   lightningLength = 4;
                   lightningLengthRand = 8;
                   lightningColor = ArikothUnitPal.scoutBlue;
                   damage = 15;
                   pierceBuilding = true;
                   pierceCap = 4;
                }};
            }});
        }};

        squall = new TankUnitType("squall"){{
            hitSize = 12f;
            constructor = TankUnit::create;
            treadPullOffset = 3;
            speed = 2.5f;
            rotateSpeed = 3.5f;
            health = 1220;
            armor = 6f;
            outlineColor = ArikothUnitPal.unitOutline;
            itemCapacity = 0;
            treadFrames = 6 * 6;
            treadRects = new Rect[] {
                    new Rect(18f, -50f, 16, 96)
            };
            researchCostMultiplier = 0f;

            weapons.add(new Weapon(name + "-weapon"){{
                layerOffset = 0.0001f;
                reload = 80f;
                shootY = 4f;
                recoil = 1.5f;
                rotate = true;
                rotateSpeed = 3.2f;
                mirror = false;
                shootSound = Sounds.shootBig;
                x = 0f;
                y = -1f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                cooldownTime = 30f;

                bullet = new BasicBulletType(8, 60, "shell"){{
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = ArikothUnitPal.scoutBlue;
                    trailWidth = 3;
                    trailLength = 12;
                    width = 14;
                    height = 18;
                    trailRotation = true;
                    trailEffect = shootSmokeTitan;
                    pierceCap = 4;
                    trailInterval = 4;
                    pierceBuilding = true;
                    hitEffect = colorSparkBig;
                    despawnEffect = circleColorSpark;
                }};
            }});
        }};

        react = new UnitType("react"){{
                constructor = LegsUnit::create;
                outlineColor = ArikothUnitPal.unitOutline;
                speed = 0.8f;
                drag = 0.11f;
                hitSize = 9f;
                rotateSpeed = 3f;
                health = 450;
                armor = 4f;
                legStraightness = 0f;
                stepShake = 0f;

                legCount = 8;
                legLength = 10f;
                lockLegBase = true;
                legContinuousMove = true;
                legExtension = -2f;
                legBaseOffset = 3.5f;
                legMaxLength = 1.2f;
                legMinLength = 0.2f;
                legLengthScl = 0.96f;
                legForwardScl = 1.2f;
                legGroupSize = 2;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;
                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = false;
                researchCostMultiplier = 0f;
                weapons.add(new Weapon(name + "-weapon") {{
                    shootSound = Sounds.sap;
                    mirror = true;
                    x = 6f;
                    y = -3f;
                    shootY = 4f;
                    reload = 10f;
                    heatColor = ArikothUnitPal.arikothUnitHeat;
                    inaccuracy = 10;
                    layerOffset = 0.001f;

                    bullet = new SapBulletType() {{
                    damage = 10;
                    sprite = "arikoth-sap-laser";
                    length = 40;
                    lengthRand = 10;
                    hitEffect = hitBulletColor;
                    despawnEffect = none;
                }};
            }});
        }};

        decay = new UnitType("decay"){{
            constructor = LegsUnit::create;
            outlineColor = ArikothUnitPal.unitOutline;
            speed = 0.7f;
            drag = 0.11f;
            hitSize = 12f;
            rotateSpeed = 3f;
            health = 1230;
            armor = 6f;
            legStraightness = 0f;
            stepShake = 0f;

            legCount = 4;
            legLength = 16f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -2f;
            legBaseOffset = 4f;
            legMaxLength = 1.2f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.2f;
            legGroupSize = 2;
            rippleScale = 0.2f;

            legMoveSpace = 1f;
            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;
            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;
            weapons.add(new Weapon(name + "-weapon") {{
                shootSound = Sounds.blaster;
                mirror = true;
                x = 6f;
                y = -3f;
                shootY = 4f;
                reload = 30f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = 0.001f;
                shoot = new ShootSpread(3, 8);

                bullet = new BasicBulletType(6, 30, "arikoth-rocket"){{
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = ArikothUnitPal.specialistPurple;
                    weaveMag = 3;
                    weaveScale = 2;
                    trailWidth = 2.5f;
                    trailLength = 12;
                    width = 12;
                    height = 16;
                    homingPower = 0.08f;
                    homingRange = 40;
                }};
            }});
        }};

        fissile = new UnitType("fissile"){{
            constructor = LegsUnit::create;
            outlineColor = ArikothUnitPal.unitOutline;
            speed = 0.5f;
            drag = 0.11f;
            hitSize = 18f;
            rotateSpeed = 2f;
            health = 4430;
            armor = 6f;
            legStraightness = 0f;
            stepShake = 0f;

            legCount = 6;
            legLength = 34f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -2.5f;
            legBaseOffset = 6f;
            legMaxLength = 1.2f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.2f;
            legGroupSize = 2;
            rippleScale = 0.2f;
            parts.add(
            new RegionPart("-weapon-front"){
                    {
                    moveRot = -10f;
                    moveX = -1f;
                    moveY = 2;
                    moves.add(new PartMove(PartProgress.reload, 2f, 1f, -25f));
                    progress = PartProgress.warmup;
                    mirror = true;
            }});

            legMoveSpace = 1f;
            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;
            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;
            weapons.add(new Weapon(name + "-weapon") {{
                shootSound = Sounds.shockBlast;
                mirror = true;
                x = 0f;
                y = 0f;
                shootY = 4;
                shootX = 8;
                alternate = false;
                reload = 30f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = 0.001f;
                shoot = new ShootHelix(6, 3);

                bullet = new BasicBulletType(6, 50, "large-orb"){{
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = ArikothUnitPal.specialistPurple;
                    trailWidth = 2.5f;
                    trailLength = 12;
                    width = 16;
                    height = 16;
                    shrinkY = 0;
                    lifetime = 25;
                    intervalBullets = 2;
                    bulletInterval = 8;
                    despawnEffect = hitEffect = new MultiEffect(
                            circleColorSpark,
                            new ParticleEffect(){{
                                line = true;
                                particles = 12;
                                lifetime = 35;
                                lenFrom = 7;
                                lenTo = 0;
                                strokeFrom = 6;
                                strokeTo = 0;
                                length = 30;
                                interp = Interp.circleOut;
                                colorFrom = Color.white;
                                colorTo = ArikothUnitPal.specialistPurple;
                            }},
                            new WaveEffect(){{
                                lifetime = 35;
                                sizeFrom = 30;
                                sizeTo = 18;
                                strokeFrom = 4;
                                strokeTo = 0;
                                interp = Interp.circleOut;
                                colorFrom = Color.white;
                                colorTo = ArikothUnitPal.specialistPurple;
                            }},
                            new WaveEffect(){{
                                lifetime = 35;
                                sizeFrom = 16;
                                sizeTo = 0;
                                strokeFrom = 4;
                                strokeTo = 0;
                                interp = Interp.circleOut;
                                colorFrom = Color.white;
                                colorTo = ArikothUnitPal.specialistPurple;
                            }}
                    );
                    intervalBullet = new LightningBulletType(){{
                        damage = 20;
                        collidesAir = false;
                        ammoMultiplier = 1f;
                        lightningColor = ArikothUnitPal.specialistPurpleDark;
                        lightningLength = 3;
                        lightningLengthRand = 6;

                        //for visual stats only.
                        buildingDamageMultiplier = 0.25f;

                        lightningType = new BulletType(0.0001f, 0f){{
                            lifetime = Fx.lightning.lifetime;
                            hitEffect = Fx.hitLancer;
                            despawnEffect = Fx.none;
                            status = StatusEffects.shocked;
                            statusDuration = 10f;
                            hittable = false;
                            lightColor = Color.white;
                            buildingDamageMultiplier = 0.25f;
                        }};
                    }};
                }};
            }});
        }};

        iracund = new UnitType("iracund"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            drag = 0.02f;
            speed = 0.8f;
            rotateSpeed = 2.2f;
            accel = 0.025f;
            engineSize = 4;
            engineOffset = 10;
            trailLength = 24;
            engineColor = trailColor = ArikothUnitPal.cryo;
            itemCapacity = 0;
            health = 2200f;
            hitSize = 17f;
            fallSpeed = 0.006f;
            faceTarget = false;
            outlineColor = ArikothUnitPal.unitOutline;

            weapons.add(new Weapon(name+"-weapon"){{
                shootSound = Sounds.bang;
                x = 0;
                y = 4;
                top = true;
                layerOffset = 0.001f;
                mirror = false;
                reload = 90f;
                shootCone = 20f;
                recoil = 1.5f;
                shake = 6;
                rotate = true;
                rotateSpeed = 1.5f;
                shootY = 12;
                parts.add(new RegionPart("-cannon"){{
                    moveY = -2.5f;
                    mirror = false;
                    under = true;
                    progress = PartProgress.recoil;
                }});

                bullet = new BasicBulletType(6, 90){{
                    lifetime = 30;
                    recoil = 0.45f;
                    splashDamage = 90;
                    splashDamageRadius = 16;
                    trailRotation = true;
                    trailInterval = 2;
                    trailEffect = disperseTrail;
                    despawnSound = hitSound = Sounds.dullExplosion;
                    despawnShake = hitShake = 6;
                    width = 16;
                    height = 18;
                    trailWidth = 3f;
                    trailLength = 18;
                    pierceCap = 4;
                    pierceArmor = true;
                    trailColor = backColor = hitColor = ArikothUnitPal.cryo;
                    frontColor = Color.white;

                    shootEffect = new MultiEffect(
                            colorSparkBig,
                            new Effect(25, e -> {
                                color(Color.white, ArikothUnitPal.cryo, e.fin());
                                float w = 2f + 3 * e.fout();
                                Drawf.tri(e.x, e.y, w + 1, 18f * e.fout(), e.rotation + -45f);
                                Drawf.tri(e.x, e.y, w + 1, 18f * e.fout(), e.rotation + 45f);
                            })
                    );
                    smokeEffect = shootSmokeSquareSparse;
                    hitColor = ArikothUnitPal.scoutBlueDark;
                    hitEffect = randLifeSpark;
                    despawnEffect = new MultiEffect(
                    new ParticleEffect(){{
                        sizeFrom = 8;
                        sizeTo = 0;
                        colorFrom = ArikothUnitPal.cryo;
                        colorTo = ArikothUnitPal.cryoDark.a(0.5f);
                        length = 25;
                        particles = 8;
                        lifetime = 60;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 9;
                        colorFrom = ArikothUnitPal.cryo;
                        colorTo = ArikothUnitPal.cryoDark.a(0.5f);
                        length = 0;
                        particles = 1;
                        lifetime  = 40;
                        sizeInterp = Interp.slope;
                    }},
                    new WaveEffect(){{
                        sizeFrom = 0;
                        sizeTo = 30;
                        colorFrom = ArikothUnitPal.cryo;
                        colorTo = ArikothUnitPal.cryoDark;
                        sides = 4;
                        rotation = 45;
                        strokeFrom = 4;
                        strokeTo = 0;
                        lifetime  = 40;
                        interp = Interp.circleOut;
                    }}
                    );
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 6;
                y = 0;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                alwaysShooting = true;
                controllable = false;
                reload = 30f;
                recoil = 0;
                shootCone = 20f;
                //rotor1
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
        }};

        aggravate = new UnitType("aggravate"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            drag = 0.02f;
            speed = 0.7f;
            rotateSpeed = 2.2f;
            accel = 0.025f;
            engineSize = 5;
            engineOffset = 18;
            trailLength = 28;
            engineColor = trailColor = ArikothUnitPal.cryo;
            itemCapacity = 0;
            health = 4230f;
            hitSize = 24f;
            fallSpeed = 0.006f;
            faceTarget = false;
            outlineColor = ArikothUnitPal.unitOutline;

            weapons.add(new Weapon(name+"-weapon"){{
                shootSound = Sounds.cannon;
                x = 0;
                y = 8;
                top = true;
                layerOffset = 0.002f;
                mirror = false;
                reload = 180f;
                shootCone = 20f;
                recoil = 2f;
                shake = 8;
                rotate = true;
                rotateSpeed = 1;
                shootY = 18;
                parts.add(new RegionPart("-cannon"){{
                    moveY = -3.5f;
                    mirror = false;
                    under = true;
                    progress = PartProgress.recoil;
                }});

                bullet = new BasicBulletType(12, 120){{
                    lifetime = 20;
                    recoil = 0.45f;
                    splashDamage = 120;
                    splashDamageRadius = 32;
                    trailRotation = false;
                    trailInterval = 4;
                    trailEffect = new Effect(36, e -> {
                        color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                        randLenVectors(e.id, 6, 8f + e.fin() * 8f, (x, y) -> {
                            Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.2f, 45);
                        });
                    });
                    despawnSound = hitSound = Sounds.dullExplosion;
                    despawnShake = hitShake = 6;
                    width = 16;
                    height = 18;
                    trailWidth = 3f;
                    trailLength = 18;
                    trailColor = backColor = hitColor = ArikothUnitPal.cryo;
                    frontColor = Color.white;

                    shootEffect = new MultiEffect(
                            colorSparkBig,
                            new Effect(25, e -> {
                                color(Color.white, ArikothUnitPal.cryo, e.fin());
                                float w = 2f + 3 * e.fout();
                                Drawf.tri(e.x, e.y, w + 1, 18f * e.fout(), e.rotation + -45f);
                                Drawf.tri(e.x, e.y, w + 1, 18f * e.fout(), e.rotation + 45f);
                            })
                    );
                    smokeEffect = shootSmokeSquareSparse;
                    hitColor = ArikothUnitPal.scoutBlueDark;
                    despawnHit = true;
                    hitEffect = new MultiEffect(
                        new ParticleEffect(){{
                            lifetime = 35;
                            lenFrom = 7;
                            lenTo = 0;
                            strokeFrom = 4;
                            strokeTo = 0;
                            colorTo = ArikothUnitPal.cryo;
                            baseRotation = 180;
                            length = 120;
                            cone = 40;
                            interp = Interp.circleOut;
                        }},
                        new ParticleEffect(){{
                          lifetime = 35;
                          sizeFrom = 4;
                          sizeTo = 0;
                          colorTo = ArikothUnitPal.cryo;
                          baseRotation = 180;
                          length = 120;
                          cone = 40;
                          interp = Interp.circleOut;
                        }}
                    );
                    despawnEffect = new MultiEffect(
                            new Effect(36, e -> {
                                color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                                randLenVectors(e.id, 18, 25f + e.fin() * 8f, (x, y) -> {
                                    Fill.square(e.x + x, e.y + y, e.fout() * 6f + 0.2f, 45);
                                });
                            }),
                            new ParticleEffect(){{
                                sizeFrom = 8;
                                sizeTo = 0;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark.a(0.5f);
                                length = 25;
                                particles = 8;
                                lifetime = 60;
                                interp = Interp.pow10Out;
                                sizeInterp = Interp.pow10In;
                            }},
                            new ParticleEffect(){{
                                sizeFrom = 0;
                                sizeTo = 9;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark.a(0.5f);
                                length = 0;
                                particles = 1;
                                lifetime  = 40;
                                sizeInterp = Interp.slope;
                            }},
                            new WaveEffect(){{
                                sizeFrom = 0;
                                sizeTo = 30;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark;
                                sides = 4;
                                rotation = 45;
                                strokeFrom = 4;
                                strokeTo = 0;
                                lifetime  = 40;
                                interp = Interp.circleOut;
                            }},
                            new WaveEffect(){{
                                sizeFrom = 0;
                                sizeTo = 60;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark;
                                sides = 4;
                                rotation = 45;
                                strokeFrom = 4;
                                strokeTo = 0;
                                lifetime  = 40;
                                interp = Interp.circleOut;
                            }}
                    );
                }};
            }});
            weapons.add(new Weapon(name+"-launcher"){{
                shootSound = Sounds.missileSmall;
                x = -32 / 4f;
                y = -48 / 4f;
                top = true;
                layerOffset = 0.001f;
                mirror = true;
                alternate = false;
                reload = 50f;
                shootCone = 20;
                recoil = 0.5f;
                shake = 8;
                shootY = 8;
                rotateSpeed = 2.5f;
                rotate = true;

                bullet = new BasicBulletType(0, 0){{
                    instantDisappear = true;
                    spawnUnit = new MissileUnitType("cryo-rocket"){{
                        trailColor = engineColor = ArikothUnitPal.cryo;
                        engineSize = 1.75f;
                        engineLayer = Layer.effect;
                        speed = 3f;
                        maxRange = 6f;
                        lifetime = 60f;
                        outlineColor = Pal.darkOutline;
                        health = 90;
                        lowAltitude = true;
                        despawnSound = Sounds.dullExplosion;

                        parts.add(new FlarePart(){{
                            progress = PartProgress.life.slope().curve(Interp.pow5Out);
                            radius = 0f;
                            radiusTo = 25f;
                            stroke = 3f;
                            rotation = 45f;
                            color1 = ArikothUnitPal.cryo;
                            y = -5f;
                            followRotation = true;
                        }});

                        weapons.add(new Weapon(){{
                            shootSound = Sounds.none;
                            shootCone = 360f;
                            mirror = false;
                            reload = 1f;
                            shootOnDeath = true;
                            bullet = new ExplosionBulletType(20f, 25f){{
                                shootEffect = new MultiEffect(
                                new WrapEffect(ArikothFx.dynamicSpikesModif1, ArikothUnitPal.cryo, 24f),
                                new WaveEffect(){{
                                    colorFrom = colorTo = ArikothUnitPal.cryo;
                                    sizeTo = 40f;
                                    lifetime = 12f;
                                    strokeFrom = 4f;
                                }},
                                new Effect(36, e -> {
                                    color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                                    randLenVectors(e.id, 8, 18f + e.fin() * 8f, (x, y) -> {
                                        Fill.square(e.x + x, e.y + y, e.fout() * 6f + 0.2f, 45);
                                    });
                                })
                                );
                            }};
                        }});
                    }};
                    despawnSound = hitSound = Sounds.none;

                    shootEffect = Fx.none;
                    smokeEffect = shootSmokeTitan;
                    hitColor = ArikothUnitPal.scoutBlueDark;
                    hitEffect = despawnEffect = Fx.none;
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 42 / 4f;
                y = 0;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                alwaysShooting = true;
                controllable = false;
                reload = 30f;
                recoil = 0;
                shootCone = 20f;
                //rotor1
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
        }};

        cantankerous = new UnitType("cantankerous"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            outlineRadius = 4;
            flying = true;
            drag = 0.02f;
            speed = 0.5f;
            rotateSpeed = 1.2f;
            accel = 0.025f;
            engineSize = 5;
            engineOffset = 24;
            trailLength = 38;
            engineColor = trailColor = ArikothUnitPal.cryo;
            itemCapacity = 0;
            health = 12300f;
            hitSize = 32f;
            fallSpeed = 0.006f;
            faceTarget = false;
            outlineColor = ArikothUnitPal.unitOutline;

            weapons.add(new Weapon(name+"-turret"){{
                shootSound = Sounds.mediumCannon;
                x = 0;
                y = -68 / 4f;
                top = true;
                layerOffset = 0.003f;
                mirror = false;
                reload = 190f;
                shootCone = 20f;
                recoil = 3f;
                shake = 8;
                rotate = true;
                rotateSpeed = 1;
                shootY = 18;
                parts.add(new RegionPart("-front"){{
                    moveY = -3.5f;
                    mirror = false;
                    under = true;
                    progress = PartProgress.recoil;
                }});

                bullet = new ArtilleryBulletType(2, 370){{
                    lifetime = 140;
                    buildingDamageMultiplier = 0.75f;
                    splashDamage = 370;
                    splashDamageRadius = 64;
                    trailRotation = false;
                    trailInterval = 4;
                    trailEffect = new Effect(36, e -> {
                        color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                        randLenVectors(e.id, 6, 8f + e.fin() * 8f, (x, y) -> {
                            Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.2f, 45);
                        });
                    });
                    despawnSound = hitSound = Sounds.titanExplosion;
                    despawnShake = hitShake = 6;
                    width = 18;
                    height = 24;
                    trailWidth = 3.5f;
                    trailLength = 18;
                    trailColor = backColor = hitColor = ArikothUnitPal.cryo;
                    frontColor = Color.white;

                    shootEffect = new MultiEffect(
                            colorSparkBig,
                            new Effect(25, e -> {
                                color(Color.white, ArikothUnitPal.cryo, e.fin());
                                float w = 2f + 3 * e.fout();
                                Drawf.tri(e.x, e.y, w + 1, 18f * e.fout(), e.rotation + -45f);
                                Drawf.tri(e.x, e.y, w + 1, 18f * e.fout(), e.rotation + 45f);
                            })
                    );
                    smokeEffect = shootSmokeTitan;
                    hitColor = ArikothUnitPal.scoutBlueDark;
                    despawnHit = true;
                    despawnEffect = new MultiEffect(
                            new Effect(36, e -> {
                                color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                                randLenVectors(e.id, 18, 25f + e.fin() * 8f, (x, y) -> {
                                    Fill.square(e.x + x, e.y + y, e.fout() * 6f + 0.2f, 45);
                                });
                            }),
                            titanSmokeSmall,
                            titanExplosionSmall,
                            new ParticleEffect(){{
                                sizeFrom = 8;
                                sizeTo = 0;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark.a(0.5f);
                                length = 25;
                                particles = 8;
                                lifetime = 60;
                                interp = Interp.pow10Out;
                                sizeInterp = Interp.pow10In;
                            }},
                            new ParticleEffect(){{
                                sizeFrom = 0;
                                sizeTo = 9;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark.a(0.5f);
                                length = 0;
                                particles = 1;
                                lifetime  = 40;
                                sizeInterp = Interp.slope;
                            }},
                            new WaveEffect(){{
                                sizeFrom = 0;
                                sizeTo = 30;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark;
                                sides = 4;
                                rotation = 45;
                                strokeFrom = 4;
                                strokeTo = 0;
                                lifetime  = 40;
                                interp = Interp.circleOut;
                            }},
                            new WaveEffect(){{
                                sizeFrom = 0;
                                sizeTo = 60;
                                colorFrom = ArikothUnitPal.cryo;
                                colorTo = ArikothUnitPal.cryoDark;
                                sides = 4;
                                rotation = 45;
                                strokeFrom = 4;
                                strokeTo = 0;
                                lifetime  = 40;
                                interp = Interp.circleOut;
                            }}
                    );
                }};
            }});
            weapons.add(new Weapon(name+"-gunner1"){{
                shootSound = Sounds.shootAlt;
                x = -38 / 4f;
                y = -48 / 4f;
                top = true;
                layerOffset = 0.001f;
                mirror = true;
                alternate = true;
                reload = 60f;
                shootCone = 20;
                recoil = 0.5f;
                shake = 0;
                shootY = 8;
                rotateSpeed = 2.5f;
                shoot.shots = 4;
                shoot.shotDelay = 3;
                rotate = true;

                bullet = new BasicBulletType(6, 30){{
                    despawnSound = hitSound = Sounds.none;

                    shootEffect = colorSparkBig;
                    smokeEffect = Fx.none;
                    height = 16; width = 12; trailWidth = 2.5f; trailLength = 12;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                        randLenVectors(e.id, 4, 8f + e.fin() * 2f, (x, y) -> {
                            Fill.square(e.x + x, e.y + y, e.fout() * 1f + 0.2f, 45);
                        });
                    });
                    trailColor = backColor = hitColor = ArikothUnitPal.cryo;
                    frontColor = Color.white;
                    lifetime = 40;
                }};
            }});
            weapons.add(new Weapon(name+"-gunner2"){{
                shootSound = Sounds.blaster;
                x = 42 / 4f;
                y = 0;
                top = true;
                layerOffset = 0.001f;
                mirror = true;
                alternate = true;
                reload = 10f;
                shootCone = 20;
                recoil = 0.5f;
                shake = 0;
                shootY = 8;
                rotateSpeed = 2.5f;
                rotate = true;

                bullet = new BasicBulletType(6, 20){{
                    despawnSound = hitSound = Sounds.none;

                    shootEffect = colorSparkBig;
                    smokeEffect = Fx.none;
                    height = 16; width = 12; trailWidth = 2.5f; trailLength = 12;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(ArikothUnitPal.cryo, ArikothUnitPal.cryoDark, e.fin());
                        randLenVectors(e.id, 4, 8f + e.fin() * 2f, (x, y) -> {
                            Fill.square(e.x + x, e.y + y, e.fout() * 1f + 0.2f, 45);
                        });
                    });
                    trailColor = backColor = hitColor = ArikothUnitPal.cryo;
                    frontColor = Color.white;
                    lifetime = 40;
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 45 / 4f;
                y = 0;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                alwaysShooting = true;
                controllable = false;
                reload = 30f;
                recoil = 0;
                shootCone = 20f;
                //rotor1
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
            weapons.add(new Weapon("placeholder"){{
                shootSound = Sounds.none;
                x = 44 / 4f;
                y = -62 / 4;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                alwaysShooting = true;
                controllable = false;
                reload = 30f;
                recoil = 0;
                shootCone = 20f;
                //rotor1
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff30");
                }});
                parts.add(new RegionPart(){{
                    moveRot = 360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                parts.add(new RegionPart(){{
                    moveRot = -360;
                    progress = PartProgress.recoil;
                    name = "arikoth-iracund-rotor-shade";
                    layerOffset = 0;
                    outline = false;
                    color = Color.valueOf("#ffffff60");
                }});
                bullet = new BasicBulletType(){{
                    instantDisappear = true;
                    despawnEffect = hitEffect = shootEffect = smokeEffect = none;
                }};
            }});
        }};

        prototype = new UnitType("prototype"){{
            outlineRadius = 6;
            constructor = LegsUnit::create;
            outlineColor = ArikothUnitPal.unitOutline;
            speed = 0.15f;
            drag = 0.11f;
            hitSize = 100f;
            rotateSpeed = 0.2f;
            health = 660900;
            armor = 60f;
            legStraightness = 6f;
            stepShake = 4f;

            legCount = 6;
            legLength = 60f;
            lockLegBase = true;
            legContinuousMove = true;
            legExtension = -4.5f;
            legBaseOffset = 45f;
            legMaxLength = 1.2f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.2f;
            legGroupSize = 6;
            legSpeed = 0.2f;
            legPairOffset = 3;
            rippleScale = 4.5f;
            legSplashDamage = 220;
            legSplashRange = 64;
            abilities.add(new SpawnDeathAbility(){{
                amount = 1;
                spread = 0;
                unit = new MissileUnitType("deamth"){{
                   lifetime = 0;
                   weapons.add(new Weapon("ded"){{
                       reload = 0;
                       shootOnDeath = true;
                       controllable = false;
                       shootCone = 360;
                       bullet = new AftershockBulletType(100, 256){{
                           splashAmount = 5;
                           splashDelay = 120;
                           shootEffect = new MultiEffect(
                                   new ParticleEffect(){{
                                       sizeFrom = 16;
                                       sizeTo = 0;
                                       interp = Interp.pow10Out;
                                       sizeInterp = Interp.exp10In;
                                       length = 130;
                                       particles = 12;
                                       colorFrom = ArikothTurretPal.strontiumLight;
                                       colorTo = ArikothTurretPal.strontiumTrail.a(60);
                                       lifetime = 220;
                                   }},
                                   new WaveEffect(){{
                                       sizeFrom = 0;
                                       sizeTo = 200;
                                       strokeFrom = 12;
                                       strokeTo = 0;
                                       lifetime = 120;
                                       startDelay = 20;
                                       interp = Interp.linear;
                                       colorFrom = ArikothTurretPal.strontiumLight;
                                       colorTo = ArikothTurretPal.strontiumTrail.a(60);
                                   }},
                                   new WaveEffect(){{
                                       sizeFrom = 0;
                                       sizeTo = 300;
                                       strokeFrom = 12;
                                       strokeTo = 0;
                                       lifetime = 120;
                                       startDelay = 20;
                                       interp = Interp.linear;
                                       colorFrom = ArikothTurretPal.strontiumLight;
                                       colorTo = ArikothTurretPal.strontiumTrail.a(60);
                                   }},
                                   new WaveEffect(){{
                                       sizeFrom = 0;
                                       sizeTo = 400;
                                       strokeFrom = 12;
                                       strokeTo = 0;
                                       lifetime = 120;
                                       startDelay = 20;
                                       interp = Interp.linear;
                                       colorFrom = ArikothTurretPal.strontiumLight;
                                       colorTo = ArikothTurretPal.strontiumTrail.a(60);
                                   }}
                           );
                           applySound = explosionbig;
                           frontColor = Color.white;
                           backColor = hitColor = ArikothTurretPal.strontiumLight;
                           particleColor = backColor;
                           particleEffect = circleColorSpark;
                       }};
                    }});
                }};
            }});

            legMoveSpace = 1f;
            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;
            shadowElevation = 0.8f;
            groundLayer = Layer.legUnit + 6f;
            targetAir = false;
            researchCostMultiplier = 0f;
            weapons.add(new Weapon(name + "-coil") {{
                shootSound = Sounds.shockBlast;
                mirror = true;
                x = 178f / 4;
                y = 136f / 4;
                shootY = 12;
                shootX = 0;
                shake = 8;
                alternate = true;
                reload = 30f;
                recoil = 6;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = -0.001f;

                bullet = new PosLightningType(90){{
                    maxRange = rangeOverride = 300;
                    splashDamage = 90;
                    splashDamageRadius = 32;
                    boltNum = 3;
                    lightningColor = hitColor = ArikothTurretPal.strontiumLight;
                    hitEffect = despawnEffect = new MultiEffect(
                    new ParticleEffect(){{
                        sizeFrom = 0;
                        sizeTo = 4;
                        interp = Interp.slope;
                        length = 30;
                        lifetime = 20;
                        particles = 12;
                        colorTo = ArikothTurretPal.strontiumLight;
                    }},
                    new Effect(35, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 12; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 50));
                            Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 4f + 0.2f, rand.random(360f));
                        };
                    }),
                    new WaveEffect(){{
                        colorTo = ArikothTurretPal.strontiumLight;
                        sizeTo = 25;
                        sizeFrom = 30;
                        interp = Interp.circleOut;
                        lifetime = 30;
                        strokeFrom = 4;
                        sides = 4;
                    }}
                    );
                }};
            }});

            weapons.add(new Weapon(name + "-ioner") {{
                shootSound = Sounds.laser;
                mirror = true;
                x = 89 / 4;
                y = -128 / 4;
                shootY = 12;
                alternate = true;
                reload = 60f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = 0.001f;
                shake = 3;
                recoil = 6;

                bullet = new LaserBulletType(30){{
                    splashDamage = 90;
                    splashDamageRadius = 32;
                    lightningColor = hitColor = ArikothTurretPal.strontiumLight;
                    hitEffect = disperseTrail;
                    length = 670;
                    width = 25;
                    sideWidth = 1.23f;
                    sideLength = 90;
                    sideAngle = 45;
                    shootEffect = ArikothFx.mediumStarFour;
                    colors = new Color[]{ArikothTurretPal.strontiumTrail.a(0.8f), ArikothTurretPal.strontiumLight, Color.white};
                }};
            }});

            weapons.add(new Weapon(name + "-coil") {{
                shootSound = Sounds.shockBlast;
                mirror = true;
                x = 280 / 4;
                y = 59 / 4;
                shootY = 12;
                alternate = true;
                reload = 30f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = -0.001f;
                shoot.firstShotDelay = 15;
                shake = 8;
                recoil = 6;

                bullet = new PosLightningType(90){{
                    maxRange = rangeOverride = 300;
                    splashDamage = 90;
                    splashDamageRadius = 32;
                    boltNum = 3;
                    lightningColor = hitColor = ArikothTurretPal.strontiumLight;
                    hitEffect = despawnEffect = new MultiEffect(
                            new ParticleEffect(){{
                                sizeFrom = 0;
                                sizeTo = 4;
                                interp = Interp.slope;
                                length = 30;
                                lifetime = 20;
                                particles = 12;
                                colorTo = ArikothTurretPal.strontiumLight;
                            }},
                            new Effect(35, e -> {
                                color(Color.white, e.color, e.fin());

                                rand.setSeed(e.id);
                                for(int i = 0; i < 12; i++){
                                    float rot = e.rotation + rand.range(360f);
                                    v.trns(rot, rand.random(e.finpow() * 50));
                                    Fill.poly(e.x + v.x, e.y + v.y, 4, e.fout() * 4f + 0.2f, rand.random(360f));
                                };
                            }),
                            new WaveEffect(){{
                                colorTo = ArikothTurretPal.strontiumLight;
                                sizeTo = 25;
                                sizeFrom = 30;
                                interp = Interp.circleOut;
                                lifetime = 30;
                                strokeFrom = 4;
                                sides = 4;
                            }}
                    );
                }};
            }});

            weapons.add(new Weapon(name + "-launcher") {{
                shootSound = Sounds.missileLaunch;
                mirror = true;
                x = 226 / 4;
                y = -79 / 4;
                shootY = 12;
                alternate = true;
                reload = 600f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = 0.001f;
                shake = 12;
                recoil = 6;

                bullet = new BasicBulletType(2, 500, "arikoth-rocket"){{
                    splashDamage = 480;
                    splashDamageRadius = 64;
                    hitColor = backColor = trailColor = ArikothTurretPal.strontiumLight;
                    frontColor = Color.white;
                    width = 48;
                    height = 64;
                    hitSize = 18;
                    trailLength = 20;
                    trailWidth = 6;
                    hitShake = despawnShake = 12;
                    lifetime = 200;
                    weaveMag = 4;
                    weaveScale = 8;
                    despawnSound = hitSound = Sounds.largeExplosion;
                    shootEffect = new Effect(130f, 300f, e -> {
                        color(ArikothTurretPal.strontiumLight);
                        alpha(0.5f);
                        rand.setSeed(e.id);
                        for(int i = 0; i < 35; i++){
                            v.trns(e.rotation + 180f + rand.range(32f), rand.random(e.finpow() * 90f)).add(rand.range(3f), rand.range(3f));
                            e.scaled(e.lifetime * rand.random(0.2f, 1.4f), b -> {
                                Fill.circle(e.x + v.x, e.y + v.y, b.fout() * 10f + 0.3f);
                            });
                        }
                    });
                    spawnBullets.add(new BasicBulletType(4, 30){{
                        fragVelocityMin = 1;
                        fragVelocityMax = 1;
                        fragSpread = 30;
                        fragBullets = 4;
                        fragAngle = 45;
                        fragRandomSpread = 0;
                        instantDisappear = true;
                        fragBullet = new BasicBulletType(4, 30, "arikoth-rocket"){{
                        drag = 0.01f;
                        width = 14f;
                        height = 14f;
                        lifetime = 100;
                        weaveRandom = false;
                        hitSize = 5f;
                        hitColor = backColor = trailColor = ArikothTurretPal.strontiumLight;
                        frontColor = Color.white;
                        trailWidth = 3f;
                        trailLength = 18;
                        weaveScale = 3;
                        weaveMag = 3;
                        homingPower = 0.04f;
                        homingDelay = 20;
                        damage = 40;
                        splashDamage = 30f;
                        splashDamageRadius = 16f;
                        despawnEffect = ArikothFx.spikyBoom;
                        }};
                    }});
                    spawnBullets.add(new BasicBulletType(4, 30){{
                        fragVelocityMin = 1;
                        fragVelocityMax = 1;
                        fragSpread = 30;
                        fragBullets = 4;
                        fragAngle = -45;
                        fragRandomSpread = 0;
                        instantDisappear = true;
                        fragBullet = new BasicBulletType(4, 30, "arikoth-rocket"){{
                            drag = 0.01f;
                            width = 14f;
                            height = 14f;
                            lifetime = 100;
                            weaveRandom = false;
                            hitSize = 5f;
                            hitColor = backColor = trailColor = ArikothTurretPal.strontiumLight;
                            frontColor = Color.white;
                            trailWidth = 3f;
                            trailLength = 18;
                            weaveScale = 3;
                            weaveMag = -3;
                            homingPower = 0.04f;
                            homingDelay = 20;
                            damage = 40;
                            splashDamage = 30f;
                            splashDamageRadius = 16f;
                            despawnEffect = ArikothFx.spikyBoom;
                        }};
                    }});
                    fragBullets = 1;
                    fragBullet = new AftershockBulletType(40, 64){{
                        frontColor = ArikothTurretPal.strontiumLight;
                        backColor = ArikothTurretPal.strontiumTrail;
                        applySound = Sounds.boom;
                        splashAmount = 5;
                        splashDelay = 60;
                    }};
                    lightningColor = hitColor = ArikothTurretPal.strontiumLight;
                    hitEffect = despawnEffect = new MultiEffect(
                            ArikothFx.spikyBoomMedium,
                            ArikothFx.spikyBoomLarge,
                            ArikothFx.smallStarFour,
                            new Effect(90f, 160f, e -> {
                                float circleRad = 6f + e.finpow() * 20f;

                                color(ArikothTurretPal.strontiumLight, e.foutpow());
                                Fill.circle(e.x, e.y, circleRad);
                            }).layer(Layer.bullet + 2f),
                            new Effect(90f, 160f, e -> {
                                float circleRad = 6f + e.finpow() * 70f;

                                color(ArikothTurretPal.strontiumLight, e.foutpow());
                                Fill.circle(e.x, e.y, circleRad);
                            }).layer(Layer.bullet + 2f)
                    );
                }};
            }});
        }};

        vision = new UnitType("vision") {{
            aiController = BuilderAI::new;
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 3;
            rotateSpeed = 15;
            accel = 0.1f;
            drag = 0.04f;
            flying = true;
            health = 320;
            hitSize = 8;
            armor = 3;
            itemCapacity = 40;
            outlineColor = ArikothUnitPal.unitOutline;
            engineOffset = 7.5f;
            faceTarget = true;
            mineTier = 1;
            mineSpeed = 5;
            buildSpeed = 1f;
            buildBeamOffset = 5;
            isEnemy = false;
            mineWalls = true;
            mineFloor = false;

            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;
                x = 0f;
                y = 6.5f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                repairSpeed = 3.1f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;
                mirror = false;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                healColor = laserColor = Color.valueOf("#8875ff");

                bullet = new BulletType() {{
                    maxRange = 60f;
                }};
            }});
        }};
    }
}
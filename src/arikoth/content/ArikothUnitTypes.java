package arikoth.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Rect;
import arc.util.Time;
import arc.util.Tmp;
import arikoth.content.effects.ArikothFx;
import arikoth.content.effects.ArikothTurretFx;
import arikoth.palettes.ArikothTurretPal;
import arikoth.palettes.ArikothUnitPal;
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
import mindustry.type.weapons.BuildWeapon;
import mindustry.type.weapons.RepairBeamWeapon;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import static arikoth.content.effects.ArikothFx.rand;
import static mindustry.content.Fx.*;

public class ArikothUnitTypes {

    public static UnitType

            //Core
            pioneer,
            vision, seeker, overseer,
            //Assault
            bolt, hasp, frame, edifice, cathedral,
            //spider-assault
            cavalier, knight, sabreur, phalanx, dragoon,
            //hover cruisers
            kindle, calxel, redox, weld, conflagration,
            //scout copters
            messenger, nomad, outrider, vanguard, oracle,
            //scout tanks
            draft, squall, zephyr, derecho, hurricane,
            //specialist spider thingy
            react, decay, fissile, dominion, nucleon, positron,
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
            health = 620;
            armor = 6;
            weapons.add(new Weapon(
                "arikoth-bolt-weapon"){{
                reload = 20;
                x = -6f;
                y = 0f;
                top = false;
                shootSound = Sounds.shootAltLong;
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
                reload = 35;
                alternate = true;
                x = 8f;
                y = 0f;
                shootY = 10;
                inaccuracy = 4;
                top = false;
                shootSound = Sounds.bang;
                ejectEffect = casing3;
                shoot.shots = 4;
                shoot.shotDelay = 6;
                bullet = new BasicBulletType(6, 40){{
                    speed = 6;
                    damage = 40;
                    splashDamage = 30;
                    splashDamageRadius = 18;
                    width = 8;
                    height = 12;
                    trailWidth = 2;
                    trailLength = 10;
                    trailColor = backColor = hitColor = lightColor = Pal.redLight;
                    frontColor = Color.white;
                    lifetime = 25;
                    hitSound = Sounds.none;
                    shootEffect = shootBigColor;
                    smokeEffect = shootBigSmoke;
                    hitEffect = hitBulletColor;
                    despawnEffect = none;
                    despawnHit = true;
                }};
            }});
        }};

        frame = new UnitType("frame"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = ArikothUnitPal.unitOutline;
            outlineRadius = 4;
            constructor = MechUnit::create;
            speed = 0.6f;
            stepShake = 1.2f;
            hitSize = 28f;
            mechStepParticles = true;
            health = 5230;
            armor = 12;
            weapons.addAll(
                new Weapon(
                    "arikoth-frame-weapon"){{
                reload = 90f;
                x = 67 / 4f;
                y = 0f;
                shake = 6;
                shootY = 12;
                top = false;
                shootSound = Sounds.dullExplosion;
                ejectEffect = Fx.casing4;
                inaccuracy = 3;
                recoil = 2;
                for(int j = 0; j < 4; j++){
                    int i = j;
                    parts.add(new RegionPart("-spine"){{
                        layerOffset = -0.01f;
                        heatLayerOffset = 0.005f;
                        x = -1f;
                        y = -2;
                        moveX = 2.5f + i * 1.9f;
                        moveY = 8f + -4f * i;
                        moveRot = 40f - i * 25f;
                        rotation = -90;
                        progress = PartProgress.warmup.delay(i * 0.2f);
                        heatProgress = p -> Mathf.absin(Time.time + i * 14f, 7f, 1f);

                        heatColor = Pal.techBlue;
                    }});
                }
                bullet = new  BasicBulletType(8f, 70, "shell"){{
                    splashDamage = 60;
                    splashDamageRadius = 32;
                    width = 24;
                    height = 24;
                    drag = 0.01f;
                    trailWidth = 4f;
                    trailLength = 18;
                    trailColor = backColor = hitColor = lightColor = ArikothUnitPal.assaultGold;
                    frontColor = Color.white;
                    lifetime = 20;
                    hitSound = Sounds.bang;
                    intervalBullets = 1;
                    bulletInterval = 3;
                    intervalBullet = new BasicBulletType(4f, 10, "missile-large"){{
                    frontColor = Color.white;
                    trailColor = backColor = hitColor = ArikothUnitPal.assaultGold;
                    trailWidth = 2;
                    trailLength = 12;
                    width = 8;
                    height = 12;
                    lifetime = 20;
                    pierceCap = 6;
                    drag = 0.01f;
                    trailInterval = 1;
                    trailRotation = true;
                    trailEffect = disperseTrail;
                    hitEffect = despawnEffect = new MultiEffect(blastExplosion, hitBulletColor);
                    }};
                    fragBullets = 8;
                    fragBullet = new BasicBulletType(4f, 10, "missile-large"){{
                        frontColor = Color.white;
                        trailColor = backColor = hitColor = ArikothUnitPal.assaultGold;
                        trailWidth = 2;
                        trailLength = 12;
                        width = 8;
                        height = 12;
                        lifetime = 25;
                        pierceCap = 6;
                        drag = 0.01f;
                        trailInterval = 1;
                        trailRotation = true;
                        trailEffect = disperseTrail;
                        hitEffect = despawnEffect = new MultiEffect(blastExplosion, hitBulletColor);
                    }};
                    shootEffect = new MultiEffect(ArikothTurretFx.shootSparkLarge, ArikothTurretFx.shootLargeColor);
                    smokeEffect = ArikothTurretFx.shootSmokeHexagonLargeSparse;
                    hitEffect = new MultiEffect(
                       ArikothTurretFx.hitLargeBulletColor,
                       new ExplosionEffect(){{
                           lifetime = 20f;
                           waveStroke = 2f;
                           waveColor = sparkColor = trailColor;
                           waveRad = 12f;
                           smokeSize = 0f;
                           smokeSizeBase = 0f;
                           sparks = 16;
                           sparkRad = 35f;
                           sparkLen = 4f;
                           sparkStroke = 1.5f;
                       }}
                    );
                    despawnEffect = none;
                    despawnHit = true;
                }};
            }},new Weapon(
                    "arikoth-frame-gunner"){{
                reload = 10f;
                x = 0;
                y = -8f;
                layerOffset = 0.002f;
                rotate = true;
                rotationLimit = 90;
                rotateSpeed = 2.5f;
                shake = 2;
                shootY = 9;
                top = false;
                shootSound = Sounds.bolt;
                ejectEffect = Fx.casing4;
                inaccuracy = 4;
                recoils = 2;
                mirror = false;
                shoot = new ShootAlternate(5);
                parts.add(new RegionPart("-front-l"){{
                    moveY = -2;
                    progress = PartProgress.recoil;
                    recoilIndex = 0;
                    under = true;
                }});
                parts.add(new RegionPart("-front-r"){{
                    moveY = -2;
                    progress = PartProgress.recoil;
                    recoilIndex = 1;
                    under = true;
                }});
                bullet = new RailBulletType(){{
                    damage = 20;
                    length = 120;
                    hitColor = ArikothUnitPal.assaultGold;
                    endEffect = new Effect(14f, e -> {
                        color(e.color);
                        Drawf.tri(e.x, e.y, e.fout() * 1.5f, 5f, e.rotation);
                    });

                    shootEffect = new Effect(10, e -> {
                        color(e.color);
                        float w = 1.2f + 7 * e.fout();
                        Drawf.tri(e.x, e.y, w, 30f * e.fout(), e.rotation);
                        color(e.color);

                        for(int i : Mathf.signs){
                            Drawf.tri(e.x, e.y, w * 0.9f, 12f * e.fout(), e.rotation + i * 90f);
                        }
                        Drawf.tri(e.x, e.y, w, 4f * e.fout(), e.rotation + 180f);
                    });
                    pointEffectSpace = 6;
                    pointEffect = new MultiEffect( new ParticleEffect(){{
                        lenFrom = 10;
                        lenTo = 3;
                        length = 10;
                        lifetime = 25;
                        colorFrom = Pal.redDust;
                        colorTo = Color.white;
                        line = true;
                        cone = 0;
                        particles = 1;
                        baseLength = 2;
                        rotWithParent = true;
                    }},
                    new ParticleEffect(){{
                        lenFrom = 10;
                        lenTo = 3;
                        length = 10;
                        lifetime = 25;
                        colorFrom = Pal.redderDust;
                        colorTo = Pal.redDust;
                        line = true;
                        cone = 0;
                        particles = 1;
                        baseLength = 2;
                        rotWithParent = true;
                    }});
                }};
            }}
            );
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
            health = 510f;
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
                shoot = new ShootAlternate(6){{
                    shots = 2;
                }};

                bullet = new BasicBulletType(){{
                    damage = 10;
                    speed = 4;
                    width = 8f;
                    height = 12;
                    weaveMag = 2;
                    weaveScale = 2;
                    trailWidth = 2;
                    trailLength = 12;
                    lifetime = 10f;
                    shootEffect = ArikothTurretFx.shootSparkSmall;
                    smokeEffect = ArikothTurretFx.shootSmokeSquareSmallSparse;
                    hitColor = trailColor = backColor = ArikothUnitPal.assaultGold;
                    frontColor = Color.white;
                    hitEffect = despawnEffect = new Effect(25, e -> {
                        color(Color.white, e.color, e.fin());

                        rand.setSeed(e.id);
                        for(int i = 0; i < 6; i++){
                            float rot = e.rotation + rand.range(360f);
                            v.trns(rot, rand.random(e.finpow() * 12f));
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
                shootSound = Sounds.torch;
                y = 6f;
                x = 0f;
                top = true;
                mirror = false;
                shootCone = 20f;
                shake = 1;
                alwaysContinuous = true;
                continuous = true;

                bullet = new ContinuousFlameBulletType(10){{
                hitColor = flareColor = ArikothUnitPal.assaultGold;
                flareRotSpeed = 0;
                width = 3;
                length = 40;
                rangeOverride = 10;
                colors = new Color[]{ArikothUnitPal.assaultGoldDark.a(0.8f), ArikothUnitPal.assaultGold, Color.white};
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
                shootSound = Sounds.plasmadrop;
                y = -6f;
                x = 8f;
                top = true;
                layerOffset = 0.0001f;
                mirror = true;
                reload = 90f;
                shootCone = 20f;
                shake = 6;

                bullet = new BasicBulletType(){{
                    sprite = "large-bomb";
                    hitSound = despawnSound = Sounds.plasmaboom;
                    splashDamage = 90;
                    splashDamageRadius = 32;

                    damage = 90;
                    speed = 16;
                    drag = 0.05f;
                    width = 18;
                    height = 18;
                    trailWidth = 3;
                    trailLength = 18;
                    lifetime = 30;
                    shrinkY = shrinkX = -1.5f;
                    spin = 2;
                    trailInterp = shrinkInterp = Interp.slope;
                    trailSinScl = 2.5f;
                    trailSinMag = 0.5f;
                    trailRotation = true;
                    trailEffect = disperseTrail;
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
                    hitEffect = despawnEffect = new MultiEffect(ArikothFx.spikyBoom, ArikothFx.crossExplodeSmall45);
                }};
            }});
        }};

        messenger = new UnitType("messenger"){{

            constructor = UnitEntity::create;
            lowAltitude = true;
            flying = true;
            drag = 0.02f;
            speed = 4f;
            rotateSpeed = 3.5f;
            accel = 0.025f;
            itemCapacity = 0;
            health = 120f;
            hitSize = 12f;
            fallSpeed = 0.006f;
            outlineColor = ArikothUnitPal.unitOutline;
            range = 120;
            engineSize = 2.5f;
            engineOffset = 7;
            engineColor = ArikothUnitPal.scoutBlue;
            circleTarget = true;
            parts.addAll(new RegionPart("-rotor"){{
                  moveRot = 360;
                  y = -6 / 4f;
                  under = true;
                  outline = false;
                  color = Color.valueOf("ffffff80");
                  progress = PartProgress.time.loop(30);
                  layerOffset = -0.001f;
              }},new RegionPart("-rotor"){{
                  moveRot = -360;
                  y = -6 / 4f;
                  under = true;
                  outline = false;
                  color = Color.valueOf("ffffff80");
                  progress = PartProgress.time.loop(30);
                  layerOffset = -0.001f;
              }},new RegionPart("-rotor-shade"){{
                  moveRot = 360;
                  y = -6 / 4f;
                  under = true;
                  outline = false;
                  color = Color.valueOf("ffffff80");
                  progress = PartProgress.time.loop(60);
                  layerOffset = -0.001f;
              }},new RegionPart("-rotor-shade"){{
                  moveRot = -360;
                  y = -6 / 4f;
                  under = true;
                  outline = false;
                  color = Color.valueOf("ffffff80");
                  progress = PartProgress.time.loop(60);
                  layerOffset = -0.001f;
              }}
            );
            abilities.add(new MoveEffectAbility(0, -6 / 4f, Color.darkGray.a(0.5f), new ParticleEffect(){{
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
            }}, 4f){{minVelocity = 0;}});
            abilities.add(new MoveEffectAbility(0, -7, ArikothUnitPal.scoutBlue,
            new MultiEffect(new ParticleEffect(){{
                colorFrom = Color.gray;
                colorTo = Color.darkGray;
                length  = 6;
                sizeFrom = 3f;
                particles = 4;
                sizeTo = 1;
                sizeInterp = Interp.linear;
                interp = Interp.linear;
                lifetime = 12;
                cone = 30;
                rotWithParent = true;
                layer = 83.9f;
            }},new ParticleEffect(){{
                colorFrom = ArikothUnitPal.scoutBlue;
                colorTo = ArikothUnitPal.scoutBlueDark;
                length  = 6;
                sizeFrom = 1.5f;
                particles = 3;
                sizeTo = 0;
                sizeInterp = Interp.linear;
                interp = Interp.linear;
                lifetime = 12;
                cone = 30;
                rotWithParent = true;
                layer = 84.9f;
            }}), 1f){{minVelocity = 1.5f; rotateEffect = true;}});

            weapons.add(new Weapon(name+"-laser"){{
                shootSound = Sounds.missile;
                x = 4;
                y = 18 / 4;
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                alternate = false;
                reload = 20f;
                shootCone = 90f;
                baseRotation = -15;

                bullet = new MissileBulletType(){{
                    damage = 10;
                    backColor = trailColor = hitColor = ArikothUnitPal.scoutBlueDark;
                    frontColor = ArikothUnitPal.scoutBlue;
                    trailWidth = 2;
                    trailLength = 6;
                    speed = 4;
                    lifetime = 35;
                    weaveMag = 1;
                    weaveScale = 2;
                    width = 12;
                    height = 12;
                    homingDelay = 5;
                    homingPower = 0.08f;
                    trailInterval = 3;
                    trailRotation = true;
                    trailEffect = new MultiEffect(
                            new ParticleEffect(){{
                                colorFrom = ArikothUnitPal.scoutBlue;
                                colorTo = ArikothUnitPal.scoutBlueDark;
                                sizeFrom = 3;
                                length = 12;
                                particles = 3;
                                cone = 3;
                                layer = 100;
                            }},
                            new ParticleEffect(){{
                                colorFrom = Color.lightGray;
                                colorTo = Color.darkGray;
                                sizeFrom = 4;
                                length = 16;
                                particles = 3;
                                cone = 3;
                                layer = 99.5f;
                            }}
                    );
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
                shootSound = Sounds.laser;
                x = 12;
                y = 8;
                shoot = new ShootHelix(6, 2);
                top = true;
                layerOffset = -0.001f;
                mirror = true;
                reload = 20f;
                shootCone = 20;

                bullet = new BasicBulletType(6, 60, "large-orb"){{
                    lifetime = 40;
                    recoil = 0.25f;
                    frontColor = ArikothUnitPal.scoutBlue;
                    backColor = trailColor = hitColor = ArikothUnitPal.scoutBlueDark;
                    trailWidth = 2;
                    trailLength = 18;
                    width = 16;
                    height = 18;
                    pierceCap = 2;
                    pierceArmor = true;
                    trailInterval = 4;
                    trailEffect = new Effect(15, e -> {
                        color(Color.white, e.color, e.fin());
                        stroke(0.2f + e.fout() * 1.7f);
                        rand.setSeed(e.id);

                        for(int i = 0; i < 4; i++){
                            float rot = e.rotation + rand.range(6f) + 180f;
                            v.trns(rot, rand.random(e.fin() * 27f));
                            lineAngle(e.x + v.x, e.y + v.y, rot, e.fout() * rand.random(4f, 8f) + 1.5f);
                        }
                    });
                    trailRotation = true;
                    shootEffect = new MultiEffect(
                            colorSparkBig,
                            new Effect(25, e -> {
                                color(Color.white, ArikothUnitPal.scoutBlue, e.fin());
                                float w = 2f + 3 * e.fout();
                                Drawf.tri(e.x, e.y, w, 38f * e.fout(), e.rotation);
                                Drawf.tri(e.x, e.y, w , 20f * e.fout(), e.rotation + 90f * e.fout(Interp.circleOut));
                                Drawf.tri(e.x, e.y, w, 20f * e.fout(), e.rotation + -90f * e.fout(Interp.circleOut));
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
            health = 240;
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

        zephyr = new TankUnitType("zephyr"){{
            hitSize = 26f;
            constructor = TankUnit::create;
            treadPullOffset = 3;
            speed = 2;
            rotateSpeed = 2.5f;
            health = 1220;
            armor = 6f;
            outlineColor = ArikothUnitPal.unitOutline;
            itemCapacity = 0;
            treadFrames = 8 * 8;
            treadRects = new Rect[] {
                    new Rect(24f, -70f, 40, 144)
            };
            researchCostMultiplier = 0f;

            weapons.add(new Weapon(name + "-weapon"){{
                layerOffset = 0.025f;
                reload = 80f;
                shootY = 0;
                recoil = 1.5f;
                rotate = true;
                rotateSpeed = 3.2f;
                mirror = false;
                shootSound = Sounds.missileSmall;
                x = 0f;
                y = -6f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                cooldownTime = 30f;
                shoot = new ShootBarrel(){{
                    shots = 7;
                    shotDelay = 6;
                    barrels = new float[]{
                            0, 5, 0,
                            2, 3, 0,
                            -2, 3, 0,
                            4, 5, 0,
                            -4, 5, 0,
                            2, 7, 0,
                            -2, 7, 0
                    };
                }};
                for(int j = 0; j < 3; j++){
                    int i = j;
                    parts.add(new RegionPart("-spine"){{
                        layerOffset = -0.01f;
                        heatLayerOffset = 0.005f;
                        x = 2f;
                        y = -3;
                        moveX = 3f + i * 1.9f;
                        moveY = 8f + -4f * i;
                        moveRot = -90f - i * 25f;
                        rotation = 0;
                        mirror = true;
                        progress = PartProgress.warmup.delay(i * 0.2f).curve(Interp.smoother);
                        heatProgress = p -> Mathf.absin(Time.time + i * 14f, 7f, 1f);

                        heatColor = ArikothUnitPal.scoutBlue;
                    }});
                }

                bullet = new AccelBulletType(8, 40, "arikoth-rocket"){{
                    frontColor = Color.white;
                    velocityBegin = 2;
                    velocityIncrease = 8;
                    backColor = trailColor = hitColor = ArikothUnitPal.scoutBlue;
                    trailWidth = 2;
                    trailLength = 12;
                    width = 8;
                    height = 18;
                    weaveScale = 8;
                    weaveMag = 2;
                    trailRotation = true;
                    trailInterval = 4;
                    trailEffect = disperseTrail;
                    pierceBuilding = true;
                    despawnHit = true;
                    hitEffect = new MultiEffect(
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
                                    Fill.poly(e.x + v.x, e.y + v.y, 6, e.fout() * 6f + 0.2f, rand.random(360f));
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
                                        Drawf.tri(e.x + Tmp.v1.x, e.y + Tmp.v1.y, e.foutpow() * 12f, e.fout() * 30f * lenRand + 6f, angle);
                                    }
                                }
                            })
                    );
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
                health = 350;
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
                    shootSound = Sounds.missile;
                    mirror = true;
                    x = 6f;
                    y = -3f;
                    shootY = 4f;
                    reload = 30f;
                    heatColor = ArikothUnitPal.arikothUnitHeat;
                    inaccuracy = 10;
                    layerOffset = 0.001f;

                    bullet = new MissileBulletType() {{
                    speed = 6;
                    lifetime = 20;
                    damage = 10;
                    weaveMag = 2;
                    weaveScale = 4;
                    trailLength = 12;
                    trailWidth = 2;
                    width = 8;
                    height = 16;
                    hitColor = trailColor = backColor = ArikothUnitPal.specialistPurpleDark;
                    frontColor = ArikothUnitPal.specialistPurple;
                    despawnEffect = hitEffect = new ExplosionEffect(){{
                        sparks = 6;
                        sparkRad = 12;
                        sparkLen = 2;
                        sparkStroke = 1;
                        lifetime = 15;
                        sparkColor = ArikothUnitPal.specialistPurple;
                        waveRad = smokeSize = 0;
                    }};
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
                shootSound = Sounds.sap;
                mirror = true;
                x = 6f;
                y = -3f;
                shootY = 4f;
                reload = 5f;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                layerOffset = 0.001f;

                bullet = new SapBulletType() {{
                    damage = 10;
                    color = ArikothUnitPal.specialistPurple;
                    length = 60;
                    lengthRand = 15;
                    hitEffect = hitBulletColor;
                    despawnEffect = none;
                    lifetime = 15;
                    shootEffect = ArikothTurretFx.shootSparkSmall;
                    smokeEffect = none;
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
                parts.add(new RegionPart("-cannon2"){{
                    moveY = -2f;
                    progress = PartProgress.recoil;
                    mirror = false;
                    under = true;
                    layerOffset = -0.001f;
                    recoilIndex = 0;
                    moves.add(new PartMove(){{progress = PartProgress.warmup; y = 2.5f;}});
                }});
                parts.add(new RegionPart("-cannon1"){{
                    moveY = -2f;
                    progress = PartProgress.recoil;
                    mirror = false;
                    under = true;
                    layerOffset = -0.001f;
                    recoilIndex = 1;
                    moves.add(new PartMove(){{progress = PartProgress.warmup; y = 2.5f;}});
                }});
                parts.add(new RegionPart("-front"){{
                   moveY = -4;
                   moveX = 4;
                   progress = PartProgress.warmup;
                   mirror = true;
                   under = true;
                }});
                mirror = false;
                x = 0f;
                y = 0f;
                shootY = 6;
                alternate = false;
                reload = 30f;
                recoil = 0;
                recoils = 2;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                shoot = new ShootAlternate(16);

                bullet = new LaserBulletType(50){{
                    lightningAngleRand = 0;
                    lightningDelay = 0;
                    lightningSpacing = 28;
                    lightningLength = 2;
                    lightningLengthRand = 1;
                    lightningCone = 1;
                    lightningType = new LaserBulletType(10){{
                        width = 12;
                        length = 20;
                        sideLength = 0;
                        shootEffect = randLifeSpark;
                        smokeEffect = shootSmokeDisperse;
                        colors = new Color[]{ArikothUnitPal.specialistPurpleDark.a(0.8f), ArikothUnitPal.specialistPurple, Color.white};
                    }};
                    width = 18;
                    length = 160;
                    sideLength = 0;
                    shootEffect = randLifeSpark;
                    smokeEffect = shootSmokeDisperse;
                    colors = new Color[]{ArikothUnitPal.specialistPurpleDark.a(0.8f), ArikothUnitPal.specialistPurple, Color.white};
                }};
            }});
        }};

        dominion = new UnitType("dominion"){{
            constructor = LegsUnit::create;
            outlineColor = ArikothUnitPal.unitOutline;
            outlineRadius = 4;
            speed = 0.38f;
            drag = 0.11f;
            hitSize = 26f;
            rotateSpeed = 2f;
            health = 12320;
            armor = 8f;

            legCount = 4;
            legMoveSpace = 1f;
            legPairOffset = 3;
            legLength = 45f;
            legExtension = -6;
            legBaseOffset = 10f;
            stepShake = 1f;
            legLengthScl = 0.96f;
            rippleScale = 2f;
            legSpeed = 0.2f;

            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;
            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;
            weapons.add(new Weapon() {{
                shootSound = Sounds.missile;
                mirror = true;
                x = 16f;
                y = -3f;
                shootY = 6;
                alternate = false;
                reload = 120f;
                recoil = 0;
                recoils = 2;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                shoot.shots = 12;
                shoot.shotDelay = 2;
                inaccuracy = 3;
                baseRotation = -90;
                shootCone = 360;

                bullet = new AimBulletType(6, 10){{
                    speed = 6;
                    width = 8;
                    height = 12;
                    weaveMag = 2;
                    trailColor = backColor = hitColor = ArikothUnitPal.specialistPurple;
                    frontColor = Color.white;
                    trailWidth = 2;
                    trailLength = 12;
                    homingDelay = 5;
                    homingPower = 0.28f;
                    lifetime = 80;
                    shootEffect = sparkShoot;
                    smokeEffect = shootSmokeDisperse;
                    hitEffect = despawnEffect = ArikothTurretFx.hitLargeBulletColor;
                }};
            }});
            weapons.add(new Weapon(name + "-weapon") {{
                shootSound = Sounds.malignShoot;
                mirror = true;
                x = 10f;
                y = -6f;
                shootY = 6;
                alternate = true;
                reload = 30f;
                recoil = 0;
                recoils = 2;
                heatColor = ArikothUnitPal.arikothUnitHeat;
                shoot.shots = 2;
                shoot.shotDelay = 8;
                inaccuracy = 3;
                layerOffset = 0.001f;

                bullet = new LaserBulletType(40){{
                    lightningAngleRand = 0;
                    lightningAngle = 45;
                    lightningDelay = 0;
                    lightningSpacing = 32;
                    lightningLength = 2;
                    lightningLengthRand = 1;
                    lightningCone = 1;
                    lightningType = new LaserBulletType(10){{
                        width = 12;
                        length = 16;
                        sideLength = 0;
                        shootEffect = randLifeSpark;
                        smokeEffect = shootSmokeDisperse;
                        colors = new Color[]{ArikothUnitPal.specialistPurpleDark.a(0.8f), ArikothUnitPal.specialistPurple, Color.white};
                    }};
                    width = 18;
                    length = 190;
                    sideLength = 0;
                    shootEffect = randLifeSpark;
                    smokeEffect = shootSmokeDisperse;
                    colors = new Color[]{ArikothUnitPal.specialistPurpleDark.a(0.8f), ArikothUnitPal.specialistPurple, Color.white};
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
                           applySound = Sounds.explosionbig;
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

        pioneer = new UnitType("pioneer") {{
            aiController = BuilderAI::new;
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 5f;
            fallSpeed = 0.05f;
            boostMultiplier = 2f;
            rotateSpeed = 6.8f;
            drag = 0.08f;
            accel = 0.1f;
            flying = true;
            health = 380;
            hitSize = 8;
            armor = 3;
            itemCapacity = 40;
            outlineColor = ArikothUnitPal.unitOutline;
            engineOffset = 7.5f;
            faceTarget = true;
            mineTier = 1;
            mineSpeed = 2;
            buildSpeed = 1f;
            buildBeamOffset = 5;
            isEnemy = false;
            mineWalls = true;
            mineFloor = false;
            targetable = true;
            hittable = true;
            weapons.add(new RepairBeamWeapon(){{
                y =6f;
                x = 0;
                mirror = false;
                layerOffset = -0.001f;
                beamWidth = 0.7f;
                repairSpeed = 3.1f;
                fractionRepairSpeed = 0.06f;
                aimDst = 0f;
                shootCone = 15f;

                targetUnits = false;
                targetBuildings = true;
                autoTarget = false;
                controllable = true;
                laserColor = Pal.accent;
                healColor = Pal.accent;

                bullet = new BulletType(){{
                    maxRange = 60f;
                }};
            }});
        }};

        vision = new UnitType("vision") {{
            aiController = BuilderAI::new;
            constructor = UnitEntity::create;

            lowAltitude = true;
            speed = 3.8f;
            fallSpeed = 0.05f;
            boostMultiplier = 2f;
            rotateSpeed = 4.8f;
            drag = 0.08f;
            accel = 0.1f;
            flying = true;
            health = 380;
            hitSize = 8;
            armor = 3;
            itemCapacity = 40;
            outlineColor = ArikothUnitPal.unitOutline;
            engineOffset = 8.5f;
            engineSize = 3.2f;
            faceTarget = true;
            mineTier = 1;
            mineSpeed = 5;
            buildSpeed = 2f;
            buildBeamOffset = 5;
            isEnemy = false;
            mineWalls = true;
            mineFloor = false;
            targetable = true;
            hittable = true;
            setEnginesMirror(
                    new UnitEngine(7.5f, -7.5f, 2.4f, -45f)
            );
            weapons.add(new Weapon(){{
                y = 4f;
                x = 0f;
                rotate = false;
                shootY = 7f;
                mirror = false;
                layerOffset = -0.001f;
                reload = 30;
                shootSound = Sounds.bolt;
                soundPitchMin = 2.8f;
                soundPitchMax = 1.2f;
                shootCone = 15f;
                shoot.shots = 5;
                shoot.shotDelay = 3;
                inaccuracy = 6;
                velocityRnd = 0.2f;

                bullet = new LaserBoltBulletType(8, 10){{
                    width = 1.5f;
                    height = 7f;
                    lifetime = 20f;
                    smokeEffect = none;
                    shootEffect = ArikothTurretFx.hitLaserAccent;

                    hitEffect = despawnEffect = ArikothTurretFx.verySmallStarFour;

                    collidesTeam = true;
                    healAmount = 30;

                    buildingDamageMultiplier = 0.05f;

                    backColor = hitColor =  Pal.accent;
                    frontColor = Color.white;
                }};
            }});
        }};
    }
}
package arikoth.content;

import arc.graphics.Blending;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Rect;
import arikoth.content.effects.ArikothHitFx;
import arikoth.content.effects.ArikothShootFx;
import arikoth.math.ArikothInterps;
import arikoth.content.effects.palettes.ArikothPal;
import arikoth.world.entities.bullets.AccelBulletType;
import arikoth.world.presets.*;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.RepairAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.SoundEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.weapons.BuildWeapon;

import static arc.math.Angles.randLenVectors;

public class ArikothUnitTypes {

    public static UnitType

            //Core
            weld,
            //airAssault
            vulkar, peregrine,
            //groundAssault - Siege
            retribute,
            //groundAssault - Attacker
            kriegsman,
            //misc
            repairDrone,

    end;

    public static void load() {

        String def = "arikoth-arikoth-bullet";
        String pointyDef = "arikoth-arikoth-pointy-bullet";
        String rocket = "arikoth-rocket";

        weld = new UnitType("weld"){{
            constructor = LegsUnit::create;
            health = 200;
            armor = 10;
            legLength = 21;
            legBaseOffset = 3;
            legCount = 6;
            legContinuousMove = true;
            legExtension = -2;

            outlineColor = ArikothPal.outline;
            outlineRadius = 3;

            buildSpeed = 1.5f;
            buildBeamOffset = 12;

            mineTier = 2;
            mineSpeed = 7.5f;
            mineHardnessScaling = false;
            itemCapacity = 120;
            mineRange = 120;
            mineBeamOffset = 12;

            drawCell = false;

            weapons.add(new BuildWeapon(){{
                rotate = true;
                rotateSpeed = 1.2f;
                x = 36/4f;
                y = 14/4f;
                layerOffset = -0.001f;
                shootY = 0f;
            }});

            weapons.add(new Weapon(){{
                reload = 5;
                rotate = false;
                shootCone = 360;
                shootY = 0;
                mirror = true;
                x = 36/4f;
                y = 14/4f;
                layerOffset = -0.0001f;
                alternate = false;
                inaccuracy = 4;
                velocityRnd = 0.3f;
                shootSound = Sounds.shootLaser;
                bullet = new LaserBoltBulletType(8, 10){{
                    buildingDamageMultiplier = 0.05f;
                    frontColor = Color.white;
                    backColor = hitColor = Color.valueOf("81e8e4");
                    despawnEffect = Fx.none;
                    despawnHit = true;
                    healAmount = 5f;
                    collidesTeam = true;
                    shootEffect = hitEffect = new WaveEffect(){{
                        followParent = false;
                        sizeFrom = 0;
                        sizeTo = 8;
                        strokeFrom = 2;
                        strokeTo = 0;
                        colorFrom = Color.white;
                        colorTo = hitColor;
                        lifetime = 15;
                        sides = 4;
                    }};
                }};
            }});
        }};

        vulkar = new UnitType("carnifex"){{
            constructor = UnitEntity::create;
            flying = true;
            hitSize = 24;

            speed = 6f;
            rotateSpeed = 4.8f;
            drag = 0.025f;
            accel = 0.025f;
            health = 6000;
            armor = 20;

            outlineColor = ArikothPal.outline;
            outlineRadius = 3;

            engineOffset = 3;
            engineSize = 2.5f;

            drawCell = false;
            parts.add(new RegionPart("-wing"){{
                moveRot = -60;
                rotation = 30;
                mirror = true;
                outline = false;
                layerOffset = 0.001f;
                x = 5;
                y = 0.5f;
                progress = PartProgress.time.loop(5).slope().curve(Interp.smoother);
                color = Color.white.cpy().a(0.5f);
            }},new RegionPart("-wing-shade"){{
                moveRot = -60;
                rotation = 30;
                mirror = true;
                outline = false;
                layerOffset = 0.001f;
                x = 5;
                y = 0.5f;
                progress = PartProgress.time.loop(5).slope().curve(Interp.smoother);
                color = Color.white.cpy().a(0.5f);
            }},new RegionPart("-wing"){{
                moveRot = 60;
                rotation = -30;
                mirror = true;
                outline = false;
                layerOffset = 0.001f;
                x = 5;
                y = 6;
                progress = PartProgress.time.loop(5).slope().curve(Interp.smoother);
                color = Color.white.cpy().a(0.5f);
            }},new RegionPart("-wing-shade"){{
                moveRot = 60;
                rotation = -30;
                mirror = true;
                outline = false;
                layerOffset = 0.001f;
                x = 5;
                y = 6;
                progress = PartProgress.time.loop(5).slope().curve(Interp.smoother);
                color = Color.white.cpy().a(0.5f);
            }});

            weapons.addAll(new Weapon(name + "-rocketPod"){{
                reload = 80;
                rotate = false;
                shootCone = 360;
                shootX = 0;
                shootY = 5;
                mirror = true;
                x = 34f/4f;
                y = 0;
                layerOffset = -0.0001f;
                alternate = true;
                shoot.shots = 10;
                shoot.shotDelay = 1;
                xRand = 2;
                shake = 4;
                shootSound = Sounds.shootMissile;
                bullet = new AccelBulletType(8, 50, rocket){{
                    splashDamage = damage;
                    splashDamageRadius = 16;
                    velocityBegin = 1;
                    velocityIncrease = 8;
                    lifeScaleRandMax = 1.2f;
                    lifeScaleRandMin = 0.8f;
                    velocityScaleRandMax = 1.2f;
                    accelInterp = Interp.pow2Out;
                    frontColor = ArikothPal.yellowEmber;
                    hitColor = trailColor = backColor = ArikothPal.orangeEmber;
                    width = 12;
                    height = 18;
                    trailWidth = 1;
                    trailLength = 12;
                    weaveMag = 6f;
                    weaveScale = 1;
                    lifetime = 35;
                    hitSound = despawnSound = Sounds.explosion;
                    homingPower = 0.08f;
                    homingDelay = 5;
                    smokeEffect = new MultiEffect(
                            Fx.shootSmokeDisperse
                    );
                    hitEffect = despawnEffect = Fx.blastExplosion;
                }};
            }},new Weapon(name + "-gunner"){{
                reload = 10;
                rotate = false;
                shootCone = 360;
                shootX = 0;
                shootY = 5;
                mirror = true;
                x = 18f/4f;
                y = 25f/4f;
                layerOffset = -0.0002f;
                alternate = true;
                shake = 1;
                shootSound = Sounds.shootStell;
                bullet = new BasicBulletType(24, 60){{
                    frontColor = ArikothPal.yellowShell;
                    hitColor = trailColor = backColor = ArikothPal.yellowShellFront;
                    width = 8;
                    height = 48;
                    trailWidth = 2;
                    trailLength = 8;
                    lifetime = 5.5f;
                    smokeEffect = new MultiEffect(
                            Fx.shootSmokeDisperse
                    );
                    hitEffect = despawnEffect = Fx.hitSquaresColor;
                }};
            }});
        }};

        repairDrone = new UnitType("repair-drone"){{
            constructor = UnitEntity::create;
            flying = true;
            hitSize = 12;

            commands.add(UnitCommand.repairCommand, UnitCommand.moveCommand);
            defaultCommand = UnitCommand.repairCommand;
            playerControllable = false;
            allowChangeCommands = true;
            aiController = RepairAI::new;

            speed = 6f;
            rotateSpeed = 4.8f;
            drag = 0.05f;
            accel = 0.025f;
            health = 600;
            armor = 5;

            outlineColor = ArikothPal.outline;
            outlineRadius = 4;

            engineOffset = 0;
            engineSize = 0;

            drawCell = false;

            engines.add(new LightEngine(0, -3, 8, 0){{
                triLength = 16;
                triWidth = 3;
                triIn = 2;
            }});

            weapons.add(new Weapon(){{
                reload = 5;
                rotate = false;
                shootCone = 360;
                shootY = 6;
                mirror = true;
                x = y = 0;
                layerOffset = -0.0001f;
                alternate = false;
                inaccuracy = 4;
                velocityRnd = 0.3f;
                shootSound = Sounds.shootLaser;
                bullet = new LaserBoltBulletType(8, 10){{
                    buildingDamageMultiplier = 0.05f;
                    frontColor = Color.white;
                    backColor = hitColor = Color.valueOf("81e8e4");
                    despawnEffect = Fx.none;
                    despawnHit = true;
                    healAmount = 5f;
                    collidesTeam = true;
                    shootEffect = hitEffect = new WaveEffect(){{
                        followParent = false;
                        sizeFrom = 0;
                        sizeTo = 8;
                        strokeFrom = 2;
                        strokeTo = 0;
                        colorFrom = Color.white;
                        colorTo = hitColor;
                        lifetime = 15;
                        sides = 4;
                    }};
                }};
            }});
        }};

        retribute = new ArikothUnitType("retribute") {{
            constructor = TankUnit::create;
            health = 85000;
            armor = 50;

            speed = 1.2f;
            rotateSpeed = 1.4f;

            hitSize = 58f;
            squareShape = true;
            crushDamage = 0.75f;

            lightColor = Color.white;
            lightRadius = 200;
            lightOpacity = 0.8f;

            wrecks = -1;

            omniMovement = false;

            floorMultiplier = 0.8f;

            range = maxRange = 160f;

            treadRects = new Rect[] {
                    new Rect(34f, 19f, 61, 96),
                    new Rect(38f, -115f, 50, 72)
            };
            parts.addAll(new RegionPart("-lower-frame"){{
                layerOffset = -0.001f;
            }},new RegionPart("-lights"){{
                layerOffset = 0.0001f;
                outline = false;
                blending = Blending.additive;
            }}, new RegionPart("-lights-glow"){{
                layerOffset = 0.0001f;
                outline = false;
                blending = Blending.additive;
            }});
            treadFrames = 12*12;

            weapons.addAll(new Weapon(){{
                mirror = true;
                x = 69/4f;
                y = 41/4f;
                reload = 2;
                alwaysContinuous = true;
                alwaysShooting = true;
                alwaysShootWhenMoving = true;
                continuous = true;
                display = false;
                controllable = false;
                alternate = false;
                shootSound = Sounds.none;
                shoot = new ShootSpread(6, 1.5f);
                bullet = new ContinuousFlameBulletType(){{
                    lightColor = Color.white;
                    lightOpacity = 0.75f;
                    drawFlare = false;
                    colors = new Color[]{Color.clear};
                    width = 0.1f;
                    length = 64;
                    damage = 0;
                    collides = false;
                }};

            }},new Weapon(name + "-cannon") {{
                mirror = false;

                x = 0f;
                y = -8f;
                shootY = 28;

                reload = 120f;
                recoil = 0;
                recoils = 3;

                rotate = true;
                rotateSpeed = 1.5f;
                shoot = new ShootAlternate(){{
                    barrels = 3;
                    spread = 23/4f;
                    shots = 3;
                    shotDelay = 10;
                }};

                parts.addAll(
                        new RegionPart("-barrel"){{
                            x = -23/4f;
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 0;
                            moves.addAll(
                                    new PartMove(PartProgress.recoil.compress(0, 0.85f), 0, -12, 0),
                                    new PartMove(PartProgress.recoil.compress(0.85f, 1).curve(Interp.pow5In), 0, 12, 0)
                            );
                        }},
                        new RegionPart("-barrel"){{
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 1;
                            moves.addAll(
                                    new PartMove(PartProgress.recoil.compress(0, 0.85f), 0, -12, 0),
                                    new PartMove(PartProgress.recoil.compress(0.85f, 1).curve(Interp.pow5In), 0, 12, 0)
                            );
                        }},
                        new RegionPart("-barrel"){{
                            x = 23/4f;
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 2;
                            moves.addAll(
                                    new PartMove(PartProgress.recoil.compress(0, 0.85f), 0, -12, 0),
                                    new PartMove(PartProgress.recoil.compress(0.85f, 1).curve(Interp.pow5In), 0, 12, 0)
                            );
                        }},
                        new RegionPart("")
                );
                shootSound = Sounds.explosionCrawler;
                layerOffset = 0.003f;
                recoilTime = 60;
                bullet = new ArtilleryBulletType(6, 0.01f) {{
                    lifetime = 100;
                    splashDamage = damage;
                    splashDamageRadius = 32;

                    shootEffect = new MultiEffect(
                            ArikothShootFx.shootSpike(32, 4, 8, 48, 8),
                            new SoundEffect(){{
                                sound = Sounds.shootTank;
                                effect = Fx.colorSparkBig;
                            }}
                    );

                    despawnSound = Sounds.explosionTitan;
                    despawnEffect = new MultiEffect(
                            ArikothHitFx.sparkExplosion(Interp.pow10Out, 32, 160, 1, 3, 7, 32, 12),
                            ArikothHitFx.spikeBoom(Interp.pow10Out, 32, 160, 12, 64, 8),
                            ArikothHitFx.explosionLight(Interp.pow10Out, 32, 160, 32, 0.25f),
                            ArikothHitFx.smokeExplosion(Interp.pow5Out, ArikothPal.redShellFront.cpy().a(0.5f), ArikothPal.redShell.cpy().a(0.25f), false, 180, 12, 16, 20, 8).layer(110)
                    );

                    collides = reflectable = hittable = absorbable = false;

                    width = 24;
                    height = 32;

                    shrinkY = 0.8f;
                    shrinkX = 0.1f;
                    shrinkInterp = ArikothInterps.parabola;

                    frontColor = lightColor = hitColor = ArikothPal.redShellFront;
                    backColor = trailColor = ArikothPal.redShell;

                    lightOpacity = 0.8f;
                    lightRadius = 55;

                    trailLength = 12;
                    trailWidth = 4;

                    fragBullets = 18;
                    fragLifeMin = 0.2f;
                    fragLifeMax = 1.2f;
                    fragBullet = new ArtilleryBulletType(4, 60){{
                        splashDamage = damage;
                        splashDamageRadius = 16;
                        lifetime = 30;
                        width = 12;
                        height = 20;

                        trailEffect = Fx.none;

                        collides = reflectable = hittable = absorbable = false;

                        despawnEffect = new MultiEffect(
                                ArikothHitFx.spikeExplosion(Interp.pow2Out, 15, 120, 8, 1, 6, 18, 8),
                                ArikothHitFx.explosionLight(Interp.pow2Out, 15, 120, 8, 0.5f),
                                Fx.blastExplosion
                        );

                        frontColor = lightColor = hitColor = ArikothPal.redShellFront;
                        backColor = trailColor = ArikothPal.redShell;
                    }};
                }};
            }});
        }};

        kriegsman = new ArikothUnitType("kriegsman") {{
            constructor = TankUnit::create;
            health = 6800;
            armor = 80;

            speed = 1.2f;
            rotateSpeed = 1.8f;

            hitSize = 24f;
            squareShape = true;
            crushDamage = 0.02f;

            lightColor = Color.white;
            lightRadius = 150;
            lightOpacity = 0.8f;

            wrecks = -1;

            omniMovement = false;

            floorMultiplier = 0.6f;

            range = maxRange = 160f;

            treadRects = new Rect[] {
                    new Rect(24f, 19f, 32, 60),
                    new Rect(24f, -51f, 32, 60)
            };
            parts.addAll(new RegionPart("-plow"){{
                layerOffset = -0.001f;
            }});
            treadFrames = 10*10;

            weapons.addAll(new Weapon(name + "-weapon") {{
                mirror = false;

                x = 0f;
                y = -4.5f;
                shootY = 20;

                reload = 5f;
                recoilTime = 15;
                recoil = 1.5f ;
                recoils = 4;

                rotate = true;
                rotateSpeed = 1.5f;
                inaccuracy = 1;
                velocityRnd = -0.2f;
                shoot = new ShootAlternate(){{
                    barrels = 2;
                    spread = 30/4f;
                }};

                parts.addAll(
                        new RegionPart("-barrel-l"){{
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 0;
                            moves.add(new PartMove(PartProgress.recoil, 0, -6, 0));
                            layerOffset = -0.001f;
                            children.add(new RegionPart("-barrel-l-glow"){{
                                progress = PartProgress.recoil;
                                recoilIndex = 0;
                                blending = Blending.additive;
                                color = Color.clear;
                                colorTo = ArikothPal.orangeEmber;
                                outline = false;
                            }});
                        }},
                        new RegionPart("-barrel-r"){{
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 1;
                            moves.add(new PartMove(PartProgress.recoil, 0, -6, 0));
                            layerOffset = -0.001f;
                            children.add(new RegionPart("-barrel-r-glow"){{
                                progress = PartProgress.recoil;
                                recoilIndex = 1;
                                blending = Blending.additive;
                                color = Color.clear;
                                colorTo = ArikothPal.orangeEmber;
                                outline = false;
                            }});
                        }},
                        new RegionPart("-barrel-l"){{
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 2;
                            moves.add(new PartMove(PartProgress.recoil, 0, -6, 0));
                            children.add(new RegionPart("-barrel-l-glow"){{
                                progress = PartProgress.recoil;
                                recoilIndex = 2;
                                blending = Blending.additive;
                                color = Color.clear;
                                colorTo = ArikothPal.orangeEmber;
                                outline = false;
                            }});
                        }},
                        new RegionPart("-barrel-r"){{
                            heatProgress = PartProgress.recoil;
                            recoilIndex = 3;
                            moves.add(new PartMove(PartProgress.recoil, 0, -6, 0));
                            children.add(new RegionPart("-barrel-r-glow"){{
                                progress = PartProgress.recoil;
                                recoilIndex = 3;
                                blending = Blending.additive;
                                color = Color.clear;
                                colorTo = ArikothPal.orangeEmber;
                                outline = false;
                            }});
                        }},
                        new RegionPart(""){{outline = false;}}
                );
                shootSound = Sounds.explosion;
                layerOffset = 0.003f;
                bullet = new BasicBulletType(18, 35) {{
                    lifetime = 15;

                    shootEffect = new MultiEffect(
                            ArikothShootFx.shootSpike(15, 2, 2, 50, 8),
                            new SoundEffect(){{
                                sound = Sounds.shootReign;
                                effect = Fx.colorSparkBig;
                            }}
                    );

                    despawnEffect = hitEffect = Fx.blastExplosion;

                    width = 6;
                    height = 48;

                    trailWidth = 1.5f;
                    trailLength = 8;

                    frontColor = lightColor = hitColor = ArikothPal.redShellFront;
                    backColor = trailColor = ArikothPal.redShell;

                    lightOpacity = 0.8f;
                    lightRadius = 55;
                }};
            }});
        }};
    }
}
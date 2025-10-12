package arikoth.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arikoth.content.effects.ArikothFx;
import arikoth.palettes.ArikothLifeformPal;
import arikoth.palettes.ArikothTurretPal;
import mindustry.ai.types.GroundAI;
import mindustry.ai.types.SuicideAI;
import mindustry.content.StatusEffects;
import mindustry.entities.abilities.LiquidExplodeAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.EffectSpawnerPart;
import mindustry.entities.part.RegionPart;
import mindustry.gen.*;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.meta.BlockFlag;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.content.Fx.*;

public class ArikothLifeformUnitTypes {

    public static UnitType

            //piron  cannon fodder
            piron12, piron18, piron23, piron36,

            //canthid  torchers
            canthid08, cathid16, canthid28, canthid32,

            // helicoth missile skirmishers
            helicoth16, helicoth18, helicoth25, helicoth32,

            // merecoid
            merecoid08, merecoid12, merecoid16, merecoid24,

            // optillid  special stuff or smth ig
            optillid12, optillid23, optillid26, optillid38,
            // caerites
            cereth;

    public static void load() {
        piron12 = new UnitType("piron-12"){{
            aiController = SuicideAI::new;
            constructor = CrawlUnit::create;

            speed = 1.4f;
            rotateSpeed = 2;
            health = 540;
            hitSize = 6;
            armor = 2;
            outlineColor = ArikothLifeformPal.lifeformOutline;
            crushDamage = 0.1f;

            segments = 2;
            segmentScl = 3;
            segmentPhase = 5;
            segmentMag = 0.3f;
            drawBody = false;
            drawCell = false;
            createScorch = false;
            deathSound = Sounds.none;
            deathExplosionEffect = new MultiEffect(
                    ArikothFx.smallDeathSmoke,
                    new ParticleEffect(){{
                        region = "arikoth-piron-head";
                        spin = 1.2f;
                        baseLength = 6;
                        length = 12;
                        lifetime = 180;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                        sizeFrom = 6;
                        colorFrom = Color.white;
                        colorTo = Color.white;
                        particles = 1;
                        layer = Layer.legUnit;
                    }},
                    new ParticleEffect(){{
                        region = "arikoth-piron-tail";
                        spin = 1.2f;
                        baseLength = 6;
                        length = 12;
                        lifetime = 180;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                        sizeFrom = 6;
                        particles = 1;
                        layer = Layer.legUnit;
                    }}
            );

            weapons.addAll(
                    new Weapon(){{
                        x = 0;
                        y = 0;
                        shootY = 0;
                        shootOnDeath = true;
                        mirror = false;
                        shoot.firstShotDelay = 60;
                        shake = 6;
                        shootSound = Sounds.dullExplosion;
                        chargeSound = Sounds.spray;
                        parts.add(new EffectSpawnerPart(){{
                            effectColor = ArikothLifeformPal.lifeformLight;
                            effectChance = 1.5f;
                            effect = vaporSmall;
                            progress = PartProgress.charge;
                        }});
                        bullet = new BulletType(){{
                            collidesTiles = false;
                            collides = false;
                            hitSound = Sounds.explosion;

                            rangeOverride = 30f;
                            hitEffect = none;
                            speed = 0f;
                            splashDamageRadius = 55f;
                            instantDisappear = true;
                            splashDamage = 90f;
                            killShooter = true;
                            hittable = false;
                            collidesAir = true;
                            fragBullets = 6;
                            fragBullet = new ArtilleryBulletType(2, 0){{
                                frontColor = ArikothLifeformPal.lifeformLight;
                                backColor = hitColor = trailColor = healColor = ArikothLifeformPal.lifeformMid;
                                trailLength = 6;
                                trailWidth = 2;
                                width = 8;
                                height = 12;
                                lifetime = 20;
                                splashDamageRadius = 10;
                                splashDamage = 20;
                                createChance = 0.8f;
                                hitEffect = despawnEffect = ArikothFx.tinyExplosion;
                            }};
                        }};
                    }}
            );
        }};

        piron18 = new UnitType("piron-18"){{
            aiController = GroundAI::new;
            constructor = CrawlUnit::create;

            speed = 1.3f;
            rotateSpeed = 2;
            health = 1240;
            hitSize = 12;
            armor = 6;
            outlineColor = ArikothLifeformPal.lifeformOutlineAlt;
            crushDamage = 0.1f;

            segments = 3;
            segmentScl = 6;
            segmentPhase = 25f;
            segmentMag = 0.4f;
            drawBody = false;
            drawCell = false;
            createScorch = false;
            deathSound = Sounds.splash;
            deathExplosionEffect = new MultiEffect(
                    ArikothFx.smallDeathSmoke,
                    new ParticleEffect(){{
                        region = "arikoth-piron-head";
                        spin = 1.2f;
                        baseLength = 6;
                        length = 12;
                        lifetime = 180;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                        sizeFrom = 6;
                        colorFrom = Color.white;
                        colorTo = Color.white;
                        particles = 1;
                        layer = Layer.legUnit;
                    }},
                    new ParticleEffect(){{
                        region = "arikoth-piron-tail";
                        spin = 1.2f;
                        baseLength = 6;
                        length = 12;
                        lifetime = 180;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                        sizeFrom = 6;
                        particles = 1;
                        layer = Layer.legUnit;
                    }}
            );

            weapons.addAll(
                    new Weapon(){{
                        x = 0;
                        y = -1.5f;
                        shootY = 0;
                        mirror = false;
                        inaccuracy = 60;
                        reload = 10;
                        shootSound = Sounds.missile;
                        bullet = new BasicBulletType(4, 20, "missile"){{
                            weaveScale = 3;
                            weaveMag = 2;
                            trailLength = 12;
                            trailWidth = 2;
                            width = 8.5f;
                            height = 12;
                            rangeOverride = 200;
                            shootEffect = none;
                            smokeEffect = new ParticleEffect(){{
                                colorFrom = ArikothLifeformPal.lifeformLight;
                                colorTo = ArikothLifeformPal.lifeformMid.a(0.5f);
                                sizeFrom = 4;
                                sizeTo = 0;
                                particles = 3;
                                length = 10;
                                sizeInterp = Interp.pow10In;
                                interp = Interp.pow10Out;
                                followParent = false;
                                rotWithParent = false;
                                cone = 20;
                                baseRotation = 180;
                                lifetime = 60;
                            }};
                            splashDamage = 20;
                            splashDamageRadius = 12;
                            homingPower = 0.08f;
                            homingDelay = 5;
                            lifetime = 30;
                            frontColor = ArikothLifeformPal.lifeformLight;
                            backColor = hitColor = trailColor = healColor = ArikothLifeformPal.lifeformMid;
                            hitEffect = despawnEffect = ArikothFx.lifeformMissile;
                        }};
                    }}
            );
        }};

        piron23 = new UnitType("piron-23"){{
            aiController = GroundAI::new;
            constructor = CrawlUnit::create;

            speed = 1.1f;
            rotateSpeed = 1.8f;
            health = 1440;
            hitSize = 12;
            armor = 7;
            outlineColor = ArikothLifeformPal.lifeformOutlineAlt;
            crushDamage = 0.1f;

            segments = 3;
            segmentScl = 6;
            segmentPhase = 25f;
            segmentMag = 0.4f;
            drawBody = false;
            drawCell = false;
            createScorch = false;
            deathSound = Sounds.splash;
            deathExplosionEffect = new MultiEffect(
                    ArikothFx.smallDeathSmoke,
                    new ParticleEffect(){{
                        region = "arikoth-piron-head";
                        spin = 1.2f;
                        baseLength = 6;
                        length = 12;
                        lifetime = 180;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                        sizeFrom = 6;
                        colorFrom = Color.white;
                        colorTo = Color.white;
                        particles = 1;
                        layer = Layer.legUnit;
                    }},
                    new ParticleEffect(){{
                        region = "arikoth-piron-tail";
                        spin = 1.2f;
                        baseLength = 6;
                        length = 12;
                        lifetime = 180;
                        interp = Interp.pow10Out;
                        sizeInterp = Interp.pow10In;
                        sizeFrom = 6;
                        particles = 1;
                        layer = Layer.legUnit;
                    }}
            );

            weapons.addAll(
                    new Weapon(name + "-weapon"){{
                        x = 0;
                        y = -1.5f;
                        shootY = 8;
                        mirror = false;
                        inaccuracy = 10;
                        reload = 80;
                        rotate = true;
                        layerOffset = 0.002f;
                        parts.add(new RegionPart("-segment"){{
                            progress = PartProgress.time.loop(120).slope().curve(Interp.smoother);
                            moveRot = -12;
                            y = -1;
                            x = -0.5f;
                            moveY = -1f;
                            moveX = -1f;
                            under = true;
                            growY = 0.1f;
                            growX = 0.1f;
                            growProgress = PartProgress.time.loop(120).slope().curve(Interp.smoother);
                        }});
                        shootSound = Sounds.dullExplosion;
                        bullet = new BasicBulletType(4, 80, "shell"){{
                            weaveScale = 3;
                            weaveMag = 2;
                            trailLength = 12;
                            trailWidth = 2.5f;
                            width = 12;
                            height = 16;
                            rangeOverride = 240;
                            knockback = 8;
                            smokeEffect = shootSmokeSquareBig;
                            shootEffect = new ParticleEffect(){{
                                colorFrom = ArikothLifeformPal.lifeformLight;
                                colorTo = ArikothLifeformPal.lifeformMid.a(0.5f);
                                sizeFrom = 3;
                                sizeTo = 0;
                                particles = 6;
                                length = 25;
                                sizeInterp = Interp.fastSlow;
                                followParent = false;
                                rotWithParent = false;
                                cone = 20;
                                lifetime = 60;
                            }};
                            splashDamage = 80;
                            splashDamageRadius = 12;
                            homingPower = 0.02f;
                            homingDelay = 10;
                            lifetime = 60;
                            frontColor = Color.white;
                            backColor = hitColor = trailColor = healColor = ArikothLifeformPal.lifeformLight;
                            hitEffect = despawnEffect = ArikothFx.tinyExplosion;
                        }};
                    }}
            );
        }};

        // Canthids

        canthid08 = new UnitType("canthid8"){{
            constructor = LegsUnit::create;
            outlineColor = ArikothLifeformPal.lifeformOutlineAlt;
            speed = 0.8f;
            drag = 0.11f;
            hitSize = 9f;
            rotateSpeed = 3f;
            health = 620;
            armor = 4f;
            legStraightness = 0f;
            stepShake = 0f;

            legCount = 4;
            legLength = 16f;
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
            healColor = ArikothLifeformPal.lifeformLight;

            legMoveSpace = 1f;
            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;
            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            drawCell = false;
            targetAir = true;

            weapons.add(new Weapon(name + "-frontsect") {{
                shootSound = Sounds.sap;
                mirror = false;
                x = 0;
                y = 0;
                shootY = 7f;
                showStatSprite = false;
                reload = 10f;
                inaccuracy = 5;
                layerOffset = -0.001f;
                recoil = 0;
                parts.addAll(
                        new RegionPart("1"){{
                            y = -3;
                            x = 5;
                            under = true;
                            moves.add(new PartMove(p -> Mathf.absin( 30f, 1f), -1, -1, -10));
                        }},
                        new RegionPart("2"){{
                            y = -3;
                            x = -5;
                            under = true;
                            moves.add(new PartMove(p -> Mathf.absin( 31f, 1f), -1, -1, 10));
                        }},
                        new RegionPart("3"){{
                            y = 3;
                            x = 4;
                            under = true;
                            moves.add(new PartMove(p -> Mathf.absin( 29f, 1f), -1, -1, -20));
                        }},
                        new RegionPart("4"){{
                            y = 3;
                            x = -4;
                            under = true;
                            moves.add(new PartMove(p -> Mathf.absin( 31f, 1f), 1, -1, 20));
                        }}
                );

                bullet = new SapBulletType() {{
                    damage = 25;
                    sapStrength = 0.5f;
                    color = ArikothLifeformPal.lifeformLight;
                    length = 60;
                    lengthRand = 5;
                    hitEffect = hitBulletColor;
                    despawnEffect = none;
                    shootEffect = ArikothFx.lifeformSmoke;
                }};
            }});
        }};

        // helicoth

        // merecoid

        merecoid08 = new UnitType("merecoid08"){{

            constructor = UnitEntity::create;
            health = 430;
            speed = 1.65f;
            accel = 0.08f;
            drag = 0.016f;
            flying = true;
            outlineColor = ArikothLifeformPal.lifeformOutlineAlt;

            hitSize = 11f;
            targetAir = false;
            engineOffset = 0;

            range = 140f;
            faceTarget = false;
            autoDropBombs = true;
            armor = 3f;
            circleTarget = true;
            drawCell = false;
            parts.add(new RegionPart("-sac"){{
                y = -13.5f / 4f;
                x = -10.5f / 4f;
                under = false;
                growX = 0.1f;
                growY = 0.1f;
                growProgress = PartProgress.time.loop(120).slope().curve(Interp.smoother);
            }});

            weapons.add(new Weapon(){{
                minShootVelocity = 0.75f;
                x = 3f;
                shootY = 0f;
                reload = 12f;
                shootCone = 180f;
                ejectEffect = none;
                inaccuracy = 15f;
                ignoreRotation = true;
                shootSound = Sounds.none;
                bullet = new BombBulletType(30f, 25f){{
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = ArikothTurretPal.icherLight;
                    width = 10f;
                    height = 14f;
                    hitEffect = ArikothFx.tinyExplosion;
                    shootEffect = none;
                    smokeEffect = none;

                    status = StatusEffects.corroded;
                    statusDuration = 60f;
                    splashDamage = 30;
                    trailInterval = 4;
                }};
            }});
        }};
        cereth = new UnitType("cereth"){{
            aiController = SuicideAI::new;
            constructor = CrawlUnit::create;

            speed = 0.8f;
            rotateSpeed = 2;
            health = 540;
            hitSize = 6;
            armor = 2;
            outlineColor = ArikothLifeformPal.caeritesOutline;
            crushDamage = 0.1f;

            segments = 3;
            segmentScl = 3;
            segmentPhase = 5;
            segmentMag = 0.3f;
            targetFlags = new BlockFlag[]{BlockFlag.generator, BlockFlag.reactor, BlockFlag.battery, null};
            drawBody = false;
            drawCell = false;
            createScorch = false;
            deathSound = Sounds.none;
            deathExplosionEffect = ArikothFx.cerethDeathSmoke;
            abilities.add(new LiquidExplodeAbility(){{
                liquid = ArikothLiquids.cerethGoop;
                amount = 100;
            }});
        }};
    }
}
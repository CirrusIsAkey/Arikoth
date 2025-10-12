package arikoth.content.otherPlanets;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arikoth.palettes.VanillaPal;
import arikoth.world.entities.bullets.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.randLenVectors;
import static mindustry.content.Fx.*;
import static mindustry.gen.Sounds.*;

public class SerpuloUnits {

    public static UnitType
    //Assault
    mar, assault,
    //special
    nucleon;
    public static void load() {
        mar = new UnitType("mar"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = Pal.darkOutline;
            constructor = MechUnit::create;
            speed = 0.7f;
            hitSize = 12f;
            mechStepParticles = true;
            health = 150;
            armor = 2;
            weapons.add(new Weapon(name + "-weapon"){{
                reload = 40;
                x = 18f / 4;
                y = 0f;
                shootY = 10;
                top = false;
                shootSound = Sounds.shoot;
                ejectEffect = casing1;
                shoot.shots = 4;
                shoot.shotDelay = 4;
                inaccuracy = 5;
                velocityRnd = 0.2f;
                bullet = new BasicBulletType(6, 10){{
                    speed = 6;
                    lifeScaleRandMin = 0.9f;
                    lifeScaleRandMax = 1.2f;
                    damage = 30;
                    width = 8;
                    height = 12;
                    trailWidth = 2;
                    trailLength = 10;
                    trailColor = backColor = hitColor = lightColor = VanillaPal.assaultPurple;
                    frontColor = Color.white;
                    lifetime = 15;
                    hitSound = Sounds.none;
                    shootEffect = shootSmallColor;
                    smokeEffect = shootBigSmoke;
                    hitEffect = hitBulletColor;
                    despawnEffect = Fx.none;
                    despawnHit = true;
                }};
            }});
        }};

        assault = new UnitType("assault"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = Pal.darkOutline;
            constructor = MechUnit::create;
            speed = 0.5f;
            hitSize = 18f;
            mechStepParticles = true;
            health = 760;
            armor = 6;
            weapons.add(new Weapon(name + "-launcher"){{
                reload = 40;
                x = 36f / 4;
                y = -2f;
                shootY = 6;
                top = false;
                shootSound = missile;
                ejectEffect = Fx.none;
                shoot.shots = 2;
                shoot.shotDelay = 4;
                inaccuracy = 5;
                layerOffset = 0.001f;
                bullet = new MissileBulletType(4, 30, "arikoth-rocket"){{
                    speed = 6;
                    weaveScale = 4;
                    weaveMag = 2;
                    damage = 30;
                    width = 12;
                    height = 14;
                    splashDamage = 30;
                    splashDamageRadius = 16;
                    trailWidth = 2.5f;
                    trailLength = 12;
                    trailColor = backColor = hitColor = lightColor = VanillaPal.assaultPurple;
                    frontColor = Color.white;
                    lifetime = 30;
                    hitSound = Sounds.none;
                    shootEffect = shootSmallColor;
                    smokeEffect = shootBigSmoke;
                    hitEffect = hitBulletColor;
                    despawnEffect = Fx.none;
                    despawnHit = true;
                }};
            }});
        }};

        nucleon = new UnitType("nucleon"){{
            researchCostMultiplier = 0.5f;
            outlineColor = mechLegColor = Pal.darkOutline;
            constructor = MechUnit::create;
            speed = 1.2f;
            hitSize = 12f;
            mechStepParticles = true;
            health = 220;
            armor = 4;
            weapons.add(new Weapon(name + "-launcher"){{
                        reload = 155;
                        shoot = new ShootSpread(4, 90) {{
                            firstShotDelay = 60;
                        }};
                        shootStatus = StatusEffects.unmoving;
                        shootStatusDuration = 680.0F;
                        x = y = 0.0f;
                        shootY = 0f;
                        continuous = parentizeEffects = true;
                        mirror = alternate = false;
                        shootCone = 360;
                        chargeSound = Sounds.lasercharge2;
                        shootSound = Sounds.laser;
                        bullet = new LaserBulletType(90){{
                                colors = new Color[]{VanillaPal.specialistRed.a(0.5f), VanillaPal.specialistRed, Color.white};
                                length = 90.0f;
                                width = 28.0f;
                                chargeEffect = new Effect(60f, 100f, e -> {
                                    color(VanillaPal.specialistRed);
                                    stroke(e.fin() * 2f);
                                    Lines.poly(e.x, e.y, 4,4f + e.fout() * 20f);

                                    Fill.poly(e.x, e.y, 4, e.fin() * 7.5f);

                                    randLenVectors(e.id, 20, 20f * e.fout(), (x, y) -> {
                                        Fill.poly(e.x + x, e.y + y, 4, e.fin() * 2f);
                                        Drawf.light(e.x + x, e.y + y, e.fin() * 2f, VanillaPal.specialistRed, 0.7f);
                                    });

                                    color();

                                    Fill.poly(e.x, e.y, 4,e.fin() * 5);
                                    Drawf.light(e.x, e.y, e.fin() * 5f, e.color, 0.7f);
                                }).followParent(true).rotWithParent(true);
                                lengthFalloff = 0.6f;
                                sideLength = 90.0f;
                                sideWidth = 1.35f;
                                sideAngle = 40.0f;
                                lightningSpacing = 40.0f;
                                lightningLength = 2;
                                lightningDelay = 1.1f;
                                lightningLengthRand = 10;
                                lightningDamage = damage / 5.0f;
                                lightningAngleRand = 40.0f;
                                hitColor = lightningColor = VanillaPal.specialistRed;
                                smokeEffect = shootEffect = Fx.none;
                                hitEffect = hitBeam;
                                lifetime = 30.0F;
                                status = VanillaStatusEffects.blight;
                                statusDuration = 60.0F;
                                killShooter = true;
                                keepVelocity = false;
                                fragBullets = 1;
                                fragBullet = new AftershockBulletType(20, 32){{
                                    splashAmount = 3;
                                    splashDelay = 10;
                                    applySound = boom;
                                    frontColor = Color.white;
                                    backColor = VanillaPal.specialistRed;
                                    particleColor = backColor;
                                }};
                            }

                            @Override
                            public void init(Bullet b) {
                                super.init(b);

                                if (killShooter && b.owner() instanceof Healthc) {
                                    ((Healthc) b.owner()).kill();
                                }
                            }
                        };
            }});
        }};
    }
}
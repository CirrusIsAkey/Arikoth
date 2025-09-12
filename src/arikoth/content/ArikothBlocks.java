package arikoth.content;

import arc.graphics.*;
import arc.graphics.g2d.Fill;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.Seq;
import arikoth.palettes.ArikothTurretPal;
import arikoth.palettes.ArikothUnitPal;
import arikoth.world.blocks.*;
import arikoth.world.blocks.env.ACliff;
import arikoth.world.blocks.env.ACliffHelper;
import arikoth.world.blocks.env.AFloor;
import arikoth.world.drawers.DrawPolyFlame;
import arikoth.world.drawers.DrawPolySmelt;
import arikoth.world.drawers.DrawWeaveModif;
import arikoth.world.meta.ArikothAttribute;
import arikoth.world.meta.ArikothEnv;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.*;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootSpread;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.RegenProjector;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.*;
import arikoth.world.blocks.env.SmallSteamVent;
import mindustry.world.blocks.heat.HeatConductor;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BlockGroup;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;
import static arikoth.graphics.ArikothShaders.*;
import static mindustry.type.Category.*;
import static mindustry.type.ItemStack.with;

public class ArikothBlocks {
    public static Block
            //cliff
            Acliff, Mcliff, AcliffHelper, hitsquare,
            //env
            duneSand, duneSandWall, duneSandMeld,
            sandstone, sandstoneWall, sandstoneMeld,
            weatheredRhenium, weatheredRheniumPlate, weatheredRheniumWall, weatheredRheniumOre, weatheredRheniumVent, weatheredRheniumVentSmall, weatheredRheniumMeld,
            weatheredNickel, weatheredNickelSlate, weatheredNickelWall, pureNickelWall, weatheredNickelMeld,
            // mercury
            mercuricRock, mercuricRockWall, mercuricCrystal, mercuricVent, greystone, greystoneWall,
            // crimson
            crimsonRegolith, crimsonRegolithWall, crimsonRegolithVent, stronticRock, stronticRockWall, stronticRockVent, terrastone, terrastoneWall, redsand, redsandWall, terracotta, terracottaWall,
            // liquids
            quicksandTile, mercurySludge,
            //ores
            strontiumOre, quartzOre,
            //props
            aereniteAmalgamLarge, mercuricCrystals,
            //turrets
            puncture, calefex, aftershock, termor, glint, longsword, augment, pyroclast,
            //drills
            plasmaDriller, advPlasmaDriller, impactQuarry, icherExtractor,
            //dist
            vaccumShaftOverpass, vaccumShaft, vaccumShaftRouter, overflowSwitch, underflowSwitch,
            //liq
            fluidChannel, fluidChannelRouter, fluidChannelOverpass, hydraulicPump,
            //power
            ventTurbine, helioPanel, teslaNode, advancedTeslaNode, strontiumGen,
            //def
            nickelWall, nickelWallLarge, amalgamWall, amalgamWallLarge,
            //craft
            mercurySynthesizer, amalgamFoundry, tyriumWeaver, nitrogenConcentrator, thermolysisCell, metalloidRefinery, explosivesMixer, burnerHeater, heatConductor,
            //units
            assaultForge, assaultReforger,
            //utilities
            coreSerenity, mendField;
    public static void load(){
        Acliff = new ACliff("cliff");
        Mcliff = new ACliff("metal-cliff");
        AcliffHelper = new ACliffHelper("cliff-helper");
        hitsquare = new StaticWall("hitbox"){{
            variants = 0;
        }};

        duneSand = new AFloor("aerna-sand"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#9a7f5c");
            cliffLightColor = Color.valueOf("#fadab2");
            itemDrop = Items.sand;
        }};
        duneSandWall = new StaticWall("aerna-sand-wall"){{
            variants = 3;
        }};
        duneSandMeld = new Prop("aerna-sand-meld"){{
            variants = 2;
        }};
        sandstone = new AFloor("sandstone"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#644c34");
            cliffLightColor = Color.valueOf("#d4b67f");
        }};
        sandstoneWall = new StaticWall("sandstone-wall"){{
            variants = 3;
        }};
        sandstoneMeld = new Prop("sandstone-meld"){{
            variants = 2;
        }};
        weatheredNickel = new AFloor("weathered-nickel"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#37312e");
            cliffLightColor = Color.valueOf("#918a84");
        }};
        weatheredNickelSlate = new AFloor("weathered-nickel-plate"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#37312e");
            cliffLightColor = Color.valueOf("#918a84");
            attributes.set(ArikothAttribute.slag, 1);
        }};
        weatheredNickelWall = new StaticTree("weathered-nickel-wall"){{
            variants = 4;
        }};
        pureNickelWall = new StaticTree("pure-nickel-wall"){{
            variants = 3;
            itemDrop = ArikothItems.nickel;
        }};
        weatheredNickelMeld = new Prop("weathered-nickel-meld"){{
            variants = 2;
        }};
        weatheredRhenium = new AFloor("weathered-rhenium"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#5e5541");
            cliffLightColor = Color.valueOf("#d4cda1");
        }};
        weatheredRheniumPlate = new AFloor("weathered-rhenium-plate"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#5e5541");
            cliffLightColor = Color.valueOf("#d4cda1");
        }};
        weatheredRheniumWall = new StaticTree("weathered-rhenium-wall"){{
            variants = 5;
        }};
        weatheredRheniumOre = new StaticTree("weathered-rhenium-ore"){{
            variants = 3;
            itemDrop = ArikothItems.rhenium;
        }};
        weatheredRheniumVent = new SteamVent("weathered-rhenium-vent"){{
            variants = 2;
            effect = Fx.ventSteam;
            effectColor = Color.valueOf("#807357");
            attributes.set(Attribute.steam, 1f);
            parent = ArikothBlocks.weatheredRhenium;
            blendGroup = ArikothBlocks.weatheredRhenium;
        }};
        weatheredRheniumVentSmall = new SmallSteamVent("weathered-rhenium-vent-small"){{
            variants = 2;
            effect = Fx.ventSteam;
            effectColor = Color.valueOf("#807357");
            attributes.set(Attribute.steam, 1f);
            effectSpacing = 15f;
            effectChance = 0.05f;
            blendGroup = ArikothBlocks.weatheredRhenium;
        }};
        weatheredRheniumMeld = new Prop("weathered-rhenium-meld"){{
            variants = 2;
        }};
        // // // // // // // // // // // // // //
        mercuricRock = new AFloor("mercuric-stone"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#3f4049");
            cliffLightColor = Color.valueOf("#aaabb5");
        }};
        mercuricRockWall = new StaticWall("mercuric-wall"){{
            variants = 3;
        }};
        mercuricCrystal = new AFloor("crystalline-mercury"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#575e74");
            cliffLightColor = Color.valueOf("#aab7bd");
        }};
        mercuricVent = new SteamVent("mercuric-vent"){{
            variants = 2;
            effect = Fx.ventSteam;
            effectColor = Color.valueOf("#1a1a20");
            attributes.set(ArikothAttribute.mercuric, 1f);
            parent = ArikothBlocks.mercuricRock;
            blendGroup = ArikothBlocks.mercuricRock;
        }};
        greystone = new AFloor("greystone"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#3f3f47");
            cliffLightColor = Color.valueOf("#aaabb5");
        }};
        greystoneWall = new StaticWall("greystone-wall"){{
            variants = 3;
        }};
        // // // // // // // // // // // // // // //
        crimsonRegolith = new AFloor("crimson-regolith"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#381818");
            cliffLightColor = Color.valueOf("#9e5555");
        }};
        crimsonRegolithWall = new StaticWall("crimson-regolith-wall"){{
            variants = 3;
        }};
        crimsonRegolithVent = new SteamVent("crimson-regolith-vent"){{
            variants = 2;
            effect = Fx.ventSteam;
            effectColor = Color.valueOf("#753737");
            attributes.set(Attribute.steam, 1f);
            parent = ArikothBlocks.crimsonRegolith;
            blendGroup = ArikothBlocks.crimsonRegolith;
        }};
        stronticRock = new AFloor("strontic-rock"){{
            variants = 3;
            cliffDarkColor = Color.valueOf("#351b1b");
            cliffLightColor = Color.valueOf("#a3766a");
        }};
        stronticRockWall = new StaticWall("strontic-rock-wall"){{
            variants = 3;
        }};
        stronticRockVent = new SteamVent("strontic-rock-vent"){{
            variants = 2;
            effect = Fx.ventSteam;
            effectColor = Color.valueOf("#753737");
            attributes.set(Attribute.steam, 1f);
            parent = ArikothBlocks.stronticRock;
            blendGroup = ArikothBlocks.stronticRock;
        }};
        terrastone = new AFloor("terrastone"){{
            variants = 4;
            cliffDarkColor = Color.valueOf("#5e312a");
            cliffLightColor = Color.valueOf("#c98479");
        }};
        terrastoneWall = new StaticWall("terrastone-wall"){{
            variants = 3;
        }};
        redsand = new AFloor("redsand"){{
            variants = 3;
            cliffDarkColor = Color.valueOf("#5e2e20");
            cliffLightColor = Color.valueOf("#c77c66");
        }};
        redsandWall = new StaticWall("redsand-wall"){{
            variants = 3;
        }};
        terracotta = new AFloor("terracotta"){{
            variants = 3;
            cliffDarkColor = Color.valueOf("#602e24");
            cliffLightColor = Color.valueOf("#d68574");
        }};
        terracottaWall = new StaticWall("terracotta-wall"){{
            variants = 3;
        }};
        quicksandTile = new AFloor("aerna-quicksand"){{
            variants = 4;
            drownTime = 300;
            speedMultiplier = 0.3f;
            liquidDrop = ArikothLiquids.liquidQuickSand;
            cacheLayer = aernaQuicksandLayer;
            //status = ;
            isLiquid = true;
            shallow = false;
        }};
        mercurySludge = new AFloor("mercuric-sludge"){{
            variants = 0;
            drownTime = 180;
            speedMultiplier = 0.6f;
            liquidDrop = ArikothLiquids.mercury;
            cacheLayer = moltenSaltLayer;
            //status = ;
            isLiquid = true;
            shallow = false;
        }};
        //props
        mercuricCrystals = new TallBlock("mercury-crystals"){{
            variants = 2;
            clipSize = 128;
            shadowAlpha = 0.6f;
            shadowOffset = -2.5f;
            solid = false;
        }};
        //ore
        strontiumOre = new OreBlock("strontium-ore"){{
            variants = 3;
            itemDrop = ArikothItems.strontium;
        }};
        quartzOre = new OreBlock("quartz-ore"){{
            variants = 3;
            itemDrop = ArikothItems.quartz;
        }};
        //turrets
        puncture = new ItemTurret("puncture"){{
            requirements(Category.turret, with(ArikothItems.rhenium, 40));
            size = 2;
            health = 600;
            squareSprite = false;
            shootSound = Sounds.shootAlt;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 200;
            reload = 30;
            shootY = 2;
            recoil = 1.5f;
            velocityRnd = 0;
            inaccuracy = 0;
            ammo(
                    ArikothItems.rhenium, new BasicBulletType(){{
                        collidesAir = true;
                        width = 12.5f;
                        height = 18;
                        speed = 8;
                        damage = 40;
                        pierceCap = 2;
                        lifetime = 25f;
                        hitColor = backColor = trailColor = ArikothTurretPal.rheniumLight;
                        frontColor = Color.white;
                        trailWidth = 2.5f;
                        trailLength = 8;
                        shootEffect = Fx.colorSparkBig;
                        smokeEffect = Fx.none;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailEffect = Fx.none;
                        trailInterval = 2;
                        trailRotation = true;
                    }},
                    ArikothItems.amalgam, new BasicBulletType(){{
                        collidesAir = true;
                        width = 12.5f;
                        height = 24;
                        speed = 8;
                        damage = 40;
                        pierceCap = 2;
                        lifetime = 30f; rangeChange = 40;
                        hitColor = backColor = trailColor = ArikothTurretPal.amalgamLight;
                        frontColor = Color.white;
                        trailWidth = 2.5f;
                        trailLength = 8;
                        shootEffect = Fx.colorSparkBig;
                        smokeEffect = Fx.none;
                        hitEffect = despawnEffect = Fx.hitBulletColor;
                        trailEffect = Fx.disperseTrail;
                        trailInterval = 4;
                        trailRotation = true;
                    }}
            );
            drawer = new DrawTurret("fortified-");
        }};
        calefex = new ItemTurret("calefex"){{
            requirements(Category.turret, with(ArikothItems.nickel, 24, ArikothItems.strontium, 20));
            size = 2;
            health = 600;
            squareSprite = false;
            shootSound = Sounds.bang;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = false;
            targetGround = true;
            range = 180;
            reload = 80;
            shootY = 4;
            recoil = 1.5f;
            velocityRnd = 0.1f;
            inaccuracy = 2;
            shoot.shots = 3;
            shoot.shotDelay = 8;
            minRange = 40;
            fogRadius = 40;
            drawMinRange = true;
            ammo(
                    ArikothItems.strontium, new ArtilleryBulletType(){{
                        collidesAir = false;
                        width = 12;
                        height = 16;
                        speed = 2;
                        shrinkY = 0.2f;
                        trailInterp = Interp.slope;
                        splashDamage = 40;
                        splashDamageRadius = 16;
                        status = StatusEffects.burning;
                        lifetime = 90;
                        hitColor = backColor = trailColor = ArikothTurretPal.strontiumTrail;
                        frontColor = ArikothTurretPal.strontiumLight;
                        shootEffect = Fx.shootBigColor;
                        smokeEffect = Fx.shootBigSmoke;
                        trailEffect = Fx.artilleryTrail;
                        hitEffect = despawnEffect = new MultiEffect(
                           ArikothFx.calefexSmoke,
                           ArikothFx.calefexExplosion
                        );
                        trailInterval = 4;
                    }},
                    Items.blastCompound, new ArtilleryBulletType(){{
                        collidesAir = false;
                        width = 12;
                        height = 16;
                        speed = 2;
                        shrinkY = 0.2f;
                        trailInterp = Interp.slope;
                        splashDamage = 80;
                        splashDamageRadius = 36;
                        status = StatusEffects.blasted;
                        inaccuracy = 6;
                        lifetime = 120;
                        rangeChange = 60;
                        reloadMultiplier = 0.5f;
                        hitColor = backColor = trailColor = Pal.blastAmmoBack;
                        frontColor = Pal.blastAmmoFront;
                        shootEffect = Fx.shootBigColor;
                        smokeEffect = Fx.shootBigSmoke;
                        trailEffect = Fx.artilleryTrail;
                        hitEffect = despawnEffect = new MultiEffect(
                                ArikothFx.calefexSmokeLarge,
                                ArikothFx.calefexExplosionLarge
                        );
                        trailInterval = 4;
                    }}
            );
            drawer = new DrawTurret("fortified-");
        }};
        aftershock = new ItemTurret("aftershock"){{
            requirements(Category.turret, with(ArikothItems.rhenium, 32, ArikothItems.strontium, 20));
            size = 2;
            health = 600;
            squareSprite = false;
            shootSound = Sounds.boom;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = false;
            targetGround = true;
            range = 96;
            reload = 80;
            shootY = 0;
            recoil = 0;
            velocityRnd = 0.1f;
            inaccuracy = 0;
            shake = 6;
            shoot.firstShotDelay = 60;
            ammoPerShot = 5;
            ammo(
                    ArikothItems.strontium, new ExplosionBulletType(80, 96){{;
                        killShooter = false;
                        collidesAir = false;
                        shootEffect = new MultiEffect(
                        new ParticleEffect(){{
                            sizeFrom = 4;
                            sizeTo = 0;
                            interp = Interp.pow10Out;
                            sizeInterp = Interp.pow5Out;
                            colorFrom = ArikothTurretPal.strontiumLight;
                            colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                            length = 10;
                            baseLength = 96;
                            particles = 18;
                        }},
                        new WaveEffect(){{
                            sizeFrom = 96;
                            sizeTo = 96;
                            strokeFrom = 4;
                            colorFrom = ArikothTurretPal.strontiumLight;
                            colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                        }},
                        new ParticleEffect(){{
                            strokeFrom = 4;
                            lenFrom = 7;
                            interp = Interp.circleOut;
                            line = true;
                            cone = 30;
                            baseRotation = 180;
                            colorFrom = ArikothTurretPal.strontiumLight;
                            colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                            length = 10;
                            baseLength = 96;
                            particles = 18;
                        }},
                        new ParticleEffect(){{
                            strokeFrom = 4;
                            lenFrom = 7;
                            interp = Interp.circleOut;
                            line = true;
                            cone = 30;
                            baseRotation = 0;
                            colorFrom = ArikothTurretPal.strontiumLight;
                            colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                            length = 10;
                            baseLength = 96;
                            particles = 18;
                        }}
                        );
                    }}
            );
            drawer = new DrawTurret("fortified-"){{
                parts.addAll(
                        new RegionPart("-inside"){{
                            moveX = 4;
                            under = true;
                            progress = PartProgress.charge.curve(Interp.pow2Out);
                            mirror = true;
                        }}
                );
            }};
        }};
        termor = new ItemTurret("termor"){{
            requirements(Category.turret, with(ArikothItems.rhenium, 32, ArikothItems.strontium, 20));
            size = 3;
            health = 900;
            squareSprite = false;
            shootSound = Sounds.titanExplosion;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = false;
            targetGround = true;
            range = 160;
            reload = 120;
            shootY = 0;
            recoil = 0;
            velocityRnd = 0.1f;
            inaccuracy = 0;
            shake = 8;
            shoot.firstShotDelay = 90;
            ammoPerShot = 10;
            ammo(
                    ArikothItems.strontium, new ExplosionBulletType(120, 160){{;
                        killShooter = false;
                        collidesAir = false;
                        shootEffect = new MultiEffect(
                                new ParticleEffect(){{
                                    sizeFrom = 4;
                                    sizeTo = 0;
                                    interp = Interp.pow10Out;
                                    sizeInterp = Interp.pow5Out;
                                    colorFrom = ArikothTurretPal.strontiumLight;
                                    colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                                    length = 10;
                                    baseLength = 160;
                                    particles = 26;
                                }},
                                new WaveEffect(){{
                                    sizeFrom = 160;
                                    sizeTo = 160;
                                    strokeFrom = 4;
                                    lifetime = 35;
                                    colorFrom = ArikothTurretPal.strontiumLight;
                                    colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                                }},
                                new ParticleEffect(){{
                                    strokeFrom = 4;
                                    lenFrom = 7;
                                    interp = Interp.circleOut;
                                    line = true;
                                    cone = 30;
                                    baseRotation = 180;
                                    colorFrom = ArikothTurretPal.strontiumLight;
                                    colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                                    length = 10;
                                    baseLength = 160;
                                    particles = 18;
                                }},
                                new ParticleEffect(){{
                                    strokeFrom = 4;
                                    lenFrom = 7;
                                    interp = Interp.circleOut;
                                    line = true;
                                    cone = 30;
                                    baseRotation = 0;
                                    colorFrom = ArikothTurretPal.strontiumLight;
                                    colorTo = ArikothTurretPal.strontiumTrail.a(0.5f);
                                    length = 10;
                                    baseLength = 160;
                                    particles = 18;
                                }},
                                ArikothFx.calefexExplosionLarge,
                                new Effect(35f, 224f, e -> {
                                    float circleRad = 6f + e.finpow() * 140f;

                                    color(ArikothTurretPal.strontiumLight, e.foutpow());
                                    Fill.circle(e.x, e.y, circleRad);
                                }).layer(Layer.bullet + 2f)
                        );
                    }}
            );
            drawer = new DrawTurret("fortified-"){{
                parts.addAll(
                        new RegionPart("-insider"){{
                            moveX = 6;
                            under = true;
                            progress = PartProgress.charge.curve(Interp.pow2Out);
                            mirror = true;
                        }},
                        new RegionPart("-inside"){{
                            moveX = 6;
                            under = true;
                            progress = PartProgress.charge.curve(Interp.pow2Out).delay(0.4f);
                            mirror = true;
                        }}
                );
            }};
        }};
        glint = new ItemTurret("glint"){{
            requirements(Category.turret, with(ArikothItems.amalgam, 60, ArikothItems.nickel, 45,  ArikothItems.quartz, 20));
            size = 3;
            health = 900;
            squareSprite = false;
            shootSound = Sounds.railgun;
            soundPitchMax = 3.2f;
            soundPitchMin = 2.8f;
            minWarmup = 0.95f;
            shootWarmupSpeed = 0.08f;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 270;
            reload = 100;
            shootY = 4;
            recoil = 2f;
            velocityRnd = 0;
            inaccuracy = 1;
            shoot.shotDelay = 60;
            consumePower(1.5f);
            ammo(
                    ArikothItems.quartz, new BasicBulletType(){{
                        sprite = "arikoth-rocket";
                        width = 12;
                        height = 18;
                        speed = 6;
                        lightningDamage = 10;
                        lightningCone = 10;
                        lightningLength = 8;
                        lightningLengthRand = 4;
                        lightningColor = ArikothTurretPal.quartzLight;
                        lightning = 4;
                        damage = 80;
                        lifetime = 30;
                        collidesGround = true;
                        collidesAir = true;
                        hitColor = backColor = trailColor = ArikothTurretPal.quartzTrail;
                        frontColor = ArikothTurretPal.quartzLight;
                        trailWidth = 2.5f;
                        trailLength = 12;
                        shootEffect = Fx.colorSparkBig;
                        smokeEffect = Fx.shootSmokeSquareSparse;
                        trailChance = 1;
                        trailEffect = Fx.disperseTrail;
                        trailInterval = 3;
                        trailRotation = true;
                    }}
            );
            drawer = new DrawTurret("fortified-"){{
                parts.addAll(
                new RegionPart("-wing"){{
                    progress = PartProgress.warmup;
                    mirror = false;
                    moveRot = 0;
                    x = 0;
                    y = 0;
                    moveX = 0;
                    moveY = -2;
                    under = false;
                    heatColor = Pal.turretHeat;
                }},
                new RegionPart("-sink"){{
                    progress = PartProgress.warmup;
                    mirror = true;
                    moveRot = 0;
                    x = 0;
                    y = 0;
                    moveX = 3;
                    moveY = -3;
                    under = true;
                    layerOffset = -0.0002f;
                    heatLayerOffset = -0.0001f;
                    heatColor = Pal.turretHeat;
                }}
                );
            }};
        }};
        longsword = new ItemTurret("longsword"){{
            requirements(Category.turret, with(ArikothItems.rhenium, 62, ArikothItems.amalgam, 80, ArikothItems.nickel, 124));
            size = 3;
            health = 900;
            squareSprite = false;
            shootSound = ArikothSounds.sniperShot;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 380;
            reload = 240;
            shootY = 4;
            recoil = 1.5f;
            shake = 4;
            velocityRnd = 0;
            inaccuracy = 0;
            ammoUseEffect = Fx.casing2Double;
            ammoEjectBack = 2;
            ammo(
                    ArikothItems.amalgam, new BasicBulletType(){{
                        collidesAir = true;
                        despawnShake = 4;
                        width = 18;
                        height = 24;
                        speed = 8;
                        damage = 90;
                        knockback = 4;
                        pierceCap = 10;
                        lifetime = 30;
                        hitColor = backColor = trailColor = ArikothTurretPal.amalgamLight;
                        frontColor = ArikothTurretPal.amalgamLight;
                        trailWidth = 3.5f;
                        trailLength = 12;
                        fragBullets = 1; fragRandomSpread = 0; fragVelocityMax = 1; fragVelocityMin = 1;
                        fragBullet = new BasicBulletType(10, 100){{
                            trailEffect = Fx.shootSmokeSquareSparse;
                            hitColor = backColor = trailColor = ArikothTurretPal.amalgamLight;
                            frontColor = ArikothTurretPal.amalgamLight;
                            width = 12;
                            height = 24;
                            trailLength = 18;
                            trailWidth = 2.5f;
                            pierceCap = 12;
                            trailInterval = 3;
                            lifetime = 14;
                            despawnEffect = hitEffect = Fx.disperseTrail;
                            trailRotation = true;
                        }};
                        despawnEffect = hitEffect = new ParticleEffect(){{
                            lenFrom = 7;
                            strokeFrom = 4;
                            length = 30;
                            colorFrom = ArikothTurretPal.amalgamLight;
                            colorTo = ArikothTurretPal.amalgamTrail;
                            particles = 12;
                            interp = Interp.circleOut;
                            baseRotation = 180;
                            cone = 20;
                            line = true;
                        }};
                        shootEffect = Fx.shootBigColor;
                        smokeEffect = Fx.shootSmokeTitan;
                        trailEffect = Fx.disperseTrail;
                        trailRotation = true;
                        trailInterval = 4;
                    }}
            );
            drawer = new DrawTurret("fortified-"){{
                parts.addAll(
                        new RegionPart("-front"){{
                            progress = PartProgress.recoil;
                            mirror = false;
                            moveRot = 0;
                            x = 0;
                            y = 0f;
                            moveX = 0;
                            moveY = -4f;
                            under = true;
                            heatColor = Pal.turretHeat;
                        }});
            }};
        }};
        augment = new ItemTurret("augment"){{
            requirements(Category.turret, with(ArikothItems.amalgam, 124, ArikothItems.irium, 86, ArikothItems.nickel, 34, Items.blastCompound, 36));

            ammo(
                    //TODO 1 more ammo type, decide on base type
                    Items.blastCompound, new ArtilleryBulletType(2.5f, 40, "shell"){{
                        hitEffect = new MultiEffect(ArikothFx.augmentExplosion, ArikothFx.augmentExplosionWave);
                        despawnEffect = Fx.none;
                        lifetime = 140f;
                        height = 16f;
                        width = 14.2f;
                        splashDamageRadius = 64f;
                        splashDamage = 220;
                        backColor = hitColor = trailColor = Pal.blastAmmoBack;
                        frontColor = Pal.blastAmmoFront;
                        hitShake = 6;
                        hitSound = Sounds.titanExplosion;
                        ammoMultiplier = 1f;

                        status = StatusEffects.blasted;

                        trailLength = 32;
                        trailWidth = 2.64f;
                        trailSinScl = 3.5f;
                        trailSinMag = 1f;
                        trailEffect = Fx.none;
                        trailColor = backColor;
                        despawnShake = 8f;

                        shootEffect = Fx.shootTitan;
                        smokeEffect = Fx.shootSmokeTitan;

                        trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                        shrinkX = 0.2f;
                        shrinkY = 0.1f;
                        fragBullets = 8;
                        fragRandomSpread = 2;
                        fragSpread = 45;
                        fragLifeMin = 0.5f;
                        fragLifeMax = 1.2f;
                        fragBullet = new BasicBulletType(8, 20){{
                            backColor = hitColor = trailColor = Pal.blastAmmoBack;
                            frontColor = Pal.blastAmmoFront;
                            despawnEffect = hitEffect = Fx.blastExplosion;
                            lifetime = 10;
                            width = 8;
                            height = 16;
                            trailWidth = 2;
                            trailLength = 18;
                        }};
                    }}
            );

            targetAir = false;
            shake = 4f;
            recoil = 1f;
            reload = 60f * 2f;
            outlineColor = ArikothTurretPal.turretOutline;
            rotateSpeed = 1.5f;

            drawer = new DrawTurret("fortified-"){{
                parts.addAll(
                        new RegionPart("-barrel"){{
                            moveY = -5f;
                            heatColor = ArikothTurretPal.arikothTurretHeat;
                            mirror = false;
                            progress = PartProgress.recoil.curve(Interp.pow2In);
                        }},
                        new RegionPart("-side"){{
                            moveX = 2f;
                            moveY = -0.4f;
                            moveRot = -45f;
                            progress = PartProgress.warmup.curve(Interp.pow2Out);
                            under = true;
                            mirror = true;
                            heatColor = ArikothTurretPal.arikothTurretHeat;
                        }});
            }};

            shootWarmupSpeed = 0.05f;
            minWarmup = 0.95f;

            shootSound = Sounds.mediumCannon;

            consumeLiquid(ArikothLiquids.icher, 1f / 60f);

            range = 360f;
            size = 3;
        }};

        pyroclast = new LiquidTurret("pyroclast"){{
            requirements(Category.turret, with(ArikothItems.amalgam, 60, ArikothItems.nickel, 45,  ArikothItems.quartz, 20));
            size = 3;
            health = 900;
            squareSprite = false;
            shootSound = Sounds.cannon;
            minWarmup = 0.95f;
            shootWarmupSpeed = 0.08f;
            outlineColor = ArikothUnitPal.turretOutline;
            targetAir = true;
            targetGround = true;
            range = 160;
            reload = 120;
            shootY = 4;
            recoil = 2f;
            velocityRnd = 0;
            inaccuracy = 6;
            shoot = new ShootSpread(15, 1.5f);
            heatRequirement = 10;
            maxHeatEfficiency = 1.5f;
            loopSound = Sounds.smelter;
            loopSoundVolume = 0.09f;
            ammo(
                    Liquids.slag, new LiquidBulletType(Liquids.slag){{
                        lifetime = 40f;
                        smokeEffect = Fx.shootSmokeTitan;
                        shootEffect = Fx.shootPyraFlame;
                        speed = 4f;
                        knockback = 2f;
                        puddles = 3;
                        puddleLiquid = Liquids.slag;
                        puddleSize = 30;
                        puddleAmount = 4;
                        puddleRange = 5;
                        lifeScaleRandMax = 1.2f;
                        lifeScaleRandMin = 0.2f;
                        orbSize = 4f;
                        damage = 25f;
                        drag = 0.001f;
                        ammoMultiplier = 0.4f;
                        statusDuration = 60f * 6f;
                        hitEffect = new MultiEffect(ArikothFx.pyroclastSmoke, ArikothFx.largeFireHit);
                        hitColor = trailColor = Pal.slagOrange;
                        trailEffect = ArikothFx.largeFireballsmoke;
                        trailInterval = 4;
                        trailLength = 12;
                        trailWidth = 2.5f;
                    }}
            );
            drawer = new DrawTurret("fortified-"){{
                parts.addAll(
                        new RegionPart("-front"){{
                            progress = PartProgress.recoil;
                            mirror = false;
                            moveRot = 0;
                            x = 0;
                            y = 0;
                            moveX = 0;
                            moveY = -2;
                            under = true;
                            heatColor = Pal.turretHeat;
                        }},
                        new RegionPart("-cell"){{
                            progress = PartProgress.recoil;
                            mirror = true;
                            moveRot = -5;
                            x = 0;
                            y = 0;
                            moveX = -0.5f;
                            moveY = 0.5f;
                            under = true;
                            layerOffset = -0.0002f;
                            heatLayerOffset = -0.0001f;
                            heatColor = Pal.turretHeat;
                        }}
                );
            }};
        }};
        //drill
        plasmaDriller = new BeamDrill("plasma-driller"){{
            requirements(Category.production, with(ArikothItems.rhenium, 40));
            consumePower(0.25f);

            drillTime = 160f;
            health = 120;
            tier = 1;
            size = 2;
            range = 6;
            fogRadius = 3;
            researchCost = with(ArikothItems.rhenium, 30);

            consumeLiquid(ArikothLiquids.icher, 0.25f / 60f).boost();
        }};
        advPlasmaDriller = new BeamDrill("advanced-plasma-driller"){{
            requirements(Category.production, with(ArikothItems.rhenium, 90, ArikothItems.nickel, 45, ArikothItems.strontium, 30));
            consumePower(0.5f);

            drillTime = 100f;
            health = 120;
            tier = 1;
            size = 3;
            range = 6;
            fogRadius = 3;
            researchCostMultiplier = 10;

            consumeLiquid(ArikothLiquids.icher, 0.25f / 60f).boost();
        }};
        impactQuarry = new RotaryImpactDrill("ignition-drill"){{
            size = 2;
            health = 1000;
            requirements(Category.production, with(ArikothItems.rhenium, 20, ArikothItems.nickel, 42));
            researchCost = with(ArikothItems.rhenium, 20, ArikothItems.nickel, 42);
            itemCapacity = 8;
            arrows = 4;
            squareSprite = false;
            arrowColor = Color.valueOf("ff9a99");
            baseArrowColor = Color.valueOf("ffffff00");
            tier = 2;
            drillMultipliers.put(ArikothItems.quartz, 1.5f);
            drillEffect = new MultiEffect(
              new ParticleEffect(){{
                  particles = 18;
                  line = true;
                  lenFrom = 7;
                  lenTo = 0;
                  strokeFrom = 4;
                  strokeTo = 0;
                  length = 20;
                  colorTo = arrowColor;
                  cone = 360;
                  interp = Interp.circleOut;
                  lifetime = 30;
              }},
              new WaveEffect(){{
                  sizeTo = 20;
                  sizeFrom = 0;
                  strokeFrom = 4;
                  strokeTo = 0;
                  colorTo = arrowColor;
                  interp = Interp.circleOut;
                  lifetime = 30;
              }},
              new WaveEffect(){{
                  sizeTo = 40;
                  sizeFrom = 0;
                  strokeFrom = 4;
                  strokeTo = 0;
                  layer = Layer.debris;
                  colorTo = arrowColor;
                  interp = Interp.circleOut;
                  lifetime = 30;
              }}
            );

            consumePower(140 / 60);
            drillTime = 300;
            invertedTime = 100;
            hardnessDrillMultiplier = 1;
        }};

        icherExtractor = new AttributeCrafter("icher-extractor"){{
            requirements(Category.crafting, with(ArikothItems.nickel, 60, ArikothItems.strontium, 24));

            size = 3;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawPolyFlame(){{
                        poly = 4;
                    }},
                    new DrawDefault()
            );
            rotate = true;
            rotateDraw = false;

            baseEfficiency = 0;
            boostScale = 1/9f;
            minEfficiency = 1f;
            attribute = Attribute.get("steam");
            outputLiquids = LiquidStack.with(ArikothLiquids.icher, 30f / 60f / 9f);
            liquidOutputDirections = new int[]{1, 2};
            craftTime = 60;

            consumePower(2f);
        }};
        //dist
        vaccumShaft = new Duct("vaccum-shaft"){{
            requirements(Category.distribution, with(ArikothItems.rhenium, 1));
            health = 100;
            speed = 5f;
            researchCost = with(ArikothItems.rhenium, 1);
            bridgeReplacement = ArikothBlocks.vaccumShaftOverpass;
        }};
        vaccumShaftRouter = new DuctRouter("vaccum-shaft-router"){{
            requirements(Category.distribution, with(ArikothItems.rhenium, 10));
            health = 120;
            speed = 5f;
            regionRotated1 = 1;
            solid = false;
            researchCost = with(ArikothItems.rhenium, 30);
        }};
        vaccumShaftOverpass = new DuctBridge("vaccum-shaft-overpass"){{
            requirements(Category.distribution, with(ArikothItems.rhenium, 15, ArikothItems.nickel, 6));
            health = 120;
            speed = 4f;
            range = 6;
            researchCostMultiplier = 2f;
        }};
        overflowSwitch = new OverflowGate("overflow-switch"){{
            requirements(Category.distribution, with(ArikothItems.rhenium, 5, ArikothItems.nickel, 5));
            health = 120;
            speed = 5f;
            researchCost = with(ArikothItems.rhenium, 15, ArikothItems.nickel, 15);
        }};
        underflowSwitch = new OverflowGate("underflow-switch"){{
            requirements(Category.distribution, with(ArikothItems.rhenium, 5, ArikothItems.nickel, 5));
            health = 120;
            invert = true;
            speed = 5f;
            researchCost = with(ArikothItems.rhenium, 15, ArikothItems.nickel, 15);
        }};
        //liq
        fluidChannel = new Conduit("fluid-channel"){{
            requirements(liquid, with(ArikothItems.nickel, 1));
            health = 100;
            botColor = Color.valueOf("1f1917");
            rotBridgeReplacement = ArikothBlocks.fluidChannelOverpass;
        }};
        fluidChannelRouter = new LiquidRouter("fluid-channel-router"){{
            requirements(liquid, with(ArikothItems.nickel, 6));
            health = 100;
            liquidCapacity = 20;
            squareSprite = false;
            liquidPadding = 0.5f;
        }};
        fluidChannelOverpass = new DirectionLiquidBridge("fluid-channel-overpass"){{
            requirements(liquid, with(ArikothItems.nickel, 20));
            health = 100;
            liquidCapacity = 10;
            range = 6;
            squareSprite = false;
        }};
        hydraulicPump = new Pump("hydraulic-pump"){{
            requirements(Category.liquid, with(ArikothItems.rhenium, 10, ArikothItems.strontium, 12));
            pumpAmount = 0.1f;
            consumePower(1.5f);
            liquidCapacity = 60f;
            hasPower = true;
            size = 2;
            drawer = new DrawMulti(
               new DrawDefault(),
               new DrawPistons(){{
                   sides = 4;
                   angleOffset = 45;
                   sinMag = 2;
                   sinScl = 4;
               }},
               new DrawLiquidTile(){{
                   drawLiquid = ArikothLiquids.moltenSalt;
                   alpha = 0.5f;
               }},
               new DrawRegion("-top"),
               new DrawPumpLiquid()
            );
        }};
        //power
        ventTurbine = new ThermalGenerator("vent-turbine"){{
            requirements(Category.power, with(ArikothItems.rhenium, 40));
            attribute = Attribute.steam;
            group = BlockGroup.liquids;
            displayEfficiencyScale = 1f / 9f;
            minEfficiency = 9f - 0.0001f;
            powerProduction = 3f / 9f;
            displayEfficiency = false;
            generateEffect = Fx.turbinegenerate;
            effectChance = 0.04f;
            size = 3;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotor", 0.6f * 9f){{
                blurThresh = 0.01f;
            }},
            new DrawRegion("-top")
            );

            hasLiquids = true;
            outputLiquid = new LiquidStack(ArikothLiquids.icher, 5f / 60f / 9f);
            liquidCapacity = 20f;
            fogRadius = 3;
            requirements(Category.power, with(ArikothItems.rhenium, 10));
        }};
        helioPanel = new SolarGenerator("helio-panel"){{
            requirements(Category.power, with(ArikothItems.rhenium, 10, ArikothItems.nickel, 8));
            powerProduction = 0.75f;
            size = 2;
        }};
        teslaNode = new BeamPylon("tesla-node"){{
            requirements(Category.power, with(ArikothItems.rhenium, 8));
            consumesPower = outputsPower = true;
            health = 90;
            range = 10;
            fogRadius = 1;
            buildCostMultiplier = 2.5f;
            consumePowerBuffered(1000f);
        }};
        advancedTeslaNode = new BeamPylon("advanced-tesla-node"){{
            requirements(Category.power, with(ArikothItems.rhenium, 60, ArikothItems.nickel, 60, ArikothItems.quartz, 120));
            consumesPower = outputsPower = true;
            health = 900;
            range = 20;
            size = 3;
            fogRadius = 1;
            buildCostMultiplier = 2.5f;
            consumePowerBuffered(1000f);
        }};
        strontiumGen = new ConsumeGenerator("strontium-burner"){{
            requirements(Category.power, with(Items.silicon, 40, ArikothItems.nickel, 50, ArikothItems.amalgam, 30));
            powerProduction = 500f / 60f;
            consumeItem(ArikothItems.strontium);
            consumeLiquid(ArikothLiquids.icher, 10 / 60f);
            itemDuration = 6 * 60;
            size = 3;
            drawer = new DrawMulti(
            new DrawRegion("-lower"),
            new DrawPistons(){{
               sinMag = 2.25f;
               sinScl = 3f;
                sideOffset = Mathf.PI / 2f;
            }},
            new DrawRegion("-mid"),
            new DrawLiquidTile(){{
                drawLiquid = ArikothLiquids.icher;
                padding = 36f / 4f;
            }},
            new DrawDefault(),
            new DrawGlowRegion(){{
                alpha = 0.5f;
                glowScale = 10f;
                color = Color.valueOf("ff0000");
            }}
            );
            generateEffect = Fx.none;

            itemCapacity = 10;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.06f;
        }};
        //def
        int ArikothWallMult = 4;
        nickelWall = new Wall("nickel-wall"){{
            requirements(Category.defense, with(ArikothItems.nickel, 8));
            health = 100 * ArikothWallMult;
            researchCostMultiplier = 1;
        }};
        nickelWallLarge = new Wall("nickel-wall-large"){{
            requirements(Category.defense, with(ArikothItems.nickel, 32));
            health = 100 * 4 * ArikothWallMult;
            researchCostMultiplier = 1;
            size = 2;
        }};
        amalgamWall = new Wall("amalgam-wall"){{
            requirements(Category.defense, with(ArikothItems.amalgam, 8));
            health = 170 * ArikothWallMult;
            researchCostMultiplier = 10;
            size = 1;
        }};
        amalgamWallLarge = new Wall("amalgam-wall-large"){{
            requirements(Category.defense, with(ArikothItems.amalgam, 32));
            health = 170 * 4 * ArikothWallMult;
            researchCostMultiplier = 1;
            size = 2;
            variants = 8;
        }};
        //craft
        amalgamFoundry = new GenericCrafter("amalgam-foundry"){{
            requirements(Category.crafting, with(ArikothItems.rhenium, 120, ArikothItems.nickel, 90));

            size = 3;
            squareSprite = false;

            itemCapacity = 40;
            craftTime = 60f * 3f;
            liquidCapacity = 20;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;

            consumeLiquid(ArikothLiquids.mercury, 0.2f);
            consumeItems(with(ArikothItems.nickel, 2));
            consumePower(120f / 60f);

            outputItem = new ItemStack(ArikothItems.amalgam, 2);

            craftEffect = Fx.none;

            drawer = new DrawMulti(
            new DrawRegion("-lower"),
            new DrawLiquidTile(ArikothLiquids.mercury){{
                padding = 3;
            }},
            new DrawCircles(){{
                color = Color.valueOf("cebee8").a(0.24f);
                strokeMax = 2.5f;
                radius = 10f;
                amount = 3;
            }},
            new DrawCrucibleFlame(){{
                flameColor = Color.valueOf("cebee8");
            }},
            new DrawDefault()
            );
        }};

        tyriumWeaver = new HeatCrafter("tyrium-weaver"){{
            requirements(Category.crafting, with(ArikothItems.rhenium, 120, ArikothItems.nickel, 90, Items.silicon, 60));

            size = 4;
            squareSprite = false;

            itemCapacity = 40;
            craftTime = 60f * 6f;
            liquidCapacity = 10;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.9f;

            consumeLiquid(ArikothLiquids.moltenSalt, 0.8f);
            consumeItems(with(ArikothItems.irium, 8, Items.thorium, 4));
            consumePower(720f / 60f);

            outputItem = new ItemStack(ArikothItems.tyrium, 8);

            craftEffect = Fx.none;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawMultiWeave(){{
                        weaveColor = Color.valueOf("417778");
                        rotateSpeed2 = -.5f;
                        rotateSpeed = .5f;
                        glowColor = weaveColor;
                    }},
                    new DrawWeaveModif(){{
                        color = Color.valueOf("cefff2");
                        line2Speed = line2AltSpeed = 5;
                    }},
                    new DrawDefault(),
                    new DrawGlowRegion("-glow"){{
                        color = Color.valueOf("8bbeb0");
                        glowScale = 10;
                        alpha = 0.5f;
                    }}
            );
        }};

        nitrogenConcentrator = new GenericCrafter("nitrogen-concentrator"){{
            requirements(Category.crafting, with(ArikothItems.rhenium, 120, ArikothItems.nickel, 90, Items.silicon, 60, ArikothItems.quartz, 120));

            size = 2;
            squareSprite = false;

            itemCapacity = 10;
            craftTime = 60f;
            liquidCapacity = 20;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;

            consumePower(60f / 60f);

            outputLiquid = new LiquidStack(Liquids.nitrogen, 2 / 60f);

            craftEffect = Fx.none;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawLiquidTile(Liquids.nitrogen){{
                        padding = 2;
                    }},
                    new DrawDefault(),
                    new DrawParticles(){{
                        poly = true;
                        sides = 4;
                        particles = 8;
                        particleSize = 2;
                        particleRad = 24;
                        rotateScl = 90;
                        particleLife = 90;
                        color = Pal.techBlue.a(0.5f);
                    }}
            );
        }};

        thermolysisCell = new AttributeCrafter("thermolytic-cell"){{
            requirements(Category.crafting, with(ArikothItems.rhenium, 90, ArikothItems.strontium, 64, ArikothItems.amalgam, 24));

            size = 3;
            squareSprite = false;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawPolySmelt(){{
                        poly = 4;
                    }},
                    new DrawDefault(),
                    new DrawLiquidOutputs()
            );
            rotate = true;
            rotateDraw = false;

            baseEfficiency = 0;
            boostScale = 1/9f;
            minEfficiency = 1f;
            attribute = Attribute.get("slag");
            outputLiquids = LiquidStack.with(Liquids.slag, 3/60f, Liquids.ozone, 2.5/60f);
            liquidOutputDirections = new int[]{1, 2};
            craftTime = 60;

            consumePower(2f);
        }};

        metalloidRefinery = new GenericCrafter("metalloid-refinery"){{
            requirements(Category.crafting, with(ArikothItems.rhenium, 120, ArikothItems.nickel, 90, Items.silicon, 60, ArikothItems.quartz, 120));

            size = 2;
            squareSprite = false;

            itemCapacity = 10;
            craftTime = 60f;
            liquidCapacity = 20;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.09f;

            consumeLiquids(LiquidStack.with(Liquids.slag, 1.5/60f, Liquids.nitrogen, 4/60f));
            consumePower(60f / 60f);

            outputItem = new ItemStack(ArikothItems.irium, 1);

            craftEffect = Fx.none;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawLiquidTile(Liquids.slag){{
                        padTop = 1;
                        padRight = 1;
                        padBottom = 1;
                        padLeft = 6;
                    }},
                    new DrawLiquidTile(Liquids.nitrogen){{
                        padTop = 1;
                        padRight = 6;
                        padBottom = 1;
                        padLeft = 1;
                    }},
                    new DrawDefault()
            );
        }};

        explosivesMixer = new GenericCrafter("explosives-mixer"){{
            requirements(Category.crafting, with(ArikothItems.rhenium, 120, ArikothItems.nickel, 90, Items.silicon, 60, ArikothItems.quartz, 120));

            size = 3;
            squareSprite = false;

            itemCapacity = 20;
            craftTime = 2 * 60f;
            liquidCapacity = 20;
            hasLiquids = true;
            hasItems = true;
            hasPower = true;

            ambientSound = Sounds.bioLoop;
            ambientSoundVolume = 0.09f;

            consumeLiquid(Liquids.ozone, 5 / 60f);
            consumeItems(ItemStack.with(Items.sand, 4, ArikothItems.strontium, 2));
            consumePower(342f / 60f);

            outputItem = new ItemStack(Items.blastCompound, 4);

            craftEffect = Fx.mineBig;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawLiquidTile(Liquids.ozone){{
                        padding = 3;
                    }},
                    new DrawPistons(){{
                        sides = 4;
                        sinMag = 1.2f;
                        sinScl = 2;
                        sideOffset = 10;
                        sinOffset = Mathf.halfPi;
                    }},
                    new DrawPistons(){{
                        sides = 4;
                        sinMag = 1.2f;
                        sinScl = 2;
                        sideOffset = 5;
                        sinOffset = Mathf.halfPi;
                        suffix = "-pistons";
                    }},
                    new DrawDefault(),
                    new DrawFlame(){{
                        flameColor = Items.blastCompound.color;
                        flameRadius = 4;
                    }}
            );
        }};

        burnerHeater = new HeatProducer("burner-heater"){{
            requirements(Category.crafting, with(ArikothItems.nickel, 40, Items.silicon, 20));

            researchCostMultiplier = 4f;

            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
            rotateDraw = false;
            hasItems = true;
            itemCapacity = 10;
            size = 2;
            heatOutput = 3f;
            regionRotated1 = 1;
            ambientSound = Sounds.hum;

            consume(new ConsumeItemFlammable());
        }};
        heatConductor = new HeatConductor("heat-conductor"){{
            requirements(Category.crafting, with(ArikothItems.nickel, 30, ArikothItems.rhenium, 10, Items.silicon, 20));

            researchCostMultiplier = 4f;

            group = BlockGroup.heat;
            size = 2;
            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput(), new DrawHeatInput("-heat"));
            regionRotated1 = 1;
            conductivePower = true;
            consumePower(5f / 60f);
        }};
        //units
        assaultForge = new UnitFactoryModif("assault-forge"){{
            requirements(Category.units, with(ArikothItems.nickel, 220, ArikothItems.strontium, 120));

            size = 3;
            configurable = true;
            plans = Seq.with(
                new UnitPlan(ArikothUnitTypes.kindle, 60f * 60f, with(ArikothItems.rhenium, 40, Items.silicon, 30)),
                new UnitPlan(ArikothUnitTypes.bolt, 60f * 60f, with(ArikothItems.nickel, 60, Items.silicon, 20))
            );
            regionSuffix = "-desert";
            fogRadius = 3;
            researchCostMultiplier = 1.5f;
            consumePower(320 / 60);
            color = ArikothUnitPal.assaultGold;
        }};
        assaultReforger = new UnitReforger("assault-reforger"){{
            requirements(Category.units, with(ArikothItems.amalgam, 130, Items.silicon, 120, ArikothItems.nickel, 220, ArikothItems.rhenium, 120));

            size = 4;
            consumePower(440 / 60);
            consumeItems(with(ArikothItems.amalgam, 60, ArikothItems.irium, 80));

            constructTime = 80f * 60f;

            upgrades.addAll(
                    new UnitType[]{ArikothUnitTypes.kindle, ArikothUnitTypes.calxel},
                    new UnitType[]{ArikothUnitTypes.bolt, ArikothUnitTypes.hasp}
            );
            color = ArikothUnitPal.assaultGold;
        }};
        //effect
        coreSerenity = new CoreBlock("core-serenity"){{
            requirements(effect, with(ArikothItems.rhenium, 1200, ArikothItems.nickel, 800));
            alwaysUnlocked = true;
            isFirstTier = true;
            unitType = ArikothUnitTypes.vision;
            health = 4500;
            armor = 5;
            itemCapacity = 2000;
            size = 4;
            buildCostMultiplier = 2f;
            squareSprite = false;
            unitCapModifier = 15;
            thrusterLength = 34/4f;
        }};
        mendField = new RegenProjector("mender-field"){{
            requirements(Category.effect, with(ArikothItems.rhenium, 45, ArikothItems.amalgam, 28));
            size = 3;
            range = 40;
            effect = ArikothFx.icherRegenParticle;
            baseColor = ArikothTurretPal.icherLight;

            consumePower(1f);
            consumeLiquid(ArikothLiquids.icher, 5f / 60f);

            healPercent = 1f / 60f;

            Color col = ArikothTurretPal.icherLight;

            drawer = new DrawMulti(new DrawRegion("-lower"), new DrawLiquidTile(ArikothLiquids.icher, 9f / 4f), new DrawDefault(), new DrawGlowRegion(){{
                color = col;
            }}, new DrawPulseShape(false){{
                layer = Layer.effect;
                color = col;
            }}, new DrawShape(){{
                layer = Layer.effect;
                radius = 2.5f;
                useWarmupRadius = true;
                timeScl = 4f;
                sides = 6;
                color = col;
            }});
        }};
        }
};
package arikoth.content.blocks;

import arc.graphics.Color;
import arc.math.Angles;
import arc.math.Interp;
import arikoth.content.RecipeRegister;
import arikoth.content.effects.palettes.ArikothPal;
import arikoth.world.blocks.crafting.RecipeCrafter;
import arikoth.world.drawers.DrawModParticles;
import arikoth.world.drawers.DrawModifCircles;
import arikoth.world.drawers.DrawModifCrucibleFlame;
import arikoth.world.drawers.DrawStartupRegion;
import mindustry.content.Liquids;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;

import static arikoth.content.ArikothLiquids.*;
import static mindustry.type.ItemStack.*;
import static arikoth.content.ArikothItems.*;
import static mindustry.content.Fx.*;

public class ArikothCrafting {
    public static Block
            regolithSeparationFurnace,
            icherCrystallizer,
    //ore refineries
            smeltingChamber, leachingVat,
    //chemical factories
            sulfuricAcidPlant, massSeparationPlatfom,
    //petrochemical

    end;

    public static void load(){
        regolithSeparationFurnace = new GenericCrafter("regolith-separation-furnace"){{
            requirements(Category.crafting, ItemStack.with(vanadium, 42, tantalum, 40));
            size = 3;
            craftTime = 60;
            consumeItem(regolith, 4);
            itemCapacity = 20;
            consumePower(120/60f);
            craftEffect = pulverizeMedium;

            outputItems = ItemStack.with(rhenium, 2, technoFossil, 1);
            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawArcSmelt(),
                    new DrawDefault(),
                    new DrawGlowRegion()
            );
        }};

        icherCrystallizer = new GenericCrafter("icher-crystallizer"){{
            requirements(Category.crafting, ItemStack.with(tantalum, 40, vanadium, 12, rhenium, 32));
            size = 4;squareSprite = false;customShadow = true;
            //inputs
            consumeLiquid(icher, 1.5f); consumePower(120/60f);
            //outputs
            outputItems = with(crystallineIcher, 4);craftTime = 120;
            //capacity
            itemCapacity = 20; liquidCapacity = 1.5f*600;

            updateEffectSpread = 0;
            updateEffectChance = 0.05f;
            updateEffect = new RadialEffect(new MultiEffect(
                    new ParticleEffect(){{
                        sizeFrom = 2;sizeTo = 6;sizeInterp = Interp.pow2Out;

                        lifetime = 300;interp = Interp.pow3Out;

                        colorFrom = Color.valueOf("fde756").a(0.6f);
                        colorTo = Color.valueOf("d6b363").a(0);

                        particles = 2;length = 120;useRotation = false;cone = 6;baseRotation = 32;
                    }},
                    new RadialEffect( new MultiEffect(
                            new ParticleEffect(){{
                                sizeFrom = 2;sizeTo = 6;sizeInterp = Interp.pow2Out;

                                lifetime = 300;interp = Interp.pow3Out;

                                colorFrom = Color.valueOf("fde756").a(0.6f);
                                colorTo = Color.valueOf("d6b363").a(0);

                                particles = 2;length = 120;useRotation = false;cone = 6;baseRotation = 32;
                            }},
                            new ParticleEffect(){{
                                sizeFrom = 2;sizeTo = 6;sizeInterp = Interp.pow2Out;

                                lifetime = 300;interp = Interp.pow3Out;

                                colorFrom = Color.valueOf("fde756").a(0.6f);
                                colorTo = Color.valueOf("d6b363").a(0);

                                particles = 2;length = 120;useRotation = false;cone = 6;baseRotation = 32;
                            }}), 2, 180, 8){{
                        rotationOffset = -90 - Angles.angle(-42/4f, 11/4f);
                    }}
            ), 1, 0, 43.41f/4f){{
                rotationOffset = Angles.angle(-42/4f, 11/4f);
            }};
            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawModParticles(){{
                        particleRad = 3;
                        particles = 15;
                        color = Color.valueOf("fde756");
                        particleSize = 4;
                        particleLife = 80;
                        x = -42/4f;
                        y = 11/4f;
                    }},
                    new DrawModParticles(){{
                        particleRad = 3;
                        particles = 15;
                        color = Color.valueOf("fde756");
                        particleSize = 4;
                        particleLife = 80;
                        x = -42/4f;
                        y = 11/4f+32/4f;
                    }},
                    new DrawModParticles(){{
                        particleRad = 3;
                        particles = 15;
                        color = Color.valueOf("fde756");
                        particleSize = 4;
                        particleLife = 80;
                        x = -42/4f;
                        y = 11/4f-32/4f;
                    }},
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        color = Color.white;
                    }}
            );
        }};

        smeltingChamber = new RecipeCrafter("smelting-chamber"){{
            requirements(Category.crafting, ItemStack.with(tantalum, 80, vanadium, 60, rhenium, 120));
            size = 4;
            squareSprite = false;

            recipes.addAll(RecipeRegister.smeltingChamber);
            configurable = true;

            itemCapacity = 20;
            consumePower(180/60f);
            //jank intensifies

            drawer = new DrawMulti(
                    new DrawRegion("-shadow"),
                    new DrawRegion("-lower"),
                    new DrawStartupRegion("-mid", ArikothPal.orangeEmber),
                    new DrawModifCircles(ArikothPal.yellowEmber){{
                        radius = 10;
                        amount = 2;
                        timeScl = 120;
                        strokeMax = 2;
                        strokeMin = 0;
                        invert = false;
                        light = true;
                    }},
                    new DrawDefault(),
                    new DrawRegion("-top-shadow"){{
                        layer = Layer.block;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.001f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = -32/4f;
                        y = -28/4f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = -32/4f;
                        y = 25/4f;
                    }},
                    new DrawRegion("-top-open"){{
                        layer = Layer.block + 0.001f;
                    }},
                    new DrawGlowRegion("-glow"){{
                        color = ArikothPal.orangeEmber;
                    }}
            ){{
                iconOverride = new String[]{"-lower"};
            }};
        }};

        leachingVat = new RecipeCrafter("leaching-vat"){{
            requirements(Category.crafting, ItemStack.with(tantalum, 40, vanadium, 12, rhenium, 32));
            size = 3;
            squareSprite = false;

            recipes.addAll(RecipeRegister.leachingVat);
            configurable = true;

            itemCapacity = 14*3;
            liquidCapacity = 80;
            craftingSpeed = 0.25f;
            consumePower(330/60f);
            //jank intensifies
            updateEffectChance = 0.001f;
            updateEffect = new MultiEffect(
                    new ParticleEffect(){{
                        particles = 1;
                        length = 20;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.sulfurSmoke;
                        colorTo = ArikothPal.sulfurSmokeDark.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 20;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.sulfurSmoke;
                        colorTo = ArikothPal.sulfurSmokeDark.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 1;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 20;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.sulfurSmoke;
                        colorTo = ArikothPal.sulfurSmokeDark.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 2;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 20;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.sulfurSmoke;
                        colorTo = ArikothPal.sulfurSmokeDark.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 3;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 20;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.sulfurSmoke;
                        colorTo = ArikothPal.sulfurSmokeDark.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 4;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 20;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.sulfurSmoke;
                        colorTo = ArikothPal.sulfurSmokeDark.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 5;
            }});
            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawCells(){{
                        range = 9;
                        particles = 50;
                        color = Color.valueOf("99963c");
                        particleColorFrom = color;
                        particleColorTo = sulfuricAcid.color;
                    }},
                    new DrawModifCircles(Color.valueOf("fffb80")){{
                        radius = 9;
                        amount = 2;
                        timeScl = 120;
                        strokeMax = 2;
                        strokeMin = 0;
                        invert = false;
                        light = true;
                    }},
                    new DrawCells(){{
                        range = 9;
                        particles = 50;
                        color = Color.valueOf("99963c").a(0.5f);
                        particleColorFrom = color;
                        particleColorTo = sulfuricAcid.color.cpy().a(0.5f);
                    }},
                    new DrawDefault(),
                    new DrawRegion("-top-shadow"){{
                        layer = Layer.block + 0.0001f;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.0001f;
                    }},
                    new DrawGlowRegion("-glow"){{
                        color = Color.valueOf("fffb80");
                    }}
            ){{
                iconOverride = new String[]{"-lower"};
            }};
        }};

        sulfuricAcidPlant = new GenericCrafter("sulfuric-acid-plant"){{
            requirements(Category.crafting, ItemStack.with(tantalum, 40, vanadium, 12, rhenium, 32));
            size = 5;
            craftTime = 180;
            squareSprite = false;
            consumeItem(sulfur, 10);
            consumeLiquid(stainedWater, 80/60f);
            consumeLiquid(Liquids.ozone, 26/60f);
            outputLiquids = LiquidStack.with(sulfuricAcid, 80/60f);
            itemCapacity = 40;
            liquidCapacity = 160;
            consumePower(1440/60f);
            craftEffect = new RadialEffect(new MultiEffect(
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 1;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 2;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 3;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 4;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 5;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.cyanEmber;
                        colorTo = ArikothPal.cyanEmber;
                        startDelay = 0;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.cyanEmber;
                        colorTo = ArikothPal.cyanEmber;
                        startDelay = 11;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.cyanEmber;
                        colorTo = ArikothPal.cyanEmber;
                        startDelay = 22;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.cyanEmber;
                        colorTo = ArikothPal.cyanEmber;
                        startDelay = 33;
                    }}
            ), 1, 90, 53.48f/4
            ){{
                rotationOffset = 110.8f;
            }};
            drawer = new DrawMulti(
                    new DrawRegion("-shadow"),
                    new DrawRegion("-lower"),
                    new DrawCells(){{
                        range = 12;
                        particles = 50;
                        color = Color.valueOf("99963c");
                        particleColorFrom = color;
                        particleColorTo = sulfuricAcid.color;
                    }},
                    new DrawLiquidTile(stainedWater){{
                        padRight = 135/4f;
                        padLeft = 2;
                        padBottom = 8;
                        padTop = 50/4f;
                    }},
                    new DrawRegion("-rotator"){{
                        x = -17/4f;
                        rotateSpeed = -6;
                        spinSprite = true;
                    }},
                    new DrawRegion("-rotator"){{
                        x = 17/4f;
                        rotateSpeed = -6;
                        spinSprite = true;
                    }},
                    new DrawCells(){{
                        range = 12;
                        particles = 50;
                        color = Color.valueOf("99963c").a(0.5f);
                        particleColorFrom = color;
                        particleColorTo = sulfuricAcid.color.cpy().a(0.5f);
                    }},
                    new DrawDefault(),
                    new DrawRegion("-top-shadow"){{
                        layer = Layer.block + 0.0004f;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.0005f;
                    }},
                    new DrawRegion("-topest-shadow"){{
                        layer = Layer.block;
                    }},
                    new DrawRegion("-topest"){{
                        layer = Layer.block + 0.0005f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = -19/4f;
                        y = 50/4f;
                        midColor = ArikothPal.blueEmber;
                        flameColor = ArikothPal.cyanEmber;
                    }},
                    new DrawRegion("-topest-open"){{
                        layer = Layer.block + 0.001f;
                    }},
                    new DrawGlowRegion("-glow"){{
                        color = ArikothPal.cyanEmber;
                    }}
            ){{
                iconOverride = new String[]{"-lower"};
            }};
        }};

        massSeparationPlatfom = new GenericCrafter("mass-separation-platform"){{
            requirements(Category.crafting, ItemStack.with(tantalum, 120, vanadium, 80, rhenium, 120));
            size = 8;
            craftTime = 120;
            squareSprite = false;
            //for visuals its 3.99
            consumeLiquid(aSlag, 4f);
            itemCapacity = 20;
            liquidCapacity = 600;
            outputItems = with(tantalum, 10);
            consumePower(380/60f);
            craftEffect = new RadialEffect(new MultiEffect(
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 1;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 2;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 3;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 4;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 40;
                        lifetime = 420;
                        sizeFrom = 0;
                        sizeTo = 5;
                        cone = 4;
                        baseRotation = 73;
                        useRotation = false;
                        sizeInterp = Interp.slope;
                        interp = Interp.pow3Out;
                        colorFrom = Color.lightGray;
                        colorTo = Color.lightGray.cpy().a(0);
                        layer = 100.3f;
                        lightOpacity = 0;
                        startDelay = 5;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.orangeEmber;
                        colorTo = ArikothPal.yellowEmber;
                        startDelay = 0;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.orangeEmber;
                        colorTo = ArikothPal.yellowEmber;
                        startDelay = 11;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.orangeEmber;
                        colorTo = ArikothPal.yellowEmber;
                        startDelay = 22;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.orangeEmber;
                        colorTo = ArikothPal.yellowEmber;
                        startDelay = 33;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.orangeEmber;
                        colorTo = ArikothPal.yellowEmber;
                        startDelay = 44;
                    }},
                    new ParticleEffect(){{
                        particles = 1;
                        length = 120;
                        lifetime = 60;
                        strokeFrom = 4;
                        line = true;
                        strokeTo = 0;
                        lenFrom = 0;
                        lenTo = 7;
                        cone = 4;
                        baseRotation = 90;
                        useRotation = false;
                        interp = Interp.pow3Out;
                        colorFrom = ArikothPal.orangeEmber;
                        colorTo = ArikothPal.yellowEmber;
                        startDelay = 55;
                    }}
            ), 4, 90, 74.9f/4f
            ){{
                rotationOffset = 45;
            }};
            lightRadius = 300;
            emitLight = true;
            lightColor = ArikothPal.orangeEmber;
            drawer = new DrawMulti(
                    new DrawRegion("-shadow"),
                    new DrawRegion("-lower"),
                    new DrawStartupRegion("-middle", ArikothPal.orangeEmber),
                    new DrawModifCircles(ArikothPal.yellowEmber){{
                        radius = 14;
                        amount = 2;
                        timeScl = 240;
                        strokeMax = 3;
                        strokeMin = 0;
                        invert = true;
                        light = true;
                    }},
                    new DrawModifCrucibleFlame(){{
                        flameRad = 6;
                        particles = 60;
                        particleRad = 12;
                    }},
                    new DrawLiquidTile(aSlag){{
                        padding = 3;
                    }},
                    new DrawDefault(),
                    new DrawRegion("-top-shadow"){{
                        layer = Layer.block;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.001f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = 53/4f;
                        y = 53/4f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = -53/4f;
                        y = 53/4f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = 53/4f;
                        y = -53/4f;
                    }},
                    new DrawModifCrucibleFlame(){{
                        layer = Layer.block + 0.001f;
                        particleRad = 1;
                        particleSize = 2;
                        particles = 3;
                        flameRad = 0.5f;
                        x = -53/4f;
                        y = -53/4f;
                    }},
                    new DrawRegion("-top-open"){{
                        layer = Layer.block + 0.001f;
                    }},
                    new DrawStartupRegion("-gloww", ArikothPal.orangeEmber){{
                        layer = Layer.blockAdditive;
                        blending = true;
                    }},
                    new DrawGlowRegion("-glow"){{
                        color = ArikothPal.orangeEmber;
                    }}
            ){{
                iconOverride = new String[]{"-lower"};
            }};
        }};
    }
}

package arikoth.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import arikoth.content.ArikothLiquids;
import arikoth.content.effects.palettes.ArikothPal;
import arikoth.world.blocks.drilling.DrawerDrill;
import arikoth.world.drawers.DrawBeamDrill;
import arikoth.world.drawers.DrawModifCircles;
import arikoth.world.drawers.DrawStartupRegion;
import arikoth.world.presets.misc.ArikothAttribute;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.consumers.ConsumeLiquidFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static arikoth.content.ArikothItems.*;
import static mindustry.type.ItemStack.*;

public class ArikothDrilling {
    public static Block
            //borers
            rotaryBorer, icherAccumalator, regolithArcForge, laserDriller,

    smallFluidSiphon,
    end;
    public static void load(){
        rotaryBorer = new DrawerDrill("rotary-crusher-MK1"){{
            requirements(Category.production, with(tantalum, 25, vanadium, 30));
            drillTime = 400;
            itemCapacity = 15;
            size = 3;
            tier = 3;
            customShadow = true;
            squareSprite = false;
            scaledHealth = 30;
            liquidBoostIntensity = 1;
            consumeLiquid(ArikothLiquids.icher, 15/60f);

            researchCost = with(vanadium, 20);
            drawer = new DrawMulti(
                    new DrawRegion("-rotator"){{
                        spinSprite = true;
                        rotateSpeed = -4.5f;
                        x = 24/4f;
                        y = 0f;
                    }},
                    new DrawRegion("-rotator"){{
                        spinSprite = true;
                        rotateSpeed = 4.5f;
                        x = -24/4f;
                        y = 0f;
                    }},
                    new DrawRegion("-rotator"){{
                        spinSprite = true;
                        rotateSpeed = -4.5f;
                        x = 0f;
                        y = 19/4f;
                    }},
                    new DrawRegion("-rotator"){{
                        spinSprite = true;
                        rotateSpeed = 4.5f;
                        x = 0f;
                        y = -19/4f;
                    }},
                    new DrawDefault()
            );
        }};

        icherAccumalator = new AttributeCrafter("icher-accumulator"){{
            requirements(Category.production, with(vanadium, 20, tantalum, 30));
            craftTime = 60;
            itemCapacity = 15;
            size = 3;
            customShadow = true;
            squareSprite = false;
            scaledHealth = 30;

            attribute = ArikothAttribute.icher;

            minEfficiency = 9f - 0.0001f;
            baseEfficiency = 0f;
            boostScale = 1/9f;
            displayEfficiency = false;

            outputLiquids = LiquidStack.with(ArikothLiquids.icher, 1);

            researchCost = with(vanadium, 20);
            updateEffectSpread = 0;
            updateEffectChance = 0.05f;
            updateEffect = new RadialEffect(new MultiEffect(
                    new ParticleEffect(){{
                        sizeFrom = 0;sizeTo = 8;sizeInterp = Interp.slope;

                        lifetime = 300;interp = Interp.pow3Out;

                        colorFrom = Color.valueOf("fde756").a(0.6f);
                        colorTo = Color.valueOf("e8a541").a(0);

                        particles = 2;length = 120;useRotation = false;cone = 6;baseRotation = 32;
                    }},
                    new RadialEffect( new MultiEffect(
                    new ParticleEffect(){{
                        sizeFrom = 0;sizeTo = 8;sizeInterp = Interp.slope;

                        lifetime = 300;interp = Interp.pow3Out;

                        colorFrom = Color.valueOf("fde756").a(0.6f);
                        colorTo = Color.valueOf("e8a541").a(0);

                        particles = 2;length = 120;useRotation = false;cone = 6;baseRotation = 32;
                    }},
                    new ParticleEffect(){{
                        sizeFrom = 0;sizeTo = 8;sizeInterp = Interp.slope;

                        lifetime = 300;interp = Interp.pow3Out;

                        colorFrom = Color.valueOf("fde756").a(0.6f);
                        colorTo = Color.valueOf("e8a541").a(0);

                        particles = 2;length = 120;useRotation = false;cone = 6;baseRotation = 32;
                    }}), 2, 180, 24/4f){{
                        rotationOffset = -90;
                    }}
            ), 1, 0, 27/4f){{
                rotationOffset = 180;
            }};
            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawStartupRegion("-middle", ArikothLiquids.icher.color),
                    new DrawModifCircles(ArikothLiquids.icher.color){{
                        radius = 10;
                        amount = 3;
                        invert = true;
                        strokeMax = 1f;
                        strokeMin = 0;
                    }},
                    new DrawRegion("-mid"),
                    new DrawDefault()
            );
        }};

        regolithArcForge = new AttributeCrafter("regolith-arc-forge"){{
            requirements(Category.production, with(tantalum, 120, vanadium, 80, rhenium, 40));
            craftTime = 60;
            itemCapacity = 15;
            size = 4;
            customShadow = true;
            squareSprite = false;
            scaledHealth = 30;

            attribute = Attribute.sand;

            minEfficiency = 16f - 0.0001f;
            baseEfficiency = 0f;
            boostScale = 1/16f;
            displayEfficiency = false;

            consumeItem(anthracite, 2);
            consumePower(300/60f);

            outputItems = with(aSilicon, 5);

            researchCost = with(vanadium, 20);
            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawArcSmelt(){{
                        flameColor = ArikothPal.orangeEmber;
                        midColor = ArikothPal.yellowEmber;
                    }},
                    new DrawDefault(),
                    new DrawRegion("-rotator"){{
                        x = 46/4f;
                        rotateSpeed = 4;
                        spinSprite = true;
                    }},
                    new DrawRegion("-rotator"){{
                        x = -46/4f;
                        rotateSpeed = 4;
                        spinSprite = true;
                    }},
                    new DrawRegion("-rotator"){{
                        y = 46/4f;
                        rotateSpeed = 4;
                        spinSprite = true;
                    }},
                    new DrawRegion("-rotator"){{
                        y = -46/4f;
                        rotateSpeed = 4;
                        spinSprite = true;
                    }},
                    new DrawRegion("-top-shadow"){{
                        layer = Layer.block + 0.0001f;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.00015f;
                    }},
                    new DrawGlowRegion(){{
                        color = ArikothPal.orangeEmber;
                        glowScale = 15;
                    }}
            );
        }};

        laserDriller = new DrawerDrill("laser-driller"){{
            requirements(Category.production, with(tantalum, 225, vanadium, 160, rhenium, 220, aSilicon, 120));
            drillTime = 100;
            itemCapacity = 15;
            size = 6;
            tier = 3;
            customShadow = true;
            squareSprite = false;
            scaledHealth = 30;
            liquidBoostIntensity = 1;
            consumePower(800/60f);

            researchCost = with(vanadium, 20);
            drawer = new DrawMulti(
                    new DrawBeamDrill(){{
                        las = "-laser";
                        lasEnd = "-laser-end";
                        suffix = "-drill";
                        x = 0;
                        y = -49/4f;
                        beamY = 16/4f;
                        beamX = 32/4f;
                        scale = 0.5f;
                    }},
                    new DrawBeamDrill(){{
                        las = "-laser";
                        lasEnd = "-laser-end";
                         suffix = "-drill";
                        x = 0;
                        y = 49/4f;
                        beamY = 16/4f;
                        beamX = 32/4f;
                        scale = 0.5f;
                    }},

                    new DrawBeamDrill(){{
                        las = "-laser";
                        lasEnd = "-laser-end";
                        suffix = "-drill";
                        x = 55/4f;
                        y = -49/4f;
                        beamY = 16/4f;
                        beamX = 32/4f;
                        scale = 0.5f;
                    }},
                    new DrawBeamDrill(){{
                        las = "-laser";
                        lasEnd = "-laser-end";
                        suffix = "-drill";
                        x = 55/4f;
                        y = 49/4f;
                        beamY = 16/4f;
                        beamX = 32/4f;
                        scale = 0.5f;
                    }},

                    new DrawBeamDrill(){{
                        las = "-laser";
                        lasEnd = "-laser-end";
                        suffix = "-drill";
                        x = -55/4f;
                        y = -49/4f;
                        beamY = 16/4f;
                        beamX = 32/4f;
                        scale = 0.5f;
                    }},
                    new DrawBeamDrill(){{
                        las = "-laser";
                        lasEnd = "-laser-end";
                        suffix = "-drill";
                        x = -55/4f;
                        y = 49/4f;
                        beamY = 16/4f;
                        beamX = 32/4f;
                        scale = 0.5f;
                    }},
                    new DrawDefault(),
                    new DrawGlowRegion(){{
                        color = Color.valueOf("ffd675");
                    }}
            );
        }};

        smallFluidSiphon = new Pump("small-fluid-siphon"){{
            requirements(Category.liquid, with(vanadium, 12));
            pumpAmount = 0.25f;
            conductivePower = true;
            squareSprite = false;
            scaledHealth = 80;
            consumePower(0.3f);
            liquidCapacity = 120f;
            hasPower = false;
            size = 1;
            consume(
                    new ConsumeLiquidFlammable(-1000, 0).optional(true, false)
            );
        }};

        smallFluidSiphon = new Pump("fluid-siphon"){{
            requirements(Category.liquid, with(vanadium, 90, tantalum, 20, rhenium, 82, aSilicon, 40));
            pumpAmount = 0.30f;
            conductivePower = true;
            squareSprite = false;
            scaledHealth = 80;
            consumePower(0.3f);
            liquidCapacity = 120f;
            hasPower = false;
            size = 3;
            consume(
                    new ConsumeLiquidFlammable(-1000, 0).optional(true, false)
            );
        }};
    }
}

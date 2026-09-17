package arikoth.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import arikoth.content.effects.palettes.ArikothPal;
import arikoth.world.blocks.power.BoundarySolarPanel;
import arikoth.world.blocks.power.DrawerPowerNode;
import arikoth.world.drawers.DrawModifBlurSpin;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Layer;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;

import static arikoth.content.ArikothItems.*;
import static arikoth.content.ArikothLiquids.*;
import static mindustry.type.Category.*;

public class ArikothPower {
    public static Block
            teslaNode, largeTeslaNode, powerTerminal,
            pvPanel, combustionPlant,
    end;

    public static void load(){
        teslaNode = new DrawerPowerNode("powerline"){{
            requirements(power, ItemStack.with(vanadium, 10, tantalum, 10));
            scaledHealth = 90;
            armor = 10;
            laserRange = 16;
            maxNodes = 12;
            drawer = new DrawMulti(
                    new DrawRegion("-shadow"){{
                        layer = Layer.block - 0.001f;
                    }},
                    new DrawRegion("-shadow-top"){{
                        layer = Layer.blockOver + 5.6f;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.blockOver + 5.61f;
                    }}
            );
        }};

        pvPanel = new BoundarySolarPanel("photovoltaic-grid"){{
            requirements(power, ItemStack.with(vanadium, 200, tantalum, 320));
            size = 6;
            scaledHealth = 60;
            armor = 2;
            powerProduction = 800/60f;
            spacing = 6;
            customShadow = true;
        }};

        combustionPlant = new ConsumeGenerator("combustion-plant"){{
            requirements(power, ItemStack.with(tantalum, 120, vanadium, 160, aSilicon, 80, rhenium, 80));

            size = 5;
            liquidCapacity = 60 * 5;

            consumeLiquid(stainedWater, 1);
            consume(new ConsumeItemFlammable());

            itemDuration = 60f * 2f;
            itemCapacity = 10;

            powerProduction = 1200 / 60f;

            ambientSound = Sounds.loopDifferential;
            ambientSoundVolume = 0.06f;

            //embers
            consumeEffect = new RadialEffect(new MultiEffect(
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
                        colorTo = ArikothPal.orangeEmber;
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
                        colorTo = ArikothPal.orangeEmber;
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
                        colorTo = ArikothPal.orangeEmber;
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
                        colorTo = ArikothPal.orangeEmber;
                        startDelay = 33;
                    }},
                    new RadialEffect(new MultiEffect(
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
                                colorTo = ArikothPal.orangeEmber;
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
                                colorTo = ArikothPal.orangeEmber;
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
                                colorTo = ArikothPal.orangeEmber;
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
                                colorTo = ArikothPal.orangeEmber;
                                startDelay = 33;
                            }}
                    ), 2, 180, 50/4f){{
                        rotationOffset = -90;
                    }}
            ), 1, 0, 12){{
                rotationOffset = 90;
            }};

            //smoke
            generateEffect = new RadialEffect(new MultiEffect(
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
                        startDelay = 0;
                    }},
                    new RadialEffect(new ParticleEffect(){{
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
                        startDelay = 0;
                    }}, 2, 180, 50/4f){{
                        rotationOffset = -90;
                    }}
            ), 1, 0, 12){{
                rotationOffset = 90;
            }};
            generateEffectRange = 1;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawLiquidTile(stainedWater){{
                        padding = 2;
                    }},
                    new DrawModifBlurSpin("-rotator", 12){{
                        x = 25/4f;
                        y = 0;
                    }},
                    new DrawModifBlurSpin("-rotator", -12){{
                        x = -25/4f;
                        y = 0;
                    }},
                    new DrawLiquidTile(stainedWater){{
                        padding = 2;
                        alpha = 0.5f;
                    }},
                    new DrawDefault(),
                    new DrawRegion("-top-shadow"){{
                        layer = Layer.block;
                    }},
                    new DrawRegion("-top"){{
                        layer = Layer.block + 0.0001f;
                    }},
                    new DrawRegion("-top-open"){{
                        layer = Layer.block + 0.0001f;
                    }},
                    new DrawGlowRegion("-pipe-glow"){{
                        color = ArikothPal.orangeEmber;
                        glowScale = 20;
                    }},
                    new DrawGlowRegion("-glow"){{
                        color = ArikothPal.yellowEmber;
                        glowScale = 10;
                    }}
            ){{
                iconOverride = new String[]{"-lower"};
            }};
        }};
    }
}

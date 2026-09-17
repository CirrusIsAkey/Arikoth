package arikoth.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import arikoth.content.ArikothItems;
import arikoth.content.ArikothLiquids;
import arikoth.content.ArikothSounds;
import arikoth.content.ArikothUnitTypes;
import arikoth.world.blocks.storage.*;
import arikoth.world.drawers.DrawModifBlurSpin;
import mindustry.content.Fx;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.units.UnitCargoLoader;
import mindustry.world.consumers.ConsumeItemFlammable;
import mindustry.world.draw.*;
import mindustry.world.meta.BuildVisibility;

import static arikoth.content.ArikothItems.*;
import static mindustry.content.Items.silicon;
import static mindustry.type.Category.power;
import static mindustry.type.ItemStack.*;

public class ArikothStorage {
    public static Block
    //
    coreSerenity, coreAtaraxia, coreHalcyon,
    //serenity KIT
    IBcombustionTurbine,

    repairDronePad,
    end;
    public static void load(){
        coreSerenity = new ModCore("ATRE-pod-serenity"){{
            requirements(Category.effect, with(ArikothItems.vanadium, 1200));

            isFirstTier = true;
            alwaysUnlocked = true;
            thrusterLength = 8;

            landSound = ArikothSounds.podLand;
            landZoomFrom = 0.02f; landZoomTo = 4f;
            landZoomInterp = Interp.pow2;

            size = 5;
            health = 2560;

            itemCapacity = 2000;
            unitCapModifier = 9;

            unitType = ArikothUnitTypes.weld;
        }};

        IBcombustionTurbine = new ConsumeGenerator("IB-combustion-turbine"){{
            requirements(power, BuildVisibility.sandboxOnly, ItemStack.with(vanadium, 90, silicon, 54));

            size = 2;
            hasLiquids = false;

            consume(new ConsumeItemFlammable());

            itemDuration = 60f * 10f;
            itemCapacity = 10;

            powerProduction = 100 / 60f;

            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.06f;

            consumeEffect = Fx.none;
            generateEffect = new MultiEffect(
                    new ParticleEffect(){{
                        lifetime = 35;
                        length = 20;
                        cone = 360;
                        particles = 4;
                        colorFrom = Color.white;
                        colorTo = Pal.redLight;
                        sizeFrom = 3;
                    }},
                    new ParticleEffect(){{
                        lifetime = 300;
                        length = 20;
                        cone = 360;
                        particles = 1;
                        colorFrom = Color.white.cpy().a(0.66f);
                        colorTo = Color.lightGray.cpy().a(00f);
                        sizeInterp = Interp.slope;
                        interp = Interp.linear;
                        sizeFrom = 0;
                        sizeTo = 8;
                        lightOpacity = 0.5f;
                        lightRadius = 45;
                        layer = 110;
                    }}
            );
            generateEffectRange = 1;

            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawModifBlurSpin("-rotator", 12),
                    new DrawModifBlurSpin("-rotator", 12){{
                        rotation = 45;
                    }},
                    new DrawDefault()
            );
        }};

        repairDronePad = new UnitCargoLoader("repair-drone-pad"){{
            requirements(Category.effect, with(rhenium, 120, vanadium, 30, tantalum, 30, aSilicon, 80));

            size = 3;

            polyColor = Color.valueOf("81e8e4");
            polyRadius = 8;
            polySides = 8;
            polyStroke = 1.5f;
            polyRotateSpeed = 3;

            unitBuildTime = 60f * 8f;
            unitType = ArikothUnitTypes.repairDrone;

            consumePower(200f / 60f);

            //intentionally set absurdly high to make this block not overpowered
            consumeLiquid(ArikothLiquids.icher, 50f / 60f);

            itemCapacity = 0;
        }};
    }
}
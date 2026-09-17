package arikoth.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import arikoth.content.ArikothLiquids;
import mindustry.content.Fx;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.LiquidBridge;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.draw.*;

import static arikoth.content.ArikothItems.*;
import static mindustry.type.ItemStack.with;

public class ArikothLogistics {
    public static Block
    rail, armoredRail, railRouter, railOverpass, railOverflow, railUnderflow, stackBelt, stackRouter, stackCatapult,
    canister, canisterUnloader,
    fluidChannel, fluidPipelineRouter, fluidPipelineJunction, fluidNode, ionithOutlet, fluidCauldron, fluidSilo,
    end;

    public static void load(){
        rail = new Duct("vessel"){{
            requirements(Category.distribution, ItemStack.with(vanadium, 1));
            speed = 2.5f;
            health = 90;
            bridgeReplacement = ArikothLogistics.railOverpass;
        }};
        railRouter = new DuctRouter("vessel-router"){{
            requirements(Category.distribution, ItemStack.with(vanadium, 6));
            speed = 2.4f;
            health = 90;
        }};
        railOverflow = new OverflowDuct("vessel-flowgate"){{
            requirements(Category.distribution, ItemStack.with(vanadium, 6));
            speed = 2.4f;
            health = 90;
        }};
        railUnderflow = new OverflowDuct("vessel-flowgate-invert"){{
            requirements(Category.distribution, ItemStack.with(vanadium, 6));
            speed = 2.4f;
            invert = true;
            health = 90;
        }};
        railOverpass = new DuctBridge("vessel-overpass"){{
            requirements(Category.distribution, ItemStack.with(vanadium, 16));
            speed = 2.4f;
            health = 90;
            range = 4;
        }};
        ((Duct) rail).bridgeReplacement = railOverpass;

        canisterUnloader = new DirectionalUnloader("canister-unloader"){{
            requirements(Category.distribution, ItemStack.with(aSilicon, 15, vanadium, 15));
            health = 120;
            speed = 4f;
            solid = false;
            underBullets = true;
            regionRotated1 = 1;
            squareSprite = false;
        }};

        stackBelt = new StackConveyor("batch-rail"){{
            requirements(Category.distribution, ItemStack.with(aSilicon, 5, rhenium, 15));
            health = 300;
            speed = 0.03333f;
            itemCapacity = 20;
            recharge = 1;

            breakEffect = new MultiEffect(new WaveEffect(){{
                colorFrom = colorTo = Color.valueOf("8875ff");
                sizeFrom = 4;
                sizeTo = 4;
                strokeFrom = 4;
                strokeTo = 0;
                sides = 4;
            }},new RadialEffect(
                    new ParticleEffect(){{
                        length = 0;
                        baseLength = 0;
                        randLength = false;
                        line = true;
                        colorFrom = colorTo = Color.valueOf("8875ff");
                        lifetime = 30;
                        lenFrom = 8;
                        lenTo = 8;
                        strokeFrom = 4;
                        strokeTo = 0;
                        cone = 0;
                    }}, 4, 90, 8/4f, 0
            ));

            loadEffect = new WaveEffect(){{
                sizeFrom = 4;
                sizeTo = 9/4f;
                strokeFrom = strokeTo = 4;
                colorTo = rhenium.color;
                colorFrom = colorFrom.cpy().a(0);
                lifetime = 35;
                sides = 4;
                interp = Interp.linear;
            }};

            unloadEffect = new WaveEffect(){{
                sizeFrom = 9/4f;
                sizeTo = 4;
                strokeFrom = strokeTo = 4;
                colorFrom = rhenium.color;
                colorTo = colorFrom.cpy().a(0);
                lifetime = 35;
                sides = 4;
                interp = Interp.linear;
            }};
        }};

        // // // region liquids // // //
        fluidChannel = new DirectionLiquidBridge("fluid-channel"){{
            requirements(Category.liquid, ItemStack.with(vanadium, 16));
            health = 120;
            range = 8;
            speed = 3;
            liquidCapacity = 30;
            hasPower = false;
            squareSprite = false;
        }};
        fluidNode = new LiquidBridge("fluid-node"){{
            requirements(Category.liquid, ItemStack.with(vanadium, 16));
            health = 120;
            range = 3;
            transportTime = 3;
            liquidCapacity = 30;
            hasPower = false;
            squareSprite = false;
        }};
        ionithOutlet = new GenericCrafter("ionith-outlet"){{
            requirements(Category.liquid,  with(vanadium, 20));
            craftEffect = Fx.none;
            outputLiquid = new LiquidStack(ArikothLiquids.flux, 1);
            craftTime = 60f;
            size = 1;
            liquidCapacity = 20;
            hasPower = true;
            hasItems = false;
            hasLiquids = true;
            rotate = true;
            quickRotate = true;
            rotateDraw = false;
            liquidOutputDirections = new int[]{0};


            drawer = new DrawMulti(
                    new DrawRegion("-lower"),
                    new DrawLiquidTile(ArikothLiquids.flux){{padding = 1;}},
                    new DrawDefault(),
                    new DrawSideRegion()
            );
            ambientSound = Sounds.loopSmelter;
            ambientSoundVolume = 0.07f;

            consumeLiquid(ArikothLiquids.icher, 1);
            consumePowerBuffered(20);
        }};
        fluidPipelineRouter = new LiquidRouter("fluid-channel-router"){{
            requirements(Category.liquid, ItemStack.with(vanadium, 6));
            health = 90;
            liquidPadding = 2;
            squareSprite = false;
            solid = false;
            liquidCapacity = 20;
        }};
    }
}

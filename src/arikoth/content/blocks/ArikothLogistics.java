package arikoth.content.blocks;

import arc.graphics.Color;
import arikoth.content.ArikothItems;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.liquid.LiquidRouter;

public class ArikothLogistics {
    public static Block
    rail, armoredRail, railRouter, railOverpass, railOverflow, railUnderflow,
    fluidPipeline, fluidPipelineRouter, fluidPipelineOverpass, fluidCauldron, fluidSilo,
    end;

    public static void load(){
        railOverpass = new DuctBridge("rail-overpass"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 16));
            speed = 3.5f;
            health = 90;
            range = 4;
        }};
        rail = new Duct("rail"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 1));
            speed = 4;
            health = 90;
            bridgeReplacement = ArikothLogistics.railOverpass;
        }};
        railRouter = new DuctRouter("rail-router"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 6));
            speed = 3.5f;
            health = 90;
        }};
        railOverflow = new OverflowDuct("rail-overflow-gate"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 6));
            speed = 3.5f;
            health = 90;
        }};
        railUnderflow = new OverflowDuct("rail-underflow-gate"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 6));
            speed = 3.5f;
            invert = true;
            health = 90;
        }};



        // // // region liquids // // //


        fluidPipeline = new Conduit("fluid-pipeline"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 1));
            health = 90;
            bridgeReplacement = ArikothLogistics.fluidPipelineOverpass;
            leaks = false;
            botColor = Color.valueOf("1e1e24");
        }};
        fluidPipelineRouter = new LiquidRouter("fluid-pipeline-router"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 6));
            health = 90;
            liquidCapacity = 20;
        }};
        fluidPipelineOverpass = new DirectionLiquidBridge("fluid-pipeline-overpass"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 16));
            health = 120;
            range = 5;
            liquidCapacity = 30;
        }};
        fluidCauldron = new LiquidRouter("fluid-cauldron"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.rust, 90));
            health = 400;
            size = 2;
            liquidCapacity = 80;
        }};
    }
}

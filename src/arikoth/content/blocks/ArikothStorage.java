package arikoth.content.blocks;

import arikoth.content.ArikothItems;
import arikoth.content.ArikothUnitTypes;
import arikoth.world.blocks.storage.*;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.*;

public class ArikothStorage {
    public static Block
    landingPod, coreSerenity, coreAtaraxia, coreHalcyon,
    end;
    public static void load(){
        landingPod = new ArikothCoreBlock("landing-pod"){{
            requirements(Category.effect, BuildVisibility.editorOnly, with(ArikothItems.rust, 600, Items.tungsten, 60));

            isFirstTier = true;
            alwaysUnlocked = true;
            radius = 6;
            spawnCooldown = 3 * 60f;

            size = 2;
            health = 1500;
            thrusterLength = 15 / 4f;

            itemCapacity = 450;
            unitCapModifier = 6;

            unitType = ArikothUnitTypes.pioneer;
        }};
        coreSerenity = new ArikothCoreBlock("core-serenity"){{
            requirements(Category.effect, with(ArikothItems.rust, 260, Items.graphite, 90, Items.beryllium, 160));

            isFirstTier = true;
            alwaysUnlocked = true;
            thrusterLength = 4;
            radius = 8;
            spawnCooldown = 3 * 60f;

            size = 3;
            health = 2500;

            itemCapacity = 900;
            unitCapModifier = 9;

            unitType = ArikothUnitTypes.vision;
        }};
    }
}

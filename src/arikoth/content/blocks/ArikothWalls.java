package arikoth.content.blocks;

import arikoth.content.ArikothItems;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;

public class ArikothWalls {
    public static Block
    rheniumWall,
    end;

    public static void load(){
        rheniumWall = new Wall("rhenium-wall"){{
            requirements(Category.defense, ItemStack.with(ArikothItems.rhenium, 32));
            size = 2;
            scaledHealth = 800;
            armor = 20;
        }};
    }
}

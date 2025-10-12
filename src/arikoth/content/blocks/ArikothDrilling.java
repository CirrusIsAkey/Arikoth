package arikoth.content.blocks;

import arikoth.content.ArikothItems;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.production.BeamDrill;

import static mindustry.type.ItemStack.*;

public class ArikothDrilling {
    public static Block
    plasmaDrillingUnit,
    end;
    public static void load(){
        plasmaDrillingUnit = new BeamDrill("plasma-drilling-unit"){{
            requirements(Category.production, with(
                    ArikothItems.rust, 30
            ));
            range = 4;
            drillTime = 3 * 60;
            sparkColor = Pal.redLight;
            scaledHealth = 60;
            size = 2;
            tier = 3;
            consumePower(30 / 60f);
        }};
    }
}

package arikoth.content;

import arc.struct.*;
import arikoth.world.blocks.crafting.Recipe;
import mindustry.type.*;

import static arikoth.content.ArikothItems.*;
import static arikoth.content.ArikothLiquids.*;

public class RecipeRegister {
    public static Seq<Recipe>
            smeltingChamber, leachingVat;

    public static Recipe simpleRecipe(String sector, Item in, Item out) {
        return new Recipe(sector).consumeItem(in).outputItem(out);
    }

    public static void load() {
        smeltingChamber = Seq.with(
                new Recipe("arikoth-arikoth-test-map").consumeItems(
                        ItemStack.with(anthracite, 2, rheniite, 2)
                ).outputItem(rhenium, 2).outputItem(sulfur, 2),
                new Recipe("arikoth-arikoth-test-map").consumeItems(
                        ItemStack.with(anthracite, 2, kunzite, 2)
                ).outputItem(tantalum, 2).outputItem(lithium, 2),
                new Recipe("arikoth-arikoth-test-map").consumeItems(
                        ItemStack.with(anthracite, 2, vanathite, 2)
                ).outputItem(vanadium, 2).outputItem(sulfur, 2)
        );

        leachingVat = Seq.with(
                new Recipe("arikoth-arikoth-test-map").
                        consumeItem(rheniite, 10).consumeLiquid(sulfuricAcid, 20/60f).
                        outputItem(rhenium, 14),
                new Recipe("arikoth-arikoth-test-map").
                        consumeItem(rheniite, 10).consumeLiquid(sulfuricAcid, 20/60f).
                        outputItem(rhenium, 14),
                new Recipe("arikoth-arikoth-test-map").
                        consumeItem(kunzite, 10).consumeLiquid(sulfuricAcid, 20/60f).
                        outputItem(lithium, 14)
        );
    }
}

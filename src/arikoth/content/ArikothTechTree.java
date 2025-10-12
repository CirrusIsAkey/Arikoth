
package arikoth.content;

import arc.struct.Seq;
import mindustry.content.Liquids;
import mindustry.game.Objectives;

import static arikoth.content.ArikothLiquids.*;
import static arikoth.content.blocks.ArikothStorage.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.TechTree.*;
import static arikoth.content.ArikothBlocks.*;
import static arikoth.content.ArikothItems.*;
import static arikoth.content.ArikothPlanets.*;
import static arikoth.content.ArikothSectors.*;
import static mindustry.content.Items.*;

public class ArikothTechTree {
    public static void load(){
        arikoth.techTree = nodeRoot("arikoth", coreSerenity, false, () -> {
        });
    }
}
package arikoth.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.type.*;

public class ArikothItems {
    public static Item
            //metals
            rust, aluminum, iron, sodium,
            //rocks
            cryolite, magnetite, limestone, halite,
            //alloys
            steel, amalgam,
            //non-metals
            algalChunk, sulfur, boron,
            //manufactured
            thermite, gunpowder, blastPowder,
            end;

    public static void load() {
        rust = new Item("rust", Color.valueOf("#f2dba7")) {{
            hardness = 1;
            cost = 1f;
        }};
        aluminum = new Item("aluminum", Color.valueOf("#999ba1")) {{
            hardness = 1;
            cost = 1.5f;
        }};
        iron = new Item("iron", Color.valueOf("#c4a7c1")) {{
            hardness = 1;
            cost = 1.5f;
        }};
        sodium = new Item("sodium", Color.valueOf("#b4adcc")) {{
            hardness = 1;
            cost = 2f;
            explosiveness = 0.2f;
            flammability = 0.05f;
        }};
        // // // region rocks // // //
        cryolite = new Item("cryolite", Color.valueOf("666469")) {{
            cost = 1;
            hardness = 1;
        }};
        magnetite = new Item("magnetite", Color.valueOf("666469")) {{
            cost = 1;
            hardness = 1;
        }};
        limestone = new Item("limestone", Color.valueOf("f0dfca")) {{
            cost = 1;
            hardness = 1;
        }};
        halite = new Item("halite", Color.valueOf("b5b3ae")) {{
            cost = 1;
            hardness = 1;
        }};
        // // // region alloys // // //
        steel = new Item("steel", Color.valueOf("7d7d7d")) {{
            cost = 3.5f;
            hardness = 1;
        }};
        amalgam = new Item("amalgam", Color.valueOf("cebee8")) {{
            cost = 3.5f;
            hardness = 1;
            charge = 0.05f;
        }};
        // // // region non metals // // //
        algalChunk = new Item("algal-chunk", Color.valueOf("75515e")) {{
            cost = 1.2f;
            hardness = 1;
            flammability = 0.2f;
        }};
        sulfur = new Item("sulfur", Color.valueOf("75515e")) {{
            cost = 1.6f;
            hardness = 1;
            flammability = 0.12f;
            explosiveness = 0.08f;
        }};
        boron = new Item("boron", Color.valueOf("dadbbd")) {{
            cost = 2f;
            hardness = 2;
        }};
        // // // region manufactured // // //
        thermite = new Item("thermite", Color.valueOf("ffc47d")) {{
            cost = 3f;
            flammability = 1.8f;
        }};
        gunpowder = new Item("gunpowder", Color.valueOf("606060")) {{
            cost = 3f;
            flammability = 0.2f;
            explosiveness = 0.8f;
        }};
        blastPowder = new Item("blastpowder", Color.valueOf("ff8c7d")) {{
            cost = 3f;
            flammability = 0.05f;
            explosiveness = 2.1f;
        }};
    }
}
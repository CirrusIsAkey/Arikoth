package arikoth.content;

import arc.graphics.*;
import mindustry.type.*;

public class ArikothItems {
    public static Item
            vanadium, tantalum, regolith, aTungsten, rhenium, lithium, crystallineIcher,
    //ores
            technoFossil, rheniite, kunzite, vanathite,
    //non metals + metalloid
            anthracite, aSilicon, sulfur,

    //ammo
            chaingunRound, chemicalChaingunRound,
            explosiveArtilleryShell, incendiaryArtilleryShell, APArtilleryShell,
            howitzerShell, chemicalHowitzerShell, fragmentaryShell,

            flashGrenade,
            end;

    public static void load() {
        vanadium = new Item("vanadium", Color.valueOf("#cf9f7a")) {{
            hardness = 2;
            cost = 1f;
        }};
        tantalum = new Item("tantalum", Color.valueOf("#ab9693")) {{
            hardness = 2;
            cost = 1f;
        }};
        regolith = new Item("regolith", Color.valueOf("#fff0cf")) {{
            hardness = 1;
            cost = 0.5f;
            lowPriority = true;
        }};
        rhenium = new Item("rhenium", Color.valueOf("c7a176")) {{
            hardness = 3;
            cost = 1f;
        }};
        aTungsten = new Item("aTungsten", Color.valueOf("8e9094")) {{
            hardness = 3;
            cost = 1f;
        }};
        lithium = new Item("lithium", Color.valueOf("b35f54")) {{
            hardness = 2;
            cost = 1f;
            flammability = 1.2f;
            explosiveness = 1.5f;
            charge = 0.05f;
        }};
        crystallineIcher = new Item("crystalline-icher", Color.valueOf("ede480")) {{
            hardness = 2;
            cost = 1f;
            explosiveness = 0.2f;
        }};

        //ore

        technoFossil = new Item("techno-fossil", Color.valueOf("707375")) {{
            hardness = 2;
            cost = 1f;
        }};
        rheniite = new Item("rheniite", Color.valueOf("#404040")) {{
            hardness = 1;
            cost = 0.5f;
        }};
        kunzite = new Item("kunzite", Color.valueOf("#a67c9a")) {{
            hardness = 1;
            cost = 0.5f;
        }};
        vanathite = new Item("vanathinite", Color.valueOf("#9e4f4f")) {{
            hardness = 1;
            cost = 0.5f;
            explosiveness = 1.5f;
            flammability = 0.5f;
        }};

        //non-metal

        anthracite = new Item("anthracite", Color.valueOf("#606060")) {{
            hardness = 1;
            cost = 0.5f;
            flammability = 1.2f;
        }};
        aSilicon = new Item("aSilicon", Color.valueOf("9b9dab")) {{
            hardness = 1;
            cost = 2f;
        }};
        sulfur = new Item("sulfur", Color.valueOf("e8d974")) {{
            hardness = 1;
            cost = 2f;
            flammability = 0.5f;
        }};

        //ammo
        chaingunRound = new Item("chaingun-round", Color.valueOf("d66767")) {{
            hardness = 1;
            cost = 2f;
            explosiveness = 1.25f;
        }};
        chemicalChaingunRound = new Item("chemical-chaingun-round", Color.valueOf("e8d974")) {{
            hardness = 1;
            cost = 2f;
            explosiveness = 0.5f;
        }};
    }
}
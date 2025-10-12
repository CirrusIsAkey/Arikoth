package arikoth.content;

import arc.graphics.Color;
import mindustry.type.CellLiquid;
import mindustry.type.Liquid;

public class ArikothLiquids {
    public static Liquid
            // liquid
            icher, pyridine, liquidQuickSand, naphtha, gasoline, mercury, metafluid, moltenSalt, steam, methane, plasmine, cerethGoop, vetherGoop;

    public static void load(){
        // liquid
        liquidQuickSand = new Liquid("liquid-quicksand"){{
            color = Color.valueOf("fadab2");
            gasColor = Color.valueOf("fadab2");
            coolant = false;
            viscosity = 0.8f;
            temperature = 0.4f;
        }};
        mercury = new Liquid("mercury"){{
            color = Color.valueOf("807e8c");
            gasColor = Color.valueOf("807e8c");
            coolant = false;
            viscosity = 0.8f;
            temperature = 0.7f;
        }};
        metafluid = new Liquid("metafluid"){{
            color = Color.valueOf("95aacf");
            gasColor = Color.valueOf("95aacf");
            coolant = false;
            viscosity = 0.65f;
            temperature = 0.6f;
        }};
        naphtha = new Liquid("naphtha"){{
            color = Color.valueOf("c28353");
            coolant = false;
            flammability = 0.6f;
            explosiveness = 0.1f;
            viscosity = 0.2f;
            temperature = 0.5f;
        }};
        gasoline = new Liquid("gasoline"){{
            color = Color.valueOf("f0c256");
            coolant = false;
            flammability = 0.3f;
            explosiveness = 0.1f;
            viscosity = 0.6f;
            temperature = 0.5f;
        }};

        // gas
        icher = new Liquid("icher"){{
            color = Color.valueOf("edc879");
            gasColor = Color.valueOf("edc879");
            coolant = false;
            boilPoint = 0.6f;
            viscosity = 0.3f;
            explosiveness = 0.2f;
            temperature = 0.4f;
            gas = true;
        }};
        pyridine = new Liquid("pyridine"){{
            color = Color.valueOf("d9ca83");
            gasColor = Color.valueOf("d9ca83");
            coolant = false;
            viscosity = 0.3f;
            flammability = 0.4f;
            explosiveness = 0f;
            temperature = 0.6f;
            gas = true;
        }};
        steam = new Liquid("steam"){{
            gas = true;
            color = Color.valueOf("9f9f9f");
            coolant = false;
            temperature = 0.8f;
        }};
        methane = new Liquid("methane"){{
            gas = true;
            color = Color.valueOf("ffe570");
            coolant = false;
            flammability = 0.6f;
            explosiveness = 0.1f;
        }};
        // extra
        plasmine = new Liquid("plasmine"){{
            color = Color.valueOf("e35f5f");
            gasColor = Color.valueOf("e35f5f");
            coolant = false;
            boilPoint = 0.6f;
            viscosity = 0.3f;
            explosiveness = 0.4f;
            temperature = 0.4f;
            gas = true;
        }};
        cerethGoop = new CellLiquid("cerethGoop"){{
            color = Color.valueOf("ab42b8");
            colorFrom = Color.valueOf("c8d2db");
            colorTo = Color.valueOf("a0a7b8");
            viscosity = 0.9f;
            cells = 40;
        }};
    }
}
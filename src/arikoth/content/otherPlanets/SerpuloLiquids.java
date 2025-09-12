package arikoth.content.otherPlanets;

import arc.graphics.Color;
import mindustry.type.Liquid;

public class SerpuloLiquids {
    public static Liquid
            // liquid
            mireGas;

    public static void load(){
        // liquid

        // gas
        mireGas = new Liquid("mire-gas"){{
            gas = true;
            color = Color.valueOf("9fc7c9");
            coolant = false;
            temperature = 0.3f;
            explosiveness = 0.2f;
            flammability = 0.4f;
        }};
    }
}
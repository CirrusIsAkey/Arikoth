package arikoth.content;

import arc.graphics.Color;
import arikoth.content.effects.palettes.ArikothPal;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;

public class ArikothLiquids {
    public static Liquid
            // liquid
            stainedWater, refrigeratedWater, moltenChandelium, magma, aSlag, mercury, sulfuricAcid, methane, steam,
            //internal
            refineryGas, refineryFluid, moltenSilica,

    // icher-based
    icher, flux, dulledFlux,

    end;
    public static void load(){
        // liquid
        stainedWater = new Liquid("stained-water"){{
            color = Color.valueOf("6c9e8d");
            gasColor = Color.valueOf("6c9e8d");
            coolant = true;
            viscosity = 0.5f;
            temperature = 0.75f;
        }};
        refrigeratedWater = new Liquid("refrigerated-water"){{
            color = Color.valueOf("b9adff");
            gasColor = Color.valueOf("b9adff");
            coolant = true;
            viscosity = 0.5f;
            temperature = 0.2f;
        }};
        sulfuricAcid = new Liquid("vitirol"){{
            color = Color.valueOf("d6c451");
            gasColor = Color.valueOf("d6c451");
            coolant = true;
            viscosity = 0.5f;
            temperature = 0.5f;
        }};
        moltenChandelium = new Liquid("molten-chandelium"){{
            color = Color.valueOf("fcedcd");
            gasColor = Color.valueOf("fcedcd");
            coolant = true;
            viscosity = 0.8f;
            temperature = 0.9f;
        }};
        magma = new Liquid("magma"){{
            color = Color.valueOf("eb9a5b");
            gasColor = Color.valueOf("eb9a5b");
            coolant = true;
            viscosity = 0.8f;
            temperature = 0.9f;
        }};
        aSlag = new Liquid("aSlag"){{
            color = ArikothPal.orangeEmber;
            lightColor = color.cpy().a(0.55f);
            gasColor = ArikothPal.orangeEmber;
            coolant = true;
            viscosity = 0.8f;
            temperature = 0.75f;
        }};
        // gas
        icher = new Liquid("icher"){{
            color = Color.valueOf("ede480");
            gasColor = Color.valueOf("ede480");
            lightColor = color.cpy().a(0.55f);
            coolant = false;
            flammability = 0.4f;
            explosiveness = 1f;
            temperature = 0.5f;
            gas = false;
        }};
        flux = new Liquid("flux"){{
            color = Color.valueOf("a88fc7");
            gasColor = Color.valueOf("a88fc7");
            coolant = false;
            flammability = 0.05f;
            temperature = 0.25f;
            explosiveness = 4;
            gas = false;
        }};
        dulledFlux = new Liquid("dulled-flux"){{
            color = Color.valueOf("a88fc7");
            gasColor = Color.valueOf("a88fc7");
            coolant = false;
            flammability = 0.1f;
            temperature = 0.25f;
            explosiveness = 1;
            gas = false;
        }};
        methane = new Liquid("methane"){{
            color = Color.valueOf("708dbf");
            gasColor = Color.valueOf("708dbf");
            coolant = false;
            gas = true;
            temperature = 0.5f;
            flammability = 0.4f;
            explosiveness = 0.01f;
        }};
        steam = new Liquid("steam"){{
            color = Color.valueOf("ffffff");
            gasColor = Color.valueOf("ffffff");
            coolant = false;
            gas = true;
            temperature = 0.8f;
        }};

        //internal

        refineryFluid = new Liquid("refinery-fluid"){{
            color = Pal.redLight;
            lightColor = color.cpy().a(0.55f);
            gasColor = Pal.redLight;
            coolant = false;
            viscosity = 0.8f;
            temperature = 0.5f;
            hidden = true;
        }};
        refineryGas = new Liquid("refinery-gas"){{
            color = Pal.redLight;
            lightColor = color.cpy().a(0.55f);
            gasColor = Pal.redLight;
            coolant = false;
            temperature = 2.5f;
            gas = true;
            incinerable = false;
            hidden = true;
        }};
        moltenSilica = new Liquid("molten-silica"){{
            color = Color.valueOf("#f5a645");
            lightColor = color.cpy().a(0.55f);
            gasColor = Color.valueOf("#f5a645");
            coolant = false;
            viscosity = 0.8f;
            temperature = 1.2f;
            hidden = true;
        }};
    }
}
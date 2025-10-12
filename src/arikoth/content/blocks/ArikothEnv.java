package arikoth.content.blocks;

import arc.graphics.Color;
import arikoth.content.ArikothItems;
import arikoth.content.effects.ArikothFx;
import arikoth.world.blocks.env.*;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.entities.effect.ParticleEffect;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;

public class ArikothEnv {
    public static Block

            aCliff, aCliffHelper,

            aerenite, aereniteCraters, aereniteSand, aereniteSmooth,
            aereniteCratersLarge, aereniteCratersMassive,
            paleAerenite, paleAereniteSand, paleAereniteSmooth,
            aereniteWall, paleAereniteWall,
            // // // region mountains // // //
            greenmat,
            greenmatWall,
            glaciatedWater, deepGlaciatedWater,
            // // // region calderas // // //
            graystone, graystoneSlate, tephra, volcanicAndesite, volcanicAndesiteRock,
            graystoneCracked, volcanicAndesiteFissures,
            graystoneWall, tephraWall, volcanicAndesiteWall,
            graystoneVent, volcanicAndesiteVent,
            // // // region dead forests // // //

            // // // region warmer deserts // // //

            // // // region crystalline areas // // //

            // // // facility // // //
            rustedFacilityTiles, facilityTiles, facilityVent, facilityGrate,
            rustedFacilityWall, facilityWall,

            // // // region ores // // //
            graphiteOre, rustOre,
    end;

    public static void load(){

        aCliff = new ACliff("cliff");
        aCliffHelper = new ACliffHelper("cliff-helper");

        aerenite = new AFloor("aerenite", 4);
        aereniteCraters = new AFloor("aerenite-craters", 3){{blendGroup = aerenite;}};
        aereniteSand = new AFloor("aerenite-sand", 3);
        aereniteSmooth = new AFloor("aerenite-smooth", 4);
        aereniteCratersLarge = new SteamVent("aerenite-craters-large"){{blendGroup = aerenite; parent = aerenite; variants = 2; effect = Fx.none;}};
        aereniteCratersMassive = new SteamVent("aerenite-craters-massive"){{blendGroup = aerenite; parent = aerenite; variants = 2; effect = Fx.none;}};
        paleAerenite = new AFloor("pale-aerenite", 4);
        paleAereniteSand = new AFloor("pale-aerenite-sand", 6);
        paleAereniteSmooth = new AFloor("pale-aerenite-smooth", 4);
        // walls
        aereniteWall = new StaticWall("aerenite-wall"){{variants = 3;}};
        paleAereniteWall = new StaticWall("pale-aerenite-wall"){{variants = 3;}};

        greenmat = new AFloor("greenmat", 4);
        // walls
        greenmatWall = new StaticWall("greenmat-wall"){{variants = 3;}};
        // liquids
        glaciatedWater = new ShallowLiquid("glaciated-water"){{
            liquidDrop = Liquids.water;
            shallow = true;
            isLiquid = true;
            variants = 0;
            speedMultiplier = 0.8f;
        }};
        deepGlaciatedWater = new Floor("deep-glaciated-water", 0){{
            liquidDrop = Liquids.water;
            shallow = false;
            isLiquid = true;
            liquidMultiplier = 1.5f;
            drownTime = 240;
            speedMultiplier = 0.65f;
        }};

        graystone = new AFloor("graystone", 4);
        graystoneSlate = new AFloor("graystone-slate", 4);
        tephra = new AFloor("tephra", 3);
        volcanicAndesite = new AFloor("volcanic-andesite", 4);
        volcanicAndesiteRock = new AFloor("volcanic-andesite-rock", 4);
        graystone = new CrackedFloor("graystone-cracked"){{
            variants = 4;
            blendGroup = graystone;
            blinkTimeRange = 60 * 5;
            maxBlinkTime = 60 * 9;
        }};
        volcanicAndesiteFissures = new CrackedFloor("volcanic-andesite-fissures"){{
            variants = 3;
            blendGroup = graystone;
            blinkTimeRange = 60 * 5;
            maxBlinkTime = 60 * 9;
        }};
        //walls
        graystoneWall = new StaticWall("graystone-wall"){{variants = 3;}};
        tephraWall = new StaticTree("tephra-wall"){{variants = 3;}};
        volcanicAndesiteWall = new StaticWall("volcanic-andesite-wall"){{variants = 3;}};

        // // // facility // // //

        rustedFacilityTiles = new Floor("rusted-facility-tiles", 9){{
            drawEdgeIn = false;
            drawEdgeOut = false;
        }};
        facilityTiles = new Floor("facility-tiles", 5){{
            drawEdgeIn = false;
            drawEdgeOut = false;
        }};
        facilityVent = new EffectFloor("facility-air-vent"){{
            drawEdgeIn = false;
            drawEdgeOut = false;
            variants = 0;
            effect = ArikothFx.craftSmoke;
            effectChance = 0.008f;
            effectColor = Color.lightGray;
        }};
        facilityGrate = new EffectFloor("facility-grate"){{
            drawEdgeIn = false;
            drawEdgeOut = false;
            variants = 0;
        }};

        rustedFacilityWall = new StaticWall("rusted-facility-wall"){{
            variants = 2;
        }};
        facilityWall = new StaticWall("facility-wall"){{
            variants = 2;
        }};

        // // // ores // // //

        graphiteOre = new OreBlock("graphite-ore"){{
           variants = 3;
           itemDrop = Items.graphite;
        }};
        rustOre = new OreBlock("rusted-ore"){{
            variants = 3;
            itemDrop = ArikothItems.rust;
        }};
    }
}

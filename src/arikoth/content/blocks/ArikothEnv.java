package arikoth.content.blocks;

import arc.graphics.Color;
import arc.math.Interp;
import arikoth.content.ArikothItems;
import arikoth.content.effects.ArikothMiscFx;
import arikoth.content.effects.palettes.ArikothPal;
import arikoth.graphics.shaders.ArikothShaders;
import arikoth.world.blocks.env.*;
import arikoth.world.presets.misc.ArikothAttribute;
import mindustry.content.StatusEffects;
import mindustry.entities.effect.ParticleEffect;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Layer;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BuildVisibility;

import static arikoth.content.ArikothItems.aSilicon;
import static arikoth.content.ArikothLiquids.*;

public class ArikothEnv {
    public static Block

            aCliff, aCliffHelper,

            halite, calcite,

            solfatarInsideA, solfatarInsideB, solfatarInsideC,
            chromite,
            komatiite, weatheredKomatiite, smoothKomatiite,
            meimechite,
            dunite, smoothDunite,
            pyroxenite,
            serpentine, talc, solfatarite, nativeSulfur, olivine,
            obsidian,

            paleSandstone, paleRegolith, paleSandstoneVent, orangeSandstone, orangeRegolith, orangeSandstoneVent, crimsonSandstone, crimsonRegolith, crimsonSandstoneVent, tanRegolith,
            purpleRegolith, silicateRegolith, ashenRegolith, paleAshenRegolith, crystallineRegolith,

            basaltIcherPocket,

            paleRegolithShallow, dullRegolithShallow, orangeRegolithShallow, stainedWaterShallower, stainedWaterShallow, stainedWaterTile1, stainedWaterTile2, stainedWaterDeep,
            paleTibaArundoRoots, orangeTibaArundoRoots,

            bloodWaterShallow, bloodWaterMid, bloodWater, submergedSanguineClay, submergedSanguineRock,

            //dunes

            paleDuneD, paleDuneL,
            orangeDuneD, orangeDuneL,

            // walls
            komatiiteWall, duniteWall, serpentineWall, talcWall, olivineWall, obsidianWall,
            paleRegolithWall, orangeRegolithWall, crimsonRegolithWall,
            sandstoneWall, cinnabarWall, sulfuricWall,
            ligniteWall, ashWall, volcanicSootWall, aSaltWall, aSaltOutcrop,
            // misc
            darknessBlob,

            // // // region props // // //
            crimsonReed, crimsonReedLarge, deadBush, crimsonWeed,
            canistrumPlant, largeCanistrumPlant,
            spiralBush,
            caerlumFern, caerlumPlant, caerlumTree,
            auraLetalis, arborRubedo,
            tibiaArundo, tibiaArundoRoots,
            altisMoss,
            quartzCrystal, quartzCrystalLarge, quartzCrystalMassive,
            olivineCluster,
            basaltCluster, basaltRock, largeBasaltRock, massiveBasaltRock,
            smallSilicateRock, silicateRock,

            // // // region ores // // //
            anthraciteOre, tungstenOre, tantalumOre, rheniumOre, vanadiumOre, rheniumDeposit, siliconOre, rheniiteOre, rhodusiteOre,

            paleRegolithOverlay, dullRegolithOverlay, orangeRegolithOverlay, rustRegolithOverlay, crimsonRegolithOverlay, tanRegolithOverlay, silicateRegolithOverlay,
            basaltOverlay, rustedBasaltDrift, sandstoneOverlay, ashOverlay, saltOverlay,
    end;

    public static void load(){

        aCliff = new ACliff("cliff");
        aCliffHelper = new ACliffHelper("cliff-helper");

        // // // water // // //
        stainedWaterDeep = new Floor("deep-stained-water"){{
            speedMultiplier = 1;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 1f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
            drownTime = 400;
        }};
        stainedWaterTile2 = new Floor("stained-water-tile2"){{
            speedMultiplier = 0.6f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.9f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
        stainedWaterTile1 = new Floor("stained-water-tile1"){{
            speedMultiplier = 0.6f;
            variants = 0;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.9f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
        stainedWaterShallower = new Floor("shallow-stained-water"){{
            speedMultiplier = 0.75f;
            variants = 3;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.7f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
        paleRegolithShallow = new Floor("pale-regolith-shallow"){{
            speedMultiplier = 0.8f;
            variants = 4;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.5f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
        dullRegolithShallow = new Floor("shallow-dull-regolith"){{
            speedMultiplier = 0.8f;
            variants = 4;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.5f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
        orangeRegolithShallow = new Floor("orange-regolith-shallow"){{
            speedMultiplier = 0.8f;
            variants = 4;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.5f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};
        orangeTibaArundoRoots = new Floor("tibia-arundo-roots-orange"){{
            speedMultiplier = 0.8f;
            variants = 3;
            status = StatusEffects.wet;
            statusDuration = 90f;
            liquidDrop = stainedWater;
            liquidMultiplier = 0.5f;
            isLiquid = true;
            cacheLayer = CacheLayer.water;
            albedo = 0.9f;
            supportsOverlay = true;
        }};

        paleSandstone = new AFloor("pale-sandstone", 3){{
            cliffDarkColor = Color.valueOf("bda580");
            cliffLightColor = Color.valueOf("f0deb6");
            itemDrop = ArikothItems.regolith;
            playerUnmineable = true;
            attributes.set(Attribute.sand, 1);
        }};
        paleSandstoneVent = new SteamVent("pale-sandstone-vent"){{
            parent = blendGroup = paleSandstone;
            effect = new ParticleEffect(){{
                sizeFrom = 0;sizeTo = 8;sizeInterp = Interp.slope;

                lifetime = 300;interp = Interp.pow3Out;

                colorFrom = Color.valueOf("fde756").a(0.6f);
                colorTo = Color.valueOf("e8a541").a(0);

                particles = 2;length = 120;useRotation = true;cone = 6;baseRotation = 32;
            }};
            attributes.set(ArikothAttribute.icher, 1);
            attributes.set(Attribute.steam, 1);
        }};

        orangeSandstone = new AFloor("orange-sandstone", 3){{
            cliffDarkColor = Color.valueOf("a34f40");
            cliffLightColor = Color.valueOf("e88d62");
            itemDrop = ArikothItems.regolith;
            playerUnmineable = true;
            attributes.set(Attribute.sand, 1);
        }};
        orangeSandstoneVent = new SteamVent("orange-sandstone-vent"){{
            parent = blendGroup = orangeSandstone;
            effect = new ParticleEffect(){{
                sizeFrom = 0;sizeTo = 8;sizeInterp = Interp.slope;

                lifetime = 300;interp = Interp.pow3Out;

                colorFrom = Color.valueOf("fde756").a(0.6f);
                colorTo = Color.valueOf("e8a541").a(0);

                particles = 2;length = 120;useRotation = true;cone = 6;baseRotation = 32;
            }};
            attributes.set(ArikothAttribute.icher, 1);
            attributes.set(Attribute.steam, 1);
        }};

        crimsonSandstone = new AFloor("crimson-sandstone", 3){{
            cliffLightColor = Color.valueOf("cc807e");
            itemDrop = ArikothItems.regolith;
            playerUnmineable = true;
            attributes.set(Attribute.sand, 1);
        }};
        crimsonSandstoneVent = new SteamVent("crimson-sandstone-vent"){{
            parent = blendGroup = crimsonSandstone;
            effect = new ParticleEffect(){{
                sizeFrom = 0;sizeTo = 8;sizeInterp = Interp.slope;

                lifetime = 300;interp = Interp.pow3Out;

                colorFrom = Color.valueOf("fde756").a(0.6f);
                colorTo = Color.valueOf("e8a541").a(0);

                particles = 2;length = 120;useRotation = true;cone = 6;baseRotation = 32;
            }};
            attributes.set(ArikothAttribute.icher, 1);
            attributes.set(Attribute.steam, 1);
        }};

        halite = new AFloor("halite"){{
            variants = 3;
            cliffLightColor = Color.valueOf("f5f3f2");
        }};
        calcite = new AFloor("calcite"){{
            variants = 3;
            cliffLightColor = Color.valueOf("f5f3f2");
        }};

        solfatarInsideA = new AFloor("solfatar-inside-a", 3){{
            canShadow = false;
            placeableOn = false;
            solid = true;
            cacheLayer = ArikothShaders.solfatarshimmerlayer;

            emitLight = true;
            lightColor = ArikothPal.blackbody2.cpy().a(0.5f);
            lightRadius = 45;

            effectTile = true;
            effectChance = 0.005f;
            effect = ArikothMiscFx.ventSteam;
        }};
        solfatarInsideB = new AFloor("solfatar-inside-b", 3){{
            canShadow = false;
            placeableOn = false;
            solid = true;
            cacheLayer = ArikothShaders.solfatarshimmerlayer;

            emitLight = true;
            lightColor = ArikothPal.blackbody2.cpy().a(0.5f);
            lightRadius = 45;

            effectTile = true;
            effectChance = 0.005f;
            effect = ArikothMiscFx.ventSteam;
        }};
        solfatarInsideC = new AFloor("solfatar-inside-c", 0){{
            canShadow = false;
            placeableOn = false;
            solid = true;
            cacheLayer = ArikothShaders.solfatarshimmerlayer;

            emitLight = true;
            lightColor = ArikothPal.blackbody2.cpy().a(0.5f);
            lightRadius = 45;

            effectTile = true;
            effectChance = 0.005f;
            effect = ArikothMiscFx.ventSteam;
        }};

        chromite = new AFloor("chromite", 4);
        komatiite = new AFloor("komatiite", 5, Color.valueOf("948972"), Color.valueOf("4a3c33"));
        weatheredKomatiite = new AFloor("weathered-komatiite", 3, Color.valueOf("948972"), Color.valueOf("4a3c33"));
        smoothKomatiite = new AFloor("smooth-komatiite", 5, Color.valueOf("948972"), Color.valueOf("4a3c33"));
        meimechite = new AFloor("meimechite", 0){{
            cacheLayer = ArikothShaders.meimechiteLayer;
        }};
        dunite = new AFloor("dunite", 4, Color.valueOf("aaac8e"), Color.valueOf("544c3a"));
        smoothDunite = new AFloor("smooth-dunite", 4, Color.valueOf("aaac8e"), Color.valueOf("544c3a"));
        pyroxenite = new AFloor("pyroxenite", 3, Color.valueOf("5f6259"), Color.valueOf("2a2e27"));

        serpentine = new AFloor("serpentine", 5);
        solfatarite = new AFloor("solfatarite", 4);
        talc = new AEdgeFloor("talc", 4);

        //cheese
        nativeSulfur = new AEdgeFloor("native-sulfur", 3, Color.valueOf("faeaa5"), Color.valueOf("aa894c"));

        olivine = new AFloor("olivine"){{
            variants = 4;
            cliffLightColor = Color.valueOf("756d5b");
        }};
        obsidian = new AFloor("obsidian"){{
            variants = 4;
            cliffLightColor = Color.valueOf("756d5b");
        }};

        paleRegolith = new AFloor("pale-regolith", 6){{
            cliffLightColor = Color.valueOf("fff0cf");
            itemDrop = ArikothItems.regolith;
            playerUnmineable = true;
            attributes.set(Attribute.sand, 1);
        }};

        orangeRegolith = new AEdgeFloor("orange-regolith", 4){{
            cliffLightColor = Color.valueOf("ffb47a");
            itemDrop = ArikothItems.regolith;
            playerUnmineable = true;
            attributes.set(Attribute.sand, 1);
        }};

        crimsonRegolith = new AEdgeFloor("crimson-regolith", 4){{
            cliffLightColor = Color.valueOf("ff8a7a");
            itemDrop = ArikothItems.regolith;
            playerUnmineable = true;
        }};

        // // // dunes // // //

        paleDuneD = new Floor("pale-dune-d", 0){{
            solid = true;
            placeableOn = false;
        }};
        paleDuneL = new Floor("pale-dune-l", 0){{
            solid = true;
            placeableOn = false;
        }};

        orangeDuneD = new Floor("orange-dune-d", 0){{
            solid = true;
            placeableOn = false;
        }};
        orangeDuneL = new Floor("orange-dune-l", 0){{
            solid = true;
            placeableOn = false;
        }};

        // // // walls // // //
        komatiiteWall = new StaticWall("komatiite-wall"){{variants = 2;}};
        duniteWall = new StaticWall("dunite-wall"){{variants = 2;}};
        talcWall = new StaticTree("talc-wall"){{variants = 2;}};
        serpentineWall = new StaticTree("serpentine-wall"){{variants = 2;}};
        olivineWall = new StaticWall("olivine-wall"){{variants = 3;}};
        obsidianWall = new StaticWall("obsidian-wall"){{variants = 3;}};

        paleRegolithWall = new StaticWall("pale-regolith-wall"){{variants = 3;forceDark = false;ignoreBuildDarkness = true;}};
        orangeRegolithWall = new StaticWall("orange-regolith-wall"){{variants = 3;forceDark = false;ignoreBuildDarkness = true;}};
        crimsonRegolithWall = new StaticWall("crimson-regolith-wall"){{variants = 3;forceDark = false;ignoreBuildDarkness = true;}};

        sandstoneWall = new StaticWall("sandstone-wall"){{variants = 3;}};
        cinnabarWall = new StaticWall("cinnabar-wall"){{variants = 3;}};
        sulfuricWall = new StaticWall("sulfuric-wall"){{variants = 3;}};

        ligniteWall = new StaticWall("lignite-wall"){{variants = 3;}};
        ashWall = new StaticWall("ash-wall"){{variants = 2;}};
        volcanicSootWall = new StaticWall("volcanic-soot-wall"){{variants = 3;}};
        aSaltOutcrop = new StaticTree("halite-wall"){{variants = 2;}};
        aSaltWall = new StaticWall("a-salt-wall"){{variants = 2;}};

        rheniumDeposit = new TallBlock("rhenium-deposit"){{variants = 2; itemDrop = ArikothItems.vanadium;}};

        // // // misc // // //
        darknessBlob = new SupportPillar("darkness-blob"){{
            variants = 0;
            lightColor = Color.valueOf("02020a");
            lightRadius = 10*8;
            emitLight = true;
        }};

        // // // props // // //
        crimsonReed = new SeaBush("crimson-reed"){{
            lobesMax = 5;
            lobesMin = 3;
            buildTime = 60;
            customShadow = true;
            instantDeconstruct = false;
        }};
        crimsonReedLarge = new SeaBush("crimson-reed-large"){{
            lobesMax = 6;
            lobesMin = 4;
            sclMax = 80; sclMin = 60;
            magMax = 8; magMin = 1.5f;
            buildTime = 120;
            instantDeconstruct = false;
        }};
        deadBush = new WobbleProp("dead-bush"){{
            variants = 2;
            buildTime = 60;
            instantDeconstruct = false;
        }};
        spiralBush = new SeaBush("spiral-bush"){{
            lobesMax = 6;
            lobesMin = 5;
            sclMax = 80; sclMin = 60;
            magMax = 8; magMin = 1.5f;
            buildTime = 60;
            instantDeconstruct = false;
        }};
        canistrumPlant = new TallBlock("canistrum-plant"){{
            variants = 2;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 300;
            shadowOffset = -1.5f;
            size = 1;
            targetable = false;
            health = 1200;
            rotationRand = 0;
        }};
        largeCanistrumPlant = new TallBlock("large-canistrum-plant"){{
            variants = 2;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 480;
            shadowOffset = -1.5f;
            size = 3;
            targetable = false;
            rotationRand = 0;
        }};
        caerlumFern = new Pseudo3DFern("caerlum-fern"){{
            segments = 10;
            minHeight = 0.02f;
            maxHeight = 0.035f;
            clipSize = 128;
            leafLayer = Layer.groundUnit + 0;
            layer = Layer.legUnit + 0.5f;
        }};
        caerlumPlant = new Pseudo3DTree("caerlum-tree"){{
            variants = 3;
            segments = 10;
            minHeight = 0.06f;
            maxHeight = 0.08f;
            layer = Layer.legUnit + 1f;
            leafLayer = Layer.groundUnit + 0.5f;
        }};

        auraLetalis = new SeaBush("aura-letalis"){{
            lobesMax = 5;
            lobesMin = 3;
        }};
        arborRubedo = new TreeBlock("arbor-rubedo"){{
            variants = 2;
        }};

        tibiaArundo = new FlatTree("tibia-arundo"){{
            segments = 10;
            minHeight = 0.09f;
            maxHeight = 0.12f;
            layer = Layer.legUnit + 3f;
            leafLayer = Layer.groundUnit + 0.25f;
        }};

        tibiaArundoRoots = new TallBlock("tibia-arundo-roots"){{
            variants = 3;
            shadowOffset = -1;
        }};

        altisMoss = new WobbleTallBlock("altis-moss"){{
            variants = 2;
            shadowOffset = -1;
            wobbleMag = 1.5f;
            wobbleScl = 300;
            shadowAlpha = 0.3f;
        }};

        crimsonWeed = new WobbleProp("crimson-weed"){{
            variants = 4;
            wscl = 40;
            wmag = 1.3f;
            wtscl = 15;
            wmag2 = 0.6f;
            hasShadow = false;
            instantDeconstruct = true;
            alwaysReplace = true;
            health = 80;
            placeableLiquid = true;
        }};

        // non-organic props //

        quartzCrystal = new TallBlock("quartz-crystal"){{
            variants = 2;
            shadowOffset = -1;
            attributes.set(ArikothAttribute.quartz, 1);
        }};
        quartzCrystalLarge = new TallBlock("quartz-crystal-large"){{
            variants = 2;
            shadowOffset = -1.5f;
            layer = Layer.power + 5;
            shadowLayer = Layer.power + 4;
            attributes.set(ArikothAttribute.quartz, 1);
        }};
        quartzCrystalMassive = new TallBlock("quartz-crystal-massive"){{
            variants = 2;
            shadowOffset = -2;
            layer = Layer.power + 10;
            shadowLayer = Layer.power + 9;
            attributes.set(ArikothAttribute.quartz, 1);
        }};
        olivineCluster = new TallBlock("olivine-cluster"){{
            variants = 3;
            shadowOffset = -1.5f;
            shadowAlpha = 0.65f;
        }};
        basaltCluster = new TallBlock("basalt-cluster"){{
            variants = 4;
            shadowOffset = -2;
            layer = Layer.power + 5;
            shadowLayer = Layer.power + 4;
        }};
        basaltRock = new TallBlock("basalt-rock"){{
            requirements(Category.defense, BuildVisibility.editorOnly, ItemStack.with(aSilicon, 5 * 2));
            variants = 3;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 600;
            shadowOffset = 0;
            shadowAlpha = 0.4f;
            size = 1;
            squareSprite = false;
            targetable = false;
            rotationRand = 0;
            customShadow = true;
        }};
        largeBasaltRock = new TallBlock("large-basalt-rock"){{
            requirements(Category.defense, BuildVisibility.editorOnly, ItemStack.with(aSilicon, 5 * 9 * 2));
            variants = 2;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 600 * 9;
            shadowOffset = 0;
            shadowAlpha = 0.4f;
            size = 3;
            squareSprite = false;
            targetable = false;
            rotationRand = 0;
            customShadow = true;
        }};
        massiveBasaltRock = new TallBlock("massive-basalt-rock"){{
            requirements(Category.defense, BuildVisibility.editorOnly, ItemStack.with(aSilicon, 5 * 25 * 2));
            variants = 2;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 600 * 25;
            shadowOffset = 0;
            shadowAlpha = 0.4f;
            size = 5;
            squareSprite = false;
            targetable = false;
            rotationRand = 0;
            customShadow = true;
        }};

        /*smallSilicateRock = new TallBlock("small-silicate-rock"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.aSilicon, 900, ArikothItems.aluminum, 120, ArikothItems.magnesium, 68));
            buildVisibility = BuildVisibility.editorOnly;
            variants = 2;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 3600;
            shadowOffset = 0;
            shadowAlpha = 0.4f;
            size = 3;
            squareSprite = false;
            targetable = false;
            rotationRand = 0;
            customShadow = true;
        }};
        silicateRock = new TallBlock("silicate-rock"){{
            requirements(Category.distribution, ItemStack.with(ArikothItems.aSilicon, 1200, ArikothItems.aluminum, 160, ArikothItems.magnesium, 90));
            buildVisibility = BuildVisibility.editorOnly;
            variants = 2;
            solid = update = destructible = true;
            instantDeconstruct = drawTeamOverlay = allowDerelictRepair = alwaysReplace = false;
            buildTime = 5400;
            shadowOffset = 0;
            shadowAlpha = 0.4f;
            size = 5;
            squareSprite = false;
            targetable = false;
            rotationRand = 0;
            customShadow = true;
        }};*/

        // // // ores // // //

        vanadiumOre = new OreBlock("vanadium-ore"){{
            variants = 4;
            itemDrop = ArikothItems.vanadium;
        }};
        tantalumOre = new OreBlock("tantalum-ore"){{
            variants = 4;
            itemDrop = ArikothItems.tantalum;
        }};
        tungstenOre = new OreBlock("aTungsten-ore"){{
            variants = 4;
            itemDrop = ArikothItems.aTungsten;
        }};
        //wip content
        rheniumOre = new OreBlock("rhenium-ore"){{
            variants = 3;
            itemDrop = ArikothItems.rhenium;
        }};
        siliconOre = new OreBlock("silicon-ore"){{
            variants = 3;
            itemDrop = aSilicon;
        }};
        anthraciteOre = new OreBlock("anthracite-ore"){{
           variants = 3;
           itemDrop = ArikothItems.anthracite;
        }};
        rheniiteOre = new OreBlock("rheniite-ore"){{
            variants = 3;
            itemDrop = ArikothItems.rheniite;
        }};
        rhodusiteOre = new OreBlock("rhodusite-ore"){{
            variants = 3;
            itemDrop = ArikothItems.vanathite;
        }};

        paleRegolithOverlay = new OverlayFloor("pale-regolith-overlay"){{
            variants = 3;
        }};
        orangeRegolithOverlay = new OverlayFloor("orange-regolith-overlay"){{
            variants = 3;
        }};
        crimsonRegolithOverlay = new OverlayFloor("crimson-regolith-overlay"){{
            variants = 3;
        }};

        basaltOverlay = new OverlayFloor("basalt-overlay"){{
            variants = 3;
        }};
        rustedBasaltDrift = new OverlayFloor("rusted-basalt-drifts"){{
            variants = 3;
        }};
        sandstoneOverlay = new OverlayFloor("sandstone-overlay"){{
            variants = 3;
        }};
        ashOverlay = new OverlayFloor("ash-overlay"){{
            variants = 3;
        }};
        saltOverlay = new OverlayFloor("salt-overlay"){{
            variants = 3;
        }};
    }
}

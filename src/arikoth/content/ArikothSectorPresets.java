package arikoth.content;

import arikoth.content.ArikothPlanets;
import mindustry.type.*;

public class ArikothSectorPresets {
    public static SectorPreset arikothTestMap;

    public static void load() {
        arikothTestMap = new SectorPreset("arikoth-test-map", ArikothPlanets.arikoth, 5) {{
            alwaysUnlocked = true;
            ArikothPlanets.arikoth.startSector = sector.id;
        }};
    }
}
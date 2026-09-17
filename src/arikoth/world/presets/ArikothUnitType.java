package arikoth.world.presets;

import arc.*;
import arc.graphics.g2d.*;
import arikoth.content.effects.palettes.ArikothPal;
import mindustry.gen.Hitboxc;
import mindustry.type.*;

public class ArikothUnitType extends UnitType {
    /**
     * credits to @liz
     */

    //general unit stuff

    public int wrecks = -1;

    public ArikothUnitType(String name) {
        super(name);
        outlineColor = ArikothPal.outline;
    }

    public <Rect extends Hitboxc> void hitboxTile(Rect rect) {
        float size = hitSize * 0.85f;
    }

    @Override
    public void load() {
        super.load();

        if (wrecks > 0) {
            wreckRegions = new TextureRegion[wrecks];
            for(int i = 0; i < wrecks; i++) {
                wreckRegions[i] = Core.atlas.find(name + "-wreck-" + (i + 1));
            }
        }
    }
}
package arikoth.world.blocks;

import arc.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.draw.*;

// something's approaching something's approaching something's approaching something's approaching something's approaching something's approaching something's approaching something's approaching
public class DrawerCore extends CoreBlock {
    public DrawBlock drawer;

    public DrawerCore(String name) {
        super(name);
    }

    @Override
    public void load() {
        super.load();
        drawer.load(this);
    }

    @Override
    public TextureRegion[] icons() {
        return drawer.finalIcons(this);
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        drawer.drawPlan(this, plan, list);
    }

    public class DrawerCoreBuild extends CoreBuild {
        @Override
        public void draw() {
            drawer.draw(this);
        }

        @Override
        public float totalProgress() {
            return totalProgress();
        }
    }
}
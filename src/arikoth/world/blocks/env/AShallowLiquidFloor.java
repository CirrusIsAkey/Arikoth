package arikoth.world.blocks.env;

import arc.Core;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import arikoth.world.blocks.env.AFloor;
import mindustry.graphics.*;
import mindustry.graphics.MultiPacker.*;
import mindustry.world.*;

/**
 * Blends water together with a standard floor. No new mechanics.
 * */
public class AShallowLiquidFloor extends AFloor {
    public String liquidBase, floorBase;
    public float liquidOpacity = 0.35f;
    public int liqVar, floorVar;

    TextureRegion[] liqVars, floorVars;

    public AShallowLiquidFloor(String name){
        super(name);
    }

    public void set(Block liquid, Block floor){
        isLiquid = true;
        shallow = true;
    }

    @Override
    public void load() {
        if(liqVar != 0) {
            liqVars = new TextureRegion[liqVar];

            for (int i = 0; i < liqVar; i++) {
                liqVars[i] = Core.atlas.find(liquidBase + (i + 1));
            }
        }

        if(floorVar != 0) {
            floorVars = new TextureRegion[floorVar];

            for (int i = 0; i < floorVar; i++) {
                liqVars[i] = Core.atlas.find(floorBase + (i + 1));
            }
        }
    }

    @Override
    public void createIcons(MultiPacker packer){
        //TODO might not be necessary at all, but I am not sure yet
        //super.createIcons(packer);
    }
}
package arikoth.world.presets.stat;

import mindustry.world.meta.*;

public class ArikothStat{
    public static final Stat
            maxdamagemultiplier = new Stat("maxdamagemultiplier"),
            maxspeedmultiplier = new Stat("maxspeedmultiplier"),
            maxreloadmultiplier = new Stat("maxreloadmultiplier"),
            recipes = new Stat("recipes", StatCat.crafting),
            contents = new Stat("contents"),
            inputEdge = new Stat("input-edge", StatCat.crafting),
            inputCorner = new Stat("input-corner", StatCat.crafting),
            used = new Stat("arikoth-used", StatCat.crafting);

}

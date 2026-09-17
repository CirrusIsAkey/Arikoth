package arikoth.world.blocks.env;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.world.Tile;

public class EffectFloor extends AFloor {

    public Effect effect = Fx.ventSteam;
    public Color effectColor = Pal.vent;
    public float effectChance = 1.2f;

    public EffectFloor(String name){
        super(name);
    }

    @Override
    public boolean updateRender(Tile tile){
        return true;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if (Mathf.chanceDelta(effectChance) && state.tile.block() == Blocks.air) {
            effect.at(state.tile.worldx(), state.tile.worldy(), effectColor);
        }
    }
}
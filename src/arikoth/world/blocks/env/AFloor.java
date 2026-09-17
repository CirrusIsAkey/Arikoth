package arikoth.world.blocks.env;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Floor;

//author @Andromeda
public class AFloor extends Floor {
    public Color cliffLightColor;
    public Color cliffDarkColor;

    public Effect effect = Fx.ventSteam;
    public Color effectColor = Pal.vent;
    public float effectChance = 1.2f;
    public Boolean effectTile = false;

    public AFloor(String name) {
        super(name);
    }

    public AFloor(String name, int variants) {
        super(name, variants);
    }

    public AFloor(String name, int variants, Color cliffLightColor, Color cliffDarkColor) {
        super(name, variants);
        this.cliffDarkColor = cliffDarkColor;
        this.cliffLightColor = cliffLightColor;
    }

    @Override
    public boolean updateRender(Tile tile){
        return effectTile;
    }

    @Override
    public void renderUpdate(UpdateRenderState state) {
        if (Mathf.chanceDelta(effectChance) && state.tile.block() == Blocks.air && effectTile) {
            effect.at(state.tile.worldx(), state.tile.worldy(), effectColor);
        }
    }
}

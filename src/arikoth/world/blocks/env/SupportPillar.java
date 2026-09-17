package arikoth.world.blocks.env;

import arc.graphics.g2d.Fill;
import arikoth.graphics.shaders.ArikothLightRendererLoad;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.TallBlock;

public class SupportPillar extends TallBlock {

    public SupportPillar(String name) {
        super(name);
    }

    @Override
    public int minimapColor(Tile tile) {
        return mapColor.rgba();
    }

    public class SupportPillarBuild extends Building {
        public void drawLight(Tile tile) {
            ArikothLightRendererLoad.thingo.shadow(() -> {
                Fill.light(tile.worldx(), tile.worldy(), 360, lightRadius, lightColor, lightColor.cpy().a(0));
            });
        }
    }
}

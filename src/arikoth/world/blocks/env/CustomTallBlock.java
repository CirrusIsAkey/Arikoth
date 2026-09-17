package arikoth.world.blocks.env;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import mindustry.Vars;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.TallBlock;

import static arc.Core.settings;

public class CustomTallBlock extends TallBlock {

    public float fadeStart = 50f;
    public float fadeEnd = 15f;

    public CustomTallBlock(String name){
        super(name);
        solid = false;
        clipSize = 90;
        customShadow = true;
    }

    @Override
    public void drawBase(Tile tile){
        float fade = 1f;

        if (settings.getBool("@setting.arikoth-fade-enabled") && (Vars.player.unit() != null && !Vars.player.unit().dead())){
            float fadeOpacity = settings.getInt("@setting.arikoth-fade-opacity") / 100f;
            float dst;

            if(settings.getBool("@setting.arikoth-toggle-mouse-fade")){
                Vec2 mouse = Core.input.mouseWorld(Core.input.mouseX(), Core.input.mouseY());
                dst = Mathf.dst(mouse.x, mouse.y, tile.worldx(), tile.worldy());
            } else {
                dst = Mathf.dst(Vars.player.unit().x, Vars.player.unit().y, tile.worldx(), tile.worldy());
            }
            fade = Mathf.clamp((dst - fadeEnd) / (fadeStart - fadeEnd), fadeOpacity, 1f);
        }

        Draw.z(shadowLayer);
        Draw.color(0f, 0f, 0f, shadowAlpha);
        Draw.rect(variants > 0 ? variantShadowRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantShadowRegions.length - 1))] : customShadowRegion,
                tile.worldx() + shadowOffset, tile.worldy() + shadowOffset);

        Draw.color();

        Draw.alpha(fade);
        Draw.z(layer);
        Draw.rect(variants > 0 ? variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))] : region, tile.worldx(), tile.worldy());
    }

    @Override
    public void drawShadow(Tile tile){

    }

    @Override
    public TextureRegion[] icons(){
        return variants == 0 ? super.icons() : new TextureRegion[]{Core.atlas.find(name + "1")};
    }
}

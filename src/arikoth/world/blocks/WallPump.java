package arikoth.world.blocks;


import arc.*;
import arc.func.*;
import arc.graphics.g2d.*;
import arc.graphics.*;
import arc.math.geom.Geometry;
import arc.util.*;
import mindustry.entities.units.*;
import mindustry.game.*;
import mindustry.type.Liquid;
import mindustry.world.*;
import mindustry.world.blocks.production.Pump;

import static arikoth.world.meta.ArikothAttribute.icher;
import static mindustry.Vars.*;




public class WallPump extends Pump {
    /** how much grotto walls it needs in front of it to work */
    public int minGrottoWalls;

    public WallPump(String name){
        super(name);
        rotate = true;
        rotateDraw = false;
        hasLiquids = true;
        squareSprite = false;
    }


    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
        drawer.drawPlan(this, plan, list);
    }

    int getEfficiency(int tx, int ty, int rotation, @Nullable Cons<Tile> ctile, @Nullable Intc2 cpos){
        int eff = 0;
        int cornerX = tx - (size-1)/2, cornerY = ty - (size-1)/2, s = size;

        for(int i = 0; i < size; i++){
            int rx = 0, ry = 0;

            switch(rotation){
                case 0 -> {
                    rx = cornerX + s;
                    ry = cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    ry = cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    ry = cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    ry = cornerY - 1;
                }
            }

            if(cpos != null){
                cpos.get(rx, ry);
            }

            Tile other = world.tile(rx, ry);
            eff += (int)other.block().attributes.get(icher);
        }
        return eff;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        Tile tile = world.tile(x, y);
        if(tile == null) return;

        float amount = 0f;
        Liquid liquidDrop = null;

        for(Tile other : tile.getLinkedTilesAs(this, tempTiles)){
            if(canPump(other)){
                if(liquidDrop != null && other.overlay().liquidDrop != liquidDrop){
                    liquidDrop = null;
                    break;
                }
                liquidDrop = other.overlay().liquidDrop;
                amount += other.overlay().liquidMultiplier;
            }
        }

        if(liquidDrop != null){
            float width = drawPlaceText(Core.bundle.formatFloat("bar.pumpspeed", amount * pumpAmount * 60f, 0), x, y, valid);
            float dx = x * tilesize + offset - width/2f - 4f, dy = y * tilesize + offset + size * tilesize / 2f + 5, s = iconSmall / 4f;
            float ratio = (float)liquidDrop.fullIcon.width / liquidDrop.fullIcon.height;
            Draw.mixcol(Color.darkGray, 1f);
            Draw.rect(liquidDrop.fullIcon, dx, dy - 1, s * ratio, s);
            Draw.reset();
            Draw.rect(liquidDrop.fullIcon, dx, dy, s * ratio, s);
        }
    }



    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(isMultiblock()){
            Liquid last = null;
            for(Tile other : tile.getLinkedTilesAs(this, tempTiles)){
                if(other.overlay().liquidDrop == null) continue;
                if(other.overlay().liquidDrop != last && last != null) return false;
                last = other.overlay().liquidDrop;
            }
            return last != null && getEfficiency(tile.x, tile.y, rotation, null, null) >= minGrottoWalls;
        }else{
            return canPump(tile) && getEfficiency(tile.x, tile.y, rotation, null, null) >= minGrottoWalls;
        }
    }

    @Override
    public boolean rotatedOutput(int x, int y) {
        return false;
    }

    @Override
    protected boolean canPump(Tile tile){
        return tile != null && tile.overlay().liquidDrop != null;
    }

    public class WallPumpBuild extends PumpBuild {

        @Override
        public void onProximityUpdate(){
            super.onProximityUpdate();

            amount = 0f;
            liquidDrop = null;

            for(Tile other : tile.getLinkedTiles(tempTiles)){
                if(canPump(other)){
                    liquidDrop = other.overlay().liquidDrop;
                    amount += other.overlay().liquidMultiplier;
                }
            }
        }
    }
}

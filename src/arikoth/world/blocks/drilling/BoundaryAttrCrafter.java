package arikoth.world.blocks.drilling;

import arc.math.geom.Point2;
import arc.struct.Seq;
import arikoth.world.blocks.power.BoundarySolarPanel;
import mindustry.game.Team;
import mindustry.input.Placement;
import mindustry.world.Tile;
import mindustry.world.blocks.production.AttributeCrafter;

import static mindustry.Vars.world;

public class BoundaryAttrCrafter extends AttributeCrafter {
    public int spacing = 6;

    public BoundaryAttrCrafter(String name){super(name);}

    public boolean intersectsSpacing(int sx, int sy, int ox, int oy, int ext){ //TODO untested with panels larger than 1x1
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        return ox >= sx - spacing + off - ext && ox <= sx + spacing + ext &&
                oy >= sy - spacing + off - ext && oy <= sy + spacing + ext;
    }

    public boolean intersectsSpacing(Tile self, Tile other){
        return intersectsSpacing(self.x, self.y, other.x, other.y, 0);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        //make sure there's enough efficiency at this location
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        for(int x = tile.x - spacing + off; x <= tile.x + spacing ; x++){
            for(int y = tile.y - spacing + off; y <= tile.y + spacing; y++){
                Tile t = world.tile(x, y);
                if(t != null && t.block() instanceof BoundaryAttrCrafter s && (s == this || s.intersectsSpacing(t.build.tile, tile))) return false;
            }
        }
        return baseEfficiency + tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attribute)) >= minEfficiency;
    }

    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation){
        if(spacing >= 1) Placement.calculateNodes(points, this, rotation, (point, other) -> intersectsSpacing(point.x, point.y, other.x, other.y, 1));
    }
}

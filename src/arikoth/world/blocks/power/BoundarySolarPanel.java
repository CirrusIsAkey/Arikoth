package arikoth.world.blocks.power;

import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Time;
import mindustry.game.Team;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.world.Tile;
import mindustry.world.blocks.power.SolarGenerator;
import mindustry.world.meta.Attribute;

import static mindustry.Vars.*;

public class BoundarySolarPanel extends SolarGenerator {
    public int spacing = 3;
    public BoundarySolarPanel(String name){
        super(name);
    }

    @Override
    public void drawOverlay(float x, float y, int rotation){
        if(spacing < 1) return;
        Drawf.dashSquare(Pal.remove, x, y, (spacing + size / 2f + 2) * tilesize);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(spacing < 1) return true;
        int off = 1 - size % 2;
        for(int x = tile.x - spacing + off; x <= tile.x + spacing ; x++){
            for(int y = tile.y - spacing + off; y <= tile.y + spacing; y++){
                Tile t = world.tile(x, y);
                if(t != null && t.block() instanceof BoundarySolarPanel s && (s == this || s.intersectsSpacing(t.build.tile, tile))) return false;
            }
        }
        return true;
    }

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
    public void changePlacementPath(Seq<Point2> points, int rotation){
        if(spacing >= 1) Placement.calculateNodes(points, this, rotation, (point, other) -> intersectsSpacing(point.x, point.y, other.x, other.y, 1));
    }
}

package arikoth.world.blocks.logistics;

import arc.Core;
import arc.graphics.g2d.*;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.util.*;
import mindustry.core.Renderer;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.blocks.distribution.DirectionBridge;
import mindustry.world.blocks.liquid.*;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class ArikothLiquidBridge extends DirectionBridge {
    public final int timerFlow = timers++;

    public float speed = 5f;
    public float liquidPadding = 1f;

    public boolean fadeIn = true;
    public boolean moveArrows = true;
    public boolean pulse = false;
    public float arrowSpacing = 4f, arrowOffset = 2f, arrowPeriod = 0.4f;
    public float arrowTimeScl = 6.2f;
    public float bridgeWidth = 6.5f;

    public ArikothLiquidBridge(String name){
        super(name);

        outputsLiquid = true;
        group = BlockGroup.liquids;
        canOverdrive = false;
        liquidCapacity = 20f;
        hasLiquids = true;

        //point2 config is relative
        config(Point2.class, (ArikothLiquidBridgeBuild tile, Point2 i) -> tile.link = Point2.pack(i.x + tile.tileX(), i.y + tile.tileY()));
        //integer is not
        config(Integer.class, (ArikothLiquidBridgeBuild tile, Integer i) -> tile.link = i);
    }

    @Override
    public TextureRegion[] icons(){
        return new TextureRegion[]{region, dirRegion};
    }

    public class ArikothLiquidBridgeBuild extends DirectionBridgeBuild{
        public int link = -1;

        public void draw(BuildPlan req, float ox, float oy, float flip){
            Draw.rect(block.region, x, y);

            Draw.rect(dirRegion, x, y, rotdeg());
            var link = findLink();
            if(link != null){
                Draw.z(Layer.power - 1);
                if(Mathf.zero(Renderer.bridgeOpacity)) return;
                Draw.alpha(Renderer.bridgeOpacity);

                Lines.stroke(bridgeWidth);

                Tmp.v1.set(ox, oy).sub(req.drawx(), req.drawy()).setLength(tilesize/2f);

                Lines.line(
                        bridgeRegion,
                        req.drawx() + Tmp.v1.x,
                        req.drawy() + Tmp.v1.y,
                        ox - Tmp.v1.x,
                        oy - Tmp.v1.y, false
                );

                Draw.rect(arrowRegion, (req.drawx() + ox) / 2f, (req.drawy() + oy) / 2f,
                        Angles.angle(req.drawx(), req.drawy(), ox, oy) + flip);

                Draw.reset();
            }
        }

        @Override
        public void updateTile(){
            var link = lastLink = findLink();
            if(link != null){
                moveLiquid(link, liquids.current());
                link.occupied[rotation % 4] = this;
            }

            if(link == null){
                if(liquids.currentAmount() > 0.0001f && timer(timerFlow, 1)){
                    moveLiquidForward(false, liquids.current());
                }
            }

            for(int i = 0; i < 4; i++){
                if(occupied[i] == null || occupied[i].rotation != i || !occupied[i].isValid() || occupied[i].lastLink != this){
                    occupied[i] = null;
                }
            }
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            var link = findLink();
            //only accept if there's an output point, or it comes from a link
            if(link == null && !(source instanceof DirectionBridgeBuild b && b.findLink() == this)) return false;

            int rel = this.relativeToEdge(source.tile);

            return
                    hasLiquids && team == source.team &&
                            (liquids.current() == liquid || liquids.get(liquids.current()) < 0.2f) && rel != rotation &&
                            (occupied[(rel + 2) % 4] == null || occupied[(rel + 2) % 4] == source);
        }
    }
}
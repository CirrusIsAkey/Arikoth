package arikoth.world.blocks.drilling;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.production.BeamDrill;

import static mindustry.Vars.tilesize;

public class ThermalBlaster extends BeamDrill {

    TextureRegion heatRegion;

    public ThermalBlaster(String name) {
        super(name);
    }

    @Override
    public void load(){
        super.load();
        heatRegion = Core.atlas.find(name + "-heat");
    }

    public class ThermalBlasterBuild extends BeamDrillBuild{

        @Override
        public void draw(){
            Draw.rect(block.region, x, y);
            Draw.rect(topRegion, x, y, rotdeg());

            if(isPayload()) return;

            var dir = Geometry.d4(rotation);
            int ddx = Geometry.d4x(rotation + 1), ddy = Geometry.d4y(rotation + 1);

            for(int i = 0; i < size; i++){
                Tile face = facing[i];
                if(face != null){
                    Item drop = face.wallDrop();

                    if(drop == null) continue;
                    Point2 p = lasers[i];
                    float lx = face.worldx() - (dir.x/2f)*tilesize, ly = face.worldy() - (dir.y/2f)*tilesize;

                    float width = (laserWidth + Mathf.absin(Time.time + i*5 + (id % 9)*9, glowScl, pulseIntensity)) * warmup;

                    Draw.z(Layer.power - 1);
                    Draw.mixcol(glowColor, Mathf.absin(Time.time + i*5 + id*9, glowScl, glowIntensity));
                    if(Math.abs(p.x - face.x) + Math.abs(p.y - face.y) == 0){
                        Draw.scl(width);

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Draw.rect(laserCenter, lx, ly);
                        }

                        if(boostWarmup > 0.01f){
                            Draw.alpha(boostWarmup);
                            Draw.rect(laserCenterBoost, lx, ly);
                        }

                        Draw.scl();
                    }else{
                        float lsx = (p.x - dir.x/2f) * tilesize, lsy = (p.y - dir.y/2f) * tilesize;

                        if(boostWarmup < 0.99f){
                            Draw.alpha(1f - boostWarmup);
                            Drawf.laser(laser, laserEnd, lsx, lsy, lx, ly, width);
                        }

                        if(boostWarmup > 0.001f){
                            Draw.alpha(boostWarmup);
                            Drawf.laser(laserBoost, laserEndBoost, lsx, lsy, lx, ly, width);
                        }
                    }

                    Draw.color();
                    Draw.mixcol();

                    Draw.z(Layer.effect - 1);
                    Lines.stroke(warmup);
                    rand.setState(i, id);
                    Color col = drop.color;
                    Color spark = Tmp.c3.set(sparkColor).lerp(boostHeatColor, boostWarmup);
                    for(int j = 0; j < sparks; j++){
                        float fin = (Time.time / sparkLife + rand.random(sparkRecurrence + 1f)) % sparkRecurrence;
                        float or = rand.range(2f);
                        Tmp.v1.set(sparkRange * fin, 0).rotate(rotdeg() + rand.range(sparkSpread));

                        Draw.color(spark, col, fin);
                        float px = Tmp.v1.x, py = Tmp.v1.y;
                        if(fin <= 1f) Lines.lineAngle(lx + px + or * ddx, ly + py + or * ddy, Angles.angle(px, py), Mathf.slope(fin) * sparkSize);
                    }
                    Draw.reset();
                }
            }

            if(heatRegion.found()){
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.color(Tmp.c1.set(heatColor).lerp(boostHeatColor, boostWarmup), warmup * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                Draw.rect(heatRegion, x, y);
                Draw.blend();
                Draw.color();
            }

            if(glowRegion.found()){
                Draw.z(Layer.blockAdditive);
                Draw.blend(Blending.additive);
                Draw.color(Tmp.c1.set(heatColor).lerp(boostHeatColor, boostWarmup), warmup * (heatColor.a * (1f - heatPulse + Mathf.absin(heatPulseScl, heatPulse))));
                Draw.rect(glowRegion, x, y, rotdeg());
                Draw.blend();
                Draw.color();
            }

            Draw.blend();
            Draw.reset();
        }
    }
}

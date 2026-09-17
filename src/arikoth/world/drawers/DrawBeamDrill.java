package arikoth.world.drawers;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

public class DrawBeamDrill extends DrawBlock {

    public float beamCycleTime = 2*60f;
    public float beamRand = Mathf.random(0.5f, 1);
    public float beamY = 12;
    public float beamX = 12;
    Interp interp = Interp.pow3;
    public TextureRegion laser, mineLaser, mineLaserEnd;
    public String suffix, las, lasEnd;
    public float scale = 1.5f;
    Color color = Color.valueOf("ffd675");
    public float x, y;
    public float particleLife = 15, particleRad = 2, particles = 3, particleLen = 3, stroke = 1, particleCone = Mathf.random(360);

    public void draw(Building build){
        float fin = (build.totalProgress() / beamCycleTime * beamRand + build.id) % 1f;
        float fin2 = (build.totalProgress() / beamCycleTime * beamRand + build.id + 0.25f) % 1f;

        // look man. i don't know what i'm doing.
        float bx = ((interp.apply(Mathf.clamp((Math.abs(2f * (fin - Mathf.floor(fin + 0.5f))) * 2f - 1f) * 2f, -1f, 1f) / 2f + 0.5f) * 2f) - 1f) * beamX;
        float by = ((interp.apply(Mathf.clamp((Math.abs(2f * (fin2 - Mathf.floor(fin2 + 0.5f))) * 2f - 1f) * 2f, -1f, 1f) / 2f + 0.5f) * 2f) - 1f) * beamY;

        Draw.rect(laser, build.x + x, build.y + y);
        Draw.blend(Blending.additive);
        Draw.alpha(1 * build.warmup());
        Drawf.laser(mineLaser, mineLaserEnd, build.x + x, build.y + y, build.x + bx, build.y + by, scale);

        float base = (Time.time / particleLife);
        for (int i = 0; i < particles; i++) {
            rand.setSeed(build.id);
            float fin3 = (rand.random(1f) + base) % 1f, fout = 1f - fin3;
            float angle = rand.random(360f);
            float len = particleRad * Interp.pow2Out.apply(fin3);
            Lines.lineAngle(build.x + Angles.trnsx(angle, len) + bx, build.y + Angles.trnsy(angle, len) + by, angle, particleLen * fout * build.warmup());
        }

        Draw.alpha(1);
        Draw.blend();
        Draw.reset();
    }

    @Override
    public void load(Block block) {
        laser = Core.atlas.find(block.name + suffix);
        mineLaser = Core.atlas.find(block.name + las);
        mineLaserEnd = Core.atlas.find(block.name + lasEnd);
    }
}

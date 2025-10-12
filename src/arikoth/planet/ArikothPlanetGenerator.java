
package arikoth.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.noise.*;
import arikoth.content.blocks.ArikothEnv;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;

public class ArikothPlanetGenerator extends PlanetGenerator {
    public float heightScl = 1f, octaves = 8, persistence = 0.7f, heightPow = 3f, heightMult = 2.2f;

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(noise(position), heightPow) * heightMult * Math.max(0.2f, Math.abs(position.y));
    }

    float noise(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    @Override
    public void getColor(Vec3 position, Color out) {
        float height = getHeight(position);
        Block block;
        if (height < 0.14f) {
            block = Blocks.sand;
        } else if (height < 0.16f) {
            block = Blocks.darksand;
        } else if (height < 0.2f) {
        block = Blocks.carbonStone;
        } else if (height < 0.25) {
            block = Blocks.ferricStone;
        } else if (height < 0.4) {
            block = ArikothEnv.aerenite;
        } else if (height < 0.45) {
            block = ArikothEnv.paleAerenite;
        } else if (height < 0.5) {
            block = ArikothEnv.graystone;
        } else if (height < 0.48) {
            block = ArikothEnv.volcanicAndesite;
        } else if (height < 0.6) {
            block = Blocks.dacite;
        } else if (height < 0.62) {
            block = ArikothEnv.greenmat;
        } else if (height < 0.63) {
            block = Blocks.ice;
        } else {
            block = Blocks.carbonStone;
        }
        out.set(block.mapColor).a(1f - block.albedo);
    }
}

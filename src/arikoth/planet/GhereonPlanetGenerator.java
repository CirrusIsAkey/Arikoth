
package arikoth.planet;

import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.util.Tmp;
import arc.util.noise.*;
import arikoth.content.otherPlanets.SerpuloBlocks;
import mindustry.content.Blocks;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;

public class GhereonPlanetGenerator extends PlanetGenerator {
    public float heightScl = 1.6f, octaves = 5, persistence = 0.8f, heightPow = 2.5f, heightMult = 1.15f;

    public static float arkThresh = 0.36f, arkScl = 0.24f;
    public static int arkSeed = 3, arkOct = 1;
    public static float redThresh = 6.8f, noArkThresh = 0.65f;
    public static int sulfurSeed = 3, sulfurOct = 1;
    public static float sulfurScl = 0.38f, sulfurMag = 0.01f;

    Block[] terrain = {Blocks.crystalFloor, Blocks.crystallineStone, SerpuloBlocks.obsidian, SerpuloBlocks.crimsil, SerpuloBlocks.purplemat, SerpuloBlocks.crimsonSand, SerpuloBlocks.corruptedWater};

    @Override
    public float getHeight(Vec3 position){
        return Mathf.pow(rawHeight(position), heightPow) * heightMult;
    }

    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }

    public Color getColor(Vec3 position){
        Block block = rawHeight(position) < 0.4f ? SerpuloBlocks.corruptedWater : rawHeight(position) < 0.5f ? SerpuloBlocks.crimsonSand : rawHeight(position) < 0.6f ? SerpuloBlocks.crimsil : Blocks.crystallineStone;
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }


    @Override
    public void getColor(Vec3 position, Color out) {
        Block block = getBlock(position);
        out.set(block.mapColor).a(1f - block.albedo);
    }

    float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 8, 0.54f, 1.4f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    Block getBlock(Vec3 position){
        float ice = rawTemp(position);
        Tmp.v32.set(position);

        float height = rawHeight(position);
        Tmp.v31.set(position);
        height *= 1.3f;
        height = Mathf.clamp(height);

        Block result = terrain[Mathf.clamp((int)(height * terrain.length), 0, terrain.length - 1)];

        if(ice < 0.3 + Math.abs(Ridged.noise3d(seed + sulfurSeed, position.x + 4f, position.y + 5f, position.z + 0.5f, sulfurOct, sulfurScl)) * sulfurMag){
            return SerpuloBlocks.crimsil;
        }

        if(ice < 0.8){
            if(result == SerpuloBlocks.obsidianWall){
                return SerpuloBlocks.obsidianWall;
            }
        }

        position = Tmp.v32;

        if(ice < redThresh - noArkThresh && Ridged.noise3d(seed + arkSeed, position.x + 2f, position.y + 8f, position.z + 1f, arkOct, arkScl) > arkThresh){
            result = SerpuloBlocks.crimsonSand;
        }

        if(ice > redThresh){
            result = SerpuloBlocks.obsidianWall;
        }else if(ice > redThresh - 0.4){
            result = SerpuloBlocks.crimsonSandWall;
        }else if(ice > redThresh - 0.6) {
            result = SerpuloBlocks.purplematWall;
        }

        return result;
    }
}

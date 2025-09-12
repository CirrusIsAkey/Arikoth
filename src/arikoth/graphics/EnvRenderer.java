
package arikoth.graphics;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.math.Rand;
import arc.util.Time;
import mindustry.graphics.Layer;
import mindustry.graphics.Shaders;
import mindustry.type.Weather;
import arikoth.world.meta.ArikothEnv;

import static mindustry.Vars.*;

public class EnvRenderer {

    public static void init() {
        Core.assets.load("sprites/distortAlpha.png", Texture.class);

        renderer.addEnvRenderer(ArikothEnv.desert, () -> {
            Texture tex = Core.assets.get("sprites/distortAlpha.png", Texture.class);
            if(tex.getMagFilter() != Texture.TextureFilter.linear){
                tex.setFilter(Texture.TextureFilter.linear);
                tex.setWrap(Texture.TextureWrap.repeat);
            }

            Draw.z(state.rules.fog ? Layer.legUnit + 10 : Layer.effect);
            Weather.drawNoiseLayers(tex, Color.valueOf("ffad4d"), 1000f, 0.1f, 1.2f, 1.5f, 1f, 0f, 4, -0.3f, 0.02f, 0.8f, 0.9f);
            Draw.reset();
        });

        Core.assets.load("sprites/distortAlpha.png", Texture.class);

        renderer.addEnvRenderer(ArikothEnv.corrupted, () -> {
            Texture tex = Core.assets.get("sprites/distortAlpha.png", Texture.class);
            if(tex.getMagFilter() != Texture.TextureFilter.linear){
                tex.setFilter(Texture.TextureFilter.linear);
                tex.setWrap(Texture.TextureWrap.repeat);
            }

            Draw.z(state.rules.fog ? Layer.flyingUnitLow : Layer.flyingUnit);
            Weather.drawNoiseLayers(tex, Color.valueOf("6b3031"), 1000f, 0.6f, 1.2f, 1.5f, 1f, 0f, 4, -0.3f, 0.1f, 0.8f, 0.9f);
            Draw.reset();
        });

        Rand rand = new Rand();

        Core.assets.load("sprites/rays.png", Texture.class).loaded = t -> {
            t.setFilter(Texture.TextureFilter.linear);
        };

        Color particleColor = Color.valueOf("cc7591");
        float windSpeed = 0.03f, windAngle = 45f;
        float windx = Mathf.cosDeg(windAngle) * windSpeed, windy = Mathf.sinDeg(windAngle) * windSpeed;

        renderer.addEnvRenderer(ArikothEnv.corrupted, () -> {

            Draw.z(Layer.light + 2);

            int rays = 20;
            float timeScale = 3000f;
            rand.setSeed(0);

            Draw.blend(Blending.additive);

            float t = Time.time / timeScale;
            Texture tex = Core.assets.get("sprites/rays.png", Texture.class);

            for(int i = 0; i < rays; i++){
                float offset = rand.random(0f, 1f);
                float time = t + offset;

                int pos = (int)time;
                float life = time % 1f;
                float opacity = rand.random(0.2f, 0.7f) * Mathf.slope(life) * 0.7f;
                float x = (rand.random(0f, world.unitWidth()) + (pos % 100)*753) % world.unitWidth();
                float y = (rand.random(0f, world.unitHeight()) + (pos % 120)*453) % world.unitHeight();
                float rot = rand.range(7f);
                float sizeScale = 1f + rand.range(0.3f);

                float topDst = (Core.camera.position.y + Core.camera.height/2f) - (y + tex.height/2f + tex.height*1.9f*sizeScale/2f);
                float invDst = topDst/1000f;
                opacity = Math.min(opacity, -invDst);

                if(opacity > 0.01){
                    Draw.alpha(opacity);
                    Draw.rect(Draw.wrap(tex), x, y + tex.height/2f, tex.width*2*sizeScale, tex.height*2*sizeScale, rot);
                    Draw.color();
                }
            }
            Draw.blend();
        });

    }
}

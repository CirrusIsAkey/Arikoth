package arikoth.graphics.shaders;

import arc.files.Fi;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.graphics.CacheLayer;
import mindustry.graphics.Shaders.SurfaceShader;

import static mindustry.Vars.headless;

//its a different class cuz very very important /s
public class ArikothShaders {

    public static @Nullable SurfaceShader solfatarshimmer;
    public static CacheLayer.ShaderLayer solfatarshimmerlayer;
    public static @Nullable SurfaceShader meimechite;
    public static CacheLayer.ShaderLayer meimechiteLayer;

    public static void load() {
        solfatarshimmer = new SurfaceShader("solfatarshimmer");
        solfatarshimmerlayer = new CacheLayer.ShaderLayer(solfatarshimmer);
        CacheLayer.add(solfatarshimmerlayer);

        meimechite = new SurfaceShader("meimechite");
        meimechiteLayer = new CacheLayer.ShaderLayer(meimechite);
        CacheLayer.add(meimechiteLayer);
    }

    public static void dispose(){
        if(!headless){
            solfatarshimmer.dispose();
        }
    }

    public static Fi file(String name) {
        return Vars.tree.get("shaders/" + name);
    }
}

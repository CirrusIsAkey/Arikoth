package arikoth.graphics.shaders;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.gl.FrameBuffer;
import arc.graphics.gl.Shader;
import arc.scene.ui.layout.Scl;
import arc.util.Time;
import arikoth.ui.SettingKeys;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.graphics.Layer;

import static mindustry.Vars.tree;

//LightShaders credits to Sh1penfire
public class ArikothLightShaderLoad {

    public static LightShaderMeld light;

    public static FrameBuffer lightBuffer = new FrameBuffer(), sonarBuffer = new FrameBuffer();

    public static boolean loaded = false;

    public static void load(){
        if(loaded) return;
        loaded = true;

        light = new LightShaderMeld("light");

        Events.run(EventType.Trigger.draw, () -> {
            sonarBuffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());

            if(Vars.state.rules.lighting && Core.settings.getBool(SettingKeys.lighting)){
                lightBuffer.resize(Core.graphics.getWidth(), Core.graphics.getHeight());
                Draw.draw(Layer.background, () -> {
                    lightBuffer.begin();
                });
                Draw.draw(Layer.light, () -> {
                    lightBuffer.end();
                    ArikothLightRendererLoad.thingo.draw2();
                });
            }
        });
    }

    /** Shaders that the the*/
    public static class NamedShader extends Shader {
        public NamedShader(String frag) {
            super(Core.files.internal("shaders/screenspace.vert"), tree.get("shaders/" + frag + ".frag"));
        }

        @Override
        public void apply() {
            setUniformf("u_time", Time.time / Scl.scl(1f));
            setUniformf("u_campos",
                    Core.camera.position.x,
                    Core.camera.position.y
            );
            setUniformf("u_resolution",
                    Core.graphics.getWidth(),
                    Core.graphics.getHeight()
            );
            setUniformf("u_drawCol", Draw.getColor().r,  Draw.getColor().g,  Draw.getColor().b,  Draw.getColor().a);
        }
    }

    public static class LightShaderMeld extends NamedShader{
        public float thickness = 0;
        public Color ambient = new Color(0.01f, 0.01f, 0.04f, 0.99f);
        public Texture lights = null;
        public Texture exclusion = null;

        public LightShaderMeld(String frag) {
            super(frag);
        }

        @Override
        public void apply(){
            /*
            setUniformf("u_campos", Core.camera.position.x - Core.camera.width / 2, Core.camera.position.y - Core.camera.height / 2);
            setUniformf("u_resolution", Core.camera.width, Core.camera.height);
            setUniformf("u_time", Time.time);

             */

            setUniformf("u_ambient", ambient);
            setUniformf("u_thickness", thickness);

            if(exclusion != null) exclusion.bind(2);
            if(lights != null) lights.bind(1);

            setUniformi("u_exclusion", 2);
            setUniformi("u_lights", 1);

            lightBuffer.getTexture().bind(0);

        }
    }
}
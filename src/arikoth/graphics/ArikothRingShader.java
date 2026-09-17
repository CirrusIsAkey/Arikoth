package arikoth.graphics;

import arc.files.Fi;
import arc.graphics.Color;
import arc.graphics.gl.Shader;
import arc.math.geom.Vec3;
import mindustry.Vars;
import mindustry.type.Planet;

import static mindustry.Vars.renderer;

public class ArikothRingShader {

    public static PlanetTextureShader planetTextureShader;

    public static void load() {
        String prevVert = Shader.prependVertexCode;
        String prevFrag = Shader.prependFragmentCode;
        Shader.prependVertexCode = Shader.prependFragmentCode = "";

        planetTextureShader = new PlanetTextureShader();

        Shader.prependVertexCode = prevVert;
        Shader.prependFragmentCode = prevFrag;
    }

    public static Fi file(String name) {
        return Vars.tree.get("shaders/" + name);
    }

    public static class PlanetTextureShader extends Shader {
        public Vec3 lightDir = new Vec3(1, 1, 1).nor();
        public Color ambientColor = Color.white.cpy();
        public Vec3 camDir = new Vec3();
        public float alpha = 1f;
        public Planet planet;

        public PlanetTextureShader() {
            super(file("ring-mesh.vert"), file("ring-mesh.frag"));
        }

        @Override
        public void apply() {
            camDir.set(renderer.planets.cam.direction).rotate(Vec3.Y, planet.getRotation());

            setUniformf("u_alpha", alpha);
            setUniformf("u_lightdir", lightDir);
            setUniformf("u_ambientColor", ambientColor.r, ambientColor.g, ambientColor.b);
            setUniformf("u_camdir", camDir);
            setUniformf("u_campos", renderer.planets.cam.position);
        }
    }
}
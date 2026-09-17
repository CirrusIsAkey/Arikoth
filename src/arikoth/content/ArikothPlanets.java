package arikoth.content;

import arc.func.*;
import arc.graphics.*;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.*;
import arikoth.content.blocks.ArikothStorage;
import arikoth.graphics.g3d.RingMesh;
import arikoth.world.presets.misc.ArikothEnv;
import mindustry.game.Gamemode;
import mindustry.graphics.g3d.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.meta.*;
import arikoth.planet.*;

import static arc.Core.atlas;

public class ArikothPlanets {
    public static Planet acithar, mesarius, halchyin, haurron, arikoth, everneon, tantrias, zealor, hypheru, kargithar, tauix, eres, kelthir, derath, ghereon, mekerir, celecar, niliux;

    public static void load(){
        acithar = new Planet("Acithar", null, 10f){{
            bloom = true;
            accessible = false;
            solarSystem = this;
            meshLoader = () -> new SunMesh(
                    this, 5,
                    5, 0.3, 2.7, 1.2, 1,
                    1.15f,
                    Color.valueOf("#99bdf9"),
                    Color.valueOf("#debb7f"),
                    Color.valueOf("#e8cf9c"),
                    Color.valueOf("#f3e3bb"),
                    Color.valueOf("#fff6db")
            );
        }};

        mesarius = new Planet("mesarius", ArikothPlanets.acithar, 4f){{
            bloom = true;
            accessible = false;
            solarSystem = acithar;
            orbitRadius = 120;
            meshLoader = () -> new SunMesh(
                    this, 5,
                    5, 0.3, 2.7, 1.2, 1,
                    1f,
                    Color.valueOf("#c8142a"),
                    Color.valueOf("#d9484a"),
                    Color.valueOf("#e86b6b"),
                    Color.valueOf("#f58d8c"),
                    Color.valueOf("#ffadad")
            );
        }};



        everneon = new Planet("everneon", acithar, 6f){{
            icon = "everneon";
            alwaysUnlocked = true;
            accessible = true;

            atmosphereColor = Color.valueOf("68587a");
            atmosphereRadIn = 0;
            atmosphereRadOut = 0.05f;
            orbitRadius = 45f;
            generator = new EverneonPlanetGenerator();

            Vec3 ringAxis = new Vec3(0, 0, 0).rotate(Vec3.X, 58);
            meshLoader = () -> new MultiMesh(
                    new HexMesh(this, 7),

                    new RingMesh(atlas.find("arikoth-everneon-ring1"), this, 80, 6.25f, 6.4f, ringAxis),
                    new RingMesh(atlas.find("arikoth-everneon-ring2"), this, 80, 6.45f, 6.6f, ringAxis)

            );
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 1, 1f, 0.05f, 6, Color.valueOf("#321a6f").a(0.6f), 2, 0.8f, 1f, 0.f),
                    new HexSkyMesh(this, 2, 1f, 0.06f, 6, Color.valueOf("#564292").a(0.6f), 2, 0.8f, 1f, 0.5f),
                    new HexSkyMesh(this, 3, 1f, 0.07f, 6, Color.valueOf("#7b6ab6").a(0.6f), 2, 0.8f, 1.2f, 0.5f),
                    new HexSkyMesh(this, 4, 1f, 0.08f, 6, Color.valueOf("#a195da").a(0.6f), 2, 0.8f, 1.2f, 0.5f),
                    new HexSkyMesh(this, 5, 1f, 0.09f, 6, Color.valueOf("#c9c2ff").a(0.6f), 2, 0.8f, 1.2f, 0.5f)
            );
        }};

        arikoth = new Planet("Arikoth", ArikothPlanets.acithar, 1f, 3){{
            generator = new ArikothPlanetGenerator();

            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 42, 2f, 0.13f, 5, Color.valueOf("d2ae8d").a(0.75f), 3, 0.7f, 1f, 0.43f),
                    new HexSkyMesh(this, 69, 2.4f, 0.12f, 5, Color.valueOf("f7cba4").a(0.75f), 3, 0.7f, 1f, 0.45f)
            );

            solarSystem = acithar;
            alwaysUnlocked = true;
            accessible = true;
            updateLighting = true;
            allowLaunchToNumbered = false;
            allowLaunchLoadout = false;
            allowSectorInvasion = false;
            startSector = 15;
            clearSectorOnLose = true;
            allowWaves = true;
            prebuildBase = false;
            defaultCore = ArikothStorage.coreSerenity;

            defaultEnv = ArikothEnv.desert | ArikothEnv.rays | Env.oxygen | Env.terrestrial | Env.groundOil;

            orbitSpacing = 1;
            drawOrbit = true;
            orbitRadius = 20;
            rotateTime = 12 * 60;

            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            sectorSeed = 1204;
            bloom = false;
            visible = true;
            atmosphereColor = Color.valueOf("cf8034");
            iconColor = Color.valueOf("31ffa4");
            hasAtmosphere = true;
            lightSrcTo = 0.8f;
            Vec3 ringAxis = new Vec3(0, 1, 0).rotate(Vec3.X, -25); // tilt angle

            meshLoader = () -> new MultiMesh(
                    new HexMesh(this, 5),
                    new RingMesh(atlas.find("arikoth-arikoth-ring1"), this, 80, 1.4f, 1.6f, ringAxis),
                    new RingMesh(atlas.find("arikoth-arikoth-ring2"), this, 80, 1.7f, 2f, ringAxis)
            );

            ruleSetter = r -> {
                r.waveTeam = ArikothTeams.conquisitoris;
                r.showSpawns = true;
                r.defaultTeam = ArikothTeams.luxis;
                r.teams.get(r.waveTeam).rtsAi = false;

                if(r.mode() == Gamemode.attack || r.mode() == Gamemode.pvp){
                }

            };
        }};
    }
    private static Planet makeAsteroid(String name, Planet parent, Block base, Block tint, int seed, float tintThresh, int pieces, float scale, Cons<AsteroidGenerator> cgen){
        return new Planet(name, parent, 0.12f){{
            hasAtmosphere = false;
            updateLighting = false;
            sectors.add(new Sector(this, Ptile.empty));
            camRadius = 0.68f * scale;
            minZoom = 0.6f;
            drawOrbit = false;
            accessible = false;
            clipRadius = 2f;
            defaultEnv = Env.space;
            icon = "commandRally";
            generator = new AsteroidGenerator();
            cgen.get((AsteroidGenerator)generator);

            meshLoader = () -> {
                iconColor = tint.mapColor;
                Color tinted = tint.mapColor.cpy().a(1f - tint.mapColor.a);
                Seq<GenericMesh> meshes = new Seq<>();
                Color color = base.mapColor;
                Rand rand = new Rand(id + 2);

                meshes.add(new NoiseMesh(
                        this, seed, 2, radius, 2, 0.55f, 0.45f, 14f,
                        color, tinted, 3, 0.6f, 0.38f, tintThresh
                ));

                for(int j = 0; j < pieces; j++){
                    meshes.add(new MatMesh(
                            new NoiseMesh(this, seed + j + 1, 1, 0.022f + rand.random(0.039f) * scale, 2, 0.6f, 0.38f, 20f,
                                    color, tinted, 3, 0.6f, 0.38f, tintThresh),
                            new Mat3D().setToTranslation(Tmp.v31.setToRandomDirection(rand).setLength(rand.random(0.44f, 1.4f) * scale)))
                    );
                }

                return new MultiMesh(meshes.toArray(GenericMesh.class));
            };
        }};
    }
}
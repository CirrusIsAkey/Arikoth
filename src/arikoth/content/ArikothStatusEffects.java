package arikoth.content;

import arc.graphics.*;
import arc.graphics.g2d.Lines;
import arc.math.*;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.graphics.Pal;
import mindustry.type.*;

import static arc.graphics.g2d.Draw.blend;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.math.Angles.randLenVectors;
import static arikoth.content.effects.ArikothShootFx.rand;

public class ArikothStatusEffects {
    public static StatusEffect massDischarge, armorRend1, armorRend2, armorRend3, blight;

    public static void load(){

        massDischarge = new StatusEffect("mass-discharge"){{
            color = Color.valueOf("a88fc7");
            damage = 0.5f;
            speedMultiplier = 0.2f;
            reloadMultiplier = 0.2f;
            effect = new RadialEffect(new Effect(15f, e -> {
                color(Color.white, Color.valueOf("a88fc7"), e.fin());
                Lines.stroke(0.5f + 1.5f * e.fout());
                blend(Blending.additive);

                rand.setSeed(e.id);
                randLenVectors(e.id, 3, 40f * e.fout(), e.rotation, 12, (x, y) -> {
                    lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fslope() * 3f + 0.5f);
                });

                blend();
            }), 2, 180, 2){{
                rotationOffset = 90;
            }};
            transitionDamage = 2f;

            init(() -> {
                affinity(StatusEffects.shocked, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    Fx.circleColorSpark.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(massDischarge, Math.min(time + result.time, 300f));
                });
            });
        }};

        // idea by EggleEgg

        armorRend1 = new StatusEffect("armor-rend-1"){{
            color = Pal.remove;
            effect = Fx.circleColorSpark;
            healthMultiplier = 0.9f;
        }};
        armorRend2 = new StatusEffect("armor-rend-2"){{
            color = Pal.remove;
            effect = Fx.circleColorSpark;
            healthMultiplier = 0.85f;
        }};
        armorRend3 = new StatusEffect("armor-rend-3"){{
            color = Pal.remove;
            effect = Fx.circleColorSpark;
            healthMultiplier = 0.8f;
        }};

        blight = new StatusEffect("blight"){{
            color = Color.valueOf("ffc455");
            damage = 0.12f;
            speedMultiplier = 1.2f;
            reloadMultiplier = 0.9f;
            effect = new ParticleEffect(){{
                sizeFrom =  5;
                sizeTo = 0;
                length = 30;
                colorFrom = Color.valueOf("875778");
                colorTo = Color.valueOf("5c3b52aa");
                lifetime = 90;
                interp = Interp.pow5Out;
                sizeInterp = Interp.pow5In;
                particles = 5;
            }};
            transitionDamage = -2f;

            init(() -> {
                affinity(StatusEffects.overclock, (unit, result, time) -> {
                    unit.damagePierce(transitionDamage);
                    Fx.circleColorSpark.at(unit.x + Mathf.range(unit.bounds() / 2f), unit.y + Mathf.range(unit.bounds() / 2f));
                    result.set(massDischarge, Math.min(time + result.time, 300f));
                });
            });
        }};
}}

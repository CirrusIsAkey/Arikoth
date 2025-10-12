package arikoth.content.effects;

import arc.graphics.g2d.Fill;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.color;
import static arikoth.content.effects.ArikothFx.rand;

public class ArikothMiscFx {
    public static Effect
    coreLandDustModified = new Effect(100f, e -> {
        color(e.color, e.fout(0.1f));
        rand.setSeed(e.id);
        Tmp.v1.trns(e.rotation, e.finpow() * 45f * rand.random(0.2f, 1f));
        Fill.circle(e.x + Tmp.v1.x, e.y + Tmp.v1.y, 8f * rand.random(0.6f, 1f) * e.fout(0.2f));
    }).layer(Layer.groundUnit + 1f);
}

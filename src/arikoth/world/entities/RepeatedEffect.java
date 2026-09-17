package arikoth.world.entities;

import mindustry.entities.Effect;

/**
 * Renders multiple particle effects in sequence.
 * Will not work correctly for effects that modify life dynamically.
 * Z layer of child effects is ignored.
 * */
public class RepeatedEffect extends Effect{
    public Effect effect;
    public int loops;
    public float interval;

    public RepeatedEffect(){
        clip = 100f;
    }

    public RepeatedEffect(Effect effect, int loops, float interval){
        this();
        this.effect = effect;
        this.interval = interval;
        this.loops = loops;
    }

    @Override
    public void init(){
        lifetime = ((loops - 1) * interval) + effect.lifetime;
    }

    @Override
    public void render(EffectContainer e){
        var cont = e.inner();
        for(int i = 0; i < loops; i++){
            float startTime = i * interval;
            float localLife = e.time - startTime;
            if(localLife >= 0 && localLife <= effect.lifetime) {
                cont.set(e.id + i, e.color, localLife, effect.lifetime, e.rotation, e.x, e.y, e.data);
                effect.render(cont);
                clip = Math.max(clip, effect.clip);
            }
        }
    }
}
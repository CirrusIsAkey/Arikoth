package arikoth.world.entities.bullets;

import arc.*;
import arc.audio.Sound;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;
import mindustry.graphics.*;

import static mindustry.Vars.indexer;

//i consider this as jank

public class IntervalSplashBulletType extends BulletType {
    public float splashDamage = 12, splashDamageInverval = 1, splashAmount = 5, loopSoundVolume = 1;
    public Color innerColor = Color.white, outerColor = Color.white, particleColor = Color.white;

    public Sound loopSound = Sounds.none;
    Effect effect = Fx.burning;

    public IntervalSplashBulletType(float splashDamage, float splashDamageInverval, float splashAmount){
        scaledSplashDamage = true;
        hitSize = speed = 0;
        smokeEffect = despawnEffect = hitEffect = Fx.none;
        displayAmmoMultiplier = false;
        keepVelocity = false;
        absorbable = false;
        hittable = false;
        collides = false;
    }

    @Override
    public void init(Bullet b){
        super.init(b);

        Damage.damage(b.team, b.x, b.y, splashDamageRadius, splashDamage * b.damageMultiplier(), collidesAir, collidesGround, scaledSplashDamage);
    }

    public void generateSplashDamage(Bullet b){
        if (b.timer(5, splashDamageInverval) && splashAmount > 1) {
            Damage.damage(b.team, b.x, b.y, splashDamageRadius, splashDamage * b.damageMultiplier(), collidesAir, collidesGround, scaledSplashDamage);
            for (int j = 0; j < ((splashAmount) * 15); j++) {
                effect.at(
                        b.x + Angles.trnsx(Mathf.random(360), Mathf.random(splashDamageRadius)),
                        b.y + Angles.trnsx(Mathf.random(360), Mathf.random(splashDamageRadius)),
                        particleColor == null ? Pal.bulletYellow : particleColor
                );
            }
            if (status != StatusEffects.none) {
                Damage.status(b.team, b.x, b.y, splashDamageRadius, status, statusDuration, collidesAir, collidesGround);
            }
            if (healPercent > 0f) {
                indexer.eachBlock(b.team, b.x, b.y, splashDamageRadius, Building::damaged, other -> {
                    Fx.healBlockFull.at(other.x, other.y, other.block.size, Pal.heal);
                    other.heal(healPercent / 100f * other.maxHealth());
                });
            }
            if (makeFire) {
                indexer.eachBlock(null, b.x, b.y, splashDamageRadius, other -> other.team != b.team, other -> Fires.create(other.tile));
            }
        }
        if (loopSound != Sounds.none) Vars.control.sound.loop(loopSound, b, b.fslope() * loopSoundVolume);
    }

    @Override
    public void draw(Bullet b){
        Fill.light(b.x, b.y, Lines.circleVertices(splashDamage), splashDamage, innerColor, outerColor);
    }

    public void update(Bullet b){
        generateSplashDamage(b);
        super.update(b);
    }
}
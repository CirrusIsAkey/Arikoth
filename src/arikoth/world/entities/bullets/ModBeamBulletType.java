package arikoth.world.entities.bullets;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.gen.*;
import mindustry.graphics.*;

public class ModBeamBulletType extends ContinuousLaserBulletType {
    public float fadeTime = 16f;
    public float lightStroke = 40f;
    public int divisions = 13;
    public Color[] colors = {Color.valueOf("ec745855"), Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white};
    public float strokeFrom = 2f, strokeTo = 0.5f, pointyScaling = 0.75f;
    public float backLength = 35f, frontLength = 42f;
    public float width = 9f, blackIn = 6, oscScl = 0.8f, oscMag = 1.5f, circleRad = 10;

    private static final Rand rand = new Rand();

    public ModBeamBulletType(float damage){
        this.damage = damage;
    }

    public ModBeamBulletType(){
    }

    {
        shake = 1f;
        largeHit = true;
        hitEffect = Fx.hitBeam;
        hitSize = 4;
        drawSize = 420f;
        lifetime = 16f;
        hitColor = colors[2];
        incendAmount = 1;
        incendSpread = 5;
        incendChance = 0.4f;
        lightColor = Color.orange;
        lightOpacity = 0.7f;
    }

    @Override
    public void draw(Bullet b){
        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);
        float realLength = Damage.findLength(b, length * fout, laserAbsorb, pierceCap);
        float rot = b.rotation();

        for(int i = 0; i < colors.length; i++){
            Draw.color(Tmp.c1.set(colors[i]).mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));

            float colorFin = i / (float)(colors.length - 1);
            float baseStroke = Mathf.lerp(strokeFrom, strokeTo, colorFin);
            float stroke = (width + Mathf.absin(Time.time, oscScl, oscMag)) * fout * baseStroke;
            float ellipseLenScl = Mathf.lerp(1 - i / (float)(colors.length), 1f, pointyScaling);

            Lines.stroke(stroke);
            Lines.lineAngle(b.x, b.y, rot, Math.max(0, realLength - frontLength), false);

            //back ellipse
            Drawf.tri(b.x, b.y, stroke, backLength, rot + 180);
            //front ellipse
            Tmp.v1.trnsExact(rot, Math.max(0, realLength - frontLength));
            Drawf.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, stroke, frontLength * ellipseLenScl, rot);
        }

        Draw.z(Layer.bullet - 0.5f);
        Draw.color(Color.black.mul(1f + Mathf.absin(Time.time, 1f, 0.1f)));

        float colorFin = (float)(colors.length - 1);
        float baseStroke = Mathf.lerp(strokeFrom, strokeTo, colorFin);
        float stroke = ((width - blackIn) + Mathf.absin(Time.time, oscScl, oscMag)) * fout * baseStroke;
        float ellipseLenScl = Mathf.lerp(1 / (float)(colors.length), 1f, pointyScaling);

        Lines.stroke(stroke);
        Lines.lineAngle(b.x, b.y, rot, Math.max(0, realLength - frontLength), false);

        //back ellipse
        Drawf.tri(b.x, b.y, stroke, backLength, rot + 180);

        //front ellipse
        Tmp.v1.trnsExact(rot, Math.max(0, realLength - frontLength));
        Drawf.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, stroke, frontLength * ellipseLenScl, rot);

        Tmp.v1.trns(b.rotation(), realLength * 1.1f);

        Drawf.light(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, lightStroke, lightColor, lightOpacity);
        Draw.reset();
    }

    @Override
    public void drawLight(Bullet b){
        //no light drawn here
    }

    @Override
    public float currentLength(Bullet b){
        float fout = Mathf.clamp(b.time > b.lifetime - fadeTime ? 1f - (b.time - (lifetime - fadeTime)) / fadeTime : 1f);
        return length * fout;
    }
}
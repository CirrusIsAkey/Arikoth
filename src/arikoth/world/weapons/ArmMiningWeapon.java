package arikoth.world.weapons;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import arikoth.math.Parallax;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.InverseKinematics;
import mindustry.type.Weapon;

/** Original code by Cirrus improved by Stabu_. */
public class ArmMiningWeapon extends Weapon{

    TextureRegion armRegion;
    TextureRegion armBaseRegion;
    public float armLength, armExtension, armBaseExtension, armBaseLength, posx, posy, scl1, mag1, scl2, mag2, layer, flyingLayer;
    public boolean side = false;

    public Vec2 armStartingOffset = new Vec2();

    public ArmMiningWeapon(){
        super();
    }

    public ArmMiningWeapon(String name){
        super(name);
        rotate = true;
        noAttack = true;
        predictTarget = false;
        display = false;
        bullet = new BulletType();
        useAttackRange = false;
    }

    @Override
    public void load(){
        super.load();
        armBaseRegion = Core.atlas.find(name + "-base");
        armRegion = Core.atlas.find(name + "-arm");
        outlineRegion = Core.atlas.find(name + "-outline");
        region = Core.atlas.find(name);
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        mount.shoot = false;
        mount.rotate = true;

        if(unit.mining()){
            mount.aimX = unit.mineTile().drawx();
            mount.aimY = unit.mineTile().drawy();
        }else{
            mount.aimX = unit.aimX();
            mount.aimY = unit.aimY();
        }

        super.update(unit, mount);
    }

    public void drawArm(float x, float y, float offsetX, float offsetY, Unit unit, float baseZ) {
        float prevZ = Draw.z();
        Tmp.v1.add(x, y).add(offsetX, offsetY);

        if(unit.mining()){
            unit.type.drawMiningBeam(unit, x + offsetX, y + offsetY);
        }

        Draw.z(unit.isFlying() && flyingLayer != 0f ? flyingLayer : baseZ + layerOffset);

        InverseKinematics.solve(armLength, armBaseLength, Tmp.v1.set(-offsetX, -offsetY), side, Tmp.v2);

        Parallax.getParallaxFrom(Tmp.v2.add(x, y).add(offsetX, offsetY), Core.camera.position, 0.25f);
        Tmp.v1.add(x, y).add(offsetX, offsetY);

        Lines.stroke(armBaseRegion.height/4f);
        Lines.line(
                armBaseRegion,
                x + Angles.trnsx(Tmp.v2.angleTo(x, y), armBaseExtension),
                y + Angles.trnsy(Tmp.v2.angleTo(x, y), armBaseExtension),
                Tmp.v2.x,
                Tmp.v2.y,
                false
        );
        Lines.stroke(armRegion.height/4f);
        Lines.line(
                armRegion,
                Tmp.v2.x + Angles.trnsx(Tmp.v2.angleTo(x + offsetX, y + offsetY) + 180f, armExtension),
                Tmp.v2.y + Angles.trnsy(Tmp.v2.angleTo(x + offsetX, y + offsetY) + 180f, armExtension),
                x + offsetX,
                y + offsetY,
                false
        );

        Draw.z(prevZ);
    }

    @Override
    public void draw(Unit unit, WeaponMount mount) {
        float baseZ = Draw.z();
        super.draw(unit, mount);
        
        float
                rotation = unit.rotation - 90,
                weaponRotation = rotation + (rotate ? mount.rotation : 0),
                wx = unit.x + Angles.trnsx(rotation, x, y),
                wy = unit.y + Angles.trnsy(rotation, x, y),
                px = wx + Angles.trnsx(weaponRotation, this.shootX, this.shootY),
                py = wy + Angles.trnsy(weaponRotation, this.shootX, this.shootY);
                
        boolean isBusy = unit.mining();
        float targetExt = this.posy;
        if(isBusy){
            float len = Mathf.dst(px, py, mount.aimX, mount.aimY);
            targetExt = Math.min(len, (armLength + armBaseLength * 0.5f));
        }

        if(mount.lastLength == 0) mount.lastLength = this.posy;
        mount.lastLength = Mathf.lerpDelta(mount.lastLength, targetExt, 0.15f);
        float ext = mount.lastLength;

        float handRecoil = 0;
        if(isBusy){
            handRecoil = Interp.pow3Out.apply(0, -0.075f, 3) * -4f;
        }
        ext -= handRecoil;

        float wobbleScl = isBusy ? 0f : 1f;
        float wobble1 = Mathf.absin(scl1, mag1) * wobbleScl;
        float wobble2 = Mathf.absin(scl2, mag2) * wobbleScl;

        float
                ex = Angles.trnsx(weaponRotation, this.posx + wobble1, ext + wobble2),
                ey = Angles.trnsy(weaponRotation, this.posx + wobble1, ext + wobble2);

        drawArm(px, py, ex, ey, unit, baseZ);
    }
}
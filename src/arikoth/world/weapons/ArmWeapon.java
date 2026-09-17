package arikoth.world.weapons;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Time;
import arc.util.Tmp;
import arikoth.math.Parallax;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.units.WeaponMount;
import mindustry.gen.Unit;
import mindustry.graphics.InverseKinematics;
import mindustry.type.Weapon;

/** Original code by Cirrus, improved by Stabu_. */
public class ArmWeapon extends Weapon{

    TextureRegion armRegion;
    TextureRegion armBaseRegion;
    TextureRegion armEndRegion;
    public Interp rotInterp = Interp.smooth;
    public float armLength, armExtension, armBaseExtension, armBaseLength, posx, posy, scl1, mag1, scl2, mag2, flyingLayer, percentage = 0.6f, shootPosX = 0, shootPosY = 4;
    public boolean side = false;

    public Vec2 armStartingOffset = new Vec2();

    public ArmWeapon(){
        super();
    }

    public ArmWeapon(String name){
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
        armEndRegion = Core.atlas.find(name + "-arm-end");
        outlineRegion = Core.atlas.find(name + "-outline");
        region = Core.atlas.find(name);
    }

    @Override
    public void update(Unit unit, WeaponMount mount){
        mount.rotate = true;

        mount.aimX = unit.aimX();
        mount.aimY = unit.aimY();

        float prevShootX = this.shootX;
        float prevShootY = this.shootY;
        this.shootX = this.posx;
        this.shootY = mount.lastLength;
        rotInterp.apply(mount.targetRotation);

        super.update(unit, mount);

        this.shootX = prevShootX;
        this.shootY = prevShootY;
    }

    public void drawArm(float x, float y, float offsetX, float offsetY, Unit unit, float baseZ, float rotation) {
        float prevZ = Draw.z();
        Tmp.v1.add(x, y).add(offsetX, offsetY);

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
        Draw.rect(armEndRegion, x + offsetX, y + offsetY, rotation);

        Draw.z(prevZ);
    }

    @Override
    public void draw(Unit unit, WeaponMount mount) {
        float baseZ = Draw.z();
        
        float prevRecoil = mount.recoil;
        mount.recoil = 0;
        super.draw(unit, mount);
        mount.recoil = prevRecoil;
        
        float
                rotation = unit.rotation - 90,
                weaponRotation = rotation + (rotate ? mount.rotation : 0),
                wx = unit.x + Angles.trnsx(rotation, x, y),
                wy = unit.y + Angles.trnsy(rotation, x, y),
                px = wx + Angles.trnsx(weaponRotation, this.shootX, this.shootY),
                py = wy + Angles.trnsy(weaponRotation, this.shootX, this.shootY);

        boolean isBusy = mount.shoot;
        float targetExt = this.posy;
        if(isBusy){
            float len = Mathf.dst(px, py, mount.aimX, mount.aimY);
            targetExt = Math.min(len, (armLength + armBaseLength * percentage));
        }

        if(mount.lastLength == 0) mount.lastLength = this.posy;
        mount.lastLength = Mathf.lerpDelta(mount.lastLength, targetExt, 0.15f);
        float ext = mount.lastLength;

        ext -= mount.recoil * 3f + recoil;

        float wobbleScl = isBusy ? 0f : 1f;
        float wobble1 = Mathf.absin(scl1, mag1) * wobbleScl;
        float wobble2 = Mathf.absin(scl2, mag2) * wobbleScl;

        float
                ex = Angles.trnsx(weaponRotation, this.posx + wobble1, ext + wobble2),
                ey = Angles.trnsy(weaponRotation, this.posx + wobble1, ext + wobble2),
                rot = Angles.moveToward(mount.rotation, mount.targetRotation, rotateSpeed * Time.delta);

        drawArm(px, py, ex, ey, unit, baseZ, weaponRotation);
    }
}
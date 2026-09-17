package arikoth.world.weapons;

import mindustry.gen.Sounds;
import mindustry.type.Weapon;

public class ThrusterWeaponPreset extends Weapon {
    public ThrusterWeaponPreset(String name){
        super(name);
        shootY = 0;
        rotate = true;
        rotateSpeed = 0;
        baseRotation = 180;
        alwaysShooting = true;
        continuous = true;
        alternate = false;
        mirror = false;
        reload = 3;
        xRand = 48;
        shootSound = Sounds.none;
        shootWarmupSpeed = 0.03f;
        minShootVelocity = 2.75f;
        recoil = 0;
        display = false;
    }
}

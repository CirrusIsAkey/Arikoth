package arikoth.world.blocks.turrets;

import arc.Core;
import arc.math.Mathf;
import arc.util.Strings;
import arc.util.Time;
import arikoth.content.effects.palettes.ArikothUnitPal;
import mindustry.entities.bullet.BulletType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.LiquidTurret;


// thanks to Sputnuc for giving me this code
public class AccelLiquidTurret extends LiquidTurret {
    public float speedUpPerShoot = 2;
    public float maxAccel = 0.5f;
    public float cooldownSpeed = 1;

    public AccelLiquidTurret(String name){
        super(name);
    }

    @Override
    public void setBars(){
        super.setBars();
        addBar("speedingUp", (AccelTurretBuild entity) ->
                new Bar(
                        () -> Core.bundle.format("bar.speedingUp", Strings.autoFixed((entity.speedUp)* 100, 0)),
                        () -> ArikothUnitPal.scoutBlue,
                        () -> entity.speedUp / maxAccel
                )
        );
    }
    @Override
    public void init(){
        super.init();
    }

    public  class AccelTurretBuild extends LiquidTurretBuild {
        public float speedUp = 0;
        @Override
        public void updateTile() {
            //coolDown progress
            if (!isShooting() || !hasAmmo()){
                speedUp = Mathf.lerpDelta(speedUp, 0, cooldownSpeed / 20);
            }
            super.updateTile();
        }
        @Override
        public void updateShooting(){
            //override shooting method
            if (reloadCounter >= reload) {

                BulletType type = peekAmmo();

                shoot(type);
                reloadCounter = 0;
            }
            else
            {
                reloadCounter += (1 + speedUp) * Time.delta * peekAmmo().reloadMultiplier * baseReloadSpeed();
            }
        }
        @Override
        public void shoot(BulletType type){
            //speedUp per shoot
            super.shoot(type);
            if (speedUp < maxAccel){
                speedUp += speedUpPerShoot * Time.delta;
            }else {
                speedUp = maxAccel;
            }
        }
    }
}
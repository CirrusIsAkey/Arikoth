
package arikoth.world.presets.stat;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arikoth.world.blocks.payloads.Missile;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;

import static arc.Core.*;
import static mindustry.Vars.*;

public class ArikothStatValues{
    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map){
        return ammo(map, 0, false);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, boolean showUnit){
        return ammo(map, 0, showUnit);
    }

    public static <T extends UnlockableContent> StatValue ammo(ObjectMap<T, BulletType> map, int indent, boolean showUnit){
        return table -> {
            table.row();

            var orderedKeys = map.keys().toSeq();
            orderedKeys.sort();

            for(T t: orderedKeys){
                boolean compact = t instanceof UnitType && !showUnit || indent > 0;
                boolean payload = t instanceof Block || (t instanceof UnitType && !showUnit);

                if(payload && t instanceof Missile m && !m.displayCampaign && state.isCampaign()) continue;

                BulletType type = map.get(t);

                if(type.spawnUnit != null && type.spawnUnit.weapons.size > 0){
                    ammo(ObjectMap.of(t, type.spawnUnit.weapons.first().bullet), indent, false).display(table);
                    continue;
                }

                table.table(compact ? null : Styles.grayPanel, bt -> {
                    bt.left().top().defaults().padRight(3).left();

                    //no point in displaying unit icon twice
                    if(!compact && !(t instanceof PowerTurret)){
                        bt.table(title -> {
                            if(payload){
                                if(t.unlockedNow()){
                                    title.image(icon(t)).size(96f).padRight(4).right().top();
                                    title.table(n -> {
                                        n.add(t.localizedName);
                                        n.row();
                                        infoButton(n, t, 4f * 8f).padTop(4f);
                                    }).padRight(10).left().top();
                                }else{
                                    title.image(Icon.lock).color(Pal.darkerGray).size(40).padRight(4).right().top();
                                    title.add("@pm-missing-research").left().colspan(2);
                                }
                            }else{
                                title.image(icon(t)).size(3 * 8).padRight(4).right().scaling(Scaling.fit).top();
                                title.add(t.localizedName).padRight(10).left().top();
                            }
                        });
                        if(payload && !t.unlockedNow()) return;
                        bt.row();
                    }

                    if(type.buildingDamageMultiplier != 1){
                        int val = (int)(type.buildingDamageMultiplier * 100 - 100);
                        sep(bt, bundle.format("bullet.buildingdamage", ammoStat(val)));
                    }

                    if(type.rangeChange != 0 && !compact){
                        sep(bt, bundle.format("bullet.range", ammoStat(type.rangeChange / tilesize)));
                    }

                    if(type.splashDamage > 0){
                        sep(bt, bundle.format("bullet.splashdamage", (int)type.splashDamage, Strings.fixed(type.splashDamageRadius / tilesize, 1)));
                    }

                    if(!compact && !Mathf.equal(type.ammoMultiplier, 1f) && type.displayAmmoMultiplier && (!(t instanceof Turret turret) || turret.displayAmmoMultiplier)){
                        sep(bt, bundle.format("bullet.multiplier", (int)type.ammoMultiplier));
                    }

                    if(!compact && !Mathf.equal(type.reloadMultiplier, 1f)){
                        int val = (int)(type.reloadMultiplier * 100 - 100);
                        sep(bt, bundle.format("bullet.reload", ammoStat(val)));
                    }

                    if(type.knockback > 0){
                        sep(bt, bundle.format("bullet.knockback", Strings.autoFixed(type.knockback, 2)));
                    }

                    if(type.healPercent > 0f){
                        sep(bt, bundle.format("bullet.healpercent", (int)type.healPercent));
                    }

                    if(type.pierce || type.pierceCap != -1){
                        sep(bt, type.pierceCap == -1 ? "@bullet.infinitepierce" : bundle.format("bullet.pierce", type.pierceCap));
                    }

                    if(type.incendAmount > 0){
                        sep(bt, "@bullet.incendiary");
                    }

                    if(type.homingPower > 0.01f){
                        sep(bt, "@bullet.homing");
                    }

                    if(type.lightning > 0){
                        sep(bt, bundle.format("bullet.lightning", type.lightning, type.lightningDamage < 0 ? type.damage : type.lightningDamage));
                    }

                    if(type.pierceArmor){
                        sep(bt, "@bullet.armorpierce");
                    }

                    if(type.suppressionRange > 0){
                        sep(bt, bundle.format("bullet.suppression", Strings.autoFixed(type.suppressionDuration / 60f, 2), Strings.fixed(type.suppressionRange / tilesize, 1)));
                    }

                    if(type.status != StatusEffects.none){
                        sep(bt, (type.status.minfo.mod == null ? type.status.emoji() : "") + "[stat]" + type.status.localizedName + (type.status.reactive ? "" : "[lightgray] ~ [stat]" + ((int)(type.statusDuration / 60f)) + "[lightgray] " + bundle.get("unit.seconds")));
                    }

                    if(type.intervalBullet != null){
                        bt.row();

                        Table ic = new Table();
                        ammo(ObjectMap.of(t, type.intervalBullet), indent + 1, false).display(ic);
                        Collapser coll = new Collapser(ic, true);
                        coll.setDuration(0.1f);

                        bt.table(it -> {
                            it.left().defaults().left();

                            it.add(bundle.format("bullet.interval", Strings.autoFixed(type.intervalBullets / type.bulletInterval * 60, 2)));
                            it.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }

                    if(type.fragBullet != null){
                        bt.row();

                        Table fc = new Table();
                        ammo(ObjectMap.of(t, type.fragBullet), indent + 1, false).display(fc);
                        Collapser coll = new Collapser(fc, true);
                        coll.setDuration(0.1f);

                        bt.table(ft -> {
                            ft.left().defaults().left();

                            ft.add(bundle.format("bullet.frags", type.fragBullets));
                            ft.button(Icon.downOpen, Styles.emptyi, () -> coll.toggle(false)).update(i -> i.getStyle().imageUp = (!coll.isCollapsed() ? Icon.upOpen : Icon.downOpen)).size(8).padLeft(16f).expandX();
                        });
                        bt.row();
                        bt.add(coll);
                    }
                }).padLeft(indent * 5).padTop(5).padBottom(compact ? 0 : 5).growX().margin(compact ? 0 : 10);

                table.row();
            }
        };
    }

    public static StatValue statusEffect(StatusEffect effect){
        return table -> table.add((effect.minfo.mod == null ? effect.emoji() : "") + "[white]" + effect.localizedName);
    }

    public static StatValue content(UnlockableContent content){
        return table -> {
            table.row();
            table.table(t -> {
                t.image(icon(content)).size(3 * 8);
                t.add("[lightgray]" + content.localizedName).padLeft(6);
                infoButton(t, content, 4 * 8).padLeft(6);
            });
        };
    }

    public static Cell<TextButton> infoButton(Table table, UnlockableContent content, float size){
        return table.button("?", Styles.flatBordert, () -> ui.content.show(content)).size(size).left().name("contentinfo");
    }

    private static Cell<Label> sep(Table table, String text){
        table.row();
        return table.add(text);
    }

    private static String ammoStat(float val){
        return (val > 0 ? "[stat]+" : "[negstat]") + Strings.autoFixed(val, 1);
    }

    private static TextureRegion icon(UnlockableContent t){
        return t.fullIcon;
    }
}

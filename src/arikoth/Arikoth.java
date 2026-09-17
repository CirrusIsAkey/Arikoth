package arikoth;

import arc.*;
import arc.util.*;
import arikoth.graphics.shaders.ArikothLightRendererLoad;
import arikoth.graphics.ArikothRingShader;
import arikoth.graphics.EnvRenderer;
import arikoth.graphics.shaders.ArikothLightShaderLoad;
import arikoth.graphics.shaders.ArikothShaders;
import mindustry.Vars;
import mindustry.game.*;
import mindustry.mod.*;
import arikoth.content.*;
import arikoth.ui.*;
import mindustry.ui.dialogs.BaseDialog;

public class Arikoth extends Mod{
    public static boolean isMod = true;

    public Arikoth(){
        Log.info("Its Arikoth Time!");

        Events.on(EventType.ClientLoadEvent.class, (event) -> {
            EditorUIModifier.modify();

            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("startup notice");
                dialog.cont.add("Arikoth is currently in development").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                //dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
                dialog.cont.button("Ok", dialog::hide).size(100f, 50f);
                dialog.show();
            });
        });
        Events.on(EventType.FileTreeInitEvent.class, e -> {
            Core.app.post(ArikothLightShaderLoad::load);
            Core.app.post(ArikothShaders::load);
        });
    }

    @Override
    public void loadContent(){
        ArikothRingShader.load();
        ArikothSounds.load();
        ArikothStatusEffects.load();
        ArikothLiquids.load();
        ArikothItems.load();
        ArikothUnitTypes.load();
        RecipeRegister.load();
        ArikothBlocks.load();

        ArikothPlanets.load();
        ArikothSectorPresets.load();

        EnvRenderer.init();
    }

    @Override
    public void init(){
        ArikothTeams.load();

        Core.settings.put(SettingKeys.lighting, true);

        if(Core.settings.getBool(SettingKeys.lighting)) Reflect.set(Vars.renderer, "lights", new ArikothLightRendererLoad());
    }
}
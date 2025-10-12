package arikoth;

import arc.*;
import arc.math.*;
import arc.util.*;
import arikoth.content.otherPlanets.SerpuloUnits;
import arikoth.content.otherPlanets.*;
import arikoth.graphics.EnvRenderer;
import mindustry.game.*;
import mindustry.mod.*;
import arikoth.content.*;
import arikoth.ui.*;

public class Arikoth extends Mod{

    public Arikoth(){
        Log.info("Its Arikoth Time!");

        Events.on(EventType.ClientLoadEvent.class, (event) -> {
            EditorUIModifier.modify();
        });
    }

    @Override
    public void loadContent(){
        ArikothTeams.load();
        ArikothInnerBlocks.load();
        ArikothSounds.load();
        ArikothLiquids.load();
        ArikothItems.load();
        ArikothUnitTypes.load();
        ArikothLifeformUnitTypes.load();
        ArikothBlocks.load();

        VanillaStatusEffects.load();
        SerpuloItems.load();
        SerpuloLiquids.load();
        SerpuloUnits.load();
        SerpuloBlocks.load();
        TechTreeContent.load();
        ArikothPlanets.load();
        GhereonTech.load();

    //    RecipeRegister.load();

        EnvRenderer.init();
    }

}
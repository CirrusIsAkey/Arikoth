package arikoth.content;

import arikoth.content.blocks.*;

public class ArikothBlocks {
    public static void load() {
        ArikothEnv.load();
        ArikothLogistics.load();
        ArikothDrilling.load();
        ArikothStorage.load();
        ArikothDefence.load();
        ArikothPower.load();
        ArikothCrafting.load();
        ArikothWalls.load();
    }
};
package arikoth.content;

import arikoth.content.blocks.ArikothDrilling;
import arikoth.content.blocks.ArikothEnv;
import arikoth.content.blocks.ArikothLogistics;
import arikoth.content.blocks.ArikothStorage;

public class ArikothBlocks {
    public static void load() {
        ArikothEnv.load();
        ArikothLogistics.load();
        ArikothDrilling.load();
        ArikothStorage.load();
    }
};

package arikoth.content;

import arc.struct.Seq;
import mindustry.content.Liquids;
import mindustry.game.Objectives;

import static arikoth.content.ArikothLiquids.*;
import static mindustry.content.Liquids.*;
import static mindustry.content.SectorPresets.facility32m;
import static mindustry.content.TechTree.*;
import static arikoth.content.ArikothBlocks.*;
import static arikoth.content.ArikothItems.*;
import static arikoth.content.ArikothPlanets.*;
import static arikoth.content.ArikothSectors.*;
import static mindustry.content.Items.*;

public class ArikothTechTree {
    public static void load(){
        arikoth.techTree = nodeRoot("arikoth", coreSerenity, false, () -> {
            node(rhenium, Seq.with(new Objectives.OnSector(awakening)), ()-> {
                node(nickel, Seq.with(new Objectives.Research(rhenium)),  ()-> {
                    node(strontium, ()-> {
                    });
                });
                node(sand, ()-> {
                            node(quartz, () -> {
                                node(silicon, () -> {
                                });
                            });
                        });
                node(liquidQuickSand, ()-> {
                    node(mercury, ()-> {
                        node(Liquids.slag, ()-> {
                            node(ozone, ()-> {
                                node(blastCompound, ()-> {
                                });
                            });
                            node(irium, ()-> {
                            });
                        });
                        node(amalgam, ()-> {
                        });
                    });
                    node(moltenSalt, ()-> {
                    });
                });
            });

            node(vaccumShaft, ()-> {
                node(vaccumShaftRouter, ()-> {
                    node(vaccumShaftOverpass, ()-> {
                    });
                });
            });

            node(plasmaDriller, Seq.with(new Objectives.Research(rhenium)), ()-> {
            });

            node(puncture, ()-> {
                node(calefex, /*Seq.with(new Objectives.OnSector(sagar))*/ ()-> {
                });
            });

            node(nickelWall, ()-> {
                node(nickelWallLarge, ()-> {
                });
            });

            node(helioPanel, ()-> {
                node(teslaNode, ()-> {
                    node(advancedTeslaNode, ()-> {
                    });
                });
                node(strontiumGen, ()-> {
                });
            });

            node(amalgamFoundry, Seq.with(
            new Objectives.Research(strontium)
            ), ()-> {
            });
            node(awakening, ()-> {
                node(sagar, Seq.with(new Objectives.SectorComplete(awakening)), ()-> {
                });
            });
        });
    }
}
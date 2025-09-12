package arikoth.content;

import arc.graphics.Color;
import mindustry.game.Team;

public class ArikothTeams {
    public static Team conquisitoris;

    public static void load(){
        conquisitoris = editTeam(Team.blue, "\uECC3", "conquisitoris", Color.valueOf("a8b2ff"),
                Color.valueOf("a8b2ff"),
                Color.valueOf("887dff"),
                Color.valueOf("5a4fd1")
        );
    }

    public static Team editTeam(Team team, String icon, String name, Color color, Color pal1, Color pal2, Color pal3){
        team.emoji = icon;
        team.name = name;
        team.color.set(color);
        team.palette[0] = pal1;
        team.palette[1] = pal2;
        team.palette[2] = pal3;

        for(int i = 0; i < 3; i++){
            team.palettei[i] = team.palette[i].rgba();
        }

        team.hasPalette = true;

        return team;
    }
}

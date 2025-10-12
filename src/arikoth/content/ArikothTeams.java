package arikoth.content;

import arc.graphics.Color;
import mindustry.game.Team;

public class ArikothTeams {
    public static Team conquisitoris, luxis;

    public static void load(){
        luxis = editTeam(Team.green, "Luxis", Color.valueOf("8875ff"),
                Color.valueOf("8875ff"),
                Color.valueOf("594dd0"),
                Color.valueOf("362c9c")
        );
        conquisitoris = editTeam(Team.blue, "Conquistoris", Color.valueOf("a8b2ff"),
                Color.valueOf("a8b2ff"),
                Color.valueOf("887dff"),
                Color.valueOf("5a4fd1")
        );
    }

    public static Team editTeam(Team team, String name, Color color, Color pal1, Color pal2, Color pal3){
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

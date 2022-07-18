package snakegame.snakegame.UI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import snakegame.snakegame.game.GameSnakesManager;
import snakegame.snakegame.gameObjects.snakes.Snake;
import snakegame.snakegame.gameObjects.snakes.SnakeBase;

public class MyScoreBoard {

    public static void setup(Player player, GameSnakesManager gameSnakesManager){




        Scoreboard board= player.getScoreboard();
        if(board==null){
            board=    Bukkit.getScoreboardManager().getNewScoreboard();
        }



        Objective obj=board.getObjective("snakeBoard");
        if(obj==null){
            obj=board.registerNewObjective("snakeBoard",
                    "dummy",
                    ChatColor.GREEN+"Snake Board");
        }


        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        int i=0;
        for(SnakeBase snake: gameSnakesManager.getSnakes()){
            Team rank1=board.getTeam(snake.getColour()+snake.getName());

            if(rank1==null){
                rank1=board.registerNewTeam(snake.getColour()+snake.getName());
            }

            rank1.addEntry(ChatColor.BOLD+snake.getColour());
            rank1.setPrefix("");
            rank1.setSuffix("");
            obj.getScore(ChatColor.BOLD+snake.getColour()).setScore(i);
            i++;
        }



        player.setScoreboard(board);
    }

    public static void unregister(Player player,GameSnakesManager gameSnakesManager){
        for(SnakeBase snake:gameSnakesManager.getSnakes()){
            Team team = player.getScoreboard().getTeam(snake.getColour()+snake.getName());
            if(team!=null){
                team.unregister();
            }


        }
        Objective obj= player.getScoreboard().getObjective("snakeBoard");
        if(obj!=null){
            obj.unregister();
        }
//        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

    }

    public static void update(Player player, GameSnakesManager gameSnakesManager){

        for(SnakeBase snake:gameSnakesManager.getSnakes()){
            StringBuilder style=new StringBuilder();
            if(snake.isDead()){
                style.append(ChatColor.STRIKETHROUGH);
            }
            if(snake instanceof Snake){

                style.append(ChatColor.BOLD);
                style.append(ChatColor.ITALIC);
                style.append(ChatColor.UNDERLINE);
            }

            player.getScoreboard().getTeam(snake.getColour()+snake.getName()).setSuffix(
                    snake.getColour()+style+snake.getName()+": "+ChatColor.RESET+snake.getLength()+"    "
            );

        }

    }
}

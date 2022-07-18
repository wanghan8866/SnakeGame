package snakegame.snakegame.CommandsExecutor;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import snakegame.snakegame.Arena.ArenaSize;
import snakegame.snakegame.SnakeGameMain;
import snakegame.snakegame.game.SnakeGame;
import snakegame.snakegame.gameObjects.Direction;

public class SnakeCommandExecutor implements CommandExecutor {
    private SnakeGameMain snakeGameMain;

    public SnakeCommandExecutor(SnakeGameMain snakeGameMain){
        this.snakeGameMain=snakeGameMain;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player=(Player) sender;

            if(args.length==4 && args[0].equalsIgnoreCase("spawn_head")){
                try {
                    player.sendMessage(ChatColor.GREEN+"Hi testing");
                    System.out.println(args);
                    int x=Integer.parseInt(args[1]);
                    int y=Integer.parseInt(args[2]);
                    int z=Integer.parseInt(args[3]);

                    this.snakeGameMain.getSnakeManager().createNewSnake(player,new Location(player.getWorld(),x,y,z));
                }catch (IllegalArgumentException e){
                    player.sendMessage(ChatColor.RED+"Wrong format for location");
                    e.printStackTrace();
                }

            }
            else if(args.length==1 && args[0].equalsIgnoreCase("reduce")){
                this.snakeGameMain.getSnakeManager().reduceSnake(player);
            }else if(args.length==1 && args[0].equalsIgnoreCase("accelerating")){
                this.snakeGameMain.getSnakeManager().acceleratingSnakeToggle(player);
            }else if(args.length==2 && args[0].equalsIgnoreCase("add")){
                try {

                    int length=Integer.parseInt(args[1]);


                    this.snakeGameMain.getSnakeManager().addSnakeBody(player,length);
                }catch (IllegalArgumentException e){
                    player.sendMessage(ChatColor.RED+"Wrong format for length");
                    e.printStackTrace();
                }
            }
            else if(args.length==2&& args[0].equalsIgnoreCase("move")){
                try {
                    Direction direction=Direction.valueOf(args[1]);
                    this.snakeGameMain.getSnakeManager().moveSnake(player,direction);

                }catch (IllegalArgumentException e){
                    player.sendMessage(ChatColor.RED+"Wrong format for direction");
                    e.printStackTrace();
                }
            }
            else if(args.length==2 && args[0].equalsIgnoreCase("game")&& args[1].equalsIgnoreCase("init")){
//                this.snakeGameMain.getSnakeManager().moveSnake(player,Direction.FORWARD);
                this.snakeGameMain.getGameManager().setGame(player, SnakeGame.createSimpleGame(this.snakeGameMain,player));

                player.performCommand("snake arena build MEDIUM");
                player.performCommand("snake game start");

            }
            else if(args.length==2 && args[0].equalsIgnoreCase("game")&& args[1].equalsIgnoreCase("start")){
//                this.snakeGameMain.getSnakeManager().moveSnake(player,Direction.FORWARD);

                this.snakeGameMain.getGameManager().startGame(player);
            }
            else if(args.length==2 && args[0].equalsIgnoreCase("game")&& args[1].equalsIgnoreCase("resume")){
//                this.snakeGameMain.getSnakeManager().moveSnake(player,Direction.FORWARD);

                this.snakeGameMain.getGameManager().resumeGame(player);
            }
            else if(args.length==2 && args[0].equalsIgnoreCase("game")&& args[1].equalsIgnoreCase("pause")){
//                this.snakeGameMain.getSnakeManager().moveSnake(player,Direction.FORWARD);

                this.snakeGameMain.getGameManager().pauseGame(player);
            }
            else if(args.length==2 && args[0].equalsIgnoreCase("game")&& args[1].equalsIgnoreCase("end")){
//                this.snakeGameMain.getSnakeManager().moveSnake(player,Direction.FORWARD);
//                this.snakeGameMain.getGameManager().setGame(player, SnakeGame.createSimpleGame(this.snakeGameMain,player));
                player.sendMessage(ChatColor.RED+"The game has ended!");
                this.snakeGameMain.getGameManager().endGame(player);
            }
            else if(args.length==3 && args[0].equalsIgnoreCase("arena")&&args[1].equalsIgnoreCase("build")){
//                this.snakeGameMain.getSnakeManager().moveSnake(player,Direction.FORWARD);
//                this.snakeGameMain.getGameManager().setGame(player, SnakeGame.createSimpleGame(this.snakeGameMain,player));
                try{

                    ArenaSize arenaSize=ArenaSize.valueOf(args[2]);
                    SnakeGame game=this.snakeGameMain.getGameManager().getGame(player);
                    if(game!=null){
                        game.buildArena(player.getLocation(),arenaSize);
                    }


                }catch (IllegalArgumentException e){
                    player.sendMessage(ChatColor.RED+"Wrong format for arena size");
                    e.printStackTrace();
                }

            }
            else if(args.length==2 && args[0].equalsIgnoreCase("arena")&&args[1].equalsIgnoreCase("clear")){
                SnakeGame game=this.snakeGameMain.getGameManager().getGame(player);
                if(game!=null){
                    game.clearArena();
                }


            }
            else {
                player.sendMessage(ChatColor.RED+"No such commands for snake!");
            }
        }

        return false;
    }
}

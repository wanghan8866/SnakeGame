package snakegame.snakegame.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.Arena.ArenaSize;
import snakegame.snakegame.SnakeGameMain;
import snakegame.snakegame.UI.Controller;
import snakegame.snakegame.UI.MyScoreBoard;
import snakegame.snakegame.gameObjects.snakes.*;
import snakegame.snakegame.manager.PathFindingManager;
import snakegame.snakegame.manager.PathManager;
import snakegame.snakegame.manager.SnakeManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SnakeGame {
    private Player player;
    private SnakeGameMain snakeGameMain;
    private Arena arena;
    private List<Snake> snakes;
    private BukkitTask bukkitTask=null;
    private boolean running;
    private double spawnRate=0.5;
    private GameSnakesManager gameSnakesManager;
    private GameCountDown gameCountDown;
    private final int maxFood=12;
    private static Random random=new Random();



    public SnakeGame(SnakeGameMain snakeGameMain, Player player, Arena arena){
        this.snakeGameMain=snakeGameMain;
        this.arena=arena;
        this.player=player;
        this.snakes=new ArrayList<>();
        running=false;
        gameSnakesManager=new GameSnakesManager(snakeGameMain);
        gameCountDown=null;

    }
    public void startGame(){


        if(player==null){
            return;
        }


        if(arena==null){
            player.sendMessage(ChatColor.RED+"To start a game, you have to create an arena first!");
            return;
        }

        if(bukkitTask!=null){
//            bukkitTask.cancel();
            player.sendMessage(ChatColor.RED+"To start a game, you have to end it first!");
            return;

        }



        Snake snake=this.snakeGameMain.getSnakeManager().getSnake(player);



        if(snake==null||snake.isDead()){
//            player.sendMessage(ChatColor.RED+"To start a game, you have to spawn it first!");
//            return;

            this.snakeGameMain.getSnakeManager().createNewSnake(player,arena.getRandomEmptyLocation());
            snake=this.snakeGameMain.getSnakeManager().getSnake(player);

        }



        snake.setArena(arena);


        Controller.setup(player);


        AtomicInteger i= new AtomicInteger();

        Snake finalSnake = snake;

        PathFindingManager.setup(arena.getGrid());
        gameSnakesManager.addSnake(snake);
        gameSnakesManager.addSnake(AISnake.createAISnake(arena.getRandomEmptyLocation(),arena));
        gameSnakesManager.addSnake(RandomAISnake.createAISnake(arena.getRandomEmptyLocation(),arena));
        AdvancedAISnake advancedAISnake=AdvancedAISnake.createAISnake(arena.getRandomEmptyLocation(),arena);
        gameSnakesManager.addSnake(advancedAISnake);
        arena.addSnake(advancedAISnake);

        GreedyAISnake greedyAISnake =GreedyAISnake.createAISnake(arena.getRandomEmptyLocation(),arena);
        gameSnakesManager.addSnake(greedyAISnake);
        arena.addSnake(greedyAISnake);


        MyScoreBoard.setup(player,gameSnakesManager);


        resumeGame();
    }

    public void endGame(){

        if(bukkitTask!=null){
            bukkitTask.cancel();
            bukkitTask=null;
            running=false;
            MyScoreBoard.unregister(player,gameSnakesManager);
            arena.reset();
            gameSnakesManager.clear();
            this.snakeGameMain.getSnakeManager().clear();
        }

    }

    public void pauseGame(){
        if(bukkitTask!=null){
            bukkitTask.cancel();
            bukkitTask=null;
            running=false;
        }
    }

    public void resumeGame(){
        if(bukkitTask!=null) return;
        if(running) return;
        running=true;

        gameCountDown=new GameCountDown(snakeGameMain,3,player);
        gameCountDown.start();

        bukkitTask=Bukkit.getScheduler().runTaskTimer(this.snakeGameMain,()->{

//            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(),"snake move "+ Direction.FORWARD+ " "+player.getName());
//            i.set((int) ((i.get() + 1) % Math.round(20/finalSnake.getSpeed())));
//            System.out.println("i: "+i);
//            System.out.println("speed: "+finalSnake.getSpeed());
//
//            if(i.get()==0){
//                player.performCommand("snake move "+ Direction.FORWARD.name());
//            }
            gameSnakesManager.moveAllSnakes();
//            PathManager.clearAllPaths();

            if(arena.getSnake()!=null&&arena.getSnake().getGoalLength()<=maxFood&&random.nextDouble()<=spawnRate/20){
                Location foodLocation=arena.spawnFoodRandomly();
                System.out.println("food spawn!");
//                if(foodLocation!=null&&!advancedAISnake.isDead()){
//                    advancedAISnake.addGoal(foodLocation);
//                }

            }
            MyScoreBoard.update(player,gameSnakesManager);

//            PathFindingManager.displayGrid();


//            PathManager.displayAllPath();

        },70 ,1);
    }

    public static SnakeGame createSimpleGame(SnakeGameMain snakeGameMain,Player player){
        return new SnakeGame(snakeGameMain,player,null);
    }

    public static SnakeGame createAGame(SnakeGameMain snakeGameMain, Player player, Arena arena){
        return new SnakeGame(snakeGameMain,player,arena);
    }
    public static SnakeGame createAGame(SnakeGameMain snakeGameMain, Player player){
        return new SnakeGame(snakeGameMain,player,Arena.createClassicArena(player.getLocation()));
    }

    public boolean isRunning() {
        return running;
    }

    public Arena getArena() {
        return arena;
    }

    public void buildArena(Location location, ArenaSize arenaSize){

        if(arena==null){
            arena=Arena.createClassicArena(location,arenaSize);
            arena.build();
        }else{
            player.sendMessage(ChatColor.RED+"Arena already exists, clear it before using it again");
        }
    }

    public void clearArena(){
        if(arena==null){
            player.sendMessage(ChatColor.RED+"Arena does not exits!");
        }else if(!running){
            this.arena.clear();
            this.snakeGameMain.getSnakeManager().removeSnake(player);
            arena=null;
        }else{
            player.sendMessage(ChatColor.RED+"Can not clear the arena before the game stops");
        }
    }
}

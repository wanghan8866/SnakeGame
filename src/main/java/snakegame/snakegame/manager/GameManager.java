package snakegame.snakegame.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.SnakeGameMain;
import snakegame.snakegame.game.SnakeGame;

import java.util.HashMap;
import java.util.UUID;

public class GameManager {
    private SnakeGameMain snakeGameMain;
    private HashMap<UUID, SnakeGame> gameMaps;

    public GameManager(SnakeGameMain snakeGameMain){
        this.snakeGameMain=snakeGameMain;
        gameMaps=new HashMap<>();


        MaterialRegister.addBadMaterial(Material.DIAMOND_BLOCK);
        MaterialRegister.addBadMaterial(Material.PINK_STAINED_GLASS);
        MaterialRegister.addBadMaterial(Material.REDSTONE_BLOCK);
        MaterialRegister.addBadMaterial(Material.LIME_CONCRETE);
        MaterialRegister.addBadMaterial(Material.GREEN_CONCRETE);
        MaterialRegister.addBadMaterial(Material.AMETHYST_BLOCK);
        MaterialRegister.addBadMaterial(Material.BLUE_STAINED_GLASS);
        MaterialRegister.addBadMaterial(Material.LIGHT_BLUE_STAINED_GLASS);
//        MaterialRegister.addBadMaterial(Material.WAXED_COPPER_BLOCK);
        MaterialRegister.addBadMaterial(Material.PURPLE_STAINED_GLASS);
        MaterialRegister.addBadMaterial(Material.LIME_STAINED_GLASS);
        MaterialRegister.addBadMaterial(Material.RAW_COPPER_BLOCK);
        MaterialRegister.addBadMaterial(Material.YELLOW_STAINED_GLASS);
        MaterialRegister.addBadMaterial(Material.ORANGE_STAINED_GLASS);
        MaterialRegister.addBadMaterial(Material.COPPER_ORE);
        MaterialRegister.addBadMaterial(Material.WAXED_COPPER_BLOCK);
        MaterialRegister.addBadMaterial(Material.WAXED_OXIDIZED_COPPER);
        MaterialRegister.addBadMaterial(Material.OBSIDIAN);

        MaterialRegister.addGoodMaterial(Material.GOLD_BLOCK);
//        MaterialRegister.addGoodMaterial(Material.AIR);
    }

    public SnakeGame getGame(Player player){
        if(player==null){
            return null;
        }

        if(gameMaps.containsKey(player.getUniqueId())){
            return gameMaps.get(player.getUniqueId());
        }
        player.sendMessage(ChatColor.RED+"No valid game is created");
        return null;
    }

    public void setGame(Player player, SnakeGame game){
        if(gameMaps.containsKey(player.getUniqueId())){
            player.sendMessage(ChatColor.RED+"You are in a game!");
            return;
        }
        gameMaps.put(player.getUniqueId(), game);
    }
    public boolean hasGameReady(Player player){
        return gameMaps.containsKey(player.getUniqueId())&&!gameMaps.get(player.getUniqueId()).isRunning();
    }
    public boolean hasGameRunning(Player player){
        return gameMaps.containsKey(player.getUniqueId())&&gameMaps.get(player.getUniqueId()).isRunning();
    }
    public void startGame(Player player){
        if(hasGameReady(player)){
            gameMaps.get(player.getUniqueId()).startGame();
        }else{
            player.sendMessage(ChatColor.RED+"The game is already running");
        }
    }
    public void pauseGame(Player player){
        if(gameMaps.get(player.getUniqueId()).isRunning()){
            gameMaps.get(player.getUniqueId()).pauseGame();
        }else{
            player.sendMessage(ChatColor.RED+"The game is already running");
        }
    }
    public void resumeGame(Player player){
        if(!gameMaps.get(player.getUniqueId()).isRunning()){
            gameMaps.get(player.getUniqueId()).resumeGame();
        }else{
            player.sendMessage(ChatColor.RED+"The game is already running");
        }
    }

    public void endGame(Player player){
        if(hasGameRunning(player)){
            gameMaps.get(player.getUniqueId()).endGame();
        }else{
            player.sendMessage(ChatColor.RED+"No valid game is running");
        }
    }


    public void endAllGame(){
        for(SnakeGame game:gameMaps.values()){
            game.endGame();
        }
    }
}

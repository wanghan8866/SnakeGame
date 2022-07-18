package snakegame.snakegame.manager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import snakegame.snakegame.SnakeGameMain;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.gameObjects.snakes.Snake;

import java.util.HashMap;
import java.util.UUID;

public class SnakeManager {
    private SnakeGameMain snakeGameMain;
    private HashMap<UUID, Snake> maps;

    public SnakeManager(SnakeGameMain snakeGameMain){
        this.snakeGameMain=snakeGameMain;
        this.maps=new HashMap<>();
    }

    public void createNewSnake(Player player, Location location){
        if(player==null) return;
        if(location==null) return;


        maps.put(player.getUniqueId(),Snake.createSnake(player,location,null));
    }

    public Snake getSnake(Player player){
        if(maps.containsKey(player.getUniqueId())){
            return maps.get(player.getUniqueId());
        }
        return null;
    }
    public void removeSnake(Player player){
        if(maps.containsKey(player.getUniqueId())){
            Snake snake=maps.get(player.getUniqueId());
            snake.clear(true);
            maps.remove(player.getUniqueId());
        }

    }
    public void moveSnake(Player player, Direction direction){
        Snake snake=getSnake(player);
        if(snake==null){
            player.sendMessage(ChatColor.RED+"You do not have a snake!");
            return;
        }
        snake.move(direction);
    }
    public void addSnakeBody(Player player,int length){
        Snake snake=getSnake(player);
        if(snake==null){
            player.sendMessage(ChatColor.RED+"You do not have a snake!");
            return;
        }
        snake.add();
    }

    public void reduceSnake(Player player){
        Snake snake=getSnake(player);
        if(snake==null){
            player.sendMessage(ChatColor.RED+"You do not have a snake!");
            return;
        }
        snake.reduce();
    }


    public void acceleratingSnakeToggle(Player player){
        Snake snake=getSnake(player);
        if(snake==null){
            player.sendMessage(ChatColor.RED+"You do not have a snake!");
            return;
        }
        snake.setAccelerating(!snake.isAccelerating());
    }
    public HashMap<UUID, Snake> getMaps() {
        return maps;
    }

    public void clear(){
        maps.clear();
    }


}

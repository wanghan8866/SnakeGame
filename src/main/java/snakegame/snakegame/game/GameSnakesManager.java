package snakegame.snakegame.game;

import snakegame.snakegame.SnakeGameMain;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.gameObjects.snakes.Snake;
import snakegame.snakegame.gameObjects.snakes.SnakeBase;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class GameSnakesManager {
    private final HashMap<SnakeBase, AtomicInteger> snakesMap;
    private final HashSet<SnakeBase> snakes;
    private final SnakeGameMain snakeGameMain;

    public GameSnakesManager(SnakeGameMain snakeGameMain){
        snakesMap=new HashMap<>();
        this.snakeGameMain=snakeGameMain;
        snakes=new HashSet<>();
    }

    public void addSnake(SnakeBase snake){
        snakesMap.put(snake,new AtomicInteger());
        snakes.add(snake);
    }
    public void moveAllSnakes(){
        HashMap<SnakeBase, AtomicInteger> new_map=new HashMap<>();
        for(SnakeBase snake:snakesMap.keySet()){
            AtomicInteger i=snakesMap.get(snake);
            i.set((int) ((i.get() + 1) % Math.round(20/snake.getSpeed())));
            if(i.get()==0&&!snake.isDead()){
                snake.move(Direction.FORWARD);

            }
            if(!snake.isDead()){
                new_map.put(snake,i);
            }

//            System.out.println(snake+" "+snakesMap.get(snake));
        }
        if(new_map.isEmpty()){
            this.snakeGameMain.getGameManager().endAllGame();
        }

        snakesMap.clear();
        snakesMap.putAll(new_map);

    }

    public void clear(){
        for(SnakeBase snake:snakes){
            snake.clear(true);
        }
        snakesMap.clear();
        snakes.clear();
    }
    public Set<SnakeBase> getSnakes(){
        return snakes;
    }
}

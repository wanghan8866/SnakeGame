package snakegame.snakegame.manager;

import org.bukkit.Material;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.gameObjects.snakes.SnakeState;

public class CollisionManager {
    public static SnakeState collideWith(Material material){
        if(MaterialRegister.isBadMaterial(material)){

            return SnakeState.DIED;
        }else if(MaterialRegister.isGoodMaterial(material)){
            return SnakeState.EATING;
        }
        return SnakeState.ALIVE;
    }
}

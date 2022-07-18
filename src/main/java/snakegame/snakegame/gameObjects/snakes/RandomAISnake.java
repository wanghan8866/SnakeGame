package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.gameObjects.Direction;

public class RandomAISnake extends AISnake{
    public RandomAISnake(Material headType, Material bodyType1, Material bodyType2, Location location, String name, String colour,Arena arena) {
        super(headType, bodyType1, bodyType2, location,name,colour,arena);
    }

    @Override
    public void move(Direction direction) {
        if(!dead){
            double random_number=random.nextDouble();
            if(random_number<0.2){
                super.move(Direction.LEFT);
            }else if(random_number>0.8){
                super.move(Direction.RIGHT);
            }
            if(direction.equals(Direction.FORWARD)){
                super.move(Direction.FORWARD);
            }
        }



    }

    public static RandomAISnake createAISnake(Location location, Arena arena){
        return new RandomAISnake(Material.AMETHYST_BLOCK,Material.BLUE_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS,
                location, "Random AI Snake", ChatColor.DARK_PURPLE.toString(), arena
        );
    }
}

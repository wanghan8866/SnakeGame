package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.manager.PathFindingManager;

import java.util.PriorityQueue;

public class GreedyAISnake extends AdvancedAISnake{
    public GreedyAISnake(Material headType, Material bodyType1, Material bodyType2, Location location, String name, String colour, Arena arena) {
        super(headType, bodyType1, bodyType2, location, name, colour, arena);
        color= Color.YELLOW;
    }

    @Override
    public void updateGoals() {
        PriorityQueue<Location> tem=new PriorityQueue<>(goalList.comparator());
        for(Location location:goalList){
            if(MaterialRegister.isGoodMaterial(location.getBlock().getType())){
                tem.add(location);
            }
        }

        goalList.clear();
        goalList=tem;
        if(goalList.isEmpty()) return;
        if(goal!=null&&!MaterialRegister.isGoodMaterial(goal.getBlock().getType())){
//            goal=goalList.get(0);
            setGoal(goalList.peek());

        }else if(goal==null){
            setGoal(goalList.peek());
        }
        else if(currentPath!=null&&currentPath.pathLength()>1.5*PathFindingManager.getDistance(head.getLocation(),goalList.peek())){
            setGoal(goalList.peek());
        }
        if(currentPath==null){
            for(Location goal:goalList){
                setGoal(goal);
                if(currentPath!=null){
                    return;
                }

            }
            goal=null;

        }
    }

    public static GreedyAISnake createAISnake(Location location, Arena arena){
        return new GreedyAISnake(Material.RAW_COPPER_BLOCK,Material.YELLOW_STAINED_GLASS, Material.ORANGE_STAINED_GLASS,
                location,"Greedy AI Snake", ChatColor.YELLOW.toString(),arena
        );
    }
}

package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.gameObjects.Node;
import snakegame.snakegame.manager.PathFindingManager;

import java.util.PriorityQueue;

public class HamiltonianSnake extends AdvancedAISnake{
    public HamiltonianSnake(Material headType, Material bodyType1, Material bodyType2, Location location, String name, String colour, Arena arena) {
        super(headType, bodyType1, bodyType2, location, name, colour, arena);
        color= Color.RED;
        setGoal(this.arena.getRandomEmptyLocation());
    }

    @Override
    public void move(Direction direction) {

        if(dead) return;
        updateGoals();
        if(goal!=null){
//            currentPath=PathFindingManager.findHamiltonianPath(this.head.getLocation(),this.head.getCurrentDirection(),goal,10);

//            System.out.println("current path in snake: "+currentPath);
            if(currentPath!=null){
//                System.out.println("my direction before: "+head.getCurrentDirection());
//
                Node node=currentPath.pop();
//                System.out.println("direction: "+node.getCurrentDirection());
//                System.out.println("direction from: "+node.getFrom());
//
//                head.setCurrentDirection(node.getCurrentDirection());
                nativeMove(node.getFrom());
//                System.out.println("my direction after: "+head.getCurrentDirection());
                nativeMove(Direction.FORWARD);

                if(!head.getCurrentDirection().equals(node.getCurrentDirection())){
                    currentPath=null;
                }
//                head.setBlock(node.getLocation(),head.getMaterial(),true);
            }



            if(direction.equals(Direction.FORWARD)){
//                nativeMove(Direction.FORWARD);
            }

     }else{
            nativeMove(direction);
        }


    }

    @Override
    protected void setGoal(Location goal) {
        this.goal=goal;

        currentPath=PathFindingManager.findHamiltonianPath(this.head.getLocation(),
                this.head.getCurrentDirection(),goal
              ,10);
//        currentPath=PathFindingManager.findPath(head.getLocation(),head.getCurrentDirection(),goal);
        if(currentPath!=null){
//            System.out.println();
//            System.out.println(currentPath);
            currentPath.displayInMinecraft(color);
            currentPath.pop();
        }
    }

    @Override
    public void updateGoals() {


        if(currentPath!=null&&currentPath.size()==0){
            setGoal(this.arena.getRandomEmptyLocation());
        }else if(currentPath==null){
            setGoal(this.arena.getRandomEmptyLocation());
        }


//        if(currentPath!=null&&!currentPath.checkSafe()){
//            setGoal(this.arena.getRandomEmptyLocation());
//        }

    }

    public static HamiltonianSnake createAISnake(Location location, Arena arena){
        return new HamiltonianSnake(Material.COPPER_ORE,Material.WAXED_COPPER_BLOCK, Material.WAXED_OXIDIZED_COPPER,
                location,"Hamiltonian AI Snake", ChatColor.RED.toString(),arena
        );
    }
}

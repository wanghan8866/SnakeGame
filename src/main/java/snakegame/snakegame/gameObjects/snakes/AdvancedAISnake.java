package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.gameObjects.Path;
import snakegame.snakegame.manager.PathFindingManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class AdvancedAISnake extends AISnake{

    protected Location goal=null;
    protected Path currentPath=null;
    protected Color color=Color.PURPLE;
    protected PriorityQueue<Location> goalList=new PriorityQueue<Location>(Comparator.comparingInt(a -> PathFindingManager.getDistance(this.head.getLocation(), a)));

    public AdvancedAISnake(Material headType, Material bodyType1, Material bodyType2, Location location, String name,String colour,Arena arena) {
        super(headType, bodyType1, bodyType2, location,name,colour,arena);
//        this.accelerating=true;
        this.speed=4.0;
    }

    @Override
    public void move(Direction direction) {
        if(!dead){
            updateGoals();

            if(goal!=null&&MaterialRegister.isGoodMaterial(goal.getBlock().getType())){

                currentPath=PathFindingManager.findPath(head.getLocation(),head.getCurrentDirection(),goal);
                System.out.println("A* path: "+currentPath);
                if(direction.equals(Direction.FORWARD)&&currentPath!=null&&currentPath.getSecond()!=null){

//                    System.out.println("first: "+currentPath.getFirst().getFrom());
//                    System.out.println("second: "+currentPath.getSecond().getFrom());
//                    System.out.println("current: "+this.head.getCurrentDirection());
//                    Vector vec=currentPath.getFirst().getLocation().toVector().clone().add(currentPath.getSecond().getLocation().toVector().clone().multiply(-1));
//                    System.out.println("vec: "+vec);
                    super.move(currentPath.getSecond().getFrom());


                }else if(currentPath==null){
                    goal=null;
                }
            }
            super.move(direction);
        }

    }

    public static AdvancedAISnake createAISnake(Location location,Arena arena){
        return new AdvancedAISnake(Material.COPPER_BLOCK,Material.PURPLE_STAINED_GLASS, Material.LIME_STAINED_GLASS,
                location,"Advanced AI Snake", ChatColor.GOLD.toString(),arena
        );
    }
    protected void nativeMove(Direction direction){
        super.move(direction);
    }
    public Location getGoal() {
        return goal;
    }

    protected void setGoal(Location goal) {

        this.goal = goal;
        currentPath=PathFindingManager.findPath(head.getLocation(),head.getCurrentDirection(),goal);
        if(currentPath!=null){
//            System.out.println();
//            System.out.println(currentPath);
            currentPath.displayInMinecraft(color);
        }
    }
    public void updateGoals(){
        PriorityQueue<Location>tem=new PriorityQueue<>(goalList.comparator());
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


//        else if(currentPath.pathLength()>1.5*PathFindingManager.getDistance(head.getLocation(),goalList.peek())){
//            setGoal(goalList.peek());
//        }
    }

    @Override
    public double getSpeed() {
        double base_speed=Math.max(1,Math.min(this.speed,2.0/(0.05*Math.max(1,this.body.size()))));
        return base_speed;
    }

    public void addGoal(Location location){
        goalList.add(location);
    }
    public int getGoalLength(){
        return goalList.size();
    }
}

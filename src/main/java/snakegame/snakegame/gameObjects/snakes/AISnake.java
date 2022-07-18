package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.manager.CollisionManager;

import java.util.ArrayList;

public class AISnake extends SnakeBase{

    private Material headType;
    private Material bodyType1;
    private Material bodyType2;

    public AISnake(Material headType, Material bodyType1, Material bodyType2, Location location, String name,String colour, Arena arena) {
        this.headType = headType;
        this.bodyType1 = bodyType1;
        this.bodyType2 = bodyType2;
        this.head= SnakePart.spawnOneHead(location,this.headType);
        body= new ArrayList<SnakePart>();
        body.add(head);
        this.accelerating=false;
        dead=false;
        this.name=name;
        this.arena=arena;
        this.colour=colour;
    }

    @Override
    public void move(Direction direction) {
//        System.out.println("AI Snake move!");
        if(dead) return;
        if(direction.equals(Direction.FORWARD)){
            Location newLocation=this.head.getLocation().clone().add(this.head.getCurrentDirection().getVelocity());

            if(!typeCheck(newLocation.getBlock().getType(), null,false,false)){
                for(Direction testDirection:Direction.getControlDirection()){
                    if(testDirection.equals(Direction.FORWARD) && !testDirection.getOpposite().equals(this.head.getCurrentDirection())){
                        continue;
                    }

                    newLocation=this.head.getLocation().clone().add(Direction.directionTable.get(this.head.getCurrentDirection()).get(testDirection).getVelocity());
//                System.out.println("new test: "+newLocation);
//                    System.out.println("new test: "+testDirection);
//                    System.out.println("new test type: "+newLocation.getBlock().getType());
                    if(typeCheck(newLocation.getBlock().getType(), null,false,false)){
                        if(!dead){
//                            System.out.println("test correct: "+testDirection);
                            super.move(testDirection);

                            if(direction.equals(Direction.FORWARD)){
//                                System.out.println("AI move forward!");
                                super.move(Direction.FORWARD);
                            }

                        }
                        return;
                    }
                }
            }
        }

        if(!dead){

            super.move(direction);
        }

    }



    @Override
    public boolean typeCheck(Material material, Location oldLocation, boolean isAdding, boolean setDeath) {
        switch (CollisionManager.collideWith(material)){
            case DIED:if(setDeath){dead=true;
                System.out.println(name+" is dead");clear(false);};return false;
            case EATING:if(isAdding){add(oldLocation);}return true;
        }
        return true;
    }


    public void add(Location location) {
        Material type=(body.size()%2==0)?bodyType1:bodyType2;
        SnakePart snakePart=SnakePart.spawnOneBody(location,type);
        super.add(snakePart);
    }

    public static AISnake createAISnake(Location location, Arena arena){
        return new AISnake(Material.REDSTONE_BLOCK,Material.GREEN_CONCRETE, Material.LIME_CONCRETE,
                location, "AI Snake", ChatColor.RED.toString(),arena
                );
    }


}

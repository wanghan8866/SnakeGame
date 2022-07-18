package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.Location;
import org.bukkit.Material;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.gameObjects.Direction;

import java.util.List;
import java.util.Random;

abstract public class SnakeBase {
    protected List<SnakePart> body;
    protected SnakePart head;
    protected double speed=4.0;
    protected double accelerating_factor=2;
    protected double reducing_factor=0.5;
    protected boolean accelerating;
    protected boolean dead=false;
    protected Arena arena;
    protected String name;
    protected String colour;
    protected static Random random=new Random();




    public void move(Direction direction){
        if(!dead){
            Location lastLocation;
            if(direction.equals(Direction.FORWARD)&&accelerating){
                if(random.nextDouble()< reducing_factor){
                    reduce();
                }
            }
            if(body.size()>1){
//                System.out.println("snake: "+head+ " size: "+body.size());
                lastLocation=head.getLocation().clone();
                Location temLocation;
                Material material=head.move(direction,false);
                if(direction.equals(Direction.FORWARD)){
                    if(!typeCheck(material,lastLocation,false,true)){
                        return;
                    }
                    SnakePart snakePart;
                    for(int i=1;i<body.size()-1;i++){
                        snakePart=body.get(i);
                        temLocation=snakePart.getLocation().clone();
                        snakePart.setBlock(lastLocation,snakePart.getMaterial(),false);
                        lastLocation=temLocation.clone();
                    }

                    snakePart=body.get(body.size()-1);
                    temLocation=snakePart.getLocation().clone();
                    snakePart.setBlock(lastLocation,snakePart.getMaterial(),true);
                    typeCheck(material,temLocation,true,true);
                }

            }else{
                lastLocation=head.getLocation().clone();
                Material material=head.move(direction,true);
                if(direction.equals(Direction.FORWARD)){
                    typeCheck(material,lastLocation,true,true);
                }
            }
        }


    }

    public abstract boolean typeCheck(Material material, Location oldLocation, boolean isAdding,boolean setDeath);


    public void clear(boolean removeAll){
        for(SnakePart snakePart:body){
            snakePart.clear(removeAll,arena);


        }
    }
    public void reduce(){

        if(body.size()>1){
            SnakePart tail=body.remove(body.size()-1);
            tail.clear(false,arena);
//            System.out.println("you are reducing!");
        }else{
            setAccelerating(false);
        }

    }


    public void add(SnakePart body){
        this.body.add(body);
    }
    public void setArena(Arena arena){
        this.arena=arena;
    }
    public String getName(){
        return name;
    }





    public double getSpeed() {
//        System.out.println(2.0/(0.1*Math.max(1,this.body.size())));
        double base_speed=Math.max(1,Math.min(this.speed,2.0/(0.05*Math.max(1,this.body.size()))));
        if(accelerating){
            base_speed*=accelerating_factor;
        }
        return base_speed;
    }


    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isAccelerating() {
        return accelerating;
    }

    public void setAccelerating(boolean accelerating) {

        this.accelerating = accelerating;
    }

    public boolean isDead(){
        return dead;
    }
    public SnakePart getHead(){
        return head;
    }

    public String getColour(){
        return colour;
    }
    public int getLength(){
        return body.size();
    }

    public SnakePart getTail(){
        if(body.isEmpty()) return null;
        return body.get(body.size()-1);
    }


}

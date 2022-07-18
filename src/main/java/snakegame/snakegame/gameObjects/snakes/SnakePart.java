package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.gameObjects.Path;
import snakegame.snakegame.manager.CollisionManager;

import java.util.Random;

public class SnakePart {
    private boolean isHead;
    private Material material;
    private Location location;
    private Block block;
    private Direction currentDirection;
    private static Random random=new Random();


    public SnakePart(boolean isHead, Material material, Location location ){
        this.isHead=isHead;
        this.material=material;
        this.location=location;
        location.getBlock().setType(material);

        this.block= location.getBlock();
        currentDirection=Direction.UP;

    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }
    public Material setBlock(Location location, Material material,boolean replaced) {
        if(location==null) return null;
        if(material==null) return null;
        if(location.getWorld()==null) return null;

        Material oldMaterial=location.getBlock().getType();


        if(isHead&&handleTypes(location)){
//            System.out.println("snake head collide with! "+location.getBlock().getType());
            return oldMaterial;
        }
//        System.out.println(replaced);
        if(replaced){
            this.location.getBlock().setType(Material.AIR);
        }
        this.location=location;
        this.material=material;
        this.location.getBlock().setType(this.material);

        return oldMaterial;
    }

    public Material move(Direction relativeDirection,boolean replaced){
        Location newLocation=this.location.clone();



        Direction absoluteDirection=currentDirection;
        if(!relativeDirection.equals(Direction.FORWARD)){
            absoluteDirection=Direction.directionTable.get(currentDirection).get(relativeDirection);
            currentDirection=absoluteDirection;
            return null;
        }

        switch (absoluteDirection){
            case UP:case LEFT:case DOWN:case RIGHT:
                newLocation.add(absoluteDirection.getVelocity());
        }
        return setBlock(newLocation,this.material,replaced);

    }
    public boolean handleTypes(Location previousLocation){
        SnakeState state=CollisionManager.collideWith(previousLocation.getBlock().getType());
//        System.out.println("location: "+previousLocation);
//        System.out.println("Collide with "+previousLocation.getBlock().getType());
        return state.equals(SnakeState.DIED);
    }
    public void clear(boolean removeAll, Arena arena){
        if(!removeAll&&random.nextDouble()<0.5){
//            location.getBlock().setType(Material.GOLD_BLOCK);
            if(arena==null){
                System.out.println("This snake is not in any arena!");
                location.getBlock().setType(Material.AIR);
            }else{
                arena.spawnFood(location);
            }

        }else{
            location.getBlock().setType(Material.AIR);
        }


    }

    public Direction getCurrentDirection(){
        return currentDirection;
    }

    public static SnakePart spawnOneHead(Location location, Material type){

        return new SnakePart(true,type,location);
    }
    public static SnakePart spawnOneBody(Location location, Material type){

        return new SnakePart(false,type,location);
    }

    @Override
    public String toString() {
        return "SnakePart{pos: "+location+", type: "+material+"}";
    }
}

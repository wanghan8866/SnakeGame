package snakegame.snakegame.gameObjects.snakes;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import snakegame.snakegame.Arena.Arena;
import snakegame.snakegame.manager.CollisionManager;

import java.util.*;

public class Snake extends SnakeBase {
    protected Player owner;
    private final static Material headType=Material.DIAMOND_BLOCK;
    private final static Material bodyType=Material.PINK_STAINED_GLASS;


    public Snake(Player player, SnakePart head, String name,String colour, Arena arena){
        owner=player;
        this.head=head;
        body= new ArrayList<SnakePart>();
        body.add(head);
        dead=false;
        this.accelerating=false;
        this.name=name;
        this.arena=arena;
        this.colour=colour;
        this.speed=4.0f;


    }

    @Override
    public boolean typeCheck(Material material, Location oldLocation, boolean isAdding,boolean setDeath){

//        System.out.println("Snake: "+material);
        switch (CollisionManager.collideWith(material)){
            case DIED:if(setDeath){dead=true;owner.sendMessage(ChatColor.RED+"Your snake is dead");clear(false);}return false;
            case EATING:if(isAdding){add(SnakePart.spawnOneBody(oldLocation,bodyType));}return true;
        }
        return true;
    }

    @Override
    public void setAccelerating(boolean accelerating) {
        owner.sendMessage(ChatColor.GREEN+"The acceleration is: "+ accelerating);
        super.setAccelerating(accelerating);
    }


    public void add() {
        super.add(SnakePart.spawnOneBody(
                head.getLocation().clone().add(head.getCurrentDirection().getVelocity().clone().multiply(-1)),
                bodyType));
    }

    public static Snake createSnake(Player player, Location head_location,Arena arena){
        return new Snake(player,SnakePart.spawnOneHead(head_location,headType),"PlayerSnake",ChatColor.BLUE.toString(),arena);
    }

}

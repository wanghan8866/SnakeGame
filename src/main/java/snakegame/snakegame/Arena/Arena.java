package snakegame.snakegame.Arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import snakegame.snakegame.gameObjects.Node;
import snakegame.snakegame.gameObjects.snakes.AdvancedAISnake;
import snakegame.snakegame.gameObjects.snakes.Snake;
import snakegame.snakegame.gameObjects.snakes.SnakeBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Arena {
    private Location anchor;
    private int width;
    private int height;
    private Material ground1;
    private Material ground2;
    private Material wall;
    private List<List<Node>>grid;
    private List<AdvancedAISnake> snakes=new ArrayList<>();
//    private List<List<Location>> arenaMap;
//    private List<List<Material>> oldMap;


    public Arena(Location anchor, int width, int height, Material ground1, Material ground2, Material wall) {
        this.anchor = anchor;
        this.width = width;
        this.height = height;
        this.ground1 = ground1;
        this.ground2 = ground2;
        this.wall = wall;
        grid=new ArrayList<>();
    }
    public void build(){
        Material material=Material.AIR;
        System.out.println(anchor.getBlockX());
        System.out.println(anchor.getX());
        System.out.println(anchor.getBlockX());

        Location location=anchor.clone();
        for(int i = anchor.getBlockX(); i<anchor.getBlockX()+height; i++){
            ArrayList<Node>row=new ArrayList<>();
            for(int j=anchor.getBlockZ();j<anchor.getBlockZ()+width;j++){

                location.setX(i);
                location.setZ(j);

                if(i==(int) anchor.getBlockX()||i==(int)anchor.getBlockX()+height-1){
                    material=wall;
                }else if(j==(int)anchor.getBlockZ()||j==(int)anchor.getBlockZ()+width-1){
                    material=wall;

                }else{
                    if((i %2) ==0){
                        if((j%2)==0){
                            material=ground1;
                        }else{
                            material=ground2;
                        }
                    }else{
                        if((j%2)==0){
                            material=ground2;
                        }else{
                            material=ground1;
                        }
                    }
                }

                if(material.equals(wall)){
                    new Location(location.getWorld(),location.getX(),location.getY()+1,location.getZ()).getBlock().setType(wall);
                }else{
                    row.add(Node.createBaseNode(location.clone().add(0,1,0)));
                }


                location.getBlock().setType(material);




            }
            if(!row.isEmpty()){
                grid.add(row);
            }


        }
    }

    public void clear(){
        Material material=Material.AIR;
        Location location=anchor.clone();
        for(int i = (int) anchor.getBlockX(); i<(int)anchor.getBlockX()+height; i++){
            for(int j=(int)anchor.getBlockZ();j<(int)anchor.getBlockZ()+width;j++){

                location.setX(i);
                location.setZ(j);

                if(i==(int) anchor.getBlockX()||i==(int)anchor.getBlockX()+height-1){
                    material=wall;
                }else if(j==(int)anchor.getBlockZ()||j==(int)anchor.getBlockZ()+width-1){
                    material=wall;

                }

                if(material.equals(wall)){
                    for(int y=anchor.getBlockY();y<anchor.getBlockY()+10;y++){
                        new Location(location.getWorld(),location.getX(),y,location.getZ()).getBlock().setType(Material.AIR);
                    }

                }


            }

        }
    }

    public void reset(){
        snakes.clear();
        for(List<Node> row:grid){
            for(Node node:row){
                node.getLocation().getBlock().setType(Material.AIR);
                Location location=node.getLocation();
                for(int y=location.getBlockY();y<location.getBlockY()+5;y++){
                    new Location(location.getWorld(),location.getX(),y,location.getZ()).getBlock().setType(Material.AIR);
                }
            }
        }
    }


    public void setArenaSize(ArenaSize arenaSize){
        this.width= arenaSize.getW();
        this.height=arenaSize.getH();
    }
    public Material getWall() {
        return wall;
    }


    public Location getAnchor() {
        return anchor;
    }

    private Location getRandomLocation(){
        Random random=new Random();
        int x=anchor.getBlockX()+random.nextInt((int) (height)-1)+1;
        int z=anchor.getBlockZ()+random.nextInt((int) (width)-1)+1;

        Location location=this.anchor.clone();
        location.setX(x);
        location.setZ(z);
        return location;
    }

    public Location getRandomEmptyLocation(){
        Location location=null;
        int i=0;
        while (i<100){
            location=getRandomLocation();
            location.add(0,1,0);

            if(location.getBlock().getType().equals(Material.AIR)){
                return location;
            }
            i++;
        }

        return location;
    }

    public Location spawnFoodRandomly(){
        Location emptyLocation=getRandomEmptyLocation();
        if(emptyLocation!=null){
//            emptyLocation.getBlock().setType(Material.GOLD_BLOCK);
            spawnFood(emptyLocation);
        }
        return emptyLocation;
    }

    public void spawnFood(Location location){
        if(location!=null){
            location.getBlock().setType(Material.GOLD_BLOCK);
            for (AdvancedAISnake snake:snakes){
                if(!snake.isDead()){
                    snake.addGoal(location);
                }

            }
        }
    }
    public void addSnake(AdvancedAISnake snakeBase){
        snakes.add(snakeBase);
    }

    public static Arena createClassicArena(Location location, ArenaSize arenaSize){
        return new Arena(location, arenaSize.getW(), arenaSize.getH(), Material.WHITE_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.OBSIDIAN);
    }
    public static Arena createClassicArena(Location location){
        return new Arena(location, ArenaSize.MEDIUM.getW(), ArenaSize.MEDIUM.getH(), Material.WHITE_CONCRETE, Material.LIGHT_GRAY_CONCRETE, Material.OBSIDIAN);
    }

    public List<List<Node>>getGrid(){
        return grid;
    }

    public AdvancedAISnake getSnake(){
        if(snakes.isEmpty()) return null;
        return snakes.get(0);
    }




}

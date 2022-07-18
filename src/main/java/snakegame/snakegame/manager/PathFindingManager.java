package snakegame.snakegame.manager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.gameObjects.Node;
import snakegame.snakegame.gameObjects.Path;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class PathFindingManager {
    private static List<List<Node>> grid;
    public static void setup(List<List<Node>> grid){
        PathFindingManager.grid=grid;
    }
    public static void displayGrid(){
        if(grid.isEmpty()){
            return;
        }
        System.out.println("size: "+grid.size()+" * "+grid.get(0).size());
        for (List<Node> row:grid){
            for(Node ele:row){
                System.out.print(ele.getLocation().getBlock().getType()+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static Node getNodeFromGrid(Location location){
        Node node=null;

        int x=location.getBlockX()-grid.get(0).get(0).getLocation().getBlockX();
        int z=location.getBlockZ()-grid.get(0).get(0).getLocation().getBlockZ();

        if(x>=0&&z>=0&&x<grid.size()&&z<grid.get(0).size()){
            node=grid.get(x).get(z);
        }

        return node;
    }
    public static List<Node> getNeighbours(Node node){
        List<Node> neighbours=new ArrayList<>();
        for(Direction direction:Direction.getControlDirection()){
            if(node.getCurrentDirection()==null){
                break;
            }
            Direction dec=Direction.directionTable.get(node.getCurrentDirection()).get(direction);

            Node newNode=getNodeFromGrid(node.getLocation().clone().add(dec.getVelocity()));
            if(newNode==null){
                continue;
            }
            newNode.setFrom(direction);
            newNode.setCurrentDirection(dec);
//            newNode.setGCost(direction.getCost());
            neighbours.add(newNode);
        }
        return neighbours;
    }
    public static int getDistance(Node A, Node B){
        return Math.abs(A.getLocation().getBlockX()-B.getLocation().getBlockX())*10+Math.abs(A.getLocation().getBlockZ()-B.getLocation().getBlockZ())*10;
    }
    public static int getDistance(Location A, Location B){
        return Math.abs(A.getBlockX()-B.getBlockX())*10+Math.abs(A.getBlockZ()-B.getBlockZ())*10;
    }
    public static Path getPath(Node targetNode){
        Path path=new Path();
        Node currentNode=targetNode;
        while (currentNode!=null){
            path.add(currentNode);
            currentNode=currentNode.getParent();
        }
//        System.out.println("Path: "+ path);
//        PathManager.addPath(path);
        return path;

    }

    public static Path findPath(Location startPos, Direction currentDirection,Location targetPos){
//        System.out.println("size: "+grid.size()+" * "+grid.get(0).size());
        System.out.println("trying to find a path!");
        Path path=null;
        Node startingNode=getNodeFromGrid(startPos);
        startingNode.setFrom(null);
        startingNode.setCurrentDirection(currentDirection);
        startingNode.setParent(null);
        startingNode.setGCost(0);


        Node targetNode=getNodeFromGrid(targetPos);
        targetNode.setHCost(0);

//        System.out.println(startingNode);
//        System.out.println(targetNode);

        PriorityQueue<Node> open_queue=new PriorityQueue<Node>((a,b)->{
            if(a.getFCost()<b.getFCost()){
                return -1;
            }else if(a.getFCost()>b.getFCost()){
                return 1;
            }else{
                return Integer.compare(a.getHCost(),b.getHCost());
            }
        });
        HashSet<Node> closeSet=new HashSet<>();
        open_queue.add(startingNode);

        while (!open_queue.isEmpty()){
                Node currentNode=open_queue.poll();
                closeSet.add(currentNode);

                if(currentNode.equals(targetNode)){
                    path=getPath(targetNode);
                    return path;
                }
                for(Node neighbour:getNeighbours(currentNode)){

                    if (MaterialRegister.isBadMaterial(neighbour.getLocation().getBlock().getType()) ||
                            closeSet.contains(neighbour)
                    ) {

                        continue;
                    }
                    int newCostToNeighbour=currentNode.getGCost()+neighbour.getFrom().getCost();

                    if(newCostToNeighbour<neighbour.getGCost()||!open_queue.contains(neighbour)){
                        neighbour.setGCost(newCostToNeighbour);
                        neighbour.setHCost(getDistance(neighbour,targetNode));
                        neighbour.setParent(currentNode);
                        if(!open_queue.contains(neighbour)){
                            open_queue.add(neighbour);
                        }
                    }

                }

        }

        return path;

    }
}

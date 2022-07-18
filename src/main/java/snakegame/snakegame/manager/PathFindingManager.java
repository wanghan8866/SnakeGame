package snakegame.snakegame.manager;

import org.bukkit.Color;
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
    private static boolean found=false;
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

    public static int getEmptySlotFromTheGrid(){
        int sum=0;
        for (List<Node> row:grid){
            for(Node ele:row){
                if(!MaterialRegister.isBadMaterial(ele.getLocation().getBlock().getType())){
                    sum++;
                }
            }

        }
        return sum;
    }

    public static Node getNodeFromGrid(Location location, boolean needsNew){
        Node node=null;

        int x=location.getBlockX()-grid.get(0).get(0).getLocation().getBlockX();
        int z=location.getBlockZ()-grid.get(0).get(0).getLocation().getBlockZ();

        if(x>=0&&z>=0&&x<grid.size()&&z<grid.get(0).size()){
            node=grid.get(x).get(z);
        }
        if(node==null) return null;

//        return new Node(node);
        if(needsNew){
            return new Node(node);
        }
        return node;
    }
    public static List<Node> getNeighbours(Node node,boolean needsNew){
        List<Node> neighbours=new ArrayList<>();
        for(Direction direction:Direction.getControlDirection()){
            if(node.getCurrentDirection()==null){
                break;
            }
            Direction dec=Direction.directionTable.get(node.getCurrentDirection()).get(direction);

            Node newNode=getNodeFromGrid(node.getLocation().clone().add(dec.getVelocity()),needsNew);
            if(newNode==null){
                continue;
            }
//            System.out.println("new node before: "+newNode);
            newNode.setFrom(direction);
            newNode.setCurrentDirection(dec);
//            System.out.println("new node after: "+newNode);
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
        System.out.println("trying to find a path with A*!");
        Path path=null;
        Node startingNode=getNodeFromGrid(startPos,false);
        startingNode.setFrom(null);
        startingNode.setCurrentDirection(currentDirection);
        startingNode.setParent(null);
        startingNode.setGCost(0);


        Node targetNode=getNodeFromGrid(targetPos,false);
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
                for(Node neighbour:getNeighbours(currentNode,false)){

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


    public static Path findHamiltonianPath(Location startPos, Direction currentDirection, Location targetPos, int n){
        System.out.println("trying to find a path with HamiltonianPath!");
//        Path path=new Path();
        Node startingNode=getNodeFromGrid(startPos,false);
        startingNode.setFrom(null);
        startingNode.setCurrentDirection(currentDirection);
        startingNode.setParent(null);
        startingNode.setGCost(0);


        Node targetNode=getNodeFromGrid(targetPos,false);
        targetNode.setHCost(0);


        return findHamiltonianPaths(startingNode,targetNode,n);



    }

    public static Path findHamiltonianPaths(Node startNode,Node targetNode, int n ){
        Path path=new Path();
        path.add(startNode);
        HashSet<Node> visited=new HashSet<>();
        visited.add(startNode);
        found=false;
        ArrayList<Path> result=new ArrayList<>();
        System.out.println("starting node: "+startNode);
        hamiltonianPaths(startNode,visited,path,targetNode,Math.min(getEmptySlotFromTheGrid(),300),result);
        System.out.println("result: "+result);
        System.out.println("starting node after: "+startNode);

        if(result.isEmpty()) return null;
        return result.get(0);

    }

    public static Path hamiltonianPaths(Node startNode,HashSet<Node> visited,Path path,Node targetNode, int n, ArrayList<Path>result){
        if((path.getLast()!=null&&path.getLast().equals(targetNode))){
//            System.out.println("before "+path);
            path.reverse();

            found=true;
//            path.displayInMinecraft(Color.RED);
//            System.out.println("empty slot: "+getEmptySlotFromTheGrid());

//            System.out.println("end "+ path);
            System.out.println(path.size());
            result.add(path);
            return path;
        }
        if(n<0){
            return null;
        }

        if(!found){
            for(Node neighbour: getNeighbours(startNode,true)){
                if(!visited.contains(neighbour) &&
                        !MaterialRegister.isBadMaterial(neighbour.getLocation().getBlock().getType())){
                    visited.add(neighbour);
                    path.add(neighbour);

                    hamiltonianPaths(neighbour,visited,path,targetNode,n-1,result);
                    if(found){
                        return null;
                    }

                    visited.remove(neighbour);
                    path.remove(path.size()-1);
                }
            }
        }

        return null;


    }
}

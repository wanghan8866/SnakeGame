package snakegame.snakegame.gameObjects;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import snakegame.snakegame.Arena.MaterialRegister;
import snakegame.snakegame.manager.ParticlesManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Path {
    List<Node> nodes;
    public Path(){
        nodes=new ArrayList<>();
    }

    public Node getFirst(){
        if(nodes.isEmpty()) return null;
        return nodes.get(nodes.size()-1);
    }
    public Node getSecond(){
        if(nodes.size()<=1){
            return null;
        }
        return nodes.get(nodes.size()-2);
    }
    public Node getLast(){
        if(nodes.isEmpty()) return null;
        return nodes.get(nodes.size()-1);
    }

    public void reverse(){
        Collections.reverse(nodes);
    }
    public void add(Node node){
        nodes.add(node);
    }

    public void displayInMinecraft(){
        displayInMinecraft(Color.RED);
    }

    public void displayInMinecraft(Color color){
        if(nodes.size()<=1) return;
        for (int i=nodes.size()-2;i>=1;i--){
            Node currentNode=nodes.get(i);
//            currentNode.getLocation().getBlock().setType(Material.WHITE_STAINED_GLASS);
//            currentNode.getLocation().getBlock();

            ParticlesManager.spawnParticle(currentNode.getLocation().clone().add(0.5,0,0.5),10,color);
        }
    }
    public void clearPath(){
        if(nodes.size()>1){
            for (int i=nodes.size()-2;i>=1;i--){
                Node currentNode=nodes.get(i);
                currentNode.getLocation().getBlock().setType(Material.AIR);
            }
        }
        nodes.clear();

    }
    public int pathLength(){
        int sum=0;
        if(nodes.size()>1){
            for (int i=nodes.size()-2;i>=0;i--){
                Node currentNode=nodes.get(i);
                if(currentNode.getFrom()==null){
                    continue;
                }
                sum+=currentNode.getFrom().getCost();
            }
        }
        return sum;
    }

    public int size(){
        return nodes.size();
    }
    public void remove(int n){
        nodes.remove(n);
    }


    public boolean checkSafe(){
        for (int i=nodes.size()-1;i>=1;i--){
            Node currentNode=nodes.get(i);
            if(MaterialRegister.isBadMaterial(currentNode.getLocation().getBlock().getType())){
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder("Path: ");
        if(nodes.size()<=1) return stringBuilder.toString();
        for (int i=nodes.size()-1;i>=0;i--){
            Node currentNode=nodes.get(i);
            stringBuilder.append("(x: " + currentNode.getLocation().getX() + " z: " + currentNode.getLocation().getZ()).append(") current ").append(currentNode.getCurrentDirection()).append(" from ").append(currentNode.getFrom()).append(", ");
        }
        stringBuilder.append(" size: "+nodes.size());
        return stringBuilder.toString();
    }

    public Node pop(){
        if(nodes.isEmpty()) return null;

        Node node=nodes.get(size()-1);
        nodes.remove(size()-1);
        return node;
    }
}

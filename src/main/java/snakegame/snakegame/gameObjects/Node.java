package snakegame.snakegame.gameObjects;

import org.bukkit.Location;

import java.util.Objects;

public class Node {
    private Location location;
    private Node parent;
    private Direction from;
    private Direction currentDirection;
    private int gCost;
    private int hCost;


    public Node(Location location, Node parent, Direction from, Direction currentDirection, int gCost, int hCost) {
        this.location = location;
        this.parent = parent;
        this.from = from;
        this.currentDirection = currentDirection;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public Direction getFrom() {
        return from;
    }

    public void setFrom(Direction from) {
        this.from = from;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static Node createBaseNode(Location location){
        return new Node(location,null,null, null, Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public String toString() {
        return "Node{" +
                "location=" + location +
                ", parent=" + parent +
                ", from=" + from +
                '}';
    }


    public int getGCost() {
        return gCost;
    }

    public void setGCost(int gCost) {
        this.gCost = gCost;
    }

    public int getHCost() {
        return hCost;
    }

    public void setHCost(int hCost) {
        this.hCost = hCost;
    }
    public int getFCost(){
        return gCost+hCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return location.equals(node.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
}

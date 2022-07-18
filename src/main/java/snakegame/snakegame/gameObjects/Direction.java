package snakegame.snakegame.gameObjects;


import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    LEFT(new Vector(0,0,-1), 14),
    RIGHT(new Vector(0,0,1), 14),
    UP(new Vector(1,0,0), 10),
    DOWN(new Vector(-1,0,0), 40),
    FORWARD(new Vector(0,0,0), 10);
    private final Vector velocity;
    private Direction opposite;
    private int cost;

    static  {
        LEFT.opposite=RIGHT;
        RIGHT.opposite=LEFT;
        UP.opposite=DOWN;
        DOWN.opposite=UP;
        FORWARD.opposite=FORWARD;
    }

    public static Map<Direction,Map<Direction,Direction>>directionTable= new HashMap<Direction, Map<Direction, Direction>>() {
        {
            put(UP,new HashMap<Direction,Direction>(){{
                put(UP,UP);
                put(DOWN,DOWN);
                put(LEFT,LEFT);
                put(RIGHT,RIGHT);
            }});

            put(DOWN,new HashMap<Direction,Direction>(){{
                put(UP,DOWN);
                put(DOWN,UP);
                put(LEFT,RIGHT);
                put(RIGHT,LEFT);
            }});


            put(LEFT,new HashMap<Direction,Direction>(){{
                put(UP,LEFT);
                put(DOWN,RIGHT);
                put(LEFT,DOWN);
                put(RIGHT,UP);
            }});


            put(RIGHT,new HashMap<Direction,Direction>(){{
                put(UP,RIGHT);
                put(DOWN,LEFT);
                put(LEFT,UP);
                put(RIGHT,DOWN);
            }});

        }
    };

    Direction(Vector vec, int cost){
        this.velocity=vec;

        this.cost = cost;
    }
    public Direction getOpposite(){
        return opposite;
    }

    public Vector getVelocity() {
        return velocity;
    }
    public int getCost(){
        return cost;
    }
    public static Direction[] getControlDirection(){
        return new Direction[]{LEFT,RIGHT,UP};
    }
}

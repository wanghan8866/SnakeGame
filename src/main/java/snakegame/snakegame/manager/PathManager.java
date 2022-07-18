package snakegame.snakegame.manager;

import snakegame.snakegame.gameObjects.Path;

import java.util.ArrayList;
import java.util.List;

public class PathManager {
    private static List<Path>paths=new ArrayList<>();

    public static void addPath(Path path){
        paths.add(path);
    }
    public static void displayAllPath(){
        for(Path path:paths){
            path.displayInMinecraft();
        }
    }
    public static void clearAllPaths(){
        for(Path path:paths){
            path.clearPath();
        }
        paths.clear();
    }

}

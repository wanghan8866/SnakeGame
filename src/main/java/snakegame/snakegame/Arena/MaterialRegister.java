package snakegame.snakegame.Arena;

import org.bukkit.Material;

import java.util.HashSet;

public class MaterialRegister {
    private static HashSet<Material> badMaterials=new HashSet<>();
    private static HashSet<Material> goodMaterials=new HashSet<>();

    public static void addBadMaterial(Material material){
        badMaterials.add(material);
    }
    public static void addGoodMaterial(Material material){
        goodMaterials.add(material);
    }

    public static boolean isBadMaterial(Material material){
        return badMaterials.contains(material);
    }

    public static boolean isGoodMaterial(Material material){
        return goodMaterials.contains(material);
    }
}

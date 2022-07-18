package snakegame.snakegame.UI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Controller {



    public static void setup(Player player){
        player.getInventory().clear();

        ItemStack compass=new ItemStack(Material.RECOVERY_COMPASS);
        ItemMeta compass_meta= compass.getItemMeta();
        compass_meta.setDisplayName("Direction Control");
        compass.setItemMeta(compass_meta);

        player.getInventory().setItem(0,compass);


        ItemStack accelerator =new ItemStack(Material.CLOCK);
        ItemMeta accelerator_meta= accelerator.getItemMeta();
        accelerator_meta.setDisplayName("Accelerator");
        accelerator.setItemMeta(accelerator_meta);

        player.getInventory().setItem(1,accelerator);
    }
}

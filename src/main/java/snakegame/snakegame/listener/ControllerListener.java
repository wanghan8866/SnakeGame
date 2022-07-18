package snakegame.snakegame.listener;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import snakegame.snakegame.SnakeGameMain;
import snakegame.snakegame.gameObjects.Direction;
import snakegame.snakegame.manager.SnakeManager;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ControllerListener implements Listener {
  private SnakeGameMain snakeGameMain;
  private final long duration=300;
  private interface callback{
      void func();
  }
  private final Cache<UUID,Long> cooldown= CacheBuilder.newBuilder().expireAfterWrite(duration, TimeUnit.MILLISECONDS).build();
  public ControllerListener(SnakeGameMain snakeGameMain){
      this.snakeGameMain=snakeGameMain;
  }

  private void cooldownCall(Player player,callback c){
      if(cooldown.asMap().containsKey(player.getUniqueId())){
          long diff=cooldown.asMap().get(player.getUniqueId())-System.currentTimeMillis();
          player.sendMessage(ChatColor.RED+"You must wait "+ (double)diff/1000.0+" seconds!");
      }else{
          c.func();
          cooldown.put(player.getUniqueId(),System.currentTimeMillis()+duration);
      }
  }
  @EventHandler
  public void onController(PlayerInteractEvent e){
      Player player=e.getPlayer();
      ItemStack item=player.getInventory().getItemInMainHand();
      if(item.getType().equals(Material.AIR)) return;
      if(item.getItemMeta()==null) return;
//      System.out.println(item);
      if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if(!item.getType().equals(Material.RECOVERY_COMPASS)) return;
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase("Direction Control")){

                e.setCancelled(true);

                cooldownCall(player,()->{
                    player.sendMessage("right");
                    player.performCommand("snake move "+ Direction.RIGHT.name());
                });

            }

      }else if(e.getAction().equals(Action.LEFT_CLICK_AIR)||e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
          if(item.getType().equals(Material.RECOVERY_COMPASS)&&item.getItemMeta().getDisplayName().equalsIgnoreCase("Direction Control")){
              e.setCancelled(true);
              cooldownCall(player,()->{
                  player.sendMessage("left");
                  player.performCommand("snake move "+ Direction.LEFT.name());
              });
   ;
          }else if(item.getType().equals(Material.CLOCK)&&item.getItemMeta().getDisplayName().equalsIgnoreCase("Accelerator")){
              e.setCancelled(true);
              cooldownCall(player,()->{
                  player.sendMessage("Accelerating");
                  player.performCommand("snake accelerating");
              });


          }
      }

  }

}

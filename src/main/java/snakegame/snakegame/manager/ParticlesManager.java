package snakegame.snakegame.manager;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import snakegame.snakegame.SnakeGameMain;

public class ParticlesManager {
    private static SnakeGameMain snakeGameMain;

    public static void setup(SnakeGameMain snakeGameMain){
        ParticlesManager.snakeGameMain=snakeGameMain;
    }

    public static void spawnParticle(Location location,int time){

      spawnParticle(location,time,Color.RED);

    }

    public static void spawnParticle(Location location,int time, Color color){

        for(int i=0;i<time;i++){
            Bukkit.getScheduler().runTaskLater(snakeGameMain,()->{

                for (Player player:Bukkit.getOnlinePlayers()
                ) {
                    Particle.DustTransition dustTransition=new Particle.DustTransition(color,Color.WHITE,1.0f);
                    player.spawnParticle(Particle.DUST_COLOR_TRANSITION,location,10,dustTransition);
//                    player.spawnParticle(Particle.DRIP_LAVA,location,10,0.1,0,0.1);
                }

            },5*i);
        }

    }
}

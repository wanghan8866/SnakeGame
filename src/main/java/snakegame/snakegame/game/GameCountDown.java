package snakegame.snakegame.game;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import snakegame.snakegame.SnakeGameMain;

public class GameCountDown extends BukkitRunnable {
    private SnakeGameMain snakeGameMain;
    private int countDownSecondsBase;
    private int countDownSeconds;
    private Player player;

    public GameCountDown(SnakeGameMain snakeGameMain, int countDownSeconds, Player player) {
        this.snakeGameMain = snakeGameMain;
        this.countDownSeconds = countDownSeconds;
        countDownSecondsBase=countDownSeconds;
        this.player = player;
    }
    public void start(){
        countDownSeconds=countDownSecondsBase;
        runTaskTimer(this.snakeGameMain,0,20);
    }


    @Override
    public void run() {
        if(countDownSeconds==0){
            player.sendTitle(ChatColor.GREEN+"Game starting now","",1,20,5);
            cancel();
        }else{
            player.sendTitle(ChatColor.GREEN+"Game starting in "+countDownSeconds+
                    ((countDownSeconds>1)?" seconds":" second"),"",1,15,5);
            countDownSeconds--;
        }

    }
}

package snakegame.snakegame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import snakegame.snakegame.CommandsExecutor.SnakeCommandExecutor;
import snakegame.snakegame.CommandsExecutor.SnakeTapCompleter;
import snakegame.snakegame.listener.ControllerListener;
import snakegame.snakegame.manager.GameManager;
import snakegame.snakegame.manager.ParticlesManager;
import snakegame.snakegame.manager.SnakeManager;

public final class SnakeGameMain extends JavaPlugin {
    private SnakeManager snakeManager;
    private GameManager gameManager;

    @Override
    public void onEnable() {
        ParticlesManager.setup(this);
        snakeManager=new SnakeManager(this);
        gameManager=new GameManager(this);

        // Plugin startup logic
        getCommand("snake").setExecutor(new SnakeCommandExecutor(this));
        getCommand("snake").setTabCompleter(new SnakeTapCompleter());

        PluginManager pluginManager= Bukkit.getPluginManager();
        pluginManager.registerEvents(new ControllerListener(this),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        gameManager.endAllGame();
    }

    public SnakeManager getSnakeManager() {
        return snakeManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }
}

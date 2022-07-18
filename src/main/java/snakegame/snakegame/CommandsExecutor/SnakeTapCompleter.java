package snakegame.snakegame.CommandsExecutor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import snakegame.snakegame.Arena.ArenaSize;
import snakegame.snakegame.gameObjects.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SnakeTapCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String>results=new ArrayList<>();
        if(sender instanceof Player){
            Player player=(Player) sender;
            if(args.length==1){
                results.add("spawn_head");
                results.add("add");
                results.add("reduce");
                results.add("accelerating");
                results.add("move");
                results.add("game");
                results.add("arena");
//                results.add("clear");
            }else if(args.length==2&&args[0].equals("spawn_head")){
                results.add("x");
            }
            else if(args.length==2&&args[0].equals("add")){
                results.add("length");
            }
            else if(args.length==3&&args[0].equals("spawn_head")){
                results.add("y");
            }
            else if(args.length==4&&args[0].equals("spawn_head")){
                results.add("z");
            }else if(args.length==2&&args[0].equals("move")){
                results.addAll(Stream.of(Direction.values()).map(Enum::name).collect(Collectors.toList()));
            }else if(args.length==2&&args[0].equals("arena")){
                results.add("build");
                results.add("clear");
            }
            else if(args.length==3&&args[0].equals("arena")&&args[1].equals("build")){
                results.addAll(Stream.of(ArenaSize.values()).map(Enum::name).collect(Collectors.toList()));
            }else if(args.length==2&&args[0].equals("game")){
                results.add("start");
                results.add("init");
                results.add("end");
                results.add("resume");
                results.add("pause");
            }


        }
        return results;
    }
}

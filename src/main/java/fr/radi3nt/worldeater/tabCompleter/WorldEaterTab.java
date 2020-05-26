package fr.radi3nt.worldeater.tabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class WorldEaterTab implements TabCompleter {


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> Completer = new ArrayList<>();
        if (args.length==1) {
            Completer.add("start");
            Completer.add("stop");
            Completer.add("pause");
            Completer.add("check");
            Completer.add("param");
        }
        if (args.length==2 && args[0].equals("param")) {
            Completer.add("radius");
            Completer.add("speed");
            Completer.add("world");
            Completer.add("worldborder");
        }
        if (args.length==2 && args[0].equals("check")) {
            Completer.add("percentage");
            Completer.add("level");
            Completer.add("blocks");
            Completer.add("time");
        }
        if (args.length==3 && args[0].equals("param") && args[1].equals("worldborder")) {
            Completer.add("center");
            Completer.add("activate");
            Completer.add("deactivate");
        }


        return Completer;
    }
}

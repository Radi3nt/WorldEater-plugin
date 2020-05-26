package fr.radi3nt.worldeater.commands;

import fr.radi3nt.worldeater.MainWorldEater;
import fr.radi3nt.worldeater.timer.Eater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static fr.radi3nt.worldeater.timer.Eater.*;
import static fr.radi3nt.worldeater.timer.Eater.getPercentageOfWorldEaten;


public class WorldEaterCommand implements CommandExecutor {

    Plugin plugin = MainWorldEater.getPlugin(MainWorldEater.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix")) + ChatColor.RESET;


        Boolean Started = plugin.getConfig().getBoolean("started");
        Integer EaterRadius = plugin.getConfig().getInt("radius");
        Boolean IsWorldBorder = plugin.getConfig().getBoolean("worldborder");
        String WorldName = plugin.getConfig().getString("world-name");
        Integer Speed = plugin.getConfig().getInt("speed");




        String Start = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message-start"));
        String Stop = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message-stop"));
        String Pause = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("message-pause"));
        String Percentage = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-percentage")).replace("%percentage%", String.valueOf(getPercentageOfWorldEaten()));
        String Level = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-level")).replace("%level%", String.valueOf(getYLevel()));
        String Blocks = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-blocks")).replace("%blocks%", String.valueOf(getNumberOfBlocksEaten()));
        String SpeedM = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-speed")).replace("%speed%", String.valueOf(Speed));
        String Radius = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-radius")).replace("%radius%", String.valueOf(EaterRadius));
        String World = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-world")).replace("%world%", String.valueOf(WorldName));
        String Activate = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-activate"));
        String Deactivate = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-deactivate"));
        String Center = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-center"));
        String Error = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-error"));
        String ErrorArgs = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-args"));
        String ErrorPerm = ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("message-perm"));



        if (args.length >= 1) {
            String Args1 = args[0];
            switch (Args1) {
                case "start":
                    if (sender.hasPermission("we.start")) {
                        plugin.getConfig().set("started", true);
                        sender.sendMessage(Prefix + " " + Start);
                    } else {
                        sender.sendMessage(Prefix + " " + ErrorPerm);
                    }
                    break;

                case "pause":
                    if (sender.hasPermission("we.pause")) {
                        plugin.getConfig().set("started", false);
                        plugin.getConfig().set("block-level", getYLevel());
                        sender.sendMessage(Prefix+ " " + Pause);
                    } else {
                        sender.sendMessage(Prefix + " " + ErrorPerm);
                    }
                    break;

                case "stop":
                    if (sender.hasPermission("we.stop")) {
                        plugin.getConfig().set("started", false);
                        plugin.getConfig().set("block-level", 0);
                        BlockEaten = 0;
                        sender.sendMessage(Prefix+ " " + Stop);
                    } else {
                        sender.sendMessage(Prefix + " " + ErrorPerm);
                    }
                    break;

                case "check":
                    if (args.length >= 2) {
                        switch (args[1]) {

                            case "percentage":
                                if (sender.hasPermission("we.check.percentage")) {
                                    sender.sendMessage(Prefix + " " + Percentage);
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;

                            case "level":
                                if (sender.hasPermission("we.check.level")) {
                                    sender.sendMessage(Prefix + " " + Level);
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;

                            case "blocks":
                                if (sender.hasPermission("we.check.blocks")) {
                                    sender.sendMessage(Prefix + " " + Blocks);
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;

                            case "time":
                                if (sender.hasPermission("we.check.time")) {


                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;

                            default:
                                sender.sendMessage(Prefix + " " + ErrorArgs);
                                break;
                        }
                    } else {
                        sender.sendMessage(Prefix + " " + ErrorArgs);
                    }


                case "param":
                    if (args.length >= 2) {
                        switch (args[1]) {

                            case "radius":
                                if (sender.hasPermission("we.param.radius")) {
                                StringBuilder sb = new StringBuilder();
                                boolean found = false;
                                for(char c : args[2].toCharArray()){
                                    if(Character.isDigit(c)){
                                        sb.append(c);
                                        found = true;
                                    } else if(found){
                                        break;
                                    }
                                }
                                if (sb.toString().isEmpty()) {
                                    //error
                                } else {
                                    plugin.getConfig().set("radius", Integer.parseInt(sb.toString()));
                                    plugin.saveConfig();
                                    sender.sendMessage(Prefix+ " " + Radius);
                                }
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;

                            case "speed":
                                if (sender.hasPermission("we.param.speed")) {
                                if (args.length>=3) {
                                    StringBuilder sb1 = new StringBuilder();
                                    boolean found1 = false;
                                    for(char c : args[2].toCharArray()){
                                        if(Character.isDigit(c)){
                                            sb1.append(c);
                                            found1 = true;
                                        } else if(found1){
                                            break;
                                        }
                                    }
                                    if (sb1.toString().isEmpty()) {
                                        //error
                                    } else {
                                        plugin.getConfig().set("speed", Integer.valueOf(sb1.toString()));
                                        plugin.saveConfig();
                                        sender.sendMessage(Prefix+ " " + SpeedM);
                                    }
                                }
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;


                            case "world":
                                if (sender.hasPermission("we.param.world")) {
                                if (args.length >= 3) {
                                    plugin.getConfig().set("world-name", args[2]);
                                    plugin.saveConfig();
                                    sender.sendMessage(Prefix+ " " + World);
                                } else {
                                    //error
                                }
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                }
                                break;

                            case "worldborder":
                                if (args.length>=3) {
                                        switch (args[2]) {

                                            case "center":
                                                if (sender.hasPermission("we.param.worldborder.center")) {
                                                if (sender instanceof Player) {
                                                    Player player = (Player) sender;
                                                    World world = Bukkit.getWorld(WorldName);
                                                    WorldBorder border = world.getWorldBorder();
                                                    border.setCenter(player.getLocation());
                                                    sender.sendMessage(Prefix+ " " + Center);
                                                } else {
                                                    //error
                                                }
                                                } else {
                                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                                }
                                                break;

                                            case "activate":
                                                if (sender.hasPermission("we.param.worldborder.activate")) {
                                                plugin.getConfig().set("worldborder", true);
                                                plugin.saveConfig();
                                                World world = Bukkit.getWorld(WorldName);
                                                WorldBorder border = world.getWorldBorder();
                                                border.setCenter(plugin.getServer().getWorld(WorldName).getSpawnLocation());
                                                border.setSize(EaterRadius);
                                                sender.sendMessage(Prefix+ " " + Activate);
                                                } else {
                                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                                }
                                                break;

                                            case "deactivate":
                                                if (sender.hasPermission("we.param.worldborder.deactivate")) {
                                                plugin.getConfig().set("worldborder", false);
                                                plugin.saveConfig();
                                                sender.sendMessage(Prefix+ " " + Deactivate);
                                                } else {
                                                    sender.sendMessage(Prefix + " " + ErrorPerm);
                                                }
                                                break;

                                            default:
                                                sender.sendMessage(Prefix + " " + ErrorArgs);
                                                break;

                                        }
                                } else {
                                    sender.sendMessage(Prefix + " " + ErrorArgs);
                                }
                                break;

                            default:
                                sender.sendMessage(Prefix + " " + ErrorArgs);
                                break;

                        }
                    } else {
                        sender.sendMessage(Prefix + " " + ErrorArgs);
                    }
                    break;


                default:
                    sender.sendMessage(Prefix + " " + ErrorArgs);
                    break;

            }
        } else {
            sender.sendMessage(Prefix + " " + ErrorArgs);
        }
        return true;
    }
}

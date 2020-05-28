package fr.radi3nt.worldeater;

import fr.radi3nt.worldeater.commands.WorldEaterCommand;
import fr.radi3nt.worldeater.events.OnPlayerRespawnEvent;
import fr.radi3nt.worldeater.tabCompleter.WorldEaterTab;
import fr.radi3nt.worldeater.timer.Eater;
import fr.radi3nt.worldeater.utilis.UpdateCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainWorldEater extends JavaPlugin {

    //--------------------------------------------------//
    public static final String VERSION = "1.0";
    //--------------------------------------------------//


    //-----------------------

    private ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {




        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.YELLOW + "Starting up !");
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.YELLOW + "WorldEater Plugin by " + ChatColor.AQUA + ChatColor.BOLD + "Radi3nt");
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.YELLOW + "If you have any issues, please report it");

        RegisterEvents();
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.RED + "Registered Events");
        RegisterCommands();
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.RED + "Registered Commands");
        RegisterRunnables();
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.RED + "Registered Runnables");

        RegisterConfig();

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public void RegisterEvents() {
        getServer().getPluginManager().registerEvents(new OnPlayerRespawnEvent(), this);
    }

    public void RegisterCommands() {
        getCommand("worldeater").setExecutor(new WorldEaterCommand());
        getCommand("worldeater").setTabCompleter(new WorldEaterTab());
    }

    public void RegisterRunnables() {
        UpdateCheck updater = new UpdateCheck();
        updater.run();

        Eater eater = new Eater();
        eater.runTaskTimer(this, 1L, 1L);
    }

    public void RegisterConfig() {
        getConfig().options().copyDefaults(true);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getConfig().set("world-name", getServer().getWorlds().get(0).getName());
        saveConfig();

        getConfig().set("version", VERSION);
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.YELLOW + "Updating version ...");
        console.sendMessage(ChatColor.GOLD + "[WorldEater] " + ChatColor.YELLOW + "Config update finished");
    }


}

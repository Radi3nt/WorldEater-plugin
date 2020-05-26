package fr.radi3nt.worldeater.timer;

import fr.radi3nt.worldeater.MainWorldEater;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;

public class Eater extends BukkitRunnable {

    public static int BlockEaten = 0;
    private int interval = 0;
    private static int y= 0;
    Plugin plugin = MainWorldEater.getPlugin(MainWorldEater.class);


    @Override
    public void run() {
        Boolean Started = plugin.getConfig().getBoolean("started");
        Integer EaterRadius = plugin.getConfig().getInt("radius");
        Boolean IsWorldBorder = plugin.getConfig().getBoolean("worldborder");
        String WorldName = plugin.getConfig().getString("world-name");
        String WayToDestruct = plugin.getConfig().getString("destruct-way");
        Boolean SpawnCenter = plugin.getConfig().getBoolean("spawn-center");

        if (WorldName.equals("world") || plugin.getConfig().getString("world-name").equals("world")) {
            plugin.getConfig().set("world-name", plugin.getServer().getWorlds().get(0).getName());
            plugin.saveConfig();
            WorldName = plugin.getConfig().getString("world-name");
        }
        if (!SpawnCenter) {
            Bukkit.getServer().getWorld(WorldName).setSpawnLocation(plugin.getServer().getWorld(WorldName).getWorldBorder().getCenter().getBlockX(), plugin.getServer().getWorld(WorldName).getHighestBlockYAt(plugin.getServer().getWorld(WorldName).getWorldBorder().getCenter().getBlockX(), plugin.getServer().getWorld(WorldName).getWorldBorder().getCenter().getBlockZ()), plugin.getServer().getWorld(WorldName).getWorldBorder().getCenter().getBlockZ());
            plugin.getConfig().set("spawn-center", true);
            plugin.saveConfig();
        }

        World world = Bukkit.getWorld(WorldName);
        WorldBorder border = world.getWorldBorder();



        if (IsWorldBorder) {
            border.setSize(EaterRadius);
        } else {
            border.reset();
        }
        if (Started) {
            if (getPercentageOfWorldEaten()>=100) {
                plugin.getConfig().set("started", false);
                plugin.saveConfig();
                //message
            }
            if (WayToDestruct.equals("downToUp")) {
                Integer Speed = plugin.getConfig().getInt("speed");
                if (plugin.getConfig().getInt("block-level") != 0) {
                    y = plugin.getConfig().getInt("block-level");
                    plugin.getConfig().set("block-level", 0);
                }
                if (interval>=Speed) {
                    //Destruct
                    int ox = border.getCenter().getBlockX() - EaterRadius/2;
                    int oz = border.getCenter().getBlockZ() - EaterRadius/2;
                    for (int z = 0; z <= EaterRadius; z++) {
                        for (int x = 0; x <= EaterRadius; x++) {
                            Location BlockToDestroy = new Location(Bukkit.getWorld(WorldName), x + ox, y, z + oz);
                            BlockToDestroy.getBlock().setType(Material.AIR);
                            BlockEaten++;
                        }
                    }
                    y++;
                    interval=0;
                } else {
                    interval++;
                }
            }
        }
    }

    public static double getPercentageOfWorldEaten() {
        Plugin plugin = MainWorldEater.getPlugin(MainWorldEater.class);
        Integer EaterRadius = plugin.getConfig().getInt("radius");
        String WorldName = plugin.getConfig().getString("world-name");

        World world = Bukkit.getWorld(WorldName);
        double percentage = Double.parseDouble(String.valueOf(BlockEaten))/(Double.parseDouble(String.valueOf(EaterRadius)) *Double.parseDouble(String.valueOf(EaterRadius))*world.getMaxHeight())*100;
        String percentageS;
        percentageS = new DecimalFormat("###.##").format(percentage);
        percentage = Double.parseDouble(percentageS);
        if (percentage > 100) {
            percentage = 100;
        }

        return percentage;
    }

    public static int getNumberOfBlocksEaten() {
        return BlockEaten;
    }

    public static int getYLevel() {
        return y;
    }

    public static int getEstmatedTime() {
        Plugin plugin = MainWorldEater.getPlugin(MainWorldEater.class);
        String WorldName = plugin.getConfig().getString("world-name");
        Integer Speed = plugin.getConfig().getInt("speed");

        int maxLevel = plugin.getServer().getWorld(WorldName).getMaxHeight();
        int time = getYLevel() - (Speed * maxLevel)/4;
        return time;
    }
}

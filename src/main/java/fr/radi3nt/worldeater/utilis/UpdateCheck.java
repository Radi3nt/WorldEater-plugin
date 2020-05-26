package fr.radi3nt.worldeater.utilis;

import fr.radi3nt.worldeater.MainWorldEater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.IOUtils;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UpdateCheck {

    public static boolean upToDate = false;
    public static boolean MajorVersion = false;
    public static String PreRelease = "false";
    public static String latest = "";
    List<String> lines = new ArrayList();
    Plugin plugin = MainWorldEater.getPlugin(MainWorldEater.class);
    String Prefix = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("prefix") + ChatColor.RESET);


    ConsoleCommandSender console = Bukkit.getConsoleSender();

    public void run() {
        console.sendMessage(Prefix + " Checking for plugin updates...");
        InputStream in = null;
        try {
            in = new URL("https://raw.githubusercontent.com/Radi3nt/Fly-plugin/master/version.txt").openStream();
        } catch (MalformedURLException e) {
            console.sendMessage(Prefix + " Unable to check for updates!");
            e.printStackTrace();
        } catch (IOException e) {
            console.sendMessage(Prefix + " Unable to check for updates!");

            e.printStackTrace();
        }

        try {
            lines.clear();
            lines = IOUtils.readLines(in);
        } catch (IOException e) {
            console.sendMessage(Prefix + " Unable to determine update!");
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        latest = lines.get(0);
        PreRelease = lines.get(1);
        console.sendMessage(Prefix + " This is a pre-release: " + PreRelease);


        console.sendMessage(Prefix + " Latest version is " + latest);
        upToDate = MainWorldEater.VERSION.equals(latest);
        if (!upToDate) {
            Integer lnumber = (int) latest.charAt(2);
            Integer cnumber = (int) MainWorldEater.VERSION.charAt(2);
            if (lnumber != cnumber) {
                MajorVersion = true;
            }
        }
        if (upToDate) {
            console.sendMessage(Prefix + " Plugin is the latest version!");
        } else {
            console.sendMessage(Prefix + " Plugin is out of date! Please update");
        }
    }
}
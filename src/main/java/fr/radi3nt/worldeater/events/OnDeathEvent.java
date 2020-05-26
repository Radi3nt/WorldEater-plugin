package fr.radi3nt.worldeater.events;

import fr.radi3nt.worldeater.MainWorldEater;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class OnDeathEvent implements Listener {

    Plugin plugin = MainWorldEater.getPlugin(MainWorldEater.class);


    @EventHandler
    public void OnDeathEvent(PlayerDeathEvent e) {

        Boolean SpectatorOnDeath = plugin.getConfig().getBoolean("spectator-on-death");
        String SpectatorMessage = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("spectator-message"));
        Player player = e.getEntity();


        if (SpectatorOnDeath) {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(SpectatorMessage);
        }

    }


}

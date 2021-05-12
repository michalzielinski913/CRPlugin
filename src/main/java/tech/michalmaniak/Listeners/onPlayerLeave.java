package tech.michalmaniak.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class onPlayerLeave implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        playerStatistics.remove(event.getPlayer());

    }

}

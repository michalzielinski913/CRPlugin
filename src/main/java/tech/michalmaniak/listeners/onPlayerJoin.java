package tech.michalmaniak.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.michalmaniak.RProfile.RProfile;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class onPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){
        Player pl=joinEvent.getPlayer();
        playerStatistics.put(pl, new RProfile(pl));
    }
}

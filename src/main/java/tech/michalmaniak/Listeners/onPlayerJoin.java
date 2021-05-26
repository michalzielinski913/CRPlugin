package tech.michalmaniak.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import tech.michalmaniak.CRPlugin;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.RProfile.RProfile;

import javax.xml.crypto.Data;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class onPlayerJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent joinEvent){
        Player pl=joinEvent.getPlayer();
        if(!CRPlugin.d.checkIfPlayerExist(pl.getUniqueId())){
            CRPlugin.d.insertPlayer(pl.getUniqueId());
        }
        playerStatistics.put(pl, new RProfile(pl));
    }
}

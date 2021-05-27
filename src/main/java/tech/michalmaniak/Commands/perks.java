package tech.michalmaniak.Commands;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

public class perks implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pl=(Player)sender;
            pl.sendMessage(ChatParser.prefixColorChat("Your perks:"));
            try {
                for(Stat.SKILL sk: Stat.SKILL.values()){
                    ResultSet set=Database.getPerk(pl.getUniqueId(), sk);
                    pl.sendMessage(ChatParser.colorChat("&6"+sk.toString()+"&f: "));
                    while(set.next()){
                        pl.sendMessage(ChatParser.colorChat("&o"+set.getString("lore")));
                        pl.sendMessage(ChatParser.colorChat("Value: "+set.getInt("value")));
                        pl.sendMessage(ChatParser.colorChat("Given by: &d"+set.getString("givenBy")));
                        pl.sendMessage(ChatParser.colorChat("Valid until: "+set.getInt("validUntil")));
                        pl.sendMessage(ChatParser.colorChat("&c==========================="));
                    }

                }
                pl.sendMessage(ChatParser.colorChat("&4END OF TEST DATA STREAM!"));
            } catch (SQLException e) {
                pl.sendMessage(ChatParser.prefixColorChat("Database error!"));
            }

        }else{
            sender.sendMessage(ChatParser.prefixColorChat("Only player can run this command!"));
        }
return true;
    }
}

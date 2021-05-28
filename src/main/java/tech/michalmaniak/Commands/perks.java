package tech.michalmaniak.Commands;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;
import tech.michalmaniak.Utility.StatSearch;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

public class perks implements CommandExecutor {


    private void allStatsPerk(Player pl) throws SQLException {
        for(Stat.SKILL sk: Stat.SKILL.values()){
            pl.sendMessage(ChatParser.colorChat("&6"+sk.toString()+"&f: "));
            singleStatPerk(pl, sk);
        }
    }

    private void singleStatPerk(Player pl, Stat.SKILL sk) throws SQLException {

        ResultSet set=Database.getPerk(pl.getUniqueId(), sk);
        while(set.next()){
            pl.sendMessage(ChatParser.colorChat("&o"+set.getString("lore")));
            pl.sendMessage(ChatParser.colorChat("Value: "+set.getInt("value")));
            pl.sendMessage(ChatParser.colorChat("Given by: &d"+set.getString("givenBy")));
            pl.sendMessage(ChatParser.colorChat("Valid until: "+set.getInt("validUntil")));
            pl.sendMessage(ChatParser.colorChat("&c==========================="));
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pl=(Player)sender;
            pl.sendMessage(ChatParser.prefixColorChat("Your perks:"));
            try{
                String stat=args[0];
                Stat.SKILL sk=StatSearch.validStat(stat);
                if(sk==null){
                    pl.sendMessage(ChatParser.prefixColorChat("Invalid statistic!"));
                }else{
                    singleStatPerk(pl, sk);
                }
            }catch (ArrayIndexOutOfBoundsException | SQLException e){
                try {
                    allStatsPerk(pl);
                } catch (SQLException ex) {
                    pl.sendMessage(ChatParser.prefixColorChat(" Database error!"));
                }
            }


        }else{
            sender.sendMessage(ChatParser.prefixColorChat("Only player can run this command!"));
        }
return true;
    }
}

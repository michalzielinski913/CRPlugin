package tech.michalmaniak.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;
import tech.michalmaniak.Utility.usernameToUUID;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class skills implements CommandExecutor {

    public void self(CommandSender sender){
        if(sender instanceof Player){
            Player pl=(Player) sender;
            pl.sendMessage(ChatParser.prefixColorChat("Your skills:"));
            for (Stat.SKILL skill : Stat.SKILL.values()) {
                pl.sendMessage(ChatParser.colorChat(skill.toString()+": "+ playerStatistics.get(pl).getSkillLevel(skill)));
            }
        }else{
            sender.sendMessage(ChatParser.prefixColorChat("Only player can use this command without argument!"));
        }
    }

    public void checkPlayer(CommandSender sender, String[] args){
        UUID uuid= usernameToUUID.get(args[0]);
        if(Database.checkIfUserExist(uuid.toString())){
            sender.sendMessage(ChatParser.prefixColorChat(args[0]+" skills:"));
            try{
                ResultSet set= Database.getStats(uuid);
                for(Stat.SKILL sk: Stat.SKILL.values()){
                sender.sendMessage(ChatParser.colorChat(sk.toString()+": "+set.getInt(sk.toString())));
                }

            }catch (SQLException e){
                sender.sendMessage(ChatParser.prefixColorChat("Database error!"));
            }


        }else{
            sender.sendMessage(ChatParser.prefixColorChat("User not found!"));
        }


    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length<1){
            self(sender);
        }else if(args.length==1){
            checkPlayer(sender, args);
        }else{
            sender.sendMessage(ChatParser.prefixColorChat("Correct syntax: /skills <None/username>"));
        }


        return true;
    }
}

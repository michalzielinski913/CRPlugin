package tech.michalmaniak.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.CRPlugin;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;
import tech.michalmaniak.Utility.Exceptions.StatNotFoundException;
import tech.michalmaniak.Utility.StatSearch;
import tech.michalmaniak.Utility.usernameToUUID;

import java.util.UUID;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class skillManipulator implements CommandExecutor {

    private void error(CommandSender sender){
        sender.sendMessage(ChatParser.prefixColorChat("Wrong command format! Correct syntax:"));
        sender.sendMessage(ChatParser.colorChat("&c/skill modify michalmaniak combat 5"));

    }

    private void modify(CommandSender sender, String[] args){
        UUID uuid= usernameToUUID.get(args[1]);
        if(!Database.checkIfUserExist(uuid.toString())){
            sender.sendMessage(ChatParser.prefixColorChat("User does not exist!"));
            return;
        }
        String plUUID=new String(uuid.toString());
        Stat.SKILL sk;
        int value;
            try{
                sk= StatSearch.validStat(args[2]);
        }catch (StatNotFoundException e){
                sender.sendMessage(ChatParser.prefixColorChat(" Statistic not found!"));
                return;
            }

            value=Integer.parseInt(args[3]);

            if(value> CRPlugin.config.getInt("max-skill")){
                sender.sendMessage(ChatParser.prefixColorChat("You cant set skill higher than "+
                        CRPlugin.config.getInt("max-skill")));
                return;
            }

                Database.updateStat(plUUID, sk, value);
                if(Bukkit.getPlayer(uuid) != null){
                    playerStatistics.get(Bukkit.getPlayer(uuid)).modifyStat(sk, value);
                }

        sender.sendMessage(ChatParser.prefixColorChat("Skill level set successfully!"));
        return;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //skill modify [nick] [skill] [value]
        try{
            if(args[0].equals("modify")){
                this.modify(sender, args);
            }
        }catch (ArrayIndexOutOfBoundsException e){
            error(sender);
        }

        return true;
    }
}

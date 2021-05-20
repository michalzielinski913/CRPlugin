package tech.michalmaniak.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;

import java.util.UUID;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class skillManipulator implements CommandExecutor {

    private void error(CommandSender sender){
        sender.sendMessage(ChatParser.prefixColorChat("Wrong command format! Correct syntax:"));
        sender.sendMessage(ChatParser.colorChat("&c/skill modify michalmaniak combat 5"));

    }

    private void modify(CommandSender sender, String[] args){
        UUID uuid;
        try{
            uuid=Bukkit.getPlayer(args[1]).getUniqueId();
        }catch (NullPointerException e){
            uuid=Bukkit.getOfflinePlayer(args[1]).getUniqueId();
        }
        if(!Database.checkIfUserExist(uuid.toString())){
            sender.sendMessage(ChatParser.prefixColorChat("User does not exist!"));
            return;
        }
        String plUUID=new String(uuid.toString());
        Stat.SKILL sk = null;
        int value;
        try{
            boolean found=false;
            for(Stat.SKILL skill: Stat.SKILL.values()){
                if(skill.toString().equals(args[2])){
                    sk=skill;
                    found=true;
                    break;
                }
            }
            if(!found) throw new IllegalArgumentException();

            value=Integer.parseInt(args[3]);

            sender.sendMessage(ChatParser.prefixColorChat(plUUID+" "+sk.toString()+" "+value));

                Database.updateStat(plUUID, sk, value);
                if(Bukkit.getPlayer(uuid) != null){
                    playerStatistics.get(Bukkit.getPlayer(uuid)).modifyStat(sk, value);
                }
        }catch (IllegalArgumentException e){
            error(sender);

            return;
        }
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

package tech.michalmaniak.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;

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
        //FIX duplicate code
        UUID uuid;
        try{
            uuid= Bukkit.getPlayer(args[0]).getUniqueId();
        }catch (NullPointerException e){
            sender.sendMessage(ChatParser.prefixColorChat("Player must be online to perform this command! //TO FIX!"));
            return;
        }
        if(!Database.checkIfUserExist(uuid.toString())){
            sender.sendMessage(ChatParser.prefixColorChat("User does not exist!"));
            return;
        }
        sender.sendMessage(ChatParser.prefixColorChat(args[0]+" skills:"));
        for (Stat.SKILL skill : Stat.SKILL.values()) {
            sender.sendMessage(ChatParser.colorChat(skill.toString()+": "+ playerStatistics.get(Bukkit.getPlayer(uuid)).getSkillLevel(skill)));
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length<1){
            self(sender);
        }else{
            checkPlayer(sender, args);
        }


        return true;
    }
}

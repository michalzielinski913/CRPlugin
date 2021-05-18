package tech.michalmaniak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class skills implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pl=(Player) sender;
            pl.sendMessage(ChatParser.prefixColorChat("Your skills:"));
            for (Stat.SKILL skill : Stat.SKILL.values()) {
                pl.sendMessage(ChatParser.colorChat(skill.toString()+": "+ playerStatistics.get(pl).getSkillLevel(skill)));
            }
            return true;
        }

        return false;
    }
}

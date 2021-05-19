package tech.michalmaniak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class roll implements CommandExecutor {
    private Stat.SKILL skill;

    public roll(Stat.SKILL skill){
        this.skill=skill;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pl=(Player) sender;
            pl.sendMessage(ChatParser.prefixColorChat(skill.toString()));
            pl.sendMessage(ChatParser.colorChat("You rolled: "+playerStatistics.get(pl).getRoll(skill)));
        }
        return true;
    }
}

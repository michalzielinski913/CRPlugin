package tech.michalmaniak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;



import java.sql.ResultSet;
import java.sql.SQLException;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class roll implements CommandExecutor {
    private Stat.SKILL skill;

    public roll(Stat.SKILL skill){
        this.skill=skill;
    }

    private int getPerkSum(Player pl, Stat.SKILL skill){
        int roll=0;
        try {
            ResultSet res = Database.getPerk(pl.getUniqueId(), skill);

            while (res.next()) {
                roll += res.getInt("value");
            }
        } catch (SQLException e) {

        }
        return roll;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player pl=(Player) sender;
            pl.sendMessage(ChatParser.prefixColorChat(skill.toString()));
            int roll=playerStatistics.get(pl).getRoll(skill);

            pl.sendMessage(ChatParser.colorChat("You rolled: "+(roll+this.getPerkSum(pl, skill))));


        }
        return true;
    }
}

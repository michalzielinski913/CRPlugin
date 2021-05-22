package tech.michalmaniak.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.CRPlugin;
import tech.michalmaniak.Utility.ChatParser;

public class crp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        sender.sendMessage(ChatParser.prefixColorChat("Spreadsheet link: "+ CRPlugin.config.getString("spreadsheet")));
        return true;

    }
}

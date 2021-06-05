package tech.michalmaniak.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tech.michalmaniak.CRPlugin;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.ChatParser;
import tech.michalmaniak.Utility.usernameToUUID;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import static tech.michalmaniak.CRPlugin.playerStatistics;

public class perkManipulator implements CommandExecutor {
  //  private static HashMap<CommandSender, Integer> awaiting=new HashMap();
  private void error(CommandSender sender){
      sender.sendMessage(ChatParser.prefixColorChat("Wrong command format! Correct syntax:"));
     // sender.sendMessage(ChatParser.colorChat("&c/skill modify michalmaniak combat 5"));

  }

  public void addPerk(CommandSender sender, String[] args){
      UUID uuid= usernameToUUID.get(args[1]);
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
          StringBuffer lore=new StringBuffer();
          for(int i=4; i<args.length; i++){
              lore.append(args[i]);
              lore.append(" ");
          }
            if(sender instanceof Player){
                Database.insertPerk(uuid, sk, value, ((Player) sender).getName(), 0, lore.toString());
            }else{
                Database.insertPerk(uuid, sk, value, "CONSOLE", 0, lore.toString());
            }

      }catch (IllegalArgumentException e){
          error(sender);

          return;
      }
      sender.sendMessage(ChatParser.prefixColorChat("Skill level set successfully!"));
      return;
  }

    public void checkPerk(CommandSender sender, UUID uuid){
        sender.sendMessage(ChatParser.prefixColorChat("User perks:"));
        int iterator=1;
            try{
                ResultSet set=Database.getPerk(uuid, null);
                while(set.next()){
                    sender.sendMessage(iterator+". "+set.getString("skill")+": "+set.getInt("value")+" "+set.getString("lore"));
                    iterator++;
                }
            }catch (SQLException e){
                sender.sendMessage(ChatParser.prefixColorChat("Database Error"));
            }


    }

    private void removePerk(CommandSender sender, String[] args) {
        UUID uuid= usernameToUUID.get(args[1]);
        if(!Database.checkIfUserExist(uuid.toString())){
            sender.sendMessage(ChatParser.prefixColorChat("User does not exist!"));
            return;
        }

            Database.removePerk(uuid, Integer.valueOf(args[2]));

    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try{
            if(args[0].equals("add")){
               addPerk(sender, args);
            }else if(args[0].equals("remove")){
                removePerk(sender, args);
            }else{
                UUID uuid;
                try{
                    uuid=Bukkit.getPlayer(args[0]).getUniqueId();
                }catch (NullPointerException e){
                    uuid=Bukkit.getOfflinePlayer(args[0]).getUniqueId();
                }
                if(!Database.checkIfUserExist(uuid.toString())){
                    //TODO
                    //fix

                    sender.sendMessage(ChatParser.prefixColorChat("User does not exist!"));

                }else{
                    checkPerk(sender, uuid);
                }
            }
        }catch (ArrayIndexOutOfBoundsException e){
            error(sender);
        }

        return true;
    }


}

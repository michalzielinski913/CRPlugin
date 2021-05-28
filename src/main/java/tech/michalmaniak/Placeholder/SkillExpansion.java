package tech.michalmaniak.Placeholder;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import tech.michalmaniak.CRPlugin;
import tech.michalmaniak.DB.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SkillExpansion extends PlaceholderExpansion {
    private CRPlugin plugin;

    public SkillExpansion(CRPlugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier(){
        return "crp";
    }

    @Override
    public String getAuthor(){
        return plugin.getDescription().getAuthors().toString();

    }

    @Override
    public String getVersion(){
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        ResultSet set=null;
        try{
            set= Database.getStats(player.getUniqueId());
        } catch (SQLException e) {
            return "Database Error!";
        }

        try{
        // %crp_combat%
        if(identifier.equals("combat")){
            return Integer.toString(set.getInt("combat"));
        }

        // %crp_dodge%
        if(identifier.equals("dodge")){
            return Integer.toString(set.getInt("dodge"));
        }

        // %crp_magic%
        if(identifier.equals("magic")){
            return Integer.toString(set.getInt("magic"));
        }

        // %crp_shooting%
        if(identifier.equals("shooting")){
            return Integer.toString(set.getInt("shooting"));
        }
        // We return null if an invalid placeholder (f.e. %example_placeholder3%)
        // was provided
        }catch (SQLException e){
            return "Database Error!";
        }
        return null;
    }
}

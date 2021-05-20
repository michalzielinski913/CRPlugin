package tech.michalmaniak.RProfile;

import org.bukkit.entity.Player;
import tech.michalmaniak.DB.Database;
import tech.michalmaniak.Stats.*;

public class RProfile {

    private Combat cmb;
    private Shooting sht;
    private Dodge doge;
    private Magic mgc;


    public RProfile(Player pl) {
        //  String uuid=pl.getUniqueId();
        //download from db
        cmb = new Combat(Database.getStat(pl, Stat.SKILL.COMBAT));
        sht = new Shooting(Database.getStat(pl, Stat.SKILL.SHOOTING));
        doge = new Dodge(Database.getStat(pl, Stat.SKILL.DODGE));
        mgc = new Magic(Database.getStat(pl, Stat.SKILL.MAGIC));
    }

    public void modifyStat(Stat.SKILL type, int value) {
        switch (type) {
            case COMBAT:
                cmb.modify(value);
                break;
            case SHOOTING:
                sht.modify(value);
                break;
            case DODGE:
                doge.modify(value);
                break;
            case MAGIC:
                mgc.modify(value);
                break;
        }
    }

    public int getSkillLevel(Stat.SKILL type) {
        switch (type) {
            case COMBAT:
                 return cmb.getLevel();
            case SHOOTING:
                return sht.getLevel();
            case DODGE:
                return doge.getLevel();
            case MAGIC:
                return mgc.getLevel();
        }
        return -1;
    }

    public int getRoll(Stat.SKILL type){
        switch (type) {
            case COMBAT:
                return cmb.getRoll();
            case SHOOTING:
                return sht.getRoll();
            case DODGE:
                return doge.getRoll();
            case MAGIC:
                return mgc.getRoll();
        }
        return -1;
    }
}
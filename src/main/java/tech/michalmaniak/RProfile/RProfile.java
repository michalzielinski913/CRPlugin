package tech.michalmaniak.RProfile;

import org.bukkit.entity.Player;
import tech.michalmaniak.Stats.Combat;
import tech.michalmaniak.Stats.Dodge;
import tech.michalmaniak.Stats.Magic;
import tech.michalmaniak.Stats.Shooting;

public class RProfile {
    public enum SKILL {
        COMBAT, SHOOTING, DODGE, MAGIC;
    }
    private Combat cmb;
    private Shooting sht;
    private Dodge doge;
    private Magic mgc;


    public RProfile(Player pl){
      //  String uuid=pl.getUniqueId();
        //download from db
    }

    public void modifyCombat(SKILL type, int value){
        switch(type){
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






}

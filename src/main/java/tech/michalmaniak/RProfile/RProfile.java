package tech.michalmaniak.RProfile;

import org.bukkit.entity.Player;

public class RProfile {
    private int attack;
    private int defense;
    private int dodge;
    private int magic;
    private int shooting;


    public RProfile(Player pl){
        //download from db
    }

    public int getAttack(){
        return this.attack;
    }
    public int getDefense(){
        return this.defense;
    }
    public int getDodge(){
        return this.defense;
    }
    public int getMagic(){
        return this.magic;
    }
    public int getShooting(){
        return this.shooting;
    }


}

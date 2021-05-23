package tech.michalmaniak.DB.ORM;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import tech.michalmaniak.Stats.Stat;

@DatabaseTable(tableName = "Players")
public class Player {
    @DatabaseField(id = true)
    private String uuid;
    private int Combat;
    private int Dodge;
    private int Magic;
    private int Shooting;

    public Player(String uuid){
        this.uuid=uuid;
        Combat=0;
        Dodge=0;
        Magic=0;
        Shooting=0;

    }

    public int getCombat() {
        return Combat;
    }

    public void setCombat(int combat) {
        Combat = combat;
    }

    public int getDodge() {
        return Dodge;
    }

    public void setDodge(int dodge) {
        Dodge = dodge;
    }

    public int getMagic() {
        return Magic;
    }

    public void setMagic(int magic) {
        Magic = magic;
    }

    public int getShooting() {
        return Shooting;
    }

    public void setShooting(int shooting) {
        Shooting = shooting;
    }

    public int getSkill(Stat.SKILL sk){
        switch (sk){
            case SHOOTING:
                return getShooting();
            case MAGIC:
                return getMagic();
            case DODGE:
                return getDodge();
            case COMBAT:
                return getCombat();
            default:
                throw new IllegalStateException("Unexpected value: " + sk);
        }

    }

}
package tech.michalmaniak.Stats;

import tech.michalmaniak.CRPlugin;

public abstract class Stat {
    protected SKILL sk;

    public static enum SKILL {
        COMBAT{
            public String toString() {
                return "Combat";
            }
        }, SHOOTING{
            public String toString() {
                return "Shooting";
            }
        }, DODGE{
            public String toString() {
                return "Dodge";
            }
        }, MAGIC{
            public String toString() {
                return "Magic";
            }
        };
    }

    int min=1;
    int max=20;
    int level;
    public Stat(int value){
        this.min= CRPlugin.config.getInt("min-roll");
        this.max= CRPlugin.config.getInt("max-roll");
        this.level=value;
    }

    public int getRoll(){
        int result=min + (int)(Math.random() * ((max - min) + 1));
        if(CRPlugin.config.getBoolean("overflow")){
            return result+level;
        }else{
            return Math.min((result+level), max);
        }

    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

    public void modify(int value){
        this.level=value;
    }


    public int getLevel(){
        return level;
    }
}

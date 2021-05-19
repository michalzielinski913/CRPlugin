package tech.michalmaniak.Stats;

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

    int min=1;//getting from config
    int max=20;
    int level;
    public Stat(int value){
        this.level=value;
    }

    public int getRoll(){
        int result=min + (int)(Math.random() * ((max - min) + 1));
        return result+level;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

    public void modify(int value){
        this.level+=value;
        this.min+=value;
        this.max+=value;
    }


    public int getLevel(){
        return level;
    }
}

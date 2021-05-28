package tech.michalmaniak.Utility;

import tech.michalmaniak.Stats.Stat;

public abstract class StatSearch {

    public static Stat.SKILL validStat(String sk){
        for(Stat.SKILL s: Stat.SKILL.values()){
            if(sk.equals(s.toString())){
                return s;
            }
        }
        return null;
    }
}

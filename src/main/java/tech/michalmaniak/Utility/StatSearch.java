package tech.michalmaniak.Utility;

import tech.michalmaniak.Stats.Stat;
import tech.michalmaniak.Utility.Exceptions.StatNotFoundException;

public abstract class StatSearch {

    public static Stat.SKILL validStat(String sk) throws StatNotFoundException {
        for(Stat.SKILL s: Stat.SKILL.values()){
            if(sk.equals(s.toString())){
                return s;
            }
        }
        throw new StatNotFoundException(sk+" stat not found!");
    }
}

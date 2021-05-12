package tech.michalmaniak.Stats;

public class Stats {
    int min=1;//getting from config
    int max=20;

    public Stats(int min, int max){
        this.min+=min;
        this.max+=max;
    }

    public int getRoll(){
        int result=min + (int)(Math.random() * ((max - min) + 1));
        return result;
    }

    public int getMin(){
        return min;
    }

    public int getMax(){
        return max;
    }

    public void modify(int value){
        this.min+=value;
        this.max+=value;
    }

}

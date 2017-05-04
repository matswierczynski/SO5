package com.company;

import java.util.Stack;

/**
 * Created by Mati on 2017-05-02.
 */
public class Procesor {
    private final int nr;
    private int remainingResources;
    private Stack<Integer> averageload;
    private Stack<Double> loadInCycle;

    public Procesor (int nr){
        this.nr = nr;
        averageload = new Stack<>();
        loadInCycle = new Stack<>();
    }

    public int getNr() {
        return nr;
    }

    public int getRemainingResources() {
        return remainingResources;
    }



    public void setRemainingResources(Process process) {
        remainingResources-=process.getLoad();
        if (!averageload.empty())
        averageload.push(averageload.peek()+process.getLoad());
        else
            averageload.push(process.getLoad());
    }

    public void computeLoadInCycle(){
        double sum=0;
        double divider = averageload.size();
        if (divider ==0)
            loadInCycle.push(0.0);
        else {
            while (!averageload.empty())
                sum += (double) averageload.pop();
            loadInCycle.push(sum / divider);
        }
    }

    public double computeAverageLoad(){
        double sum=0;
        double divider = loadInCycle.size();
            while (!loadInCycle.empty()){
                double pop = loadInCycle.pop();
                if (pop == 0) divider--;
                else
                sum +=pop;
            }
        if (divider <=0 )
            return 0;

        double result = sum/divider;
        if (result>100)
            return 100;
            return sum / divider;

    }


    public void clearRemainingResources(){remainingResources=100;}

    public void clearLoadStack() {averageload.clear();}

    @Override
    public String toString() {
        return ("Nr procesora: "+nr+" Średnie obciążenie: "+computeAverageLoad());
    }
}

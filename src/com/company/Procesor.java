package com.company;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by Mati on 2017-05-02.
 */
public class Procesor {
    private final int nr;
    private int remainingResources;
    private Stack<Process> processesToHandle;
    private Stack<Integer> averageload;
    private Stack<Double> loadInCycle;

    public Procesor (int nr){
        this.nr = nr;
        averageload = new Stack<>();
        loadInCycle = new Stack<>();
        processesToHandle = new Stack<>();
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
        Stack<Double> stack = (Stack<Double>) loadInCycle.clone();
        double sum=0;
        double divider = stack.size();
            while (!stack.empty()){
                double pop = stack.pop();
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

    public double sum(Stack<Double> stack){
        Stack<Double> s = (Stack<Double>) stack.clone();
        double sum=0;
        while(!s.isEmpty())
            sum+=s.pop();
        return sum;
    }

    public double computeStandardDeviation(){
        Stack<Double> stack = (Stack<Double>) loadInCycle.clone();
        double divider = stack.size();
        if (divider>1) {
            double average = sum(stack) / divider;
            double sumOfDev = 0;
            while (!stack.isEmpty())
                sumOfDev += Math.pow((stack.pop() - average), 2);
            return Math.sqrt((1 / (divider - 1)) * sumOfDev);
        }
        else
            return 0;
    }


    public void clearRemainingResources(){remainingResources=100;}

    public void clearStacks() {
        averageload.clear();
        loadInCycle.clear();
    }

    public Stack<Process> getProcessesToHandle() {
        return processesToHandle;
    }

    public void addProcessesToHandle(Process proc) {
        if (proc!=null)
        processesToHandle.push(proc);
    }

    public Process getPeekProcess(){
        if (processesToHandle.isEmpty())
            throw new EmptyStackException();
        return processesToHandle.pop();
    }

    public void subtractRemainingResources(int toSubtract ) {
        remainingResources-=toSubtract;
    }

    public void addRemainingResources(int toAdd){
        remainingResources+=toAdd;
    }

    @Override
    public String toString() {
        return ("Nr procesora: "+nr+" Średnie obciążenie: "+computeAverageLoad()
        +"\nOdchylenie od wartości średniej: "+computeStandardDeviation()+"\n");
    }
}

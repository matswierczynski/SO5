package com.company;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Mati on 2017-05-02.
 */
public class Planner {
    private int N,p,r,z;
    Procesor [] proc;
    Stack<Process> [] arrayTasks; //tablica, której elementami są stosy procesów o różnych długościach i wymaganiach

    public Planner(int N, int p, int r, int z){
        this.N = N;
        this.p = p;
        this.r = r;
        this.z = z;
        proc = new Procesor [N];
        addProcesors();
        arrayTasks = new Stack[360]; //10 przebiegów sinusoidalnych o pełnych okresach
        addTasks();

    }

    private void addProcesors (){
        for (int i=0;i<proc.length;i++)
            proc[i] = new Procesor(i);
    }

    private void addTasks(){
        Tasks tasks = new Tasks();
        for (int i=0;i<arrayTasks.length;i++)
            arrayTasks[i] = tasks.randomTasks();
    }

    private void clearAvailableProcessorsResources(){
        for (Procesor pr : proc)
            pr.clearRemainingResources();
    }

    public void askZtimes(){
        int askedNr=0,migrNr=0;
        for (int i=0;i<arrayTasks.length;i++){
            clearAvailableProcessorsResources();
            Stack<Process> processes = (Stack<Process>) arrayTasks[i].clone();
            while (!processes.empty()){
                Process process = processes.pop();
                int procNr = new Random().nextInt(N);
                int receiver=0;
                int k=0;
                boolean readyForDispatch=false;
                while (k<z && !readyForDispatch){
                    receiver = new Random().nextInt(N);
                    askedNr++;
                    if (100-(proc [receiver].getRemainingResources())<p){
                        readyForDispatch=true;
                    }
                    k++;
                }
                if (k<z && receiver!=procNr) {
                    proc[receiver].setRemainingResources(process);
                    migrNr++;
                }
                else
                    proc[procNr].setRemainingResources(process);
            }
            for (Procesor pr : proc) {
                try {
                    pr.computeLoadInCycle();
                }
                catch (ArithmeticException ae) {ae.printStackTrace();}
            }
        }

        for (Procesor pr : proc) {
            try {
                System.out.println(pr);
            } catch (ArithmeticException ae) {
                ae.printStackTrace();
            }
        }
        System.out.println("Liczba zapytań: "+askedNr);
        System.out.println("Liczba migracji: "+migrNr);


    }

    public void askRandomAndSend(){

    }

    public void askifBelow(){

    }


}

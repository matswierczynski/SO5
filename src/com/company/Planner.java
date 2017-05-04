package com.company;

import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

/**
 * Created by Mati on 2017-05-02.
 */
public class Planner {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

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

    private void clearStacks(){
        for (Procesor pr : proc)
            pr.clearStacks();
    }

    public void askZtimes(){
        int askedNr=0,migrNr=0;
        Stack<Process> secondChance = new Stack<>();
        clearStacks();
        for (int i=0;i<arrayTasks.length;i++){
            clearAvailableProcessorsResources();
            Stack<Process> processes = (Stack<Process>) arrayTasks[i].clone();
            while (!secondChance.isEmpty())
                processes.push(secondChance.pop()); //dodanie procesów których nie przydzielono poprzednio
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
                else {
                    if ((100-proc[procNr].getRemainingResources())>99)
                        secondChance.push(process);
                    else
                    proc[procNr].setRemainingResources(process);
                }
            }
            for (Procesor pr : proc) {
                try {
                    pr.computeLoadInCycle();
                }
                catch (ArithmeticException ae) {ae.printStackTrace();}
            }
        }

        System.out.println("Użycie proceorów w pierwszej stretegii przydziału:\n");
        for (Procesor pr : proc) {
                System.out.println(pr);
            }
        System.out.println("Liczba zapytań w pierszej strategii: "+ANSI_RED+askedNr+ANSI_RESET);
        System.out.println("Liczba migracji w pierwszej strategii: "+ANSI_GREEN+migrNr+ANSI_RESET+"\n");


    }

    public void askRandomAndSend(){
        int askedNr=0,migrNr=0;
        Stack<Process> secondChance = new Stack<>();
        clearStacks();
        for (int i=0;i<arrayTasks.length;i++){
            clearAvailableProcessorsResources();
            Stack<Process> processes = (Stack<Process>) arrayTasks[i].clone();
            while (!secondChance.isEmpty())
                processes.push(secondChance.pop()); //dodanie procesów których nie przydzielono poprzednio
            while (!processes.empty()){
                Process process = processes.pop();
                int procNr = new Random().nextInt(N);
                if (100-(proc [procNr].getRemainingResources())>p) {
                    int receiver = 0;
                    int k = 0;
                    boolean readyForDispatch = false;
                    while (k < N && !readyForDispatch) {
                        receiver = new Random().nextInt(N);
                        askedNr++;
                        if (100 - (proc[receiver].getRemainingResources()) < p) {
                            readyForDispatch = true;
                        }
                        k++;
                    }
                    if (k < N) {
                        proc[receiver].setRemainingResources(process);
                        migrNr++;
                    } else
                        secondChance.push(process);
                }

                else{
                    proc[procNr].setRemainingResources(process);
                }
            }
            for (Procesor pr : proc) {
                    pr.computeLoadInCycle();
            }
        }
        System.out.println("Użycie proceorów w drugiej stretegii przydziału:\n");
        for (Procesor pr : proc) {
                System.out.println(pr);
            }
        System.out.println("Liczba zapytań w drugiej strategii przydziału: "+ANSI_RED+askedNr+ANSI_RESET);
        System.out.println("Liczba migracji w drugiej strategii przydziału: "+ANSI_GREEN+migrNr+ANSI_RESET+"\n");

    }

    public void askifBelow(){
        int askedNr=0,migrNr=0;
        Stack<Process> secondChance = new Stack<>();
        clearStacks();
        for (int i=0;i<arrayTasks.length;i++){
            clearAvailableProcessorsResources();
            Stack<Process> processes = (Stack<Process>) arrayTasks[i].clone();
            while (!secondChance.isEmpty())
                processes.push(secondChance.pop()); //dodanie procesów których nie przydzielono poprzednio
            while (!processes.empty()){
                Process process = processes.pop();
                int procNr = new Random().nextInt(N);
                if (100-(proc [procNr].getRemainingResources())>p) {
                    int receiver = 0;
                    int k = 0;
                    boolean readyForDispatch = false;
                    while (k < N && !readyForDispatch) {
                        receiver = new Random().nextInt(N);
                        askedNr++;
                        if (100 - (proc[receiver].getRemainingResources()) < p) {
                            readyForDispatch = true;
                        }
                        k++;
                    }
                    if (k < N) {
                        proc[receiver].setRemainingResources(process);
                        proc[receiver].addProcessesToHandle(process);
                        migrNr++;
                    } else
                        secondChance.push(process);
                }

                else{
                    proc[procNr].setRemainingResources(process);
                    proc[procNr].addProcessesToHandle(process);
                }
                int [] askedNrMigrNr = searchForUnderloaderProcessors();
                askedNr+=askedNrMigrNr[0];
                migrNr+=askedNrMigrNr[1];
            }

            for (Procesor pr : proc) {
                pr.computeLoadInCycle();
            }
        }
        System.out.println("Użycie proceorów w trzeciej stretegii przydziału:\n");
        for (Procesor pr : proc) {
            System.out.println(pr);
        }
        System.out.println("Liczba zapytań w trzeciej strategii przydziału: "+ANSI_RED+askedNr+ANSI_RESET);
        System.out.println("Liczba migracji w trzeciej strategii przydziału: "+ANSI_GREEN+migrNr+ANSI_RESET+"\n");

    }

    private int [] searchForUnderloaderProcessors(){
        Stack<Integer> indexesOfUnderloaded = new Stack<>();
        int askedNr=0, migrNr=0;
        for(int i=0;i<proc.length;i++) {
            if ((100 - proc[i].getRemainingResources()) < r)
                indexesOfUnderloaded.push(i);
        }

        Stack<Integer> indexesOfOverloaded = new Stack<>();
        for(int i=0;i<z && indexesOfUnderloaded.size()<indexesOfUnderloaded.size();i++){
            if ((100-proc[new Random().nextInt(N)].getRemainingResources())>40)
                indexesOfOverloaded.push(i);
            askedNr++;
        }

        while(!indexesOfOverloaded.isEmpty() && !indexesOfUnderloaded.isEmpty()) {
            int indexOfOverloaded = indexesOfOverloaded.pop();
            int indexOfUnderloaded = indexesOfUnderloaded.pop();
            for (int i = 0; i < 2; i++) {
                try {
                    Process pr = proc[indexOfOverloaded].getPeekProcess();
                    proc[indexOfOverloaded].addRemainingResources(pr.getLoad());
                    proc[indexOfUnderloaded].subtractRemainingResources(pr.getLoad());
                    proc[indexOfUnderloaded].addProcessesToHandle(pr);
                } catch (EmptyStackException eSE) { }
            }
            migrNr++;
        }
        int [] askedNrMigrNr = new int[2];
        askedNrMigrNr[0] = askedNr;
        askedNrMigrNr[1] = migrNr;
        return askedNrMigrNr;
        }
    }


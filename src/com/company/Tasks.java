package com.company;

import java.util.Random;
import java.util.Stack;

/**
 * Created by Mati on 2017-05-03.
 */
public class Tasks {
    private Stack<Process> randomTasks;
    private int degArg;
    Tasks (){
        randomTasks = new Stack<>();
        degArg=0;
    }

    Stack<Process> randomTasks (){
        randomTasks.clear();
    double tasksNrDouble = Math.abs((Math.sin(degArg*Math.PI/180)))*1000; //kolejna wartość dla sinusa
        long tasksNr = Math.round(tasksNrDouble);
    degArg+=10;
    for (long i=0;i<tasksNr;i++){
        randomTasks.push(new Process((new Random().nextInt(9))+1));//generowanie żądanej liczby procesów
        //System.out.println(randomTasks.peek());
    }                                                                          //o obciążeniu 1-10%
    return randomTasks;

    }
}

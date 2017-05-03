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
    long tasksNr = Math.abs(Math.round(Math.sin(degArg*Math.PI/180))); //kolejna wartość dla sinusa
    degArg++;
    for (long i=0;i<tasksNr*10;i++){
        randomTasks.push(new Process((new Random().nextInt(9))+1)); //generowanie żądanej liczby procesów
    }                                                                          //o obciążeniu 1-10%
    return randomTasks;

    }
}

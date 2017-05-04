package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	boolean isCorrect = false;
	int N=0,p=0,r=0,z=0;
	while (!isCorrect) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj liczbę procesorów w systemie");
        if (scanner.hasNextInt()) {
            N=scanner.nextInt();
            isCorrect = true;
        }
    }
    isCorrect = false;
        while (!isCorrect) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj maksymalny próg obciążenia");
            if (scanner.hasNextInt()) {
                p=scanner.nextInt();
                isCorrect = true;
            }
        }
        isCorrect = false;
        while (!isCorrect) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj minimalny próg obciążenia ");
            if (scanner.hasNextInt()) {
                r=scanner.nextInt();
                isCorrect = true;
            }
        }
        isCorrect = false;
        while (!isCorrect) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Podaj liczbę maksymalnej ilości zapytań o zajętość");
            if (scanner.hasNextInt()) {
                z=scanner.nextInt();
                isCorrect = true;
            }
        }

        Planner planner = new Planner(N,p,r,z);
        planner.askZtimes();


    }
}

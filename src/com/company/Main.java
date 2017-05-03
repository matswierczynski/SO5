package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	boolean isCorrect = false;
	while (!isCorrect) {
        Scanner N = new Scanner(System.in);
        System.out.println("Podaj liczbę procesorów w systemie");
        if (N.hasNextInt()) {
            N.nextInt();
            isCorrect = true;
        }
    }
    isCorrect = false;
        while (!isCorrect) {
            Scanner p = new Scanner(System.in);
            System.out.println("Podaj maksymalny próg obciążenia");
            if (p.hasNextInt()) {
                p.nextInt();
                isCorrect = true;
            }
        }
        isCorrect = false;
        while (!isCorrect) {
            Scanner r = new Scanner(System.in);
            System.out.println("Podaj minimalny próg obciążenia ");
            if (r.hasNextInt()) {
                r.nextInt();
                isCorrect = true;
            }
        }
        isCorrect = false;
        while (!isCorrect) {
            Scanner z = new Scanner(System.in);
            System.out.println("Podaj liczbę maksymalnej ilości zapytań o zajętość");
            if (z.hasNextInt()) {
                z.nextInt();
                isCorrect = true;
            }
        }

    }
}

package ru.megalomaniac.tasks.calculator;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пожалуйста операцию:");
        String input = scanner.nextLine();
        scanner.close();
        System.out.println("Результат вычисления: "+Calculator.calculateFromString(input));


    }
}

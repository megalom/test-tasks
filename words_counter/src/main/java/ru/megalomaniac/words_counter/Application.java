package ru.megalomaniac.words_counter;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Application {
    public static void main(String[] args) {
        /*String line = "Мама мыла-мыла-Мыла,мыла большую 3 и 3 раза." +
                " Раму раму. Вот такая мама хорошая уборщица-уборщица";*/

        Scanner scanner = new Scanner(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        System.out.println("Введите текст:");
        String line = scanner.nextLine();
        scanner.close();
        System.out.println("Результат:");
        Stream.of(line.toLowerCase()
                .split("\\P{L}+"))
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream()
                .sorted((e1,e2)-> {
                    int result = (int)(e2.getValue()-e1.getValue());
                    if(result==0){
                        result=e1.getKey().compareTo(e2.getKey());
                    }
                    return result;
                })
                .limit(10)
                .forEach(e1-> System.out.println(e1.getKey()));

    }
}

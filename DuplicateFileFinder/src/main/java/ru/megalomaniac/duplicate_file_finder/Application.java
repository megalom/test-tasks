package ru.megalomaniac.duplicate_file_finder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Application {
    public static void main(String[] args) {

        if (args.length<1) {
            System.out.println(" add directory path to parameters.");
        }
        else
        try {
            //Path path = Paths.get("/home/megalom/duplicateTest");
            Path path = Paths.get(args[0]);
            DuplicateFileFinder duplicateFileFinder = new DuplicateFileFinder();
            Files.walkFileTree(path,duplicateFileFinder);
            duplicateFileFinder.showCounter();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

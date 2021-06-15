package ru.megalomaniac.duplicate_file_finder;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuplicateFileFinder implements FileVisitor<Path> {
    Map<String,Integer> fileHashCounter = new HashMap<>();
    Map<String, List<String>> fileNameMap = new HashMap<>();

    public DuplicateFileFinder(){

    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            String hash = getFileHash(file.toFile(),md);
            fileHashCounter.merge(hash,0,(oldValue, newValue)->oldValue+1);
            if(fileNameMap.containsKey(hash)){
                fileNameMap.get(hash).add(file.toString());
            }
            else {
                List<String> fileList = new ArrayList<>();
                fileList.add(file.toString());
                fileNameMap.put(hash,fileList);
            }


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public void showCounter(){
        fileHashCounter.forEach((key, value)-> {
            System.out.println("For file "+fileNameMap.get(key).get(0)+" got "+value+" duplicates: ");
            for(int i=1;i<fileNameMap.get(key).size();i++){
                System.out.println(fileNameMap.get(key).get(i));
            }

        });
    }


    public static String getFileHash(File file, MessageDigest md) throws IOException {
        try (
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
                DigestInputStream dis = new DigestInputStream(bis, md)
        ) {
            while (dis.read() != -1) ;
            md = dis.getMessageDigest();
        }
        // bytes to hex
        StringBuilder result = new StringBuilder();
        for (byte b : md.digest()) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}

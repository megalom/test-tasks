package ru.megalomaniac.tasks.uniqueNumber;

import java.util.Arrays;
import java.util.Random;

public class Application {

    public static void main(String[] args) {
        int[] array;
        int arrayLength=21;

        array=generateArray(arrayLength);

        System.out.println(Arrays.toString(array));

        System.out.println("Method XOR");
        long startTime = System.nanoTime();
        int uniqueNumber=getUniqueNumber1(array);
        long endTime = System.nanoTime();

        System.out.println(uniqueNumber);
        System.out.println("Total execution time: " + (endTime-startTime) + "ns");

        System.out.println("Method SORT");
        startTime = System.nanoTime();
        uniqueNumber=getUniqueNumber2(array);
        endTime = System.nanoTime();

        System.out.println(uniqueNumber);
        System.out.println("Total execution time: " + (endTime-startTime) + "ns");


    }

    public static int getUniqueNumber1(int[]array){
        int a = 0;
        for(int n : array) {
            a ^= n;
        }
        return a;

    }

    public static int getUniqueNumber2(int[]array){
        int result = -1;

        Arrays.sort(array);
        /*
        if(array[0]!=array[1])
            return array[0];
        if(array[array.length-1]!=array[array.length-2])
            return array[array.length-1];

        for(int i=1;i<array.length-1;i++)
        {
            if((array[i]!=array[i+1])&&(array[i]!=array[i-1])) {
                result=array[i];
                break;
            }
        }*/

        //System.out.println(Arrays.toString(array));
        return result;

    }

    public static int[] generateArray(int arrayLength){
        int[] tmpArray = new int[arrayLength];
        Random random=new Random();

        for(int i=0;i<arrayLength;i++){
            if(i<arrayLength/2) {
                tmpArray[i] = i;
            }
            else
                tmpArray[i]=arrayLength-(i+1);
        }
        return tmpArray;
    }

}

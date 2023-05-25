package edu.hitsz.basic;

import java.util.Random;

public class RandomNumGenerator {
    public static int getRandom(){
        Random r=new Random();
        int i=r.nextInt(100);
        return i;
    }
}
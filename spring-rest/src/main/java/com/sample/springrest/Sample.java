package com.sample.springrest;

public class Sample {


    public static void main(String args[]) {
        int x = 5;
        int y = x++; //5
        int z = ++x; //7
        int result = x + y + z;
        System.out.println(result);
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
    }

}

package com.company;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

public class Main {

    private static Unsafe UNSAFE;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            UNSAFE = (Unsafe) field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
	// write your code here
        Unsafe unsafe = Unsafe.getUnsafe();
    }
}

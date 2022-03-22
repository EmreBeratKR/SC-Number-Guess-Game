package com.company.helper;

import com.company.helper.enums.JustifyMode;

import java.util.Scanner;

public final class Console
{
    private static final Scanner inputScanner = new Scanner(System.in);


    public static int ScanInt()
    {
        int result = inputScanner.nextInt();
        inputScanner.nextLine();
        return result;
    }

    public static String ScanString()
    {
        return inputScanner.nextLine();
    }

    public static String Justify(String input, int length, JustifyMode mode)
    {
        int startLength = input.length();

        if (mode == JustifyMode.CENTER)
        {
            int left = (length-startLength) / 2;
            int right = length-startLength-left;
            return Strings.Multiply(" ", left) + input + Strings.Multiply(" ", right);
        }

        if (mode == JustifyMode.RIGHT)
        {
            return Strings.Multiply(" ", length-startLength-1) + input + " ";
        }

        if (mode == JustifyMode.LEFT)
        {
            return " " + input + Strings.Multiply(" ", length-startLength-1);
        }

        return "ERROR!";
    }
}

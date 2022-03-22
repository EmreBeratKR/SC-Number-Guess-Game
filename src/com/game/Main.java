package com.game;

import java.util.Random;
import java.util.Scanner;

public class Main
{
    private static final Random randomizer = new Random();
    private static final Scanner inputScanner = new Scanner(System.in);


    public static void main(String[] args)
    {
        var game = new Game();
        game.Start();
    }


    public static int RandomInt(int minInclusive, int maxInclusive)
    {
        return randomizer.nextInt(minInclusive, maxInclusive+1);
    }

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

    public static String ToOrdinal(int number)
    {
        if (number <= 0) return "Error!";

        String result = String.valueOf(number);

        char lastChar = result.charAt(result.length()-1);

        switch (lastChar)
        {
            case '1':
                return result + "st";
            case '2':
                return result + "nd";
            case '3':
                return result + "rd";
            default:
                return result + "th";
        }
    }

    public static String MultiplyString(String base, int times)
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < times; i++)
        {
            result.append(base);
        }

        return result.toString();
    }

    public static String Justify(String input, int length, JustifyMode mode)
    {
        int startLength = input.length();

        if (mode == JustifyMode.CENTER)
        {
            int left = (length-startLength) / 2;
            int right = length-startLength-left;
            return MultiplyString(" ", left) + input + MultiplyString(" ", right);
        }

        if (mode == JustifyMode.RIGHT)
        {
            return MultiplyString(" ", length-startLength-1) + input + " ";
        }

        if (mode == JustifyMode.LEFT)
        {
            return " " + input + MultiplyString(" ", length-startLength-1);
        }

        return "ERROR!";
    }
}

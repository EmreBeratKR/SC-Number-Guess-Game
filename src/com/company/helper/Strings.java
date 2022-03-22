package com.company.helper;

public final class Strings
{
    public static String ToOrdinal(int number)
    {
        if (number <= 0) return "Error!";

        String result = String.valueOf(number);

        char lastChar = result.charAt(result.length()-1);

        return switch (lastChar)
        {
            case '1' -> result + "st";
            case '2' -> result + "nd";
            case '3' -> result + "rd";
            default -> result + "th";
        };
    }

    public static String Multiply(String base, int times)
    {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < times; i++)
        {
            result.append(base);
        }

        return result.toString();
    }
}

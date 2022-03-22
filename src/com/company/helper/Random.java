package com.company.helper;

public final class Random
{
    private static final java.util.Random randomizer = new java.util.Random();


    public static int Range(int minInclusive, int maxInclusive)
    {
        return randomizer.nextInt(minInclusive, maxInclusive+1);
    }
}

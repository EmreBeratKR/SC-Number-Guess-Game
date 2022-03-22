package com.company.classes;

import com.company.helper.Console;
import com.company.helper.Strings;
import com.company.helper.enums.JustifyMode;

public class Player
{
    private final String name;
    private final Stats stats;

    public Player(String name)
    {
        this.name = name;
        stats = new Stats();
    }

    public void Win()
    {
        this.stats.winStrike++;
        this.stats.totalWin++;
        this.stats.loseStrike = 0;
    }

    public void Lose()
    {
        this.stats.loseStrike++;
        this.stats.totalLose++;
        this.stats.winStrike = 0;
    }

    public int Guess(int lower, int upper)
    {
        this.stats.guess++;
        System.out.printf("It's your turn %s (%d,%d)>", this.name, lower, upper);
        return Console.ScanInt();
    }

    public void ResetGuess()
    {
        this.stats.guess = 0;
    }

    public Stats GetStats()
    {
        return new Stats(this.stats);
    }

    public int GetPoint()
    {
        return this.stats.totalWin * Game.WIN_POINT - this.stats.totalLose * Game.LOSE_POINT;
    }

    public void PrintStatsBoard(int rank)
    {
        var ordinalRank = Console.Justify(Strings.ToOrdinal(rank), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);
        int nameLength = Math.min(this.name.length(), Game.STAT_BAR_LENGTH);
        var name = Console.Justify(this.name.substring(0, nameLength), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);
        var winStrikes = Console.Justify(String.valueOf(this.stats.winStrike), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);
        var loseStrikes = Console.Justify(String.valueOf(this.stats.loseStrike), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);
        var totalWins = Console.Justify(String.valueOf(this.stats.totalWin), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);
        var totalLoses = Console.Justify(String.valueOf(this.stats.totalLose), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);
        var totalPoints = Console.Justify(String.valueOf(this.GetPoint()), Game.STAT_BAR_LENGTH, JustifyMode.CENTER);

        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|%n", ordinalRank, name, winStrikes, loseStrikes, totalWins, totalLoses, totalPoints);
        System.out.println(Strings.Multiply("-", Game.GetBoardLength()));
    }

    @Override
    public String toString()
    {
        return this.name;
    }




    public static class Stats
    {
        public int guess;
        public int winStrike;
        public int loseStrike;
        public int totalWin;
        public int totalLose;

        public Stats()
        {
            Reset();
        }

        public Stats(Stats stats)
        {
            this.guess = stats.guess;
            this.winStrike = stats.winStrike;
            this.loseStrike = stats.loseStrike;
            this.totalWin = stats.totalWin;
            this.totalLose = stats.totalLose;
        }

        public void Reset()
        {
            this.guess = 0;
            this.winStrike = 0;
            this.loseStrike = 0;
            this.totalWin = 0;
            this.totalLose = 0;
        }
    }
}

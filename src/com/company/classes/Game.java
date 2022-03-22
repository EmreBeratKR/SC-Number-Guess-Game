package com.company.classes;

import com.company.helper.Random;
import com.company.helper.Console;
import com.company.helper.Strings;
import com.company.helper.enums.JustifyMode;

import java.util.ArrayList;

public class Game
{
    public static final int STAT_BAR_LENGTH = 15;

    public static final int WIN_POINT = 5;
    public static final int LOSE_POINT = 1;

    private static final int MIN_NUMBER = 0;
    private static final int MAX_NUMBER = 100;


    private int turn;
    private ArrayList<Player> players;

    private int lowerBound;
    private int upperBound;

    private boolean assistedBoundsEnabled;


    public Game()
    {
        turn = 0;
        players = new ArrayList<>();

        lowerBound = MIN_NUMBER;
        upperBound = MAX_NUMBER;
    }

    public static int GetBoardLength()
    {
        return STAT_BAR_LENGTH * 7 + 8;
    }

    public void NextGame(Player lastWinner)
    {
        turn = 0;
        var newPlayerList = new ArrayList<Player>();

        lowerBound = MIN_NUMBER;
        upperBound = MAX_NUMBER;

        for (var player : players)
        {
            if (player != lastWinner)
            {
                player.ResetGuess();
                newPlayerList.add(player);
            }
        }

        lastWinner.ResetGuess();
        newPlayerList.add(lastWinner);

        players = newPlayerList;

        SetAssistedBounds();
    }

    public void Start()
    {
        System.out.print("How many Players >");
        int playerCount = Console.ScanInt();

        if (playerCount <= 0) return;

        for (int i = 0; i < playerCount; i++)
        {
            Join();
        }

        MainLoop();
    }


    private void Join()
    {
        var ordinalExpression = Strings.ToOrdinal(players.size()+1);
        System.out.printf("Please, Enter %s Player's name >", ordinalExpression);
        String name = Console.ScanString();
        players.add(new Player(name));
    }

    private void Leave(Player player)
    {
        players.remove(player);
    }

    private void MainLoop()
    {
        SetAssistedBounds();

        Player winner;
        while (true)
        {
            winner = Play();

            System.out.print("Do You Want to Play Again? (Y/N) >");
            String answer = Console.ScanString();

            if (answer.equalsIgnoreCase("Y"))
            {
                NextGame(winner);
                continue;
            }

            Quit();
            break;
        }
    }

    private Player Play()
    {
        final int pickedNumber = PickNumber();

        int currentGuess;
        Player currentPlayer;
        while (true)
        {
            currentPlayer = CurrentPlayer();
            currentGuess = currentPlayer.Guess(lowerBound, upperBound);

            if (currentGuess < pickedNumber)
            {
                if (assistedBoundsEnabled) lowerBound = Math.max(lowerBound, currentGuess+1);

                System.out.println("Wrong guess, please guess a bigger number!");
            }
            else if (currentGuess > pickedNumber)
            {
                if (assistedBoundsEnabled) upperBound = Math.min(upperBound, currentGuess-1);

                System.out.println("Wrong guess, please guess a smaller number!");
            }
            else
            {
                End(currentPlayer);
                return currentPlayer;
            }

            NextPlayer();
        }
    }

    private void End(Player winner)
    {
        winner.Win();
        var winnerStats = winner.GetStats();
        System.out.printf("%s Wins with %d Guess(es)!%n", winner, winnerStats.guess);

        for (var player : players)
        {
            if (player != winner) player.Lose();
        }

        PrintLeaderBoard();
    }

    private void Quit()
    {
        var sessionWinner = RankedList().get(0);

        System.out.printf("%s wins with %d Point(s)!", sessionWinner, sessionWinner.GetPoint());
    }

    private ArrayList<Player> RankedList()
    {
        ArrayList<Player> result = new ArrayList<>();

        ArrayList<Player> copy = new ArrayList<>(players);

        int best;
        Player bestPlayer;
        while (true)
        {
            best = copy.get(0).GetPoint();
            bestPlayer = copy.get(0);
            for (var player : copy)
            {
                var current = player.GetPoint();
                if (current > best)
                {
                    best = current;
                    bestPlayer = player;
                }
            }

            result.add(bestPlayer);
            copy.remove(bestPlayer);

            if (copy.size() == 0) return result;
        }
    }

    private void PrintLeaderBoard()
    {
        PrintBanner();

        var rankedList = RankedList();

        for (int i = 0; i < rankedList.size(); i++)
        {
            rankedList.get(i).PrintStatsBoard(i+1);
        }
    }

    private void PrintBanner()
    {
        int boardLength = GetBoardLength();

        System.out.println(Strings.Multiply("-", boardLength));
        System.out.println("|" + Console.Justify("LEADERS BOARD", boardLength-2, JustifyMode.CENTER) + "|");
        System.out.println(Strings.Multiply("-", boardLength));

        var ranks = Console.Justify("RANKS", STAT_BAR_LENGTH, JustifyMode.CENTER);
        var players = Console.Justify("PLAYERS", STAT_BAR_LENGTH, JustifyMode.CENTER);
        var winStrikes = Console.Justify("WIN STRIKES", STAT_BAR_LENGTH, JustifyMode.CENTER);
        var loseStrikes = Console.Justify("LOSE STRIKES", STAT_BAR_LENGTH, JustifyMode.CENTER);
        var totalWins = Console.Justify("TOTAL WINS", STAT_BAR_LENGTH, JustifyMode.CENTER);
        var totalLoses = Console.Justify("TOTAL LOSES", STAT_BAR_LENGTH, JustifyMode.CENTER);
        var totalPoints = Console.Justify("TOTAL POINTS", STAT_BAR_LENGTH, JustifyMode.CENTER);

        System.out.printf("|%s|%s|%s|%s|%s|%s|%s|%n", ranks, players, winStrikes, loseStrikes, totalWins, totalLoses, totalPoints);
        System.out.println(Strings.Multiply("-", boardLength));
    }

    private Player CurrentPlayer()
    {
        return players.get(turn);
    }

    private void NextPlayer()
    {
        turn++;
        if (turn >= players.size())
        {
            turn = 0;
        }
    }

    private int PickNumber()
    {
        return Random.Range(MIN_NUMBER, MAX_NUMBER);
    }

    private void SetAssistedBounds()
    {
        System.out.print("Do You Want to Enable Assisted Bounds? (Y/N) >");
        String answer = Console.ScanString();

        this.assistedBoundsEnabled = answer.equalsIgnoreCase("Y");

        if (this.assistedBoundsEnabled)
        {
            System.out.println("Assisted Bounds has been Enabled!");
            return;
        }

        System.out.println("Assisted Bounds has been Disabled!");
    }
}

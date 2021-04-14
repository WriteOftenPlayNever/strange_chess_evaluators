package dev.wopn.realchess;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        Board testBoard = new Board();

        testBoard.initialise();

        System.out.println(testBoard);

        Tournament tourney = new Tournament();

        System.out.println(Arrays.toString(tourney.runTournament()));

    }
}

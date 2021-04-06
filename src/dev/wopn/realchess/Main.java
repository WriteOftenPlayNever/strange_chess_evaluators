package dev.wopn.realchess;


import dev.wopn.realchess.components.CentreDistanceComponent;

public class Main {

    public static void main(String[] args) {

        Board testBoard = new Board();

        testBoard.initialise();

        System.out.println(testBoard);

        testBoard.board[1] = (byte) 0;

        testBoard.board[6] = (byte) 0;

        testBoard.board[20] = (byte) 10;

        System.out.println(testBoard);

        CentreDistanceComponent FCC = new CentreDistanceComponent((byte) 2, new int[] {}, new float[] {-30.0f});

        System.out.println(FCC.evaluate(testBoard));


    }
}

package dev.wopn.realchess;


import dev.wopn.realchess.components.DiagonalCountComponent;
import dev.wopn.realchess.components.FileCountComponent;
import dev.wopn.realchess.components.RankCountComponent;

public class Main {

    public static void main(String[] args) {

        Board testBoard = new Board();

        testBoard.initialise();

        System.out.println(testBoard);

        testBoard.board[1] = (byte) 0;

        System.out.println(testBoard);

        DiagonalCountComponent FCC = new DiagonalCountComponent((byte) 2, new int[] {}, new float[] {30.0f}, (byte) 1);

        System.out.println(FCC.evaluate(testBoard));


    }
}

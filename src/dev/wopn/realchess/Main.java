package dev.wopn.realchess;

import dev.wopn.realchess.components.*;


public class Main {

    public static void main(String[] args) {

        Board testBoard = new Board();

        testBoard.initialise();

        System.out.println(testBoard);

        testBoard.board[1] = (byte) 0;


        testBoard.board[20] = (byte) 2;

        System.out.println(testBoard);

//        FavTilesCountComponent FCC = new FavTilesCountComponent((byte) 2, new int[] {}, new float[] {-30.0f}, new int[] {6, 7});

        EnemyAdjCountComponent FCC = EnemyAdjCountComponent.generate((byte) 2);

        System.out.println(FCC.toString());

        System.out.println(FCC.evaluate(testBoard));


    }
}

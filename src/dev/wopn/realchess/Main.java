package dev.wopn.realchess;



public class Main {

    public static void main(String[] args) {

        Board testBoard = new Board();

        testBoard.initialise();

        System.out.println(testBoard);

        testBoard.playMove(testBoard.getMoves().get(11));

        testBoard.playMove(testBoard.getMoves().get(37));

        System.out.println(testBoard);

        for (Move move : testBoard.getMoves()) {
            System.out.println(move.toString());
        }


    }
}

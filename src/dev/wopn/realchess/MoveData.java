package dev.wopn.realchess;

import java.util.ArrayList;
import java.util.List;

public class MoveData {

    public static final byte[] DIRECTION_OFFSETS = {8, -8, -1, 1, 7, -7, 9, -9};

    public byte[][] knightMatrix = new byte[64][];
    public byte[][] whitePawnMoves = new byte[64][];
    public byte[][] whitePawnAttacks = new byte[64][];
    public byte[][] blackPawnMoves = new byte[64][];
    public byte[][] blackPawnAttacks = new byte[64][];
    public byte[][] squaresToEdge = new byte[64][];

    public MoveData() {
        generateKnightMatrix();
        generateEdgeData();
        generateWhitePMoves();
        generateBlackPMoves();
        generateWhitePAttacks();
        generateBlackPAttacks();
    }

    private void generateKnightMatrix() {
        int[][] movement = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

        for (int i = 0; i < 64; i++) {
            List<Byte> validMoves = new ArrayList<>();
            int[] coords = Board.coordConversion(i);
            for (int[] move : movement) {
                int x = coords[0] + move[0];
                int y = coords[1] + move[1];
                if (Board.validateCoords(x, y)) {
                    validMoves.add(Board.coordConversion(x, y));
                }
            }
            byte[] finalMoves = new byte[validMoves.size()];
            for (int j = 0; j < validMoves.size(); j++) {
                finalMoves[j] = validMoves.get(j);
            }

            knightMatrix[i] = finalMoves;
        }
    }

    private void generateEdgeData() {
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {

                byte up = (byte) (7 - rank);
                byte down = (byte) rank;
                byte left = (byte) file;
                byte right = (byte) (7 - file);

                int index = (rank * 8) + file;

                squaresToEdge[index] = new byte[]{
                        up, down, left, right,
                        (byte) Math.min(up, left),
                        (byte) Math.min(down, right),
                        (byte) Math.min(up, right),
                        (byte) Math.min(down, left),
                };

            }
        }
    }

    private void generateWhitePMoves() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                List<Byte> validMoves = new ArrayList<>();

                if (rank != 0) {
                    validMoves.add(Board.coordConversion(rank - 1, file));
                }

                if (rank == 6) {
                    validMoves.add(Board.coordConversion(rank - 2, file));
                }


                int index = (rank * 8) + file;

                byte[] finalMoves = new byte[validMoves.size()];
                for (int j = 0; j < validMoves.size(); j++) {
                    finalMoves[j] = validMoves.get(j);
                }

                whitePawnMoves[index] = finalMoves;

            }
        }
    }

    private void generateBlackPMoves() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                List<Byte> validMoves = new ArrayList<>();

                if (rank != 7) {
                    validMoves.add(Board.coordConversion(rank + 1, file));
                }

                if (rank == 1) {
                    validMoves.add(Board.coordConversion(rank + 2, file));
                }


                int index = (rank * 8) + file;

                byte[] finalMoves = new byte[validMoves.size()];
                for (int j = 0; j < validMoves.size(); j++) {
                    finalMoves[j] = validMoves.get(j);
                }

                blackPawnMoves[index] = finalMoves;

            }
        }
    }

    private void generateWhitePAttacks() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                List<Byte> validMoves = new ArrayList<>();

                int[] left = {rank - 1, file - 1};
                int[] right = {rank - 1, file + 1};

                if (Board.validateCoords(left)) {
                    validMoves.add(Board.coordConversion(left));
                }

                if (Board.validateCoords(right)) {
                    validMoves.add(Board.coordConversion(right));
                }

                int index = (rank * 8) + file;

                byte[] finalMoves = new byte[validMoves.size()];
                for (int j = 0; j < validMoves.size(); j++) {
                    finalMoves[j] = validMoves.get(j);
                }

                whitePawnAttacks[index] = finalMoves;

            }
        }
    }

    private void generateBlackPAttacks() {
        for (int rank = 0; rank < 8; rank++) {
            for (int file = 0; file < 8; file++) {
                List<Byte> validMoves = new ArrayList<>();

                int[] left = {rank + 1, file - 1};
                int[] right = {rank + 1, file + 1};

                if (Board.validateCoords(left)) {
                    validMoves.add(Board.coordConversion(left));
                }

                if (Board.validateCoords(right)) {
                    validMoves.add(Board.coordConversion(right));
                }

                int index = (rank * 8) + file;

                byte[] finalMoves = new byte[validMoves.size()];
                for (int j = 0; j < validMoves.size(); j++) {
                    finalMoves[j] = validMoves.get(j);
                }

                blackPawnAttacks[index] = finalMoves;

            }
        }
    }

}

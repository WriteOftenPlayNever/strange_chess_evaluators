package dev.wopn.realchess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public byte[] board = new byte[64];
    private MoveData mData = new MoveData();
    private int plies = 0;
    private List<Move> historyList = new ArrayList<>();

    public Board() {

    }

    public void initialise() {
        loadFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
    }


    private List<Move> getPieceMoves(byte index) {
        byte piece = board[index];
        int[] coords = coordConversion(index);
        List<Move> moves = new ArrayList<>();

        if (piece == 0) {
            return null;
        }
        else if (piece == 1) {
            for (byte targetIndex : mData.whitePawnMoves[index]) {
                byte target = board[targetIndex];
                boolean isPush = (coords[0] - coordConversion(targetIndex)[0]) == 2;

                if (target == 0) {
                    moves.add(new Move(index, targetIndex, piece, target, isPush ? (byte) 3 : (byte) 0));
                } else {
                    break;
                }
            }


            for (byte targetIndex : mData.whitePawnAttacks[index]) {
                byte target = board[targetIndex];

                if (target > 8) {
                    moves.add(new Move(index, targetIndex, piece, target, (byte) 0));
                }
            }
        }
        else if (piece == 9) {
            for (byte targetIndex : mData.blackPawnMoves[index]) {
                byte target = board[targetIndex];
                boolean isPush = (coordConversion(targetIndex)[0] - coords[0]) == 2;

                if (target == 0) {
                    moves.add(new Move(index, targetIndex, piece, target, isPush ? (byte) 3 : (byte) 0));
                } else {
                    break;
                }
            }

            for (byte targetIndex : mData.blackPawnAttacks[index]) {
                byte target = board[targetIndex];

                if ((target != 0) && (target < 8)) {
                    moves.add(new Move(index, targetIndex, piece, target, (byte) 0));
                }
            }
        }
        else if (piece == 2 || piece == 10) {
            for (byte targetIndex : mData.knightMatrix[index]) {
                byte target =  board[targetIndex];
                if (target == 0) {
                    moves.add(new Move(index, targetIndex, piece, target, (byte) 0));
                } else if (piece < 8 && target > 8) {
                    moves.add(new Move(index, targetIndex, piece, target, (byte) 0));
                } else if (piece > 8 && target < 8) {
                    moves.add(new Move(index, targetIndex, piece, target, (byte) 0));
                }
            }
        }
        else if (piece == 3 || piece == 11) {
            moves.addAll(getSlidingPieceMoves(index));
        }
        else if (piece == 4 || piece == 12) {
            moves.addAll(getSlidingPieceMoves(index));
        }
        else if (piece == 5 || piece == 13) {
            moves.addAll(getSlidingPieceMoves(index));
        }
        else if (piece == 6 || piece == 14) {
            for (int x = -1; x < 2; x++) {
                for (int y = -1; y < 2; y++) {
                    int[] target = {coords[0] + x, coords[1] + y};
                    if (validateCoords(target)) {
                        byte captured = board[coordConversion(target)];
                        if (captured == 0 || (captured < 8 == piece > 8)) {
                            moves.add(new Move(index, coordConversion(target), piece, captured, (byte) 0));
                        }
                    }
                }
            }
        }

        return moves;
    }

    private List<Move> getSlidingPieceMoves(byte index) {
        List<Move> slideMoves = new ArrayList<>();
        byte piece = board[index];

        int startDir = (piece == 3 || piece == 11) ? 4 : 0;
        int endDir = (piece == 4 || piece == 12) ? 4 : 8;

        for (int direction = startDir; direction < endDir; direction++) {
            for (int i = 0; i < mData.squaresToEdge[index][direction]; i++) {
                int targetSquare = index + (MoveData.DIRECTION_OFFSETS[direction] * (i + 1));
                byte captured = board[targetSquare];

                if (piece < 8 == captured < 8) {
                    break;
                }

                slideMoves.add(new Move(index, (byte) targetSquare, piece, captured, (byte) 0));

                if ((piece < 8 != captured < 8) && captured > 0) {
                    break;
                }
            }
        }

        return slideMoves;
    }


    public void playMove(Move move) {
        board[move.to] = move.moved;
        board[move.from] = (byte) 0;
        historyList.add(move);
        plies++;
    }

    public void unMove() {
        Move unMove = historyList.remove(historyList.size() - 1);
        board[unMove.to] = unMove.captured;
        board[unMove.from] = unMove.moved;
        plies--;
    }

    public List<Move> getMoves() {
        List<Move> moves = new ArrayList<>();

        for (byte index = 0; index < board.length; index++) {
            byte piece = board[index];
            if (piece != 0) {
                moves.addAll(getPieceMoves(index));
            }
        }

        return moves;
    }

    public int getPlies() {
        return plies;
    }

    public List<Move> getHistoryList() {
        return historyList;
    }

    public static int[] coordConversion(byte index) {
        return new int[] {index / 8, index % 8};
    }

    public static int[] coordConversion(int index) {
        return new int[] {index / 8, index % 8};
    }

    public static byte coordConversion(int rank, int file) {
        return (byte) ((rank * 8) + file);
    }

    public static byte coordConversion(int[] coords) {
        return (byte) ((coords[0] * 8) + coords[1]);
    }

    public static boolean validateCoords(int rank, int file) {
        return rank > -1 && rank < 8 && file > -1 && file < 8;
    }

    public static boolean validateCoords(int[] coords) {
        return coords[0] > -1 && coords[0] < 8 && coords[1] > -1 && coords[1] < 8;
    }

    public void loadFromFEN(String FEN) {
        String fenBoard = FEN.split(" ")[0];

        int rank = 0, file = 0;

        for (String c : fenBoard.split("")) {
            if (c.equals("/")) {
                file = 0;
                rank++;
            } else {
                if (c.matches("\\d")) {
                    file += Integer.parseInt(c);
                } else {
                    board[rank * 8 + file] = (byte) (new ArrayList<>(Arrays.asList(Piece.PIECE_CODES))).indexOf(c);
                    file++;
                }
            }
        }
    }

    public String toString() {
        StringBuilder boardString = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            boardString.append("   ").append(Piece.PIECE_CODES[board[i]]);
            if ((i + 1) % 8 == 0) {
                boardString.append("\n");
            }
        }

        return boardString.toString();
    }
}

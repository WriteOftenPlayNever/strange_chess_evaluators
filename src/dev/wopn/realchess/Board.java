package dev.wopn.realchess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    public byte[] board;
    public boolean gameOngoing;
    private MoveData mData;
    private int plies;
    private List<Move> historyList;
    private MADCollection MAD;

    public Board() {
        board = new byte[64];
        mData = new MoveData();
        plies = 0;
        historyList = new ArrayList<>();
        MAD = null;
        gameOngoing = true;
    }

    private Board(byte[] board, boolean gameOngoing, MoveData mData, int plies, List<Move> historyList) {
        this.board = board;
        this.gameOngoing = gameOngoing;
        this.mData = mData;
        this.plies = plies;
        this.historyList = historyList;
        this.MAD = new MADCollection();
        this.generateMAD();
    }

    public void initialise() {
        loadFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
        generateMAD();
    }

    public void playMove(Move move) {
        board[move.to] = move.moved;
        board[move.from] = (byte) 0;
        historyList.add(move);
        plies++;

        if (move.flag == 1) {

        } else if (move.flag == 2) {
            board[move.to] = move.moved > 8 ? (byte) 13 : (byte) 5;
        } else if (move.flag == 4) {
            board[historyList.get(historyList.size() - 2).to] = (byte) 0;
        }

        if (move.captured == 6 || move.captured == 14) {
            gameOngoing = false;
        }
        generateMAD();
    }

    public void unMove() {
        Move unMove = historyList.remove(historyList.size() - 1);
        board[unMove.to] = unMove.captured;
        board[unMove.from] = unMove.moved;
        plies--;

        if (unMove.flag == 1) {

        } else if (unMove.flag == 4) {
            Move push = historyList.get(historyList.size() - 1);
            board[push.to] = push.moved;
        }

        if (unMove.captured == 6 || unMove.captured == 14) {
            gameOngoing = true;
        }
        generateMAD();
    }

    public List<Move> validMoves() {
        List<Move> moves = new ArrayList<>();

        for (Move move : MAD.attackList) {
            if (move.moved < 8 == (plies % 2 == 0)) {
                moves.add(move);
            }
        }

        for (Move move : MAD.moveList) {
            if (move.moved < 8 == (plies % 2 == 0)) {
                moves.add(move);
            }
        }

        return moves;
    }

    public MADCollection getMAD()    {
        return MAD;
    }

    private void generateMAD() {
        MAD = new MADCollection();

        for (byte index = 0; index < board.length; index++) {
            byte piece = board[index];

            int[] coords = coordConversion(index);

            if (piece == 1) {
                for (byte targetIndex : mData.whitePawnMoves[index]) {
                    byte target = board[targetIndex];
                    boolean isPush = (coords[0] - coordConversion(targetIndex)[0]) == 2;

                    if (target == 0) {
                        if (coords[1] == 1) {
                            MAD.moveList.add(new Move(index, targetIndex, piece, target, (byte) 2));
                        } else {
                            MAD.moveList.add(new Move(index, targetIndex, piece, target, isPush ? (byte) 3 : (byte) 0));
                        }
                    } else {
                        break;
                    }
                }


                for (byte targetIndex : mData.whitePawnAttacks[index]) {
                    byte target = board[targetIndex];
                    Move move = new Move(index, targetIndex, piece, target, (byte) 0);

                    if (target > 8) {
                        if (coords[1] == 1) {
                            MAD.attackList.add(new Move(index, targetIndex, piece, target, (byte) 2));
                        } else {
                            MAD.attackList.add(move);
                        }
                    } else if (target < 8 && target > 0) {
                        MAD.defendList.add(move);
                    } else if (target == 0 && coords[0] == 3) {
                        Move lastMove = historyList.get(historyList.size() - 1);
                        boolean wasPush = lastMove.flag == 3;
                        boolean correctColumn = coordConversion(targetIndex)[1] == coordConversion(lastMove.from)[1];
                        if (wasPush && correctColumn) {
                            MAD.attackList.add(new Move(index, targetIndex, piece, target, (byte) 4));
                        }
                    }
                }
            }
            else if (piece == 9) {
                for (byte targetIndex : mData.blackPawnMoves[index]) {
                    byte target = board[targetIndex];
                    boolean isPush = (coordConversion(targetIndex)[0] - coords[0]) == 2;

                    if (target == 0) {
                        if (coords[1] == 6) {
                            MAD.moveList.add(new Move(index, targetIndex, piece, target, (byte) 2));
                        } else {
                            MAD.moveList.add(new Move(index, targetIndex, piece, target, isPush ? (byte) 3 : (byte) 0));
                        }
                    } else {
                        break;
                    }
                }

                for (byte targetIndex : mData.blackPawnAttacks[index]) {
                    byte target = board[targetIndex];
                    Move move = new Move(index, targetIndex, piece, target, (byte) 0);

                    if ((target != 0) && (target < 8)) {
                        if (coords[1] == 6) {
                            MAD.attackList.add(new Move(index, targetIndex, piece, target, (byte) 2));
                        } else {
                            MAD.attackList.add(move);
                        }
                    } else if (target > 8) {
                        MAD.defendList.add(move);
                    } else if (target == 0 && coords[0] == 4) {
                        Move lastMove = historyList.get(historyList.size() - 1);
                        boolean wasPush = lastMove.flag == 3;
                        boolean correctColumn = coordConversion(targetIndex)[1] == coordConversion(lastMove.from)[1];
                        if (wasPush && correctColumn) {
                            MAD.attackList.add(new Move(index, targetIndex, piece, target, (byte) 4));
                        }
                    }
                }
            }
            else if (piece == 2 || piece == 10) {
                for (byte targetIndex : mData.knightMatrix[index]) {
                    byte target = board[targetIndex];
                    Move move = new Move(index, targetIndex, piece, target, (byte) 0);

                    if (target == 0) {
                        MAD.moveList.add(move);
                    } else if (piece < 8 != target < 8) {
                        MAD.attackList.add(move);
                    } else {
                        MAD.defendList.add(move);
                    }
                }
            }
            else if (piece == 3 || piece == 11) {
                for (int direction = 4; direction < 8; direction++) {
                    for (int i = 0; i < mData.squaresToEdge[index][direction]; i++) {
                        int targetSquare = index + (MoveData.DIRECTION_OFFSETS[direction] * (i + 1));
                        byte captured = board[targetSquare];
                        Move move = new Move(index, (byte) targetSquare, piece, captured, (byte) 0);

                        if (captured == 0) {
                            MAD.moveList.add(move);
                        } else if (piece < 8 != captured < 8) {
                            MAD.attackList.add(move);
                            break;
                        } else {
                            MAD.defendList.add(move);
                            break;
                        }
                    }
                }
            }
            else if (piece == 4 || piece == 12) {
                for (int direction = 0; direction < 4; direction++) {
                    for (int i = 0; i < mData.squaresToEdge[index][direction]; i++) {
                        int targetSquare = index + (MoveData.DIRECTION_OFFSETS[direction] * (i + 1));
                        byte captured = board[targetSquare];
                        Move move = new Move(index, (byte) targetSquare, piece, captured, (byte) 0);

                        if (captured == 0) {
                            MAD.moveList.add(move);
                        } else if (piece < 8 != captured < 8) {
                            MAD.attackList.add(move);
                            break;
                        } else {
                            MAD.defendList.add(move);
                            break;
                        }
                    }
                }
            }
            else if (piece == 5 || piece == 13) {
                for (int direction = 0; direction < 8; direction++) {
                    for (int i = 0; i < mData.squaresToEdge[index][direction]; i++) {
                        int targetSquare = index + (MoveData.DIRECTION_OFFSETS[direction] * (i + 1));
                        byte captured = board[targetSquare];
                        Move move = new Move(index, (byte) targetSquare, piece, captured, (byte) 0);

                        if (captured == 0) {
                            MAD.moveList.add(move);
                        } else if (piece < 8 != captured < 8) {
                            MAD.attackList.add(move);
                            break;
                        } else {
                            MAD.defendList.add(move);
                            break;
                        }
                    }
                }
            }
            else if (piece == 6 || piece == 14) {
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        int[] target = {coords[0] + x, coords[1] + y};
                        if (validateCoords(target)) {
                            byte captured = board[coordConversion(target)];
                            Move move = new Move(index, coordConversion(target), piece, captured, (byte) 0);

                            if (captured == 0) {
                                MAD.moveList.add(move);
                            } else if (piece < 8 != captured < 8) {
                                MAD.attackList.add(move);
                            } else {
                                MAD.defendList.add(move);
                            }
                        }
                    }
                }
            }

        }
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

    @Override
    protected Board clone() {
        byte[] newBoard = new byte[64];
        for (int index = 0; index < board.length; index++) {
            newBoard[index] = board[index];
        }

        List<Move> moveList = new ArrayList<>(historyList);

        return new Board(newBoard, gameOngoing, new MoveData(), plies, moveList);
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

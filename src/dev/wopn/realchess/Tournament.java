package dev.wopn.realchess;

import dev.wopn.realchess.components.BasicComponent;

import java.util.*;

public class Tournament {

    private static final int playerCount = 45;
    private Player[] playerArray;
    private HashMap<Player, Float> playerScores;

    public Tournament() {
        this.playerArray = new Player[playerCount];
        this.playerScores = new HashMap<>();

        for (int i = 0; i < this.playerArray.length; i++) {
            this.playerArray[i] = new Player();
        }
    }

    public Tournament(Player starPlayer) {
        this.playerArray = new Player[playerCount];
        this.playerScores = new HashMap<>();

        playerArray[0] = starPlayer;

        for (int i = 1; i < this.playerArray.length; i++) {
            this.playerArray[i] = new Player();
        }
    }

    private String runGame(Player player1, Player player2) {
        Board gameBoard = new Board();
        gameBoard.initialise();
        int ply = 0;

        // Players are deterministic, so we give them a random starting position to work from
        Random r = new Random();
        gameBoard.playMove(gameBoard.validMoves().get(r.nextInt(20)));
        gameBoard.playMove(gameBoard.validMoves().get(r.nextInt(20)));

        // Conjure forth the oracles!
        Oracle p1oracle = new Oracle(11, player1.getComponents(), gameBoard);
        Oracle p2oracle = new Oracle(11, player2.getComponents(), gameBoard);

        for (; ply < 80 && gameBoard.gameOngoing; ply++) {
            Move chosenMove;
            Oracle oracle;

            if (ply % 2 == 0) {
                p1oracle.updateBoard(gameBoard);
                oracle = p1oracle;
            } else {
                p2oracle.updateBoard(gameBoard);
                oracle = p2oracle;
            }

            chosenMove = oracle.bestMove().second;

            gameBoard.playMove(chosenMove);
        }

        float result = new BasicComponent((byte)0,new int[]{0,0,0,0,0,0,20000},new float[]{}).evaluate(gameBoard);

        float speedFactor = ((75.0f - ply) / 100.0f);
        float player1Score = playerScores.get(player1);
        float player2Score = playerScores.get(player2);

        player1Score += result > 0 ? 1.0f + speedFactor : -speedFactor;
        player2Score += result < 0 ? 1.0f + speedFactor : -speedFactor;

        playerScores.put(player1, player1Score);
        playerScores.put(player2, player2Score);

        return "Player " + (result > 0 ? player1.getId() : player2.getId()) + " beat " +
                (result > 0 ? player2.getId() : player1.getId()) +
                " in " + ply + " moves and now scores " + (result > 0 ? player1Score : player2Score);
    }

    public static long runGameRecyledOracles(int oracles, Player player1, Player player2) {
        long date = new Date().getTime();
        Board gameBoard = new Board();
        gameBoard.initialise();
        int ply = 0;


        // Conjure forth the oracles!
        Oracle p1oracle = new Oracle(oracles, player1.getComponents(), gameBoard);
        Oracle p2oracle = new Oracle(oracles, player2.getComponents(), gameBoard);

        for (; ply < 100 && gameBoard.gameOngoing; ply++) {
            Move chosenMove;
            Oracle oracle;

            if (ply % 2 == 0) {
                p1oracle.updateBoard(gameBoard);
                oracle = p1oracle;
            } else {
                p2oracle.updateBoard(gameBoard);
                oracle = p2oracle;
            }

            chosenMove = oracle.bestMove().second;

            gameBoard.playMove(chosenMove);
        }

        return new Date().getTime() - date;
    }


    public Player[] runTournament() {
        Random r = new Random();
        playerScores = new HashMap<>();

        // Initialise scores at 0.0f
        for (Player player : this.playerArray) {
            playerScores.put(player, 0.0f);
        }

        // Make sure each player plays at least twice
        for (int playerIndex = 0; playerIndex < (playerCount * 2); playerIndex++) {
            runGame(playerArray[playerIndex % playerCount], playerArray[r.nextInt(playerCount)]);
        }

        // GET AVERAGE SCORE
        float average = 0.0f;
        for (float score : playerScores.values()) {
            average += score;
        }
        average /= playerCount;

        // Name and shame failed players
        // Also check if anyone made the leaderboards
        int bestVeterancy = -1;
        List<Integer> failedPlayers = new ArrayList<>();
        for (Player player : playerScores.keySet()) {
            player.incrementVeterancy();
            bestVeterancy = Math.max(player.getVeterancy(), bestVeterancy);

            if (playerScores.get(player) < average) {
                failedPlayers.add(player.getId());
            } else if (player.getVeterancy() > 9) {
                Leaderboard.INSTANCE.addPlayer(player);
            }
        }

        // Prune failed players
        for (int i = 0; i < playerArray.length; i++) {
            if (failedPlayers.contains(playerArray[i].getId())) {
                playerScores.remove(playerArray[i]);

                // Replace with newly hatched player
                playerArray[i] = new Player();
            }
        }

        System.out.println("Best current veterancy is: " + bestVeterancy);
        return playerArray;
    }

    public void runForever() {
        for (int i = 1; true; i++) {
            runTournament();
            Leaderboard.INSTANCE.saveLeaderboards();
            System.out.println("Tournament " + i + " complete.");
        }
    }

}

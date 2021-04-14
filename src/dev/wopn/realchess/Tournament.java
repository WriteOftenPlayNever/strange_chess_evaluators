package dev.wopn.realchess;

import dev.wopn.realchess.components.BasicComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Tournament {

    private Player[] playerArray;
    private HashMap<Player, Float> playerScores;

    public Tournament() {
        this.playerArray = new Player[50];
        this.playerScores = new HashMap<>();

        for (int i = 0; i < this.playerArray.length; i++) {
            this.playerArray[i] = new Player();
        }
    }

    private Pair<Player, Float> runGame(Player player1, Player player2) {
        Board gameBoard = new Board();
        gameBoard.initialise();
        int ply = 0;

        for (; ply < 100 && gameBoard.gameOngoing; ply++) {
            Move chosenMove;
            Oracle oracle;

            if (ply % 2 == 0) {
                oracle = new Oracle(9, player1.getComponents(), gameBoard);
            } else {
                oracle = new Oracle(9, player2.getComponents(), gameBoard);
            }

            chosenMove = oracle.bestMove().second;

            gameBoard.playMove(chosenMove);
        }

        float result = new BasicComponent((byte)0,new int[]{0,0,0,0,0,0,20000},new float[]{}).evaluate(gameBoard);

        float speedFactor = ((100.0f - ply) / 100.0f);
        float player1Score = playerScores.get(player1);
        float player2Score = playerScores.get(player2);

        player1Score += result > 0 ? 1.0f + speedFactor : -speedFactor;
        player2Score += result < 0 ? 1.0f + speedFactor : -speedFactor;

        playerScores.put(player1, player1Score);
        playerScores.put(player2, player2Score);

        return new Pair<>(player1Score > player2Score ? player1 : player2, Math.max(player1Score, player2Score));
    }


    public Player[] runTournament() {
        Random r = new Random();

        // Initialise scores at 0.0f
        for (Player player : this.playerArray) {
            playerScores.put(player, 0.0f);
        }

        // Make sure each player plays at least once
        for (int playerIndex = 0; playerIndex < 50; playerIndex++) {
            System.out.println(runGame(playerArray[playerIndex], playerArray[r.nextInt(50)]));
        }

        // Run another 169 random games
        for (int i = 0; i < 169; i++) {
            System.out.println(runGame(playerArray[r.nextInt(50)], playerArray[r.nextInt(50)]));
        }

        // GET AVERAGE SCORE
        float average = 0.0f;
        for (float score : playerScores.values()) {
            average += score;
        }
        average /= playerArray.length;

        // Name and shame failed players
        List<Integer> failedPlayers = new ArrayList<>();
        for (Player player : playerScores.keySet()) {
            if (playerScores.get(player) < average) {
                failedPlayers.add(player.getId());
            }
        }

        // Prune failed players
        for (int i = 0; i < playerArray.length; i++) {
            if (failedPlayers.contains(playerArray[i].getId())) {
                playerScores.remove(playerArray[i]);

                playerArray[i] = new Player();
            }
        }


        return playerArray;
    }

}

package dev.wopn.realchess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard {

    public static Leaderboard INSTANCE = new Leaderboard();
    private final ArrayList<LeaderboardPlayer> playerListings = new ArrayList<>();

    private Leaderboard() {
        File leaderboardFile = new File("Leaderboard.txt");

        try {
            Scanner leaderboardReader = new Scanner(leaderboardFile);

            while (leaderboardReader.hasNextLine()) {
                String line = leaderboardReader.nextLine();
                int veterancy = Integer.parseInt((line.split("veterancy=", 2)[1]).split(",")[0]);
                int id = Integer.parseInt((line.split("id=", 2)[1]).split(",")[0]);

                playerListings.add(new LeaderboardPlayer(id, veterancy, line));
            }

            playerListings.sort((o, j) -> j.veterancy - o.veterancy);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<LeaderboardPlayer> getPlayerListings() {
        return playerListings;
    }

    public void addPlayer(Player newPlayer) {
        for (LeaderboardPlayer player : playerListings) {
            if (player.id == newPlayer.getId()) {
                player.veterancy = newPlayer.getVeterancy();
                player.string = newPlayer.toString();
                return;
            }
        }
        System.out.println("New player on the leaderboards!: " + newPlayer.getId());
        playerListings.add(new LeaderboardPlayer(newPlayer.getId(), newPlayer.getVeterancy(), newPlayer.toString()));
    }

    public void addPlayer(LeaderboardPlayer newPlayer) {
        for (LeaderboardPlayer player : playerListings) {
            if (player.id == newPlayer.id) {
                player.veterancy = newPlayer.veterancy;
                return;
            }
        }
        playerListings.add(new LeaderboardPlayer(newPlayer.id, newPlayer.veterancy, newPlayer.string));
    }

    public void saveLeaderboards() {
        playerListings.sort((o, j) -> j.veterancy - o.veterancy);
        FileWriter out = null;

        try {
            out = new FileWriter("Leaderboard.txt");
            for (int i = 0; i < playerListings.size(); i++) {
                out.write(playerListings.get(i).string + ((i < (playerListings.size() - 1)) ? "\n" : ""));
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


class LeaderboardPlayer {

    public int id;
    public int veterancy;
    public String string;

    public LeaderboardPlayer(int id, int veterancy, String string) {
        this.id = id;
        this.veterancy = veterancy;
        this.string = string;
    }

}


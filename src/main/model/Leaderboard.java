package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriting;

import java.util.ArrayList;
import java.util.List;

// represents a leaderboard that stores previous high scores
public class Leaderboard implements JsonWriting {

    //private static Leaderboard theLeaderBoard;
    private List<LeaderboardEntry> leaderboard;

    // EFFECTS: creates a new leaderboard
    public Leaderboard() {
        leaderboard = new ArrayList<>();
    }

//    public static Leaderboard getInstance() {
//        if (theLeaderBoard == null) {
//            theLeaderBoard = new Leaderboard();
//        }
//
//        return theLeaderBoard;
//    }

    // getters and setters
    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    // EFFECTS: adds an entry to the leaderboard
    public void addEntry(LeaderboardEntry entry) {
        Board.Difficulty d = entry.getDifficulty();

        int index = 0;
        if (d == Board.Difficulty.INTERMEDIATE) {
            index = 1;
        } else if (d == Board.Difficulty.EXPERT) {
            index = 2;
        }

        for (int i = index * 10; i < (index + 1) * 10; i++) {
            if (entry.getTime() < leaderboard.get(i).getTime()) {
                leaderboard.remove((index + 1) * 10 - 1);
                leaderboard.add(i, entry);
                break;
            }
        }
    }

    // EFFECTS: creates a blank leaderboard
    public void createLeaderBoard() {

        for (Board.Difficulty d : Board.Difficulty.values()) {
            for (int i = 0; i < 10; i++) {
                leaderboard.add(new LeaderboardEntry(d, "null", 999));
            }
        }
    }

    @Override
    // EFFECTS: returns a JSON object that contains a player
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("leaderboard", leaderboardToJson());
        return json;
    }

    // EFFECTS: returns leaderboardEntries as a JSON array
    private JSONArray leaderboardToJson() {
        JSONArray jsonArray = new JSONArray();

        for (LeaderboardEntry entry : leaderboard) {
            jsonArray.put(entry.toJson());
        }

        return jsonArray;
    }
}

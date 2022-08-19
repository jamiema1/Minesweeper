package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonWriting;

import java.util.ArrayList;
import java.util.List;

public class Leaderboard implements JsonWriting {

    public enum Difficulty {
        beginner,
        intermediate,
        expert,
        custom
    }

    private List<LeaderboardEntry> leaderboard;

    public Leaderboard() {
        if (leaderboard == null) {
            leaderboard = new ArrayList<>();
            createLeaderBoard();
        }
    }

    public List<LeaderboardEntry> getLeaderboard() {
        return leaderboard;
    }

    public void addEntry(LeaderboardEntry entry) {
        Difficulty d = entry.getDifficulty();

        int index = 0;
        if (d == Difficulty.intermediate) {
          index = 1;
        } else if (d == Difficulty.expert) {
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

    private void createLeaderBoard() {

        for (Difficulty d : Difficulty.values()) {
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

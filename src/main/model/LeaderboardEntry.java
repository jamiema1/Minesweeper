package model;

import org.json.JSONObject;
import persistence.JsonWriting;

// represents a single entry into a leaderboard
public class LeaderboardEntry implements JsonWriting {

    private final Board.Difficulty difficulty;
    private final String name;
    private final int time;

    // EFFECTS: creates an entry
    public LeaderboardEntry(Board.Difficulty difficulty, String name, int time) {
        this.difficulty = difficulty;
        this.name = name;
        this.time = time;
    }

    // getters and setters
    public Board.Difficulty getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    @Override
    // EFFECTS: returns a JSON object that contains a leaderboardEntry
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("difficulty", difficulty);
        json.put("name", name);
        json.put("time", time);
        return json;
    }
}

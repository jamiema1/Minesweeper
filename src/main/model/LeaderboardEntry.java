package model;

import org.json.JSONObject;
import persistence.JsonWriting;

public class LeaderboardEntry implements JsonWriting {

    private Leaderboard.Difficulty difficulty;
    private String name;
    private int time;

    public LeaderboardEntry(Leaderboard.Difficulty difficulty, String name, int time) {
        this.difficulty = difficulty;
        this.name = name;
        this.time = time;
    }

    public Leaderboard.Difficulty getDifficulty() {
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

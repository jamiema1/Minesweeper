package persistence;

import org.json.JSONObject;
import model.Player;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

// represents a JSON writer that can write to a JSON file
// modelled after JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriter {

    private static final int INDENT_FACTOR = 4;
    private PrintWriter writer;
    private String destinationFile;

    // EFFECTS: creates a JsonWriter and sets its destination
    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of a player to file
    public void write(Player player) throws FileNotFoundException  {
        writer = new PrintWriter(destinationFile);
        JSONObject json = player.toJson();
        writer.print(json.toString(INDENT_FACTOR));
        writer.close();
    }
}

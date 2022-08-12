package persistence;

import org.json.JSONObject;

// represents a class that contains the method for converting objects to JSON objects
// modelled after JsonSerializationDemo: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface JsonWriting {

    // EFFECTS: converts an object to a JSON object
    JSONObject toJson();
}

package server;

import com.google.gson.Gson;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private File file;

    public Database(String fileName) {
        this.file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("{}");
            writer.close();
        } catch (IOException e) {

        }
    }

    public Map<String, String> getData() {
        Map<String, String> currentData = new HashMap<>();

        try (FileReader reader = new FileReader(file)) {
            currentData = new Gson().fromJson(reader, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentData;
    }

    public void updateDatabase(Map<String, String> currentData) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(new Gson().toJson(currentData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

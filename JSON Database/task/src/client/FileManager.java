package client;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    private static final String PATH_ABSOLUTE = "/Users/kamil/Code/Hyperskill/JSON Database/JSON Database/task/src/client/data/";


    public static Message messageFromFile(String fileName) {
        Message msg = null;
        String path = PATH_ABSOLUTE + fileName;
        try (FileReader reader = new FileReader(path)) {
            msg = new Gson().fromJson(reader, Message.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }
}

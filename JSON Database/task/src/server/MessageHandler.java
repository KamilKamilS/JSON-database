package server;

import java.util.Arrays;

public class MessageHandler {

    Database database;
    String type;
    int index;
    String content;

    public MessageHandler(Database database) {
        this.database = database;
    }

    public String process(String msg) {
        String[] msgArray = msg.split("\\s+");
        type = msgArray[0];
        if (type.equals("exit")) {
            return "EXIT";
        }

        try {
            index = Integer.parseInt(msgArray[1]);
        } catch (Exception e) {
            return "ERROR";
        }


        if (index < 1 || index > database.array.length) {
            return "ERROR";
        } else {
            index = index - 1;
        }

        switch (type) {
            case "get":
                return database.getData(index).equals("") ? "ERROR" : database.getData(index);
            case "set":
                content = String.join(" ", Arrays.copyOfRange(msgArray, 2, msgArray.length));
                database.setData(index, content);
                return "OK";
            case "delete":
                database.deleteData(index);
                return "OK";
            default:
                return "ERROR";
        }
    }

}
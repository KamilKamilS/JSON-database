package server;

import client.Message;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static Map<String, String> database = new HashMap<>();
    static Gson gson = new Gson();

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        String query;



        try (
                ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))
        ) {
            System.out.println("Server started!");
            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
                    query = input.readUTF();

                    Message msg = gson.fromJson(query, Message.class);

                    Message messageFromServer = process(msg);
                    output.writeUTF(gson.toJson(messageFromServer));

                    if (msg.getType().equals("exit")) {
                        output.writeUTF("OK");
                        System.exit(0);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Message process(Message msg) {
        String type;
        int index;
        String content;

        type = msg.getType();
        if (type.equals("exit")) {
            return new Message.Builder()
                    .reason("OK")
                    .build();
        }



        switch (type) {
            case "get":
                if (database.containsKey(msg.getKey())) {
                    return new Message.Builder()
                            .response("OK")
                            .value(database.get(msg.getKey()))
                            .build();
                } else {
                    return new Message.Builder()
                            .response("ERROR")
                            .reason("No such key")
                            .build();
                }
            case "set":
                database.put(msg.getKey(), msg.getValue());
                return new Message.Builder()
                        .response("OK")
                        .build();
            case "delete":
                if (database.containsKey(msg.getKey())) {
                    database.remove(msg.getKey());
                    return new Message.Builder()
                            .response("OK")
                            .build();
                }
                return new Message.Builder()
                        .response("ERROR")
                        .reason("No such key")
                        .build();
            default:
                return new Message.Builder()
                        .response("ERROR")
                        .build();
        }
    }
}
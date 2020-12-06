package server;

import client.Message;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    Database database;
    DatabaseHandler databaseHandler;

    public ClientThread(ServerSocket serverSocket, Socket clientSocket, Database database, DatabaseHandler databaseHandler) {
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        this.database = database;
        this.databaseHandler = databaseHandler;
    }

    @Override
    public void run() {
        try (
                DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream())) {

            String query = input.readUTF();
            Message msg = new Gson().fromJson(query, Message.class);

            if (msg.getType().equals("exit")) {
                output.writeUTF(new Gson().toJson(new Message.Builder().response("OK").build()));
                    serverSocket.close();
            }

            Message messageFromServer = process(msg);
            output.writeUTF(new Gson().toJson(messageFromServer));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private Message process(Message msg) {
        String type;
        type = msg.getType();

        switch (type) {
            case "get":
                if (databaseHandler.getData(msg.getKey()) != null) {
                    return new Message.Builder()
                            .response("OK")
                            .value(databaseHandler.getData(msg.getKey()))
                            .build();
                } else {
                    return new Message.Builder()
                            .response("ERROR")
                            .reason("No such key")
                            .build();
                }
            case "set":
                databaseHandler.setData(msg.getKey(), msg.getValue());
                return new Message.Builder()
                        .response("OK")
                        .build();
            case "delete":
                if (databaseHandler.getData(msg.getKey()) != null) {
                    databaseHandler.deleteData(msg.getKey());
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


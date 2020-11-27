package server;

import client.Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    static String[] database = new String[1000];

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        String query;

        Arrays.fill(database, "");


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

                    String result = process(query);
                    if (result.equals("EXIT")) {
                        output.writeUTF("OK");
                        System.exit(0);
                    }
                    output.writeUTF(result);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String process(String msg) {
        String type;
        int index;
        String content;

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


        if (index < 1 || index > database.length) {
            return "ERROR";
        } else {
            index = index - 1;
        }

        switch (type) {
            case "get":
                return database[index].equals("") ? "ERROR" : database[index];
            case "set":
                database[index] = String.join(" ", Arrays.copyOfRange(msgArray, 2, msgArray.length));
                return "OK";
            case "delete":
                database[index] = "";
                return "OK";
            default:
                return "ERROR";
        }
    }
}
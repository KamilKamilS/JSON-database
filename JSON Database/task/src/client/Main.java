package client;

import com.beust.jcommander.JCommander;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        String answer;

        Message msg = new Message();
        Gson gson = new Gson();

        JCommander.newBuilder()
                .addObject(msg)
                .build()
                .parse(args);

        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream()))
        {
            System.out.println("Client started!");

            String query = gson.toJson(msg);
            output.writeUTF(query);
            System.out.println("Sent: " + query);

            answer = input.readUTF();
            System.out.println("Received: " + answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
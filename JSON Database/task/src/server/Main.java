package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    static Database database = new Database("/Users/kamil/Code/Hyperskill/JSON Database/JSON Database/task/src/server/data/db.json");
    static DatabaseHandler handler = new DatabaseHandler(database);

    public static void main(String[] args) {
        String address = "127.0.0.1";
        int port = 23456;
        Socket clientSocket = null;

        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (!server.isClosed()) {
                try {
                    clientSocket = server.accept();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
                if (clientSocket != null) {
                    new Thread(new ClientThread(server, clientSocket, database, handler)).start();
                }
            }
        } catch (UnknownHostException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}

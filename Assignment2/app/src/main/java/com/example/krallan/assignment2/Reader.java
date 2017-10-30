package com.example.krallan.assignment2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Reader implements Runnable {

    private Socket socket;
    private InputStream inputStream;
    private DataInputStream diStream;
    private Connection connection;

    public Reader(Socket socket, Connection connection) {
        this.socket = socket;
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            System.out.println("Reader started");
            inputStream = socket.getInputStream();
            diStream = new DataInputStream(inputStream);
            while (Thread.interrupted() == false) {
                String message = diStream.readUTF();
                connection.readMessage(message);
                System.out.println("Client reader: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

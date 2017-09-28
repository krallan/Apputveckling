package com.example.krallan.assignment2;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Krallan on 2017-09-28.
 */

public class Connection {

    public static final String SERVER_IP = "195.178.225.53";
    public static final int SERVER_PORT = 7117;

    private Socket socket;
    private Reader reader;
    private Writer writer;

    public Connection(){

        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);


            reader = new Reader(socket);
            writer = new Writer(socket);

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

}

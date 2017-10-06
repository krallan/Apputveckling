package com.example.krallan.assignment2;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Reader{

    private Socket socket;
        private InputStream inputStream;
    private DataInputStream diStream;

    public Reader(Socket socket){
        this.socket = socket;

        try{
            inputStream = socket.getInputStream();
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public DataInputStream getStream(){
        return diStream;
    }

}

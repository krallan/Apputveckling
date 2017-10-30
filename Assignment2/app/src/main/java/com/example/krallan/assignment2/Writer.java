package com.example.krallan.assignment2;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Writer implements Runnable {
    private Socket socket;
    private Buffer buffer;
    private OutputStream opStream;
    private DataOutputStream doStream;

    public Writer(Socket socket, Buffer buffer){
        this.socket = socket;
        this.buffer = buffer;
    }
    @Override
    public void run() {
        try{
            System.out.println("Writer started");
            opStream = socket.getOutputStream();
            doStream = new DataOutputStream(opStream);
            while(Thread.interrupted()==false){
                String message = buffer.get();
                doStream.writeUTF(message);
                doStream.flush();
                System.out.println("Client writer: " + message);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

package com.example.krallan.assignment2;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Krallan on 2017-09-28.
 */

public class Writer {
    private Socket socket;

    private OutputStream opStream;
    private DataOutputStream doStream;

    public Writer(Socket socket){
        this.socket = socket;

        try{
            opStream = socket.getOutputStream();
        }catch(IOException e){
            e.printStackTrace();
        }
        doStream = new DataOutputStream(opStream);
    }
    public DataOutputStream getStream (){
        return doStream;
    }
}

package com.example.krallan.assignment2;

import java.util.LinkedList;

/**
 * Created by Linus on 2017-10-06.
 */

public class Buffer {
    private LinkedList<String> buffer = new LinkedList<>();

    public synchronized void put (String string){
        buffer.addLast(string);
        notifyAll();
    }
    public synchronized String get () throws InterruptedException {
        while(buffer.isEmpty()){
            wait();
        }
        return buffer.removeFirst();
    }
}

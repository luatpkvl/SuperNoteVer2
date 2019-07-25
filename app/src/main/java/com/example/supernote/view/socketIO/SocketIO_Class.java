package com.example.supernote.view.socketIO;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class SocketIO_Class {
    private Socket socket;
    public Socket inIt() {
        {
            try{
                socket = IO.socket("http://192.168.1.5:3000");
            }catch (URISyntaxException e){}
        }
        return  socket;
    }
}

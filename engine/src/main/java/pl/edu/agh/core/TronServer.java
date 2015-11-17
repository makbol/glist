package pl.edu.agh.core;

import java.io.IOException;

public class TronServer {

    private static int PORT = 1666;
    
    private final TronListener listener;

    public TronServer() throws IOException {
       this.listener = new TronListener(PORT);
    }
    
    public void start() {
        listener.start();
    }
   
    public static void main(String[] args) throws IOException {
         new TronServer().start();
    }
}

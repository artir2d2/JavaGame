package TCPUtil;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Artir2d2 on 21.02.2016.
 */
public class ServerTask implements Callable<String> {
    private static int threadCount = 0;
    private static final BlockingQueue<PlayerAction> messageQueue = new LinkedBlockingQueue<PlayerAction>();
    private int id = threadCount++;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public ServerTask(Socket s) throws IOException {
        this.socket = s; //przekazanie obiektu połączenia z klientem
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //pobieranie danych
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); //wysyłanie danych
    }
    public String call(){
        while(true){
            //kod kazdego wątku obslugi klienta
         break;
        }
        return null;
    }

}

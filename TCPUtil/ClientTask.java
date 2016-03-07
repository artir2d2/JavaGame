package TCPUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Artir2d2 on 26.02.2016.
 */
public class ClientTask implements Callable<String> {
    private Socket socket;

    public ClientTask(InetAddress adr,int port) throws IOException {
        try{
            socket = new Socket(adr,port);
        }catch(IOException e) {
            System.err.println("Creating socket failed");
        }
    }

    @Override
    public String call() throws Exception {
        while(true){
           // System.out.print("tu sobie gracz gra w gre");
            if(false) break;
        }
        return null;
    }
}

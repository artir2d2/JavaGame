package TCPUtil;

import GuiUtil.GameGui;
import MapUtil.Cell;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Artir2d2 on 26.02.2016.
 */
public class Client{
    protected static String clientID;
    protected static ObjectOutputStream out;
    protected static ObjectInputStream in;
    private Socket socket;
    public static Cell cellsToRender[][];

    public Client(String hostAddress,int port,String playerName) throws IOException {
        InetAddress adr = InetAddress.getByName(hostAddress);
        cellsToRender = new Cell[31][21];
        try{
            socket = new Socket(adr,port);
        }catch(IOException e) {
            System.err.println("Creating socket failed");
        }
        in = new ObjectInputStream(this.socket.getInputStream());
        out = new ObjectOutputStream(this.socket.getOutputStream());

        try {
            clientID = (String)in.readObject();//getting client id from server
            System.out.println("Client: "+clientID);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        out.writeObject(playerName);
        if(clientID.contains("ERROR")){ //message to print if something gone wrong on server side
            GameGui.messageToPop = clientID;
            System.out.println("Client: "+clientID);
        }else{
            System.out.println("Connected to server");
            GameGui.messageToPop = "Connected to server";
            String startClientMessage = ""; //waits until server send "startclient" message
            try {
                in = new ObjectInputStream(this.socket.getInputStream());
                out = new ObjectOutputStream(this.socket.getOutputStream());
                startClientMessage = (String)in.readObject();
                System.out.println("Client: succes with reading object");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(startClientMessage+"<-tu startclient");
            if(startClientMessage.equals("startclient")){ //now when the server is ready, client also starts its game task
                ExecutorService exec = Executors.newCachedThreadPool();
                ClientTask task = new ClientTask();
                ClientTaskCallback<String> ctc = new ClientTaskCallback<String>(task);
                exec.execute(ctc);
                System.out.println("Adress = " + adr);
            }
        }


    }

    class ClientTaskCallback<T> extends FutureTask<T> {
        public ClientTaskCallback(Callable<T> callable) {
            super(callable);
        }

        public void done() {
            String msg = "Wynik: ";
            if (isCancelled())
                msg += "Anulowane.";
            else{
                try {
                    msg += get();
                } catch (Exception exc) {
                    msg += exc.toString();
                }
            }
            System.out.println("Wątek klienta zakonczony." + msg);
        }
    }

    public static void main(String []args) throws IOException {
        Client client = new Client("localhost",8080,"wertyui");
    }
}

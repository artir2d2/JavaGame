package TCPUtil;

import GuiUtil.GameGui;
import MapUtil.Cell;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by Artir2d2 on 26.02.2016.
 */
public class Client implements Runnable{
    private static ExecutorService exec;
    private static InetAddress adr;
    private static Callable<String> clientTask;
    private static ClientTask task;
    private static ClientTaskCallback<String> ctc;
    private int port;
    protected static String clientID;
    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;
    public static Cell cellsToRender[][];

    public Client(String hostAddress,int port,String playerName) throws IOException {
        this.adr = InetAddress.getByName(hostAddress);
        this.port = port;
        this.cellsToRender = new Cell[31][21];
        try{
            socket = new Socket(adr,port);
        }catch(IOException e) {
            System.err.println("Creating socket failed");
        }

        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream())); //pobieranie danych
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())),true); //wysyłanie danych
        this.clientID = in.readLine();//getting client id from server
        this.out.println(playerName);//sending player nickname to server
        if(clientID.contains("ERROR")){ //message to print if something gone wrong on server side
            GameGui.messageToPop = clientID;
            System.out.println(clientID);
        }else{
            GameGui.messageToPop = "Connected to server";
            String startClientMessage = in.readLine(); //waits until server send "startclient" message
            if(startClientMessage.equals("startclient")){ //now when the server is ready, client also starts its game task
                this.exec = Executors.newCachedThreadPool();
                task = new ClientTask(socket);
                ctc = new ClientTaskCallback<String>(task);
                exec.execute(ctc);
                System.out.println("Adress = " + adr);
            }
        }


    }

    @Override
    public void run() {
//        try {
//            task = new ClientTask(adr,port);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ctc = new ClientTaskCallback<String>(task);
//        exec.execute(ctc);

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

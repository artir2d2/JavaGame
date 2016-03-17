package TCPUtil;

import MapUtil.Player;
import MapUtil.WorldMap2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * Created by artir2d2 on 21.02.2016.
 * Server class
 */
public class Server implements Runnable {
    private static ServerSocket serverSocket;
    private static ExecutorService exec;//= Executors.newCachedThreadPool();
    private static Callable<String> serverTask;//= new ServerTask1();
    private static ServerTask task;
    private static ArrayList<ServerTaskCallback<String>> ft;
    public static WorldMap2 worldMap;
    protected static int playerCount;
    private PrintWriter out;
    private BufferedReader in;
    protected LinkedBlockingQueue<PlayerAction> playerActions;
    protected static HashMap players;
    boolean done = false;

    /**
     *
    */
    public Server(int port,int mapSize)throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.playerActions = new LinkedBlockingQueue<PlayerAction>();
        this.players = new HashMap<Integer,Player>();
        this.worldMap = new WorldMap2(mapSize,mapSize,50);
        this.ft = new ArrayList<ServerTaskCallback<String>>();
        this.exec = Executors.newCachedThreadPool();
    }
    public double returnWorldMapProgress(){
        return this.worldMap.progress;
    }
    @Override
    public void run() {
        System.out.println("Tworze mape");
        worldMap.create();
        while(true) {
            Socket connectionSocket = null;
            try {
                connectionSocket = serverSocket.accept();
                this.in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); //pobieranie danych
                this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream())),true); //wysyłanie danych
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Przyjeto pol.: " + connectionSocket);
            /*
            Checking if there is enough space for another player and gets the connection requirements
             */
            String playerName=null;
            if(playerCount<2){
                out.println(playerCount);//client gets its server id
                try {
                    playerName = new String(in.readLine()); //server gets players name
                    System.out.println(playerName+ " playerName");
                } catch (IOException e) {
                    e.printStackTrace();
                }
               // System.out.println(playerName);
                players.put(playerCount,this.worldMap.placePlayer(playerName,playerCount));//add(this.worldMap.placePlayer(playerName,playerCount));//placing player on the map
               Player play = (Player)this.players.get(playerCount);
                System.out.println(play.getPozX()+"    "+play.getPozY());
                /*
                Creating new server task
                */
                try {
                    this.task = new ServerTask(connectionSocket,playerCount);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                playerCount++;
                this.ft.add(new ServerTaskCallback<String>(task));
                if(playerCount == 2){
                    for(ServerTaskCallback<String> it : ft)
                        this.exec.execute(it);
                }
            }else{
                out.println("ERROR Connection Refused: Server is Full");
            }
        }
    }

    static class ServerTaskCallback<T> extends FutureTask<T> {
        public ServerTaskCallback(Callable<T> callable) {
            super(callable);
        }

        public void done() {
            System.out.println("Wątek serwera zakonczony");

            //tu kod który powinien wykonac sie po zakonczeniu dzialania kazdego z watkow servera...
        }
    }

    public static void main(String []args) throws IOException {
        Server serwer = new Server(5674,200);
    }
}

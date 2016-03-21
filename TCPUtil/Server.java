package TCPUtil;

import MapUtil.Player;
import MapUtil.WorldMap2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by artir2d2 on 21.02.2016.
 * Server class
 */
public class Server implements Runnable {
    private static ServerSocket serverSocket; //socket for all the connections
    private static ExecutorService exec; //server tasks executor
    private static ArrayList<Thread> ft; //threads to execute by exec
    public static WorldMap2 worldMap; //world map of the server
    protected static int playerCount; //player counter
    private ObjectOutputStream out;
    private ObjectInputStream in;
    /**
     *
    */
    public Server(int port,int mapSize)throws IOException {
        serverSocket = new ServerSocket(port);
        worldMap = new WorldMap2(mapSize,mapSize,50);
        ft = new ArrayList<>();
        exec = Executors.newCachedThreadPool();
    }
    public double returnWorldMapProgress(){
        return worldMap.progress;
    }
    @Override
    public void run() {
        worldMap.create(); //creates the map
        while(true) {
            Socket connectionSocket = null;
            try {
                connectionSocket = serverSocket.accept();
                out = new ObjectOutputStream(connectionSocket.getOutputStream());
                in = new ObjectInputStream(connectionSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Przyjeto pol.: " + connectionSocket);
            /*
            Checking if there is enough space for another player and gets the connection requirements
             */
            String playerName="";
            if(playerCount<2){
                try {
                    out.writeObject(playerCount+"");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    playerName = (String)in.readObject(); //server gets players name
                    System.out.println(playerName+ " <-playerName");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                WorldMap2.players.put(playerCount,worldMap.placePlayer(playerName,playerCount));//add(this.worldMap.placePlayer(playerName,playerCount));//placing player on the map
                Player play = (Player) WorldMap2.getPlayer(playerCount);
                System.out.println(play.getPozX()+"  playerPos  "+play.getPozY());
                /*
                Creating new server task
                */
                ft.add(new Thread(new ServerTask(connectionSocket,playerCount)));
                playerCount++;
                System.out.println("wszystko ok");
                if(playerCount == 2){
                    for(Thread it : ft)
                        exec.execute(it);
                }
            }else{
                try {
                    out.writeObject("ERROR Connection Refused: Server is Full");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String []args) throws IOException {
       new Server(5674,200);
    }
}

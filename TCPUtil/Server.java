package TCPUtil;

import MapUtil.WorldMap2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Created by artir2d2 on 21.02.2016.
 */
public class Server implements Runnable {
    private static ServerSocket serverSocket;
    private static ExecutorService exec;//= Executors.newCachedThreadPool();
    private static Callable<String> serverTask;//= new ServerTask1();
    private static ServerTask task;
    private static ServerTaskCallback<String> ft;
    public static WorldMap2 worldMap;

    public Server(int port,int mapSize)throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.worldMap = new WorldMap2(mapSize,mapSize,50);
        System.out.println("zrobilem mape");
    }
    public double returnWorldMapProgress(){
        return this.worldMap.progress;
    }
    @Override
    public void run() {
        while(true) {
            Socket connectionSocket = null;
            try {
                connectionSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Przyjeto pol.: " + connectionSocket);
            try {
                this.task = new ServerTask(connectionSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.ft= new ServerTaskCallback<String>(task);
            System.out.println(ft.toString());
            this.exec = Executors.newCachedThreadPool();
            this.exec.execute(ft);
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

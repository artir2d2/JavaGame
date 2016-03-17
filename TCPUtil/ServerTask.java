package TCPUtil;

import MapUtil.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Artir2d2 on 21.02.2016.
 */
public class ServerTask implements Callable<String> {
    private int id;
    private Socket socket;
//    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerTask(Socket s, int id) throws IOException {
        this.id = id;
        this.socket = s; //przekazanie obiektu połączenia z klientem
//        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //pobieranie danych
        this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); //wysyłanie danych
    }
    public String call() throws IOException {
        System.out.println("Server task "+id+"  socket: "+socket.toString());
        this.out.println("startclient");//this indicate the client task to start at the same time as server tasks starts
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        Player player = (Player)Server.players.get(id);
        int minXPos = player.getPozX()-15;
        if(minXPos<0) minXPos =0;
        else if(minXPos>Server.worldMap.getXSize()-15) minXPos = Server.worldMap.getXSize()-15;
        int minYPos = player.getPozY()-10;
        if(minYPos<0) minYPos =0;
        else if(minYPos>Server.worldMap.getYSize()-10) minYPos = Server.worldMap.getYSize()-10;
        int maxXPos = minXPos+31;
        int maxYPos = minYPos+21;
        for(int x = minXPos;x<maxXPos+1;x++){
            for(int y = minYPos;y<maxYPos+1;y++){
                oos.writeObject(Server.worldMap.getCell(x,y));
            }
        }
        oos.writeObject(new String("done"));
        oos.close();
//        this.ois = new ObjectInputStream(socket.getInputStream());
//        ois.close();
      // Player player =(Player) Server.players.get(id);
      //  System.out.println(player.playerName);
        while(true){

            break;
        }
        return null;
    }

    public void sendMapData(){

//        for(int y=0;y<)
//        this.oos.writeObject();
    }
}

package TCPUtil;

import MapUtil.Player;
import MapUtil.WorldMap2;

import java.io.*;
import java.net.Socket;

/**
 * Created by Artir2d2 on 21.02.2016.
 */
public class ServerTask implements Runnable {
    private int id;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    public ServerTask(Socket s, int id) {
        this.id = id;
        this.socket = s; //przekazanie obiektu połączenia z klientem
    }
    public void run(){
        System.out.println("Server task "+id+"  socket: "+socket.toString());
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.writeObject("startclient");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            sendMapData();//initial map sending
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            String command = "";
            try {
                command = (String)in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            Server.worldMap.movePlayer(id,command);
            try {
                sendMapData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(command.equals("end")) break;
        }
    }

    public void sendMapData() throws IOException {
        Player player = WorldMap2.getPlayer(id);
        int minXPos = player.getPozX()-15;
        if(minXPos<0) minXPos =0;
        else if(minXPos>Server.worldMap.getXSize()-30) minXPos = Server.worldMap.getXSize()-31;
        int minYPos = player.getPozY()-10;
        if(minYPos<0) minYPos =0;
        else if(minYPos>Server.worldMap.getYSize()-20) minYPos = Server.worldMap.getYSize()-21;
        int maxXPos = minXPos+31;
        int maxYPos = minYPos+21;
        for(int x = minXPos;x<maxXPos;x++){
            for(int y = minYPos;y<maxYPos;y++){
                    out.writeObject(Server.worldMap.getCell(x,y));
            }
        }
        System.out.println("done sending map data to client");
    }
}

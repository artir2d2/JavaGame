package TCPUtil;

import MapUtil.Cell;
import MapUtil.GameFrame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Artir2d2 on 26.02.2016.
 */
public class ClientTask implements Callable<String> {

    @Override
    public String call(){
        getMapData();
        GameFrame gf = new GameFrame();
        gf.play();
        while(true){
            if(false) break;
        }
        return "Klient sie rozlaczyl";
    }
    public synchronized static void getMapData(){
        try {
            for (int x = 0; x < 31; x++) {
                for (int y = 0; y < 21; y++) {
                    Object o = null;
                    try {
                            Client.cellsToRender[x][y] = (Cell)Client.in.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                        System.exit(0);
                    }
                    //System.out.print(((Cell) o).getCord().getZ()+" ");
                }
                //System.out.println();
            }

            System.out.println("Done recieving data from server");
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }
}

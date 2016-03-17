package TCPUtil;

import MapUtil.Cell;
import MapUtil.GameFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Artir2d2 on 26.02.2016.
 */
public class ClientTask implements Callable<String> {
    private Socket socket;
    private ObjectInputStream ois;
    public ClientTask(Socket s) throws IOException {
        this.socket = s;
        ois = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public String call(){
        int iter=0;
        try {
            for (int x = 0; x < 31; x++) {
                for (int y = 0; y < 21; y++) {
                    iter++;
                    Object o = null;
                    try {
                        o = ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (o instanceof Cell) {
                        Cell ds = (Cell) o;
                        Client.cellsToRender[x][y] = (Cell) o;
                        //System.out.println(x + "  " + y + "  " + iter);
                        //System.out.println(ds.getCord().getX()+"   "+ds.getCord().getY());
                    } else {
                        if (o instanceof String && o.equals("done")) {
                            break;
                        } else
                            System.out.println("something gone wrong");
                        break;
                    }
                }
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        GameFrame gf = new GameFrame();
        gf.play();
        while(true){
           System.out.print("Wykonano Client task klienta nr "+Client.clientID);
            if(true) break;
        }
        return null;
    }
}

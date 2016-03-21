package TCPUtil;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

/**
 * Created by Artir2d2 on 19.03.2016.
 */
public class InputKeyEvent implements KeyListener {

    public void sendAction(String action){
           //Client.out.println(action);
        try {
            Client.out.writeObject(action);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        e.consume();
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP){
            sendAction("up");
            System.out.println("up");
            ClientTask.getMapData();
            e.consume();
        }else if(key == KeyEvent.VK_DOWN){
            sendAction("down");
            System.out.println("down");
            ClientTask.getMapData();
            e.consume();
        }else if(key == KeyEvent.VK_LEFT){
            sendAction("left");
            System.out.println("left");
            ClientTask.getMapData();
            e.consume();
        }else if(key == KeyEvent.VK_RIGHT){
            sendAction("right");
            System.out.println("right");
            ClientTask.getMapData();
            e.consume();
        }else{
            e.consume();
        }
    }
}

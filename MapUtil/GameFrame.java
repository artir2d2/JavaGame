package MapUtil;

import TCPUtil.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

/**
 * Created by Adam Zadora on 2016-02-06.
 */
public class GameFrame extends Canvas {
    private SpriteCache spriteCache;
    private BufferStrategy bufferStrategy;
    private double width = 1550;
    private double height = 1050;
    //private WorldMap2 worldMap2;

    public GameFrame (){
        spriteCache = new SpriteCache();
        JFrame frame = new JFrame("Welcome to the game!");
        setSize((int)width,(int)height);
        frame.add(this);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
        requestFocus();
    }

//    public void init(){
//        worldMap2 = new WorldMap2(300, 300);
//        worldMap2.create();//.start();
//    }

    public void play(){
        //init();
        while(true){
            paintWorld();
        }
    }
    /*public void chooseGraph(){
        for(int i=1; i<size.getX()-1; i++) {
            for (int j = 1; j < size.getY()-1; j++) {
                if (matrix[i][j - 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "01.png");
                }
                if (matrix[i + 1][j].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "02.png");
                }
                if (matrix[i + 1][j + 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "03.png");
                }
                if (matrix[i - 1][j].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "04.png");
                }
                if (matrix[i - 1][j - 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "09.png");
                }
                if (matrix[i + 1][j - 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "10.png");
                }
                if (matrix[i + 1][j + 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "11.png");
                }
                if (matrix[i - 1][j + 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                    matrix[i][j].setSpriteName(checkHeight(i, j) + "12.png");
                }
            }
        }
    }*/

    public int checkHeight(int dx, int dy){
        if(Client.cellsToRender[dx][dy].getCord().getZ()>=17)
            return 2;
        else if(Client.cellsToRender[dx][dy].getCord().getZ()==0) return 3;
        else return 1;
    }

    private void paintWorld() {
        Graphics2D g2 = (Graphics2D)bufferStrategy.getDrawGraphics();
        g2.clearRect(0,0,1550,1050);
        //x-=15;
        //y-=10;
        //if(x<0) x=0;
        //if(y<0) y=0;
        Cell matrix[][] = Client.cellsToRender;//= worldMap2.getMatrix();
        Cell cell = null;
        int screenX = 0;
        int screenY;
        try{
            for(int i =0; i<31; i++) {
                screenY = 0;
                for (int j = 0; j <21; j++) {
                    if (checkHeight(i, j) == 3) {
                        g2.drawImage(spriteCache.getSprite("woda.png"), screenX * 50, screenY * 50, this);
                    }
                    screenY++;
                }
                screenX++;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }
        screenX = 0;
        try {
            for (int i = 0; i < 31; i++) {
                screenY = 0;
                for (int j = 0; j < 21; j++) {
                    cell = Client.cellsToRender[i][j];//matrix[i][j];
                    ArrayList<MapObject> allObjects = cell.getAllObjects();
                    if (checkHeight(i, j) == 1 || checkHeight(i, j) == 2) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i, j) + "09.png"), screenX * 50, screenY * 50, this);
                    }
                    if (j > 0 && matrix[i][j - 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i, j - 1) + "01.png"), screenX * 50, screenY * 50, this);
                    }
                    if (i<30 && matrix[i + 1][j].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i + 1, j) + "02.png"), screenX * 50, screenY * 50, this);
                    }
                    if (j<20 && matrix[i][j + 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i, j + 1) + "03.png"), screenX * 50, screenY * 50, this);
                    }
                    if (i > 0 && matrix[i - 1][j].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i - 1, j) + "04.png"), screenX * 50, screenY * 50, this);
                    }
                    if (i > 0 && j > 0 && matrix[i - 1][j - 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i - 1, j - 1) + "05.png"), screenX * 50, screenY * 50, this);
                    }
                    if (i<30 && j>0 && matrix[i + 1][j - 1].getCord().getZ() > matrix[i][j].getCord().getZ() && j > 0) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i + 1, j - 1) + "06.png"), screenX * 50, screenY * 50, this);
                    }
                    if (i<30 && j<20 && matrix[i + 1][j + 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i + 1, j + 1) + "07.png"), screenX * 50, screenY * 50, this);
                    }
                    if (i > 0 && j<20 && matrix[i - 1][j + 1].getCord().getZ() > matrix[i][j].getCord().getZ()) {
                        g2.drawImage(spriteCache.getSprite(checkHeight(i - 1, j + 1) + "08.png"), screenX * 50, screenY * 50, this);
                    }

                    if (allObjects != null)
                        for (MapObject ob : allObjects) {
                            if (ob instanceof Tree)
                                g2.drawImage(spriteCache.getSprite("sh3a.png"), screenX * 50 - 30, screenY * 50 - 20, this);
                            else if (ob instanceof Rock)
                                g2.drawImage(spriteCache.getSprite("sh4.png"), screenX * 50 - 20, screenY * 50 - 30, this);
                            g2.drawImage(spriteCache.getSprite(ob.getSpriteName()), screenX * 50 - 10, screenY * 50 - 25, this);
                        }

                    screenY++;
                    //System.out.print("y = " + screenY + ", ");
                }
                screenX++;
                //System.out.println("x = " + screenX);
            }
        }catch(ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
        }

        bufferStrategy.show();
    }

    public static void main(String args [] ) {
        GameFrame gra = new GameFrame();
        gra.play();
    }


}

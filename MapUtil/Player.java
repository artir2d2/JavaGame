package MapUtil;

import java.io.Serializable;

/**
 * Created by Adam Zadora on 2016-02-06.
 */
public class Player extends MapObject implements Serializable{
    public int pozX, pozY;
    public String playerName;

    public Player(String playerName, int x, int y, String playerSprite){
        this.playerName = playerName;
        this.pozX = x;
        this.pozY = y;
        this.spriteName = playerSprite;
    }

    public Player(){
       
    }

    public int getPozX(){
        return pozX;
    }

    public int getPozY(){
        return pozY;
    }

    public void setPozX(int x){
        pozX = x;
    }
    public void setPozY(int y){
        pozY = y;
    }

    @Override
    public void placeObject(Cell cell) {

    }

    @Override
    public void removeObject(Cell cell) {

    }

    @Override
    public void interact(Character character) {

    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

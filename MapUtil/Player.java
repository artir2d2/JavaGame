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

    public synchronized int getPozX(){
        return pozX;
    }
    public synchronized int getPozY(){
        return pozY;
    }
    public synchronized void setPozX(int x){
        pozX = x;
    }
    public synchronized void setPozY(int y){
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Player player = (Player) o;
        return pozX == player.pozX && pozY == player.pozY && playerName.equals(player.playerName);
    }

    @Override
    public int hashCode() {
        int result = pozX;
        result = 31 * result + pozY;
        result = 31 * result + playerName.hashCode();
        return result;
    }
}

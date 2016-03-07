package MapUtil;

import java.io.Serializable;

/**
 * Created by Dupasraka on 06.02.2016.
 */
public class Rock extends MapObject implements Serializable {
    int stoneCount;

    public Rock(int rockCount, String spriteName){
        this.exist = true;
        this.stoneCount = rockCount;
        this.spriteName = spriteName;
    }
    @Override
    public void placeObject(Cell cell)  {
        cell.addObject(this);
    }

    @Override
    public void removeObject(Cell cell) {cell.removeObject(this);}

    @Override
    public void interact(Character character) {
        if(stoneCount>0){
			/*
			 * heres the action of adding  resource to players equipment
			 */
        }
        else{
            exist = false;
            throwExist(); //returns false flag to every listener on listeners array
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean same = false;
        if(obj!=null && obj instanceof Rock) {
            same = (this.exist == ((Rock) obj).exist &&
                    this.stoneCount == ((Rock) obj).stoneCount);
        }
        return same;
    }
}

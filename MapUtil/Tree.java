package MapUtil;

import java.io.Serializable;
import java.time.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Implementation of Tree object on the game environment. Log - in polish is a pień.
 * @author Artir2d2
 *
 */
public class Tree extends MapObject implements Serializable{
	private int growthStage = 4;
	private int logNum = 20;
	/**
	 * When the tree is constructed, the exist marker is turned true, exist flag is a message to remove an object from the map
	 * Every tree should be at growth stage 4 at the begining...
	 * @param growthStage
	 */
	public Tree(int growthStage, String spriteName){
		this.exist = true;
		this.growthStage = growthStage;
		this.spriteName = spriteName;
		switch(growthStage){
		case 1:
			logNum = 1;
			break;
		case 2:
			logNum = 5;
			break;
		case 3:
			logNum = 10;
			break;
		case 4:
			logNum = 20;
			break;
		}
	}
	
	@Override
	public void placeObject(Cell cell) {cell.addObject(this);}

	@Override
	public void removeObject(Cell cell) {cell.removeObject(this);}

	@Override
	public void interact(Character character) {
		if(logNum>0){
			/*
			 * heres the action of adding  resource to players equipment
			 */
		}
		else{
			exist = false;
			throwExist(); //returns false flag to every listener on listeners array
		}
	}
	/**
	 * Equals method. One tree is equals to other when every field is equal. That includes: id, exist, growthStage, branchNum, logNum
	 */
	public boolean equals(Object obj) {
		boolean same = false;
		if(obj!=null &&obj instanceof Tree){
			same = (this.exist ==((Tree)obj).exist &&
					this.growthStage ==((Tree)obj).growthStage &&
					this.logNum ==((Tree)obj).logNum);
		}
		return same;
	}
}

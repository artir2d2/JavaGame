package MapUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * MapObject class is the base class to extends by objects wchich will be see as visible objects in world map cells.
 * Because MapObject can be created by the player or removed (for example: cutting the trees), it needs to inform cell on wchich it is located,
 * that it does not exist anymore, so the cell can remove that object from its list. 
 * To do so, Cell class needs to implement ExistListener and override existChange() method.
 * @author Artir2d2
 */
public abstract class MapObject implements Serializable{
	protected boolean exist = false;
	private List<ExistListener> listeners = new ArrayList<>();
	protected String spriteName;

	
	
	/**
	 * Adding listener to arrayList of current listeners that instantation of object needs to inform if exist flag will change
	 * @param toAdd - listener to add
	 */
	public void addListener(ExistListener toAdd) {
		listeners.add(toAdd);
	}
	
    /**
     * Returns actual state of exist flag and inform every listener that flag has changed.
     * @return returns exist mark
     */
	public boolean throwExist() { //say hello
		for (ExistListener hl : listeners)
			hl.existChanged(this);
		return exist;
	}

	public String getSpriteName(){
		return spriteName;
	}

	abstract public void placeObject(Cell cell);
	abstract public void removeObject(Cell cell);
	abstract public void interact(Character character);
	public static void getExist(MapObject obj){
	}

	@Override
	public int hashCode() {
		int result = (exist ? 1 : 0);
		result = 31 * result + (listeners != null ? listeners.hashCode() : 0);
		result = 31 * result + spriteName.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MapObject mapObject = (MapObject) o;
		if (exist != mapObject.exist) return false;
		if (listeners != null ? !listeners.equals(mapObject.listeners) : mapObject.listeners != null) return false;
		return spriteName.equals(mapObject.spriteName);

	}
}

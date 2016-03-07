package MapUtil;
import java.io.Serializable;
import java.util.*;

/**
 * Implementation of cells which WorldMap is made of and every MapObject needs to be placed in array of MapObjects composition
 * @author Artir2d2
 *
 */
public class Cell implements ExistListener, Serializable{
	private int id;
	private Point coord;
	private ArrayList<MapObject> object;
	
	Cell(int x, int y, int z, int id){
		this.coord = new Point(x,y,z);
		this.id = id;
		this.object = new ArrayList<MapObject>();
	};

	public int getId(){
		return this.id;
	}
	
	public Point getCord(){
		return this.coord;
	}
	
	public void update(int x, int y, int z, int id){
		this.id = id;
		this.coord = new Point(x,y,z);
	}
	
	public void addObject(MapObject obj){
		this.object.add(obj);
	}
	
	public void removeObject(MapObject obj){
			object.remove(obj);
	}

	public MapObject getObject(int i){
		return object.get(i);
	}
	
	public ArrayList<MapObject> getAllObjects(){
		return object;
	}

	@Override
	/**
	 * When exist of initiator object will change to false,
	 * the MapObject deletes this MapObject from object array,
	 */
	public void existChanged(MapObject initiator) { //someone said hello
		// TODO Auto-generated method stub
		if(!initiator.exist){
			object.remove(initiator);
		}
	}
}

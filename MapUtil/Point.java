package MapUtil;

import java.io.Serializable;

/**
 * Extension of @link java.awt.Point
 * This implementation wraps the Point class from its ancestor and adds Z axis.
 * @author Artir2d2
 *
 */
public class Point extends java.awt.Point implements Serializable{
	private static final long serialVersionUID = 1L;
private int z;

Point(int x,int y, int z){
	super(x,y);
	this.z = z;
}
public int getZ(){
	return this.z;
}
public void setZ(int z){
	this.z = z;
}
}

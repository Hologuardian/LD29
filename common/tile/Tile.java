package holo.common.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public class Tile 
{
	public Image tex;
	public Vector2f loc;
	public boolean collidable;
	public static int size = 64;
	
	public Tile(Vector2f coords, String texture, boolean collidable) throws SlickException
	{
		tex = new Image(texture);
		loc = coords;
		this.collidable = collidable;
	}
	
	public Rectangle getBB()
	{
		return new Rectangle(loc.x * size, loc.y * size, size, size);
	}
	
	public boolean getCollidable()
	{
		return collidable;
	}
	
	public Vector2f getCoords()
	{
		return loc;
	}
	
	public Image getTexture()
	{
		return tex;
	}
}

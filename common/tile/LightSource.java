package holo.common.tile;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Vector2f;

public class LightSource 
{
	public Vector2f loc;
	public float strength;
	public Color color;
	public static int size = 8;
	public static float maxStrength = 127;
	
	/**
	 * 
	 * @param coord
	 * @param strength
	 * @param c
	 */
	public LightSource(Vector2f coord, float strength, Color c)
	{
		this.strength = strength;
		loc = coord;
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setCoords(Vector2f coords)
	{
		loc = coords;
	}
	
	public void moveCoords(float x, float y)
	{
		loc.set(x, y);
	}
	
	public Vector2f getCoords()
	{
		return loc;
	}
	
	public void setStrength(float s)
	{
		strength = s;
	}
	
	public float getStrength()
	{
		return strength;
	}
}

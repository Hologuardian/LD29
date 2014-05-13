package holo.common.tile;

import holo.common.world.World;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.*;

public class Decoration 
{
	public Image[] tex;
	public Shape bb;
	public Vector2f loc;
	public World world;
	public int frameTime;
	public float lightLevel;
	public boolean collidable;
	public LightSource light;
	public float theta;
	public long timer;
	
	/**
	 * 
	 * @param world
	 * @param coords
	 * @param texture
	 * @param size
	 * @param theta
	 * @param collidable
	 * @param lightLevel
	 * @param frameTime
	 */
	public Decoration(World world, Vector2f coords, String[] texture, float[] size, float theta, boolean collidable, float lightLevel, int frameTime)
	{
		tex = new Image[texture.length];
		try
		{
			for(int i = 0; i < texture.length; ++i)
			{
				tex[i] = new Image(texture[i]);
			}
		}
		catch(SlickException e)
		{
			e.printStackTrace();
		}
		loc = coords;
		this.world = world;
		this.theta = theta;
		this.collidable = collidable;
		this.lightLevel = lightLevel;
		this.frameTime = frameTime;
		bb = new Rectangle(coords.getX(), coords.getY(), size[0], size[1]);
		this.light = new LightSource(this.getCenterCoords(), this.lightLevel, Color.blue);
	}
	
	public void update(int delta)
	{
		timer += delta;
		if(this.isLight())
		{
			this.light.setCoords(this.getCenterCoords());
			this.light.setStrength(lightLevel + timer % 8);
		}
	}
	
	public boolean collidable()
	{
		return collidable;
	}
	
	public boolean isLight()
	{
		return lightLevel > 0;
	}
	
	public Vector2f getCoords()
	{
		return loc;
	}
	
	public Vector2f getCenterCoords()
	{
		return new Vector2f(getBB().getCenter());
	}
	
	public Image getFrame(int i)
	{
		return tex[i];
	}
	
	public int getNumFrames()
	{
		return tex.length;
	}
	
	public int getFrameTime()
	{
		return frameTime;
	}
	
	public Shape getBB()
	{
		return bb;
	}
	
	public float getRotation()
	{
		return theta;
	}
}

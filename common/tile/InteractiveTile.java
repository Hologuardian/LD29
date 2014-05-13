package holo.common.tile;

import holo.common.entity.EntityPlayer;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public abstract class InteractiveTile extends Tile
{

	public InteractiveTile(Vector2f coords, String texture, boolean collidable) throws SlickException 
	{
		super(coords, texture, collidable);
	}
	
	public abstract String getDisplayMessage();
	
	public abstract void interact(EntityPlayer player);
}

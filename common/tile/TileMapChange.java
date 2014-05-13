package holo.common.tile;

import holo.common.entity.EntityPlayer;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class TileMapChange extends InteractiveTile
{
	public String mapName;
	public Vector2f spawn;
	
	public TileMapChange(Vector2f coords, String texture, boolean collidable, String mapName, Vector2f spawn) throws SlickException 
	{
		super(coords, texture, collidable);
		this.mapName = mapName;
		this.spawn = spawn;
	}

	@Override
	public String getDisplayMessage() 
	{
		return ("Press 'E' to enter " + this.mapName + ".");
	}

	@Override
	public void interact(EntityPlayer player) 
	{
		player.world.changeMapForceSpawn(mapName, spawn);
	}

}

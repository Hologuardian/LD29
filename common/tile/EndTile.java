package holo.common.tile;

import holo.common.entity.EntityPlayer;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EndTile extends InteractiveTile
{

	public EndTile(Vector2f coords, String texture, boolean collidable) throws SlickException 
	{
		super(coords, texture, collidable);
	}

	@Override
	public String getDisplayMessage() 
	{
		return "Press 'E' to call for evac and successfly complete your mission.";
	}

	@Override
	public void interact(EntityPlayer player) 
	{
		player.world.win();
	}

}

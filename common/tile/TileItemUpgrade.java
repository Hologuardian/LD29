package holo.common.tile;

import holo.common.entity.EntityPlayer;
import holo.common.item.ITEMS;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class TileItemUpgrade extends InteractiveTile
{
	public ITEMS item;

	public TileItemUpgrade(Vector2f coords, String texture, boolean collidable, ITEMS item) throws SlickException 
	{
		super(coords, texture, collidable);
		this.item = item;
	}

	@Override
	public String getDisplayMessage() 
	{
		return "Press 'E' to change " + getItemType(item.getSlot()) + " to: " + item.getName();
	}
	
	public String getItemType(int slot)
	{
		if(slot == 0)
			return "Sword";
		else if(slot == 1)
			return "Bow";
		else if(slot == 2)
			return "Lantern";
		else if(slot == 3)
			return "Potion";
		
		return "";
	}

	@Override
	public void interact(EntityPlayer player) 
	{
		player.inventory.putItem(item);
	}

}

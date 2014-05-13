package holo.common.item;

import holo.common.entity.Entity;
import holo.common.entity.EntityPlayer;

import org.newdawn.slick.geom.Vector2f;

public class ItemLantern extends Item
{
	public float lightLevel;

	public ItemLantern(String[] texLoc, float lightLevel) 
	{
		super(texLoc);
		this.lightLevel = lightLevel;
	}

	@Override
	public void onUse(Entity entity) 
	{
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer p = (EntityPlayer)entity;
			p.lightStrength = this.lightLevel;
			
			float r = entity.getBB(entity.getCoords()).getBoundingCircleRadius();
			Vector2f looking = entity.getLooking().copy().normalise().scale(r);
			
			float x = entity.getCenterCoords().copy().getX() + looking.copy().getX() + looking.copy().getPerpendicular().getX();
			float y = entity.getCenterCoords().copy().getY() + looking.copy().getY() + looking.copy().getPerpendicular().getY();
				
			p.light.setCoords(new Vector2f(x, y));
		}
	}

}

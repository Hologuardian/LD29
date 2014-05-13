package holo.common.entity;

import holo.common.world.World;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class EntityDummy extends Entity
{
	public Rectangle bb = new Rectangle(0, 0, 32, 32);

	public EntityDummy(World world, Vector2f coords, String texture) throws SlickException 
	{
		super(world, coords, texture);
	}

	@Override
	public float getResistance() 
	{
		return 1.0F;
	}

	@Override
	public float getWeight() 
	{
		return 75;
	}

	@Override
	public float getMaxHealth() 
	{
		return 500;
	}

	@Override
	public Shape getBB(Vector2f location) 
	{
		bb.setLocation(location);
		return bb;
	}

	@Override
	public void onCollideWithEntity(Entity entity) 
	{
		
	}

}

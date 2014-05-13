package holo.common.entity;

import holo.common.world.World;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class EntityArrow extends Entity
{
	public Shape bb;
	public Entity shooter;
	public float damage;
	public float knockback;
	public float timeAlive = 0;
	public boolean hasCollided;
	public final float lifeTime = 7;
	public Vector2f target = null;
	
	public EntityArrow(World world, Vector2f coords, String texture, float damage, float knockback, float[] size, Vector2f facing, Entity shooter)throws SlickException 
	{
		super(world, coords, texture);
		bb = new Rectangle(0, 0, size[0], size[1]);
		this.addFacing(facing);
		this.damage = damage;
		this.knockback = knockback;
		this.shooter = shooter;
		this.setLooking(facing);
		this.hasCollided = false;
	}
	
	public float getSpeed()
	{
		return 1.225F;
	}
	
	public void update(int delta)
	{
		Vector2f v = facing.copy().normalise().scale(this.getSpeed() * delta);
		
		if(timeAlive >= lifeTime)
		{
			if(this.target != null)
			{
				this.faceAt(target.copy());
				this.lookAt(target.copy());
				this.target = null;
			}
			if(!world.isEntityColliding(this, this.getBB(loc.copy())))
			{
				loc.add(v);
			}
			else 
			{
				this.hasCollided = true;
				this.setDead();
			}
		}
		else
			loc.add(v);
		
		timeAlive += delta;
	}
	
	public void setTarget(Vector2f t)
	{
		this.target = t.copy();
	}

	@Override
	public float getResistance() 
	{
		return 1.0F;
	}

	@Override
	public float getWeight() 
	{
		return 0;
	}

	@Override
	public float getMaxHealth() 
	{
		return 1337;
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
		if(timeAlive >= lifeTime && !this.hasCollided && !(entity instanceof EntityPlayer))
		{
			this.hasCollided = true;
			world.attemptAttack(this, this.getBB(this.getCoords()), this.getFacing().copy().normalise(), this.damage, this.knockback);
		}
	}

}

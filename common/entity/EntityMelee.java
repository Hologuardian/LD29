package holo.common.entity;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import holo.common.entity.ai.Path;
import holo.common.item.ITEMS;
import holo.common.tile.Tile;
import holo.common.world.World;

public class EntityMelee extends Entity
{
	public Shape bb;
	public float resistance;
	public float wieght;
	public float maxHealth;
	public float aggroRange;
	public float speed;
	public ITEMS weapon;
	public Path path;
	public Entity target;

	/**
	 * 
	 * @param world
	 * @param coords
	 * @param texture
	 * @param size
	 * @param aggroRange
	 * @param resistance
	 * @param wieght
	 * @param maxHealth
	 * @param speed
	 * @param weapon
	 * @throws SlickException
	 */
	public EntityMelee(World world, Vector2f coords, String texture, float[] size, float aggroRange, float resistance, float wieght, float maxHealth, float speed, ITEMS weapon) throws SlickException 
	{
		super(world, coords, texture);
		this.bb = new Rectangle(0, 0, size[0], size[1]);
		this.resistance = resistance;
		this.aggroRange = aggroRange;
		this.wieght = wieght;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.weapon = weapon;
		this.health = this.maxHealth;
	}

	public float getSpeed()
	{
		return this.speed;
	}

	public void update(int delta)
	{
		if (path == null)
			this.path = new Path(world, this.getCenterCoords(), this.getCenterCoords());

		EntityPlayer player = world.render.player;
		
		if(target == null && player != null)
		{
			this.getTarget(player);
		}

		if(target != null)
		{
			Vector2f point = path.getPath().get(0);
			
			if(point.distance(this.getCenterCoords()) <= Tile.size / 2 - bb.getBoundingCircleRadius() && path.getPath().size() > 1)
			{
				path.updatePath(this.getCenterCoords(), target.getCenterCoords());
				path.getPath().remove(0);
			}
			this.faceAt(point);
			this.lookAt(point);
		}

		if(this.health <= 0)
			this.setDead();
		
		Vector2f v = facing.copy().normalise().scale(this.getSpeed() * delta);
		
		if(knockback.length() > 0)
		{
			v.add(knockback);
			knockback.set(0, 0);
		}
		
		Vector2f xVec = new Vector2f(1, 0);
		Vector2f yVec = new Vector2f(0, 1);
		
		v.projectOntoUnit(xVec.copy(), xVec);
		v.projectOntoUnit(yVec.copy(), yVec);
		
		if(!world.isEntityColliding(this, this.getBB(loc.copy().add(v))))
			loc.add(v);
		else if(!world.isEntityColliding(this, this.getBB(loc.copy().add(xVec))))
			loc.add(xVec);
		else if(!world.isEntityColliding(this, this.getBB(loc.copy().add(yVec))))
			loc.add(yVec);
		
		if(this.lastAttackTimer > 0)
			this.lastAttackTimer -= delta;
	}

	public void getTarget(EntityPlayer player)
	{
		if(world.getDistanceToEntity(this.getCenterCoords(), player) <= aggroRange)
		{
			this.target = player;
			path.updatePath(this.getCenterCoords(), player.getCenterCoords());
		}
	}

	@Override
	public float getResistance() 
	{
		return this.resistance;
	}

	@Override
	public float getWeight() 
	{
		return this.wieght;
	}

	@Override
	public float getMaxHealth() 
	{
		return this.maxHealth;
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
		if(entity == this.target)
			weapon.getItem().onUse(this);
	}

}

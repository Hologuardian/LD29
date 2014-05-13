package holo.common.entity;

import holo.common.world.World;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity 
{
	public Image tex;
	public Vector2f loc;
	public Vector2f facing = new Vector2f(0, 0);
	public Vector2f looking = new Vector2f(0, 0);
	public World world;
	public float health;
	public float lastAttackTimer = 0;
	public Vector2f knockback = new Vector2f(0, 0);

	public Entity(World world, Vector2f coords, String texture) throws SlickException
	{
		tex = new Image(texture);
		loc = coords;
		this.world = world;
		this.health = this.getMaxHealth();
	}

	public void update(int delta)
	{
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

		Vector2f pos = loc.copy().add(v);

		v.projectOntoUnit(xVec.copy(), xVec);
		v.projectOntoUnit(yVec.copy(), yVec);

		if(pos.getX() >= 0 && (pos.getX() + getBB(pos).getWidth()) <= world.loadedMap.mapWidth
				&& pos.getY() >= 0 && (pos.getY() + getBB(pos).getHeight()) <= world.loadedMap.mapHeight)
		{
			if(!world.isEntityColliding(this, this.getBB(pos)))
				loc.add(v);
			else if(!world.isEntityColliding(this, this.getBB(loc.copy().add(xVec))))
				loc.add(xVec);
			else if(!world.isEntityColliding(this, this.getBB(loc.copy().add(yVec))))
				loc.add(yVec);
		}

		if(this.lastAttackTimer > 0)
			this.lastAttackTimer -= delta;
	}

	public void takeDamage(Entity attackingEntity, Vector2f direction, float damage, float knockbackStrength)
	{
		this.health -= (damage * (1 - this.getResistance()));
		this.knockback.add(direction).scale(knockbackStrength / this.getWeight());
	}

	public abstract float getResistance();
	public abstract float getWeight();

	public float getCurrentHealth()
	{
		return this.health;
	}

	public abstract float getMaxHealth();

	public float getSpeed()
	{
		return 0.25F;
	}

	public float getWidth()
	{
		return this.getBB(loc).getWidth();
	}

	public float getHeight()
	{
		return this.getBB(loc).getHeight();
	}

	public void addFacing(Vector2f dir)
	{
		this.facing.add(dir);
	}

	public void setFacing(Vector2f dir)
	{
		this.facing.set(dir);
	}

	public Vector2f getFacing()
	{
		return this.facing;
	}

	public void faceAt(Vector2f v)
	{
		facing.set(this.getCenterCoords().copy().negate());
		facing.add(v.copy());
	}

	public void setLooking(Vector2f v)
	{
		looking.set(v);
	}

	public void lookAt(Vector2f v)
	{
		looking.set(this.getCenterCoords().copy().negate());
		looking.add(v.copy());
	}

	public Vector2f getLooking()
	{
		return looking;
	}

	public Vector2f getCoords()
	{
		return loc;
	}

	public Vector2f getCenterCoords()
	{
		return new Vector2f(getBB(loc).getCenter());
	}

	public abstract Shape getBB(Vector2f location);

	public Image getTexture()
	{
		return tex;
	}

	public void setAttackTimer(float t)
	{
		this.lastAttackTimer = t;
	}

	public void setDead()
	{
		this.world.removeEntity(this);
	}

	public abstract void onCollideWithEntity(Entity entity);
}

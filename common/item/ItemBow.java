package holo.common.item;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import holo.common.entity.Entity;
import holo.common.entity.EntityArrow;
import holo.common.entity.EntityPlayer;

public class ItemBow extends ItemWeapon
{
	public String arrowTexture;
	public float damage;
	public float knockback;
	public float[] arrowSize;
	public float delay;
	public Shape bb;
	
	/**
	 * 
	 * @param texLoc
	 * @param arrowTexture
	 * @param delay
	 * @param damage
	 * @param knockback
	 * @param arrowSize
	 * @param bowSize
	 */
	public ItemBow(String[] texLoc, String arrowTexture, float delay, float damage, float knockback, float[] arrowSize, float[] bowSize) 
	{
		super(texLoc);
		bb = new Rectangle(0, 0, bowSize[0], bowSize[1]);
		this.arrowTexture = arrowTexture;
		this.damage = damage;
		this.knockback = knockback;
		this.arrowSize = arrowSize;
		this.delay = delay;
	}

	@Override
	public void attack(Entity entity) 
	{
		float r = entity.getBB(entity.getCoords()).getBoundingCircleRadius();
		Vector2f facing = entity.getLooking().copy().normalise().scale(r);
		Vector2f loc = new Vector2f(entity.getCoords().copy().getX()  + facing.copy().getX() + facing.copy().getPerpendicular().getX()
				, entity.getCoords().copy().getY()  + facing.copy().getY() + facing.copy().getPerpendicular().getY());
		EntityArrow arrow;
		try 
		{
			arrow = new EntityArrow(entity.world, loc, arrowTexture, damage, knockback, arrowSize, entity.getLooking().copy(), entity);
			if(entity instanceof EntityPlayer)
			{
				arrow.setTarget(((EntityPlayer)entity).mouse);
			}
			entity.world.addEntity(arrow);
		}
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}

	@Override
	public float getAttackDelay() 
	{
		return delay;
	}

}

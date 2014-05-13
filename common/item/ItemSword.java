package holo.common.item;

import holo.common.entity.Entity;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class ItemSword extends ItemWeapon
{
	public float damage;
	public float knockbackStrength;
	public Shape bb;
	public float attackTime;
	
	/**
	 * 
	 * @param texLoc
	 * @param damage
	 * @param knockbackStrength
	 * @param attackTimer
	 * @param attackArea
	 */
	public ItemSword(String[] texLoc, float damage, float knockbackStrength, float attackTimer, float[] attackArea)
	{
		super(texLoc);
		this.damage = damage;
		this.knockbackStrength = knockbackStrength;
		attackTime = attackTimer;
		bb = new Rectangle(0, 0, attackArea[0], attackArea[1]);
	}
	
	public Shape getAlignedBB(Vector2f loc, Vector2f facing)
	{
		Shape newBB = new Rectangle(loc.copy().getX() - bb.getCenterX() + facing.copy().getX() + facing.copy().getPerpendicular().getX()
				, loc.copy().getY() - bb.getCenterY() + facing.copy().getY() + facing.copy().getPerpendicular().getY()
				, bb.getWidth(), bb.getHeight());
		newBB.transform(Transform.createRotateTransform((float) (facing.getTheta() / 360)));
		return newBB;
	}

	@Override
	public void attack(Entity entity) 
	{
		float r = entity.getBB(entity.getCoords()).getBoundingCircleRadius();
		Shape attackBB = getAlignedBB(entity.getCenterCoords(), entity.getLooking().copy().normalise().scale(r));
		entity.world.attemptAttack(entity, attackBB, entity.getLooking().copy().normalise(), this.damage, this.knockbackStrength);
	}

	@Override
	public float getAttackDelay() 
	{
		return attackTime;
	}
}

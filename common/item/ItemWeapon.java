package holo.common.item;

import holo.common.entity.Entity;

public abstract class ItemWeapon extends Item
{
	public ItemWeapon(String[] texLoc)
	{
		super(texLoc);
	}
	
	public void onUse(Entity entity)
	{
		if(entity.lastAttackTimer <= 0)
		{
			this.attack(entity);
			entity.setAttackTimer(this.getAttackDelay());
		}
	}
	
	public abstract void attack(Entity entity);
	public abstract float getAttackDelay();
}

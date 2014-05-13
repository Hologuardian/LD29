package holo.common.entity;

import holo.common.item.ITEMS;
import holo.common.world.World;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EntityGenerator 
{
	public static Entity genEntity(World world, int ID, Vector2f loc) throws SlickException
	{
		switch(ID)
		{
		case 0:
			return new EntityDummy(world, loc, "res/textures/entity/TestEntity.png");
		case 1:
			return new EntityMelee(world, loc, "res/textures/entity/SlowEnemy.png", new float[] {32, 32}, 128, 0.6F, 320.0F, 30.0F, 0.08F, ITEMS.MOLTENBLADE);
		case 2:
			return new EntityMelee(world, loc, "res/textures/entity/NormalEnemy.png", new float[] {32, 32}, 160, 0.0F, 140.0F, 10.0F, 0.15F, ITEMS.SHARPBLADE);
		case 3:
			return new EntityMelee(world, loc, "res/textures/entity/FastEnemy.png", new float[] {32, 32}, 256, 0.0F, 30.0F, 5.0F, 0.27F, ITEMS.DULLBLADE);
		}
		
		return null;
	}
}

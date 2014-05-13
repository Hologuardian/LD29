package holo.common.tile;

import holo.common.world.World;

import org.newdawn.slick.geom.Vector2f;

public class DecorationGenerator 
{
	public static Decoration genDecoration(World world, int ID, Vector2f loc)
	{
		switch(ID)
		{
		case 0:
			return new Decoration(world, loc, DecorationHelper.brazier, new float[]{24, 24}, 0.0F, false, 140, 140);
		case 1:
			return new Decoration(world, loc, DecorationHelper.arcaneLamp, new float[]{24, 24}, 0.0F, false, 140, 140);
		}
		
		return null;
	}
}

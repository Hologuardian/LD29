package holo.common.item;

import holo.common.entity.Entity;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Item 
{
	public Image[] textures;
	
	public Item (String[] texLoc)
	{
		textures = new Image[texLoc.length];
		for(int i = 0; i < texLoc.length; i++)
		{
			try
			{
				textures[i] = new Image(texLoc[i]);
			}
			catch(SlickException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public abstract void onUse(Entity entity);
	
	public Image[] getTextures()
	{
		return textures;
	}
}

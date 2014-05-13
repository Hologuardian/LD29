package holo.common.tile;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class AnimatedTile extends Tile
{
	public Image[] frames;
	public float frameTime;
	
	public AnimatedTile(Vector2f coords, String[] texture, boolean collidable, float frameTime) throws SlickException 
	{
		super(coords, texture[0], collidable);
		
		frames = new Image[texture.length];
		this.frameTime = frameTime;
		for(int i = 0; i < texture.length; ++i)
		{
			frames[i] = new Image(texture[i]);
		}
	}

	public Image getFrameFromTime(long time)
	{
		float n = frames.length * frameTime;
		return frames[(int)((time % n) / frameTime)].copy();
	}
}

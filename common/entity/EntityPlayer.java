package holo.common.entity;

import holo.common.entity.player.Inventory;
import holo.common.item.ITEMS;
import holo.common.render.GameRender;
import holo.common.tile.InteractiveTile;
import holo.common.tile.LightSource;
import holo.common.world.World;

import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class EntityPlayer extends Entity
{
	public Rectangle bb = new Rectangle(0, 0, 32, 32);
	public final LightSource light;
	public float lightStrength = 0;
	public Vector2f mouse = new Vector2f(0, 0);
	public Inventory inventory;
	public InteractiveTile hoverTile = null;
	
	public EntityPlayer(World world, Vector2f coords, String texture) throws SlickException 
	{
		super(world, coords, texture);
		this.light = new LightSource(coords, lightStrength, new Color(1.0F, 0.0F, 1.0F));
		inventory = new Inventory(4);
		this.inventory.putItem(ITEMS.DULLBLADE);
		this.inventory.putItem(ITEMS.CROSSBOW);
		this.inventory.putItem(ITEMS.BRIGHTLANTERN);
	}
	
	public void update(int delta)
	{
		super.update(delta);
		if(!(inventory.getSelectedSlot() == 2))
		{
			lightStrength = 0;
		}
		else
		{
			inventory.getSelectedItem().getItem().onUse(this);
		}
		light.setStrength(lightStrength);
		if(!world.lightSources.contains(light))
			world.addLight(light);
		world.updateLighting();
		
		hoverTile = world.canInteract(this);
	}
	
	public void mousePressed(int button, int x, int y) 
	{
		if(this.inventory.getSelectedItem() != null)
		{
			this.inventory.getSelectedItem().getItem().onUse(this);
		}
	}
	
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
		mouse = new Vector2f(newx + world.render.cLoc.copy().getX() - GameRender.cameraWidth / 2, newy + world.render.cLoc.copy().getY() - GameRender.cameraHeight / 2);
		this.lookAt(mouse);
	}
	
	public void onKeyPressed(int key)
	{
		if(key == Input.KEY_A)
			this.addFacing(new Vector2f(-1.0F, 0));
		else if(key == Input.KEY_D)
			this.addFacing(new Vector2f(1.0F, 0));
		else if(key == Input.KEY_W)
			this.addFacing(new Vector2f(0, -1.0F));
		else if(key == Input.KEY_S)
			this.addFacing(new Vector2f(0, 1.0F));
		else if(key == Input.KEY_1)
		{
			this.inventory.selectSlot(0);
		}
		else if(key == Input.KEY_2)
		{
			this.inventory.selectSlot(1);
		}
		else if(key == Input.KEY_3)
		{
			this.inventory.selectSlot(2);
		}
		
		if(key == Input.KEY_E && hoverTile != null)
			hoverTile.interact(this);
	}
	
	public void onKeyReleased(int key)
	{
		if(key == Input.KEY_A)
			this.addFacing(new Vector2f(1.0F, 0));
		else if(key == Input.KEY_D)
			this.addFacing(new Vector2f(-1.0F, 0));
		else if(key == Input.KEY_W)
			this.addFacing(new Vector2f(0, 1.0F));
		else if(key == Input.KEY_S)
			this.addFacing(new Vector2f(0, -1.0F));
	}
	
	public float getSpeed()
	{
		return 0.3F;
	}

	@Override
	public Shape getBB(Vector2f location) 
	{
		bb.setLocation(location);
		return bb;
	}

	@Override
	public float getMaxHealth() 
	{
		return 40.0F;
	}

	@Override
	public float getResistance() 
	{
		return 0;
	}

	@Override
	public float getWeight() 
	{
		return 80;
	}

	@Override
	public void onCollideWithEntity(Entity entity) 
	{
		
	}
}

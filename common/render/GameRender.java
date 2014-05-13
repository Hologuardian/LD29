package holo.common.render;

import holo.common.entity.*;
import holo.common.item.*;
import holo.common.tile.*;
import holo.common.world.World;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.util.FontUtils;

public class GameRender 
{
	public Vector2f cLoc = new Vector2f(0, 0);
	public int tileSize = Tile.size;
	public ArrayList<Tile> tilesToRender = new ArrayList<Tile>();
	public ArrayList<Entity> entitiesToRender = new ArrayList<Entity>();
	public ArrayList<Decoration> decorationsToRender = new ArrayList<Decoration>();
	public static int cameraWidth = 1024;
	public static int cameraHeight = 800;
	public Image light;
	public EntityPlayer player = null;
	
	public GameRender (Image lighting)
	{
		light = lighting;
	}
	
	public void render(GameContainer gc, Graphics g, World world)
	{
		g.clear();
		long timer = gc.getTime();
		
		for(Tile tile : tilesToRender)
		{
			if (tile != null) 
			{
				if(tile instanceof AnimatedTile)
				{
					renderAnimatedTile(g, (AnimatedTile) tile, timer);
				}
				else
					renderTile(g, tile);
			}
		}
		
		for(Decoration d : decorationsToRender)
		{
			renderDecoration(g, d, timer);
		}
				
		for(Entity ent : entitiesToRender)
		{
			if(ent != null)
			{
				renderEntity(g, ent);
				if(ent instanceof EntityMelee)
				{
					renderItem(g, ent, ((EntityMelee)ent).weapon.getItem());
				}
				else if (ent instanceof EntityPlayer)
				{
					EntityPlayer p = (EntityPlayer)ent;
					if(p.inventory.getSelectedItem() != null)
					{
						renderItem(g, p, p.inventory.getSelectedItem().getItem());
					}
				}
			}
		}
		
		renderLighting(g, world);
		
		
		
		if (player != null)
		{
			cLoc = player.getCenterCoords();
			Font font = gc.getDefaultFont();
			if(player.hoverTile != null)
			{
				FontUtils.drawCenter(font, player.hoverTile.getDisplayMessage(), cameraWidth / 2, 
						cameraHeight * 3 / 4, font.getWidth(player.hoverTile.getDisplayMessage()));
			}
			String s = "Health: " + player.health;
			FontUtils.drawCenter(font, s, cameraWidth - font.getWidth(s), 0, font.getWidth(s));
		}
	}
	
	public void renderLighting(Graphics g, World world)
	{
		Color[][] light = world.getLightGrid();
		int cx = (int)cLoc.x;
		int cy = (int)cLoc.y;
		
		for(int j = 0; j < Math.min(light.length, (cx + cameraWidth / 2 + LightSource.size) / LightSource.size); j++)
		{
			for(int i = 0; i < Math.min(light[j].length, (cy + cameraHeight / 2 + LightSource.size) / LightSource.size); i++)
			{
				Color c = light[j][i];
				g.setColor(c);
				float x = j * LightSource.size - cLoc.x + cameraWidth / 2;
				float y = i * LightSource.size - cLoc.y + cameraHeight / 2;
				Image image = this.light.getSubImage(0, 0, LightSource.size, LightSource.size);
				g.drawImage(image, x, y, c);
			}
		}
	}
	
	public void updateCamera(float x, float y)
	{
		cLoc.set(x, y);
	}
	
	public void updateTileRenderList(World world)
	{
		tilesToRender.clear();
		for(Tile tile : world.getTileList())
		{
			Vector2f c = tile.getCoords().copy().scale(64);
			if((c.x >= (cLoc.x - (cameraWidth / 2) - tileSize) || c.x <= (cLoc.x + (cameraWidth / 2) + tileSize))
					|| (c.y >= (cLoc.y - (cameraHeight / 2) - tileSize) || c.y <= (cLoc.y + (cameraHeight / 2) + tileSize)))
			{
				tilesToRender.add(tile);
			}
		}
	}
	
	public void updateEntityRenderList(World world)
	{
		entitiesToRender.clear();
		for(Entity ent : world.getEntityList())
		{
			Vector2f c = ent.getCoords();
			if((c.x >= (cLoc.x - (cameraWidth / 2) - ent.getWidth()) || c.x <= (cLoc.x + (cameraWidth / 2) + ent.getWidth()))
					|| (c.y >= (cLoc.y - (cameraHeight / 2) - ent.getHeight()) || c.y <= (cLoc.y + (cameraHeight / 2) + ent.getHeight())))
			{
				entitiesToRender.add(ent);
			}
		}
	}
	
	public void updateDecorRenderList(World world)
	{
		decorationsToRender.clear();
		for(Decoration decoration : world.getDecorList())
		{
			Vector2f c = decoration.getCoords();
			if((c.x >= (cLoc.x - (cameraWidth / 2) - decoration.getBB().getWidth()) || c.x <= (cLoc.x + (cameraWidth / 2) + decoration.getBB().getWidth()))
					|| (c.y >= (cLoc.y - (cameraHeight / 2) - decoration.getBB().getHeight()) || c.y <= (cLoc.y + (cameraHeight / 2) + decoration.getBB().getHeight())))
			{
				decorationsToRender.add(decoration);
			}
		}
	}
	
	public void renderTile(Graphics g, Tile t)
	{
		float x = t.getCoords().copy().scale(64).x - cLoc.x + cameraWidth / 2;
		float y = t.getCoords().copy().scale(64).y - cLoc.y + cameraHeight / 2;
		g.drawImage(t.getTexture(), x, y);
	}
	
	public void renderAnimatedTile(Graphics g, AnimatedTile t, long timer)
	{
		float x = t.getCoords().copy().scale(64).x - cLoc.x + cameraWidth / 2;
		float y = t.getCoords().copy().scale(64).y - cLoc.y + cameraHeight / 2;
		Image img = t.getFrameFromTime(timer);
		g.drawImage(img, x, y);
	}
	
	public void renderEntity(Graphics g, Entity ent)
	{
		float x = ent.getCoords().x - cLoc.x + cameraWidth / 2;
		float y = ent.getCoords().y - cLoc.y + cameraHeight / 2;
		Image img = ent.getTexture().copy();
		img.rotate((float) ent.getLooking().getTheta());
		g.drawImage(img, x, y);
	}
	
	public void renderItem(Graphics g, Entity entity, Item item)
	{
		if(item instanceof ItemSword || item instanceof ItemBow && entity != null)
		{
			float r = entity.getBB(entity.getCoords()).getBoundingCircleRadius();
			Vector2f looking = entity.getLooking().copy().normalise().scale(r);
			int n = ((int)((item.getTextures().length - 1) *( 1 - (entity.lastAttackTimer / ((ItemWeapon)item).getAttackDelay()))));
			
			
			if(entity.lastAttackTimer / ((ItemWeapon)item).getAttackDelay() <= 0)
				n = 0;
			
			if(n < 0)
				n = 0;
			Image img = item.getTextures()[n].copy();
			Shape bb = null;
			if(item instanceof ItemSword)
				bb = ((ItemSword) item).bb;
			else if (item instanceof ItemBow)
				bb = ((ItemBow) item).bb;
				
			
			img.rotate((float) entity.getLooking().getTheta());
			
			g.drawImage(img, entity.getCenterCoords().copy().getX() - 
					bb.getCenterX() + looking.copy().getX() + looking.copy().getPerpendicular().getX()  - cLoc.x + cameraWidth / 2, 
					entity.getCenterCoords().copy().getY() - 
					bb.getCenterY() + looking.copy().getY() + looking.copy().getPerpendicular().getY() - cLoc.y + cameraHeight / 2);
		}
		else
		{
			float r = entity.getBB(entity.getCoords()).getBoundingCircleRadius();
			Vector2f looking = entity.getLooking().copy().normalise().scale(r);
			Image img = item.getTextures()[0].copy();
			Shape bb = new Rectangle(0, 0, img.getWidth(), img.getHeight());
				
			
			img.rotate((float) entity.getLooking().getTheta());
			
			g.drawImage(img, entity.getCenterCoords().copy().getX() - 
					bb.getCenterX() + looking.copy().getX() + looking.copy().getPerpendicular().getX()  - cLoc.x + cameraWidth / 2, 
					entity.getCenterCoords().copy().getY() - 
					bb.getCenterY() + looking.copy().getY() + looking.copy().getPerpendicular().getY() - cLoc.y + cameraHeight / 2);
		}
	}
	
	public void renderDecoration(Graphics g, Decoration d, long timer)
	{
		int animTime = d.getNumFrames() * d.getFrameTime();
		int frame = (int)((timer % animTime) / d.getFrameTime());
		Image img = d.getFrame(frame).copy();
		img.rotate(d.theta);
		g.drawImage(img, d.getCoords().getX() - cLoc.x + cameraWidth / 2, d.getCoords().getY() - cLoc.y + cameraHeight / 2);
	}
}

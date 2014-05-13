package holo.common.world;

import holo.common.Main;
import holo.common.entity.Entity;
import holo.common.entity.EntityMelee;
import holo.common.entity.EntityPlayer;
import holo.common.render.GameRender;
import holo.common.tile.Decoration;
import holo.common.tile.InteractiveTile;
import holo.common.tile.LightSource;
import holo.common.tile.Tile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class World 
{
	public Tile[][] tileGrid;
	public ArrayList<LightSource> lightSources = new ArrayList<LightSource>();
	public Color[][] lightLevelGrid;
	public ArrayList<Tile> tileList = new ArrayList<Tile>();
	public ArrayList<InteractiveTile> interactiveList = new ArrayList<InteractiveTile>();
	public ArrayList<Decoration> decorList = new ArrayList<Decoration>();
	public ArrayList<Entity> entityList = new ArrayList<Entity>();
	public ArrayList<Entity> entityRemoveList = new ArrayList<Entity>();
	public ArrayList<Entity> entityAddList = new ArrayList<Entity>();
	public Map loadedMap;
	public HashMap<String, Map> maps = new HashMap<String, Map>();
	public ArrayList<Shape> bbList = new ArrayList<Shape>();
	public GameRender render;
	public float score = 0;
	public StateBasedGame game;

	public World(GameRender render, StateBasedGame game)
	{
		this.render = render;
		this.game = game;
	}

	public void update(int delta)
	{

		if(!entityAddList.isEmpty())
		{
			for(Entity ent : entityAddList)
			{
				entityList.add(ent);
			}

			entityAddList.clear();
		}

		if(!entityRemoveList.isEmpty())
		{
			for(Entity ent : entityRemoveList)
			{
				if(ent instanceof EntityPlayer)
					this.lose();
				entityList.remove(ent);
			}
			entityRemoveList.clear();
		}

		for(Entity ent : entityList)
		{
			ent.update(delta);
		}
		
		for(Decoration d : decorList)
		{
			d.update(delta);
		}
		
		render.updateEntityRenderList(this);
	}
	
	public InteractiveTile canInteract(EntityPlayer player)
	{
		InteractiveTile tile = null;
		for(InteractiveTile t : interactiveList)
		{
			if(t.getBB().intersects(player.getBB(player.getCoords())))
			{
				tile = t;
			}
		}
		
		return tile;
	}
	
	public float getDistanceToEntity(Vector2f v, Entity entity)
	{
		return v.distance(entity.getCenterCoords());
	}
	
	public float getDistanceSquaredToEntity(Vector2f v, Entity entity)
	{
		return v.distanceSquared(entity.getCenterCoords());
	}

	public void attemptAttack(Entity attackingEntity, Shape attackBB, Vector2f direction, float damage, float knockbackStrength)
	{
		for(Entity ent : entityList)
		{
			if(attackBB.intersects(ent.getBB(ent.getCoords())) && ent != attackingEntity)
			{
				ent.takeDamage(attackingEntity, direction, damage, knockbackStrength);
			}
		}
	}

	public void addEntity(Entity ent)
	{
		entityAddList.add(ent);
		if(ent instanceof EntityPlayer)
			render.player = (EntityPlayer) ent;
	}

	public void removeEntity(Entity ent)
	{
		entityRemoveList.add(ent);
		if(ent instanceof EntityMelee)
			this.score += 100;
	}

	public ArrayList<Entity> getEntityList()
	{
		return entityList;
	}

	public ArrayList<Tile> getTileList()
	{
		return tileList;
	}

	public ArrayList<Decoration> getDecorList()
	{
		return decorList;
	}

	public boolean isBBColliding(Shape bb)
	{
		for(Shape r : bbList)
		{
			if(bb.intersects(r))
				return true;
		}
		return false;
	}

	public boolean isEntityColliding(Entity ent, Shape bb)
	{
		for(Entity e : entityList)
		{
			if(e != ent && bb.intersects(e.getBB(e.getCoords())))
			{
				ent.onCollideWithEntity(e);
				e.onCollideWithEntity(ent);
				return true;
			}
		}
		return isBBColliding(bb);
	}

	public void addLight(LightSource l)
	{
		lightSources.add(l);
		updateLighting();
	}

	public Color[][] getLightGrid()
	{
		return lightLevelGrid;
	}

	public void updateLighting()
	{
		Color col = new Color(1.0F, 1.0F, 1.0F, 1.0F);
		for(int i = 0; i < lightLevelGrid.length; i++)
		{
			Arrays.fill(lightLevelGrid[i], col);
		}

		for(LightSource l : lightSources)
		{
			Vector2f c = l.getCoords();
			int x = (int)c.x / LightSource.size;
			int y = (int)c.y / LightSource.size;
			recursiveLight(l.getStrength(), new int[] {x, y}, l.getColor());
		}
	}

	public void recursiveLight(float level, int[] loc, Color color)
	{
		if(level <= 0)
			return;
		int x = loc[0];
		int y = loc[1];
		if(x < 0 || x >= lightLevelGrid.length || y < 0 || y >= lightLevelGrid[x].length)
			return;




		if(lightLevelGrid[x][y].a > 1 - (Math.min(level, LightSource.maxStrength) / LightSource.maxStrength))
		{
			lightLevelGrid[x][y] = new Color(color.r, color.g, color.b, 1 - (Math.min(level, LightSource.maxStrength) / LightSource.maxStrength));
		}
		else return;


		int[] loc1 = new int[] {x + 1, y};
		int[] loc2 = new int[] {x - 1, y};
		int[] loc3 = new int[] {x, y + 1};
		int[] loc4 = new int[] {x, y - 1};
		recursiveLight(level - LightSource.size, loc1, color);
		recursiveLight(level - LightSource.size, loc2, color);
		recursiveLight(level - LightSource.size, loc3, color);
		recursiveLight(level - LightSource.size, loc4, color);

	}
	
	public void changeMapForceSpawn(String mapName, Vector2f spawn)
	{
		loadMap(maps.get(mapName));
		if(render.player != null)
		{
			render.player.loc = spawn.copy();
		}
	}

	public void changeMap(String mapName)
	{
		loadMap(maps.get(mapName));
		if(render.player != null)
		{
			render.player.loc = new Vector2f(loadedMap.spawnX, loadedMap.spawnY);
		}
	}

	public void loadMap(Map map)
	{
		loadedMap = map;
		tileGrid = map.getTileGrid();
		lightSources = map.getLightSources();
		lightLevelGrid = new Color[map.mapWidth / LightSource.size][map.mapHeight / LightSource.size];
		this.interactiveList = map.interactionList;
		decorList = map.decorations;
		bbList.clear();
		updateDecorations();
		updateLighting();
		updateTileList();
		for(Entity ent : map.entityList)
		{
			this.addEntity(ent);
		}
	}

	public void updateTileList()
	{
		tileList.clear();
		for(int i = 0; i < tileGrid.length; i++)
		{
			for(int j = 0; j < tileGrid[i].length; j++)
			{
				Tile t = tileGrid[i][j];
				if(t == null)
				{
					//					System.out.println(i + " " + j + " Nope");
				}
				tileList.add(t);
				if (t.collidable)
				{
					bbList.add(t.getBB());
				}
			}
		}

		render.updateTileRenderList(this);
	}


	public void updateDecorations()
	{
		for(Decoration d : decorList)
		{
			if(d.collidable())
				bbList.add(d.getBB());
		}

		render.updateDecorRenderList(this);
	}
	public void load()
	{

		Scanner sc = null;
		try
		{
			File file = new File("src/res/maps/Maps");
			if (!file.exists())
				return;

			sc = new Scanner(file);

			while(sc.hasNext())
			{
				String name = sc.next();
				maps.put(name, new Map(name));
			}

			sc.close();
		}
		catch (IOException eio)
		{
			eio.printStackTrace();
		}
	}

	public void win() 
	{
		this.game.enterState(Main.victoryStateID);
		Main.score = this.score;
	}

	public void lose() 
	{
		this.game.enterState(Main.gameOverStateID);
		Main.score = this.score;
	}
}

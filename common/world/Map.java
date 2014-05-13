package holo.common.world;

import holo.common.entity.Entity;
import holo.common.entity.EntityGenerator;
import holo.common.states.GameState;
import holo.common.tile.Decoration;
import holo.common.tile.DecorationGenerator;
import holo.common.tile.InteractiveTile;
import holo.common.tile.LightSource;
import holo.common.tile.Tile;
import holo.common.tile.TileGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Map 
{
	public Tile[][] tileGrid;
	public ArrayList<LightSource> lightSources = new ArrayList<LightSource>();
	public ArrayList<Decoration> decorations = new ArrayList<Decoration>();
	public ArrayList<Entity> entityList = new ArrayList<Entity>();
	public ArrayList<InteractiveTile> interactionList = new ArrayList<InteractiveTile>();
	public int tileWidth;
	public int tileHeight;
	public int mapWidth;
	public int mapHeight;
	public int spawnX;
	public int spawnY;
	
	public Map(String file)
	{
		load(file);
	}
	
	public Tile[][] getTileGrid()
	{
		return tileGrid;
	}
	
	public ArrayList<LightSource> getLightSources()
	{
		return lightSources;
	}
	
	public void load(String fileLoc)
	{
		Scanner sc = null;
		try
		{
			File file = new File("src/res/maps/" + fileLoc);
			if (!file.exists())
			{
				System.out.println("No File! " + fileLoc);
				return;
			}
			
			sc = new Scanner(file);
			
			{
				tileWidth = sc.nextInt();
				tileHeight = sc.nextInt();
				mapWidth = tileWidth * Tile.size;
				mapHeight = tileHeight * Tile.size;
				spawnX = sc.nextInt();
				spawnY = sc.nextInt();
//				System.out.println(tileWidth + " " + tileHeight);
				
				int numEntities = sc.nextInt();
				int numDecorations = sc.nextInt();
				tileGrid = new Tile[tileHeight][tileWidth];
				for(int j = 0; j < tileHeight; j++)
				{
					for(int i = 0; i < tileWidth; i++)
					{
						int id = sc.nextInt();
						Tile t = TileGenerator.genTile(this, id, new Vector2f(i, j));
						tileGrid[j][i] = t;
						if(t == null)
						{
							System.out.println(i + " " + j + " Nope");
						}
						else if (t instanceof InteractiveTile)
							interactionList.add((InteractiveTile) t);
					}
				}
				
				for(int n = 0; n < numEntities; n++)
				{
					int id = sc.nextInt();
					float x = sc.nextFloat();
					float y = sc.nextFloat();
					entityList.add(EntityGenerator.genEntity(GameState.world, id, new Vector2f(x, y)));
				}
				
				for(int n = 0; n < numDecorations; n++)
				{
					int id = sc.nextInt();
					float x = sc.nextFloat();
					float y = sc.nextFloat();
					
					Decoration d = DecorationGenerator.genDecoration(GameState.world, id, new Vector2f(x, y));
					decorations.add(d);
					lightSources.add(d.light);
				}
			}
			
			sc.close();
		}
		catch (IOException eio)
		{
			eio.printStackTrace();
		} 
		catch (SlickException e) 
		{
			e.printStackTrace();
		}
	}
}

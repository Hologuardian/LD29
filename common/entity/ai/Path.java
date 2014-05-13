package holo.common.entity.ai;

import holo.common.tile.Tile;
import holo.common.world.World;

import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.geom.Vector2f;

public class Path 
{
	public World world;
	public Tile start;
	public Tile end;
	public ArrayList<Vector2f> path = new ArrayList<Vector2f>();

	public Path(World world, Vector2f start, Vector2f end)
	{
		this.world = world;
		this.start = getTileFromVec(start);
		this.end = getTileFromVec(end);
		for(Tile t : this.GeneratePath())
		{
			Vector2f v = new Vector2f(t.getCoords().getX() * Tile.size + Tile.size / 2, t.getCoords().getY() * Tile.size + Tile.size / 2);
			path.add(v);
		}
		path.add(end);
	}

	public Path(World world, Tile start, Tile end)
	{
		this.world = world;
		this.start = start;
		this.end = end;
		for(Tile t : this.GeneratePath())
		{
			Vector2f v = new Vector2f(t.getCoords().getX() * Tile.size + Tile.size / 2, t.getCoords().getY() * Tile.size + Tile.size / 2);
			path.add(v);
		}
	}
	
	public void updatePath(Vector2f start, Vector2f end)
	{
		this.start = getTileFromVec(start);
		this.end = getTileFromVec(end);
		
		path.clear();
		
		for(Tile t : this.GeneratePath())
		{
			Vector2f v = new Vector2f(t.getCoords().getX() * Tile.size + Tile.size / 2, t.getCoords().getY() * Tile.size + Tile.size / 2);
			path.add(v);
		}
		
		path.add(end);
	}
	
	public void updatePath(Tile start, Tile end)
	{
		this.start = start;
		this.end = end;
		
		path.clear();
		
		for(Tile t : this.GeneratePath())
		{
			Vector2f v = new Vector2f(t.getCoords().getX() * Tile.size + Tile.size / 2, t.getCoords().getY() * Tile.size + Tile.size / 2);
			path.add(v);
		}
	}
	
	public ArrayList<Vector2f> getPath()
	{
		return path;
	}

	public Tile getTileFromVec(Vector2f v)
	{
		int x = (int) (v.getX() / Tile.size);
		int y = (int) (v.getY() / Tile.size);
		
		if(x < 0 || x > world.loadedMap.tileWidth - 1 || y < 0 || y > world.loadedMap.tileHeight - 1)
			return null;
		
		return world.tileGrid[y][x];
	}

	public ArrayList<Tile> getValidEdge(Tile tile)
	{
		int x = (int) tile.getCoords().getX();
		int y = (int) tile.getCoords().getY();

		ArrayList<Tile> tiles = new ArrayList<Tile>();

		if(y > 0 && !world.tileGrid[y - 1][x].collidable)
			tiles.add(world.tileGrid[y - 1][x]);
		if(y < world.loadedMap.tileHeight - 1 && !world.tileGrid[y + 1][x].collidable)
			tiles.add(world.tileGrid[y + 1][x]);
		if(x > 0 && !world.tileGrid[y][x - 1].collidable)
			tiles.add(world.tileGrid[y][x - 1]);
		if(x < world.loadedMap.tileWidth - 1 && !world.tileGrid[y][x + 1].collidable)
			tiles.add(world.tileGrid[y][x + 1]);

		return tiles;
	}

	public ArrayList<Tile> GeneratePath()
	{
		ArrayList<Tile> revPath = aStarSearch(start, end);
		ArrayList<Tile> path = new ArrayList<Tile>();
		for(Tile t : revPath)
			path.add(path.size(), t);
			
		return path;//recursiveSearch(new ArrayList<Tile>(), end, start, 1);
	}
	
//	public ArrayList<Tile> recursiveSearch(ArrayList<Tile> visited, Tile end, Tile current, int length)
//	{
////		ArrayList<Tile> newVisited = new ArrayList<Tile>();
////		for(Tile t: visited)
////			System.out.print("(" + t.getCoords().getX() + " " + t.getCoords().getY() + ") ");
////		System.out.println("Found World");
////		newVisited.addAll(visited);
//		visited.add(current);
//
//		ArrayList<Tile> tiles = getValidEdge(current);
//
//		tiles.removeAll(visited);
//
//		ArrayList<Tile> shortest = new ArrayList<Tile>();
//		shortest.add(current);
//
//		if(!tiles.isEmpty())
//		{
//			for(Tile n : tiles)
//			{
//				ArrayList<Tile> set = recursiveSearch(visited, end, n, length + 1);
//				if(set.contains(end))
//				{
//					return set;
////					if((shortest.size() == 1 && shortest.contains(current)) || set.size() < shortest.size() - 1)
////					{
////						shortest.clear();
////						shortest.add(current);
////						shortest.addAll(set);
////					}
//				}
//			}
//		}
//
//		return shortest;
//	}
	
	public ArrayList<Tile> aStarSearch(Tile start, Tile end)
	{
		ArrayList<Tile> closedSet = new ArrayList<Tile>();
		ArrayList<Tile> openSet = new ArrayList<Tile>();
		openSet.add(start);
		HashMap<Tile, Tile> cameFrom = new HashMap<Tile, Tile>();
		HashMap<Tile, Integer> gScore = new HashMap<Tile, Integer>();
		gScore.put(start, 0);
		
		while(!openSet.isEmpty())
		{
			Tile current = null;
			for(Tile t : openSet)
			{
				if(current == null || gScore.get(t) < gScore.get(current))
				{
					current = t;
				}
			}
			
			if(current == end)
				return reconstructPath(cameFrom, current);
			
			openSet.remove(current);
			closedSet.add(current);
			
			for(Tile n : getValidEdge(current))
			{
				if(closedSet.contains(n))
					continue;
				
				int tentativeGScore = gScore.get(current) + 1;
				
				if(!openSet.contains(n) || tentativeGScore < gScore.get(n))
				{
					cameFrom.put(n, current);
					gScore.put(n, tentativeGScore);
					if(!openSet.contains(n))
						openSet.add(n);
				}
			}
		}
		
		return null;
	}
	
	public ArrayList<Tile> reconstructPath(HashMap<Tile, Tile> cameFrom, Tile current)
	{
		if(cameFrom.containsKey(current))
		{
			ArrayList<Tile> path = reconstructPath(cameFrom, cameFrom.get(current));
			path.add(current);
			return path;
		}
		else
		{
			ArrayList<Tile> path = new ArrayList<Tile>();
			path.add(current);
			return path;
		}
	}
}

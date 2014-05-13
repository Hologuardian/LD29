package holo.common.tile;

import holo.common.item.ITEMS;
import holo.common.world.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class TileGenerator 
{
	public static Tile genTile(Map map, int ID, Vector2f loc) throws SlickException
	{
		switch(ID)
		{
		case 0:
		{
			return new Tile(loc, "res/textures/tile/Stone.png", false);
		}
		case 1:
		{
			return new Tile(loc, "res/textures/tile/Wall.png", true);
		}
		case 2:
		{
			map.lightSources.add(new LightSource(new Vector2f(loc.getX() * Tile.size + Tile.size / 2, loc.getY() * Tile.size + Tile.size / 2), 127, Color.red));
			return new AnimatedTile(loc, TileHelper.lava, true, 260);
		}
		case 3:
		{
			return new Tile(loc, "res/textures/tile/Arcane.png", true);
		}
		case 4:
		{
			return new EndTile(loc, "res/textures/tile/Tear.png", false);
		}
		case 10: //Spawn -> Tunnel
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Tunnel", new Vector2f(480, 32));
		}
		case 11: //Tunnel -> Spawn
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Spawn", new Vector2f(480, 864));
		}
		case 12: //Spawn -> Abandoned
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Abandoned", new Vector2f(32, 480));
		}
		case 13: //Abandoned -> Spawn
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Spawn", new Vector2f(864, 480));
		}
		case 14: //Abandoned -> Tunnel
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Tunnel", new Vector2f(1680, 32));
		}
		case 15: //Tunnel -> Abandoned
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Abandoned", new Vector2f(784, 864));
		}
		case 16: //Abandoned -> LavaPools
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "LavaPools", new Vector2f(32, 160));
		}
		case 17: //LavaPools -> Abandoned
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Abandoned", new Vector2f(928, 160));
		}
		case 18: //Tunnel -> ArcaneCrossroad
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "ArcaneCrossroad", new Vector2f(32, 224));
		}
		case 19: //ArcaneCrossroad -> Tunnel
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "Tunnel", new Vector2f(3168, 224));
		}
		case 20: //LavaPools -> ArcaneCrossroad
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "ArcaneCrossroad", new Vector2f(96, 32));
		}
		case 21: //ArcaneCrossroad -> LavaPools
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "LavaPools", new Vector2f(1376, 864));
		}
		case 22: //ArcaneCrossroad -> ArcaneVault
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "ArcaneVault", new Vector2f(32, 160));
		}
		case 23: //ArcaneVault -> ArcaneCrossroad
		{
			return new TileMapChange(loc, "res/textures/tile/Stone.png", false, "ArcaneCrossroad", new Vector2f(416, 96));
		}
		case 100: //Dull Sword
		{
			return new TileItemUpgrade(loc, "res/textures/tile/SwordUpgradeTile0.png", false, ITEMS.DULLBLADE);
		}
		case 101: //Sharp Sword
		{
			return new TileItemUpgrade(loc, "res/textures/tile/SwordUpgradeTile1.png", false, ITEMS.SHARPBLADE);
		}
		case 102: //Molten Sword
		{
			return new TileItemUpgrade(loc, "res/textures/tile/SwordUpgradeTile2.png", false, ITEMS.MOLTENBLADE);
		}
		case 110: //Bent Crossbow
		{
//			return new TileItemUpgrade(loc, "res/textures/tile/SwordUpgradeTile0.png", false, ITEMS.DULLBLADE);
		}
		case 111: //Crossbow
		{
			return new TileItemUpgrade(loc, "res/textures/tile/CrossbowUpgradeTile1.png", false, ITEMS.CROSSBOW);
		}
		case 112: //Molten Crossbow
		{
			return new TileItemUpgrade(loc, "res/textures/tile/CrossbowUpgradeTile2.png", false, ITEMS.MOLTENCROSSBOW);
		}
		case 120: //DimLantern
		{
			return new TileItemUpgrade(loc, "res/textures/tile/LanternUpgradeTile0.png", false, ITEMS.DIMLANTERN);
		}
		case 121: //DimLantern
		{
			return new TileItemUpgrade(loc, "res/textures/tile/LanternUpgradeTile1.png", false, ITEMS.LANTERN);
		}
		case 122: //DimLantern
		{
			return new TileItemUpgrade(loc, "res/textures/tile/LanternUpgradeTile1.png", false, ITEMS.BRIGHTLANTERN);
		}
		}

		return null;
	}
}

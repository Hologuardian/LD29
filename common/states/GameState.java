package holo.common.states;

import holo.common.Main;
import holo.common.entity.EntityPlayer;
import holo.common.render.GameRender;
import holo.common.world.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState extends BasicGameState
{
	public static World world;
	public GameRender render;
	public EntityPlayer player;
	public StateBasedGame game;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException 
	{
		this.game = game;
		Image white = new Image("res/textures/tile/White.png");
		render = new GameRender(white);
		world = new World(render, game);
		world.load();
		world.changeMap("Spawn");;
		player = new EntityPlayer(world, new Vector2f(world.loadedMap.spawnX, world.loadedMap.spawnY), "res/textures/entity/Player.png");
		world.addEntity(player);
		world.addLight(player.light);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException 
	{
		render.render(gc, g, world);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException 
	{
		world.update(delta);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) 
	{
		player.mousePressed(button, x, y);
	}

	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) 
	{
		player.mouseMoved(oldx, oldy, newx, newy);
	}
	
	@Override
    public void keyPressed(int key, char c) 
    {
		if(key == Input.KEY_ESCAPE)
			game.enterState(Main.menuStateID);
		player.onKeyPressed(key);
    }
	
	@Override
	public void keyReleased(int key, char c)
	{
		player.onKeyReleased(key);
	}

	@Override
	public int getID() 
	{
		return Main.gameStateID;
	}

}

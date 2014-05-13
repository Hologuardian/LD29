package holo.common.states;

import holo.common.Main;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MenuState extends BasicGameState
{
	public Image title;
	public Image start;
	public Image help;
	public Image quit;
	
	public Image wall;
	public Image floor;
	
	public int textX;

	public int titleY;
	public int startY;
	public int helpY;
	public int quitY;
	
	public float defaultScale;
	public float startScale;
	public float helpScale;
	public float quitScale;
	
	public MouseOverArea startBox;
	public MouseOverArea helpBox;
	public MouseOverArea quitBox;
	
	public Random rand;
	public StateBasedGame game;
	public GameContainer container;
	
	public Music music;
	
	public boolean[][] background;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException
	{
		this.game = game;
		container = gc;
		
		rand = new Random();
		
		background = new boolean[(int) StrictMath.ceil(gc.getHeight() / 64) + 1][(int) StrictMath.ceil(gc.getWidth() / 64) + 1];
		
		for(int y = 0; y < background.length; ++y)
		{
			for(int x = 0; x < background[y].length; ++x)
			{
				background[y][x] = rand.nextBoolean();
			}
		}
		
		textX = gc.getWidth() / 2;
		
		wall = new Image("res/textures/tile/Wall.png");
		floor = new Image("res/textures/tile/Stone.png");
		
		title = new Image("res/textures/menu/Title.png");
		start = new Image("res/textures/menu/Start.png").getScaledCopy(0.75F);
		help = new Image("res/textures/menu/Help.png").getScaledCopy(0.75F);
		quit = new Image("res/textures/menu/Quit.png").getScaledCopy(0.75F);

		defaultScale = (gc.getHeight() - title.getHeight()) / (start.getHeight() + help.getHeight() + quit.getHeight());
		
		titleY = title.getHeight() / 2 + title.getHeight() / 20;
		
		startY = (int)(start.getHeight() * defaultScale) / 2 + title.getHeight() + (int)(start.getHeight() * defaultScale) / 20 + title.getHeight() / 10;
		startScale = defaultScale;
		startBox = new MouseOverArea(gc, start, textX - start.getWidth() / 2, startY - start.getHeight() / 2);
		
		helpY = (int)(help.getHeight() * defaultScale) / 2 + (int)(title.getHeight() + start.getHeight() * defaultScale) + (int)(help.getHeight() * defaultScale) / 20 + (int)(start.getHeight() * defaultScale) / 10 + title.getHeight() / 10;
		helpScale = defaultScale;
		helpBox = new MouseOverArea(gc, help, textX - help.getWidth() / 2, helpY - help.getHeight() / 2);
		
		quitY = (int)(quit.getHeight() * defaultScale) / 2 + title.getHeight() + (int)(start.getHeight() * defaultScale) + (int)(help.getHeight() * defaultScale) + (int)(quit.getHeight() * defaultScale) / 20 + (int)(start.getHeight() * defaultScale) / 10 + (int)(help.getHeight() * defaultScale) / 10 + title.getHeight() / 10;
		quitScale = defaultScale;
		quitBox = new MouseOverArea(gc, quit, textX - quit.getWidth() / 2, quitY - quit.getHeight() / 2);
		
		music = new Music("res/sound/Music.wav");
		music.loop(1.0F, 0.3F);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException
	{
		g.clear();
		
		for(int y = 0; y < background.length; ++y)
		{
			for(int x = 0; x < background[y].length; ++x)
			{
				if(background[y][x])
					wall.draw(x * 64, y * 64);
				else
					floor.draw(x * 64, y * 64);
			}
		}
		
		title.drawCentered(textX, titleY);
		start.getScaledCopy(startScale).drawCentered(textX, startY);
		help.getScaledCopy(helpScale).drawCentered(textX, helpY);
		quit.getScaledCopy(quitScale).drawCentered(textX, quitY);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta)
			throws SlickException
	{
		if(startBox.isMouseOver())
			startScale = defaultScale * 1.05F;
		else
			startScale = defaultScale;
		if(helpBox.isMouseOver())
			helpScale = defaultScale * 1.05F;
		else
			helpScale = defaultScale;
		if(quitBox.isMouseOver())
			quitScale = defaultScale * 1.05F;
		else
			quitScale = defaultScale;
	}

	@Override
	public int getID()
	{
		return Main.menuStateID;
	}
	
	public void mousePressed(int button, int x, int y) 
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(startBox.isMouseOver())
			{
				try {
					game.getState(Main.gameStateID).init(game.getContainer(), game);
				} catch (SlickException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				game.enterState(Main.gameStateID);
			}
			else if(helpBox.isMouseOver())
				game.enterState(Main.helpStateID);
			else if(quitBox.isMouseOver())
				container.exit();
		}
	}
}

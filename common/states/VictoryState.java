package holo.common.states;

import holo.common.Main;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class VictoryState extends BasicGameState
{
	public int level;
	public int score;
	public int startScore;
	
	public float defaultScale;
	
	public Image success;
	public int successX;
	public int successY;
	
	public Image menu;
	public int menuX;
	public int menuY;
	public float menuScale;
	public MouseOverArea menuBox;
	
	public StateBasedGame game;
	public GameContainer container;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException
	{
		this.game = game;
		container = gc;
		
		defaultScale = gc.getHeight() / 480;
		
		success = new Image("res/textures/menu/Success.png");
		successX = gc.getWidth() / 2;
		successY = success.getHeight() / 2;
		
		menu = new Image("res/textures/menu/Menu.png").getScaledCopy(0.5F);
		menuX = menu.getWidth() / 2 + menu.getWidth() / 20;
		menuY = gc.getHeight() - menu.getHeight();
		menuScale = defaultScale;
		menuBox = new MouseOverArea(gc, menu, menuX - menu.getWidth() / 2, menuY - menu.getHeight() / 2);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
	{
		Font f = gc.getDefaultFont();
		success.drawCentered(gc.getWidth() / 2, success.getHeight() / 2);
		menu.getScaledCopy(menuScale).drawCentered(menuX, menuY);
		
		String s = "You have successfully recovered the Tear of Ali'Kura!";
		FontUtils.drawCenter(f, s, gc.getWidth() / 2 - f.getWidth(s) / 2, success.getHeight() + f.getLineHeight(), f.getWidth(s));
		s = "Your final score was " + Main.score + ".";
		FontUtils.drawCenter(f, s, gc.getWidth() / 2 - f.getWidth(s) / 2, success.getHeight() + f.getLineHeight() * 2, f.getWidth(s));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	{
		if(menuBox.isMouseOver())
			menuScale = defaultScale * 1.05F;
		else
			menuScale = defaultScale;
	}

	@Override
	public int getID()
	{
		return Main.victoryStateID;
	}
	
	public void updateStats(int level, int score, int levelStartScore)
	{
		this.level = level;
		this.score = score;
		this.startScore = levelStartScore;
	}
	
	public void mousePressed(int button, int x, int y) 
	{
		if(button == Input.MOUSE_LEFT_BUTTON)
		{
			if(menuBox.isMouseOver())
			{
				game.enterState(Main.menuStateID);
			}
		}
	}
}

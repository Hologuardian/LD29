package holo.common.states;

import holo.common.Main;

import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;

public class HelpState extends BasicGameState
{
	public static final String[] line = new String[]{"&&&Greetings bold adventurer, you have been chosen for a special assignment.", 
		"Your mission is to retrieve the Tear of Ali'Kura from a hostile environment deep beneath the surface of the earth.",
		"&The cave is a hostile and dangerous environment, you are also being sent in with minimal equipment, ",
		"though you should be able to find weapons and other things to aid you within the cave itself.",
		"&There is a race of indigenous people living within the cave, expect armed resistance and you have been ",
		"Given the authority to kill on sight.",
		"Anything you meet will be able to see clearly in the dark environment giving them the advantage in the dark.",
		"&Use the WASD keys to move and your mouse to look around",
		"clicking any mouse button will use the item you are currently holding.",
		"You have 3 types of items that you can access at any time.",
		"Pressing '1' will select your sword, Pressing '2' will select your bow, and pressing '3' will select your lantern.",
		"&When you are ready, press the escape key to return to the menu.",
		"&&&&Good luck adventurer, you are going to need it."};
	
	public StateBasedGame game;
	public GameContainer container;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException
	{
		this.game = game;
		container = gc;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException
	{
		Font f = gc.getDefaultFont();
		
		int y = 0;
		for(String text : line)
		{
			while(text.charAt(0) == '&')
			{
				y += f.getLineHeight();
				text = text.substring(1);
			}
			FontUtils.drawCenter(f, text, gc.getWidth() / 2 - f.getWidth(text) / 2, y, f.getWidth(text));
			y += f.getLineHeight();
		}
		
		FontUtils.drawCenter(f, "Developer: Hologuardian", gc.getWidth() / 2 - f.getWidth("Developer: Hologuardian") / 2, gc.getHeight() - f.getHeight("Developer: Hologuardian"), f.getWidth("Developer: Hologuardian"));
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException
	{
		
	}

	@Override
	public int getID()
	{
		return Main.helpStateID;
	}
	
	@Override
    public void keyPressed(int key, char c) 
    {
		if(key == Input.KEY_ESCAPE)
			game.enterState(Main.menuStateID);
    }
}

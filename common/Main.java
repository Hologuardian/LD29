package holo.common;

import holo.common.render.GameRender;
import holo.common.states.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame
{
	public static final int gameStateID = 0;
	public static final int menuStateID = 1;
	public static final int helpStateID = 2;
	public static final int gameOverStateID = 3;
	public static final int victoryStateID = 4;
	public static float score = 0;
	
	public Main() {
		super("Subterranian");
	}
	
	public static void main(String[] args) throws SlickException 
	{
		AppGameContainer app = new AppGameContainer(new Main());
		  
        app.setDisplayMode(GameRender.cameraWidth, GameRender.cameraHeight, false);
        
        app.setShowFPS(false);
        
        if (app.isUpdatingOnlyWhenVisible())
        	app.setUpdateOnlyWhenVisible(false);
        
        app.setVSync(false);

        app.start();
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException 
	{
		this.addState(new MenuState());
		this.addState(new GameState());
		this.addState(new HelpState());
		this.addState(new VictoryState());
		this.addState(new GameOverState());
	}

}

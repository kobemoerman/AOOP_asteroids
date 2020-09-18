package aoop.asteroids.view;

import aoop.asteroids.model.Game;
import aoop.asteroids.controller.PlayerSingle;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *	SingleplayerFrame is a class that extends JFrame and thus provides a game
 *	window for the Asteroids game.
 */

public class SingleplayerFrame extends GameFrame implements Gamemode {

	/** serialVersionUID */
	public static final long serialVersionUID = 1L;

	/** Quit action. */
	private AbstractAction quitAction;

	/** New game action. */
	private AbstractAction newGameAction;

	/** The panel in which the game is painted. */
	private AsteroidsPanel ap;

	/** 
	 *	Constructs a new Frame, requires a game model.
	 *
	 *	@param game game model.
	 *	@param controller key listener that catches the users actions.
	 */
	public SingleplayerFrame(Game game, PlayerSingle controller) {
		super(game,controller,SINGLEPLAYER);

		this.initActions();

		JMenuBar mb = new JMenuBar ();
		JMenu m = new JMenu ("Game");
		mb.add (m);
		m.add (this.quitAction);
		m.add (this.newGameAction);
		this.setJMenuBar (mb);

		this.ap = new AsteroidsPanel (game);
		this.add (this.ap);
		this.setVisible (true);

	}

	/** Initializes the quit- and new game action. */
	private void initActions() {
		// Quits the application
		this.quitAction = new AbstractAction ("Quit") {
			public static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed (ActionEvent arg0)	{
				switchToMenu();
			}
		};

		// Creates a new model
		this.newGameAction = new AbstractAction ("New Game")	{
			public static final long serialVersionUID = 3L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				SingleplayerFrame.this.newGame ();
			}
		};

	}
}

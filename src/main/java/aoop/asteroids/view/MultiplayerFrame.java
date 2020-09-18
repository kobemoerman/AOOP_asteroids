package aoop.asteroids.view;

import aoop.asteroids.model.Game;
import aoop.asteroids.controller.PlayerMulti;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 *	MultiplayerFrame is a class that extends JFrame and thus provides a game
 *	window for the Asteroids game.
 */

public class MultiplayerFrame extends GameFrame implements Gamemode{

    /** serialVersionUID */
    public static final long serialVersionUID = 2L;

    /** Quit action. */
    private AbstractAction quitAction;

    /** The panel in which the game is painted. */
    private AsteroidsPanel ap;

    /**
     *	Constructs a new Frame, requires a game model.
     *
     *	@param game game model.
     *  @param controller key listener that catches the users action.
     */
    public MultiplayerFrame (Game game, PlayerMulti controller) {
        super(game,controller,MULTIPLAYER);

        this.initActions();

        JMenuBar mb = new JMenuBar ();
        JMenu m = new JMenu ("Game");
        mb.add (m);
        m.add (this.quitAction);
        this.setJMenuBar (mb);

        this.ap = new AsteroidsPanel (game);
        this.add (this.ap);
        this.setVisible (true);

    }

    /** Initializes the quit game action. */
    private void initActions() {
        // Quits the application
        this.quitAction = new AbstractAction ("Quit") {
            public static final long serialVersionUID = 2L;

            @Override
            public void actionPerformed (ActionEvent arg0) {
                switchToMenu();
            }
        };
    }
}

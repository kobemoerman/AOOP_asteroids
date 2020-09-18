package aoop.asteroids.view;

import aoop.asteroids.model.Game;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

/**
 *	SpectateFrame is a class that extends JFrame and thus provides a game
 *	window for the Asteroids game.
 */

public class SpectateFrame extends GameFrame implements Gamemode {

    /** serialVersionUID */
    public static final long serialVersionUID = 1L;

    /** Quit action. */
    private AbstractAction quitAction;

    /** The panel in which the game is painted. */
    private AsteroidsPanel ap;

    /**
     *	Constructs a new Frame, requires a game model.
     *
     *	@param game game model.
     */
    public SpectateFrame (Game game) {
        super(game,null,SPECTATE);

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
        this.quitAction = new AbstractAction ("Quit"){
            public static final long serialVersionUID = 2L;

            @Override
            public void actionPerformed (ActionEvent arg0)
            {
                switchToMenu();
            }
        };
    }
}

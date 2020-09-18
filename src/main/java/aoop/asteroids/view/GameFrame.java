package aoop.asteroids.view;

import aoop.asteroids.controller.Player;
import aoop.asteroids.controller.PlayerSingle;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.Serializable;

/**
 *	SingleplayerFrame is a class that extends JFrame and thus provides a game
 *	window for the Asteroids game.
 */

public class GameFrame extends JFrame {

    /** serialVersionUID */
    public static final long serialVersionUID = 3L;

    /** The game model. */
    private Game game;

    /** This constructor doesn't do anything. */
    public GameFrame(){}

    /**
     *	Constructs a new Frame, requires a game model.
     *
     *	@param game game model.
     *	@param controller key listener that catches the users actions.
     *  @param title game frame type.
     */
    public GameFrame (Game game, Player controller, String title) {
        this.game = game;

        this.setTitle (title);
        this.setSize (800, 800);
        this.addKeyListener (controller);
        this.setLocationRelativeTo(null);

        this.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);

    }

    /** Quits the old game and starts a new one. */
    protected void newGame () {
        this.game.abort ();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.err.println ("Could not sleep before initialing a new game.");
            e.printStackTrace ();
        }
        this.game.initGameData ();
    }

    /** Updates the game model from the server packets. */
    public void receivedGameUpdate(Game g){
        if(g.getTick() >= this.game.getLastUpdate()){
            this.game.setLastUpdate(g.getTick());
            this.game.setAsteroids(g.getAsteroids());
            this.game.setBullets(g.getBullets());
            this.game.setShip(g.getSpaceships());
        }

    }

    /** Switches between the Game frame and the Menu frame */
    protected void switchToMenu(){
        this.dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING));
        new MenuFrame();
    }
}

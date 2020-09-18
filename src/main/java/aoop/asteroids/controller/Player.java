package aoop.asteroids.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *	Player is the controller class that listens to the users input (key events)
 *	and forwards those events to the spaceship class.
 */

public abstract class Player implements KeyListener {

    /** Indicates whether the fire button is pressed. */
    protected boolean firing;

    /** Indicates whether the accelerate button is pressed. */
    protected boolean up;

    /** Indicates whether the turn right button is pressed. */
    protected boolean right;

    /** Indicates whether the turn left button is pressed. */
    protected boolean left;

    /** Subclasses need to specify their own behaviour. */
    protected abstract void updateShip();

    /**
     *	This method is invoked when a key is pressed and sets the corresponding
     *	fields in the spaceship to true.
     *
     *	@param e keyevent that triggered the method.
     */
    @Override
    public void keyPressed (KeyEvent e) {
        switch (e.getKeyCode ()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_SPACE:
                firing = true;
        }
        updateShip();
    }

    /**
     *	This method is invoked when a key is released and sets the
     *	corresponding fields in the spaceship to false.
     *
     *	@param e keyevent that triggered the method.
     */
    @Override
    public void keyReleased (KeyEvent e) {
        switch (e.getKeyCode ()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_SPACE:
                firing = false;
        }
        updateShip();
    }

    /**
     *	This method doesn't do anything.
     *
     *	@param e keyevent that triggered the method.
     */
    @Override
    public void keyTyped (KeyEvent e) {}

}

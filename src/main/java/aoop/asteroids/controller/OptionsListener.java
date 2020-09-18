package aoop.asteroids.controller;

import aoop.asteroids.view.MenuOptions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class OptionsListener implements KeyListener {

    /** The option that is being influenced. */
    private MenuOptions options;

    /**
     * Updates the option.
     * @param action current selected option.
     */
    public OptionsListener(MenuOptions action) {
        this.options = action;
    }

    /**
     *	This method is invoked when a key is pressed and updates
     *	the corresponding fields in MenuOptions.
     *
     *	@param e keyevent that triggered the method.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP)
            options.setGraphics("up");

        if(e.getKeyCode() == KeyEvent.VK_DOWN)
            options.setGraphics("down");

        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            options.optionSelected();
    }

    /**
     *	This method doesn't do anything.
     *	@param e keyevent that triggered the method.
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     *	This method doesn't do anything.
     *	@param e keyevent that triggered the method.
     */
    @Override
    public void keyReleased(KeyEvent e) { }
}

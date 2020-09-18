package aoop.asteroids.view;

import aoop.asteroids.controller.OptionsListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 *	MenuFrame is a class that extends JFrame and thus provides
 *	a frame with the main menu for the user.
 */

public class MenuFrame extends JFrame {

    /** Stores image from the 'Resources' package. */
    private BufferedImage img = null;

    /** Constructs a new Menu frame. */
    public MenuFrame() {
        this.setLayout(new BorderLayout());

        menuTitle();

        menuCredits();

        menuOptions();

        this.pack();
        this.setTitle("Asteroids Menu");
        this.setSize(800, 800);
        this.setLocationRelativeTo(null);
        this.setFocusable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /** Adds the read images to the frame. Responsible for the game options. */
    private void menuOptions() {
        try {
            BufferedImage[] menuOptions = {
                    ImageIO.read(new File("Resources/Menu/single.png")), ImageIO.read(new File("Resources/Menu/create.png")),
                    ImageIO.read(new File("Resources/Menu/join.png")), ImageIO.read(new File("Resources/Menu/spectate.png")),
                    ImageIO.read(new File("Resources/Menu/highscore.png")), ImageIO.read(new File("Resources/Menu/quit.png"))
            };
            MenuOptions options = new MenuOptions(menuOptions,this);
            this.add(options, BorderLayout.CENTER);
            OptionsListener actions = new OptionsListener(options);
            this.addKeyListener(actions);
        } catch (IOException e) {
            System.err.print(e.toString());
        }
    }

    /** Adds the read images to the frame. Responsible for the menu credits. */
    private void menuCredits() {
        try {
            img = ImageIO.read(new File("Resources/Menu/credits.png"));
            MenuOptions credits = new MenuOptions(img);
            credits.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()-50));
            credits.setBackground(Color.black);
            this.add(credits, BorderLayout.PAGE_END);
        } catch (IOException e) {
            System.err.print(e.toString());
        }
    }

    /** Adds the read images to the frame. Responsible for the menu title. */
    private void menuTitle() {
        try {
            img = ImageIO.read(new File("Resources/Menu/title.png"));
            MenuOptions title = new MenuOptions(img);
            title.setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
            title.setBackground(Color.black);
            this.add(title,BorderLayout.PAGE_START);

        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
}

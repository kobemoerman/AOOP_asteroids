package aoop.asteroids.view;

import aoop.asteroids.packet.PacketSpectate;
import aoop.asteroids.server.*;
import aoop.asteroids.controller.PlayerSingle;
import aoop.asteroids.model.Game;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class MenuOptions extends JPanel implements Gamemode {

    /** Game model. */
    private Game game;

    /** Current frame. */
    private JFrame frame;

    /** Connect to this port. */
    private int port;

    /** Connect to this ip. */
    private InetAddress ip = null;

    /** Username of the player. */
    private String username;

    /** Current option selected by the user. */
    private int selected = 0;

    /** Loaded image. */
    private BufferedImage img;

    /** List of images. */
    private ArrayList<BufferedImage> event = new ArrayList<>();

    /** Graphics instance to use */
    private Graphics G;

    /**
     * Sets the image to paint.
     *
     * @param image loaded image from the 'Resources' package.
     */
    public MenuOptions (BufferedImage image) {
        this.img = image;
    }

    /**
     * Sets the list of images to paint.
     *
     * @param images list of images.
     * @param frame current frame.
     */
    public MenuOptions(BufferedImage[] images, JFrame frame) {
        event.addAll(Arrays.asList(images));
        this.frame = frame;
    }

    /**
     * Updates the index of the images list.
     * @param choice action from Options Listener.
     */
    public void setGraphics(String choice) {
        if(choice.equalsIgnoreCase("up"))
            if(selected > 0)
                selected--;

        if(choice.equalsIgnoreCase("down"))
            if(selected < event.size() - 1)
                selected++;

        paintComponent(G);
        repaint();
    }

    /** Take action depending on the selected index */
    public void optionSelected() {
        switch (selected) {
            case NEWGAME:
                startGame();
                break;
            case CREATEGAME:
                startCreate();
                break;
            case JOINGAME:
                startJoin();
                break;
            case SPECTATEGAME:
                startSpectate();
                break;
            case HIGHSCORE:
                new HighscoreFrame();
                killFrame();
                break;
            case QUIT:  {
                System.exit(0);
            }
        }
    }

    /** Start a Single Player game. */
    private void startGame() {
        PlayerSingle player = new PlayerSingle();
        Color color = new Color(255,255,255);
        game = new Game(color,true);
        game.linkController (player);
        Thread t = new Thread (game);
        t.start ();
        new SingleplayerFrame(game, player);
        killFrame();
    }

    /** Host a Multi Player game. */
    private void startCreate() {
        this.port = Integer.parseInt(JOptionPane.showInputDialog(this,"Please enter a valid port."));

        if(this.port > 0 && this.port < 65535){
            try {
                this.ip = InetAddress.getLocalHost();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            game = new Game(false,this.ip,this.port);
            Thread t = new Thread (game);
            t.start ();
            getUsername();
            ManagerServer server = new ManagerServer(game,this.port);
            server.start();
            t = new ManagerMultiplayer(this.username,ip,this.port);
            System.out.println(ip);
            t.start();
            killFrame();
        }else{
            JOptionPane.showMessageDialog(null, "ERROR: port must be greater than 0 and smaller than 65535.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Join a Multi Player game. */
    private void startJoin() {
        setServerAddress();
        getUsername();
        Thread t = new ManagerMultiplayer(this.username,this.ip,this.port);
        t.start();
        killFrame();
    }

    /** Spectate a Multi Player game. */
    private void startSpectate() {
        setServerAddress();
        try{
            DatagramSocket ds = new DatagramSocket();
            Thread t = new ManagerSpectate(ds,this.ip,this.port);
            System.out.println("ds has port " + ds.getLocalPort());
            PacketSpectate spectate = new PacketSpectate(ds.getInetAddress(), ds.getLocalPort());
            spectate.send(this.ip,this.port);
            t.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        killFrame();
    }

    /** Ask the user for a IP and port number. */
    private void setServerAddress () {
        String input = JOptionPane.showInputDialog(this,"Please enter valid address (ip:port).");
        if (input.contains(":")) {
            String[] arr = input.split(":");
            try {
                this.ip = InetAddress.getByName(arr[0]);
                this.port = Integer.parseInt(arr[1]);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null,"ERROR: Invalid server address. Must be of the form (ip:port).", "ERROR", JOptionPane.ERROR_MESSAGE);
            setServerAddress();
        }
    }

    /** Ask the user for a name. */
    private void getUsername (){
        String username = JOptionPane.showInputDialog(this, "Please enter username.");
        if(username == null || username.trim().equals("")) {
            JOptionPane.showMessageDialog(null,"ERROR: A username must contain characters.", "ERROR", JOptionPane.ERROR_MESSAGE);
            getUsername();
        } else {
            this.username = username;
        }
    }

    /** Removes the Menu frame */
    private void killFrame(){
        frame.dispatchEvent(new WindowEvent(frame,WindowEvent.WINDOW_CLOSING));
    }

    /** Paints the images. */
    @Override
    public void paintComponent (Graphics g) {
        this.G = g;
        super.paintComponent(g);
        if (event.size() == 0)
            g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
        else
            g.drawImage(event.get(selected), 0, 0, event.get(selected).getWidth(), event.get(selected).getHeight(), null);
    }
}

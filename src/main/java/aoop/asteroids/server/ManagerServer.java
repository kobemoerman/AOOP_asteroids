package aoop.asteroids.server;

import aoop.asteroids.packet.Packet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;
import aoop.asteroids.packet.PacketControl;
import aoop.asteroids.packet.PacketLogin;
import aoop.asteroids.packet.PacketSpectate;

import java.awt.*;
import java.net.*;
import java.util.Objects;
import java.util.Random;

public class ManagerServer extends ManagerPacket {

    /** Game that is being influenced on the server. */
    private Game game;

    /** List of connected multi-players. */
    private Messenger messenger;

    /**
     * Initialises the game on the server.
     *
     * @param game game to take action on.
     * @param port port to listen to.
     */
    public ManagerServer(Game game, int port) {
        this.game = game;
        this.messenger = new Messenger(game);
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns a random color to the new player.
     * @return random color.
     */
    private Color getColor(){
        int r = new Random().nextInt(250);
        int g = new Random().nextInt(250);
        int b = new Random().nextInt(250);
        return new Color(r,g,b);
    }

    /**
     * Takes action on the packet depending on its type.
     * @param packet packet that is received.
     */
    @Override
    protected void receivePacket(DatagramPacket packet) {
        Object obj = SerializePacket.deserialize(packet.getData());
        int action = ((Packet)Objects.requireNonNull(obj)).action();

        switch (action){
            case 0:
                PacketLogin login = (PacketLogin)obj;
                messenger.addAddress(new ServerAddress(login.getIp(),login.getPort()));
                game.addSpaceship(new Spaceship(login.getUsername(), getColor(),login.getPort(),login.getIp()));
                break;
            case 1:
                PacketControl cp = (PacketControl)obj;
                game.updateControl(cp);
                break;
            case 2:
                PacketSpectate spectate = (PacketSpectate)obj;
                messenger.addAddress(new ServerAddress(packet.getAddress(),spectate.getPort()));
                break;
        }
    }
}

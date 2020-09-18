package aoop.asteroids.server;

import aoop.asteroids.view.SpectateFrame;
import aoop.asteroids.model.Game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ManagerSpectate extends ManagerPacket {

    /** Game frame for spectator. */
    private SpectateFrame sf;

    /**
     * Initialises server functionalities for Spectator.
     *
     * @param ds socket to listen on.
     * @param ip ip of the server.
     * @param port port of the server.
     */
    public ManagerSpectate(DatagramSocket ds, InetAddress ip, int port){
        this.sf = new SpectateFrame(new Game(false,ip,port));
        this.socket = ds;
    }

    /**
     * Updates the game with the given packet.
     * @param packet packet that is sent.
     */
    @Override
    protected void receivePacket(DatagramPacket packet) {
        Game g = (Game)SerializePacket.deserialize(packet.getData());
        sf.receivedGameUpdate(g);
    }
}

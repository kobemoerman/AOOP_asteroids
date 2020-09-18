package aoop.asteroids.server;

import aoop.asteroids.packet.PacketLogin;
import aoop.asteroids.view.MultiplayerFrame;
import aoop.asteroids.model.Game;
import aoop.asteroids.controller.PlayerMulti;

import java.net.*;

public class ManagerMultiplayer extends ManagerPacket {

    /** Game frame for multi-player. */
    private MultiplayerFrame mf;

    /** Player inside the game. */
    private PlayerMulti mplayer = null;

    /** packet responsible for the login. */
    private PacketLogin lp = null;

    /**
     * Initialises server functionalities for a Player.
     *
     * @param username username of the new player.
     * @param ip ip the player connects to.
     * @param port port the player connects to.
     */
    public ManagerMultiplayer(String username, InetAddress ip, int port){
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        try { //ask to join game
            this.lp = new PacketLogin(username, InetAddress.getLocalHost(),socket.getLocalPort());
            this.mplayer = new PlayerMulti(ip,InetAddress.getLocalHost(),port,socket.getLocalPort());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        lp.send(ip, port);
        this.mf = new MultiplayerFrame(new Game(false,ip,port), this.mplayer);
    }

    /**
     * Updates the game with the given packet.
     * @param packet packet that is sent.
     */
    @Override
    protected void receivePacket(DatagramPacket packet) {
        Game g = (Game)SerializePacket.deserialize(packet.getData());
        mf.receivedGameUpdate(g);
    }
}

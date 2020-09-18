package aoop.asteroids.server;

import aoop.asteroids.model.Game;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.*;

public class Messenger extends Thread implements Observer {

    /** Game that is being influenced on the server. */
    private Game game;

    /** Socket to listen. */
    private DatagramSocket socket;

    /** List of all addresses. */
    private List<ServerAddress> addressList;

    /**
     * Initialises the server.
     *
     * @param g game that is in use.
     */
    public Messenger(Game g){
        this.addressList = new ArrayList<>();
        this.game = g;
        game.addObserver(this);
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        start();
    }

    /** Add address to the server. */
    public void addAddress(ServerAddress a){
        addressList.add(a);
    }

    /** Remove address from the server. */
    public void removeAddress(ServerAddress a){
        addressList.remove(a);
    }

    /**
     * Compresses the game and sends it to all other addresses.
     * @param observable not used.
     * @param o not used.
     */
    @Override
    public void update(Observable observable, Object o) {
        byte[] gameBytes = SerializePacket.serialize(game);

        try{ //send game
            for (ServerAddress a: addressList) {
                DatagramPacket p = new DatagramPacket(gameBytes,Objects.requireNonNull(gameBytes).length,a.getIp(),a.getPort());
                this.socket.send(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

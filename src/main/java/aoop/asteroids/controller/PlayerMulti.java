package aoop.asteroids.controller;

import aoop.asteroids.packet.PacketControl;

import java.net.InetAddress;

/** PlayerMulti influences the behaviour of the spaceship. */

public class PlayerMulti extends Player {

    /** Port of the host. */
    private int port;

    /** Port of the player. */
    private  int ownPort;

    /** IP of the host. */
    private InetAddress ip;

    /** IP of the player */
    private InetAddress ownIP;

    /**
     * Constructs the player and listens to key events.
     *
     * @param ip IP of the host.
     * @param ownIP IP of the player.
     * @param port port of the host.
     * @param ownPort port of the player.
     */
    public PlayerMulti(InetAddress ip, InetAddress ownIP, int port, int ownPort) {
        this.ip = ip;
        this.port = port;
        this.ownIP = ownIP;
        this.ownPort = ownPort;
    }

    /**
     * Returns the port of the host.
     * @return port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the IP of the host.
     * @return ip.
     */
    public InetAddress getIp() {
        return ip;
    }

    /** Updates the state of the spaceship over the server. */
    @Override
    protected void updateShip() {
        PacketControl cp = new PacketControl(this.up,this.left,this.right,this.firing,ownIP,ownPort);
        cp.send(ip,port);
    }
}

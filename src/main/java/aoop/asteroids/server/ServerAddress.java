package aoop.asteroids.server;

import java.net.InetAddress;

public class ServerAddress {

    /** Ip of the server. */
    private InetAddress ip;

    /** Port of the server. */
    private int port;

    /**
     * Initialises the server with the given IP and port.
     * @param ip ip of the server.
     * @param port port of the server.
     */
    public ServerAddress(InetAddress ip, int port){
        this.ip =ip;
        this.port = port;
    }

    /**
     * Returns the ip of the server.
     * @return ip.
     */
    public InetAddress getIp(){return this.ip;}

    /**
     * Returns the port of the server.
     * @return port.
     */
    public int getPort(){return this.port;}
}

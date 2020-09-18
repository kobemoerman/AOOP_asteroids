package aoop.asteroids.packet;

import java.net.InetAddress;

public class PacketLogin extends Packet {

    /** Username of the player that connects */
    private String username;

    /**
     * Initialises a user on this specific server.
     *
     * @param username name of the user.
     * @param ip ip of the server.
     * @param port port of the server.
     */
    public PacketLogin(String username, InetAddress ip, int port) {
        super(ip,port);
        this.username = username;
    }

    /**
     * Returns the username of the user.
     * @return username.
     */
    public String getUsername () {
        return username;
    }

    /**
     * Sets the username of the user.
     * @param username new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Specifies which packet type has arrived.
     * @return login packet.
     */
    @Override
    public int action() {
        return LOGIN;
    }
}

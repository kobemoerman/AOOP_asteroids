package aoop.asteroids.packet;

import java.net.InetAddress;

public class PacketSpectate extends Packet {
    /**
     * Initialises a new spectator on the server.
     *
     * @param ip ip to spectate on.
     * @param port port to spectate on
     */
    public PacketSpectate(InetAddress ip, int port){
        super(ip,port);
    }

    /**
     * Specifies which packet type has arrived.
     * @return spectate packet.
     */
    @Override
    public int action() {
        return SPECTATE;
    }
}

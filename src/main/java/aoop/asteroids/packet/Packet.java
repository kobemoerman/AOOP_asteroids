package aoop.asteroids.packet;

import aoop.asteroids.server.SerializePacket;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Objects;

public abstract class Packet implements Serializable, PacketAction {

    /** serialVersionUID */
    public static final long serialVersionUID = 4L;

    /** The port of the server. */
    private int port;

    /** The ip of the server. */
    private InetAddress ip;

    /**
     * Initialises the parameters of the server
     *
     * @param ip ip of the server.
     * @param port port of the server.
     */
    public Packet(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Returns the port of the server.
     * @return port.
     */
    public int getPort(){return this.port;}

    /**
     * Returns the ip of the server.
     * @return ip.
     */
    public InetAddress getIp() {
        return ip;
    }

    /**
     * Sets the port of the server.
     * @param p new port.
     */
    public void setPort(int p) {
        this.port = p;
    }

    /**
     * Sets the ip of the server.
     * @param ia new ip.
     */
    public void setIp(InetAddress ia) {
        this.ip = ia;
    }

    /**
     * Sends the package (in byte form) to the server.
     * @param ip ip to send the package.
     * @param port port to send the package.
     */
    public void send(InetAddress ip, int port){
        byte[] bytes = SerializePacket.serialize(this);

        try{
            DatagramPacket dp = new DatagramPacket(bytes,Objects.requireNonNull(bytes).length, ip, port);
            DatagramSocket ds = new DatagramSocket();
            ds.send(dp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

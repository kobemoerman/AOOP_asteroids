package aoop.asteroids.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public abstract class ManagerPacket extends Thread {

    /** Maximum size of a packet. */
    private static final int SIZE = 10240;

    /** Socket to listen. */
    protected DatagramSocket socket;

    /** Updates the game depending on the packet type. */
    protected abstract void receivePacket(DatagramPacket packet);

    /** Receives the packet and sends it to the player. */
    public void run () {
        while (true) {
            byte[] input = new byte[SIZE];
            DatagramPacket packet = new DatagramPacket(input,input.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            receivePacket(packet);
        }
    }

    /**
     * Returns the IP address of the game.
     * @return LocalAddress.
     */
    public InetAddress getAddress(){
        return this.socket.getLocalAddress();
    }

    /**
     * Returns the port of the game.
     * @return LocalPort.
     */
    public int getLocalPort(){
        return socket.getLocalPort();
    }
}

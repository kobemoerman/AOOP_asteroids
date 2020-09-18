package aoop.asteroids.packet;

import java.net.InetAddress;

public class PacketControl extends Packet {

    /** Indicates whether the fire button is pressed. */
    private boolean firing;

    /** Indicates whether the accelerate button is pressed. */
    private boolean up;

    /** Indicates whether the turn right button is pressed. */
    private boolean right;

    /** Indicates whether the turn left button is pressed. */
    private boolean left;

    /**
     * Updates the state of the ship.
     *
     * @param up sets the spaceship to accelerate.
     * @param left sets the spaceship to turn left.
     * @param right sets the spaceship to turn right.
     * @param firing sets the spaceship to fire.
     * @param ip the ip of the spaceship.
     * @param port the port of the spaceship.
     */
    public PacketControl(boolean up, boolean left, boolean right, boolean firing, InetAddress ip, int port) {
        super(ip, port);
        this.firing = firing;
        this.up = up;
        this.right = right;
        this.left = left;
    }

    /**
     * Determines whether the spaceship is accelerating.
     * @return up.
     */
    public boolean getUp() {
        return this.up;
    }

    /**
     * Determines whether the spaceship is turning left.
     * @return left.
     */
    public boolean getLeft() {
        return this.left;
    }

    /**
     * Determines whether the spaceship is turning right.
     * @return right.
     */
    public boolean getRight() {
        return this.right;
    }

    /**
     * Determines whether the spaceship is firing.
     * @return firing.
     */
    public boolean getFiring() {
        return this.firing;
    }

    /**
     * Specifies which packet type has arrived.
     * @return control packet.
     */
    @Override
    public int action() {
        return CONTROL;
    }
}


package aoop.asteroids.packet;

/**
 * Actions to take for each packet type.
 */

public interface PacketAction {
    int action();

    int LOGIN = 0;
    int CONTROL = 1;
    int SPECTATE = 2;
}

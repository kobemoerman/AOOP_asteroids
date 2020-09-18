package aoop.asteroids.view;

/**
 * Displays which game-mode the player started.
 */

public interface Gamemode {
    String SINGLEPLAYER = "Singe Player";
    String MULTIPLAYER = "Multi Player";
    String SPECTATE = "Spectate";

    int NEWGAME = 0;
    int CREATEGAME = 1;
    int JOINGAME = 2;
    int SPECTATEGAME = 3;
    int HIGHSCORE = 4;
    int QUIT = 5;
}

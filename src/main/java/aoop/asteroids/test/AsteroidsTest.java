package aoop.asteroids.test;

import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests functionalities of the program.
 */

public class AsteroidsTest {

    /** Instance of a game. */
    private Game game;

    /** Username of a player */
    private String username = "test";

    /** Color of a player. */
    private Color color = new Color(255,255,255);

    /** Instance of a spaceship. */
    private Spaceship ship = new Spaceship(username,color,0,null);

    /** Sets up a new Single Player Game. */
    public void setUp(){
        game = new Game(color,true);
    }

    /** The constructor should create a player. */
    @Test
    public void GameTest(){
        setUp();
        assertEquals(1,game.getSpaceships().size());
    }

    /**
     * Add a player to the game.
     * Note that the constructor already adds a player.
     */
    @Test
    public void addSpaceshipTest(){
        setUp();
        game.addSpaceship(ship);
        assertEquals(2,game.getSpaceships().size());
    }

    /** Add an Asteroid to the game. */
    @Test
    public void addRandomAsteroidTest(){
        setUp();
        game.addRandomAsteroid();
        assertEquals(1,game.getAsteroids().size());
        game.addRandomAsteroid();
        game.addRandomAsteroid();
        assertEquals(3,game.getAsteroids().size());
    }

    /** Add a new bullet to the game. */
    @Test
    public void addBulletTest(){
        setUp();
        Collection bullets = game.getBullets();
        Bullet b = new Bullet(ship,ship.getLocation(),0,0);
        bullets.add(b);
        game.setBullets(bullets);
        assertEquals(1,game.getBullets().size());
        bullets.add(b);
        bullets.add(b);
        game.setBullets(bullets);
        assertEquals(3,game.getBullets().size());
    }

    /** Tests the username of the player. */
    @Test
    public void getUsernameTest(){
        assertEquals("test",ship.getUsername());
    }

    /** Tests the color of the player. */
    @Test
    public void getColorTest(){
        assertEquals(new Color(255,255,255),ship.getColor());
    }

    /** Increases the score of the player. */
    @Test
    public void increaseScoreTest(){
        assertEquals(0,ship.getScore());
        ship.increaseScore();
        ship.increaseScore();
        assertEquals(2,ship.getScore());
        ship.reinit();
        assertEquals(0,ship.getScore());
    }

    /** Tests the reinitialisation of a player. */
    @Test
    public void reinitTest(){
        assertEquals(0,ship.getScore());
        assertEquals("test",ship.getUsername());
        assertFalse(ship.isAccelerating());
        assertFalse(ship.isFiring());
        assertFalse(ship.isDestroyed());

        ship.increaseScore();
        ship.setUp(true);
        ship.setIsFiring(true);

        assertEquals(1,ship.getScore());
        assertEquals("test",ship.getUsername());
        assertTrue(ship.isAccelerating());
        assertTrue(ship.isFiring());
        assertFalse(ship.isDestroyed());

        ship.reinit();

        assertEquals(0,ship.getScore());
        assertEquals("test",ship.getUsername());
        assertFalse(ship.isAccelerating());
        assertFalse(ship.isFiring());
        assertFalse(ship.isDestroyed());

    }

}

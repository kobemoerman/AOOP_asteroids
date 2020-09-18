package aoop.asteroids.model;

import aoop.asteroids.database.ConnectionDB;
import aoop.asteroids.packet.PacketControl;
import aoop.asteroids.controller.PlayerSingle;

import java.awt.*;
import java.io.Serializable;
import java.lang.Runnable;
import java.net.InetAddress;
import java.util.*;

/**
 *	The game class is the backbone of all simulations of the asteroid game. It 
 *	contains all game object and keeps track of some other required variables 
 *	in order to specify game rules.
 *	<p>
 *	The game rules are as follows:
 *	<ul>
 *		<li> All game objects are updated according to their own rules every 
 *			game tick. </li>
 *		<li> Every 200th game tick a new asteroid is spawn. An asteroid cannot 
 *			spawn within a 50 pixel radius of the player. </li>
 *		<li> There is a maximum amount of asteroids that are allowed to be 
 *			active simultaneously. Asteroids that spawn from destroying a 
 *			larger asteroid do count towards this maximum, but are allowed to 
 *			spawn if maximum is exceeded. </li>
 *		<li> Destroying an asteroid spawns two smaller asteroids. I.e. large 
 *			asteroids spawn two medium asteroids and medium asteroids spawn two 
 *			small asteroids upon destruction. </li>
 *		<li> The player dies upon colliding with either a bullet or an
 *			asteroid. </li>
 *		<li> Destroying every 5th asteroid increases the asteroid limit by 1, 
 *			increasing the difficulty. </li>
 *	</ul>
 *	<p>
 *	This class implements Runnable, so all simulations will be run in its own 
 *	thread. This class extends Observable in order to notify the view element 
 *	of the program, without keeping a reference to those objects.
 *
 *	@author Yannick Stoffers
 */
public class Game extends Observable implements Runnable, Serializable {

	/** Game ticks. */
	private int tickNum = 0;

	/** Port the game is on. */
	private int port = 0;

	/** IP the game is on. */
	private InetAddress ip = null;

	/** List of spaceships. */
	private Collection<Spaceship> spaceships;

	/** List of bullets. */
	private Collection <Bullet> bullets;

	/** List of asteroids. */
	private Collection <Asteroid> asteroids;

	/** Random number generator. */
	private static Random rng;

	/** Game tick counter for spawning random asteroids. */
	private int cycleCounter;

	/** Asteroid limit. */
	private int asteroidsLimit;

	/** Determines if single-player(true) or multi-player(false) */
	private boolean gamemode;

	/** Start the game when there are at least 2 players */
	private boolean start = false;

	/** The last tick number the game was updated on */
	private int lastUpdate;

	/** 
	 *	Indicates whether the a new game is about to be started. 
	 *
	 *	@see #run()
	 */
	private boolean aborted;

	/**
	 * Initializes a new multi-player game from scratch.
	 *
	 * @param bool mode of the current game.
	 */
	public Game (Boolean bool, InetAddress ip, int port) {
		Game.rng = new Random ();
		this.spaceships = new ArrayList<>();
		this.initGameData ();
		this.gamemode = bool;
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Initializes a new single-player game from scratch.
	 *
	 * @param color color of the spaceship.
	 * @param bool mode of the current game.
	 */
	public Game(Color color, Boolean bool) {
		Game.rng = new Random ();
		this.spaceships = new ArrayList<>();
		this.initGameData ();
		this.gamemode = bool;
		this.spaceships.add(new Spaceship("",color,this.port,this.ip));
	}

	/** Sets all game data to hold the values of a new game. */
	public void initGameData () {
		this.aborted = false;
		this.start = false;
		this.cycleCounter = 0;
		this.asteroidsLimit = 7;
		this.bullets = new ArrayList <> ();
		this.asteroids = new ArrayList <> ();
		for (Spaceship sp : spaceships) sp.reinit();
		this.lastUpdate = 0;
	}

	/**
	 * Adds a spaceship to the game.
	 * @param s the spaceship to be added.
	 */
	public void addSpaceship(Spaceship s){
		spaceships.add(s);
		this.setChanged ();
		this.notifyObservers ();
	}

	/**
	 *	Links the given controller to the spaceship.
	 *
	 *	@param p the controller that is supposed to control the spaceship.
	 */
	public void linkController (PlayerSingle p) {
		for (Spaceship sp : spaceships) p.addShip(sp);
	}

	/** 
	 *	Returns a clone of the spaceship, preserving encapsulation. 
	 *
	 *	@return a clone the spaceship.
	 */
	public Collection <Spaceship> getSpaceships () {
		Collection <Spaceship> c = new ArrayList <> ();
		for (Spaceship sp : this.spaceships) c.add (sp.clone());
		return c;
	}

	/** 
	 *	Returns a clone of the asteroid set, preserving encapsulation.
	 *
	 *	@return a clone of the asteroid set.
	 */
	public Collection <Asteroid> getAsteroids () {
		Collection <Asteroid> c = new ArrayList <> ();
		for (Asteroid a : this.asteroids) c.add (a.clone ());
		return c;
	}

	/** 
	 *	Returns a clone of the bullet set, preserving encapsulation.
	 *
	 *	@return a clone of the bullet set.
	 */
	public Collection <Bullet> getBullets () {
		Collection <Bullet> c = new ArrayList <> ();
		for (Bullet b : this.bullets) c.add (b.clone ());
		return c;
	}

	/**
	 *	Method invoked at every game tick. It updates all game objects first. 
	 *	Then it adds a bullet if the player is firing. Afterwards it checks all 
	 *	objects for collisions and removes the destroyed objects. Finally the 
	 *	game tick counter is updated and a new asteroid is spawn upon every 
	 *	200th game tick.
	 */
	private void update () {
		for (Asteroid a : this.asteroids) a.nextStep ();
		for (Bullet b : this.bullets) b.nextStep ();

		if (spaceships.size() >= 4) this.start = true;
		if (start) tickNum++;

		for (Spaceship ship : this.spaceships) {
			ship.nextStep();
			if (ship.isFiring() && ((start && tickNum > 25*10) || gamemode)) {
				double direction = ship.getDirection();
				this.bullets.add(new Bullet(ship,ship.getLocation(), ship.getVelocityX() + Math.sin(direction) * 15, ship.getVelocityY() - Math.cos(direction) * 15));
				ship.setFired();
			}

			this.checkCollisions ();
			this.removeDestroyedObjects ();
		}

		if ((this.cycleCounter == 0 && this.asteroids.size () < this.asteroidsLimit) && (start || gamemode)) this.addRandomAsteroid();
		this.cycleCounter++;
		this.cycleCounter %= 100;

		this.setChanged ();
		this.notifyObservers ();
	}

	/** 
	 *	Adds a randomly sized asteroid at least 50 pixels removed from the 
	 *	player.
	 */
	public void addRandomAsteroid () {
		int prob = Game.rng.nextInt (3000);
		Point loc;
		int x, y;
		do {
			loc = new Point (Game.rng.nextInt (800), Game.rng.nextInt (800));
			x = loc.x;
			y = loc.y;
		} while (Math.sqrt (x * x + y * y) < 50);

		if (prob < 1000)		this.asteroids.add (new LargeAsteroid  (loc, Game.rng.nextDouble () * 6 - 3, Game.rng.nextDouble () * 6 - 3));
		else if (prob < 2000)	this.asteroids.add (new MediumAsteroid (loc, Game.rng.nextDouble () * 6 - 3, Game.rng.nextDouble () * 6 - 3));
		else					this.asteroids.add (new SmallAsteroid  (loc, Game.rng.nextDouble () * 6 - 3, Game.rng.nextDouble () * 6 - 3));
	}

	/** 
	 *	Checks all objects for collisions and marks them as destroyed upon
	 *	collision. All objects can collide with objects of a different type, 
	 *	but not with objects of the same type. I.e. bullets cannot collide with 
	 *	bullets etc.
	 */
	private void checkCollisions () {
		// Destroy all objects that collide.
		for (Bullet b : this.bullets) {
			// For all bullets.
			for (Asteroid a : this.asteroids) {
				// Check all bullet/asteroid combinations.
				if (a.collides (b))	{
					// Collision -> destroy both objects.
					b.destroy ();
					a.destroy ();
				}
			}

			for (Spaceship s : this.spaceships) {
				// Check all bullet/spaceship combinations.
				if (b.collides(s)) {
					// Collision with player -> destroy both objects
					ConnectionDB db = new ConnectionDB();
					db.setDataDB(s.getUsername(),s.getScore());
					b.destroy();
					s.destroy();
					if (!gamemode) increaseScoreMulti(b.getShip());
				}
			}
		}

		for (Asteroid a : this.asteroids) {
			// For all asteroids, no cross check with bullets required.
			for (Spaceship s : this.spaceships) {
				// Check all asteroids/spaceship combinations.
				if (a.collides(s)) {
					// Collision with player -> destroy both objects.
					a.destroy();
					s.destroy();
				}
			}
		}
	}

	/**
	 * Returns the current gamemode.
	 * @return true (single-player) or false (multi-player).
	 */
	public boolean getGamemode (){
		return this.gamemode;
	}

	/**
	 * Returns the IP address of the game.
	 * @return ip.
	 */
	public InetAddress getIp (){
		return this.ip;
	}

	/**
	 * Returns the port of the game.
	 * @return port.
	 */
	public int getPort (){
		return this.port;
	}

	/**
	 * 	Increases the score of the player by one when he hits another player
	 * 	and updates asteroid limit.
	 */
	private void increaseScoreMulti (Spaceship sp) {
		sp.increaseScore ();
		this.asteroidsLimit++;
	}

	/**
	 * 	Increases the score of the player by one if he hits an asteroid
	 * 	and updates asteroid limit when required.
	 */
	private void increaseScoreSingle () {
		Spaceship sp = (Spaceship) spaceships.toArray()[0];
		sp.increaseScore();
		if (sp.getScore () % 5 == 0) this.asteroidsLimit++;
	}

	/**
	 *	Removes all destroyed objects. Destroyed asteroids increase the score 
	 *	and spawn two smaller asteroids if it wasn't a small asteroid. New 
	 *	asteroids are faster than their predecessor and travel in opposite 
	 *	direction.
	 */
	private void removeDestroyedObjects () {
		Collection <Asteroid> newAsts = new ArrayList <> ();
		for (Asteroid a : this.asteroids) {
			if (a.isDestroyed ()) {
				if (gamemode) this.increaseScoreSingle ();
				Collection <Asteroid> successors = a.getSuccessors ();
				newAsts.addAll (successors);
			} else newAsts.add (a);
		}
		this.asteroids = newAsts;

		Collection <Bullet> newBuls = new ArrayList <> ();
		for (Bullet b : this.bullets) if (!b.isDestroyed ()) newBuls.add (b);
		this.bullets = newBuls;

		if (spaceships.size() > 1) {
			Collection<Spaceship> newSpas = new ArrayList<>();
			for (Spaceship sp : this.spaceships) if (!sp.isDestroyed()) newSpas.add(sp);
			this.spaceships = newSpas;
		}
	}

	/**
	 *	Returns whether the game is over. The game is over when the spaceship 
	 *	is destroyed.
	 *
	 *	@return true if game is over, false otherwise.
	 */ 
	private boolean gameOver () {
		if (gamemode) {
			Spaceship sp = (Spaceship) spaceships.toArray()[0];
			return sp.isDestroyed();
		}
		else {
			if ((spaceships.size() == 1) && start) {
				Spaceship s = (Spaceship) spaceships.toArray()[0];
				ConnectionDB db = new ConnectionDB();
				db.setDataDB(s.getUsername(),s.getScore()+4);
				this.abort();
				this.start = false;
				return true;
			}
			return false;
		}
	}

	/** 
	 *	Aborts the game. 
	 *
	 *	@see #run()
	 */
	public void abort () {
		this.aborted = true;
	}

	/**
	 *	This method allows this object to run in its own thread, making sure 
	 *	that the same thread will not perform non essential computations for 
	 *	the game. The thread will not stop running until the program is quit. 
	 *	If the game is aborted or the player died, it will wait 100 
	 *	milliseconds before reevaluating and continuing the simulation. 
	 *	<p>
	 *	While the game is not aborted and the player is still alive, it will 
	 *	measure the time it takes the program to perform a game tick and wait 
	 *	40 minus execution time milliseconds to do it all over again. This 
	 *	allows the game to update every 40th millisecond, thus keeping a steady 
	 *	25 frames per second. 
	 *	<p>
	 *	Decrease waiting time to increase fps. Note 
	 *	however, that all game mechanics will be faster as well. I.e. asteroids 
	 *	will travel faster, bullets will travel faster and the spaceship may 
	 *	not be as easy to control.
	 */
	public void run () {
		// Update -> sleep -> update -> sleep -> etc...
		long executionTime, sleepTime;
		while (true) {
			if (!this.gameOver () && !this.aborted) {
				executionTime = System.currentTimeMillis ();
				this.update ();
				executionTime -= System.currentTimeMillis ();
				sleepTime = Math.max (0, 40 + executionTime);
			} else {
				this.abort();
				sleepTime = 100;
			}

			try {
				Thread.sleep (sleepTime);
			} catch (InterruptedException e) {
				System.err.println ("Could not perform action: Thread.sleep(...)");
				System.err.println ("The thread that needed to sleep is the game thread, responsible for the game loop (update -> wait -> update -> etc).");
				e.printStackTrace ();
			}
		}
	}

	/**
	 * Updates the list of ships.
	 * @param s collection of ships.
	 */
	public void setShip(Collection<Spaceship> s){
		this.spaceships = s;
		this.setChanged ();
		this.notifyObservers();
	}

	/**
	 * Updates the list of bullets.
	 * @param b collection of bullets.
	 */
	public void setBullets(Collection<Bullet> b){
		this.bullets = b;
		this.setChanged ();
		this.notifyObservers();
	}

	/**
	 * Updates the list of asteroids.
	 * @param a collection of asteroids.
	 */
	public void setAsteroids(Collection<Asteroid> a){
		this.asteroids = a;
		this.setChanged ();
		this.notifyObservers();
	}

	/**
	 * Updates the functions of all spaceships.
	 * @param cp packet from server
	 */
	public void updateControl(PacketControl cp){
		for (Spaceship s: spaceships) {
			s.receiveControlls(cp);
		}
	}

	/**
	 * Returns the last game update.
	 * @return update.
	 */
	public int getLastUpdate(){
		return this.lastUpdate;
	}

	/**
	 * Updates the last game update.
	 * @param i new update.
	 */
	public void setLastUpdate(int i){
		this.lastUpdate = i;
	}

	/**
	 * Returns the game tick.
	 * @return tick.
	 */
    public int getTick(){
    	return this.tickNum;
    }
}

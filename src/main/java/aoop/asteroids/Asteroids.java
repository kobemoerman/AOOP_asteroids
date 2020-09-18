package aoop.asteroids;

import aoop.asteroids.view.MenuFrame;

/**
 *	ConnectionDB class of the Asteroids program.
 *	<p>
 *	Asteroids is simple game, in which the player is represented by a small 
 *	spaceship. If playing single-player, the goal is to destroy as many asteroids
 *	as possible and thus survive for as long as possible. Otherwise, if playing against
 *  other players, your goal is to be the last spaceship remaining.
 *
 *	@author Yannick Stoffers
 */
public class Asteroids {

	/** Constructs a new instance of the program. */
	private Asteroids ()
	{
		new MenuFrame();
	}

	/** 
	 *	ConnectionDB function.
	 *
	 *	@param args input arguments.
	 */
	public static void main (String [] args) {
		if (System.getProperty ("os.name").contains ("Mac")) {
			System.setProperty ("apple.laf.useScreenMenuBar", "true");
		}
		new Asteroids ();
		
	}
	
}

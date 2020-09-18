package aoop.asteroids.controller;

import aoop.asteroids.model.Spaceship;

/** PlayerSingle influences the behaviour of the spaceship. */

public class PlayerSingle extends Player {

	/** The spaceship that is being influenced. */
	private Spaceship ship;

	/**
	 *	This method allows one to add a ship to an otherwise useless object.
	 *	@param ship this is the spaceship that this class will influence from now on.
	 */
	public void addShip (Spaceship ship) {
		this.ship = ship;
	}

	/** Updates the state of the spaceship. */
	@Override
	protected void updateShip() {
		this.ship.setIsFiring(this.firing);
		this.ship.setUp(this.up);
		this.ship.setLeft(this.left);
		this.ship.setRight(this.right);
	}
}

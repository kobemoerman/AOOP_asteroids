package aoop.asteroids.view;

import aoop.asteroids.model.Asteroid;
import aoop.asteroids.model.Bullet;
import aoop.asteroids.model.Game;
import aoop.asteroids.model.Spaceship;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.lang.Object;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;

/**
 *	AsteroidsPanel extends JPanel and thus provides the actual graphical 
 *	representation of the game model.
 *
 *	@author Yannick Stoffers
 */
public class AsteroidsPanel extends JPanel {

	/** serialVersionUID */
	public static final long serialVersionUID = 4L;

	/** Game model. */
	private Game game;

	/** 
	 *	Constructs a new game panel, based on the given model.
	 *
	 *	@param game game model.
	 */
	public AsteroidsPanel (Game game) {
		this.game = game;
		this.game.addObserver ((o, arg) -> AsteroidsPanel.this.repaint ());
	}
	
	/**
	 *	Method for refreshing the GUI.
	 *
	 *	@param g graphics instance to use.
	 */
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent (g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.setBackground (Color.black);

		this.paintSpaceship (g2);
		this.paintAsteroids (g2);
		this.paintBullets (g2);
		this.paintGameInfo (g2);
	}

	/**
	 *	Draws all bullets in the GUI as a yellow circle.
	 *
	 *	@param g graphics instance to use.
	 */
	private void paintBullets (Graphics2D g) {
		g.setColor(Color.yellow);

		for (Bullet b : this.game.getBullets ())
		    g.drawOval (b.getLocation ().x - 2, b.getLocation ().y - 2, 5, 5);
	}

	/**
	 *	Draws all asteroids in the GUI as a filled gray circle.
	 *
	 *	@param g graphics instance to use.
	 */
	private void paintAsteroids (Graphics2D g)	{
		g.setColor (Color.GRAY);

		for (Asteroid a : this.game.getAsteroids ()) {
			Ellipse2D.Double e = new Ellipse2D.Double ();
			e.setFrame (a.getLocation ().x - a.getRadius (), a.getLocation ().y - a.getRadius (), 2 * a.getRadius (), 2 * a.getRadius ());
			g.fill (e);
		}
	}

	/**
	 *	Draws the player in the GUI as a triangle. If the
	 *	player is accelerating a yellow triangle is drawn as a simple
	 *	representation of flames from the exhaust.
	 *
	 *	@param g graphics instance to use.
	 */
	private void paintSpaceship (Graphics2D g)	{
		int i = 0;
		for (Spaceship s : this.game.getSpaceships()) {
			// Draw body of the spaceship
			Polygon p = new Polygon();
			p.addPoint((int) (s.getLocation().x + Math.sin(s.getDirection()) * 20), (int) (s.getLocation().y - Math.cos(s.getDirection()) * 20));
			p.addPoint((int) (s.getLocation().x + Math.sin(s.getDirection() + 0.8 * Math.PI) * 20), (int) (s.getLocation().y - Math.cos(s.getDirection() + 0.8 * Math.PI) * 20));
			p.addPoint((int) (s.getLocation().x + Math.sin(s.getDirection() + 1.2 * Math.PI) * 20), (int) (s.getLocation().y - Math.cos(s.getDirection() + 1.2 * Math.PI) * 20));

			g.setColor(s.getColor());
			g.fill(p);
			g.setColor(Color.WHITE);
			g.draw(p);
			drawShipName(g, s, s.getUsername());

			// Spaceship accelerating -> continue, otherwise abort.
			if (s.isAccelerating()) {
				// Draw flame at the exhaust
				p = new Polygon();
				p.addPoint((int) (s.getLocation().x - Math.sin(s.getDirection()) * 25), (int) (s.getLocation().y + Math.cos(s.getDirection()) * 25));
				p.addPoint((int) (s.getLocation().x + Math.sin(s.getDirection() + 0.9 * Math.PI) * 15), (int) (s.getLocation().y - Math.cos(s.getDirection() + 0.9 * Math.PI) * 15));
				p.addPoint((int) (s.getLocation().x + Math.sin(s.getDirection() + 1.1 * Math.PI) * 15), (int) (s.getLocation().y - Math.cos(s.getDirection() + 1.1 * Math.PI) * 15));
				g.setColor(Color.yellow);
				g.fill(p);
			}
			g.setColor (s.getColor());
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString("[" + String.valueOf(s.getScore()) + "]  " + s.getUsername(), 20, 20+i);
			i = i + 20;
		}

	}

	private void paintGameInfo (Graphics2D g){
		if (!game.getGamemode()) {
			g.drawString("Game starts when 4 players are connected", 270, 700);
			g.drawString("Current lobby ["+game.getIp().getHostAddress()+":"+game.getPort()+"]", 300, 720);
		}
	}

	/**
	 * Displays the players username above its spaceship.
	 *
	 * @param g graphics instance to use.
	 * @param s spaceship in use.
	 * @param text username of the spaceship.
	 */
	private void drawShipName(Graphics2D g, Spaceship s, String text)	{
		Font font = new Font("Arial", Font.PLAIN, 20);
		FontMetrics metrics = g.getFontMetrics(font);
		int x = s.getLocationX() - (metrics.stringWidth(text) / 2);
		int y = s.getLocationY() - (metrics.getAscent() * 2);
		g.setFont(font);
		g.drawString(text,x,y);
	}

}

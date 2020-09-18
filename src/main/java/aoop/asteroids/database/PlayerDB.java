package aoop.asteroids.database;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class PlayerDB implements Serializable {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Id of the object. */
    @Id @GeneratedValue
    private long id;

    /** Data to save in the object. */
    private String x;

    /** Data to save in the object. */
    private int y;

    /**
     * Initialises the object with the appropriate information.
     *
     * @param username username of the player.
     * @param score score of the player.
     */
    PlayerDB(String username, int score) {
        this.x = username;
        this.y = score;
    }

    /**
     * Get the object's id;
     * @return id.
     */
    public Long getId () {
        return id;
    }

    /**
     * Returns the name of the player.
     * @return x.
     */
    public String getX () {
        return x;
    }

    /**
     * Returns the score of the player.
     * @return y.
     */
    public int getY () {
        return y;
    }

    /**
     * Converts the object into a string.
     * @return concatenation of the data.
     */
    @Override
    public String toString () {
        return String.format("%s, %d",this.x, this.y);
    }
}

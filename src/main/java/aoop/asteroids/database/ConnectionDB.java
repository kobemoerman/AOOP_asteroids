package aoop.asteroids.database;

import javax.persistence.*;
import java.util.*;

public class ConnectionDB {

    private EntityManagerFactory emf;
    private EntityManager em;

    /** Create a connection to the database. */
    public ConnectionDB () {
        this.emf = Persistence.createEntityManagerFactory("$objectdb/db/highscore.odb");
        this.em = emf.createEntityManager();
    }

    /** Close the connection to the database. */
    private void closeConnection () {
        this.em.close();
        this.emf.close();
    }

    /** Store the object inside the database */
    public void setDataDB(String username, int score) {
        em.getTransaction().begin();
        PlayerDB p = new PlayerDB(username,score);
        em.persist(p);
        em.getTransaction().commit();
        closeConnection();
    }

    /** Retrieve the object from the database. */
    public List receiveDataDB() {
        TypedQuery<PlayerDB> query = em.createQuery("SELECT p FROM PlayerDB p ORDER by y desc", PlayerDB.class);
        List<PlayerDB> results = query.getResultList();
        closeConnection();
        return results;
    }
}

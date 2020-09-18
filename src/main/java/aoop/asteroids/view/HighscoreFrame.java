package aoop.asteroids.view;

import aoop.asteroids.database.ConnectionDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class HighscoreFrame extends GameFrame {

    /** Maximum number of scores shown. */
    private int max;

    /** Panel displayed in the frame. */
    private JPanel panel;

    /** Quit action. */
    private AbstractAction backAction;

    /** Constructs a new Frame. Displays the high-scores of the database. */
    public HighscoreFrame () {
        this.setTitle("High Score");
        this.setSize(300,230);
        this.setLocationRelativeTo(null);

        createTable();

        this.setContentPane(panel);

        this.initActions();

        JMenuBar mb = new JMenuBar ();
        JMenu m = new JMenu ("Info");
        mb.add (m);
        m.add (this.backAction);
        this.setJMenuBar (mb);

        this.pack();
        this.setFocusable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /** Creates the table of the top 10 players. */
    private void createTable() {
        this.panel = new JPanel(new GridLayout(1,0));

        ConnectionDB db = new ConnectionDB();
        List hs = db.receiveDataDB();

        if (hs.size() > 10) max = 10;
        else max = hs.size();

        Object[][] row = new Object[10][2];
        for (int i = 0; i < max; i++){
            String[] arr = hs.get(i).toString().split(",");
            System.arraycopy(arr, 0, row[i], 0, 2);
        }

        String[] column = {"Username", "Total Kills"};
        JTable table = new JTable(row, column);
        table.setPreferredScrollableViewportSize(new Dimension(300, 160));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        this.panel.add(scrollPane);
        this.panel.setOpaque(true);
    }

    /** Initializes the quit game action. */
    private void initActions()
    {
        // Quits the application
        this.backAction = new AbstractAction ("Back")
        {
            public static final long serialVersionUID = 2L;

            @Override
            public void actionPerformed (ActionEvent arg0)
            {
                switchToMenu();
            }
        };
    }
}

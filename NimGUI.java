import javax.swing.*;
import javax.swing.border.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.*;
import java.text.*;

/**
 * Graphical user interface for a 3 stack Nim game. The players can be human or
 * can be strategies provided by methods in the NimPlayer class. See that class
 * for more details on implementing strategies.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version Oct 5, 2007
 */
public class NimGUI extends JFrame implements ActionListener {

    private static final long serialVersionUID = 0;

    /*
     * Need to keep 3 stacks that are shared by the players and 3 reference
     * stacks that are actually displayed. When a method in the player is called
     * it changes the player stacks which the code in this class then compares
     * to the reference stacks for validity. If the move is valid, then the
     * display stacks are changed to match. If the move is invalid the player
     * loses. If a human player changes a stack, then the players' stacks are
     * changed to match the display stacks.
     */
    private NimStack playerStack1;

    private NimStack playerStack2;

    private NimStack playerStack3;

    private NimStack dispStack1;

    private NimStack dispStack2;

    private NimStack dispStack3;

    private NimStackDisplay disp1;

    private NimStackDisplay disp2;

    private NimStackDisplay disp3;

    private NimPlayer player1;

    private NimPlayer player2;

    private JPanel[] playerPanel;

    private JComboBox[] playerStrats;

    private JTextArea[] playerStats;

    private JLabel[] playerLabel;

    private JButton playStop;

    // private JButton reset;
    private JTextField gamesField;

    private JTextField trialsField;

    private JComboBox animationSpeed;

    private TrialRunner runner;

    private static final Random rnd = new Random();

    /**
     * Construct and display a new GUI for the Nim game. This creates 2
     * NimPlayers and 3 NimStacks that are shared by them.
     */
    public NimGUI() {
        dispStack1 = new NimStack(rnd.nextInt(6) + 5); // 5 - 10 chips in
        // stack.
        disp1 = new NimStackDisplay(dispStack1);
        playerStack1 = new NimStack(dispStack1.getNumChips());
        // System.out.println(stack1.getNumChips());

        dispStack2 = new NimStack(rnd.nextInt(6) + 5);
        disp2 = new NimStackDisplay(dispStack2);
        playerStack2 = new NimStack(dispStack2.getNumChips());
        // System.out.println(stack2.getNumChips());

        dispStack3 = new NimStack(rnd.nextInt(6) + 5);
        disp3 = new NimStackDisplay(dispStack3);
        playerStack3 = new NimStack(dispStack3.getNumChips());
        // System.out.println(stack3.getNumChips());

        player1 = new NimPlayer(playerStack1, playerStack2, playerStack3);
        player2 = new NimPlayer(playerStack1, playerStack2, playerStack3);

        playerStrats = new JComboBox[3]; // index 0 is unused.
        playerStats = new JTextArea[3];
        playerLabel = new JLabel[3];
        playerPanel = new JPanel[3];

        setLayout(new BorderLayout());
        add(getPlayersAndStacks(), BorderLayout.CENTER);
        add(getControls(), BorderLayout.SOUTH);

        setTitle("3 Stack Nim");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }

    private Box getControls() {
        Box controls = Box.createHorizontalBox();
        controls.add(Box.createHorizontalGlue());

        playStop = new JButton("Play");
        playStop.addActionListener(this);
        controls.add(playStop);

        controls.add(Box.createHorizontalStrut(10));

        controls.add(new JLabel("Games: "));
        gamesField = new JTextField("1", 3);
        gamesField.setMaximumSize(gamesField.getPreferredSize());
        controls.add(gamesField);

        controls.add(Box.createHorizontalStrut(10));

        controls.add(new JLabel("Trials: "));
        trialsField = new JTextField("1", 3);
        trialsField.setMaximumSize(gamesField.getPreferredSize());
        controls.add(trialsField);

        controls.add(Box.createHorizontalStrut(10));
        controls.add(new JLabel("Animation: "));
        animationSpeed = new JComboBox(new String[] { "Normal", "Fast", "Super Fast" });
        animationSpeed.setPreferredSize(animationSpeed.getMinimumSize());
        animationSpeed.setMaximumSize(animationSpeed.getMinimumSize());
        animationSpeed.addActionListener(this);
        controls.add(animationSpeed);

        /*
         * controls.add(Box.createHorizontalStrut(10));
         * 
         * reset = new JButton("Reset"); reset.addActionListener(this);
         * controls.add(reset);
         */

        controls.add(Box.createHorizontalGlue());
        return controls;
    }

    private Box getPlayersAndStacks() {
        Box pandb = Box.createHorizontalBox();

        JPanel player1Panel = getPlayerPanel(1);
        player1Panel.setAlignmentY(Component.TOP_ALIGNMENT);
        pandb.add(player1Panel);

        pandb.add(Box.createHorizontalStrut(10));

        JPanel stacks = getNimStacks();
        stacks.setAlignmentY(Component.TOP_ALIGNMENT);
        pandb.add(stacks);

        pandb.add(Box.createHorizontalStrut(10));

        JPanel player2Panel = getPlayerPanel(2);
        player2Panel.setAlignmentY(Component.TOP_ALIGNMENT);
        pandb.add(player2Panel);

        return pandb;
    }

    private JPanel getNimStacks() {
        JPanel stackPanel = new JPanel();
        Box stacks = Box.createHorizontalBox();
        stacks.add(disp1);
        stacks.add(Box.createHorizontalStrut(10));
        stacks.add(disp2);
        stacks.add(Box.createHorizontalStrut(10));
        stacks.add(disp3);
        stackPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        stackPanel.add(stacks);
        return stackPanel;
    }

    private JPanel getPlayerPanel(int playerNum) {

        playerPanel[playerNum] = new JPanel();

        Box player = Box.createVerticalBox();
        player.add(Box.createVerticalStrut(10));
        playerLabel[playerNum] = new JLabel("Player #" + playerNum + ":");
        playerLabel[playerNum].setAlignmentX(Component.LEFT_ALIGNMENT);
        player.add(playerLabel[playerNum]);
        player.add(Box.createVerticalStrut(10));
        playerStrats[playerNum] = getStrategyList();
        playerStrats[playerNum].setAlignmentX(Component.LEFT_ALIGNMENT);
        player.add(playerStrats[playerNum]);
        player.add(Box.createVerticalStrut(10));
        JPanel stats = getPlayerStats(playerNum);
        stats.setAlignmentX(Component.LEFT_ALIGNMENT);
        player.add(stats);

        player.setMaximumSize(player.getPreferredSize());
        player.setMinimumSize(player.getPreferredSize());

        playerPanel[playerNum].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        playerPanel[playerNum].add(player);

        return playerPanel[playerNum];
    }

    private JPanel getPlayerStats(int playerNum) {

        JPanel stats = new JPanel();

        playerStats[playerNum] = new JTextArea("trial\twins\tlosses\n");

        playerStats[playerNum].setFont(new Font("Monospaced", Font.PLAIN, 12));
        playerStats[playerNum].setEditable(false);

        JScrollPane scroller = new JScrollPane(playerStats[playerNum]);
        scroller.setPreferredSize(new Dimension(200, 200));
        stats.add(scroller);

        return stats;
    }

    private JComboBox getStrategyList() {
        NimPlayer p = new NimPlayer(null, null, null);
        Method[] m = p.getClass().getDeclaredMethods();
        String[] names = new String[m.length];

        int j = 0;
        for (int i = 0; i < m.length; i++) {
            if (m[i].getReturnType().getName().equals("void")) {
                names[j] = m[i].getName();
                j++;
            }
        }

        String[] trimmedNames = new String[j + 1];
        trimmedNames[0] = "human";
        for (int i = 0; i < j; i++) {
            trimmedNames[i + 1] = names[i];
        }

        JComboBox strats = new JComboBox(trimmedNames);
        strats.setPreferredSize(strats.getMinimumSize());
        strats.setMaximumSize(strats.getMinimumSize());

        return strats;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Play")) {
            playerStats[1].setText("trial\twins\tlosses\n");
            playerStats[2].setText("trial\twins\tlosses\n");
            playStop.setText("Stop");
            // reset.setEnabled(false);
            gamesField.setEnabled(false);
            trialsField.setEnabled(false);
            playerStrats[1].setEnabled(false);
            playerStrats[2].setEnabled(false);

            runner = new TrialRunner();
            runner.start();
        }
        else if (e.getActionCommand().equals("Stop")) {
            if (runner != null) {
                runner.halt();
                disp1.releaseWaitingThread();
            }

            playStop.setText("Play");
            // reset.setEnabled(true);
            gamesField.setEnabled(true);
            trialsField.setEnabled(true);
            playerStrats[1].setEnabled(true);
            playerStrats[2].setEnabled(true);
        }
        else if (e.getActionCommand().equals("comboBoxChanged")) {
            disp1.setFastAnimation(animationSpeed.getSelectedIndex());
        }
    }

    /*
     * Run the specified number of trials, each with the specified number of
     * games between the two strategies. The strategies alternate going first.
     * Each game is played on a new set of 3 random stacks.
     */
    private class TrialRunner extends Thread {

        private int trials;

        private int games;

        private String strat1;

        private String strat2;

        private boolean valid;

        private boolean halt;

        private double[] p1Wins;

        private double[] p1Losses;

        private double[] p2Wins;

        private double[] p2Losses;

        public TrialRunner() {
            halt = false;
            valid = true;
            try {
                games = Integer.parseInt(gamesField.getText());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid value for number of games");
                valid = false;
            }

            try {
                trials = Integer.parseInt(trialsField.getText());
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid value for number of trials");
                valid = false;
            }

            strat1 = (String) playerStrats[1].getSelectedItem();
            strat2 = (String) playerStrats[2].getSelectedItem();

            p1Wins = new double[trials];
            p2Wins = new double[trials];
            p1Losses = new double[trials];
            p2Losses = new double[trials];
        }

        public void halt() {
            halt = true;
        }

        public void run() {
            if (valid) {
                int trialsRun = 0;
                while (!halt && trialsRun < trials) {
                    int gamesRun = 0;
                    int p1TrialWins = 0;
                    int p2TrialWins = 0;

                    while (!halt && gamesRun < games) {
                        if (getTotalChips() == 0) {
                            // new game so we need to reset the stacks.
                            dispStack1 = new NimStack(rnd.nextInt(6) + 5);
                            // 5 - 10 chips in stack.
                            disp1.setStack(dispStack1);
                            playerStack1 = new NimStack(dispStack1.getNumChips());

                            dispStack2 = new NimStack(rnd.nextInt(6) + 5);
                            disp2.setStack(dispStack2);
                            playerStack2 = new NimStack(dispStack2.getNumChips());

                            dispStack3 = new NimStack(rnd.nextInt(6) + 5);
                            disp3.setStack(dispStack3);
                            playerStack3 = new NimStack(dispStack3.getNumChips());

                            player1 = new NimPlayer(playerStack1, playerStack2, playerStack3);
                            player2 = new NimPlayer(playerStack1, playerStack2, playerStack3);
                        }

                        int winner = 0;
                        if (gamesRun % 2 == 0) {
                            winner = runGame(player1, strat1, player2, strat2, 1);
                        }
                        else {
                            winner = runGame(player1, strat1, player2, strat2, 2);
                        }

                        if (winner == 1) {
                            p1TrialWins++;
                        }
                        else {
                            p2TrialWins++;
                        }

                        gamesRun++;
                    }

                    p1Wins[trialsRun] = p1TrialWins;
                    p1Losses[trialsRun] = games - p1TrialWins;
                    p2Wins[trialsRun] = p2TrialWins;
                    p2Losses[trialsRun] = games - p2TrialWins;

                    playerStats[1].append(trialsRun + 1 + "\t" + (int) p1Wins[trialsRun] + "\t"
                            + (int) p1Losses[trialsRun] + "\n");
                    playerStats[2].append(trialsRun + 1 + "\t" + (int) p2Wins[trialsRun] + "\t"
                            + (int) p2Losses[trialsRun] + "\n");

                    trialsRun++;
                }

                // only print stats for complete runs with > 1 trail.
                if (trialsRun > 1 && trialsRun == trials) {
                    DecimalFormat df = new DecimalFormat("0.00");
                    playerStats[1].append("\nMean:\t" + df.format(BasicStatistics.getMean(p1Wins))
                            + "\t" + df.format(BasicStatistics.getMean(p1Losses)) + "\n");
                    playerStats[1].append("StDev:\t" + df.format(BasicStatistics.getStdDev(p1Wins))
                            + "\t" + df.format(BasicStatistics.getStdDev(p1Losses)));

                    playerStats[2].append("\nMean:\t" + df.format(BasicStatistics.getMean(p2Wins))
                            + "\t" + df.format(BasicStatistics.getMean(p2Losses)) + "\n");
                    playerStats[2].append("StDev:\t" + df.format(BasicStatistics.getStdDev(p2Wins))
                            + "\t" + df.format(BasicStatistics.getStdDev(p2Losses)));
                }
            }

            playStop.setText("Play");
            // reset.setEnabled(true);
            gamesField.setEnabled(true);
            trialsField.setEnabled(true);
            playerStrats[1].setEnabled(true);
            playerStrats[2].setEnabled(true);
            playerPanel[1].setBackground(null);
            playerPanel[2].setBackground(null);
        }

        private int getTotalChips() {
            return dispStack1.getNumChips() + dispStack2.getNumChips() + dispStack3.getNumChips();
        }

        /*
         * Return 1 if player 1 wins and 2 if player 2 wins.
         */
        private int runGame(NimPlayer player1, String strat1, NimPlayer player2, String strat2,
                int firstPlayer) {

            int p1Num = 1;
            NimPlayer p1Player = player1;
            String p1Strat = strat1;

            int p2Num = 2;
            NimPlayer p2Player = player2;
            String p2Strat = strat2;

            if (firstPlayer == 2) {
                p1Num = 2;
                p1Player = player2;
                p1Strat = strat2;

                p2Num = 1;
                p1Player = player1;
                p2Strat = strat1;
            }

            while (!halt && getTotalChips() > 0) {
                playerPanel[p1Num].setBackground(Color.green.darker());
                playerPanel[p2Num].setBackground(Color.red);
                boolean lost = runTurn(p1Player, p1Strat);

                if (lost) {
                    if (strat1.equals("human") || strat2.equals("human")) {
                        JOptionPane.showMessageDialog(null, "Player #" + p2Num + " Wins!");
                    }
                    return p2Num;
                }

                if ((!halt && !lost)) {
                    playerPanel[p2Num].setBackground(Color.green.darker());
                    playerPanel[p1Num].setBackground(Color.red);
                    lost = runTurn(p2Player, p2Strat);

                    if (lost) {
                        if (strat1.equals("human") || strat2.equals("human")) {
                            JOptionPane.showMessageDialog(null, "Player #" + p1Num + " Wins!");
                        }
                        return p1Num;
                    }
                }
            }

            // Make silly compiler happy.
            return 0;
        }

        /*
         * return true if the player loses on this turn.
         */
        private boolean runTurn(NimPlayer player, String strat) {
            if (strat.equals("human")) {
                return playHumanTurn();
            }
            else {
                return playCustomStratTurn(player, strat);
            }
        }

        /*
         * return true if the player loses on this turn.
         */
        private boolean playHumanTurn() {
            disp1.setClickEnabled(true); // one does all stacks.
            disp1.waitForRemove();

            // Synch the player stacks with the display stacks.
            if (playerStack1.getNumChips() != dispStack1.getNumChips()) {
                playerStack1.removeChips(playerStack1.getNumChips() - dispStack1.getNumChips());
            }
            else if (playerStack2.getNumChips() != dispStack2.getNumChips()) {
                playerStack2.removeChips(playerStack2.getNumChips() - dispStack2.getNumChips());
            }
            else if (playerStack3.getNumChips() != dispStack3.getNumChips()) {
                playerStack3.removeChips(playerStack3.getNumChips() - dispStack3.getNumChips());
            }

            if (getTotalChips() == 0) {
                return true;
            }
            else {
                return false;
            }
        }

        private boolean playCustomStratTurn(NimPlayer player, String strat) {
            try {
                Method m = player.getClass().getMethod(strat);
                m.invoke(player);
            }
            catch (Exception e) {
                System.out.println("Problem with the NimPlayer class. "
                        + "Unable to access method " + strat + ".");
                System.out.println(e);
                System.exit(-1);
            }

            boolean valid = makeMove();

            if (valid) {
                if (getTotalChips() == 0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                if (strat1.equals("human") || strat2.equals("human")) {
                    JOptionPane.showMessageDialog(null, "Current player attempted an invalid move!");
                }
                return true; // invalid move - you lose.
            }
        }

        /*
         * Return true if the move made by the player was valid. Exactly one
         * stack must be modified and it must have fewer chips than before the
         * move.
         */
        private boolean makeMove() {
            if (playerStack2.getNumChips() == dispStack2.getNumChips()
                    && playerStack3.getNumChips() == dispStack3.getNumChips()) {
                // stack 1 must be modified...
                if (playerStack1.getNumChips() < dispStack1.getNumChips()) {
                    disp1.removeChips(dispStack1.getNumChips() - playerStack1.getNumChips());
                    disp1.waitForRemove();
                    return true;
                }
                else {
                    return false;
                }
            }
            else if (playerStack1.getNumChips() == dispStack1.getNumChips()
                    && playerStack3.getNumChips() == dispStack3.getNumChips()) {
                // stack 2 must be modified...
                if (playerStack2.getNumChips() < dispStack2.getNumChips()) {
                    disp2.removeChips(dispStack2.getNumChips() - playerStack2.getNumChips());
                    disp2.waitForRemove();
                    return true;
                }
                else {
                    return false;
                }
            }
            else if (playerStack1.getNumChips() == dispStack1.getNumChips()
                    && playerStack2.getNumChips() == dispStack2.getNumChips()) {
                // stack 3 must be modified...
                if (playerStack3.getNumChips() < dispStack3.getNumChips()) {
                    disp3.removeChips(dispStack3.getNumChips() - playerStack3.getNumChips());
                    disp3.waitForRemove();
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                // no stack modified...
                return false;
            }
        }
    }

    public static void main(String[] args) {
        new NimGUI();
    }
}

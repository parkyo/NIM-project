import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.*;

/**
 * A display component for a NimStack. The display shows the number of chips on
 * the stack. The user can click on a chip to remove that chip and all of the
 * chips above it on the stack.
 * 
 * @author Grant Braught
 * @author Dickinson College
 * @version Oct 5, 2007
 */
public class NimStackDisplay extends JPanel {

    private static final long serialVersionUID = 0;

    private NimStack stack;

    private static boolean clickEnabled;  // disable all stacks!

    private int numToBlink;

    private static final int yStart = 250;

    private static final int chipHeight = 20;

    private static final int chipWidth = 80;

    private static final int chipGap = 5;

    private static final int numBlinks = 3;

    private static final long slowBlinkDelay = 200;
    
    private static final long fastBlinkDelay = 25;
    
    private static final long superFastBlinkDelay = 0;
    
    private static long blinkDelay = slowBlinkDelay;

    private static final Color blinkColor = Color.yellow.darker();

    private static final Color chipColor = Color.blue;

    // TODO: Convert this to a semaphore... so that if notify
    // happens before wait things still keep going.
    
    // Used to block while waiting for a move to complete.
    // static so as to wait and notify on any stack.
    private static final Semaphore barrier = new Semaphore(0);
    
    /**
     * Create a display for the specified NimStack
     * 
     * @param stack the stack to display.
     */
    public NimStackDisplay(NimStack stack) {
        this.stack = stack;
        this.addMouseListener(new NimStackClickHandler());
        clickEnabled = false;
        numToBlink = 0;
    }
    
    public void setStack(NimStack newStack) {
        stack = newStack;
        repaint();
    }
    
    public void setFastAnimation(int speed) {
        if (speed == 0) {
            blinkDelay = slowBlinkDelay;
        }
        else if (speed == 1) {
            blinkDelay = fastBlinkDelay;
        }
        else {
            blinkDelay = superFastBlinkDelay;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(100, 300);
    }

    public Dimension getMinimiumSize() {
        return getPreferredSize();
    }

    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int yPos = yStart;

        g.setColor(chipColor);
        for (int chip = 0; chip < stack.getNumChips() - numToBlink; chip++) {
            g.fillRoundRect(10, yPos, chipWidth, chipHeight, 10, 10);
            yPos = yPos - chipHeight - chipGap;
        }
        g.setColor(blinkColor);
        for (int chip = 0; chip < numToBlink; chip++) {
            g.fillRoundRect(10, yPos, chipWidth, chipHeight, 10, 10);
            yPos = yPos - chipHeight - chipGap;
        }
    }

    /**
     * Remove the specified number of chips from this stack and graphically
     * animate the removal to indicate that it is happening.
     * 
     * @param num the number of chips to remove.
     */
    public void removeChips(int num) {
        ChipRemover cr = new ChipRemover(num);
        cr.run();
    }

    /**
     * Set whether or not this NimStackDisplay is currently responsive to mouse
     * clicks.
     * 
     * @param state true or false.
     */
    public void setClickEnabled(boolean state) {
        clickEnabled = state;
    }

    /**
     * Find out if this NimStackDisplay is currently responsive to mouse clicks.
     * 
     * @return true or false.
     */
    public boolean getClickEnabled() {
        return clickEnabled;
    }

    public static void main(String[] args) {
        JFrame jf = new JFrame("Test");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        NimStackDisplay disp = new NimStackDisplay(new NimStack(12));
        disp.setClickEnabled(true);
        jf.add(disp);
        jf.pack();
        jf.setVisible(true);

        disp.removeChips(5);
    }

    /*
     * Handle mouse clicks on the component and translate them into a number of
     * chips to be removed.
     */
    private class NimStackClickHandler extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (clickEnabled) {
                int xpos = e.getPoint().x;
                int ypos = e.getPoint().y;

                // Is the click within (with a little buffer) the chips?
                if (xpos >= 15 && xpos <= 85 && ypos <= yStart + chipHeight) {

                    // Find out which if any chip was clicked...
                    int chip = 10 - (ypos / (chipHeight + chipGap) - 1);
                    if (chip > 0 && chip <= stack.getNumChips()) {
                        ChipRemover cr = new ChipRemover(stack.getNumChips() - chip + 1);
                        cr.start();
                    }
                }
            }
        }
    }

    public void waitForRemove() {
        try {
            barrier.acquire();
        }
        catch (InterruptedException e) {
        }
    }
    
    public void releaseWaitingThread() {
        barrier.release();
    }
    
    /*
     * Handle removing the chips and animating them.
     */
    private class ChipRemover extends Thread {

        private int numToRemove;

        public ChipRemover(int num) {
            numToRemove = num;
        }

        public void run() {
            clickEnabled = false;
            for (int i = 0; i < numBlinks; i++) {
                numToBlink = numToRemove;
                repaint();
                try {
                    Thread.sleep(blinkDelay);
                }
                catch (InterruptedException e) {
                }
                numToBlink = 0;
                repaint();
                try {
                    Thread.sleep(blinkDelay);
                }
                catch (InterruptedException e) {
                }
            }

            stack.removeChips(numToRemove);
            repaint();
            
            barrier.release();
        }
    }
}

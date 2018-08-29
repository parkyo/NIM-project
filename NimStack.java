/**
 * This class respresnts a stack in the game of Nim. A stack is a pile of chips
 * from which a player can remove 1 or more chips, up to the number of chips in
 * the stack.
 * 
 * @author Grant Braught
 * 
 * @author (HaHoangYounsePark)
 * @version (03/19/2018)
 */
public class NimStack {
    private int size;
    /**
     * Create a new NimStack that initially contains initSize chips. The maximum
     * size of a stack should be 10 chips. If the value specified for initSize
     * is greater than 10 then the size of the stack should be set to 10. The
     * minimum size of a stack is 0 chips. If the value specified for initSize
     * is less than 0, then the size of the stack should be set to 0.
     * 
     * @param initSize the number of chips initially on this NimStack.
     */
    public NimStack(int initSize) {
        size = initSize;
        if (initSize > 10) {
            size = 10;
        } else if (initSize < 0) {
            size = 0;
        }
    }

    /**
     * Get the number of chips that are currently on this NimStack.
     * 
     * @return the number of chips on the stack.
     */
    public int getNumChips() {
        return size;
    }

    /**
     * Remove num chips from this NimStack. If num is less than one, then one
     * chip will be removed from the stack (unless the stack is already empty in
     * which case it remains empty). If num is greater than the number of chips
     * on the stack then all of the remaining chips are removed.
     * 
     * @param num the number of chips to remove.
     */
    public void removeChips(int num) {
        if (size == 0){
            size = size;
        }else if (num < 1) {
            size = size - 1;
        } else if (num > size){
            size = size - size;
        }else { 
            size = size - num;
        }
    }
}
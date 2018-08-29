/**
 * This class implements different strategies for playing 3 stack Nim. During
 * each turn one of the strategy methods (takeOneFromLargest, takeFiveFromAny,
 * takeHalfFromAny) will be invoked by the GUI.  The strategy must remove chips 
 * from one and only one stack during each turn. If a strategy removes chips from
 * more than one stack during a single turn or does not remove any chips during
 * a turn that player forfeits the game.
 * 
 * @author Grant Braught
 * 
 * @author (HaHoang)
 * @version (03/19/2018)
 */
public class NimPlayer {
    private NimStack stackA;
    private NimStack stackB;
    private NimStack stackC;
    /**
     * Create a new NimPlayer that will play the game using the provided stacks.
     * 
     * @param stack1 the first stack of chips
     * @param stack2 the second stack of chips
     * @param stack3 the third stack of chips
     */
    public NimPlayer(NimStack stack1, NimStack stack2, NimStack stack3) {
        stackA = stack1;
        stackB = stack2;
        stackC = stack3;
    }

    /**
     * Get the total number of chips remaining on all of the NimStacks.
     * 
     * @return the total number of chips.
     */
    public int getTotalChips() {
        int size1 = stackA.getNumChips();
        int size2 = stackB.getNumChips();
        int size3 = stackC.getNumChips();
        return size1 + size2 + size3 ;
    }

    /**
     * This strategy plays by removing a single chip from the largest NimStack
     * (i.e. the one with the most chips). If there is a tie for the largest
     * stack then the chip may be removed from any stack that is tied.
     */
    public void takeOneFromLargest() {
        if (stackA.getNumChips()>stackB.getNumChips()){
            if (stackA.getNumChips() == stackC.getNumChips()){
                stackA.removeChips(1);
            }else if(stackA.getNumChips() > stackC.getNumChips()){
                stackA.removeChips(1);
            }else{
                stackC.removeChips(1);
            }      
        } else if (stackB.getNumChips() > stackC.getNumChips()){
            if (stackB.getNumChips() == stackA.getNumChips()){
                stackB.removeChips(1);
            } else {
                stackB.removeChips(1);
            }
        }else if (stackC.getNumChips() > stackB.getNumChips()){
            stackC.removeChips(1);
        }else{
            stackC.removeChips(1);
        }
    }

    /**
     * This strategy plays by removing 5 chips from any NimStack with 5 or more
     * chips. If no stack has at least 5 chips then one chip is removed from the
     * largest remaining stack. If there is a tie for the largest stack then the
     * chip may be removed from any stack that is tied.
     */
    public void takeFiveFromAny() {
        if (stackA.getNumChips() >= 5){
            stackA.removeChips(5);
        }else if(stackB.getNumChips() >= 5){
            stackB.removeChips(5);
        }else if(stackC.getNumChips() >= 5){
            stackC.removeChips(5);
        }else if(stackA.getNumChips() > stackB.getNumChips()){
            if (stackA.getNumChips() == stackC.getNumChips()){
                stackA.removeChips(1);
            }else if (stackA.getNumChips() > stackC.getNumChips()){
                stackA.removeChips(1);
            }else {
                stackC.removeChips(1);
            }
        }else if(stackB.getNumChips() > stackC.getNumChips()){
            if (stackB.getNumChips() == stackA.getNumChips()){
                stackB.removeChips(1);
            }else {
                stackB.removeChips(1);
            }
        }else if (stackC.getNumChips() > stackB.getNumChips()){
            stackC.removeChips(1);
        }else{
            stackC.removeChips(1);
        }
    }

    /**
     * This strategy plays by removing half of the chips from any non-empty
     * stack.  If the stack has an even number of chips then exactly half
     * of the chips are removed.  If the stack has an odd number of chips then
     * half of one more than the number of chips are removed (e.g. if there 
     * are 5 chips then (5+1)/2 = 3 chips are removed).
     */
    public void takeHalfFromAny() {
        if (stackA.getNumChips() != 0){
            if (stackA.getNumChips() % 2 == 0){
                stackA.removeChips(stackA.getNumChips()/2);
            }else {
                stackA.removeChips((stackA.getNumChips()+1)/2);
            }
        }else if(stackB.getNumChips() != 0){
            if (stackB.getNumChips() % 2 == 0){
                stackB.removeChips(stackB.getNumChips()/2);
            }else {
                stackB.removeChips((stackB.getNumChips()+1)/2);
            }
        }else if (stackC.getNumChips() != 0){
            if (stackC.getNumChips() % 2 == 0){
                stackC.removeChips(stackC.getNumChips()/2);
            }else {
                stackC.removeChips((stackC.getNumChips()+1)/2);
            }
        }else{
            stackA = stackA;
            stackB = stackB;
            stackC = stackC;
        }
    }

}


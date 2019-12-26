/**
 * Cubicle object in Model for minesweeper
 * @author Alejandro Gallardo
 * @author Kate Grossman
 * @author Alex Battiste
 * June 2019
 */
public class Cubicle{
    private boolean hasFlag;
    private boolean hasBomb;
    private int numberOfAdjacentBombs;
    private boolean isRevealed;

    /**
     * constructor
     * @param addBomb true when cubicle has a bomb; false otherwise.
     */
    public Cubicle(Boolean addBomb){
        this.isRevealed = false;
        this.hasFlag = false;
        this.numberOfAdjacentBombs = -1;
        this.hasBomb = addBomb;
    }

    /**
     * checks if cubicle has a bomb
     * @return true if cubicle has bomb; false otherwise
     */
    boolean isBomb(){
        return this.hasBomb;
    }

    /**
     * gets number of bombs adjacent to this cubicle
     * @return integer number of adjacent bombs
     */
    int getNumberOfAdjacentBombs(){
        return this.numberOfAdjacentBombs;
    }

    /**
     * checks if cubicle has been flagged
     * @return true if has been flagged; false otherwise
     */
    boolean isCubicleFlagged(){
        return this.hasFlag;
    }

    /**
     * checks if cubicle has been revealed
     * @return true if has been revealed; false otherwise
     */
    boolean isCubicleRevealed(){
        return this.isRevealed;
    }

    /**
     * mutator method that changes the number of adjacent bombs for this cubicle
     * @param adjacentBombs number of bombs adjacent to this cubicle
     */
    void setNumberOfAdjacentBombs(int adjacentBombs){
        this.numberOfAdjacentBombs = adjacentBombs;
    }

    /**
     * mutator method that informs the cubicle it has been flagged
     */
    void setCubeFlag(){
        this.hasFlag = true;
    }

    /**
     * mutator method that removes the flag from a cubicle
     */
    void removeCubeFlag(){
        this.hasFlag = false;
    }

    /**
     * mutator method that sets a cubicle as revealed/visited
     */
    void setAsRevealed(){
        this.isRevealed = true;
    }
}


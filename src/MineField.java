/**
 * MineField object in Model for minesweeper
 * @author Alejandro Gallardo
 * @author Kate Grossman
 * @author Alex Battiste
 * June 2019
 */

import java.lang.Math;

public class MineField{
    private Cubicle[][] gridField;
    private int height;
    private int width;
    private int desiredNumberOfBombsInField;
    private boolean hasLost;

    /**
     * constructor
     */
    public MineField(){
        //setting up instance variables
        this.height = 15;
        this.width = 20;
        this.desiredNumberOfBombsInField = this.height *this.width *2/10;
        this.gridField  = new Cubicle[this.height][this.width];
        //planting bombs in field and other setup
        this.plantBombsInField();
        this.setNumberOfAdjacentBombsForEachCubicleInGrid();
        this.hasLost = false;
    }

    /**
     * constructor
     */
    public MineField(int difficultyLevel){
        //setting up instance variables
        this.height = 15;
        this.width = 20;
        this.gridField  = new Cubicle[this.height][this.width];
        if (difficultyLevel == 0){
            this.desiredNumberOfBombsInField = this.height *this.width *2/10;
        } else if (difficultyLevel == 1){
            this.desiredNumberOfBombsInField = this.height *this.width *3/10;
        } else if(difficultyLevel == 2){
            this.desiredNumberOfBombsInField = this.height *this.width *4/10;
        } 
        //planting bombs in field and other setup
        this.plantBombsInField();
        this.setNumberOfAdjacentBombsForEachCubicleInGrid();
    }

    /**
     * Helper function that plants a given amount of bombs in the minefield.
     * The number of bombs is determined by the instance variable in MineField class.
     */
    private void plantBombsInField(){
        int bombsCount = 1;
        for(int row = 0; row < this.height; row++){
            for(int col = 0; col < this.width; col++){
                double randomNum =  Math.random()*(double)300;
                if (randomNum <= desiredNumberOfBombsInField &&
                    bombsCount <= desiredNumberOfBombsInField){
                    gridField[row][col] = new Cubicle(true);
                    bombsCount++;
                }
                else{
                    gridField[row][col] = new Cubicle(false);
                }
            }
        }
    }

    /**
     * Helper function that determines number of adjacent bombs provided a cubicle address
     * @param row row location of cubicle in minefield
     * @param col column location of cubicle in minefield
     * @return number of bombs adjacent to a particular cubicle
     */
    private int determineNumberOfAdjacentBombs(int row, int col){
        int numBombs = 0;
        for (int x = row -1; x <= row + 1; x++){
            for (int y = col - 1; y <= col + 1; y++){
                if ( x>=0 && x < this.height &&
                        y >= 0 && y < this.width &&
                        this.gridField[x][y].isBomb() == true){
                    numBombs++;
                }
            }
        }
        return numBombs;

    }

    /**
     * helper function that informs each cubicle in the model how many adjacent bombs there are
     */
    private void setNumberOfAdjacentBombsForEachCubicleInGrid(){
        for(int row = 0; row < this.height; row++){
            for(int col = 0; col < this.width; col++){
                int numBombs = this.determineNumberOfAdjacentBombs(row, col);
                this.gridField[row][col].setNumberOfAdjacentBombs(numBombs);
            }
        }
    }

    /**
     * function that determines if a cubicle at a particular (row,column) address has a bomb
     * @param row row location of a particular cubicle within the minfield
     * @param column column location of a particular cubicle within the minfield
     * @return number of bombs
     */
    public boolean isBomb(int row, int column){
        return gridField[row][column].isBomb();
    }

    /**
     * accessor method that obtains number of bombs adjacent to a particular cubicle
     * @param row row location of a particular cubicle within the minfield
     * @param col column column location of a particular cubicle within the minfield
     * @return integer number representing number of adjacent bombs
     */
    public int getNumberOfAdjacentBombs(int row, int col){
        return this.gridField[row][col].getNumberOfAdjacentBombs();
    }

    /**
     * accessor method that gets total number of bombs in the minefield
     * @return integer number representing total number of bombs in field
     */
    public int getTotalNumberOfBombs(){
        int numBombs = 0;
        for(int row = 0; row < 15; row++){
            for(int col = 0; col < 20; col++){
                if (this.isBomb(row, col) == true){
                    numBombs++;
                }
            }
        }
        return numBombs;
    }

    /**
     * Determines if cubicle at a particular address has been flagged by user
     * @param row row row location of a particular cubicle within the minfield
     * @param col column column location of a particular cubicle within the minfield
     * @return return true if cubicle has been flagged; false otherwise
     */
    public boolean isFlagged(int row, int col){
        return this.gridField[row][col].isCubicleFlagged();
    }

    /**
     * Determines if cubicle at a particular row column address
     * @param row row row location of a particular cubicle within the minfield
     * @param col column column location of a particular cubicle within the minfield
     * @return returns true if cubicle has been revealed; false otherwise
     */
    public boolean isRevealed(int row, int col){
        return this.gridField[row][col].isCubicleRevealed();
    }

    /**
     * Determines if user passes winning conditions
     * @return true if user passes winning conditions; false otherwise
     */

    public boolean hasUserWon() {
        boolean userWon = true;
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 20; col++) {
                Cubicle cube = this.gridField[row][col];
                if ((!cube.isBomb() && !cube.isCubicleRevealed())) {
                    userWon = false;
                }

            }
        }
        return userWon;
    }

    /**
     * Mutator method that informs a cubicle that it has been flagged
     * @param row row row location of a particular cubicle within the minfield
     * @param col column column location of a particular cubicle within the minfield
     */
    public void setFlag(int row, int col){
        this.gridField[row][col].setCubeFlag();
    }

    /**
     * mutator method that informs a cubicle it has been revealed
     * @param row row row location of a particular cubicle within the minfield
     * @param col column column location of a particular cubicle within the minfield
     */
    void setCubicleAsRevealed(int row, int col){
        this.gridField[row][col].setAsRevealed();
    }

    /**
     * mutator method that informs cubicle that flag has been removed
     * @param row row row location of a particular cubicle within the minfield
     * @param col column column location of a particular cubicle within the minfield
     */
    void removeFlag(int row, int col){
        this.gridField[row][col].removeCubeFlag();
    }

    /**
     * Changes the hasLost boolean so that it can remember that a user has lost.
     */
    void userLost() {
        this.hasLost = true;
    }

    /**
     * Tells whether or not the user has lost.
     * @return True if the user has lost, false if the user has not lost.
     */
    boolean hasUserLost() {
    return this.hasLost;
    }
}
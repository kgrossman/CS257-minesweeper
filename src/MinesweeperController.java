public class MinesweeperController {
    private MinesweeperView view;
    private MineField model;


    /**
     * Constructor
     */
    public MinesweeperController() {
        model = new MineField();
        view = new MinesweeperView();
        view.setController(this);
        view.callLaunch();
    }

    /**
     * Constructor
     * @param newView the view instance.
     */
    public MinesweeperController(MinesweeperView newView) {
        model = new MineField();
        view = newView;
    }

    /**
     * Main user interaction, displays a "you lose" message if you click a bomb, or reveals the number of adjacent bombs if it is not a bomb.
     * @param row the row of the button.
     * @param column the column of the button.
     */
    public void buttonPressed(int row, int column) {
        if (model.isFlagged(row, column) || model.hasUserLost()){
        } else if(model.isBomb(row, column)) {
            model.userLost();
            view.youLose();
        } else{
            model.setCubicleAsRevealed(row, column);
            int numAdjacent = model.getNumberOfAdjacentBombs(row, column);
            view.displayNumberOfAdjacentBombs(row, column, numAdjacent);
            if (numAdjacent == 0){
                callAdjacentSquares(row, column);
            }
            if (model.hasUserWon()){
                view.youWin();
            }
        }
    }

    /**
     * Flags a button if the button is not flagged, unflags a button if it is already flagged.
     * @param row the row of the button.
     * @param column the column of the button.
     */

    public void flagButton(int row, int column) {
        if (model.hasUserLost()){
        } else if (model.isFlagged(row, column)) {
            model.removeFlag(row, column);
            view.setUnflagged(row, column);
        } else {
            model.setFlag(row, column);
            view.flagButton(row, column);
        }
    }

    /**
     * Creates a new game.
     */
    public void newGame() {
        model = new MineField();
        view.resetGrid();
    }
    public void newGame(int difficultyLevel) {
        model = new MineField(difficultyLevel);
        view.resetGrid();
    }

    /**
     * Returns the number of bombs in the whole grid.
     * @return numBombs the number of total bombs in the minefield.
     */
    public int getTotalNumberOfBombs() {
        int numBombs = model.getTotalNumberOfBombs();
        return numBombs;
    }

    /**
     * For when a zero is clicked.  Presses all the buttons surrounding the zero since they are guaranteed to not be bombs.
     * @param row the row of the button.
     * @param column the column of the button.
     */
    public void callAdjacentSquares(int row, int column) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j<2;j++) {
                int newRow = row + i;
                int newCol = column + j;
                if (newRow > -1 && newCol > -1 && newRow < 15 && newCol < 20) {
                    if (!model.isRevealed(newRow, newCol)) {
                        buttonPressed(newRow, newCol);
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        MinesweeperController controller = new MinesweeperController();
    }

}
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseButton;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * View for the minesweeper game.
 *
 * @author Kate Grossman
 * @author Alex Battiste,
 * @author Alejandro Gallardo
 * June 2019
 */
public class MinesweeperView extends Application {
    private static final double SCENE_WIDTH = 1200;
    private static final double SCENE_HEIGHT = 800;
    private MinesweeperController controller;
    private GridPane grid;
    private int bombsRemaining;
    private Stage stage;
    private VBox statsPane;
    private Text bombsRemainingText;

    /**
     * constructor
     */
    public MinesweeperView() {
        this.grid = new GridPane();
        this.controller = new MinesweeperController(this);
        this.bombsRemaining = controller.getTotalNumberOfBombs();
        this.statsPane = new VBox();
    }

    /**
     * Initializes the controller.
     * @param cont the current instance of the controller.
     */
    public void setController(MinesweeperController cont) {
        this.controller = cont;
        this.bombsRemaining = controller.getTotalNumberOfBombs();
    }

    /**
     * Creates a new view with three panes for the title, the stats, and the game.
     * @param primaryStage the javaFX container holding the scene.
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        BorderPane root = new BorderPane();

        HBox titlePane = addTitlePane();
        VBox statsPane = addStatsPane();
        VBox gamePane = addGamePane();

        root.setTop(titlePane);
        root.setRight(statsPane);
        root.setLeft(gamePane);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setTitle("MINESWEEPER");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * When called, the MinesweeperView class in launched.
     */
    public void callLaunch() {
        Application.launch(MinesweeperView.class);
    }

    /**
     * Creates and adds the title pane which includes the minesweeper title and a new game button.
     * @return titlePane the pane containing the title of the game and the new game button.
     */
    private HBox addTitlePane() {
        HBox titlePane = titlePaneSettings();
        Text title = createTitle();
        Button newEasyGameButton = createNewGameButton("Easy");
        Button newMediumGameButton = createNewGameButton("Medium");
        Button newHardGameButton = createNewGameButton("Hard");

        newEasyGameButton.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * Calls a new game when the new game button is pressed.
             * @param event new game button is pressed.
             */
            @Override
            public void handle(ActionEvent event) {
                controller.newGame(0); // 0 means easy difficulty
            }
        });

        newMediumGameButton.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * Calls a new game when the new game button is pressed.
             * @param event new game button is pressed.
             */
            @Override
            public void handle(ActionEvent event) {
                controller.newGame(1); // 1 means medium difficulty
            }
        });

        newHardGameButton.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * Calls a new game when the new game button is pressed.
             * @param event new game button is pressed.
             */
            @Override
            public void handle(ActionEvent event) {
                controller.newGame(2); // 2 means hard difficulty
            }
        });

        titlePane.getChildren().addAll(title, newEasyGameButton, newMediumGameButton, newHardGameButton);

        return titlePane;
    }

    /**
     * Creates and adds the stats pane which includes two lines of instructions and the updated
     * number of bombs remaining unflagged on the grid.
     * @return statsPane the pane containing the instructions and the number of bombs remaining.
     */
    private VBox addStatsPane() {
        statsPaneSettings();
        Text inst1 = getInst1();
        Text inst2 = getInst2();
        Text bombs = getBombsNumber();
        statsPane.getChildren().addAll(inst1, inst2, bombs, bombsRemainingText);

        return statsPane;
    }

    /**
     * Creates and adds the game pane which includes a matrix of buttons.
     * @return gamePane the pane containing the grid for minesweeper.
     */
    private VBox addGamePane() {
        VBox gamePane = gamePaneSetting();
        createGrid();
        gamePane.getChildren().addAll(this.grid);

        return gamePane;
    }

    /**
     * Alerts the use that they have won the game.
     */
    void youWin() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("YOU WIN!");

        alert.showAndWait();
    }

    /**
     * Alerts the use that they have lost the game.
     */
    void youLose() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("You lost... better try again!");
        alert.showAndWait();
    }

    /**
     * Reveals the number of adjacent bombs for a given button.
     * @param row the row of the button.
     * @param column the column of the button.
     * @param numAdjacent the number of bombs adjacent to the button.
     */
    void displayNumberOfAdjacentBombs(int row, int column, int numAdjacent) {
        String numAdjacentString = Integer.toString(numAdjacent);

        Button button = new Button();
        button.setMaxSize(35,35);
        button.setMinSize(35,35);
        button.setStyle("-fx-background-color: #aae0ff;");
        button.setText(numAdjacentString);

        this.grid.add(button, column, row);
    }

    /**
     * Unflags a given button.
     * @param row the row of the button.
     * @param column the column of the button.
     */
    void setUnflagged(int row, int column) {
        Button button = new Button();
        getAction(row, column, button);

        bombsRemaining = bombsRemaining +1;
        bombsRemainingText = new Text(Integer.toString(bombsRemaining));
        bombsRemainingText.setFont(Font.font("Impact", 30));
        statsPane.getChildren().remove(3);
        statsPane.getChildren().add(3, bombsRemainingText);
    }

    /**
     * Flags a given button.
     * @param row the row of the button.
     * @param column the column of the button.
     */
    void flagButton(int row, int column){
        Button button = new Button("X");
        button.setTextFill(Color.WHITE);
        getAction(row, column, button);

        bombsRemaining = bombsRemaining - 1;
        bombsRemainingText = new Text(Integer.toString(bombsRemaining));
        bombsRemainingText.setFont(Font.font("Impact", 30));
        statsPane.getChildren().remove(3);
        statsPane.getChildren().add(3, bombsRemainingText);

    }

    /**
     * Determines what action should be taken when a button is pressed based of if it was a left or right click.
     * @param row the row of the button.
     * @param column the column of the button.
     * @param button the button that was pressed.
     */
    private void getAction(int row, int column, Button button) {
        button.setMaxSize(35,35);
        button.setMinSize(35,35);
        button.setStyle("-fx-background-color: #435777;");

        this.grid.add(button, column, row);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {

            /**
             * Calls on the controller to flag a button or press a button.
             * @param event a button in the grid is pressed.
             */
            @Override
            public void handle(MouseEvent event) {
                int row = GridPane.getRowIndex(button);
                int column = GridPane.getColumnIndex(button);

                if (event.getButton() == MouseButton.SECONDARY) {
                    controller.flagButton(row, column);
                } else {
                    controller.buttonPressed(row, column);
                }
            }
        });
    }

    /**
     * Resets the grid for a new game.
     */
    public void resetGrid(){
        BorderPane root = new BorderPane();

        HBox titlePane = addTitlePane();
        this.bombsRemaining = controller.getTotalNumberOfBombs();
        bombsRemainingText = new Text(Integer.toString(bombsRemaining));
        bombsRemainingText.setFont(Font.font("Impact", 30));
        statsPane.getChildren().remove(3);
        statsPane.getChildren().add(3, bombsRemainingText);
        VBox gamePane = addGamePane();

        root.setTop(titlePane);
        root.setRight(statsPane);
        root.setLeft(gamePane);

        this.bombsRemainingText =  new Text(Integer.toString(bombsRemaining));

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        stage.setTitle("MINESWEEPER");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Provides settings for the title pane.
     * @return titlePane the title pane with its included settings.
     */
    private HBox titlePaneSettings() {
        HBox titlePane = new HBox();
        titlePane.setAlignment(Pos.CENTER_LEFT);
        titlePane.setPadding(new Insets(300));
        titlePane.setSpacing(20);
        titlePane.setStyle("-fx-background-color: #829091;");
        titlePane.setMaxHeight(100);
        titlePane.setMinHeight(100);

        return titlePane;
    }

    /**
     * Creates the title.
     * @return title the title of the pane.
     */
    private Text createTitle() {
        Text title = new Text();
        title.setText("MINESWEEPER");
        title.setFont(Font.font("Impact", FontWeight.BOLD, 50));

        return title;
    }

    /**
     * Creates the new game button.
     * @return newGameButton the new game button.
     */
    private Button createNewGameButton(String difficultyLevel) {
        Button newGameButton = new Button();
        newGameButton.setText("New " + difficultyLevel + " Game");
        newGameButton.setFont(Font.font("Impact", 20));
        newGameButton.setPadding(new Insets(5));

        return newGameButton;
    }

    /**
     * Provides settings for the stats pane.
     * @return statsPane the title pane with its included settings.
     */
    private void statsPaneSettings() {
        statsPane.setAlignment(Pos.CENTER_LEFT);
        statsPane.setPadding(new Insets(10));
        statsPane.setSpacing(30);
        statsPane.setStyle("-fx-background-color: #9c6259;");
        statsPane.setFillWidth(true);
        statsPane.setPrefWidth(800);
        statsPane.setMinWidth(800);
        statsPane.setMaxWidth(1000);
    }

    /**
     * Creates the first instruction.
     * @return inst1 the first instruction.
     */
    private Text getInst1() {
        Text inst1 = new Text("Click on a square to reveal it");
        inst1.setFont(Font.font("Impact", 20));

        return inst1;
    }

    /**
     * Creates the second instruction.
     * @return inst2 the second instruction.
     */
    private Text getInst2() {
        Text inst2 = new Text("Right click on a square to flag it");
        inst2.setFont(Font.font("Impact", 20));

        return inst2;
    }

    /**
     * Creates the text reporting the number of bombs left.
     * @return bombs the text for the number of bombs left.
     */
    private Text getBombsNumber() {
        Text bombs = new Text("Bombs Remaining:");
        bombs.setFont(Font.font("Impact", 30));
        bombsRemainingText = new Text(Integer.toString(bombsRemaining));
        bombsRemainingText.setFont(Font.font("Impact", 30));

        return bombs;
    }

    /**
     * Provides settings for the game pane.
     * @return gamePane the game pane with its included settings.
     */
    private VBox gamePaneSetting() {
        VBox gamePane = new VBox();
        gamePane.setAlignment(Pos.CENTER);
        gamePane.setPadding(new Insets(100));
        gamePane.setStyle("-fx-background-color: #ba8686;");
        gamePane.setFillWidth(true);
        gamePane.setPrefWidth(900);
        gamePane.setMinWidth(900);
        gamePane.setMaxWidth(1500);

        return gamePane;
    }

    /**
     * Creates the grid.
     */
    private void createGrid() {
        this.grid.setPadding(new Insets(5,5,5,5));
        this.grid.setHgap(2);
        this.grid.setVgap(2);
        this.grid.setStyle("-fx-background-color: #d1dfe5;");

        for (int i = 0; i<20; i++) {
            for (int j=0; j<15; j++) {
                Button button = new Button();
                getAction(j, i, button);
            }
        }
    }
}


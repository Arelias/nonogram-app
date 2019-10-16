package nonogram.menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import nonogram.NonogramMain;
import nonogram.board.Board;
import nonogram.board.drawer.BoardDrawer;
import nonogram.board.drawer.FixedSizeDrawer;
import nonogram.board.generator.BoardGenerator;
import nonogram.board.generator.RandomBoardGenerator;
import nonogram.board.solver.BruteSolver;
import nonogram.board.solver.NonogramSolver;
import nonogram.board.solver.SolutionDto;
import nonogram.files.controller.TxtFileController;

public class PuzzleMenu extends Menu{

    private static GridPane boardGrid;
    private static Board puzzleBoard;
    private static Menu parentMenu;
    private static PuzzleMenu menuInstance;
    private static BoardDrawer boardDrawer;
    private static BoardGenerator boardGenerator;
    private static NonogramSolver nonogramSolver;
    private static Integer puzzleSize;
    private static Button backButton = new Button("Powrot");
    private static Button solveButton = new Button("Sprawdz zagadke");
    private static Button saveButton = new Button("Zapisz zagadke");
    private static Label solutionLabel = new Label("");


    private PuzzleMenu(BoardDrawer drawer, BoardGenerator generator, NonogramSolver solver, Integer size){
        boardGrid = new GridPane();
        puzzleBoard = new Board(2,2, true);
        parentMenu = CreationMenu.getMenuInstance();
        boardDrawer = drawer;
        boardGenerator = generator;
        nonogramSolver = solver;
        puzzleSize = size;
        menuGridPane = new GridPane();
        menuScene = new Scene(boardGrid);
        menuScene = menuSetUp();

    }

    public static Menu getMenuInstance() {
        if (menuInstance == null) {
            menuInstance = new PuzzleMenu(new FixedSizeDrawer(), new RandomBoardGenerator(), new BruteSolver(), 600);
        }
        return menuInstance;
    }



    public static Menu getMenuInstance(BoardDrawer boardDrawer, BoardGenerator boardGenerator, NonogramSolver nonogramSolver, Integer size) {
        if (menuInstance == null) {
            menuInstance = new PuzzleMenu(boardDrawer, boardGenerator, nonogramSolver, size);
        }
        return menuInstance;
    }

    public Scene getMenuScene() {
        return menuScene;
    }

    public static Board getPuzzleBoard() {
        return puzzleBoard;
    }

    public static BoardGenerator getBoardGenerator() {
        return boardGenerator;
    }

    public static BoardDrawer getBoardDrawer() {
        return boardDrawer;
    }

    public void setParentScene(Menu parent){
        parentMenu = parent;

    }
    @Override
    public Scene menuSetUp(){
        Scene scene;

        menuGridPane.getChildren().clear();
        GridPane controls = controlsSetUp(parentMenu);
        menuGridPane.add(boardGrid,0,0);
        menuGridPane.add(controls,0,1);
        boardDrawer.drawGrid(boardGrid,puzzleBoard,puzzleSize,puzzleSize);
        boardGrid.setAlignment(Pos.CENTER);
        scene = new Scene(menuGridPane, Color.GRAY);

        return scene;
    }

    public GridPane controlsSetUp(Menu parentMenu){

        GridPane controls = new GridPane();

        backButton.setOnAction(e -> {
            NonogramMain.getStage().setScene(parentMenu.getMenuScene());

        });

        solveButton.setOnAction(e -> {
            if(puzzleBoard.isSolvingMode()){
                solvingMode();
            } else {
                creationMode();
            }
        });

        saveButton.setOnAction(e -> {
            TxtFileController txtFileController = new TxtFileController();
            txtFileController.saveFile(puzzleBoard);
        });

        controls.add(backButton,0,0);
        controls.add(solveButton,1,0);
        controls.add(saveButton,2,0);
        controls.add(solutionLabel,3,0);

        return controls;
    }

    private void solvingMode(){
        if(puzzleBoard.isSolved()){
            solutionLabel.setText("You solved it!");
            solutionLabel.setTextFill(Color.GREEN);
        } else {
            solutionLabel.setText("Try again");
            solutionLabel.setTextFill(Color.RED);
        }
    }

    private void creationMode(){
        puzzleBoard.setRowsData(Board.rowsValues(puzzleBoard.getCells()));
        puzzleBoard.setColumnsData(Board.columnValues(puzzleBoard.getCells()));
        SolutionDto solutionDto = nonogramSolver.solve(puzzleBoard.getRowsData(),puzzleBoard.getColumnsData());
        if(solutionDto.getSolutionsCount() > 1){
            solutionLabel.setText("More than one solution!");
            solutionLabel.setTextFill(Color.RED);
            saveButton.setDisable(true);
        } else {
            solutionLabel.setText("Only one solution!");
            solutionLabel.setTextFill(Color.GREEN);
            saveButton.setDisable(false);
        }
        boardDrawer.drawGrid(boardGrid,puzzleBoard,puzzleSize,puzzleSize);
        NonogramMain.getStage().sizeToScene();
    }

    public void updateBackButton(){
        backButton.setOnAction(e -> {
            NonogramMain.getStage().setScene(parentMenu.getMenuScene());
        });
        solutionLabel.setText("");
    }

    public void insertEmptyBoard(int width, int height){
        saveButton.setDisable(true);
        puzzleBoard = new Board(width,height, false);
        boardDrawer.drawGrid(boardGrid,puzzleBoard,puzzleSize,puzzleSize);
        System.out.println();
    }

    public void insertLoadedBoard(Board board){
        saveButton.setDisable(false);
        puzzleBoard = board;
        boardDrawer.drawGrid(boardGrid,puzzleBoard,puzzleSize,puzzleSize);
    }

    public void insertGeneratedBoard(int width, int height){
        puzzleBoard = boardGenerator.generateBoard(width,height);
        boardDrawer.drawGrid(boardGrid,puzzleBoard,puzzleSize,puzzleSize);
    }
}

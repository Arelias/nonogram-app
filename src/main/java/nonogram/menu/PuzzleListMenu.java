package nonogram.menu;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import nonogram.NonogramMain;
import nonogram.board.Board;
import nonogram.files.controller.FileController;
import nonogram.files.controller.TxtFileController;

public class PuzzleListMenu extends Menu {

    private static GridPane puzzlesGrid;
    private static Board[] boardsArray;
    private static FileController fileController;
    private static Menu parentMenu;
    private static PuzzleListMenu menuInstance;



    private PuzzleListMenu(FileController controller){
        menuGridPane = new GridPane();
        puzzlesGrid = new GridPane();
        boardsArray = new Board[0];
        fileController = controller;
        menuScene = menuSetUp();
    }

    public static Menu getMenuInstance() {
        if (menuInstance == null) {
            menuInstance = new PuzzleListMenu(new TxtFileController());
        }
        return menuInstance;
    }

    public static Menu getMenuInstance(FileController fileController) {
        if (menuInstance == null) {
            menuInstance = new PuzzleListMenu(fileController);
        }
        return menuInstance;
    }



    @Override
    public Scene menuSetUp() {
        //Potrzebuje przycisku zaladuj folder
        //Po nacisnieciu laduje mi caly folder
        //Po zaladowaniu folderu skanuje go w poszukiwaniu plikow txt
        //Kazdy plik
        Scene scene;
        Button loadButton = new Button("Wybierz folder");
        Button backButton = new Button("Wroc");

        menuGridPane.setPadding(new Insets(10, 10, 10, 10)); //margins around
        menuGridPane.setMinHeight(150);
        menuGridPane.setVgap(10);

        loadButton.setOnAction(e -> {
            loadButton.setDisable(true);
            insertPuzzles();
            NonogramMain.getStage().sizeToScene();
            loadButton.setDisable(false);
        });
        backButton.setOnAction(e ->{
            NonogramMain.getStage().setScene(MainMenu.getMenuInstance().getMenuScene());
        });

        menuGridPane.add(loadButton, 0,0);
        menuGridPane.add(puzzlesGrid, 0,1);
        menuGridPane.add(backButton,0,2);
        menuGridPane.setHalignment(backButton, HPos.CENTER);
        menuGridPane.setHalignment(backButton, HPos.CENTER);

        scene = new Scene(menuGridPane, Color.GRAY);
        return scene;
    }

    public void insertPuzzles(){
        Board[] boards = fileController.loadDirectory();
        if(boards.length > 0) {
            boardsArray = boards;
            puzzlesGrid.getChildren().clear();
            puzzlesGrid.setAlignment(Pos.CENTER);
            puzzlesGrid.setVgap(10);
            for (int i = 0; i < boardsArray.length; i++) {
                Button puzzleButton = new Button("Puzzle: " + (i + 1));
                int finalI = i;
                puzzleButton.setOnAction(e -> {
                    System.out.println("You are getting board: " + finalI);

                    PuzzleMenu puzzleMenu = (PuzzleMenu) PuzzleMenu.getMenuInstance();
                    puzzleMenu.setParentScene(menuInstance);
                    puzzleMenu.updateBackButton();
                    puzzleMenu.insertLoadedBoard(boardsArray[finalI]);
                    NonogramMain.getStage().setScene(PuzzleMenu.getMenuInstance().getMenuScene());

                });
                puzzlesGrid.add(puzzleButton, 0, i);
            }
        }
    }
}


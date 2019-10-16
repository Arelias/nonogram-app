package nonogram.menu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import nonogram.NonogramMain;
import nonogram.board.Board;
import nonogram.files.controller.FileController;
import nonogram.files.controller.TxtFileController;


public class MainMenu extends Menu {

    private static MainMenu menuInstance;
    private static FileController fileController;

    private MainMenu(FileController controller){
       menuGridPane = new GridPane();
       fileController = controller;
       menuScene = menuSetUp();


    }

    public static Menu getMenuInstance() {
        if (menuInstance == null) {
            menuInstance = new MainMenu(new TxtFileController());
        }
        return menuInstance;
    }

    public static Menu getMenuInstance(FileController fileController) {
        if (menuInstance == null) {
            menuInstance = new MainMenu(fileController);
        }
        return menuInstance;
    }

    public Scene getMenuScene() {
        return menuScene;
    }

    @Override
    public Scene menuSetUp() {

        Scene scene;

        menuGridPane.setPadding(new Insets(10, 10, 10, 10)); //margins around
        menuGridPane.setMinHeight(150);
//      menuGridPane.setMinWidth(200);

        menuGridPane.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
        Button solveButton = new Button("Rozwiazuj nonogramy");
        solveButton.setMinWidth(180);
        menuGridPane.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
        Button generateButton = new Button("Tryb tworzenia");
        generateButton.setMinWidth(180);
        menuGridPane.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
        Button exitButton = new Button("Wyjscie");
        exitButton.setMinWidth(180);

        solveButton.setOnAction(e -> {
            solveButton.setDisable(true);
//            Board board = fileController.loadFile();
//            if (board != null) {
//                PuzzleMenu puzzleMenu = (PuzzleMenu) PuzzleMenu.getMenuInstance();
//                puzzleMenu.setParentScene(menuInstance);
//                puzzleMenu.updateBackButton();
//                puzzleMenu.insertLoadedBoard(board);
//                NonogramMain.getStage().setScene(PuzzleMenu.getMenuInstance().getMenuScene());
//            }
            //Board[] xd = fileController.loadDirectory();
            NonogramMain.getStage().setScene(PuzzleListMenu.getMenuInstance().getMenuScene());
            solveButton.setDisable(false);
        });
        generateButton.setOnAction(e -> {
            Menu creationInstance = CreationMenu.getMenuInstance();
            NonogramMain.getStage().setScene(creationInstance.getMenuScene());
        });
        exitButton.setOnAction(e -> {
            NonogramMain.getStage().close();
        });

        menuGridPane.add(solveButton, 0, 0);
        menuGridPane.add(generateButton, 0, 1);
        menuGridPane.add(exitButton, 0, 2);
        scene = new Scene(menuGridPane, Color.GRAY);
        return scene;
    }
}

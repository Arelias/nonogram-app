package nonogram.menu;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import nonogram.NonogramMain;
import nonogram.board.Board;
import nonogram.board.drawer.BoardDrawer;
import nonogram.board.drawer.FixedSizeDrawer;
import nonogram.board.generator.BoardGenerator;
import nonogram.board.generator.RandomBoardGenerator;

public class CreationMenu extends Menu{

    private static CreationMenu menuInstance;
    BoardDrawer boardDrawer;
    BoardGenerator boardGenerator;

    private CreationMenu(BoardDrawer drawer, BoardGenerator generator) {
        menuGridPane = new GridPane();
        boardDrawer = drawer;
        boardGenerator = generator;
        menuScene = menuSetUp();
    }

    @Override
    public Scene getMenuScene() {
        return menuScene;
    }

    public static Menu getMenuInstance() {
        if (menuInstance == null) {
            menuInstance = new CreationMenu(new FixedSizeDrawer(), new RandomBoardGenerator());
        }
        return menuInstance;
    }

    public static Menu getMenuInstance(BoardDrawer boardDrawer, BoardGenerator boardGenerator) {
        if (menuInstance == null) {
            menuInstance = new CreationMenu(boardDrawer, boardGenerator);
        }
        return menuInstance;
    }
    @Override
    public Scene menuSetUp() {


        Scene scene;

        menuGridPane.setPadding(new Insets(10, 10, 10, 10)); //margins around
        menuGridPane.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
        menuGridPane.setHgap(50);
        menuGridPane.setVgap(10);
        menuGridPane.setMinHeight(200);
        menuGridPane.setMinWidth(200);

        Label generationLabel = new Label("Opcje tworzenia");
        Label widthLabel = new Label("Szerokosc (max 25)");
        TextField boardWidthTextField = new TextField("");
        Label heightLabel = new Label("Wysokosc (max 25)");
        TextField boardHeightTextField = new TextField("");
        Button buttonManual = new Button("Stworz nonogram recznie");
        Button buttonAuto = new Button("Wygeneruj nonogram");
        Button backButton = new Button("Powrot");

        buttonManual.setOnAction(e -> {
            int width = Integer.parseInt(boardWidthTextField.getText());
            int height = Integer.parseInt(boardHeightTextField.getText());
            if (verifySize(width, height)) {
                PuzzleMenu puzzleInstance = (PuzzleMenu) PuzzleMenu.getMenuInstance();
                puzzleInstance.setParentScene(menuInstance);
                puzzleInstance.updateBackButton();
                puzzleInstance.insertEmptyBoard(width, height);
                NonogramMain.getStage().setScene(puzzleInstance.getMenuScene());
            }
        });
        buttonAuto.setOnAction(e -> {
            int width = Integer.parseInt(boardWidthTextField.getText());
            int height = Integer.parseInt(boardHeightTextField.getText());
            if (verifySize(width, height)) {
                PuzzleMenu puzzleInstance = (PuzzleMenu) PuzzleMenu.getMenuInstance();
                puzzleInstance.setParentScene(menuInstance);
                puzzleInstance.updateBackButton();
                puzzleInstance.insertGeneratedBoard(width, height);
                NonogramMain.getStage().setScene(PuzzleMenu.getMenuInstance().getMenuScene());
            }
        });
        backButton.setOnAction(e -> {
            NonogramMain.getStage().setScene(MainMenu.getMenuInstance().getMenuScene());
        });

        menuGridPane.add(generationLabel, 0, 0);
        menuGridPane.add(widthLabel, 0, 1);
        menuGridPane.add(boardWidthTextField, 0, 2);
        menuGridPane.add(heightLabel, 0, 3);
        menuGridPane.add(boardHeightTextField, 0, 4);
        menuGridPane.add(buttonManual, 0, 5);
        menuGridPane.add(buttonAuto, 0, 6);
        menuGridPane.add(backButton, 0, 7);

        scene = new Scene(menuGridPane, Color.GRAY);
        return scene;
    }

    private boolean verifySize(int width, int height) {
        return (width <= 25 && height <= 25 && width >= 2 && height >= 2);
    }

}

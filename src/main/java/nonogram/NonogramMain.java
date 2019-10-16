package nonogram;

import javafx.application.Application;
import javafx.stage.Stage;
import nonogram.board.drawer.BoardDrawer;
import nonogram.board.drawer.FixedSizeDrawer;
import nonogram.board.generator.BoardGenerator;
import nonogram.board.generator.RandomBoardGenerator;
import nonogram.board.solver.BruteSolver;
import nonogram.board.solver.NonogramSolver;
import nonogram.files.controller.FileController;
import nonogram.files.controller.TxtFileController;
import nonogram.menu.CreationMenu;
import nonogram.menu.MainMenu;
import nonogram.menu.PuzzleListMenu;
import nonogram.menu.PuzzleMenu;


//Co drugi wiersz lub kolumna inny kolor zeby bylo latwiej rozroznic miedzy wierszami / kolumnami




public class NonogramMain extends Application {
    //I got main board part done
    //Now i need Row and a Column in my main display GridPane for all the board Data with numbers

    //Constants
    private static Integer puzzleSize = 500;
    private static BoardDrawer boardDrawer = new FixedSizeDrawer();
    private static FileController fileController = new TxtFileController();
    private static BoardGenerator boardGenerator = new RandomBoardGenerator();
    private static NonogramSolver bruteSolver = new BruteSolver();

    private static Stage stage;

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        PuzzleListMenu puzzleListMenu = (PuzzleListMenu) PuzzleListMenu.getMenuInstance(fileController);
        CreationMenu creationMenu = (CreationMenu) CreationMenu.getMenuInstance(boardDrawer,boardGenerator);
        PuzzleMenu puzzleMenu = (PuzzleMenu) PuzzleMenu.getMenuInstance(boardDrawer,boardGenerator,bruteSolver, puzzleSize);
        MainMenu mainMenu = (MainMenu) MainMenu.getMenuInstance(fileController);



        stage = primaryStage;
        primaryStage.setScene(mainMenu.getMenuScene());
        NonogramMain.getStage().sizeToScene();
        primaryStage.show();


    }

//    public Scene creationScene(BoardDrawer boardDrawer, FileController fileController, Integer WINDOW_HEIGHT, Integer WINDOW_WIDTH){
//
//        GridPane creationGrid = new GridPane();
//        creationGrid.setPadding(new Insets(10, 10, 10, 10)); //margins around
//        creationGrid.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
//        creationGrid.getRowConstraints().add(new RowConstraints(20)); // column 0 is 100 wide
//        creationGrid.setHgap(10);
//        creationGrid.setVgap(10);
//
//
//        Label filesLabel = new Label("Files options");
//        Label generationLabel = new Label("Creation options");
//        filesLabel.setStyle("-fx-background-color:POWDERBLUE");
//        TextField folderPath = new TextField("Folder path");
//        folderPath.setDisable(true);
//        Button folderButton = new Button("Choose nonograms folder");
//        Label widthLabel = new Label("Board width (max 25)");
//        TextField boardWidthTextField = new TextField("");
//        Label heightLabel = new Label("Board height (max 25)");
//        TextField boardHeightTextField = new TextField("");
//        Label percentLabel = new Label("Minimal 10% fill (automatic only)");
//        TextField boardPercentageTextField = new TextField("");
//        Button buttonManual = new Button("Create Nonogram manually");
//        Button buttonAuto = new Button("Generate Nonogram automatically");
//
//
//        folderButton.setOnAction(e ->{
//            String path = fileController.pickAFile(boardGrid, boardDrawer,fileController, WINDOW_HEIGHT, WINDOW_WIDTH);
//            if(!path.equals("")){
//                folderPath.setText(path);
//            }
//
//        });
//        buttonAuto.setOnAction(e ->{
//            int width = Integer.parseInt(boardWidthTextField.getText());
//            int height = Integer.parseInt(boardHeightTextField.getText());
//            int percentage = Integer.parseInt(boardPercentageTextField.getText());
//            if(width <= 25 && height <= 25 && width >= 2 && height >= 2 && percentage >= 10 && percentage <= 100){
//                Board test = boardGenerator.generateBoard(width,height, percentage);
//                boardDrawer.drawGrid(boardGrid, test, WINDOW_HEIGHT, WINDOW_WIDTH);
//
//                System.out.println("Hello World!");
//            }
//        });
//
//
//
//
//
//
//        Scene creationScene = new Scene(creationGrid, Color.GRAY);
//
//        return creationScene;
//    }
//
//    public Scene menuScene(BoardDrawer boardDrawer, FileController fileController){
//
//
//        GridPane menuGridLayout = new GridPane();
//
//        //Accordion sprawddz to
//        menuGridLayout.setPadding(new Insets(10, 10, 10, 10)); //margins around
//        menuGridLayout.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
//        menuGridLayout.getRowConstraints().add(new RowConstraints(20)); // column 0 is 100 wide
//        menuGridLayout.setHgap(10);
//        menuGridLayout.setVgap(10);
//        Label filesLabel = new Label("Files options");
//        Label generationLabel = new Label("Creation options");
//        filesLabel.setStyle("-fx-background-color:POWDERBLUE");
//        TextField folderPath = new TextField("Folder path");
//        folderPath.setDisable(true);
//        Button folderButton = new Button("Choose nonograms folder");
//        Label widthLabel = new Label("Board width (max 25)");
//        TextField boardWidthTextField = new TextField("");
//        Label heightLabel = new Label("Board height (max 25)");
//        TextField boardHeightTextField = new TextField("");
//        Label percentLabel = new Label("Minimal 10% fill (automatic only)");
//        TextField boardPercentageTextField = new TextField("");
//        Button buttonManual = new Button("Create Nonogram manually");
//        Button buttonAuto = new Button("Generate Nonogram automatically");
//
//        folderButton.setOnAction(e ->{
//            String path = fileController.pickAFile(boardGrid, boardDrawer,fileController, WINDOW_HEIGHT, WINDOW_WIDTH);
//            if(!path.equals("")){
//                folderPath.setText(path);
//            }
//
//        });
//        buttonAuto.setOnAction(e ->{
//            int width = Integer.parseInt(boardWidthTextField.getText());
//            int height = Integer.parseInt(boardHeightTextField.getText());
//            int percentage = Integer.parseInt(boardPercentageTextField.getText());
//            if(width <= 25 && height <= 25 && width >= 2 && height >= 2 && percentage >= 10 && percentage <= 100){
//                Board test = boardGenerator.generateBoard(width,height, percentage);
//                boardDrawer.drawGrid(boardGrid, test, WINDOW_HEIGHT, WINDOW_WIDTH);
//
//                System.out.println("Hello World!");
//
//                Stage secondStage = new Stage();
//                GridPane copy = new GridPane();
//                boardDrawer.drawGrid(copy, test, WINDOW_HEIGHT, WINDOW_WIDTH);
//                GridPane container = new GridPane();
//                container.getChildren().add(copy);
//                Scene puzzleScene = new Scene(container, Color.GRAY);
//
//                secondStage.setTitle("Automatic Puzzle");
//            }
//
//
//
//        });
//        ComboBox nonogramsComboBox = new ComboBox();
//        nonogramsComboBox.setMaxWidth(WINDOW_WIDTH - WINDOW_WIDTH/3);
//
//
//        menuGridLayout.add(filesLabel, 0, 0);
//        menuGridLayout.add(folderButton, 0, 1);
//        menuGridLayout.add(folderPath, 0, 2);
//        menuGridLayout.add(nonogramsComboBox, 0, 3);
//        menuGridLayout.add(widthLabel, 0, 4);
//        menuGridLayout.add(boardWidthTextField, 0, 5);
//        menuGridLayout.add(heightLabel, 0, 6);
//        menuGridLayout.add(boardHeightTextField, 0, 7);
//        menuGridLayout.add(percentLabel, 0, 8);
//        menuGridLayout.add(boardPercentageTextField, 0, 9);
//
//        menuGridLayout.add(buttonManual, 0, 10);
//        menuGridLayout.add(buttonAuto, 0, 11);
//
//        menuGridLayout.setMinHeight(WINDOW_HEIGHT);
//        menuGridLayout.setMinWidth(WINDOW_WIDTH/3);
//
//        Scene menuScene = new Scene(menuGridLayout, Color.GRAY);
//        return menuScene;
//
//
//    }
//
//
//    public GridPane setupMenu(BoardDrawer boardDrawer, FileController fileController){
//
//
//        GridPane menuGridLayout = new GridPane();
//
//        //Accordion sprawddz to
//        menuGridLayout.setPadding(new Insets(10, 10, 10, 10)); //margins around
//        menuGridLayout.getRowConstraints().add(new RowConstraints(40)); // column 0 is 100 wide
//        menuGridLayout.getRowConstraints().add(new RowConstraints(20)); // column 0 is 100 wide
//        menuGridLayout.setHgap(10);
//        menuGridLayout.setVgap(10);
//        Label filesLabel = new Label("Files options");
//        Label generationLabel = new Label("Creation options");
//        filesLabel.setStyle("-fx-background-color:POWDERBLUE");
//        TextField folderPath = new TextField("Folder path");
//        folderPath.setDisable(true);
//        Button folderButton = new Button("Choose nonograms folder");
//        Label widthLabel = new Label("Board width (max 25)");
//        TextField boardWidthTextField = new TextField("");
//        Label heightLabel = new Label("Board height (max 25)");
//        TextField boardHeightTextField = new TextField("");
//        Label percentLabel = new Label("Minimal 10% fill (automatic only)");
//        TextField boardPercentageTextField = new TextField("");
//        Button buttonManual = new Button("Create Nonogram manually");
//        Button buttonAuto = new Button("Generate Nonogram automatically");
//
//
//
//        folderButton.setOnAction(e ->{
//            String path = fileController.pickAFile(boardGrid, boardDrawer,fileController, WINDOW_HEIGHT, WINDOW_WIDTH);
//            if(!path.equals("")){
//                folderPath.setText(path);
//            }
//
//        });
//        buttonAuto.setOnAction(e ->{
//            int width = Integer.parseInt(boardWidthTextField.getText());
//            int height = Integer.parseInt(boardHeightTextField.getText());
//            int percentage = Integer.parseInt(boardPercentageTextField.getText());
//            if(width <= 25 && height <= 25 && width >= 2 && height >= 2 && percentage >= 10 && percentage <= 100){
//                Board test = boardGenerator.generateBoard(width,height, percentage);
//                boardDrawer.drawGrid(boardGrid, test, WINDOW_HEIGHT, WINDOW_WIDTH);
//
//                System.out.println("Hello World!");
//
//                Stage secondStage = new Stage();
//                GridPane copy = new GridPane();
//                boardDrawer.drawGrid(copy, test, WINDOW_HEIGHT, WINDOW_WIDTH);
//                GridPane container = new GridPane();
//                container.getChildren().add(copy);
//                Scene puzzleScene = new Scene(container, Color.GRAY);
//
//                secondStage.setTitle("Automatic Puzzle");
//                //secondStage.setScene(puzzleScene);
//                //secondStage.show();
//            }
//
//
//
//        });
//        ComboBox nonogramsComboBox = new ComboBox();
//        nonogramsComboBox.setMaxWidth(WINDOW_WIDTH - WINDOW_WIDTH/3);
//
//
//        menuGridLayout.add(filesLabel, 0, 0);
//        menuGridLayout.add(folderButton, 0, 1);
//        menuGridLayout.add(folderPath, 0, 2);
//        menuGridLayout.add(nonogramsComboBox, 0, 3);
//        menuGridLayout.add(widthLabel, 0, 4);
//        menuGridLayout.add(boardWidthTextField, 0, 5);
//        menuGridLayout.add(heightLabel, 0, 6);
//        menuGridLayout.add(boardHeightTextField, 0, 7);
//        menuGridLayout.add(percentLabel, 0, 8);
//        menuGridLayout.add(boardPercentageTextField, 0, 9);
//
//        menuGridLayout.add(buttonManual, 0, 10);
//        menuGridLayout.add(buttonAuto, 0, 11);
//
//        menuGridLayout.setMinHeight(WINDOW_HEIGHT);
//        menuGridLayout.setMinWidth(WINDOW_WIDTH/3);
//
//        Scene menuScene = new Scene(menuGridLayout, Color.GRAY);
//        return menuGridLayout;
//
//
//    }
}
package nonogram.board.drawer;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import nonogram.board.Board;
import nonogram.board.Cell;

public class FixedSizeDrawer implements BoardDrawer{


    @Override
    public void drawGrid(GridPane gameGrid, Board board, Integer WINDOW_HEIGHT, Integer WINDOW_WIDTH) {

        int biggerSide;
        if(board.getCells().length > board.getCells()[0].length){
            biggerSide = board.getCells().length;
        } else {
            biggerSide = board.getCells()[0].length;
        }
        if(biggerSide < 8){
            biggerSide =  8;
        }


        double imageSizeValue = ((WINDOW_HEIGHT-100)/biggerSide*2) + (0.5*Math.log(biggerSide));
        //int imageSize = (int)((imageSizeValue/3) + ((biggerSide*4)/35));
        int imageSize = (WINDOW_HEIGHT + WINDOW_WIDTH)/(2*biggerSide);

        GridPane rowHeaders = drawRowHints(board,imageSize);
        GridPane columnHeaders = drawColumnHints(board,imageSize);
        GridPane boardGrid = drawBoardGrid(board,imageSize);


        gameGrid.getChildren().clear();
        gameGrid.setStyle("-fx-background-color: lightblue;");
        gameGrid.setPadding(new Insets(0, 30, 30, 0)); //margins around the
        gameGrid.add(columnHeaders,1,0);
        gameGrid.add(rowHeaders, 0, 1);
        gameGrid.add(boardGrid,1,1);

    }

    public GridPane drawRowHints(Board board, int imageSize){
        GridPane rowHeaders = new GridPane();
        rowHeaders.setPadding(new Insets(imageSize/10, imageSize/10, imageSize/10, 0));
        rowHeaders.setAlignment(Pos.CENTER);

        int maxLength = 0;
        for(int i = 0; i < board.getRowsData().length; i++){
            if(board.getRowsData()[i].length > maxLength){
                maxLength = board.getRowsData()[i].length - 1;
            }
        }
        for(int x = maxLength; x >= 0; x--){
            rowHeaders.getColumnConstraints().add(new ColumnConstraints(imageSize + (imageSize/10))); // column 0 is 100 wide
        }
        for(int i = 0; i < board.getRowsData().length; i++){
            rowHeaders.getRowConstraints().add(new RowConstraints(imageSize + (imageSize/10))); // column 0 is 100 wide
            for(int j = 0; j < board.getRowsData()[i].length; j++){

                String temp = board.getRowsData()[i][(board.getRowsData()[i].length -1) - j].toString();
                Label tempLabel = new Label(temp);

                tempLabel.setFont(new Font("Arial", imageSize-(imageSize/8)));
                rowHeaders.add(tempLabel, maxLength - j, i);

                rowHeaders.setHalignment(tempLabel, HPos.CENTER); // To align horizontally in the cell
                rowHeaders.setValignment(tempLabel, VPos.CENTER); // To align vertically in the cell
            }
        }

        return rowHeaders;
    }

    public GridPane drawColumnHints(Board board, int imageSize){
        GridPane columnHeaders = new GridPane();
        columnHeaders.setPadding(new Insets(0, imageSize/10, imageSize/10, imageSize/10));
        columnHeaders.setAlignment(Pos.CENTER);
        for(int i = 0; i < board.getColumnsData().length; i++){
            columnHeaders.getColumnConstraints().add(new ColumnConstraints(imageSize + (imageSize/10))); // column 0 is 100 wide
            //Get single column hint
            for(int j = 0; j < board.getColumnsData()[i].length; j++){
                //This reverses order of hints
                //String temp = board.getColumnsData()[i][j].toString();

                String temp = board.getColumnsData()[i][(board.getColumnsData()[i].length -1) - j].toString();
                Label indicatorNumberLabel = new Label(temp);

                indicatorNumberLabel.setFont(new Font("Arial", imageSize-(imageSize/8)));
                columnHeaders.add(indicatorNumberLabel, i , board.getColumnsData().length - j);

                columnHeaders.setHalignment(indicatorNumberLabel, HPos.CENTER); // To align horizontally in the cell
                columnHeaders.setValignment(indicatorNumberLabel, VPos.CENTER); // To align vertically in the cell
            }
        }

        return columnHeaders;
    }

    public GridPane drawBoardGrid(Board board, int imageSize){

        GridPane boardGrid = new GridPane();
        boardGrid.setStyle("-fx-background-color: black;");
        boardGrid.setVgap(imageSize/10);
        boardGrid.setHgap(imageSize/10);
        boardGrid.setPadding(new Insets(imageSize/10, imageSize/10, imageSize/10, imageSize/10));
        Image covered = new Image("SquareImageCovered.png", imageSize, imageSize, true, false);
        Image unCovered = new Image("SquareImageUncovered.png", imageSize, imageSize, true, false);


//        int size = imageSize;
//
//        for (int i = 0; i < board.getCells().length; i++) {
//            RowConstraints rowConstraints = new RowConstraints();
//            rowConstraints.setMinHeight( imageSize + imageSize/10);
//
//            if((i+1)%5 == 0 || (i-1)%5 == 0 && i != 1){
//                rowConstraints.setMinHeight( imageSize + 5 + imageSize/10);
//            }
//
//            if(i%5 == 0 && i != 0){
//                rowConstraints.setMinHeight(imageSize + (imageSize/2));
//            }
//            boardGrid.getRowConstraints().add(rowConstraints);
//        }

        for (int i = 0; i < board.getCells().length; i++) {

            for (int j = 0; j < board.getCells()[0].length; j++) {
                ImageView nodeImage;

                    if(board.getCells()[i][j].isUncovered()){
                        nodeImage = new ImageView(unCovered);
                    } else {
                        nodeImage = new ImageView(covered);
                    }

                nodeImage.setOnMouseClicked(e -> {
                    int x = GridPane.getColumnIndex((Node) e.getSource());
                    int y = GridPane.getRowIndex((Node) e.getSource());
                    setCellImage(board.getCells()[y][x], nodeImage, imageSize, board.isSolvingMode());
                });

                boardGrid.add(nodeImage, j, i);
            }
        }
        return boardGrid;
    }

    private void setCellImage(Cell cell, ImageView cellImage, int imageSize, boolean solvingMode){
        if (cell.isUncovered()) {
            cellImage.setImage(new Image("SquareImageCovered.png", imageSize, imageSize, true, false));
            cell.setUncovered(false);
        } else {
//                        if(board.getCells()[y][x].isSafe()){
//                            nodeImage.setImage(new Image("SquareImageUncovered.png", imageSize, imageSize, true, false));
//                        } else {
//                            nodeImage.setImage(new Image("SquareImageDanger.png", imageSize, imageSize, true, false));
//                        }
            cellImage.setImage(new Image("SquareImageUncovered.png", imageSize, imageSize, true, false));
            cell.setUncovered(true);
        }
        if(!solvingMode){
            cell.setSafe(!cell.isSafe());
            System.out.println(cell);
        }
    }

}

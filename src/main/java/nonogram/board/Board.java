package nonogram.board;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private boolean solvingMode;
    private Cell[][] cells;
    private Integer[][] rowsData;
    private Integer[][] columnsData;

    public Board(int width, int height, boolean solvingMode){
        this.cells = getEmptyBoard(width,height);
        this.rowsData = rowsValues(cells);
        this.columnsData = columnValues(cells);
        this.solvingMode = solvingMode;
    }

    public Board(Integer[][] rowsData, Integer[][] columnsData, boolean solvingMode){
        this.cells = getEmptyBoard(columnsData.length, rowsData.length);
        this.rowsData = rowsData;
        this.columnsData = columnsData;
        this.solvingMode = solvingMode;
    }

    public Board(Cell[][] cells, Integer[][] rowsData, Integer[][] columnsData, boolean solvingMode) {
        this.cells = cells;
        this.rowsData = rowsData;
        this.columnsData = columnsData;
        this.solvingMode = solvingMode;
    }

    public boolean isSolvingMode() {
        return solvingMode;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Integer[][] getRowsData() {
        return rowsData;
    }

    public Integer[][] getColumnsData() {
        return columnsData;
    }

    private static Cell[][] getEmptyBoard(int width, int height){

        Cell[][] output = new Cell[height][width];

        for(int y = 0; y < height; y++){

            for(int x = 0; x < width; x++){
                output[y][x] = new Cell(false);
            }
        }

        return  output;
    }

    public static Integer[][] rowsValues(Cell[][] boardData) {

        List<Integer[]> rowsData = new ArrayList<>();

        for (int row = 0; row < boardData.length; row++) {
            int rowValue = 0;
            List<Integer> rowData = new ArrayList<>();

            for (int column = 0; column < boardData[0].length; column++) {
                if (boardData[row][column].isSafe()) {
                    rowValue++;
                } else {
                    if (rowValue > 0) {
                        rowData.add(rowValue);
                        rowValue = 0;
                    }
                }
            }
            if (rowValue > 0) {
                rowData.add(rowValue);
            }
            if(rowData.size() == 0){
                rowData.add(0);
            }
            rowsData.add(rowData.toArray(new Integer[rowData.size()]));
        }
        return rowsData.toArray(new Integer[rowsData.size()][]);
    }

    public static Integer[][] columnValues(Cell[][] boardData) {

        List<Integer[]> columnsData = new ArrayList<>();

        for (int column = 0; column < boardData[0].length; column++) {
            int columnValue = 0;
            List<Integer> columnData = new ArrayList<>();

            for (int row = 0; row < boardData.length; row++) {
                if (boardData[row][column].isSafe()) {
                    columnValue++;
                } else {
                    if (columnValue > 0) {
                        columnData.add(columnValue);
                        columnValue = 0;
                    }
                }
            }
            if (columnValue > 0) {
                columnData.add(columnValue);
            }
            if(columnData.size() == 0){
                columnData.add(0);
            }
            columnsData.add(columnData.toArray(new Integer[columnData.size()]));
        }
        return columnsData.toArray(new Integer[columnsData.size()][]);

    }

    public void setRowsData(Integer[][] rowsData) {
        this.rowsData = rowsData;
    }

    public void setColumnsData(Integer[][] columnsData) {
        this.columnsData = columnsData;
    }

    public boolean isSolved(){
        for(Cell[] cells : cells){
            for(Cell cell : cells){
                if((cell.isUncovered() && !cell.isSafe()) || (!cell.isUncovered() && cell.isSafe())){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
       String output = "";
       for(Cell[] cellsRow : cells){
           for (Cell cell : cellsRow){
               output += cell;
           }
           output += "\n";
       }
       return output;
    }

}
//        pawn.getImage().setOnMouseClicked(e -> {
//
//            int x = GridPane.getColumnIndex((Node) e.getSource());
//            int y = GridPane.getRowIndex((Node) e.getSource());
//
//            //selection logic
//            if (selectedPawn == null) {
//                selectionLogic(x, y);
//            } else {
//                moveLogic(x, y);
//            }
//        });

//    -fx-background-color: white ; -fx-padding: 10 ;
//rowsHeaders.setVgap(imageSize/10);
//rowsHeaders.setHgap(imageSize/10);
//rowsHeaders.getColumnConstraints().add(new ColumnConstraints(imageSize)); // column 0 is 100 wide
//rowsHeaders.getRowConstraints().add(new RowConstraints(imageSize)); // column 1 is 200 wide
//        for(int i = 0; i < rowsData.length; i++){
//            for(int j = 0; j < rowsData[i].length; j++){
//                Label tempLabel = new Label(rowsData[i][j].toString());
//                tempLabel.setFont(new Font("Cambria", imageSize));
//                rowsHeaders.add(tempLabel, i,j);
//            }
//        }
//            //gridPane.setStyle("-fx-background-color: lightgray; -fx-vgap: 5; -fx-hgap: 5; -fx-padding: 5;");
//            //rowsHeaders.setStyle("-fx-background-color: lightgray; -fx-vgap: 5; -fx-hgap: 5; -fx-padding: 5;");
//-fx-background-fill: black, white ;
//-fx-background-insets: 0, 1 ;
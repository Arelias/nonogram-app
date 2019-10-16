package nonogram.board.solver;

import nonogram.board.Board;

import java.util.Arrays;

class SlideSolver {
    public Board solve(Integer[][] rowsHints, Integer[][] columnsHints) {
        Board board = new Board(rowsHints, columnsHints, true);
        int height = rowsHints.length;
        int width = columnsHints.length;

        for (int i = 0; i < height; i++) {
            int[] row = getOverlap(rowsHints[i], width);
            fillRow(board, row, i);
        }
        for (int i = 0; i < width; i++) {
            int[] column = getOverlap(columnsHints[i], height);
            fillColumn(board, column, i);
        }
        return board;
    }

    public int maxHint(int[] rowHints, int[] columnHints){
        int output = -1;
        for(int i = 0; i < rowHints.length; i++){
            if(output < rowHints[i]){
                output = rowHints[i];
            }
        }
        for(int i = 0; i < columnHints.length; i++){
            if(output < columnHints[i]){
                output = columnHints[i];
            }
        }
        return output;
    }
    //dostaje tu pasek postaci 3 2 1
    //zamienic na 2 paski typu 11102203000 itp itd jeden lewo drugi prawo
    //porownac i wypluc pasek postaci 011000000 gdzie jedynki sa tylko dla pokrywajacych sie pol
    public int[] getOverlap(Integer[] strip, int size){
        int[] outputOverlap;
        Integer[] stripLeft = shiftHintsLeft(strip, size);
        Integer[] stripRight = shiftHintsRight(strip, size);
        outputOverlap = compareStrips(stripLeft, stripRight, size);

        return outputOverlap;
    }

    public int[] compareStrips(Integer[] stripLeft, Integer[] stripRight, int size){

        int[] output = new int[size];
        Arrays.fill(output, 0);

        for(int i = 0; i < stripLeft.length; i++){
            if(stripLeft[i] == stripRight[i] && stripLeft[i] != 0){
                output[i] = 1;
            }
        }
        return output;
    }

    public void fillRow(Board board, int[] row, int rowIndex){

        for(int i = 0; i < row.length; i++){
            if(row[i] == 1){
                board.getCells()[rowIndex][i].setSafe(true);
            }

        }

    }

    public void fillColumn(Board board, int[] column, int columnIndex){

        for(int i = 0; i < column.length; i++){
            if(column[i] == 1){
                board.getCells()[i][columnIndex].setSafe(true);
            }

        }

    }

    public Integer[] shiftHintsLeft(Integer[] strip, int size){
        Integer[] output = new Integer[size];
        Arrays.fill(output, 0);

        int index = 0;
        //przechodzimy po { 3, 2, 1 }
        for(int i = 0; i < strip.length; i++){
            //I teraz chcemy wypelnic tyle pol ile wartosc ma element wskazowki
            for(int j = strip[i]; j > 0; j--){
                output[index] = i+1;
                index++;
            }
            index++;
        }
        return output;
    }

    public Integer[] shiftHintsRight(Integer[] strip, int size){
        Integer[] output = new Integer[size];
        Arrays.fill(output, 0);

        int index = output.length-1;
        //przechodzimy po { 3, 2, 1 }
        for(int i = strip.length-1; i >= 0; i--){
            //I teraz chcemy wypelnic tyle pol ile wartosc ma element wskazowki
            for(int j = strip[i]; j > 0; j--){
                output[index] = i+1;
                index--;
            }
            index--;
        }
        return output;
    }

    public void compareBoard(Board originalBoard, Board solvedBoard){

        int counter = 0;
        for(int y = 0; y < originalBoard.getCells().length; y++){
            for(int x = 0; x < originalBoard.getCells()[y].length; x++){


                if(originalBoard.getCells()[y][x].isSafe() == solvedBoard.getCells()[y][x].isSafe()){
                    System.out.print("1");
                } else {
                    System.out.print("0");
                    counter++;
                }

            }
            System.out.println();
        }
        System.out.println("Total difference: " + counter);
    }
}

package nonogram.board.solver;

import nonogram.board.Board;
import nonogram.board.Cell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteSolver implements NonogramSolver {

    //can be optimised, after each iteration of solving i could check for size == 1 and then put those rows / columns into board
    @Override
    public SolutionDto solve(Integer[][] rowsHints, Integer[][] columnsHints) {

        SlideSolver slideSolver = new SlideSolver();
        Board board = slideSolver.solve(rowsHints, columnsHints);
        SolutionDto outputSolution = generateOutput(board);

        return outputSolution;
    }

    private SolutionDto generateOutput(Board inputBoard){

        SolutionDto outputSolution;
        int height = inputBoard.getRowsData().length;
        int width = inputBoard.getColumnsData().length;
        int size = 0;
        int possibilities = 1;
        boolean solvable = false;

        List<Integer[]> emptyInput = new ArrayList<>();
        List<Integer[]> outputCombinations = new ArrayList<>();
        List<List<Integer[]>> allRows = new ArrayList<>();
        List<List<Integer[]>> allColumns = new ArrayList<>();

        for(int y = 0; y < height; y++){
            List<Integer> rowHints = Arrays.asList(inputBoard.getRowsData()[y]);
            generateAllPossibilities(inputBoard, -1, y, rowHints, width, emptyInput, outputCombinations);
            allRows.add(outputCombinations);
            emptyInput = new ArrayList<>();
            outputCombinations = new ArrayList<>();
        }
        for(int x = 0; x < width; x++){
            List<Integer> columnHints = Arrays.asList(inputBoard.getColumnsData()[x]);
            generateAllPossibilities(inputBoard, x, -1, columnHints, height, emptyInput, outputCombinations);
            allColumns.add(outputCombinations);
            emptyInput = new ArrayList<>();
            outputCombinations = new ArrayList<>();
        }


        for (List<Integer[]> colCombs : allColumns) {
            size += colCombs.size();
        }
        for (List<Integer[]> rowCombs : allRows) {
            size += rowCombs.size();
        }

        for(int i = 0; i < 100; i++) {
            int sizeTemp = 0;
            allColumns = columnsSwipe(allColumns, allRows);
            allRows = columnsSwipe(allRows, allColumns);
            for (List<Integer[]> colCombs : allColumns) {
                sizeTemp += colCombs.size();
            }
            for (List<Integer[]> rowCombs : allRows) {
                sizeTemp += rowCombs.size();
            }
            if(sizeTemp == size){
                solvable = true;
                break;
            } else {
                size = sizeTemp;
            }
        }

        if(solvable == true){
            for (List<Integer[]> colCombs : allColumns) {
                possibilities *= colCombs.size();
            }
            for (List<Integer[]> rowCombs : allRows) {
                possibilities *= rowCombs.size();
            }

            fillRows(inputBoard,allRows);
            fillColumns(inputBoard,allColumns);
            outputSolution = new SolutionDto(possibilities, inputBoard);

            return outputSolution;

        } else {

            System.out.println("Board is not solvable");
            Board dummy = new Board(inputBoard.getRowsData(),inputBoard.getColumnsData(), false);
            outputSolution = new SolutionDto(0, dummy);

            return outputSolution;
        }


    }

    private void fillRows(Board board, List<List<Integer[]>> allRows){
        for(int y = 0; y < allRows.size(); y++){
            if(allRows.get(y).size() >= 1){
                try {
                    fillRow(board,allRows.get(y), y);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fillRow(Board board, List<Integer[]> row, int rowIndex) throws IllegalAccessException {

        for(int i = 0; i < board.getCells()[0].length; i++) {
            if (!board.getCells()[rowIndex][i].isSafe() && row.get(0)[i] == 1) {
                board.getCells()[rowIndex][i].setSafe(true);
            } else if (board.getCells()[rowIndex][i].isSafe() && row.get(0)[i] == 0) {
                throw new IllegalAccessException("This row is wrong, index = " + rowIndex);
            }
        }
    }

    private void fillColumns(Board board, List<List<Integer[]>> allColumns){
        for(int x = 0; x < allColumns.size(); x++){
            if(allColumns.get(x).size() == 1){
                try {
                    fillColumn(board,allColumns.get(x), x);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void fillColumn(Board board, List<Integer[]> column, int colIndex) throws IllegalAccessException {
        for(int i = 0; i < board.getCells().length; i++) {
            if (!board.getCells()[i][colIndex].isSafe() && column.get(0)[i] == 1) {
                board.getCells()[i][colIndex].setSafe(true);
            } else if (board.getCells()[i][colIndex].isSafe() && column.get(0)[i] == 0) {
                throw new IllegalAccessException("This column is wrong, index = " + colIndex);
            }
        }
    }

    //x = -1 generujemy wiersze
    //y = -1 generujemy kolumny
    private void generateAllPossibilities(Board board, int x, int y, List<Integer> elements, int size, List<Integer[]> recursions, List<Integer[]> output) {


        List<Integer> recursionElements = new ArrayList<>(elements);
        Integer currentElement = -1;

        if(elements.contains(0)){
            Integer[] temp;
            if(x > 0){
                temp = new Integer[board.getRowsData().length];
            } else {
                temp = new Integer[board.getColumnsData().length];
            }
            Arrays.fill(temp,0);
            recursions.add(temp);
        }




        //If elements size == 0 we reached the end of recursion and got final combinations
        if (elements.size() == 0 || elements.contains(0)) {
            for (Integer[] recursion : recursions) {
                if (checkOption(board, x, y, recursion)) {
                    output.add(recursion);
                }
            }
        } else {
            currentElement = recursionElements.get(0);
            recursionElements.remove(0);
            if (recursions.size() == 0) {
                //Initialise
                Integer[] recursionsInit = new Integer[size];
                Arrays.fill(recursionsInit, 0);
                int endIndex = endIndex(recursionsInit.length - 1, recursionElements);
                recursions = shiftElement(recursionsInit, currentElement, endIndex);
                generateAllPossibilities(board, x, y, recursionElements, size, recursions, output);
            } else {
                for (Integer[] recursion : recursions) {
                    //If passes check
                    //Create List<Integer[]> from this recursion
                    //pass the recursion to another testingRecursion step

                    int endIndex = endIndex(recursion.length - 1, recursionElements);
                    List<Integer[]> newRecursions = shiftElement(recursion, currentElement, endIndex);
                    generateAllPossibilities(board, x, y, recursionElements, size, newRecursions, output);
                }
            }
        }
    }

    //Jesli sprawdzam jedna kombinacje col dla wszystkich z wiersza to chyba sprawdzam tak naprawde kolumne a nie wiersz?????
    //Ogarnac to jutro jak sie wyspie

    //Sprawdza czy ta opcja (wiersz lub kolumna) jest mozliwa w naszej planszy
    //Mozna zoptymalizowac, dajac koncowy indeks funkcji, bo sprawdzalbym tez nieukonczone opcje ale tylko do indeksu, bo reszta beda zera i sie wysypie
    private boolean checkOption(Board board, int x, int y, Integer[] option) {


        //If x then it must be a column
        //If y then it must be a row
        if (x >= 0) {
            //Column
            for (int i = 0; i < board.getCells().length; i++) {
                Cell cell = board.getCells()[i][x];
                if (cell.isSafe() && option[i] == 0) {
                    return false;
                }
            }
        } else {
            //Row
            for (int i = 0; i < option.length; i++) {
                //if row from y and row element from i
                Cell cell = board.getCells()[y][i];
                if (cell.isSafe() && option[i] == 0) {
                    return false;
                }
            }

        }

        return true;
    }

    private List<List<Integer[]>> columnsSwipe(List<List<Integer[]>> allColumns, List<List<Integer[]>> allRows){

        List<List<Integer[]>> output = new ArrayList<>();

        for(int i = 0; i < allColumns.size(); i++){
            List<Integer[]> temp = new ArrayList<>();
            for(Integer[] colOption : allColumns.get(i)){
                //i is column index
                if(checkAllRowsCombinations(colOption,allRows,i)){
                    temp.add(colOption);
                }

            }
            output.add(temp);
        }
        return output;
    }
    //Column is a collection of possibilities for single collumn
    private boolean checkAllRowsCombinations(Integer[] column, List<List<Integer[]>> allRows, int colIndex){
        List<List<Integer[]>> output = new ArrayList<>(allRows);
        for(int y = 0; y < allRows.size(); y++){

            if(!checkRowAllCombinations(column,allRows.get(y),colIndex,y)){
                return false;
            }
        }
        return true;
    }

    private boolean checkRowAllCombinations(Integer[] columnOption, List<Integer[]> row, int colIndex, int rowIndex){
        boolean output = false;

        for(int i = 0; i < row.size(); i++){
            if(checkRowCombination(columnOption,row.get(i),colIndex,rowIndex)){
                output = true;
            }
        }
        return output;
    }
    //Row index - which row in the board that is, so which col element to use
    //Col index - which column in the board that is, so which row element to use
    private boolean checkRowCombination (Integer[] columnOption, Integer[] rowOption, int colIndex, int rowIndex){

        if(rowOption[colIndex] == columnOption[rowIndex]){
            return true;
        } else {
            return false;
        }
    }
    //Przesuwa dany element pomiedzy startem a koncem na wszystkie mozliwe sposoby i wypluwa kolekcje
    private List<Integer[]> shiftElement(Integer[] strip, int element, int end) {

        int start = 0;
        List<Integer[]> output = new ArrayList<>();

        for (int x = strip.length - 1; x >= 0; x--) {
            if (strip[x] != 0) {
                start = x + 2;
                break;
            }
        }

        for (int i = start; i + element <= end + 1; i++) {
            Integer[] temp = strip.clone();
            int tempElement = element;
            for (int x = i; tempElement > 0; tempElement--) {
                temp[x + tempElement - 1] = 1;
            }

            output.add(temp);
        }
        return output;
    }

    private int endIndex(int stripLength, List<Integer> hints) {
        int output = stripLength - sumHints(0, hints);
        return output;
    }

    //Ta funkcja bierze pod uwage ze jesli mam 2 elementy,
    //to musze dodac jeszcze przerwe dla pierwszego, ktory zabralem z listy
    private int sumHints(int start, List<Integer> hints) {

        int output = 0;
        Integer[] limitedHints = new Integer[hints.size() - start];
        for (int x = 0; start < hints.size(); start++, x++) {
            limitedHints[x] = hints.get(start);
        }
        if (limitedHints.length != 0) {
            return sumHints(limitedHints) + 1;
        } else {
            return 0;
        }
    }

    private int sumHints(Integer[] hints) {
        int output = 0;
        for (int hint : hints) {
            output += hint;
        }
        //For X elements there is always x-1 gaps betweeen them
        output += hints.length - 1;

        return output;
    }
}

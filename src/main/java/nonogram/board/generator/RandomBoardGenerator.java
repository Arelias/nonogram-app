package nonogram.board.generator;

import nonogram.board.Board;
import nonogram.board.Cell;
import nonogram.board.solver.BruteSolver;
import nonogram.board.solver.NonogramSolver;
import nonogram.board.solver.SolutionDto;

import java.util.Random;

public class RandomBoardGenerator implements BoardGenerator {


    @Override
    public Board generateBoard(int width, int height) {
        return generateBoard(width,height,60);
    }
    @Override
    public Board generateBoard(int width, int height, int percentageFill) {
        Board board = new Board(width,height, true);
        //Cell[][] cells;
        SolutionDto solutionDto;
        Random random = new Random();
        NonogramSolver bruteSolver = new BruteSolver();
        int counter = 0;
        do {
            //cells = Board.getEmptyBoard(width, height);
            while (countFillPercentage(board.getCells()) < percentageFill) {


                if(random.nextInt(2) == 0){
                    randomRowFill(board.getCells(),width,height);
                } else {
                    randomColFill(board.getCells(),width,height);
                }
            }

            Integer[][] rowsData = Board.rowsValues(board.getCells());
            Integer[][] colsData = Board.columnValues(board.getCells());
            solutionDto = bruteSolver.solve(rowsData, colsData);
            counter++;
            if(counter == 10){
                percentageFill = percentageFill+5;
                counter = 0;
            }

        } while (solutionDto.getSolutionsCount() > 1);



        return solutionDto.getBoard();
    }

    private int countFill(Cell[][] cells){
        int output = 0;
        for(Cell[] row : cells){
            for(Cell cell : row){
                if(cell.isSafe() == true){
                    output++;
                }
            }
        }

        return output;
    }

    private double countFillPercentage(Cell[][] cells){


        double cellCount = cells.length*cells[0].length;
        double fillCount = countFill(cells);

        return ((fillCount/cellCount)*100);

    }

    private void randomRowFill(Cell[][] cells, int width, int height) {
        Random random = new Random();
        int rowToFill = random.nextInt(height);
        rowFill(cells,width,rowToFill);
    }

    private void rowFill(Cell[][] cells, int width, int rowIndex) {
        Random random = new Random();
        int fillAmount = random.nextInt(width/2) + 1;
        int startIndex = random.nextInt(width-fillAmount);

        for(int i = 0; i < fillAmount; i++){
            cells[rowIndex][i+startIndex].setSafe(true);
        }
    }

    private void randomColFill(Cell[][] cells, int width, int height) {
        Random random = new Random();
        int colToFill = random.nextInt(width);
        colFill(cells,height,colToFill);
    }

    private void colFill(Cell[][] cells, int height, int colIndex) {
        Random random = new Random();
        int fillAmount = random.nextInt(height/2) + 1;
        int startIndex = random.nextInt(height-fillAmount);

        for(int i = 0; i < fillAmount; i++){
            cells[i+startIndex][colIndex].setSafe(true);
        }
    }
}
//                int choice = random.nextInt(2);
//                if (random.nextInt(2) == 0) {
//                    randomRowFill(cells, width, height);
//                } else {
//                    randomColFill(cells, width, height);
//                }

//                for(int x = 0; x < width; x++){
//                    colFill(cells,width,height,x);
//                }
//                for(int y = 0; y < height; y++){
//                    rowFill(cells,width,height,y);
//                }
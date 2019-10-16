package nonogram.files.controller;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import nonogram.board.Board;
import nonogram.board.Cell;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TxtFileController implements FileController {

    @Override
    public void saveFile(Board board) {
        String output = "";
        output += "WIDTH: " + System.getProperty("line.separator");
        output += board.getCells()[0].length + System.getProperty("line.separator");
        output += "HEIGHT: " + System.getProperty("line.separator");
        output += board.getCells().length + System.getProperty("line.separator");
        output += "BOARD: " + System.getProperty("line.separator");
        for (Cell[] cellsRow : board.getCells()) {
            for (Cell cell : cellsRow) {
                if (cell.isSafe()) {
                    output += '#';
                } else {
                    output += '0';
                }
            }
            output += System.getProperty("line.separator");
        }
        try {
            File file;
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            file = fileChooser.showSaveDialog(null);
            if (file != null) {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.write(output);
                writer.close();
            }

        } catch (Exception e) {
            System.out.println("File not saved " + e);
        }
    }


    public Board parseFile(String path) {
        try {
            String[] gameFile;
            Charset charset = Charset.forName("UTF-8");
            if(path.equals("")){
                gameFile = Files.readAllLines(Paths.get("Example.txt"), charset).toArray(new String[0]);
            } else {
                gameFile = Files.readAllLines(Paths.get(path), charset).toArray(new String[0]);
            }
            int width = Integer.parseInt(gameFile[1]);
            int height = Integer.parseInt(gameFile[3]);
            Cell[][] cells = new Cell[height][width];
            Integer[][] rowsData;
            Integer[][] columnsData;

            for (int i = 0; i < height; i++) {
                char[] data = gameFile[5 + i].toCharArray();
                char[] test = new char[width];
                Arrays.fill(test, ' ');
                for(int x = 0; x < width; x++){
                    test[x] = data[x];
                }
                data = test;
                for (int j = 0; j < width; j++) {
                    Cell cell = new Cell(data[j] == '#');
                    cells[i][j] = cell;

                }
            }

            rowsData = Board.rowsValues(cells);
            columnsData = Board.columnValues(cells);

            return new Board(cells, rowsData, columnsData, true);

        } catch (Exception e) {
            System.out.println("Save file corrupted! " + e);
            return null;
        }
    }

    @Override
    public Board loadFile() {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String filePath = file.getPath();
            Board board = parseFile(filePath);
            return board;
        }
        return null;
    }
    @Override
    public Board[] loadDirectory() {
        File directory;
        List<Board> boards = new ArrayList<>();
        DirectoryChooser fileChooser = new DirectoryChooser();
        directory = fileChooser.showDialog(null);
        if (directory != null) {
            if(directory.isDirectory()){
                File[] files = directory.listFiles();
                for(File file : files){
                    String fileName = file.getName();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1, file.getName().length());
                    System.out.println(">> fileExtension" + fileExtension);
                    if(fileExtension.equals("txt")){
                        Board board = parseFile(file.getPath());
                        if(board != null){
                            boards.add(board);
                        }
                    }
                }
            }
        }
        return boards.toArray(new Board[boards.size()]);
    }

    public void printHints(String header, Integer[][] hints){
        System.out.println(header);

        for(Integer[] strip : hints){
            for(Integer cell : strip){
                System.out.print(cell);
            }
            System.out.println();
        }
    }
}
//    @Override
//    public String pickAFile(GridPane mainBoardGrid, BoardDrawer boardDrawer, FileController fileController, Integer WINDOW_HEIGHT, Integer WINDOW_WIDTH) {
//        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "Txt board files", "txt", "cfg");
//        chooser.setFileFilter(filter);
//        int returnVal = chooser.showOpenDialog(null);
//        if (returnVal == JFileChooser.APPROVE_OPTION) {
//
//            System.out.println("You chose to open this file: " +
//                    chooser.getSelectedFile().getName());
//            mainBoardGrid.getChildren().clear();
//            String filePath = chooser.getSelectedFile().getPath();
//            Board board = fileController.loadFile(filePath);
//            boardDrawer.drawGrid(mainBoardGrid, board, WINDOW_HEIGHT, WINDOW_WIDTH);
//            return filePath;
//        }
//        return "";
//    }
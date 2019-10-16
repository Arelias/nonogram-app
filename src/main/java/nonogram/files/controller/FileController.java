package nonogram.files.controller;

import javafx.scene.layout.GridPane;
import nonogram.board.Board;
import nonogram.board.drawer.BoardDrawer;

public interface FileController {

    public Board loadFile();
    public Board[] loadDirectory();

    public void saveFile(Board board);
}

package nonogram.board.drawer;

import javafx.scene.layout.GridPane;
import nonogram.board.Board;

public interface BoardDrawer {

    public void drawGrid(GridPane gameBoardGrid, Board board, Integer WINDOW_HEIGHT, Integer WINDOW_WIDTH);
}

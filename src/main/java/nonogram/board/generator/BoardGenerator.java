package nonogram.board.generator;

import nonogram.board.Board;

public interface BoardGenerator {

    public Board generateBoard(int width, int height);
    public Board generateBoard(int width, int height, int percentageFill);

}

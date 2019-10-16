package nonogram.board.solver;

import nonogram.board.Board;

public final class SolutionDto {

    private final int solutionsCount;
    private final Board board;

    public SolutionDto(int solutionsCount, Board board) {
        this.solutionsCount = solutionsCount;
        this.board = board;
    }

    public int getSolutionsCount() {
        return solutionsCount;
    }

    public Board getBoard() {
        return board;
    }
}

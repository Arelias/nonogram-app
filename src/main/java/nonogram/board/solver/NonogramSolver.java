package nonogram.board.solver;

public interface NonogramSolver {
    public SolutionDto solve(Integer[][] rowsHints, Integer[][] columnsHints);
}

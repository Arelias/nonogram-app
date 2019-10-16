package nonogram.board;

public class Cell {

    private boolean uncovered = false;
    private boolean safe;

    public Cell(boolean safe) {
        this.safe = safe;
    }

    public boolean isUncovered() {
        return uncovered;
    }

    public void setUncovered(boolean uncovered) {
        this.uncovered = uncovered;
    }

    public boolean isSafe() {
        return safe;
    }

    public void setSafe(boolean safe) {
        this.safe = safe;
    }

    @Override
    public String toString() {
        if (this.isSafe()){
            return "1";
        } else {
            return "0";
        }
    }
}

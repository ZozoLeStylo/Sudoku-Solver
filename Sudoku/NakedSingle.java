package Sudoku;

public class NakedSingle extends DeductionRule {
    @Override
    public boolean apply(SudokuGrid grid, int i, int j) {
        boolean progress = false;
        Cell cell = grid.getCell(i, j);
        if (cell.possibleMoves.size() == 1 && cell.value == 0) {
            int value = cell.possibleMoves.iterator().next();
            cell.setValue(value);
            grid.getCell(i,j).clearPossibleMoves();
            progress = true;
            //grid.display(); // if we want to display the grid at each cell modification, to follow the progress of the resolution
        }
        return progress;
    }
}
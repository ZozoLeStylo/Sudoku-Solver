package Sudoku;

// Strategy Pattern for Deduction Rules (all Rules classes wiil implement this class)
public abstract class DeductionRule {
    public boolean apply(SudokuGrid grid, int i, int j) {
        return false;
    }
}
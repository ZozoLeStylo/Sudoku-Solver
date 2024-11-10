package Sudoku;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Singleton Pattern for SudokuGrid
public class SudokuGrid {
    private static SudokuGrid instance;
    private Cell[][] grid;

    // constructor
    private SudokuGrid() {
        grid = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        // Add observers to each cell for row, column, and block
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell cell = grid[i][j];
                // Add observers for the row
                for (int col = 0; col < 9; col++) {
                    if (col != j) {
                        cell.addObserver(grid[i][col]);
                    }
                }
                // Add observers for the column
                for (int row = 0; row < 9; row++) {
                    if (row != i) {
                        cell.addObserver(grid[row][j]);
                    }
                }
                // Add observers for the block
                int blockRowStart = (i / 3) * 3;
                int blockColStart = (j / 3) * 3;
                for (int row = blockRowStart; row < blockRowStart + 3; row++) {
                    for (int col = blockColStart; col < blockColStart + 3; col++) {
                        if (row != i || col != j) {
                            cell.addObserver(grid[row][col]);
                        }
                    }
                }
            }
        }
    }

    // Singleton Pattern
    public static SudokuGrid getInstance() {
        if (instance == null) {
            instance = new SudokuGrid();
        }
        return instance;
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean isSolved() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void loadFromFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Grilles/" + filename));
        String line;
        int row = 0;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            for (int col = 0; col < 9; col++) {
                if (Integer.parseInt(values[col]) != 0) {
                    int value = Integer.parseInt(values[col]);
                    grid[row][col].setValue(value);
                }
            }
            row++;
        }
        reader.close();
    }

    public void display() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getValue() == 0) {
                    System.out.print(" |");
                }
                else {
                    System.out.print(grid[i][j].getValue() + "|");
                }
            }
            System.out.println();
        }
        System.out.println("__________________");
    }
}

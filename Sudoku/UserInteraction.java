package Sudoku;

import java.util.*;

public class UserInteraction {
    static Scanner scanner = new Scanner(System.in);

    public static boolean askUserForInput(SudokuGrid grid) {

        grid.display();

        System.out.print("Enter row (0-8): ");
        int row = scanner.nextInt();

        System.out.print("Enter column (0-8): ");
        int col = scanner.nextInt();
        
        System.out.print("Enter value (1-9): ");
        int value = scanner.nextInt();

        Cell cell = grid.getCell(row, col);
        if (cell.getPossibleMoves().contains(value)) {
            // if the given value is not detected as wrong
            cell.setValue(value);
            return false ;
            
        } else {
            // if the given value is detected as wrong
            System.out.println("Please restart the solving - inconsistent value entered.");
            return true ;

        }
    }
}
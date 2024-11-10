package Sudoku;

import java.io.IOException;

public class SudokuSolver {

    // Create an array with three deduction rules that will be used to solve the sudoku grid
    DeductionRule[] rules = {
        DeductionRuleFactory.createRule("NakedSingle"),
        DeductionRuleFactory.createRule("HiddenSingle"),
        DeductionRuleFactory.createRule("NakedPairs")
    };

    // Method to apply deduction rule on each cell of the grid
    public int solve(SudokuGrid grid, int UsingRules) {
        // progress will indicate when deduction rules will no longer make changes to cells
        boolean progress ;

        // UsingRules indicate the number of rule we use (1 if we only use DR1, 2 for DR1 and DR2, 3 for DR1, DR2 and DR3 and 4 if theses three rules can't resolve the grid)
        while (UsingRules < 4) {
            progress = false ;
            for (int x=0 ; x<UsingRules ; x ++) { // To test different rules one by one

                for (int i=0 ; i<9 ; i++) {
                    for (int j=0 ; j<9 ; j++) { // To go through each cell

                        // We test a cell only if it has no value
                        if (grid.getCell(i,j).getValue() == 0) {

                            // If a cell has no value and no possible moves remaining, we made a mistake, error detected
                            if (grid.getCell(i,j).getPossibleMoves().size() == 0) {
                                System.out.println("Please restart the solving - inconsistent value entered.");
                                return 5;
                            }

                            // Otherwise we apply the rule on the cell (and check if the rule made changes with a boolean return value)
                            progress |= rules[x].apply(grid,i,j) ;
                        }
                    }
                }
            }
            // If the grid is solved, we return UsingRules integer to get the level of difficulty of the grid
            if (grid.isSolved()) {
                return UsingRules ;
            }

            // If the current rules no longer make changes, we incrmeents UsingRules to use one more rule (or to ask user for help if the 3 rules have already been used)
            if (!progress) {
                UsingRules ++ ;
            }
        }
        
        return UsingRules ;
    }

    // To print at the end of the solving the level of difficulty of the grid
    public void displayDifficulty(int difficulty) {
        switch(difficulty){
   
            case 1: 
                System.out.println("It was an easy Sudoku !");
                break;
        
            case 2:
                System.out.println("It was a medium Sudoku !");
                break;
        
            case 3:
                System.out.println("It was a diffcult Sudoku !");
                break;
            default:
                System.out.println("It was a very difficult Sudoku !");
                break;
        }
    }

    public static void main(String[] args) throws IOException {

        SudokuGrid grid = SudokuGrid.getInstance() ;

        // to load the grid from the file in parameters
        grid.loadFromFile(args[0]);
        
        // to display the grid initialized
        grid.display();

        SudokuSolver solver = new SudokuSolver() ;

        // we only use the first rule at the beggining
        int difficulty = 1 ;
        int UsingRule = 1 ;

        while (!grid.isSolved()) {
            UsingRule = solver.solve(grid, UsingRule-1) ;

            if (UsingRule == 5) {
                // if the solve() method detect an error
                break ;
            }
            if (difficulty < 4) {
                // to update the level of difficulty which will be print at the end
                difficulty = UsingRule ;
            }

            if (!grid.isSolved()) {
                // if the 3 rules could not solve the grid, the user is asked for help
                boolean test = UserInteraction.askUserForInput(grid);
                if (test) {
                    // if askUserForInput method return true, the user entered a wrong value, error detected
                    break ;
                }
            }
            else {
                // when the grid is finally solved
                grid.display();
                System.out.println("Sudoku solved !");
                solver.displayDifficulty(difficulty);
            }
        }
    }
}
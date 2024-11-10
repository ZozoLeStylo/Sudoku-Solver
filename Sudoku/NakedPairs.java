package Sudoku;

import java.util.*;

public class NakedPairs extends DeductionRule {
    @Override
    public boolean apply(SudokuGrid grid, int i, int j) {

        // to get possible moves of the cell [i][j]
        Set<Integer> possibleMoves = new HashSet<>() ;
        possibleMoves = grid.getCell(i,j).getPossibleMoves() ;
        if (possibleMoves.size() !=2 ) {
            // if there is less or more than two possible moves, the rule can't be applied
            return false ;
        }
        // to get an array of the naked pair in this cell
        int[] val = new int[2] ;
        Iterator<Integer> it = possibleMoves.iterator() ;
        val[0] = it.next() ;
        val[1] = it.next() ;

        boolean progress = false ;
        // 3 different methods to apply the rule to the row, the column and the block of the cell
        progress |= applyToRow(grid, possibleMoves, val, i, j) ;
        progress |= applyToColumn(grid, possibleMoves, val, i, j) ;
        progress |= applyToBlock(grid, possibleMoves, val, i, j) ;
        return progress ;
    }

    public boolean applyToRow(SudokuGrid grid, Set<Integer> possibleMoves, int[] val, int i, int j) {
        // to apply the rule to the row of the cell
        int coord = -1 ;
        for (int k=0; k<9; k++) {
            if (k != j) {
                Set<Integer> pMoves = new HashSet<>() ;
                pMoves = grid.getCell(i,k).getPossibleMoves() ;
                if (possibleMoves.equals(pMoves) && pMoves.size() == 2) {
                    if (coord == -1) {
                        coord = k ;
                    }
                    else { return false ; }
                }
                else { continue ; }
            }
        }
        if (coord != -1) {
            boolean test = false ;
            for (int k=0; k<9; k++) {
                if (k != j && k != coord) {
                    for (int v : val) {
                        Set<Integer> pMoves2 = grid.getCell(i,k).getPossibleMoves();
                        if (test == false && (pMoves2.contains(v))) {
                            test = true ;
                            grid.getCell(i, k).removePossibleMoves(v);
                        }
                    }
                }
            }
            return test ;
        }

        return false ;
    }

    public boolean applyToColumn(SudokuGrid grid, Set<Integer> possibleMoves, int[] val, int i, int j) {
        // to apply the rule to the column of the cell
        int coord = -1 ;
        for (int k=0; k<9; k++) {
            if (k != i) {
                Set<Integer> pMoves = new HashSet<>() ;
                pMoves = grid.getCell(k,j).getPossibleMoves() ;
                if (possibleMoves.equals(pMoves) && pMoves.size() == 2) {
                    if (coord == -1) {
                        coord = k ;
                    }
                    else { return false ; }
                }
                else { continue ; }
            }
        }
        if (coord != -1) {
            boolean test = false ;
            for (int k=0; k<9; k++) {
                if (k != i && k != coord) {
                    for (int v : val) {
                        Set<Integer> pMoves2 = grid.getCell(k,j).getPossibleMoves();
                        if (test == false && (pMoves2.contains(v))) {
                            test = true ;
                            grid.getCell(k, j).removePossibleMoves(v);
                        }
                    }
                }
            }
            return test ;
        }

        return false ;
    }

    public boolean applyToBlock(SudokuGrid grid, Set<Integer> possibleMoves, int[] val, int i, int j) {
        // to apply the rule to the block of the cell
        int[] coord = {-1, -1} ;
        int ii = i / 3 ; int jj = i / 3 ;
        for (int k = ii*3; k < ii*3+3; k++) {
            for (int l = jj*3; l < jj*3+3; l++) {
                if (k != i && l != j) {
                    Set<Integer> pMoves = new HashSet<>() ;
                    pMoves = grid.getCell(i,k).getPossibleMoves() ;
                    if (possibleMoves.equals(pMoves)) {
                        if (coord[0] == -1) {
                            coord[0] = k ; coord[1] = l ;
                        }
                        else { return false ; }
                    }
                    else { continue ; }
                }
            }
        }
        if (coord[0] != -1) {
            boolean test = false ;
            for (int k = ii*3; k < ii*3+3; k++) {
                for (int l = jj*3; l < jj*3+3; l++) {
                    System.out.print("APPLY TO BLOCK") ;
                    if ((k != i && l != j) || (k != coord[0] && l != coord[1])) {
                        for (int v : val) {
                            Set<Integer> pMoves2 = grid.getCell(k,l).getPossibleMoves();
                            if (test == false && pMoves2.contains(v)) {
                                test = true ;
                                grid.getCell(k, l).removePossibleMoves(v);
                            }
                        }
                    }
                }
            }
            return test ;
        }
        return false ;
    }

}
package Sudoku;

import java.util.*;

public class HiddenSingle extends DeductionRule {
    @Override
    public boolean apply(SudokuGrid grid, int i, int j) {
        Set<Integer> possibleMoves = new HashSet<>() ;
        possibleMoves = grid.getCell(i,j).getPossibleMoves() ;
        Map <Integer, Integer> count = new HashMap<>() ;
        for (int m : possibleMoves) {
            count.put(m, 0) ;
        }
        Set<Integer> keys = count.keySet() ;
        boolean progress = false ;

        // 3 different methods to apply the rule to the row, the column and the block of the cell
        progress |= applyToRow(grid, keys, count, i, j) ;
        progress |= applyToColumn(grid, keys, count, i, j) ;
        progress |= applyToBlock(grid, keys, count, i, j) ;
        //if (progress) { grid.display() ; } // if we want to display the grid at each cell modification, to follow the progress of the resolution
        return progress ;
    }

    public boolean applyToRow(SudokuGrid grid, Set<Integer> keys, Map<Integer,Integer> count, int i, int j) {
        // to apply the rule to the row of the cell
        for (int k=0; k < 9; k++) {
            Set<Integer> pMoves = new HashSet<>() ;
            pMoves = grid.getCell(i,k).getPossibleMoves() ;
            for (int x : keys) {
                if (pMoves.contains(x)) {
                    int v = count.get(x) ;
                    count.replace(x, v+1) ;
                }
            }
        }
        for (int y : keys) {
            if (count.get(y) == 1) {
                grid.getCell(i,j).setValue(y);
                grid.getCell(i,j).clearPossibleMoves();
                return true ;
            }
        }
        return false ;
    }

    public boolean applyToColumn(SudokuGrid grid, Set<Integer> keys, Map<Integer,Integer> count, int i, int j) {
        // to apply the rule to the column of the cell
        for (int k=0; k < 9; k++) {
            Set<Integer> pMoves = new HashSet<>() ;
            pMoves = grid.getCell(k,j).getPossibleMoves() ;
            for (int x : keys) {
                if (pMoves.contains(x)) {
                    int v = count.get(x) ;
                    count.replace(x, v+1) ;
                }
            }
        }
        for (int y : keys) {
            if (count.get(y) == 1) {
                grid.getCell(i,j).setValue(y);
                grid.getCell(i,j).clearPossibleMoves();
                return true ;
            }
        }
        return false ;
    }

    public boolean applyToBlock(SudokuGrid grid, Set<Integer> keys, Map<Integer,Integer> count, int i, int j) {
        // to apply the rule to the block of the cell
        int ii = i / 3 ; int jj = i / 3 ;
        for (int k = ii*3; k < ii*3+3; k++) {
            for (int l = jj*3; l < jj*3+3; l++) {
                Set<Integer> pMoves = new HashSet<>() ;
                pMoves = grid.getCell(k,l).getPossibleMoves() ;
                for (int x : keys) {
                    if (pMoves.contains(x)) {
                        int v = count.get(x) ;
                        count.replace(x, v+1) ;
                    }
                }
            }
        }
        for (int y : keys) {
            if (count.get(y) == 1) {
                grid.getCell(i,j).setValue(y);
                grid.getCell(i,j).clearPossibleMoves();
                return true ;
            }
        }
        return false ;
    }
}
package Sudoku;
import java.util.*;

// Observer Pattern for updating possible moves
public class Cell implements CellObserver {
    int value;
    Set<Integer> possibleMoves; // set of possible values ​​for a cell (initialized to {1,2,...,9}), all values are possible at the beggining)
    private List<CellObserver> observers; // list of CellObserver object to notify all cells of the row, the column and the block of this cell when his value is updated
    private int row;
    private int col;

    // constructor
    public Cell(int row, int col) {
        this.value = 0;
        this.row = row;
        this.col = col;
        this.possibleMoves = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        this.observers = new ArrayList<>();
    }

    public void setValue(int value) {
        this.value = value;
        if (value != 0) {
            // when a cell get a value, we clear his set of possible moves
            possibleMoves.clear();
            // and we call notifyObservers() method to notify all cells of the row, the column and the block of this cell
            notifyObservers();
        }
    }

    public int getValue() {
        return value;
    }

    public int[] getCoords() {
        return new int[] {row, col} ;
    }

    public Set<Integer> getPossibleMoves() {
        return possibleMoves ;
    }

    public void removePossibleMoves(int value) {
        possibleMoves.remove(value) ;
    }

    public void clearPossibleMoves() {
        possibleMoves.clear() ;
    }

    // to add CellObserver to precise which cells will be notified when a change of value occurs
    public void addObserver(CellObserver observer) {
        observers.add(observer);
    }

    // to remove value of possible moves when notifyObservers() method is called
    private void notifyObservers() {
        for (CellObserver observer : observers) {
            observer.update(value);
        }
    }

    @Override
    public void update(int value) {
        possibleMoves.remove(value);
    }
}
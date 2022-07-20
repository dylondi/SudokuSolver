import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringsRepository;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Checks if a number is valid in a given cell of a sudoku board by creating
 * a SudokuConstraintChecker object and . Implements Callable to give the
 * ability to have a return value from a process ran on a separate thread.
 */
public class SudokuConstraintCheckerCallable implements Callable<Boolean> {

    /**
     * Instance Objects and Variables
     */
    private Map<Point, Integer> unsolvedBoard;
    private int row;
    private int column;
    private int number;


    /**
     * Constructor for which the sudoku board is passed, the row and column of cell is passed, and the
     * number that requires validation to be inserted into the cell is also passed.
     * @param unsolvedBoard
     * @param row
     * @param column
     * @param number
     */
    public SudokuConstraintCheckerCallable(Map<Point, Integer> unsolvedBoard, int row, int column, int number){
        this.unsolvedBoard = unsolvedBoard;
        this.row = row;
        this.column = column;
        this.number = number;
    }


    /**
     * Checks if the number is valid in the given cell.
     * @return Boolean indicating if num is valid in cell.
     */
    @Override
    public Boolean call() {

        SudokuConstraintChecker constraintChecker = new SudokuConstraintChecker(row, column, number);
        return constraintChecker.isNumValid(unsolvedBoard);
    }
}



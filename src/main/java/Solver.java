import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringsRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;


/**
 * Solves sudoku's represented by a LinkedHashMap<Point, Integer>, both single-threaded and multithreaded depending on which method is called.
 */
public class Solver{


    /**
     * Instance Objects & Variables
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(Solver.class);
    private Map<Point, Integer> sudokuBoard;
    private static ExecutorService threadPool;

    private static final int EMPTY_CELL = 0;
    private static final int BOARD_SIDE_LENGTH = 9;
    private static final int SUB_BOX_SIDE_LENGTH = 3;

    private int row;
    private int column;
    private int number;

    /**
     * Constructor used to solve sudoku concurrently
     * @param sudokuBoard
     * @param threadPool
     */
    public Solver(Map<Point, Integer> sudokuBoard, ExecutorService threadPool){
        this.sudokuBoard = sudokuBoard;
        this.threadPool = threadPool;
    }

    /**
     * Constructor used to solve sudoku with single thread
     * @param sudokuBoard
     */
    public Solver(Map<Point, Integer> sudokuBoard){
        this.sudokuBoard = sudokuBoard;
    }

    public Map<Point, Integer> getSudokuBoard() {
        return sudokuBoard;
    }

    /**
     * Attempts to solve sudoku board recursively using a backtracking algorithm.
     * @return boolean representing if sudoku is solved.
     */
     boolean solveSudoku() {

        //loop through each cell of sudoku board
        for (Map.Entry<Point, Integer> cell : this.sudokuBoard.entrySet()) {

                if (cell.getValue() == EMPTY_CELL) {

                    //loop through possible cell value numbers 1 - 9
                    for (int targetNum = 1; targetNum <= BOARD_SIDE_LENGTH; targetNum++) {

                         row = (int) cell.getKey().getX();
                         column = (int) cell.getKey().getY();
                         number = targetNum;

                        //if constraints satisfied, the number can be placed in the cell
                        if (isNumValid(sudokuBoard)) {

                                cell.setValue(targetNum);

                                //recursive call to backtrack
                                if (solveSudoku()) {
                                    return true;
                                }
                                //else not a solution
                                else {
                                    cell.setValue(EMPTY_CELL);
                                }
                            }
                    }
                    return false;
                }
        }
        // sudoku solved
        return true;
    }


    /**
     * Attempts to solve sudoku board recursively using a backtracking algorithm and concurrently with multiple threads.
     * @return boolean representing if sudoku is solved.
     * @throws Exception
     */
     boolean solveSudokuThreaded() throws Exception {

         //loop through each cell of sudoku board
        for (Map.Entry<Point, Integer> cell : this.sudokuBoard.entrySet()) {

             if (cell.getValue() == EMPTY_CELL) {

                 List<Future<Boolean>> list = new ArrayList<>();

                    //loop through possible cell value numbers 1 - 9
                    for (int targetNum = 1; targetNum <= BOARD_SIDE_LENGTH; targetNum++) {

                          row = (int) cell.getKey().getX();
                          column = (int) cell.getKey().getY();
                          number = targetNum;

                           //submit a SudokuConstraintCheckerCallable object to the threadPool. SudokuConstraintCheckerCallable implements Callable<Boolean> which returns boolean from call method.
                            Future<Boolean> isNumValid = threadPool.submit(new SudokuConstraintCheckerCallable(sudokuBoard, row, column, number));
                            list.add(isNumValid);
                    }

                    int i=0;

                    for(Future<Boolean> bool : list){
                        i++;
                        //if constraints satisfied, the number can be placed in the cell
                        if (bool.get()) {

                            cell.setValue(i);

                            //recursive call to backtrack
                            if (solveSudokuThreaded()) {
                                return true;
                            } else {
                                //else not a solution
                                cell.setValue(EMPTY_CELL);
                            }
                        }
                    }
                    return false;
                }
        }

        // sudoku solved
        return true;
    }

    /**
     * Checks if a number is valid in a given cell.
     * @param unsolvedBoard
     * @return boolean indicating if number is valid in cell
     */
    private boolean isNumValid(Map<Point, Integer> unsolvedBoard){
        SudokuConstraintChecker constraintChecker = new SudokuConstraintChecker(row, column, number);
        return constraintChecker.isNumValidInRow(unsolvedBoard) && constraintChecker.isNumValidInColumn(unsolvedBoard) && constraintChecker.isNumValidInSubBox(unsolvedBoard);
    }


    /**
     * Prints the current instance's sudoku board
     */
     void display () {

        //i represents column num of cell, j represents row number
        int i = 0;
        int j = 0;

        System.out.println(StringsRepository.SPLIT_ROWS);
        System.out.print("|");

        //traverse each value of sudoku board
        for (Map.Entry<Point, Integer> cell : sudokuBoard.entrySet()) {

            //if end of row
            if (i >= BOARD_SIDE_LENGTH) {

                System.out.println();
                i = 0;
                j++;

                //every 3 rows we print a line splitting previous row from the next one
                if(j==SUB_BOX_SIDE_LENGTH){
                    System.out.println(StringsRepository.SPLIT_ROWS);
                    j=0;
                }
                //print at start of each new line
                System.out.print("|");
            }

              System.out.print(" " + cell.getValue() + " ");

            //if i is equal to 2, 5, or 8 we print a sub box divider
            if( i>0 && (i+1)%SUB_BOX_SIDE_LENGTH==0 && i<BOARD_SIDE_LENGTH ){
                System.out.print("|");
            }
            i++;
        }
        System.out.println();
        System.out.println(StringsRepository.SPLIT_ROWS);
    }
}



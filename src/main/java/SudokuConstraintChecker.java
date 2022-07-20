import java.awt.*;
import java.util.Map;

/**
 * Checks if a number is valid in a given cell of a sudoku board.
 */
public class SudokuConstraintChecker {

    /**
     * Instance Objects & Variables
     */
    private static final int SUB_BOX_SIDE_LENGTH = 3;
    private int row;
    private int column;
    private int number;


    /**
     * Constructor for which the row and column of cell is passed, and the
     * number that requires validation to be inserted into the cell is also passed.
     * @param row
     * @param column
     * @param number
     */
    public SudokuConstraintChecker(int row, int column, int number) {
        this.row = row;
        this.column = column;
        this.number = number;
    }

    /**
     * Checks if number is valid in a given cell.
     * @param unsolvedBoard
     * @return boolean indicating if number is valid in cell
     */
     boolean isNumValid(Map<Point, Integer > unsolvedBoard){
        return isNumValidInRow(unsolvedBoard) && isNumValidInColumn(unsolvedBoard) && isNumValidInSubBox(unsolvedBoard);
    }

    /**
     * Checks if a number is valid in a given row.
     * @param unsolvedBoard
     * @return boolean indicating if number is valid in row
     */
    boolean isNumValidInRow(Map<Point, Integer> unsolvedBoard) {

        for (Map.Entry<Point, Integer> cell : unsolvedBoard.entrySet()) {
            if ((int) cell.getKey().getX() == row && cell.getValue() == number) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if a number is valid in a given column.
     * @param unsolvedBoard
     * @return boolean indicating if number is valid in column.
     */
    boolean isNumValidInColumn(Map<Point, Integer> unsolvedBoard){


        for (Map.Entry<Point, Integer> cell : unsolvedBoard.entrySet()) {
            if ((int) cell.getKey().getY() == column && cell.getValue() == number) {
                return false;
            }
        }
        return true;
    }


    /**
     * Checks if a number is valid in a given sub box.
     * @param unsolvedBoard
     * @return boolean indicating if number is valid in sub box.
     */
    boolean isNumValidInSubBox(Map<Point, Integer> unsolvedBoard){

        int originRow = row - row % SUB_BOX_SIDE_LENGTH;
        int originColumn = column - column % SUB_BOX_SIDE_LENGTH;

        for (Map.Entry<Point, Integer> cell : unsolvedBoard.entrySet()) {

            int x = (int) cell.getKey().getX();
            int y = (int) cell.getKey().getY();


            if (x >= originRow && x < originRow + SUB_BOX_SIDE_LENGTH &&
                    y >= originColumn && y < originColumn + SUB_BOX_SIDE_LENGTH &&
                    cell.getValue() == number) {
                return false;
            }
        }
        return true;
    }
}

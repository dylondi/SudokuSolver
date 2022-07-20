import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class SolverTest {

    CsvParser csvParser;
    CsvParser csvParserTwo;
    LinkedHashMap<Point, Integer> unsolvedSudoku;
    LinkedHashMap<Point, Integer> solvedSudoku;
    Solver solver;



    ExecutorService threadPool;

    @BeforeEach
    void setUp() {
        csvParser = new CsvParser("sudoku1");
        csvParserTwo = new CsvParser("solver");

        unsolvedSudoku = csvParser.generateMapFromCsv();
        solvedSudoku = csvParserTwo.generateMapFromCsv();
        threadPool = Executors.newCachedThreadPool();
        solver = new Solver(unsolvedSudoku, threadPool);


    }

    @Test
    void traverseAndSolveSudokuBoard() throws Exception {

        solver.solveSudoku();

        if(!solvedSudoku.equals(unsolvedSudoku)){
            fail();
        }


    }

    @Test
    void traverseAndSolveSudokuBoardThreaded() throws Exception {
        solver.solveSudokuThreaded();

        if(!solvedSudoku.equals(unsolvedSudoku)){
            fail();
        }
    }

}
import exceptions.InvalidParameterException;
import exceptions.InvalidParameterValueException;
import util.StringsRepository;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);


    public static void main(String[]args) throws Exception {


        if(args.length==3) {

            /**
            * String Initialisation containing argument values
            */
            String flag = args[0];
            String value = args[1];
            String filename = args[2];

            /**
            * Instantiate CsvParser Object and generate LinkedHashMap<Point, Integer> containing unsolved Sudoku board.
            * Point class contains x and y variable which represents row and column of a given cell, whilst the Integer represents the value of the cell.
            */
            CsvParser csvParser = new CsvParser(filename);
            LinkedHashMap<Point, Integer> unsolvedBoard = csvParser.generateMapFromCsv();

            Solver solver;

            if (flag.matches("-p")) {

                /**
                 * If user passes the value 1 after the -p flag, utilising the multithreaded algorithm, we create a CachedThreadPool
                 * and Initialize the Solver object previously defined. We attempt to solve the sudoku concurrently, and then deal with
                 * the shutdown of the CachedThreadPool.
                 *
                 * Else if the user passes the value 0 after the -p flag, utilising the single-threaded algorithm, we attempt to solve
                 * the sudoku with a single thread.
                 */

                LOGGER.info(StringsRepository.P_FLAG_INPUTTED);

                if (value.matches("1")) {

                    LOGGER.info(StringsRepository.P_FLAG_VALUE_1_INPUTTED);

                    ExecutorService threadPool = Executors.newCachedThreadPool();
                    solver = new Solver(unsolvedBoard, threadPool);

                    solveWithMultipleThreads(solver);

                    threadPool.shutdown();
                    threadPool.awaitTermination(30, TimeUnit.SECONDS);

                } else if (value.matches("0")) {

                    LOGGER.info(StringsRepository.P_FLAG_VALUE_0_INPUTTED);

                    solver = new Solver(unsolvedBoard);

                    solveWithSingleMainThread(solver);
                } else {
                    throw new InvalidParameterValueException(StringsRepository.INVALID_P_FLAG_VALUE_EXCEPTION_MESSAGE);
                }
            } else {
                throw new InvalidParameterException(args[0] + StringsRepository.INVALID_FLAG_TYPE_EXCEPTION_MESSAGE);
            }

        }else{
            throw new InvalidParameterException(StringsRepository.INVALID_PARAMETER_EXCEPTION_MESSAGE);
        }
    }


    /**
     * Attempts to solve the sudoku with the single main thread. If the sudoku is solved successfully it will be printed.
     * @param solver
     * @throws Exception
     */
    private static void solveWithSingleMainThread(Solver solver) {

        LOGGER.info(StringsRepository.SOLVING_SINGLE_THREADED);

        long startTime = System.nanoTime();

        if (solver.solveSudoku()) {

            long endTime = System.nanoTime();

            solver.display();
            System.out.println(StringsRepository.NANOSECONDS_SOLVING_SINGLE_THREADED + (endTime - startTime));

        } else {
            System.out.println(StringsRepository.UNSOLVABLE);
        }
    }

    /**
     * Attempts to solve the sudoku concurrently with multiple threads. If the sudoku is solved successfully it will be printed.
     * @param solver
     * @throws Exception
     */
    private static void solveWithMultipleThreads(Solver solver) throws Exception {

        LOGGER.info(StringsRepository.SOLVING_MULTI_THREADED);

        long startTime = System.nanoTime();

        if (solver.solveSudokuThreaded()) {

            long endTime = System.nanoTime();

            solver.display();
            System.out.println(StringsRepository.NANOSECONDS_SOLVING_MULTI_THREADED + (endTime - startTime));

        } else {
            System.out.println(StringsRepository.UNSOLVABLE);
        }
    }


}

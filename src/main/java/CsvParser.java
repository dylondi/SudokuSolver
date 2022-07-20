import exceptions.InvalidCsvException;
import exceptions.InvalidLinkedHashMapInsertionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.StringsRepository;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Parses a CSV file containing a sudoku board into a LinkedHashMap<Point, Integer> object.
 */
public class CsvParser {


    /**
     * Instance variables and objects
     */
    private static final int EMPTY_CELL = 0;
    private static final int BOARD_SIDE_LENGTH = 9;
    private static String filename;
    private LinkedHashMap<Point, Integer> sudokuHashMap;
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvParser.class);

    public CsvParser(String filename) {
        this.filename = filename;
        sudokuHashMap = new LinkedHashMap<>();
    }

    /**
     * Generates LinkedHashMap containing sudoku board from CSV file.
     * @return Parsed sudoku board from a CSV file to a LinkedHashMap containing a Point
     * object with x(row) and y(column) values and an Integer representing the cell value.
     */
    public LinkedHashMap<Point, Integer> generateMapFromCsv() {

        List<String> listOfRows = parseCsv();
        validateRows(listOfRows);
        populateLinkedHashMap(listOfRows);

        return sudokuHashMap;
    }

    /**
     * Validates each row, currently represented as Strings, has the correct amount of values (9).
     * If not, an InvalidCsvException is thrown.
     * @param listOfRows
     */
    void validateRows(List<String> listOfRows) {
        for(String row : listOfRows){
            String[] tempRow = row.replaceAll("\\s", "").split(",");
            if(tempRow.length!=BOARD_SIDE_LENGTH){
                throw new InvalidCsvException(StringsRepository.INVALID_NUM_OF_VALUES_IN_ROW);
            }
        }
        LOGGER.info(StringsRepository.ROW_SIZES_ARE_VALID);

    }

    /**
     * Populates sudokuHashMap representing the full sudoku board by parsing a list of Strings containing each row of the sudoku.
     * Any value that is not a digit will be updated to 0, which represents an unsolved cell. Any value not between 0 and 9 will
     * throw an InvalidHashMapInsertionException.
     * @param listOfRows
     */
    void populateLinkedHashMap(List<String> listOfRows) {

        LOGGER.info(StringsRepository.ATTEMPTING_TO_POPULATE_LINKEDHASHMAP);

        for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
            String[] tempRow = listOfRows.get(i).replaceAll("\\s", "").split(",");

            for (int j = 0; j < BOARD_SIDE_LENGTH; j++) {

                if (tempRow[j].chars().allMatch(Character::isDigit) && !tempRow[j].equals("")) {

                    int cellValue = Integer.parseInt(tempRow[j]);
                    if(cellValue<0 || cellValue>9){
                        throw new InvalidLinkedHashMapInsertionException(StringsRepository.INVALID_LINKEDHASHMAP_INSERTION_NOT_BETWEEN_0_AND_9);
                    }
                    sudokuHashMap.put(new Point(i, j), cellValue);
                } else {
                    sudokuHashMap.put(new Point(i, j), EMPTY_CELL);
                }
            }
        }
    }

    /**
     * Parses a CSV file using an InputStream and a BufferedReader.
     * @return A list of Strings, each containing a line from the CSV file.
     */
    public List<String> parseCsv() {

        List<String> listOfRows;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        if (classLoader == null) {
            classLoader = getClass().getClassLoader();
        }

        LOGGER.info(StringsRepository.ATTEMPTING_TO_CREATE_BUFFEREDREADER_TO_PARSE_CSV);

        try (InputStream resource = classLoader.getResourceAsStream(filename)) {
            listOfRows =
                    new BufferedReader(new InputStreamReader(resource,
                            StandardCharsets.UTF_8)).lines().collect(Collectors.toList());

        } catch(NullPointerException e){
            throw new InvalidCsvException(StringsRepository.INVALID_CSV_EMPTY_NONEXISTENT_EXCEPTION_MESSAGE);
        } catch(Exception e){
            throw new InvalidCsvException(e + StringsRepository.INVALID_CSV_EXCEPTION_MESSAGE);

        }

        if(listOfRows.size()==BOARD_SIDE_LENGTH){
            return listOfRows;
        }else{
            throw new InvalidCsvException(StringsRepository.INVALID_NUM_OF_ROWS_EXCEPTION);
        }
    }
}

import exceptions.InvalidCsvException;
import exceptions.InvalidLinkedHashMapInsertionException;
import util.StringsRepository;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvParserTest {

    String fileName;


    @org.junit.jupiter.api.Test
    void generateArrayFromCsvMissingValuesInRow() {

        fileName = "sudoku-test-1";
        CsvParser csvParser = new CsvParser(fileName);

        try {
            LinkedHashMap<Point, Integer> unsolvedSudoku = csvParser.generateMapFromCsv();
            fail();
        }
        catch(InvalidCsvException e){
            assertEquals(StringsRepository.INVALID_NUM_OF_VALUES_IN_ROW, e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void parseCsvFileNonExistent() {
        fileName = "sudoku-non-existent";
        CsvParser csvParser = new CsvParser(fileName);

        try {
            csvParser.parseCsv();
            fail();
        }
        catch(InvalidCsvException e){
            System.out.print(e.getMessage());
            assertEquals(StringsRepository.INVALID_CSV_EMPTY_NONEXISTENT_EXCEPTION_MESSAGE, e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void parseCsvEmptyOrNotEnoughRowsInFile() {
        fileName = "sudoku-test-2";
        CsvParser csvParser = new CsvParser(fileName);

        try {
            csvParser.parseCsv();
            fail();
        }
        catch(InvalidCsvException e){
            System.out.print(e.getMessage());
            assertEquals(StringsRepository.INVALID_NUM_OF_ROWS_EXCEPTION, e.getMessage());
        }
    }



    @org.junit.jupiter.api.Test
    void populateLinkedHashMapTestNumberNotBetween0And9() {

        fileName = "sudoku-test-3";
        CsvParser csvParser = new CsvParser(fileName);
        List<String> listOfRows = csvParser.parseCsv();

        csvParser.validateRows(listOfRows);

        try {
            csvParser.populateLinkedHashMap(listOfRows);
            fail();
        }catch(InvalidLinkedHashMapInsertionException e){
            assertEquals(StringsRepository.INVALID_LINKEDHASHMAP_INSERTION_NOT_BETWEEN_0_AND_9, e.getMessage());
        }
    }
}
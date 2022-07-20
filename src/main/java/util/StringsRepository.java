package util;

public class StringsRepository {

    public static final String SOLVING_MULTI_THREADED = "Solving sudoku with multiple threads";
    public static final String SOLVING_SINGLE_THREADED = "Solving sudoku on single thread";
    public static final String NANOSECONDS_SOLVING_MULTI_THREADED = "Nanoseconds to solve multi-threaded: ";
    public static final String NANOSECONDS_SOLVING_SINGLE_THREADED = "Nanoseconds to solve single-threaded: ";
    public static final String UNSOLVABLE = "Unsolvable";
    public static final String INVALID_P_FLAG_VALUE_EXCEPTION_MESSAGE = "Flag -p value can only be 0 or 1. 0 indicating a single-threaded execution and 1 indicating a multi-threaded execution.";
    public static final String INVALID_FLAG_TYPE_EXCEPTION_MESSAGE = " is not a valid parameter. Use flag -p to determine whether to run the sudoku solver on a multi-threaded basis or not. Following the flag -p with either 0 or 1 to decide single-threaded or multi-threaded execution, respectively.";
    public static final String INVALID_PARAMETER_EXCEPTION_MESSAGE = "Use flag -p to determine whether to run the sudoku solver on a multi-threaded basis or not. Following the flag -p with either 0 or 1 to decide single-threaded or multi-threaded execution, respectively. The third and final argument is the name of the csv file containing the unsolved sudoku";
    public static final String INVALID_CSV_EMPTY_NONEXISTENT_EXCEPTION_MESSAGE = "NullPointerException: CSV file is empty or doesn't exist.";
    public static final String INVALID_CSV_EXCEPTION_MESSAGE = ", Could not load CSV file.";
    public static final String INVALID_LINKEDHASHMAP_INSERTION_NOT_BETWEEN_0_AND_9 = "One or more values in CSV file not between 0 and 9.";
    public static final String INVALID_NUM_OF_ROWS_EXCEPTION = "Invalid number of rows in CSV file.";
    public static final String INVALID_NUM_OF_VALUES_IN_ROW = "Each row must have 9 values.";
    public static final String ATTEMPTING_TO_CREATE_BUFFEREDREADER_TO_PARSE_CSV = "Attempting to create BufferedReader to read CSV file and parse each line into a list of Strings.";
    public static final String ATTEMPTING_TO_POPULATE_LINKEDHASHMAP = "Attempting to populate LinkedHashMap.";
    public static final String ROW_SIZES_ARE_VALID = "Row sizes are valid.";
    public static final String P_FLAG_INPUTTED = "Flag -p inputted by user";
    public static final String P_FLAG_VALUE_1_INPUTTED = "Value 1 for flag -p inputted by user, indicating to solve concurrently.";
    public static final String P_FLAG_VALUE_0_INPUTTED = "Value 0 for flag -p inputted by user, indicating to solve with single thread.";
    public static final String SPLIT_ROWS = "-------------------------------";

}

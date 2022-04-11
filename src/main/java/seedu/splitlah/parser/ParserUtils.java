package seedu.splitlah.parser;

import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.commandparser.ActivityCreateCommandParser;
import seedu.splitlah.parser.commandparser.ActivityDeleteCommandParser;
import seedu.splitlah.parser.commandparser.ActivityEditCommandParser;
import seedu.splitlah.parser.commandparser.ActivityViewCommandParser;
import seedu.splitlah.parser.commandparser.ActivityListCommandParser;
import seedu.splitlah.parser.commandparser.ExitCommandParser;
import seedu.splitlah.parser.commandparser.GroupEditCommandParser;
import seedu.splitlah.parser.commandparser.GroupListCommandParser;
import seedu.splitlah.parser.commandparser.GroupViewCommandParser;
import seedu.splitlah.parser.commandparser.GroupCreateCommandParser;
import seedu.splitlah.parser.commandparser.GroupDeleteCommandParser;
import seedu.splitlah.parser.commandparser.HelpCommandParser;
import seedu.splitlah.parser.commandparser.SessionCreateCommandParser;
import seedu.splitlah.parser.commandparser.SessionDeleteCommandParser;
import seedu.splitlah.parser.commandparser.SessionEditCommandParser;
import seedu.splitlah.parser.commandparser.SessionListCommandParser;
import seedu.splitlah.parser.commandparser.SessionSummaryCommandParser;
import seedu.splitlah.parser.commandparser.SessionViewCommandParser;
import seedu.splitlah.ui.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

/**
 * A utility class that provides supporting parsing methods to the main Parser class.
 * 
 * @author Warren
 */
public class ParserUtils {
    
    // DELIMITERS
    public static final String NAME_DELIMITER = "/n";
    public static final String PERSON_LIST_DELIMITER = "/pl";
    public static final String INVOLVED_DELIMITER = "/i";
    public static final String PAYER_DELIMITER = "/p";
    public static final String SESSION_ID_DELIMITER = "/sid";
    public static final String ACTIVITY_ID_DELIMITER = "/aid";
    public static final String GROUP_ID_DELIMITER = "/gid";
    public static final String DATE_DELIMITER = "/d";
    public static final String TOTAL_COST_DELIMITER = "/co";
    public static final String COST_LIST_DELIMITER = "/cl";
    public static final String GST_DELIMITER = "/gst";
    public static final String SERVICE_CHARGE_DELIMITER = "/sc";

    // MISC CONSTANTS
    private static final String LOCALDATE_TODAY_INDICATOR = "today";
    static final String DELIMITER_INDICATOR = "/";
    static final double MINIMUM_SURCHARGE_PERCENT = 0;
    static final double MAXIMUM_SURCHARGE_PERCENT = 100;
    private static final String NEXT_DELIMITER_INDICATOR = " /";
    private static final int ZERO_INDEXING_OFFSET = 1;
    private static final int PERCENTAGE_ALLOWED_INTEGER_PLACES = 3;
    private static final int TWO_DECIMAL_PLACES = 2;
    static final String REGEX_WHITESPACES_DELIMITER = "\\s+";
    private static final String REGEX_NUMERIC_VALUE = "[0-9.-]+";
    private static final String REGEX_PRINTABLE_ASCII_ONLY = "\\A[ -~]*\\z";
    static final int INVALID_INDEX_INDICATOR = -1;
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // SUPPORTING METHODS
    /**
     * Returns a String object that represents an argument in the command that follows a specified delimiter.
     * 
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user. 
     * @param delimiter   A String object that represents a demarcation of a specific argument in the command.
     * @return A String object that represents an argument demarcated by the specified delimiter in the command.
     * @throws InvalidFormatException If the specified delimiter is not found in the arguments of the command, or
     *                                if a specified delimiter is found but no argument follows the delimiter.
     */
    static String getArgumentFromDelimiter(String commandArgs, String delimiter) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        assert delimiter != null : Message.ASSERT_PARSER_DELIMITER_NULL;
        
        int delimiterIndex = commandArgs.toLowerCase().indexOf(delimiter);
        if (delimiterIndex == INVALID_INDEX_INDICATOR) {
            throw new InvalidFormatException(ParserErrors.getMissingDelimiterErrorMessage(delimiter));
        }
        int argumentIndex = delimiterIndex + delimiter.length();
        int endingIndex = commandArgs.indexOf(NEXT_DELIMITER_INDICATOR, argumentIndex);
        String output;
        if (endingIndex == INVALID_INDEX_INDICATOR) {
            output = commandArgs.substring(argumentIndex).trim();
        } else {
            output = commandArgs.substring(argumentIndex, endingIndex).trim();
        }

        if (output.isEmpty()) {
            throw new InvalidFormatException(ParserErrors.getMissingArgumentErrorMessage(delimiter));
        }
        return output;
    }

    /**
     * Extracts an integer from an input string.
     * 
     * @param input     A String object that contains numeric characters only and represents an integer.
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return An integer represented by the input String object.
     * @throws InvalidFormatException If the provided input String object contains non-numeric characters and cannot
     *                                be parsed as an integer.
     */
    static int parseIntFromString(String input, String delimiter) throws InvalidFormatException {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        assert delimiter != null : Message.ASSERT_PARSER_DELIMITER_NULL;
        
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            throw new InvalidFormatException(ParserErrors.getNonIntegerErrorMessage(delimiter));
        }
    }

    /**
     * Extracts a unique identifier from an input string.
     * 
     * @param input     A String object that contains numeric characters only and represents a unique identifier number.
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return An integer representing a unique identifier number.
     * @throws InvalidFormatException If the provided input String object contains non-numeric characters and cannot be
     *                                parsed as an integer, or
     *                                if the integer parsed from the input String object is not a positive integer.
     */
    static int parseIdFromString(String input, String delimiter) throws InvalidFormatException {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        assert delimiter != null : Message.ASSERT_PARSER_DELIMITER_NULL;
        
        int idVal = parseIntFromString(input, delimiter);
        if (idVal <= 0) {
            throw new InvalidFormatException(Message.ERROR_PARSER_ID_VALUE_NOT_POSITIVE);
        }
        return idVal;
    }

    /**
     * Extracts a real number from an input string.
     * 
     * @param input A String object that represents a numeric value.
     * @return A double represented by the input String object.
     * @throws NumberFormatException If the provided input String object contains characters that are either
     *                               non-numeric, not a decimal point or not a negative sign, or
     *                               if the provided input String object cannot be parsed as a double.
     */
    private static double parseDoubleFromString(String input) throws NumberFormatException {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        
        if (!input.matches(REGEX_NUMERIC_VALUE)) {
            throw new NumberFormatException();
        }
        return Double.parseDouble(input);
    }

    /**
     * Checks if the given String object representing a real number has at most two decimal places.
     * 
     * @param input A String object representing a real number.
     * @return true if the String object can be parsed as a double and 
     *         represents a real number has at most two decimal places,
     *         false otherwise.
     */
    private static boolean hasAtMostTwoDecimalPlaces(String input) {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        
        try {
            double value = parseDoubleFromString(input);
        } catch (NumberFormatException exception) {
            return false;
        }
        
        int indexOfDecimal = input.indexOf('.');
        if (indexOfDecimal == INVALID_INDEX_INDICATOR) {
            return true;
        }
        int decimalPlaces = input.length() - indexOfDecimal - ZERO_INDEXING_OFFSET;
        return decimalPlaces <= TWO_DECIMAL_PLACES;
    }

    /**
     * Checks if the given String object representing a real number has less than
     * a specified number of digits before decimal point.
     *
     * @param input  A String object representing a real number.
     * @param places An integer representing the maximum number of digits that the input value
     *               should have before the decimal point.
     * @return true if the String object can be parsed as a double and
     *         represents a real number that has at most the specified number of digits before decimal point,
     *         false otherwise.
     */
    private static boolean hasAtMostGivenIntegerPlaces(String input, int places) {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        assert places >= 0 : Message.ASSERT_PARSER_PLACES_NEGATIVE;
        
        try {
            double value = parseDoubleFromString(input);
        } catch (NumberFormatException exception) {
            return false;
        }

        int numberChars = input.length();
        int indexOfDecimal = input.indexOf('.');
        if (indexOfDecimal == INVALID_INDEX_INDICATOR) {
            return numberChars <= places;
        }
        return indexOfDecimal <= places;
    }

    /**
     * Checks if the given String object representing a monetary value has at most twelve digits before decimal point.
     *
     * @param input A String object representing a monetary value.
     * @return true if the String object can be parsed as a double and
     *         represents a monetary value that has at most twelve digits before decimal point,
     *         false otherwise.
     */
    private static boolean hasAtMostTwelveIntegerPlaces(String input) {
        return hasAtMostGivenIntegerPlaces(input, 12);
    }

    /**
     * Extracts a cost value from an input string.
     *
     * @param input     A String object that contains numeric characters or a single decimal point character,
     *                  representing a cost value.
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return A double representing a cost value.
     * @throws InvalidFormatException If the provided input String object contains characters other than numeric
     *                                characters or a single decimal point character,
     *                                and cannot be parsed as a double,
     *                                if the double parsed from the input String object is not a positive value,
     *                                if the parsed double has more than 2 decimal points, or
     *                                if the parsed double has more than 12 digits before the decimal point.
     */
    static double parseCostFromString(String input, String delimiter) throws InvalidFormatException {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        assert delimiter != null : Message.ASSERT_PARSER_DELIMITER_NULL;
        
        double cost;
        try {
            cost = parseDoubleFromString(input);
        } catch (NumberFormatException exception) {
            throw new InvalidFormatException(ParserErrors.getNonMonetaryErrorMessage(delimiter));
        }
        
        if (cost <= 0) {
            throw new InvalidFormatException(Message.ERROR_PARSER_COST_NOT_POSITIVE);
        }
        if (!hasAtMostTwoDecimalPlaces(input)) {
            throw new InvalidFormatException(Message.ERROR_PARSER_COST_NOT_TWO_DP);
        }
        if (!hasAtMostTwelveIntegerPlaces(input)) {
            throw new InvalidFormatException(Message.ERROR_PARSER_COST_MORE_THAN_TWELVE_DIGITS_BEFORE_DP);
        }
        return cost;
    }

    /**
     * Extracts a percentage value from an input string.
     *
     * @param input     A String object that contains numeric characters or a single decimal point character,
     *                  representing a percentage value.
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return A double representing a percentage value.
     * @throws InvalidFormatException If the provided input String object contains characters other than numeric
     *                                characters or a single decimal point character,
     *                                and cannot be parsed as a double,
     *                                if the double parsed from the input String object is a negative value,
     *                                if the parsed double has more than 2 decimal points, or
     *                                if the parsed double has more than 3 digits before the decimal point.
     */
    static double parsePercentageFromString(String input, String delimiter) throws InvalidFormatException {
        assert input != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        assert delimiter != null : Message.ASSERT_PARSER_DELIMITER_NULL;

        double percentage;
        try {
            percentage = parseDoubleFromString(input);
        } catch (NumberFormatException exception) {
            throw new InvalidFormatException(ParserErrors.getNonPercentageErrorMessage(delimiter));
        }

        if (percentage < 0) {
            throw new InvalidFormatException(Message.ERROR_PARSER_PERCENTAGE_NEGATIVE);
        }
        if (!hasAtMostTwoDecimalPlaces(input)) {
            throw new InvalidFormatException(Message.ERROR_PARSER_PERCENTAGE_NOT_TWO_DP);
        }
        if (!hasAtMostGivenIntegerPlaces(input, PERCENTAGE_ALLOWED_INTEGER_PLACES)) {
            throw new InvalidFormatException(Message.ERROR_PARSER_PERCENTAGE_MORE_THAN_THREE_DIGITS_BEFORE_DP);
        }
        return percentage;
    }

    /**
     * Checks the provided String object which represents the command arguments for the existence of a specified
     * delimiter.
     * 
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @param delimiter   A String object that represents a demarcation of a specific argument in the command.
     * @return true if the String object representing the command arguments contains the specified delimiter,
     *         false otherwise.
     */
    static boolean hasDelimiter(String commandArgs, String delimiter) {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        assert delimiter != null : Message.ASSERT_PARSER_DELIMITER_NULL;
        
        int delimiterIndex = commandArgs.indexOf(delimiter);
        return delimiterIndex != INVALID_INDEX_INDICATOR;
    }

    /**
     * Checks if the provided String object, which represents a single token in the command arguments, is a delimiter
     * that is used in any of the commands available in SplitLah.
     * 
     * @param token A String object representing a sequence of characters that are isolated by whitespaces in the
     *              command arguments.
     * @return true if the provided String object is a delimiter used in any of the commands available in SplitLah,
     *         false otherwise.
     */
    private static boolean isValidDelimiter(String token) {
        assert token != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        
        switch (token.toLowerCase()) {
        case NAME_DELIMITER:
            // Fallthrough
        case PERSON_LIST_DELIMITER:
            // Fallthrough
        case INVOLVED_DELIMITER:
            // Fallthrough
        case PAYER_DELIMITER:
            // Fallthrough
        case SESSION_ID_DELIMITER:
            // Fallthrough
        case ACTIVITY_ID_DELIMITER:
            // Fallthrough
        case GROUP_ID_DELIMITER:
            // Fallthrough
        case DATE_DELIMITER:
            // Fallthrough
        case TOTAL_COST_DELIMITER:
            // Fallthrough
        case COST_LIST_DELIMITER:
            // Fallthrough
        case GST_DELIMITER:
            // Fallthrough
        case SERVICE_CHARGE_DELIMITER:
            return true;
        default:
            return false;
        }
    }

    /**
     * Checks whether the provided String object which represents the command arguments contains any invalid
     * delimiters.
     * 
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return true if there are tokens in the command arguments that contains a forward slash character ('/') but is
     *         not a valid delimiter used in any commands in SplitLah,
     *         false otherwise.
     */
    private static boolean containsInvalidDelimiters(String commandArgs) {
        assert commandArgs != null : Message.ASSERT_PARSER_TOKEN_INPUT_NULL;
        
        String[] argumentTokens = commandArgs.split(REGEX_WHITESPACES_DELIMITER);
        for (String token : argumentTokens) {
            if (token.contains(DELIMITER_INDICATOR) && !isValidDelimiter(token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the provided String object which represents the command arguments contains any duplicate
     * delimiters.
     * 
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return true if there are tokens in the command arguments containing a forward slash character ('/') that
     *         appears twice or more times in the arguments,
     *         false otherwise.
     */
    private static boolean containsDuplicateDelimiters(String commandArgs) {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;

        Set<String> delimiterSet = new HashSet<>();
        String[] argumentTokens = commandArgs.split(REGEX_WHITESPACES_DELIMITER);
        for (String token : argumentTokens) {
            if (token.contains(DELIMITER_INDICATOR) && !delimiterSet.add(token)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a String array object contains a String object 
     * with the same contents, ignoring case, as the specified String object.
     * 
     * @param stringToCheck A String object that is specified to be checked against the String array object.
     * @param stringArray   A String array object to be checked against.
     * @return true if stringArray contains a String object with the same contents as stringToCheck,
     *         false otherwise.
     */
    private static boolean hasStringInStringArray(String stringToCheck, String[] stringArray) {
        assert stringToCheck != null && stringArray != null : Message.ASSERT_PARSER_PARAMETERS_NULL;
        
        for (String string : stringArray) {
            if (string.equalsIgnoreCase(stringToCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether any delimiter inside the provided String object which represents the command arguments does not
     * belong to the command type specified by the String object which represents the command type.
     * 
     * @param commandType   A String object representing the command type of the command input from the user.
     * @param remainingArgs A String object containing the arguments portion of the entire command input from the user.
     * @return true if any delimiter inside remainingArgs do not belong to command type as represented by commandType,
     *         false otherwise.
     */
    private static boolean containsDelimitersNotFromCommand(String commandType, String remainingArgs) {
        assert commandType != null : Message.ASSERT_PARSER_COMMAND_TYPE_NULL;
        assert remainingArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        
        String[] delimiterList;
        switch (commandType.toLowerCase()) {
        case ActivityCreateCommandParser.COMMAND_TEXT:
            delimiterList = ActivityCreateCommandParser.COMMAND_DELIMITERS;
            break;
        case ActivityDeleteCommandParser.COMMAND_TEXT:
            delimiterList = ActivityDeleteCommandParser.COMMAND_DELIMITERS;
            break;
        case ActivityEditCommandParser.COMMAND_TEXT:
            delimiterList = ActivityEditCommandParser.COMMAND_DELIMITERS;
            break;
        case ActivityListCommandParser.COMMAND_TEXT:
            delimiterList = ActivityListCommandParser.COMMAND_DELIMITERS;
            break;
        case ActivityViewCommandParser.COMMAND_TEXT:
            delimiterList = ActivityViewCommandParser.COMMAND_DELIMITERS;
            break;
        case SessionCreateCommandParser.COMMAND_TEXT:
            delimiterList = SessionCreateCommandParser.COMMAND_DELIMITERS;
            break;
        case SessionDeleteCommandParser.COMMAND_TEXT:
            delimiterList = SessionDeleteCommandParser.COMMAND_DELIMITERS;
            break;
        case SessionEditCommandParser.COMMAND_TEXT:
            delimiterList = SessionEditCommandParser.COMMAND_DELIMITERS;
            break;
        case SessionViewCommandParser.COMMAND_TEXT:
            delimiterList = SessionViewCommandParser.COMMAND_DELIMITERS;
            break;
        case SessionSummaryCommandParser.COMMAND_TEXT:
            delimiterList = SessionSummaryCommandParser.COMMAND_DELIMITERS;
            break;
        case GroupCreateCommandParser.COMMAND_TEXT:
            delimiterList = GroupCreateCommandParser.COMMAND_DELIMITERS;
            break;
        case GroupDeleteCommandParser.COMMAND_TEXT:
            delimiterList = GroupDeleteCommandParser.COMMAND_DELIMITERS;
            break;
        case GroupEditCommandParser.COMMAND_TEXT:
            delimiterList = GroupEditCommandParser.COMMAND_DELIMITERS;
            break;
        case GroupViewCommandParser.COMMAND_TEXT:
            delimiterList = GroupViewCommandParser.COMMAND_DELIMITERS;
            break;
        default:
            return !remainingArgs.isEmpty();
        }

        String[] argumentTokens = remainingArgs.split(REGEX_WHITESPACES_DELIMITER);
        for (String token : argumentTokens) {
            if (token.contains(DELIMITER_INDICATOR) && !hasStringInStringArray(token.toLowerCase(), delimiterList)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if the provided String object representing the command type of the command input from the user,
     * is a valid command type in SplitLah.
     *
     * @param commandType   A String object representing the command type of the command input from the user.
     * @return true if the provided String object is a command type available in SplitLah,
     *         false otherwise.
     */
    private static boolean isValidCommandType(String commandType) {
        assert commandType != null : Message.ASSERT_PARSER_COMMAND_TYPE_NULL;
        
        switch (commandType.toLowerCase()) {
        case SessionCreateCommandParser.COMMAND_TEXT:
            // Fallthrough
        case SessionDeleteCommandParser.COMMAND_TEXT:
            // Fallthrough
        case SessionEditCommandParser.COMMAND_TEXT:
            // Fallthrough
        case SessionSummaryCommandParser.COMMAND_TEXT:
            // Fallthrough
        case SessionListCommandParser.COMMAND_TEXT:
            // Fallthrough
        case SessionViewCommandParser.COMMAND_TEXT:
            // Fallthrough
        case ActivityCreateCommandParser.COMMAND_TEXT:
            // Fallthrough
        case ActivityDeleteCommandParser.COMMAND_TEXT:
            // Fallthrough
        case ActivityEditCommandParser.COMMAND_TEXT:
            // Fallthrough
        case ActivityListCommandParser.COMMAND_TEXT:
            // Fallthrough
        case ActivityViewCommandParser.COMMAND_TEXT:
            // Fallthrough
        case GroupCreateCommandParser.COMMAND_TEXT:
            // Fallthrough
        case GroupDeleteCommandParser.COMMAND_TEXT:
            // Fallthrough
        case GroupEditCommandParser.COMMAND_TEXT:
            // Fallthrough
        case GroupListCommandParser.COMMAND_TEXT:
            // Fallthrough
        case GroupViewCommandParser.COMMAND_TEXT:
            // Fallthrough
        case HelpCommandParser.COMMAND_TEXT:
            // Fallthrough
        case ExitCommandParser.COMMAND_TEXT:
            // Fallthrough
            return true;
        default:
            return false;
        }
    }

    /**
     * Returns a String object that represents a corresponding error message for the first error check to be failed,
     * when checking for validity of arguments for a specified command.
     * 
     * @param commandType   A String object representing the command type of the command input from the user.
     * @param remainingArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A String object with the first error check to be failed, if any, or
     *         an empty String object if remainingArgs is empty or if none of the error checks fail.
     */
    static String checkIfArgumentsValidForCommand(String commandType, String remainingArgs) {
        assert commandType != null : Message.ASSERT_PARSER_COMMAND_TYPE_NULL;
        assert remainingArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        
        if (remainingArgs.isEmpty()) {
            return "";
        } else if (!remainingArgs.startsWith(DELIMITER_INDICATOR)) {
            return Message.ERROR_PARSER_ADDITIONAL_INVALID_TOKEN;
        } else if (containsInvalidDelimiters(remainingArgs)
                || containsDelimitersNotFromCommand(commandType, remainingArgs)) {
            return Message.ERROR_PARSER_INVALID_DELIMITERS;
        } else if (containsDuplicateDelimiters(remainingArgs)) {
            return Message.ERROR_PARSER_DUPLICATE_DELIMITERS;
        } else {
            return "";
        }
    }

    /**
     * Returns a String object that represents a corresponding error message for the first error check to be failed,
     * when checking for validity of the command type followed by the validity of the command arguments.
     * 
     * @param commandType   A String object representing the command type of the command input from the user.
     * @param remainingArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A String object with the first error check to be failed, if any, or
     *         an empty String object if remainingArgs is empty or if none of the error checks fail.
     */
    public static String checkIfCommandIsValid(String commandType, String remainingArgs) {
        assert commandType != null : Message.ASSERT_PARSER_COMMAND_TYPE_NULL;
        assert remainingArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;

        if (commandType.isEmpty()) {
            return Message.ERROR_PARSER_EMPTY_COMMAND;
        }
        
        if (!isValidCommandType(commandType)) {
            return Message.ERROR_PARSER_INVALID_COMMAND;
        }
        
        if (!commandType.matches(REGEX_PRINTABLE_ASCII_ONLY) || !remainingArgs.matches(REGEX_PRINTABLE_ASCII_ONLY)) {
            return Message.ERROR_PARSER_NON_ASCII_ARGUMENT;
        }
        
        return checkIfArgumentsValidForCommand(commandType, remainingArgs);
    }

    // MAIN PUBLIC PARSING METHODS
    /**
     * Extracts a name from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A String object that represents a name.
     * @throws InvalidFormatException If the Name delimiter is not found in the command arguments, or
     *                                if no arguments representing a name were provided after the Name delimiter.
     */
    public static String parseName(String commandArgs) throws InvalidFormatException {
        return getArgumentFromDelimiter(commandArgs, NAME_DELIMITER);
    }

    /**
     * Extracts a list of names of participants in a session or a group from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A String array object that represents a list of names.
     * @throws InvalidFormatException If the Person list delimiter is not found in the command arguments, or
     *                                if no arguments representing a list of names were provided after the
     *                                Person list delimiter.
     */
    public static String[] parsePersonList(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, PERSON_LIST_DELIMITER);
        return argument.split(REGEX_WHITESPACES_DELIMITER);
    }

    /**
     * Extracts a list of names of involved persons in an activity from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A String array object that represents a list of names of involved persons.
     * @throws InvalidFormatException If the Involved delimiter is not found in the command arguments, or
     *                                if no arguments representing a list of names were provided after the
     *                                Involved delimiter.
     */
    public static String[] parseInvolved(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, INVOLVED_DELIMITER);
        return argument.split(REGEX_WHITESPACES_DELIMITER);
    }

    /**
     * Extracts a name of a payer from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A String object that represents a name of a payer.
     * @throws InvalidFormatException If the Payer delimiter is not found in the command arguments,
     *                                if no arguments representing a name were provided after the Payer delimiter, or
     *                                if the argument contains more than a single name.
     */
    public static String parsePayer(String commandArgs) throws InvalidFormatException {
        String payer = getArgumentFromDelimiter(commandArgs, PAYER_DELIMITER);
        if (payer.indexOf(' ') != INVALID_INDEX_INDICATOR) {
            throw new InvalidFormatException(Message.ERROR_PARSER_MORE_THAN_ONE_PAYER);
        }
        return payer;
    }

    /**
     * Extracts a session unique identifier from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return An integer that represents a session unique identifier.
     * @throws InvalidFormatException If the Session ID delimiter is not found in the command arguments,
     *                                if no arguments representing a session unique identifier were provided after the
     *                                Session ID delimiter,
     *                                if the parsed argument cannot be parsed as an integer, or
     *                                if the integer parsed from the argument is not a positive integer.
     */
    public static int parseSessionId(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, SESSION_ID_DELIMITER);
        return parseIdFromString(argument, SESSION_ID_DELIMITER);
    }

    /**
     * Extracts an activity unique identifier from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return An integer that represents an activity unique identifier.
     * @throws InvalidFormatException If the Activity ID delimiter is not found in the command arguments,
     *                                if no arguments representing an activity unique identifier were provided after
     *                                the Activity ID delimiter,
     *                                if the parsed argument cannot be parsed as an integer, or
     *                                if the integer parsed from the argument is not a positive integer.
     */
    public static int parseActivityId(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, ACTIVITY_ID_DELIMITER);
        return parseIdFromString(argument, ACTIVITY_ID_DELIMITER);
    }

    /**
     * Extracts a group unique identifier from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return An integer that represents a group unique identifier.
     * @throws InvalidFormatException If the Group ID delimiter is not found in the command arguments,
     *                                if no arguments representing a group unique identifier were provided after
     *                                the Group ID delimiter,
     *                                if the parsed argument cannot be parsed as an integer, or
     *                                if the integer parsed from the argument is not a positive integer.
     */
    public static int parseGroupId(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, GROUP_ID_DELIMITER);
        return parseIdFromString(argument, GROUP_ID_DELIMITER);
    }

    /**
     * Extracts a date from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A LocalDate object that represents a date specified by the argument in the format of 'DD-MM-YYYY' or
     *         the current date if the argument following the Date delimiter indicates "today".
     * @throws InvalidFormatException If the Date delimiter is not found in the command arguments,
     *                                if no arguments representing a date were provided after the
     *                                Date delimiter, or
     *                                if the argument provided does not indicate "today" nor follow the date format of
     *                                'DD-MM-YYYY'.
     */
    public static LocalDate parseLocalDate(String commandArgs) throws InvalidFormatException {
        if (!hasDelimiter(commandArgs, DATE_DELIMITER)) {
            throw new InvalidFormatException(ParserErrors.getMissingDelimiterErrorMessage(DATE_DELIMITER));
        }

        String argument = getArgumentFromDelimiter(commandArgs, DATE_DELIMITER);
        if (argument.equalsIgnoreCase(LOCALDATE_TODAY_INDICATOR)) {
            return LocalDate.now();
        }

        try {
            return LocalDate.parse(argument, DATE_FORMAT);
        } catch (DateTimeParseException exception) {
            throw new InvalidFormatException(Message.ERROR_PARSER_INVALID_DATE_FORMAT);
        }
    }

    /**
     * Extracts a total cost of an activity from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A double that represents a single total cost.
     * @throws InvalidFormatException If the Total cost delimiter is not found in the command arguments,
     *                                if no arguments representing a total cost were provided after the
     *                                Total cost delimiter,
     *                                if the arguments cannot be parsed as a double,
     *                                if the parsed cost value is not positive,
     *                                if the parsed cost value has more than 2 decimal points, or
     *                                if the parsed cost value has more than 12 digits before the decimal point.
     */
    public static double parseTotalCost(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, TOTAL_COST_DELIMITER);
        return parseCostFromString(argument, TOTAL_COST_DELIMITER);
    }

    /**
     * Extracts a list of costs for an activity from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A double array object that represents a list of cost values.
     * @throws InvalidFormatException If the Cost list delimiter is not found in the command arguments,
     *                                if no arguments representing a list of cost values were provided after the
     *                                Cost list delimiter,
     *                                if any token in the argument cannot be parsed as a double, or
     *                                if any cost value parsed is not positive,
     *                                if any parsed cost value has more than 2 decimal points, or
     *                                if any parsed cost value has more than 12 digits before the decimal point.
     */
    public static double[] parseCostList(String commandArgs) throws InvalidFormatException {
        String argument = getArgumentFromDelimiter(commandArgs, COST_LIST_DELIMITER);
        String[] costStrings = argument.split(REGEX_WHITESPACES_DELIMITER);
        double[] costs = new double[costStrings.length];
        for (int i = 0; i < costStrings.length; i++) {
            costs[i] = parseCostFromString(costStrings[i], COST_LIST_DELIMITER);
        }
        return costs;
    }

    /**
     * Extracts a GST percentage from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A double that represents a GST charge in percentage.
     * @throws InvalidFormatException If no arguments representing a GST charge were provided after the
     *                                GST delimiter,
     *                                if the argument cannot be parsed as a double,
     *                                if the parsed percentage has more than 2 decimal points,
     *                                if the parsed percentage has more than 3 digits before the decimal point, or
     *                                if the parsed percentage is not in [0, 100].
     */
    public static double parseGst(String commandArgs) throws InvalidFormatException {
        if (!hasDelimiter(commandArgs, GST_DELIMITER)) {
            return 0;
        }

        String argument = getArgumentFromDelimiter(commandArgs, GST_DELIMITER);
        double gst = parsePercentageFromString(argument, GST_DELIMITER);
        assert gst >= 0 : Message.ASSERT_PARSER_PERCENTAGE_NEGATIVE;
        if (gst > MAXIMUM_SURCHARGE_PERCENT) {
            throw new InvalidFormatException(ParserErrors.getInvalidGstErrorMessage());
        }
        return gst;
    }

    /**
     * Extracts a GST percentage from a user input. This method can differentiate between no GST being provided
     * and a value of 0 GST being explicitly provided.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A double that represents a GST charge in percentage if the GST delimiter is found,
     *         a double value of -1 otherwise.
     * @throws InvalidFormatException If no arguments representing a GST charge were provided after the
     *                                GST delimiter,
     *                                if the argument cannot be parsed as a double,
     *                                if the parsed percentage has more than 2 decimal points,
     *                                if the parsed percentage has more than 3 digits before the decimal point, or
     *                                if the parsed percentage is not in [0, 100].
     */
    public static double parseGstIncludingZero(String commandArgs) throws InvalidFormatException {
        if (!ParserUtils.hasDelimiter(commandArgs, ParserUtils.GST_DELIMITER)) {
            return -1;
        }

        String argument = ParserUtils.getArgumentFromDelimiter(commandArgs, ParserUtils.GST_DELIMITER);
        double gst = ParserUtils.parsePercentageFromString(argument, ParserUtils.GST_DELIMITER);
        assert gst >= 0 : Message.ASSERT_PARSER_PERCENTAGE_NEGATIVE;
        if (gst > MAXIMUM_SURCHARGE_PERCENT) {
            throw new InvalidFormatException(ParserErrors.getInvalidGstErrorMessage());
        }
        return gst;
    }

    /**
     * Extracts a service charge percentage from a user input.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A double that represents a service charge in percentage.
     * @throws InvalidFormatException If no arguments representing a service charge were provided after the
     *                                Service charge delimiter,
     *                                if the argument cannot be parsed as a double,
     *                                if the parsed percentage has more than 2 decimal points,
     *                                if the parsed percentage has more than 3 digits before the decimal point, or
     *                                if the parsed percentage is not in [0, 100].
     */
    public static double parseServiceCharge(String commandArgs) throws InvalidFormatException {
        if (!hasDelimiter(commandArgs, SERVICE_CHARGE_DELIMITER)) {
            return 0;
        }

        String argument = getArgumentFromDelimiter(commandArgs, SERVICE_CHARGE_DELIMITER);
        double serviceCharge = parsePercentageFromString(argument, SERVICE_CHARGE_DELIMITER);
        assert serviceCharge >= 0 : Message.ASSERT_PARSER_PERCENTAGE_NEGATIVE;
        if (serviceCharge > MAXIMUM_SURCHARGE_PERCENT) {
            throw new InvalidFormatException(ParserErrors.getInvalidServiceChargeErrorMessage());
        }
        return serviceCharge;
    }

    /**
     * Extracts a service charge percentage from a user input. This method can differentiate between no service charge
     * being provided and a value of 0 GST being explicitly provided.
     *
     * @param commandArgs A String object containing the arguments portion of the entire command input from the user.
     * @return A double that represents a service charge in percentage if the service charge delimiter is found,
     *         a double value of -1 otherwise.
     * @throws InvalidFormatException If no arguments representing a service charge were provided after the
     *                                Service charge delimiter,
     *                                if the argument cannot be parsed as a double,
     *                                if the parsed percentage has more than 2 decimal points,
     *                                if the parsed percentage has more than 3 digits before the decimal point, or
     *                                if the parsed percentage is not in [0, 100].
     */
    public static double parseServiceChargeIncludingZero(String commandArgs) throws InvalidFormatException {
        if (!ParserUtils.hasDelimiter(commandArgs, ParserUtils.SERVICE_CHARGE_DELIMITER)) {
            return -1;
        }

        String argument = ParserUtils.getArgumentFromDelimiter(commandArgs, ParserUtils.SERVICE_CHARGE_DELIMITER);
        double serviceCharge = ParserUtils.parsePercentageFromString(argument, ParserUtils.SERVICE_CHARGE_DELIMITER);
        assert serviceCharge >= 0 : Message.ASSERT_PARSER_PERCENTAGE_NEGATIVE;
        if (serviceCharge > MAXIMUM_SURCHARGE_PERCENT) {
            throw new InvalidFormatException(ParserErrors.getInvalidServiceChargeErrorMessage());
        }
        return serviceCharge;
    }
}

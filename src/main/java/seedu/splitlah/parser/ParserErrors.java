package seedu.splitlah.parser;

import seedu.splitlah.ui.Message;

/**
 * A utility class that provides supporting methods for printing error messages used in the Parser component.
 * 
 * @author Warren
 */
public class ParserErrors {

    /**
     * Returns an error message for a specified delimiter that is missing.
     * 
     * @param delimiter A String object that represents a demarcation of a specific argument in the command. 
     * @return A String object representing an error message for missing a specified delimiter in the input command.
     */
    public static String getMissingDelimiterErrorMessage(String delimiter) {
        return Message.ERROR_PARSER_DELIMITER_NOT_FOUND + delimiter;
    }

    /**
     * Returns an error message for a missing argument that should follow after a specified
     * delimiter in the command input by the user.
     *
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return A String object representing an error message for missing an argument in the input command.
     */
    public static String getMissingArgumentErrorMessage(String delimiter) {
        return Message.ERROR_PARSER_MISSING_ARGUMENT + delimiter;
    }

    /**
     * Returns an error message when the parser is not able to read an input as an integer.
     *
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return A String object representing an error message for a non-integer input.
     */
    public static String getNonIntegerErrorMessage(String delimiter) {
        return Message.ERROR_PARSER_NON_INTEGER_ARGUMENT + delimiter;
    }

    /**
     * Returns an error message when the parser is not able to read an input
     * as a double to be used as a monetary value.
     *
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return A String object representing an error message for a non-double input.
     */
    public static String getNonMonetaryErrorMessage(String delimiter) {
        return Message.ERROR_PARSER_NON_MONETARY_VALUE_ARGUMENT + delimiter;
    }

    /**
     * Returns an error message when the parser is not able to read an input
     * as a double to be used as a percentage value.
     *
     * @param delimiter A String object that represents a demarcation of a specific argument in the command.
     * @return A String object representing an error message for a non-double input.
     */
    public static String getNonPercentageErrorMessage(String delimiter) {
        return Message.ERROR_PARSER_NON_PERCENTAGE_ARGUMENT + delimiter;
    }

    /**
     * Returns an error message when the parser is not able to parse an input
     * as a valid Goods and Services Tax (GST) in percentage.
     *
     * @return A String object representing an error message for an invalid GST input.
     */
    public static String getInvalidGstErrorMessage() {
        return Message.ERROR_PARSER_INVALID_GST_SURCHARGE + ParserUtils.GST_DELIMITER;
    }

    /**
     * Returns an error message when the parser is not able to parse an input
     * as a valid service charge in percentage.
     *
     * @return A String object representing an error message for an invalid service charge input.
     */
    public static String getInvalidServiceChargeErrorMessage() {
        return Message.ERROR_PARSER_INVALID_SERVICE_CHARGE + ParserUtils.SERVICE_CHARGE_DELIMITER;
    }
}

package seedu.duke.parser;

/**
 * Represents an interpreter that interprets different parts of the user input
 * into data that can be understood by the program.
 * @author Warren
 */
public class Parser {
    // DELIMITERS
    private static final String NAME_DELIMITER = "/n";
    private static final String PERSON_LIST_DELIMITER = "/pl";
    private static final String INVOLVED_DELIMITER = "/i";
    private static final String PAYER_DELIMITER = "/p";
    private static final String SESSION_ID_DELIMITER = "/sid";
    private static final String ACTIVITY_ID_DELIMITER = "/aid";
    private static final String GROUP_ID_DELIMITER = "/gid";
    private static final String DATE_DELIMITER = "/d";
    private static final String TOTAL_COST_DELIMITER = "/c";
    private static final String COST_LIST_DELIMITER = "/cl";
    private static final String GST_DELIMITER = "/gst";
    private static final String SERVICE_CHARGE_DELIMITER = "/sc";

    // MISC CONSTANTS
    private static final String NEXT_DELIMITER_INDICATOR = " /";
    private static final int INVALID_INDEX_INDICATOR = -1;
}

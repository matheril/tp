package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.ActivityCreateCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that is able to parse user arguments into an ActivityCreateCommand object.
 *
 * @author Ivan
 */
public class ActivityCreateCommandParser implements CommandParser<ActivityCreateCommand> {

    public static final String COMMAND_TEXT = "activity /create";

    public static final String COMMAND_FORMAT = "Syntax:\n\t";

    public static final String COMMAND_FORMAT_FIRST =
            "activity /create /sid [SESSION_ID] /n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] "
                    + "/co [TOTAL_COST] [</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]";

    public static final String COMMAND_FORMAT_SECOND =
            "activity /create /sid [SESSION_ID] /n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] "
                    + "/cl [COST1 COST2...] [</gst [GST_PERCENTAGE]>] [</sc [SERVICE_CHARGE]>]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER,
        ParserUtils.NAME_DELIMITER,
        ParserUtils.PAYER_DELIMITER,
        ParserUtils.INVOLVED_DELIMITER,
        ParserUtils.TOTAL_COST_DELIMITER,
        ParserUtils.COST_LIST_DELIMITER,
        ParserUtils.GST_DELIMITER,
        ParserUtils.SERVICE_CHARGE_DELIMITER
    };

    private String[] involvedList;
    private double totalCost = 0;
    private double[] costList = null;

    /**
     * Returns a ActivityCreateCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return An ActivityCreateCommand object if all necessary parameters required are found in the input arguments.
     * @throws InvalidFormatException If at least one of the necessary parameters cannot be found
     *                                in the input arguments.
     */
    @Override
    public ActivityCreateCommand getCommand(String commandArgs) throws InvalidFormatException {
        int sessionId;
        String activityName;
        String payer;
        double gst;
        double serviceCharge;

        try {
            sessionId = ParserUtils.parseSessionId(commandArgs);
            activityName = ParserUtils.parseName(commandArgs);
            payer = ParserUtils.parsePayer(commandArgs);
            involvedList = ParserUtils.parseInvolved(commandArgs);
        } catch (InvalidFormatException e) {
            String invalidMessage = e.getMessage() + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST + "\n\t"
                    + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }

        boolean hasCost = hasCost(commandArgs);
        boolean hasCostList = hasCostList(commandArgs);
        boolean hasDifferentLength = false;
        checkIfMissingBothCostAndCostList(hasCost, hasCostList);
        checkIfHasBothCostAndCostList(hasCost, hasCostList);
        checkIfHasDifferentLength(hasCost, hasDifferentLength);

        try {
            gst = ParserUtils.parseGst(commandArgs);
            serviceCharge = ParserUtils.parseServiceCharge(commandArgs);
        } catch (InvalidFormatException e) {
            String invalidMessage = e.getMessage() + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST
                    + "\n\t" + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }
      
        return new ActivityCreateCommand(sessionId, activityName, totalCost, payer, involvedList, costList, gst,
                serviceCharge);
    }

    /**
     * Checks if the cost list and the involved list provided by the user have different lengths.
     *
     * @param hasCost A boolean representing whether the total cost was not provided by the user.
     * @throws InvalidFormatException If the cost list and the involved list have different lengths.
     */
    private void checkIfHasDifferentLength(boolean hasCost, boolean hasDifferentLength) throws InvalidFormatException {
        if (!hasCost) {
            hasDifferentLength = involvedList.length != costList.length;
        }
        if (hasDifferentLength) {
            String invalidMessage = Message.ERROR_ACTIVITYCREATE_INVOLVED_AND_COST_DIFFERENT_LENGTH
                    + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST + "\n\t" + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }
    }

    /**
     * Checks if both the total cost and the cost list are provided by the user.
     *
     * @param hasCost     A boolean representing whether the total cost was not provided by the user.
     * @param hasCostList A boolean representing whether the cost list was not provided by the user.
     * @throws InvalidFormatException If both the total cost and cost list are provided by the user.
     */
    private void checkIfHasBothCostAndCostList(boolean hasCost, boolean hasCostList) throws InvalidFormatException {
        boolean hasBothCostAndCostList = hasCost && hasCostList;
        if (hasBothCostAndCostList) {
            String invalidMessage = Message.ERROR_ACTIVITYCREATE_HAS_BOTH_COST_AND_COST_LIST
                    + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST + "\n\t" + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }
    }

    /**
     * Checks if both the total cost and the cost list are not provided by the user.
     *
     * @param hasCost     A boolean representing whether the total cost was not provided by the user.
     * @param hasCostList A boolean representing whether the cost list was not provided by the user.
     * @throws InvalidFormatException If both the total cost and cost list are not provided by the user.
     */
    private void checkIfMissingBothCostAndCostList(boolean hasCost, boolean hasCostList) throws InvalidFormatException {
        boolean hasMissingCostAndMissingCostList = !hasCost && !hasCostList;
        if (hasMissingCostAndMissingCostList) {
            String invalidMessage = Message.ERROR_ACTIVITYCREATE_MISSING_COST_AND_COST_LIST
                    + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST + "\n\t" + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }
    }

    /**
     * Checks if the cost list of the activity is provided by the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return true if the cost list is successfully parsed from user input
     *         false if the user did not indicate the cost list using the cost list delimiter.
     * @throws InvalidFormatException If the cost list argument cannot be found in the input arguments.
     */
    private boolean hasCostList(String commandArgs) throws InvalidFormatException {
        try {
            costList = ParserUtils.parseCostList(commandArgs);
            return true;
        } catch (InvalidFormatException e) {
            if (!e.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                    + ParserUtils.COST_LIST_DELIMITER)) {
                String invalidMessage = e.getMessage() + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST
                        + "\n\t" + COMMAND_FORMAT_SECOND;
                throw new InvalidFormatException(invalidMessage);
            }
            return false;
        }
    }

    /**
     * Checks if the total cost of the activity is provided by the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return true if the total cost is successfully parsed from user input,
     *         false if the user did not indicate the total cost using the total cost delimiter.
     * @throws InvalidFormatException If the total cost argument cannot be found in the input arguments.
     */
    private boolean hasCost(String commandArgs) throws InvalidFormatException {
        try {
            totalCost = ParserUtils.parseTotalCost(commandArgs);
            return true;
        } catch (InvalidFormatException e) {
            if (!e.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                    + ParserUtils.TOTAL_COST_DELIMITER)) {
                String invalidMessage = e.getMessage() + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST
                        + "\n\t" + COMMAND_FORMAT_SECOND;
                throw new InvalidFormatException(invalidMessage);
            }
            return false;
        }
    }
}

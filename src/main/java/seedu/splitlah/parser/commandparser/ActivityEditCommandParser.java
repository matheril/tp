package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.ActivityEditCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserErrors;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that is able to parse user arguments into a ActivityEditCommand object.
 *
 * @author Saurav
 */
public class ActivityEditCommandParser implements CommandParser<ActivityEditCommand> {

    public static final String COMMAND_TEXT = "activity /edit";

    private static final String COMMAND_FORMAT = "Syntax:\n\t";

    public static final String COMMAND_FORMAT_FIRST =
            "activity /edit /sid [SESSION_ID] /aid [ACTIVITY_ID] {/n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] "
                    + "/co [TOTAL_COST] /gst [GST_PERCENTAGE] /sc [SERVICE_CHARGE]}";

    public static final String COMMAND_FORMAT_SECOND =
            "activity /edit /sid [SESSION_ID] /aid [ACTIVITY_ID] {/n [ACTIVITY_NAME] /p [PAYER] /i [NAME1 NAME2...] "
                    + "/cl [COST1 COST2...] /gst [GST_PERCENTAGE] /sc [SERVICE_CHARGE>]}";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER,
        ParserUtils.ACTIVITY_ID_DELIMITER,
        ParserUtils.NAME_DELIMITER,
        ParserUtils.PAYER_DELIMITER,
        ParserUtils.INVOLVED_DELIMITER,
        ParserUtils.TOTAL_COST_DELIMITER,
        ParserUtils.COST_LIST_DELIMITER,
        ParserUtils.GST_DELIMITER,
        ParserUtils.SERVICE_CHARGE_DELIMITER
    };

    private int sessionId;
    private int activityId;
    private String activityName = null;
    private String payer = null;
    private String[] involvedList = null;
    private double totalCost = -1;
    private double[] costList = null;
    private double gst = -1;
    private double serviceCharge = -1;

    /**
     * Validates the list of supplied delimiters, to check if any invalid permutations of delimiters are supplied
     * or if insufficient delimiters are supplied.
     *
     * @throws InvalidFormatException If both a costlist and total cost are supplied, which is invalid.
     *                                If no activity details are supplied.
     */
    private void checkIfRequiredDelimitersExist() throws InvalidFormatException {
        // Check if no delimiters at all are supplied.
        if (activityName == null && payer == null && involvedList == null && totalCost == -1 && costList == null
                && gst == -1 && serviceCharge == -1) {
            throw new InvalidFormatException(Message.ERROR_ACTIVITYEDIT_NO_CHANGE_TO_ACTIVITY);
        }
        // Check if a costlist or total cost is required.
        if (involvedList == null) {
            return;
        }
        if (costList == null && totalCost == -1) {
            throw new InvalidFormatException(Message.ERROR_ACTIVITYEDIT_COST_NOT_PROVIDED);
        }
    }

    private void getServiceChargeIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            serviceCharge = ParserUtils.parseServiceChargeIncludingZero(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/sc"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void getGstIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            gst = ParserUtils.parseGstIncludingZero(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/gst"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void checkIfBothCostListAndTotalCostSupplied() throws InvalidFormatException {
        if (costList != null && totalCost != -1) {
            String invalidMessage = Message.ERROR_ACTIVITYCREATE_HAS_BOTH_COST_AND_COST_LIST
                    + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST + "\n\t" + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }
    }

    private void getCostListIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            costList = ParserUtils.parseCostList(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/cl"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void getTotalCostIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            totalCost = ParserUtils.parseTotalCost(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/co"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void getInvolvedListIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            involvedList = ParserUtils.parseInvolved(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/i"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void getPayerNameIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            payer = ParserUtils.parsePayer(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/p"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void getActivityNameIfSupplied(String commandArgs) throws InvalidFormatException {
        try {
            activityName = ParserUtils.parseName(commandArgs);
        } catch (InvalidFormatException exception) {
            if (!exception.getMessage().equals(ParserErrors.getMissingDelimiterErrorMessage("/n"))) {
                throw new InvalidFormatException(exception.getMessage());
            }
        }
    }

    private void getSessionIdAndActivityId(String commandArgs) throws InvalidFormatException {
        try {
            sessionId = ParserUtils.parseSessionId(commandArgs);
            activityId = ParserUtils.parseActivityId(commandArgs);
        } catch (InvalidFormatException exception) {
            String invalidMessage = exception.getMessage() + "\n" + COMMAND_FORMAT + COMMAND_FORMAT_FIRST
                    + "\n\t" + COMMAND_FORMAT_SECOND;
            throw new InvalidFormatException(invalidMessage);
        }
    }

    /**
     * Returns an ActivityEditCommand object from the supplied command arguments.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return An ActivityEditCommand object if all necessary parameters required are found in the input arguments.
     * @throws InvalidFormatException If at least one of the necessary parameters cannot be found
     *                                in the input arguments.
     */
    @Override
    public ActivityEditCommand getCommand(String commandArgs) throws InvalidFormatException {

        assert commandArgs != null : Message.ASSERT_ACTIVITYEDIT_COMMAND_ARGS_NULL;
        getSessionIdAndActivityId(commandArgs);
        getActivityNameIfSupplied(commandArgs);
        getPayerNameIfSupplied(commandArgs);
        getInvolvedListIfSupplied(commandArgs);
        getTotalCostIfSupplied(commandArgs);
        getCostListIfSupplied(commandArgs);
        checkIfBothCostListAndTotalCostSupplied();
        getGstIfSupplied(commandArgs);
        getServiceChargeIfSupplied(commandArgs);
        checkIfRequiredDelimitersExist();
        return new ActivityEditCommand(sessionId, activityId, activityName, payer, involvedList, totalCost,
                costList, gst, serviceCharge);
    }
}

package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.ActivityDeleteCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;

/**
 * Represents a command parser that is able to parse user arguments into an ActivityDeleteCommand object.
 *
 * @author Ivan
 */
public class ActivityDeleteCommandParser implements CommandParser<ActivityDeleteCommand> {

    public static final String COMMAND_TEXT = "activity /delete";

    public static final String COMMAND_FORMAT = "Syntax: activity /delete /sid [SESSION_ID] /aid [ACTIVITY_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER,
        ParserUtils.ACTIVITY_ID_DELIMITER
    };

    /**
     * Returns a ActivityDeleteCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return An ActivityDeleteCommand object if all necessary parameters required are found in the input arguments.
     * @throws InvalidFormatException If at least one of the necessary parameters cannot be found
     *                                in the input arguments.
     */
    @Override
    public ActivityDeleteCommand getCommand(String commandArgs) throws InvalidFormatException {
        try {
            int sessionId = ParserUtils.parseSessionId(commandArgs);
            int activityId = ParserUtils.parseActivityId(commandArgs);
            return new ActivityDeleteCommand(sessionId, activityId);
        } catch (InvalidFormatException e) {
            throw new InvalidFormatException(e.getMessage() + "\n" + COMMAND_FORMAT);
        }
    }
}

package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.ActivityViewCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that is able to parse user arguments into an ActivityViewCommand object.
 *
 * @author Tianle
 */
public class ActivityViewCommandParser implements CommandParser<ActivityViewCommand> {

    public static final String COMMAND_TEXT = "activity /view";

    public static final String COMMAND_FORMAT = "Syntax: activity /view /sid [SESSION_ID] /aid [ACTIVITY_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER,
        ParserUtils.ACTIVITY_ID_DELIMITER
    };

    /**
     * Returns a ActivityViewCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A ActivityViewCommand object if a valid integer representing a session's unique identifier,
     *         and a valid integer representing an activity's unique identifier are found
     *         in the input arguments.
     * @throws InvalidFormatException If a valid integer representing a session's unique identifier
     *                                and an activity's unique identifier cannot be found in the input arguments.
     */
    @Override
    public ActivityViewCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        try {
            int sessionId = ParserUtils.parseSessionId(commandArgs);
            int activityId = ParserUtils.parseActivityId(commandArgs);
            return new ActivityViewCommand(sessionId,activityId);
        } catch (InvalidFormatException exception) {
            String invalidCommandMessage = exception.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

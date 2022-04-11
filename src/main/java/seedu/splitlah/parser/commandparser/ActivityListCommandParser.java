package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.ActivityListCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that parses user arguments into an ActivityListCommand object.
 *
 * @author Tianle
 */
public class ActivityListCommandParser implements CommandParser<ActivityListCommand> {

    public static final String COMMAND_TEXT = "activity /list";

    public static final String COMMAND_FORMAT = "Syntax: activity /list /sid [SESSION_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER
    };

    /**
     * Returns an ActivityListCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return An ActivityListCommand object if a valid integer representing a session's unique identifier is found
     *         in the input arguments.
     * @throws InvalidFormatException If a valid integer representing a session's unique identifier cannot be found
     *                                in the input arguments.
     */
    @Override
    public ActivityListCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        try {
            int sessionId = ParserUtils.parseSessionId(commandArgs);
            return new ActivityListCommand(sessionId);
        } catch (InvalidFormatException exception) {
            String invalidCommandMessage = exception.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

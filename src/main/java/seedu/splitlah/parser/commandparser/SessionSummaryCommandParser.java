package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.SessionSummaryCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that is able to parse user arguments into a SessionSummaryCommand object.
 *
 * @author Warren
 */
public class SessionSummaryCommandParser implements CommandParser<SessionSummaryCommand> {

    public static final String COMMAND_TEXT = "session /summary";

    public static final String COMMAND_FORMAT = "Syntax: session /summary /sid [SESSION_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER
    };

    /**
     * Returns a SessionSummaryCommand object after parsing the input arguments from the user.
     * 
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A SessionSummaryCommand object if a valid integer representing a session's unique identifier is found
     *         in the input arguments.
     * @throws InvalidFormatException If a valid integer representing a session's unique identifier cannot be found
     *                                in the input arguments.
     */
    @Override
    public SessionSummaryCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        try {
            int sessionId = ParserUtils.parseSessionId(commandArgs);
            return new SessionSummaryCommand(sessionId);
        } catch (InvalidFormatException exception) {
            String invalidCommandMessage = exception.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

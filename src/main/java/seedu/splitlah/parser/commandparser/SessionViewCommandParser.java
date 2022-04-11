package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.SessionViewCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that views a Session object.
 *
 * @author Saurav
 */
public class SessionViewCommandParser implements CommandParser<SessionViewCommand> {

    public static final String COMMAND_TEXT = "session /view";

    public static final String COMMAND_FORMAT = "Syntax: session /view /sid [SESSION_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER
    };

    /**
     * Returns a SessionViewCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A SessionViewCommand object if a valid integer representing a session's unique identifier is found
     *         in the input arguments.
     * @throws InvalidFormatException If a valid integer representing a session's unique identifier cannot be found
     *                                in the input arguments.
     */
    @Override
    public SessionViewCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        try {
            int sessionId = ParserUtils.parseSessionId(commandArgs);
            return new SessionViewCommand(sessionId);
        } catch (InvalidFormatException exception) {
            String invalidCommandMessage = exception.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

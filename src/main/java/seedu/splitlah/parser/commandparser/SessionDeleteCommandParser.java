package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.SessionDeleteCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;

/**
 * Represents a command parser that is able to parse user arguments into a SessionDeleteCommand object.
 *
 * @author Roy
 */
public class SessionDeleteCommandParser implements CommandParser<SessionDeleteCommand> {

    public static final String COMMAND_TEXT = "session /delete";

    public static final String COMMAND_FORMAT = "Syntax: session /delete /sid [SESSION_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER
    };

    /**
     * Returns a SessionDeleteCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A SessionDeleteCommand object if a valid integer representing a session's unique identifier is found
     *         in the input arguments.
     * @throws InvalidFormatException If a valid integer representing a session's unique identifier cannot be found
     *                                in the input arguments.
     */
    @Override
    public SessionDeleteCommand getCommand(String commandArgs) throws InvalidFormatException {
        try {
            int sessionId = ParserUtils.parseSessionId(commandArgs);
            return new SessionDeleteCommand(sessionId);
        } catch (InvalidFormatException formatException) {
            String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

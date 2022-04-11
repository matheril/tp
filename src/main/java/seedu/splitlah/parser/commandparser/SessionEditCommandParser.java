package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.SessionEditCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

import java.time.LocalDate;

/**
 * Represents a command parser that is able to parse user arguments into a SessionEditCommand object.
 *
 * @author Roy
 */
public class SessionEditCommandParser implements CommandParser<SessionEditCommand> {

    public static final String COMMAND_TEXT = "session /edit";

    public static final String COMMAND_FORMAT =
            "Syntax: session /edit /sid [SESSION_ID] {/n [SESSION_NAME] /d [SESSION_DATE] /pl [NAME1 NAME2...]}";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.SESSION_ID_DELIMITER,
        ParserUtils.NAME_DELIMITER,
        ParserUtils.DATE_DELIMITER,
        ParserUtils.PERSON_LIST_DELIMITER,
        ParserUtils.GROUP_ID_DELIMITER
    };

    /**
     * Returns a SessionEditCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A SessionEditCommand object if all necessary arguments required for the SessionEditCommand object
     *         to function are found in the input arguments.
     * @throws InvalidFormatException If the session unique identifier cannot be found in the input arguments or
     *                                if all optional arguments cannot be found in the input arguments.
     */
    @Override
    public SessionEditCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        int parsedSessionId = 0;
        try {
            parsedSessionId = ParserUtils.parseSessionId(commandArgs);
        } catch (InvalidFormatException formatException) {
            String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
        assert parsedSessionId > 0;

        boolean hasSessionNameDelimiter = false;
        String parsedSessionName = null;
        try {
            parsedSessionName = ParserUtils.parseName(commandArgs);
            hasSessionNameDelimiter = true;
        } catch (InvalidFormatException formatException) {
            if (!formatException.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                    + ParserUtils.NAME_DELIMITER)) {
                String invalidCommandMessage = formatException.getMessage() + "\n" +  COMMAND_FORMAT;
                throw new InvalidFormatException(invalidCommandMessage);
            }
        }

        boolean hasSessionDateDelimiter = false;
        LocalDate parsedSessionDate = null;
        try {
            parsedSessionDate = ParserUtils.parseLocalDate(commandArgs);
            hasSessionDateDelimiter = true;
        } catch (InvalidFormatException formatException) {
            if (!formatException.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                    + ParserUtils.DATE_DELIMITER)) {
                String invalidCommandMessage = formatException.getMessage() + "\n" +  COMMAND_FORMAT;
                throw new InvalidFormatException(invalidCommandMessage);
            }
        }

        boolean hasPersonListDelimiter = false;
        String [] parsedNames = null;
        try {
            parsedNames = ParserUtils.parsePersonList(commandArgs);
            hasPersonListDelimiter = true;
        } catch (InvalidFormatException formatException) {
            if (!formatException.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                    + ParserUtils.PERSON_LIST_DELIMITER)) {
                String invalidCommandMessage = formatException.getMessage() + "\n" +  COMMAND_FORMAT;
                throw new InvalidFormatException(invalidCommandMessage);
            }
        }

        boolean hasNoEdit = !hasSessionNameDelimiter && !hasSessionDateDelimiter && !hasPersonListDelimiter;
        if (hasNoEdit) {
            String invalidCommandMessage = Message.ERROR_SESSIONEDIT_NO_EDIT_DELIMITERS_FOUND + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }

        return new SessionEditCommand(parsedSessionId, parsedSessionName, parsedNames, parsedSessionDate);
    }
}

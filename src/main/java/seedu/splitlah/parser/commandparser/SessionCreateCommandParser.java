package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.SessionCreateCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

import java.time.LocalDate;

/**
 * Represents a command parser that is able to parse user arguments into a SessionCreateCommand object.
 *
 * @author Roy
 */
public class SessionCreateCommandParser implements CommandParser<SessionCreateCommand> {

    public static final String COMMAND_TEXT = "session /create";

    public static final String COMMAND_FORMAT =
            "Syntax: session /create /n [SESSION_NAME] /d [SESSION_DATE] {/pl [NAME1 NAME2...] /gid [GROUP_ID]}";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.NAME_DELIMITER,
        ParserUtils.DATE_DELIMITER,
        ParserUtils.PERSON_LIST_DELIMITER,
        ParserUtils.GROUP_ID_DELIMITER
    };

    /**
     * Returns a SessionCreateCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A SessionCreateCommand object if all necessary arguments required for the SessionCreateCommand object
     *         to function are found in the input arguments.
     * @throws InvalidFormatException If at least one of the necessary arguments cannot be found in the input arguments.
     */
    @Override
    public SessionCreateCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;

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

        boolean hasGroupIdDelimiter = false;
        int groupId = -1;
        try {
            groupId = ParserUtils.parseGroupId(commandArgs);
            hasGroupIdDelimiter = true;
        } catch (InvalidFormatException formatException) {
            if (!formatException.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                    + ParserUtils.GROUP_ID_DELIMITER)) {
                String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
                throw new InvalidFormatException(invalidCommandMessage);
            }
        }

        boolean isMissingBothDelimiters = !hasPersonListDelimiter && !hasGroupIdDelimiter;
        if (isMissingBothDelimiters) {
            String invalidCommandMessage = Message.ERROR_SESSIONCREATE_MISSING_PERSONLIST_AND_GROUP_DELIMITERS + "\n"
                    + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }

        try {
            String parsedSessionName = ParserUtils.parseName(commandArgs);
            LocalDate parsedSessionDate = ParserUtils.parseLocalDate(commandArgs);
            return new SessionCreateCommand(parsedSessionName, parsedNames, parsedSessionDate, groupId);
        } catch (InvalidFormatException formatException) {
            String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

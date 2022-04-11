package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.GroupEditCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that is able to parse user arguments into a GroupEditCommand object.
 */
public class GroupEditCommandParser implements CommandParser<GroupEditCommand> {

    public static final String COMMAND_TEXT = "group /edit";

    public static final String COMMAND_FORMAT =
        "Syntax: group /edit /gid [GROUP_ID] {/n [GROUP_NAME] /pl [NAME1 NAME2...]}";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.GROUP_ID_DELIMITER,
        ParserUtils.NAME_DELIMITER,
        ParserUtils.PERSON_LIST_DELIMITER
    };

    /**
     * Returns a GroupEditCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A GroupEditCommand object if all necessary arguments required for the GroupEditCommand object
     *         are found in the input arguments.
     * @throws InvalidFormatException If the group unique identifier cannot be found in the input arguments or
     *                                if all optional arguments cannot be found in the input arguments.
     */
    @Override
    public GroupEditCommand getCommand(String commandArgs) throws InvalidFormatException {
        int parsedGroupId;
        try {
            parsedGroupId = ParserUtils.parseGroupId(commandArgs);
        } catch (InvalidFormatException formatException) {
            String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
        boolean hasGroupNameDelimiter = false;
        String parsedGroupName = null;
        try {
            parsedGroupName = ParserUtils.parseName(commandArgs);
            hasGroupNameDelimiter = true;
        } catch (InvalidFormatException formatException) {
            if (!formatException.getMessage().equalsIgnoreCase(Message.ERROR_PARSER_DELIMITER_NOT_FOUND
                + ParserUtils.NAME_DELIMITER)) {
                String invalidCommandMessage = formatException.getMessage() + "\n" +  COMMAND_FORMAT;
                throw new InvalidFormatException(invalidCommandMessage);
            }
        }

        boolean hasPersonListDelimiter = false;
        String[] parsedNames = null;
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

        boolean hasNoEdit = (!hasGroupNameDelimiter && !hasPersonListDelimiter);
        if (hasNoEdit) {
            String invalidCommandMessage = Message.ERROR_GROUPEDIT_NO_EDIT_DELIMITERS_FOUND + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }

        return new GroupEditCommand(parsedNames, parsedGroupName, parsedGroupId);
    }
}

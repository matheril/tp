package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.GroupDeleteCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;

/**
 * Represents a command parser that is able to parse user arguments into a GroupDeleteCommand object.
 *
 * @author Tianle
 */
public class GroupDeleteCommandParser implements CommandParser<GroupDeleteCommand> {

    public static final String COMMAND_TEXT = "group /delete";

    public static final String COMMAND_FORMAT = "Syntax: group /delete /gid [GROUP_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.GROUP_ID_DELIMITER
    };

    /**
     * Returns a GroupDeleteCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A GroupDeleteCommand object if a valid integer representing a group's unique identifier is found
     *         in the input arguments.
     * @throws InvalidFormatException If a valid integer representing a group's unique identifier cannot be found
     *                                in the input arguments.
     */
    @Override
    public GroupDeleteCommand getCommand(String commandArgs) throws InvalidFormatException {
        try {
            int groupId = ParserUtils.parseGroupId(commandArgs);
            return new GroupDeleteCommand(groupId);
        } catch (InvalidFormatException formatException) {
            String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}

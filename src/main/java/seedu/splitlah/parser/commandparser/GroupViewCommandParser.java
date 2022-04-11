package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.GroupViewCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

/**
 * Represents a command parser that is able to parse user arguments into an GroupViewCommand object.
 *
 * @author Ivan
 */
public class GroupViewCommandParser implements CommandParser<GroupViewCommand> {

    public static final String COMMAND_TEXT = "group /view";

    public static final String COMMAND_FORMAT = "Syntax: group /view /gid [GROUP_ID]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.GROUP_ID_DELIMITER
    };

    /**
     * Returns a GroupViewCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return An GroupViewCommand object if all necessary parameters required are found in the input arguments.
     * @throws InvalidFormatException If at least one of the necessary parameters cannot be found
     *                                in the input arguments.
     */
    @Override
    public GroupViewCommand getCommand(String commandArgs) throws InvalidFormatException {
        try {
            int groupId = ParserUtils.parseGroupId(commandArgs);
            assert groupId > 0 : Message.ASSERT_GROUPVIEWPARSER_GROUP_ID_NOT_INITIALIZED;
            return new GroupViewCommand(groupId);
        } catch (InvalidFormatException e) {
            throw new InvalidFormatException(e.getMessage() + "\n" + COMMAND_FORMAT);
        }
    }
}

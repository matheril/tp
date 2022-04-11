package seedu.splitlah.parser.commandparser;

import seedu.splitlah.command.GroupCreateCommand;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;


/**
 * Represents a command parser that is able to parse user arguments into a GroupCreateCommand object.
 *
 * @author Tianle
 */
public class GroupCreateCommandParser implements CommandParser<GroupCreateCommand> {

    public static final String COMMAND_TEXT = "group /create";

    public static final String COMMAND_FORMAT = "Syntax: group /create /n [GROUP_NAME] /pl [NAME1 NAME2...]";

    public static final String[] COMMAND_DELIMITERS = {
        ParserUtils.NAME_DELIMITER,
        ParserUtils.PERSON_LIST_DELIMITER
    };

    /**
     * Returns a GroupCreateCommand object after parsing the input arguments from the user.
     *
     * @param commandArgs A String object representing arguments provided by the user.
     * @return A GroupCreateCommand object if all necessary arguments required for the GroupCreateCommand object
     *         to function are found in the input arguments.
     * @throws InvalidFormatException If at least one of the necessary arguments cannot be found in the input arguments.
     */
    @Override
    public GroupCreateCommand getCommand(String commandArgs) throws InvalidFormatException {
        assert commandArgs != null : Message.ASSERT_PARSER_COMMAND_ARGUMENTS_NULL;
        try {
            String parsedGroupName = ParserUtils.parseName(commandArgs);
            String[] parsedNameList = ParserUtils.parsePersonList(commandArgs);
            return new GroupCreateCommand(parsedGroupName, parsedNameList);
        } catch (InvalidFormatException formatException) {
            String invalidCommandMessage = formatException.getMessage() + "\n" + COMMAND_FORMAT;
            throw new InvalidFormatException(invalidCommandMessage);
        }
    }
}


package seedu.splitlah.parser;

import seedu.splitlah.command.Command;
import seedu.splitlah.command.InvalidCommand;
import seedu.splitlah.data.Manager;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.commandparser.ActivityCreateCommandParser;
import seedu.splitlah.parser.commandparser.ActivityDeleteCommandParser;
import seedu.splitlah.parser.commandparser.ActivityEditCommandParser;
import seedu.splitlah.parser.commandparser.ActivityViewCommandParser;
import seedu.splitlah.parser.commandparser.ActivityListCommandParser;
import seedu.splitlah.parser.commandparser.ExitCommandParser;
import seedu.splitlah.parser.commandparser.GroupEditCommandParser;
import seedu.splitlah.parser.commandparser.GroupListCommandParser;
import seedu.splitlah.parser.commandparser.GroupCreateCommandParser;
import seedu.splitlah.parser.commandparser.GroupDeleteCommandParser;
import seedu.splitlah.parser.commandparser.GroupViewCommandParser;
import seedu.splitlah.parser.commandparser.HelpCommandParser;
import seedu.splitlah.parser.commandparser.SessionCreateCommandParser;
import seedu.splitlah.parser.commandparser.SessionDeleteCommandParser;
import seedu.splitlah.parser.commandparser.SessionEditCommandParser;
import seedu.splitlah.parser.commandparser.SessionListCommandParser;
import seedu.splitlah.parser.commandparser.SessionSummaryCommandParser;
import seedu.splitlah.parser.commandparser.SessionViewCommandParser;
import seedu.splitlah.ui.Message;

import java.util.logging.Level;

/**
 * Represents a parser that interprets the user input into a command that can be run by the program.
 *
 * @author Warren
 */
public class Parser {

    private static final int COMMAND_WITH_ARGS_TOKEN_COUNT = 3;
    private static final int DELIMITERED_COMMAND_MIN_TOKEN_COUNT = 2;

    /**
     * Returns a String object containing the arguments portion of the entire command input from the user.<br>
     * E.g. Returns "/n Lunch /d 11-03-2022 /pl Warren Ivan Roy" where
     *      commandString = "session /create /n Lunch /d 11-03-2022 /pl Warren Ivan Roy"
     *
     * @param commandString A String object that contains the entire command input provided by the user.
     * @return A String object containing the arguments portion of the entire command input from the user if any,
     *         an empty String object otherwise.
     */
    public static String getRemainingArgument(String commandString) {
        String[] commandTokens =
                commandString.trim().split(ParserUtils.REGEX_WHITESPACES_DELIMITER, COMMAND_WITH_ARGS_TOKEN_COUNT);
        if (commandTokens.length < COMMAND_WITH_ARGS_TOKEN_COUNT) {
            return "";
        }
        return commandTokens[2];
    }

    /**
     * Returns a String object containing the command type portion of the entire command input from the user.<br>
     * E.g. Returns "session /create" where
     *      commandString = "session /create /n Lunch /d 11-03-2022 /pl Warren Ivan Roy"
     *
     * @param commandString A String object that contains the entire command input provided by the user.
     * @return A String object containing the command type portion of the entire command input from the user if valid,
     *         null otherwise.
     */
    public static String getCommandType(String commandString) {
        String[] commandTokens =
                commandString.trim().split(ParserUtils.REGEX_WHITESPACES_DELIMITER, COMMAND_WITH_ARGS_TOKEN_COUNT);

        if (commandTokens.length < DELIMITERED_COMMAND_MIN_TOKEN_COUNT) {
            return commandTokens[0];
        } else if (!commandTokens[1].startsWith(ParserUtils.DELIMITER_INDICATOR)) {
            return null;
        }
        return commandTokens[0] + " " + commandTokens[1];
    }

    /**
     * Returns a Command object that corresponds to the String object representing the input provided by the user.
     *
     * @param input A String object representing the input entered by the user of SplitLah.
     * @return A Command object that performs the task specified by the user if the syntax of the input is valid,
     *         an InvalidCommand object that prints an error message otherwise.
     */
    public static Command getCommand(String input) {
        String commandType = getCommandType(input);
        String remainingArgs = getRemainingArgument(input);

        if (commandType == null) {
            return new InvalidCommand(Message.ERROR_PARSER_INVALID_COMMAND);
        }
        
        Manager.getLogger().log(Level.FINEST, Message.LOGGER_PARSER_COMMAND_TYPE + commandType);
        Manager.getLogger().log(Level.FINEST, Message.LOGGER_PARSER_REMAINING_ARGS + remainingArgs);

        String errorMessage = ParserUtils.checkIfCommandIsValid(commandType, remainingArgs);
        if (!errorMessage.isEmpty()) {
            return new InvalidCommand(errorMessage);
        }

        try {
            switch (commandType.toLowerCase()) {
            case SessionCreateCommandParser.COMMAND_TEXT:
                return new SessionCreateCommandParser().getCommand(remainingArgs);
            case SessionDeleteCommandParser.COMMAND_TEXT:
                return new SessionDeleteCommandParser().getCommand(remainingArgs);
            case SessionEditCommandParser.COMMAND_TEXT:
                return new SessionEditCommandParser().getCommand(remainingArgs);
            case SessionSummaryCommandParser.COMMAND_TEXT:
                return new SessionSummaryCommandParser().getCommand(remainingArgs);
            case SessionListCommandParser.COMMAND_TEXT:
                return new SessionListCommandParser().getCommand(remainingArgs);
            case SessionViewCommandParser.COMMAND_TEXT:
                return new SessionViewCommandParser().getCommand(remainingArgs);
            case ActivityCreateCommandParser.COMMAND_TEXT:
                return new ActivityCreateCommandParser().getCommand(remainingArgs);
            case ActivityDeleteCommandParser.COMMAND_TEXT:
                return new ActivityDeleteCommandParser().getCommand(remainingArgs);
            case ActivityListCommandParser.COMMAND_TEXT:
                return new ActivityListCommandParser().getCommand(remainingArgs);
            case ActivityViewCommandParser.COMMAND_TEXT:
                return new ActivityViewCommandParser().getCommand(remainingArgs);
            case ActivityEditCommandParser.COMMAND_TEXT:
                return new ActivityEditCommandParser().getCommand(remainingArgs);
            case GroupCreateCommandParser.COMMAND_TEXT:
                return new GroupCreateCommandParser().getCommand(remainingArgs);
            case GroupDeleteCommandParser.COMMAND_TEXT:
                return new GroupDeleteCommandParser().getCommand(remainingArgs);
            case GroupListCommandParser.COMMAND_TEXT:
                return new GroupListCommandParser().getCommand(remainingArgs);
            case GroupEditCommandParser.COMMAND_TEXT:
                return new GroupEditCommandParser().getCommand(remainingArgs);
            case GroupViewCommandParser.COMMAND_TEXT:
                return new GroupViewCommandParser().getCommand(remainingArgs);
            case HelpCommandParser.COMMAND_TEXT:
                return new HelpCommandParser().getCommand(remainingArgs);
            case ExitCommandParser.COMMAND_TEXT:
                return new ExitCommandParser().getCommand(remainingArgs);
            default:
                return new InvalidCommand(Message.ERROR_PARSER_INVALID_COMMAND);
            }
        } catch (InvalidFormatException exception) {
            return new InvalidCommand(exception.getMessage());
        }
    }
}

package seedu.splitlah.parser;

import org.junit.jupiter.api.Test;
import seedu.splitlah.command.ActivityCreateCommand;
import seedu.splitlah.command.ActivityDeleteCommand;
import seedu.splitlah.command.ActivityEditCommand;
import seedu.splitlah.command.ActivityListCommand;
import seedu.splitlah.command.ActivityViewCommand;
import seedu.splitlah.command.Command;
import seedu.splitlah.command.ExitCommand;
import seedu.splitlah.command.GroupCreateCommand;
import seedu.splitlah.command.GroupDeleteCommand;
import seedu.splitlah.command.GroupEditCommand;
import seedu.splitlah.command.GroupListCommand;
import seedu.splitlah.command.GroupViewCommand;
import seedu.splitlah.command.HelpCommand;
import seedu.splitlah.command.InvalidCommand;
import seedu.splitlah.command.SessionCreateCommand;
import seedu.splitlah.command.SessionDeleteCommand;
import seedu.splitlah.command.SessionEditCommand;
import seedu.splitlah.command.SessionListCommand;
import seedu.splitlah.command.SessionSummaryCommand;
import seedu.splitlah.command.SessionViewCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ParserTest {

    // getCommand()
    /**
     * Checks if valid objects of subclasses of Command class are returned when valid inputs are provided by the user.
     */
    @Test
    void getCommand_validInput_validCommand() {
        String activityCreateCommandInput = "activity /create /sid 2 /n Class Lunch /p Alice /i Alice Bob /co 10";
        Command command = Parser.getCommand(activityCreateCommandInput);
        assertEquals(ActivityCreateCommand.class, command.getClass());

        String activityDeleteCommandInput = "activity /delete /sid 2 /aid 1";
        command = Parser.getCommand(activityDeleteCommandInput);
        assertEquals(ActivityDeleteCommand.class, command.getClass());

        String activityEditCommandInput = 
                "activity /edit /sid 1 /aid 1 /n Dinner /p Bob /i Alice Bob /co 30 /gst 7 /sc 10";
        command = Parser.getCommand(activityEditCommandInput);
        assertEquals(ActivityEditCommand.class, command.getClass());

        String activityListCommandInput = "activity /list /sid 1";
        command = Parser.getCommand(activityListCommandInput);
        assertEquals(ActivityListCommand.class, command.getClass());

        String activityViewCommandInput = "activity /view /sid 1 /aid 1";
        command = Parser.getCommand(activityViewCommandInput);
        assertEquals(ActivityViewCommand.class, command.getClass());

        String groupCreateCommandInput = "group /create /n SplitLah /pl Roy Ivan Warren Saurav Tianle";
        command = Parser.getCommand(groupCreateCommandInput);
        assertEquals(GroupCreateCommand.class, command.getClass());

        String groupDeleteCommandInput = "group /delete /gid 1";
        command = Parser.getCommand(groupDeleteCommandInput);
        assertEquals(GroupDeleteCommand.class, command.getClass());
        
        String groupEditCommandInput = "group /edit /gid 1 /n Class gathering";
        command = Parser.getCommand(groupEditCommandInput);
        assertEquals(GroupEditCommand.class, command.getClass());

        String groupListCommandInput = "group /list";
        command = Parser.getCommand(groupListCommandInput);
        assertEquals(GroupListCommand.class, command.getClass());

        String groupViewCommandInput = "group /view /gid 1";
        command = Parser.getCommand(groupViewCommandInput);
        assertEquals(GroupViewCommand.class, command.getClass());

        String sessionCreateCommandInput = "session /create /n Class Gathering /d 16-04-2022 /gid 1 /pl Alice";
        command = Parser.getCommand(sessionCreateCommandInput);
        assertEquals(SessionCreateCommand.class, command.getClass());

        String sessionDeleteCommandInput = "session /delete /sid 1";
        command = Parser.getCommand(sessionDeleteCommandInput);
        assertEquals(SessionDeleteCommand.class, command.getClass());

        String sessionEditCommandInput = "session /edit /sid 1 /n Class gathering /d 16-03-2022";
        command = Parser.getCommand(sessionEditCommandInput);
        assertEquals(SessionEditCommand.class, command.getClass());
        
        String sessionListCommandInput = "session /list";
        command = Parser.getCommand(sessionListCommandInput);
        assertEquals(SessionListCommand.class, command.getClass());

        String sessionViewCommandInput = "session /view /sid 1";
        command = Parser.getCommand(sessionViewCommandInput);
        assertEquals(SessionViewCommand.class, command.getClass());
        
        String sessionSummaryCommandInput = "session /summary /sid 1";
        command = Parser.getCommand(sessionSummaryCommandInput);
        assertEquals(SessionSummaryCommand.class, command.getClass());
        
        String helpCommandInput = "help";
        command = Parser.getCommand(helpCommandInput);
        assertEquals(HelpCommand.class, command.getClass());
        
        String exitCommandInput = "exit";
        command = Parser.getCommand(exitCommandInput);
        assertEquals(ExitCommand.class, command.getClass());
    }
    
    /**
     * Checks if an InvalidCommand is returned when an empty String object is provided by the user.
     */
    @Test
    void getCommand_emptyString_InvalidCommand() {
        String emptyString = "";
        Command command = Parser.getCommand(emptyString);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if an InvalidCommand is returned when a String object containing only whitespaces is provided by the user.
     */
    @Test
    void getCommand_whitespaceInput_InvalidCommand() {
        String whitespaceString = "     ";
        Command command = Parser.getCommand(whitespaceString);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if an InvalidCommand object is returned when additional irrelevant argument tokens are appended to the 
     * single token commands (e.g. "help", "exit") as input.
     */
    @Test
    void getCommand_singleTokenCommandsWithIrrelevantTokens_InvalidCommand() {
        // Single additional token, no delimiters
        String helpWithIrrelevantArguments = "help apple";
        Command command = Parser.getCommand(helpWithIrrelevantArguments);
        assertEquals(InvalidCommand.class, command.getClass());

        // Single additional token, with delimiters
        helpWithIrrelevantArguments = "help /apple";
        command = Parser.getCommand(helpWithIrrelevantArguments);
        assertEquals(InvalidCommand.class, command.getClass());

        // Two additional tokens, without delimiters
        helpWithIrrelevantArguments = "help apple orange";
        command = Parser.getCommand(helpWithIrrelevantArguments);
        assertEquals(InvalidCommand.class, command.getClass());

        // Two additional tokens, one delimiters
        helpWithIrrelevantArguments = "help /apple orange";
        command = Parser.getCommand(helpWithIrrelevantArguments);
        assertEquals(InvalidCommand.class, command.getClass());

        // Three additional tokens, two delimiters
        helpWithIrrelevantArguments = "help /apple /sid 1";
        command = Parser.getCommand(helpWithIrrelevantArguments);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if an InvalidCommand object is returned when duplicate valid delimiters are provided by the user.
     */
    @Test
    void getCommand_duplicateValidDelimiters_InvalidCommand() {
        String inputWithDuplicateValidDelimiters = "session /create /n Class outing /d today /pl Alice /d 25-03-2022";
        Command command = Parser.getCommand(inputWithDuplicateValidDelimiters);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if an InvalidCommand object is returned when delimiters not belonging to
     * commands are provided by the user.
     */
    @Test
    void getCommand_invalidDelimiterForCommand_InvalidCommand() {
        String inputWithInvalidDelimiter = "session /create /n Outing /d today /pl Alice /co 20";
        Command command = Parser.getCommand(inputWithInvalidDelimiter);
        assertEquals(InvalidCommand.class, command.getClass());
    }
    
    // getCommandType()
    /**
     * Checks if an empty String object is returned as the command string
     * when an empty String object is provided by the user.
     */
    @Test
    void getCommandType_emptyStringInput_emptyString() {
        String emptyString = "";
        String output = Parser.getCommandType(emptyString);
        assertEquals("", output);
    }

    /**
     * Checks if a String object containing identical contents as the input is returned as the command string
     * when a single token of input is provided by the user.
     * Matching command types: "help", "list", "exit"
     */
    @Test
    void getCommandType_singleTokenInput_inputEqualsOutput() {
        String singleTokenString = "randomTest123";
        String output = Parser.getCommandType(singleTokenString);
        assertEquals(singleTokenString, output);
    }

    /**
     * Checks if null is returned when an input, with a second token does not start with a delimiter,
     * is provided by the user.
     */
    @Test
    void getCommandType_doubleTokenNoDelimiterInput_null() {
        String doubleTokenWithNoDelimiterString = "session create";
        String output = Parser.getCommandType(doubleTokenWithNoDelimiterString);
        assertNull(output);
    }

    // getRemainingArgument()
    /**
     * Checks if an empty String object is returned as the remaining arguments of the command
     * when an empty String object is provided by the user.
     */
    @Test
    void getRemainingArgument_emptyStringInput_emptyString() {
        String emptyString = "";
        String output = Parser.getRemainingArgument(emptyString);
        assertEquals("", output);
    }

    /**
     * Checks if an empty String object is returned as the remaining arguments of the command
     * when an input of only a single token is provided by the user.
     */
    @Test
    void getRemainingArgument_singleTokenInput_emptyString() {
        String singleTokenString = "randomTest123";
        String output = Parser.getRemainingArgument(singleTokenString);
        assertEquals("", output);
    }

    /**
     * Checks if an empty String object is returned as the remaining arguments of the command
     * when an input of only two tokens is provided by the user.
     */
    @Test
    void getRemainingArgument_twoInputTokens_emptyString() {
        String twoInputTokensString = "brownFox jumpsOver";
        String output = Parser.getRemainingArgument(twoInputTokensString);
        assertEquals("", output);
    }

    /**
     * Checks if the third token is correctly returned as the remaining arguments of the command
     * when an input of three tokens and multiple whitespaces is provided by the user.
     */
    @Test
    void getRemainingArgument_threeInputTokensAndWhitespace_thirdToken() {
        String threeInputTokensAndWhitespaceString = "brownFox jumpsOver theLazyDog  ";
        String output = Parser.getRemainingArgument(threeInputTokensAndWhitespaceString);
        assertEquals("theLazyDog", output);
    }

    /**
     * Checks if the third token and onwards is correctly returned as the remaining arguments of the command
     * when an input of four tokens is provided by the user.
     */
    @Test
    void getRemainingArgument_fourInputTokens_thirdTokenOnwards() {
        String fourInputTokensString = "brownFox jumpsOver theLazy Dog";
        String output = Parser.getRemainingArgument(fourInputTokensString);
        assertEquals("theLazy Dog", output);
    }
}

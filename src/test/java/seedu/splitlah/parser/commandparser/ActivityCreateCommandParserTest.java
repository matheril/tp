package seedu.splitlah.parser.commandparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.command.ActivityCreateCommand;
import seedu.splitlah.command.Command;
import seedu.splitlah.data.Manager;
import seedu.splitlah.exceptions.InvalidFormatException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ActivityCreateCommandParserTest {

    private static final String COMMAND_TYPE = ActivityCreateCommandParser.COMMAND_TEXT;

    Manager manager = new Manager();

    /**
     * Creates a session that is stored and managed by the Manager object.
     */
    @BeforeEach
    public void setUp() {
        String sessionInput = "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
        Command createSession = Parser.getCommand(sessionInput);
        createSession.run(manager);
    }

    /**
     * Checks if an InvalidFormatException with the correct message is thrown
     * when both cost and cost list are not provided by the user.
     */
    @Test
    public void getCommand_hasMissingCostAndCostList_InvalidFormatExceptionThrown() {
        String userInput = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie";
        String arguments = Parser.getRemainingArgument(userInput);
        String errorMessage = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, arguments);
        if (!errorMessage.isEmpty()) {
            fail();
        }

        ActivityCreateCommandParser activityCreateCommandParser = new ActivityCreateCommandParser();
        try {
            activityCreateCommandParser.getCommand(arguments);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_ACTIVITYCREATE_MISSING_COST_AND_COST_LIST
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }
    }

    /**
     * Checks if an InvalidFormatException with the correct message is thrown
     * when both cost and cost list are provided by the user.
     */
    @Test
    public void getCommand_hasBothCostAndCostList_InvalidFormatExceptionThrown() {
        String userInput = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30 /cl 10 10 10";
        String arguments = Parser.getRemainingArgument(userInput);
        String errorMessage = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, arguments);
        if (!errorMessage.isEmpty()) {
            fail();
        }

        ActivityCreateCommandParser activityCreateCommandParser = new ActivityCreateCommandParser();
        try {
            activityCreateCommandParser.getCommand(arguments);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_ACTIVITYCREATE_HAS_BOTH_COST_AND_COST_LIST
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }
    }

    /**
     * Checks if an InvalidFormatException with the correct message is thrown
     * when the cost list and involved list that are provided by the user are of different lengths.
     */
    @Test
    public void getCommand_costListAndInvolvedListDifferentLength_InvalidFormatExceptionThrown() {
        ActivityCreateCommandParser activityCreateCommandParser = new ActivityCreateCommandParser();

        //Case 1: Involved list longer than cost list
        String userInputOne = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /cl 10 10";
        String argumentsOne = Parser.getRemainingArgument(userInputOne);
        String errorMessageOne = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argumentsOne);
        if (!errorMessageOne.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argumentsOne);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_ACTIVITYCREATE_INVOLVED_AND_COST_DIFFERENT_LENGTH
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        //Case 2: Involved list shorter than cost list
        String userInputTwo = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob /cl 10 10 10";
        String argumentsTwo = Parser.getRemainingArgument(userInputTwo);
        String errorMessageTwo = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argumentsTwo);
        if (!errorMessageTwo.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argumentsTwo);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_ACTIVITYCREATE_INVOLVED_AND_COST_DIFFERENT_LENGTH
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }
    }

    /**
     * Checks if an InvalidFormatException with the correct message is thrown
     * when missing delimiters are detected in the user input.
     */
    @Test
    public void getCommand_hasMissingDelimiter_InvalidFormatExceptionThrown() {
        ActivityCreateCommandParser activityCreateCommandParser = new ActivityCreateCommandParser();

        // Case 1: Missing /sid delimiter
        String inputMissingSessionIdDelimiter = "activity /create /n Dinner /p Alice /i Alice Bob Charlie /co 15";
        String argsMissingSessionIdDelimiter = Parser.getRemainingArgument(inputMissingSessionIdDelimiter);
        String errorMessageOne = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingSessionIdDelimiter);
        if (!errorMessageOne.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingSessionIdDelimiter);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_DELIMITER_NOT_FOUND + "/sid"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 2: Missing /n delimiter
        String inputMissingNameDelimiter = "activity /create /sid 1 /p Alice /i Alice Bob Charlie /co 15";
        String argsMissingNameDelimiter = Parser.getRemainingArgument(inputMissingNameDelimiter);
        String errorMessageTwo = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingNameDelimiter);
        if (!errorMessageTwo.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingNameDelimiter);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_DELIMITER_NOT_FOUND + "/n"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 3: Missing /p delimiter
        String inputMissingPayerDelimiter = "activity /create /sid 1 /n Dinner /i Alice Bob Charlie /co 15";
        String argsMissingPayerDelimiter = Parser.getRemainingArgument(inputMissingPayerDelimiter);
        String errorMessageThree = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingPayerDelimiter);
        if (!errorMessageThree.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingPayerDelimiter);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_DELIMITER_NOT_FOUND + "/p"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 4: Missing /i delimiter
        String inputMissingInvolvedListDelimiter = "activity /create /sid 1 /n Dinner /p Alice /co 15";
        String argsMissingInvolvedListDelimiter = Parser.getRemainingArgument(inputMissingInvolvedListDelimiter);
        String errorMessageFour = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingInvolvedListDelimiter);
        if (!errorMessageFour.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingInvolvedListDelimiter);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_DELIMITER_NOT_FOUND + "/i"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }
    }

    /**
     * Checks if an InvalidFormatException with the correct message is thrown
     * when missing arguments are detected in the user input.
     */
    @Test
    public void getCommand_hasMissingArguments_InvalidFormatExceptionThrown() {
        ActivityCreateCommandParser activityCreateCommandParser = new ActivityCreateCommandParser();

        // Case 1: Missing session ID
        String inputMissingSessionIdArgument = "activity /create /sid /n Dinner /p Alice /i Alice Bob Charlie /co 15";
        String argsMissingSessionIdArgument = Parser.getRemainingArgument(inputMissingSessionIdArgument);
        String errorMessageOne = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingSessionIdArgument);
        if (!errorMessageOne.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingSessionIdArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/sid"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 2: Missing Activity Name
        String inputMissingNameArgument = "activity /create /sid 1 /n /p Alice /i Alice Bob Charlie /co 15";
        String argsMissingNameArgument = Parser.getRemainingArgument(inputMissingNameArgument);
        String errorMessageTwo = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingNameArgument);
        if (!errorMessageTwo.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingNameArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/n"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 3: Missing Payer
        String inputMissingPayerArgument = "activity /create /sid 1 /n Dinner /p /i Alice Bob Charlie /co 15";
        String argsMissingPayerArgument = Parser.getRemainingArgument(inputMissingPayerArgument);
        String errorMessageThree = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingPayerArgument);
        if (!errorMessageThree.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingPayerArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/p"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 4: Missing involved list
        String inputMissingInvolvedListArgument = "activity /create /sid 1 /n Dinner /p Alice /i /co 15";
        String argsMissingInvolvedListArgument = Parser.getRemainingArgument(inputMissingInvolvedListArgument);
        String errorMessageFour = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingInvolvedListArgument);
        if (!errorMessageFour.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingInvolvedListArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/i"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 5: Missing total cost
        String inputMissingTotalCostArgument = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co";
        String argsMissingTotalCostArgument = Parser.getRemainingArgument(inputMissingTotalCostArgument);
        String errorMessageFive = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingTotalCostArgument);
        if (!errorMessageFive.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingTotalCostArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/co"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 6: Missing cost list
        String inputMissingCostListArgument = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /cl";
        String argsMissingCostListArgument = Parser.getRemainingArgument(inputMissingCostListArgument);
        String errorMessageSix = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingCostListArgument);
        if (!errorMessageSix.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingCostListArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/cl"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 7: Missing gst
        String inputMissingGstArgument = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30 /gst";
        String argsMissingGstArgument = Parser.getRemainingArgument(inputMissingGstArgument);
        String errorMessageSeven = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingGstArgument);
        if (!errorMessageSeven.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingGstArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/gst"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }

        // Case 8: Missing service charge
        String inputMissingScArgument = "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30 /sc";
        String argsMissingScArgument = Parser.getRemainingArgument(inputMissingScArgument);
        String errorMessageEight = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, argsMissingScArgument);
        if (!errorMessageEight.isEmpty()) {
            fail();
        }

        try {
            activityCreateCommandParser.getCommand(argsMissingScArgument);
            fail();
        } catch (InvalidFormatException e) {
            String messageToTest = Message.ERROR_PARSER_MISSING_ARGUMENT + "/sc"
                    + "\n" + ActivityCreateCommandParser.COMMAND_FORMAT
                    + ActivityCreateCommandParser.COMMAND_FORMAT_FIRST + "\n\t"
                    + ActivityCreateCommandParser.COMMAND_FORMAT_SECOND;
            assertEquals(messageToTest, e.getMessage());
        }
    }

    /**
     * Checks if an ActivityCreateCommand object is correctly returned when the command is correctly entered.
     */
    @Test
    public void getCommand_validUserInput_ActivityCreateCommand() {
        String validUserInput = "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
        String validArguments = Parser.getRemainingArgument(validUserInput);
        String errorMessage = ParserUtils.checkIfCommandIsValid(COMMAND_TYPE, validArguments);
        if (!errorMessage.isEmpty()) {
            fail();
        }

        ActivityCreateCommandParser activityCreateCommandParser = new ActivityCreateCommandParser();
        try {
            Command command = activityCreateCommandParser.getCommand(validArguments);
            assertEquals(ActivityCreateCommand.class, command.getClass());
        } catch (InvalidFormatException e) {
            fail();
        }
    }
}

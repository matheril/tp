package seedu.splitlah.parser.commandparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.command.Command;
import seedu.splitlah.command.InvalidCommand;
import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivityEditCommandParserTest {

    private static final int SESSION_ID = 1;
    private static final int ACTIVITY_ID = 1;

    Manager manager = new Manager();
    Session session;

    /**
     * Creates a session that is stored and managed by the Manager object.
     * Creates an activity in the new session that was created.
     */
    @BeforeEach
    void setUp() throws InvalidDataException {
        String sessionOneArgs = "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
        Command createSessionOne = Parser.getCommand(sessionOneArgs);
        createSessionOne.run(manager);
        String activityOneArgs = "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
        Command createActivityOne = Parser.getCommand(activityOneArgs);
        createActivityOne.run(manager);
        session = manager.getProfile().getSession(SESSION_ID);
    }

    /**
     * Checks if the overall cost of the activity is unchanged when an invalid overall cost is supplied.
     *
     */
    @Test
    public void getCommand_overallCostIsInvalid_InvalidCommandReturned() {
        String userInput = "activity /edit /sid 1 /aid 1 /co -1";
        Command command = Parser.getCommand(userInput);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if the overall cost of an activity does not update when an invalid gst value is supplied.
     *
     */
    @Test
    public void getCommand_gstIsInvalid_InvalidCommandReturned() {
        String userInput = "activity /edit /sid 1 /gst -1";
        Command command = Parser.getCommand(userInput);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if the overall cost of an activity remains unchanged when an invalid service charge is supplied.
     *
     */
    @Test
    public void getCommand_serviceChargeIsInvalid_InvalidCommandReturned() {
        String userInput = "activity /edit /sid 1 /aid 1 /sc -1";
        Command command = Parser.getCommand(userInput);
        assertEquals(InvalidCommand.class, command.getClass());
    }

    /**
     * Checks if an activity is not edited when an activity has duplicate names in the involved list.
     */
    @Test
    public void getCommand_hasNameDuplicatesInInvolvedList_InvalidCommandReturned() {
        String userInput = "activity /edit /sid 1 /n Dinner /p Alice /i Alice Alice Charlie /co 30";
        Command command = Parser.getCommand(userInput);
        assertEquals(InvalidCommand.class, command.getClass());
    }
}

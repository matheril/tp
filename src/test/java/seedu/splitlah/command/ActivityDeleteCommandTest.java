package seedu.splitlah.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.data.Manager;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.ui.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ActivityDeleteCommandTest {

    Manager manager = new Manager();

    /**
     * Creates a session that is stored and managed by the Manager object.
     * Creates two activities in the new session that was created.
     */
    @BeforeEach
    void setUp() {
        String sessionArgs = "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
        Command createSession = Parser.getCommand(sessionArgs);
        createSession.run(manager);
        String activityOneArgs = "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
        Command createActivityOne = Parser.getCommand(activityOneArgs);
        createActivityOne.run(manager);
        String activityTwoArgs = "activity /create /sid 1 /n Lunch /p Alice /i Bob Charlie /cl 5 5";
        Command createActivityTwo = Parser.getCommand(activityTwoArgs);
        createActivityTwo.run(manager);
    }

    /**
     * Checks if an activity is not deleted with an invalid session unique identifier.
     *
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    @Test
    public void run_sessionDoesNotExists_activityListSizeRemainsTwo() throws InvalidDataException {
        String userInput = "activity /delete /sid 3 /aid 1";

        //Checks that command returned is an ActivityDeleteCommand object
        Command command = Parser.getCommand(userInput);
        assertEquals(ActivityDeleteCommand.class, command.getClass());

        //Checks that the number of activities stored remains the same
        command.run(manager);
        assertEquals(2, manager.getProfile().getSession(1).getActivityList().size());
    }

    /**
     * Checks if an activity is not deleted with an invalid activity unique identifier.
     *
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    @Test
    public void run_activityDoesNotExists_activityListSizeRemainsTwo() throws InvalidDataException {
        String userInput = "activity /delete /sid 1 /aid 3";

        //Checks that command returned is an ActivityDeleteCommand object
        Command command = Parser.getCommand(userInput);
        assertEquals(ActivityDeleteCommand.class, command.getClass());

        //Checks that the number of activities stored remains the same
        command.run(manager);
        assertEquals(2, manager.getProfile().getSession(1).getActivityList().size());
    }

    /**
     * Checks if an activity is deleted with a valid command.
     *
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    @Test
    public void run_validCommand_activitySuccessfullyDeleted() throws InvalidDataException {
        String userInput = "activity /delete /sid 1 /aid 1";

        //Checks that command returned is an ActivityDeleteCommand object
        Command command = Parser.getCommand(userInput);
        assertEquals(ActivityDeleteCommand.class, command.getClass());

        //Checks that the number of activities stored decrease by 1
        command.run(manager);
        assertEquals(1, manager.getProfile().getSession(1).getActivityList().size());

        //Checks that the activity with the specified id still exists
        try {
            manager.getProfile().getSession(1).getActivity(1);
            fail();
        } catch (InvalidDataException e) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, e.getMessage());
        }
    }

}

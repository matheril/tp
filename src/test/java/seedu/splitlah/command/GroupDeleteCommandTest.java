package seedu.splitlah.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.data.Manager;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.ui.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class GroupDeleteCommandTest {

    Manager manager = new Manager();

    /**
     * Creates two groups that are stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        String groupOneArgs = "group /create /n grp1 /pl Alice Bob";
        String groupTwoArgs = "group /create /n grp2 /pl Cindy David";
        Command createGroupOne = Parser.getCommand(groupOneArgs);
        createGroupOne.run(manager);
        Command createGroupTwo = Parser.getCommand(groupTwoArgs);
        createGroupTwo.run(manager);
    }

    /**
     * Checks if group is deleted successfully and removed from the list of groups.
     */
    @Test
    public void run_validCommand_groupListSizeBecomesOne() {
        String userInput = "group /delete /gid 1";
        Command command = Parser.getCommand(userInput);

        // Check if a GroupDeleteCommand instance was returned.
        assertEquals(GroupDeleteCommand.class, command.getClass());
        command.run(manager);

        // Check if group was successfully removed from the list of groups.
        assertEquals(1, manager.getProfile().getGroupList().size());

        // Check if the group still exists.
        try {
            manager.getProfile().getGroup(1);
            fail();
        } catch (InvalidDataException invalidDataException) {
            assertEquals(Message.ERROR_PROFILE_GROUP_NOT_IN_LIST, invalidDataException.getMessage());
        }
    }

    /**
     * Checks if the group is deleted with an invalid group unique identifier.
     */
    @Test
    public void run_groupDoesNotExists_groupListSizeRemainsTwo() {
        String userInput = "group /delete /gid 3";
        Command command = Parser.getCommand(userInput);
        command.run(manager);
        assertEquals(2, manager.getProfile().getGroupList().size());
    }
}

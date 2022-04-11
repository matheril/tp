package seedu.splitlah.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.data.Manager;
import seedu.splitlah.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupCreateCommandTest {

    Manager manager = new Manager();

    /**
     * Creates two groups stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        String groupArgs = "group /create /n grp1 /pl Alice Bob";
        Command createGroup = Parser.getCommand(groupArgs);
        createGroup.run(manager);
    }

    /**
     * Checks if the group is created successfully with Person List delimiter
     * and group unique identifier tracker in Profile object is incremented.
     */
    @Test
    public void run_validCommand_groupSuccessfullyCreated() {
        String userInput = "group /create /n grp2 /pl Cindy David";
        Command command = Parser.getCommand(userInput);
        int currentGroupId = manager.getProfile().getGroupIdTracker();
        command.run(manager);
        int testGroupId = manager.getProfile().getGroupIdTracker();
        assertEquals(currentGroupId + 1, testGroupId);

        assertEquals(GroupCreateCommand.class, command.getClass());
        command.run(manager);
        assertEquals(2, manager.getProfile().getGroupList().size());
    }

    /**
     * Checks if the group is created with invalid person names.
     */
    @Test
    public void run_hasInvalidPersonName_groupListSizeRemainsOne() {
        String userInput = "group /create /n grp2 /pl Alice Xae1vr Bob";
        Command command = Parser.getCommand(userInput);
        command.run(manager);

        assertEquals(1, manager.getProfile().getGroupList().size());
    }

    /**
     * Checks if group is created with duplicated person names.
     */
    @Test
    public void run_hasOneNameDuplicate_groupListSizeRemainsOne() {
        String userInput = "group /create /n grp2 /pl Cindy David Cindy";
        Command command = Parser.getCommand(userInput);
        command.run(manager);
        assertEquals(1, manager.getProfile().getGroupList().size());
    }

    /**
     * Checks if group unique identifier is incremented if a group fails
     * to be created due to duplicate names in person list.
     */
    @Test
    public void run_hasOneNameDuplicate_groupIdNotIncremented() {
        int currentGroupId = manager.getProfile().getGroupIdTracker();
        String userInput = "group /create /n grp2 /pl Cindy David Cindy";
        Command command = Parser.getCommand(userInput);
        command.run(manager);
        int testGroupId = manager.getProfile().getGroupIdTracker();
        assertEquals(currentGroupId, testGroupId);
    }

    /**
     * Checks if group is created when a group with the same name exists.
     */
    @Test
    public void run_hasGroupDuplicate_groupListSizeRemainsOne() {
        String userInput = "group /create /n grp1 /pl Cindy David";
        Command command = Parser.getCommand(userInput);
        command.run(manager);
        assertEquals(1, manager.getProfile().getGroupList().size());
    }

    /**
     * Checks if group unique identifier is incremented when a group fails to be created
     * because another group with the same name already exists.
     */
    @Test
    public void run_hasGroupDuplicate_groupIdNotIncremented() {
        int currentGroupId = manager.getProfile().getGroupIdTracker();
        String userInput = "group /create /n grp1 /pl Cindy David";
        Command command = Parser.getCommand(userInput);
        command.run(manager);
        int testGroupId = manager.getProfile().getGroupIdTracker();
        assertEquals(currentGroupId, testGroupId);
    }
}

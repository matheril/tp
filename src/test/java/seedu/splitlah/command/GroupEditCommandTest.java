package seedu.splitlah.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Group;
import seedu.splitlah.data.Person;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GroupEditCommandTest {

    Manager manager = new Manager();

    private static final String ORIGINAL_GROUP_NAME = "grp1";
    private static final int ORIGINAL_GROUP_PERSON_LIST_SIZE = 2;

    private static final String EDITED_GROUP_NAME = "grp_edited";
    private static final int EDITED_GROUP_PERSON_LIST_SIZE = 3;

    /**
     * Creates a group that is stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        String groupArgs = "group /create /n grp1 /pl Alice Bob";
        Command createGroup = Parser.getCommand(groupArgs);
        createGroup.run(manager);
    }

    /**
     * Checks if group is edited successfully with name delimiter.
     *
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    @Test
    public void run_validCommand_groupNameEdited() throws InvalidDataException {
        String userInput = "group /edit /gid 1 /n grp_edited";
        Command command = Parser.getCommand(userInput);

        // Check if a GroupEditCommand instance was returned.
        assertEquals(GroupEditCommand.class, command.getClass());
        command.run(manager);

        Group editedGroup = manager.getProfile().getGroup(1);

        String newNameInGroup = editedGroup.getGroupName();
        int personListSizeInGroup = editedGroup.getPersonList().size();

        assertEquals(EDITED_GROUP_NAME, newNameInGroup);
        assertEquals(ORIGINAL_GROUP_PERSON_LIST_SIZE, personListSizeInGroup);
    }

    /**
     * Checks if group is not edited when an invalid group unique identifier is provided.
     *
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    @Test
    public void run_groupIdDoesNotExist_groupRemainsUnedited() throws InvalidDataException {
        String invalidInput  = "group /edit /gid 2 /n grp_edited";
        Command command = Parser.getCommand(invalidInput);

        // Check if a GroupEditCommand instance was returned.
        assertEquals(GroupEditCommand.class, command.getClass());
        command.run(manager);

        Group editedGroup = manager.getProfile().getGroup(1);
        String nameInGroup = editedGroup.getGroupName();
        int personListSizeInGroup = editedGroup.getPersonCount();

        assertEquals(ORIGINAL_GROUP_NAME, nameInGroup);
        assertEquals(ORIGINAL_GROUP_PERSON_LIST_SIZE, personListSizeInGroup);
    }

    /**
     * Checks if the group is edited successfully with person list delimiter.
     *
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    @Test
    public void run_validCommand_groupPersonListEdited() throws InvalidDataException {
        ArrayList<Person> originalPersonList = new ArrayList<>();
        originalPersonList.add(Person.createPersonFromString("Alice"));
        originalPersonList.add(Person.createPersonFromString("Bob"));
        originalPersonList.add(Person.createPersonFromString("Cindy"));

        String userInput = "group /edit /gid 1 /pl Alice Bob Cindy";
        Command command = Parser.getCommand(userInput);

        // Check if a GroupEditCommand instance was returned.
        assertEquals(GroupEditCommand.class, command.getClass());
        command.run(manager);

        Group editedGroup = manager.getProfile().getGroup(1);

        String nameInGroup = editedGroup.getGroupName();

        assertEquals(ORIGINAL_GROUP_NAME, nameInGroup);

        // Checks if newly edited person list in group has size of 3.
        assertEquals(EDITED_GROUP_PERSON_LIST_SIZE, editedGroup.getPersonCount());

        // Check if Person objects in group before edit is still in group after edit
        originalPersonList.removeAll(editedGroup.getPersonList());
        assertEquals(0, originalPersonList.size());
    }

    /**
     * Checks if group is not edited when supplied with a new group name that exists within the list of groups.
     *
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    @Test
    public void run_invalidGroupNameDuplicate_groupRemainsUnedited() throws InvalidDataException {
        String validInputWithDuplicateGroupName = "group /edit /gid 1 /n grp1";
        Command commandWithSameGroupName = Parser.getCommand(validInputWithDuplicateGroupName);
        commandWithSameGroupName.run(manager);

        Group unEditedGroup = manager.getProfile().getGroup(1);
        String nameInGroup = unEditedGroup.getGroupName();
        int personListSizeInGroup = unEditedGroup.getPersonCount();

        assertEquals(ORIGINAL_GROUP_NAME, nameInGroup);
        assertEquals(ORIGINAL_GROUP_PERSON_LIST_SIZE, personListSizeInGroup);
    }

    /**
     * Checks if group is not edited when list of persons provided after Person list delimiter
     * does not contain all persons that were originally in the group.
     *
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    @Test
    public void run_invalidPersonListArgument_groupRemainsUnedited() throws InvalidDataException {
        String invalidInput  = "group /edit /gid 1 /pl Alice Cindy";
        Command command = Parser.getCommand(invalidInput);

        // Check if a GroupEditCommand instance was returned.
        assertEquals(GroupEditCommand.class, command.getClass());
        command.run(manager);

        Group editedGroup = manager.getProfile().getGroup(1);
        String nameInGroup = editedGroup.getGroupName();
        int personListSizeInGroup = editedGroup.getPersonCount();

        assertEquals(ORIGINAL_GROUP_NAME, nameInGroup);
        assertEquals(ORIGINAL_GROUP_PERSON_LIST_SIZE, personListSizeInGroup);
    }

    /**
     * Checks if group is not edited when there were duplicate names in Person list delimiter.
     *
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    @Test
    public void run_hasOneNameDuplicate_groupRemainsUnedited() throws InvalidDataException {
        String invalidInput  = "group /edit /gid 1 /pl Alice Bob Cindy Cindy";
        Command command = Parser.getCommand(invalidInput);

        // Check if a GroupEditCommand instance was returned.
        assertEquals(GroupEditCommand.class, command.getClass());
        command.run(manager);
        Group editedGroup = manager.getProfile().getGroup(1);
        String nameInGroup = editedGroup.getGroupName();
        int personListSizeInGroup = editedGroup.getPersonCount();

        assertEquals(ORIGINAL_GROUP_NAME, nameInGroup);
        assertEquals(ORIGINAL_GROUP_PERSON_LIST_SIZE, personListSizeInGroup);
    }
}

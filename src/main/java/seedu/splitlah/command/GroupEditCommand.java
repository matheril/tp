package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.data.PersonList;
import seedu.splitlah.data.Group;
import seedu.splitlah.data.Person;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Represents a command object that edits a Group object.
 *
 * @author Tianle
 */
public class GroupEditCommand extends Command {
    private static final String COMMAND_NO_EDITS_MADE = "No changes made.";
    private static final String COMMAND_SUCCESS = "The group was edited successfully.";
    private final String groupName;
    private final String[] involvedList;
    private final int groupId;

    /**
     * Initializes a GroupEditCommand object.
     *
     * @param involvedList  An array of String objects that represents the involved persons for the group.
     * @param groupName     A String object that represents the group name.
     * @param groupId       An integer that represents the group unique identifier for the group to be edited.
     */
    public GroupEditCommand(String[] involvedList, String groupName, int groupId) {
        assert groupId > 0 : Message.ASSERT_GROUPEDIT_GROUP_ID_INVALID;
        this.involvedList = involvedList;
        this.groupName = groupName;
        this.groupId = groupId;
    }
    
    public boolean existingGroupWithTheSameName(ArrayList<Group> groupList, String newName, int groupId) {
        boolean hasDuplicate = false;
        for (Group group: groupList) {
            if (newName.equalsIgnoreCase(group.getGroupName()) && (group.getGroupId() != groupId)) {
                hasDuplicate = true;
            }
        }
        return hasDuplicate;
    }

    /**
     * Runs the command with the group identifier as provided by the user input.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage objects.
     */
    @Override
    public void run(Manager manager) {
        TextUI ui = manager.getUi();
        Group group;
        boolean editedGroupName = false;
        boolean editedPersonList = false;


        try {
            group = manager.getProfile().getGroup(groupId);
        } catch (InvalidDataException invalidDataException) {
            ui.printlnMessageWithDivider(invalidDataException.getMessage());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_PROFILE_GROUP_NOT_IN_LIST);
            return;
        }

        if (groupName != null) {
            boolean duplicateName = existingGroupWithTheSameName(
                manager.getProfile().getGroupList(), groupName, groupId);
            if (duplicateName) {
                ui.printlnMessage(Message.ERROR_GROUPEDIT_GROUP_NAME_DUPLICATE);
                ui.printlnMessageWithDivider(COMMAND_NO_EDITS_MADE);
                return;
            } else if (!groupName.equalsIgnoreCase(group.getGroupName())) {
                editedGroupName = true;
            }
        }

        if (involvedList != null) {
            boolean hasDuplicates = PersonList.hasNameDuplicates(involvedList);
            if (hasDuplicates) {
                ui.printlnMessage(Message.ERROR_GROUPEDIT_DUPLICATE_NAME_IN_GROUP);
                Manager.getLogger().log(Level.FINEST, Message.LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_EDITGROUP);
                ui.printlnMessageWithDivider(COMMAND_NO_EDITS_MADE);
                return;
            }

            ArrayList<Person> oldList = group.getPersonList();
            PersonList newList = new PersonList(involvedList);
            if (newList.isSamePersonList(oldList)) {
                ui.printlnMessage(Message.ERROR_GROUPEDIT_SAME_PERSON_LIST);
            } else {
                editedPersonList = true;
            }
        }

        if (groupName != null && groupName.equalsIgnoreCase(group.getGroupName()) && !editedPersonList) {
            ui.printlnMessage(Message.ERROR_GROUPEDIT_GROUP_NAME_NOT_NEW);
        }

        if (editedGroupName) {
            group.setGroupName(groupName);
        }
        if (editedPersonList) {
            PersonList newPersonList = new PersonList(involvedList);
            group.setPersonList(newPersonList);
        }

        boolean isGroupEdited = (editedGroupName || editedPersonList);
        if (isGroupEdited) {
            ui.printlnMessageWithDivider(COMMAND_SUCCESS + "\n" + group);
        } else {
            ui.printlnMessageWithDivider(COMMAND_NO_EDITS_MADE);
            return;
        }
        manager.saveProfile();
    }
}

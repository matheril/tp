package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

import java.util.logging.Level;

/**
 * Represents a command which deletes a Group object from a list of groups managed by the Profile object.
 *
 * @author Tianle
 */
public class GroupDeleteCommand extends Command {

    private static final String COMMAND_SUCCESS = "The group was deleted successfully.";

    private int groupId;

    /**
     * Initializes a GroupDeleteCommand object.
     *
     * @param groupId An integer that represents the group unique identifier for the group to be deleted.
     */
    public GroupDeleteCommand(int groupId) {
        assert groupId > 0 : Message.ASSERT_GROUPDELETE_GROUP_ID_NOT_INITIALIZED;
        this.groupId = groupId;
    }

    /**
     * Runs the command to delete a Group object from the list of groups managed by a Manager Object.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage object.
     */
    @Override
    public void run(Manager manager) {
        TextUI ui = manager.getUi();
        try {
            manager.getProfile().removeGroup(groupId);
            manager.saveProfile();
            ui.printlnMessageWithDivider(COMMAND_SUCCESS);
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_GROUPDELETE_GROUP_REMOVED + groupId);
        } catch (InvalidDataException e) {
            ui.printlnMessage(e.getMessage());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_GROUPDELETE_GROUP_REMOVED_FAILED + groupId);
        }
    }
}

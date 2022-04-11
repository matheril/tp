package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

import java.util.logging.Level;

/**
 * Represents a command which deletes an Activity object from a Session object.
 *
 * @author Ivan
 */
public class ActivityDeleteCommand extends Command {

    private static final String COMMAND_SUCCESS = "The activity was deleted successfully.";
    
    private final int sessionId;
    private final int activityId;

    /**
     * Initializes an ActivityDeleteCommand object.
     *
     * @param sessionId  An integer that uniquely identifies a session.
     * @param activityId An integer that uniquely identifies an activity.
     */
    public ActivityDeleteCommand(int sessionId, int activityId) {
        assert sessionId > 0 : Message.ASSERT_ACTIVITYDELETE_SESSION_ID_NOT_INITIALIZED;
        assert activityId > 0 : Message.ASSERT_ACTIVITYDELETE_ACTIVITY_ID_NOT_INITIALIZED;
        this.sessionId = sessionId;
        this.activityId = activityId;
    }

    /**
     * Runs the command to delete an Activity object from the list of activities in a Session object
     * managed by the Profile object.
     * Gets the Session object using a session unique identifier.
     * Requests for confirmation from user to delete the Activity object.
     * If user confirms, proceeds to remove activity from a Session object,
     * the command aborts otherwise.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage object.
     */
    @Override
    public void run(Manager manager) {
        TextUI ui = manager.getUi();
        try {
            Session session = manager.getProfile().getSession(sessionId);
            assert session != null : Message.ASSERT_ACTIVITYDELETE_SESSION_IS_NULL;
            session.removeActivity(activityId);
            assert !session.hasActivity(activityId) : Message.ASSERT_ACTIVITYDELETE_ACTIVITY_NOT_DELETED;
            manager.saveProfile();
            ui.printlnMessageWithDivider(COMMAND_SUCCESS);
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYDELETE_ACTIVITY_REMOVED + activityId);
        } catch (InvalidDataException e) {
            ui.printlnMessage(e.getMessage());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYDELETE_ACTIVITY_REMOVE_FAILED + activityId);
        }
    }
}

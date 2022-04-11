package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;

import java.util.logging.Level;

/**
 * Represents a command which displays the details of each Activity object within a Session object.
 *
 * @author Tianle
 */
public class ActivityListCommand extends Command {

    private final int sessionId;

    /**
     * Initializes an ActivityListCommand object.
     *
     * @param sessionId An integer that uniquely identifies a session.
     */
    public ActivityListCommand(int sessionId) {
        assert sessionId > 0 : Message.ASSERT_ACTIVITYLIST_SESSION_ID_LESS_THAN_ONE;
        this.sessionId = sessionId;
    }

    /**
     * Runs the command to list all existing activities in a Session object managed by the Profile Object.
     * 
     * @param manager A Manager object that manages the TextUI, Profile and Storage object.
     */
    @Override
    public void run(Manager manager) {
        try {
            Session sessionToBePrinted = manager.getProfile().getSession(sessionId);
            manager.getUi().printlnMessage(sessionToBePrinted.getActivityListSummaryString());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYLIST_ACTIVITIES_LISTED + sessionId);
        } catch (InvalidDataException e) {
            manager.getUi().printlnMessage(e.getMessage());
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYLIST_SESSION_ID_NOT_FOUND + sessionId);
        }
    }
}

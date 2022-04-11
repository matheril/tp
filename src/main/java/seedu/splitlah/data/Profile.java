package seedu.splitlah.data;

import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TableFormatter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a profile that manages a list of sessions and keeps track of unique session and activity
 * identifiers for the SplitLah application.
 * 
 * @author Roy
 */
public class Profile implements Serializable {

    private ArrayList<Session> sessionList;
    private ArrayList<Group> groupList;
    private int sessionIdTracker;
    private int activityIdTracker;
    private int groupIdTracker;

    private static final String SESSION_LIST_HEADER = "List of Sessions";
    private static final String[] SESSION_LIST_COLS = {"#","Name", "Date","# of Participants","# of Activities"};
    private static final String GROUP_LIST_HEADER = "List of Groups";
    private static final String[] GROUP_LIST_COLS = { "#", "Name", "Number of persons" };

    /**
     * Initializes a Profile object.
     */
    public Profile() {
        this.sessionList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.sessionIdTracker = 1;
        this.activityIdTracker = 1;
        this.groupIdTracker = 1;
    }

    /**
     * Checks if there is a Session object that exists with the same name.
     *
     * @param sessionName A String object that represents the session name.
     * @return true if a session exists with the same name,
     *         false otherwise.
     */
    public boolean hasSessionName(String sessionName) {
        if (sessionList.isEmpty()) {
            return false;
        }
        for (Session session : sessionList) {
            if (session.getSessionName().equalsIgnoreCase(sessionName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a Session object that exists with the specified session unique identifier.
     *
     * @param sessionId An integer that uniquely identifies a Session object.
     * @return true if a session exists with the specified session unique identifier,
     *         false otherwise.
     */
    public boolean hasSessionId(int sessionId) {
        if (sessionList.isEmpty()) {
            return false;
        }
        for (Session session : sessionList) {
            if (session.getSessionId() == sessionId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the Session object via the session unique identifier specified by the user.
     *
     * @param sessionId An integer that uniquely identifies a Session object.
     * @return A Session object with the matching session unique identifier.
     * @throws InvalidDataException If there are no sessions stored or
     *                              if the session unique identifier specified was not found.
     */
    public Session getSession(int sessionId) throws InvalidDataException {
        if (sessionList.isEmpty()) {
            throw new InvalidDataException(Message.ERROR_PROFILE_SESSION_LIST_EMPTY);
        }
        for (Session session : sessionList) {
            if (session.getSessionId() == sessionId) {
                return session;
            }
        }
        throw new InvalidDataException(Message.ERROR_PROFILE_SESSION_NOT_IN_LIST);
    }

    /**
     * Adds a Session object to the list of sessions.
     *
     * @param session A Session object that is to be added.
     */
    public void addSession(Session session) {
        sessionList.add(session);
    }

    /**
     * Removes a Session object from the list of sessions.
     *
     * @param sessionId An integer that uniquely identifies a Session object to be removed.
     */
    public void removeSession(int sessionId) throws InvalidDataException {
        Session sessionToBeRemoved = getSession(sessionId);
        sessionList.remove(sessionToBeRemoved);
    }

    public ArrayList<Session> getSessionList() {
        return sessionList;
    }

    /**
     * Returns a String object containing a summary of the state of the member attribute sessionList.
     *
     * @return A String object containing a summary of all Session objects in sessionList or
     *         a message stating that the sessionList is empty if there are no Session objects within.
     */
    public String getSessionListSummaryString() {
        if (sessionList.isEmpty()) {
            return Message.ERROR_PROFILE_SESSION_LIST_EMPTY;
        }

        TableFormatter summaryTable = new TableFormatter(
                SESSION_LIST_COLS[0], SESSION_LIST_COLS[1], SESSION_LIST_COLS[2],
                SESSION_LIST_COLS[3], SESSION_LIST_COLS[4]
        );
        summaryTable.addTableName(SESSION_LIST_HEADER);
        sessionList.sort(Session::compareTo);

        for (Session session : sessionList) {
            String id = Integer.toString(session.getSessionId());
            String name = session.getSessionName();
            String date = session.getDateCreated().format(ParserUtils.DATE_FORMAT);
            String numParticipants = Integer.toString(session.getPersonArrayList().size());
            String numActivities = Integer.toString(session.getActivityList().size());
            summaryTable.addRow(id, name, date, numParticipants, numActivities);
        }
        return summaryTable.toString();
    }

    /**
     * Checks if there is a Group object that exists with the same name.
     *
     * @param groupName A String object that represents the group name.
     * @return true if a group exists with the same name,
     *         false otherwise.
     */
    public boolean hasGroupName(String groupName) {
        if (groupList.isEmpty()) {
            return false;
        }
        for (Group group : groupList) {
            if (group.getGroupName().equalsIgnoreCase(groupName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a Group object that exists with the specified group unique identifier.
     *
     * @param groupId An integer that uniquely identifies a group.
     * @return true if a group exists with the specified group unique identifier
     *         false otherwise.
     */
    public boolean hasGroupId(int groupId) {
        if (groupList.isEmpty()) {
            return false;
        }
        for (Group group : groupList) {
            if (group.getGroupId() == groupId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the Group object via the group unique identifier specified by the user.
     *
     * @param groupId An integer that uniquely identifies a Group object.
     * @return A Group object with the matching group unique identifier.
     * @throws InvalidDataException If there are no groups stored or
     *                              if the group unique identifier specified was not found.
     */
    public Group getGroup(int groupId) throws InvalidDataException {
        if (groupList.isEmpty()) {
            throw new InvalidDataException(Message.ERROR_PROFILE_GROUP_LIST_EMPTY);
        }
        for (Group group : groupList) {
            if (group.getGroupId() == groupId) {
                return group;
            }
        }
        throw new InvalidDataException(Message.ERROR_PROFILE_GROUP_NOT_IN_LIST);
    }

    /**
     * Adds a Group object to the list of groups.
     *
     * @param group A Group object that is to be added.
     */
    public void addGroup(Group group) {
        groupList.add(group);
    }

    /**
     * Removes a Group object with the specified group unique identifier from the list of groups.
     * At the same time, loops through existing sessions to update their group attribute to null
     * if the group attribute is the group to be removed.
     *
     * @param groupId An integer that uniquely identifies a Group object to be removed.
     */
    public void removeGroup(int groupId) throws InvalidDataException {
        Group groupToBeRemoved = getGroup(groupId);
        for (Session session : sessionList) {
            Group groupToCheck = session.getGroup();
            if (groupToCheck != null && groupToCheck.getGroupId() == groupId) {
                session.setGroup(null);
            }
        }
        groupList.remove(groupToBeRemoved);
    }

    public ArrayList<Group> getGroupList() {
        return groupList;
    }

    /**
     * Returns a String object containing a summary of the state of the member attribute groupList.
     *
     * @return A String object containing a summary of all Group objects in groupList or
     *         a message stating that the groupList is empty if there are no Group objects within.
     */
    public String getGroupListSummaryString() {
        if (groupList.isEmpty()) {
            return Message.ERROR_PROFILE_GROUP_LIST_EMPTY;
        }

        TableFormatter summaryTable = new TableFormatter(
                GROUP_LIST_COLS[0], GROUP_LIST_COLS[1], GROUP_LIST_COLS[2]
        );
        summaryTable.addTableName(GROUP_LIST_HEADER);
        for (Group group : groupList) {
            String id = Integer.toString(group.getGroupId());
            String name = group.getGroupName();
            String count = Integer.toString(group.getPersonCount());
            summaryTable.addRow(id, name, count);
        }
        return summaryTable.toString();
    }

    /**
     * Returns a new session unique identifier for Session object to be created.
     * Assumption: Function is called when a new Session object is being created without errors.
     *
     * @return An integer that represents the new session unique identifier.
     */
    public int getNewSessionId() {
        int newSessionId = sessionIdTracker;
        sessionIdTracker += 1;
        return newSessionId;
    }

    public int getSessionIdTracker() {
        return sessionIdTracker;
    }

    /**
     * Returns a new activity unique identifier for Activity object to be created.
     * Assumption: Function is called when a new Activity object is being created without errors.
     *
     * @return An integer that represents the new activity unique identifier.
     */
    public int getNewActivityId() {
        int newActivityId = activityIdTracker;
        activityIdTracker += 1;
        return newActivityId;
    }

    public int getActivityIdTracker() {
        return activityIdTracker;
    }

    /**
     * Returns a new group unique identifier for Group object to be created.
     * Assumption: Function is called when a new Group object is being created without errors.
     *
     * @return An integer that represents the new group unique identifier.
     */
    public int getNewGroupId() {
        int newGroupId = groupIdTracker;
        groupIdTracker += 1;
        return newGroupId;
    }

    public int getGroupIdTracker() {
        return groupIdTracker;
    }
}

package seedu.splitlah.data;

import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.ParserUtils;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TableFormatter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Represents a group outing session which contains a list of activities and is participated by a group of participants.
 *
 * @author Warren
 */
public class Session implements Serializable, Comparable<Session> {

    private String sessionName;
    private int sessionId;
    private LocalDate dateCreated;
    private ArrayList<Activity> activityList;
    private PersonList personList;
    private Group group;

    // CONSTANTS
    private static final String[] ACTIVITY_LIST_COLS = { "#", "Activities", "Cost", "Payer" };
    private static final String PERSON_LIST_HEADER = "Participants:";
    private static final String COST_FORMATTING = "%.2f";
    private static final String COST_PREPEND = "$";
    private static final int ZERO_INDEXING_OFFSET = 1;

    /**
     * Initializes a Session object.
     *
     * @param sessionName A String object that represents the name of the session.
     * @param sessionId   An integer that uniquely identifies a session.
     * @param dateCreated A LocalDate object storing the date that the session occurs on.
     * @param personList  An ArrayList object of Person objects representing participants of the session.
     * @param group       A Group object representing a group of persons participating in the session.
     * @see Profile#getNewSessionId() for issuing a unique sessionId
     */
    public Session(String sessionName, int sessionId, LocalDate dateCreated, PersonList personList,
                   Group group) {
        assert personList != null : Message.ASSERT_SESSION_PERSON_LIST_EMPTY;
        assert personList.getSize() != 0 : Message.ASSERT_SESSION_PERSON_LIST_EMPTY;
        this.sessionName = sessionName;
        this.sessionId = sessionId;
        this.dateCreated = dateCreated;
        this.personList = personList;
        this.activityList = new ArrayList<>();
        this.group = group;
    }
    
    public String getSessionName() {
        return sessionName;
    }

    public int getSessionId() {
        return sessionId;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public ArrayList<Activity> getActivityList() {
        return activityList;
    }

    public ArrayList<Person> getPersonArrayList() {
        return personList.getPersonList();
    }

    public PersonList getPersonList() {
        return personList;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    /**
     * Checks whether the Session object has an Activity object with the queried unique identifier.
     * 
     * @param activityId An integer that uniquely identifies an activity.
     * @return true if the Session object has an Activity object with a unique identifier matching the query,
     *         false otherwise.
     */
    public boolean hasActivity(int activityId) {
        if (activityList.isEmpty()) {
            return false;
        }
        
        for (Activity activity : activityList) {
            if (activity.getActivityId() == activityId) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an Activity object specified by a numerical identifier that uniquely identifies the activity.
     *
     * @param activityId An integer that uniquely identifies an activity.
     * @return An Activity object in the Session class specified by activityId
     * @throws InvalidDataException If activityList is empty or
     *                              if activityList does not contain an Activity object with the specified activityId
     */
    public Activity getActivity(int activityId) throws InvalidDataException {
        if (activityList.isEmpty()) {
            throw new InvalidDataException(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST);
        }

        for (Activity activity : activityList) {
            if (activity.getActivityId() == activityId) {
                return activity;
            }
        }
        throw new InvalidDataException(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST);
    }

    /**
     * Removes all ActivityCost objects from all Person objects involved in and paying for the activity.
     * 
     * @param activity An Activity object in which all involved Person objects should have their ActivityCost objects
     *                 with the same activity unique identifier removed.
     * @throws InvalidDataException If any Person object involved in the Activity object does not have any ActivityCost
     *                              objects with the same activity unique identifier.
     */
    private void removeActivityCosts(Activity activity) throws InvalidDataException {
        int activityId = activity.getActivityId();
        ArrayList<Person> involvedPersonList = activity.getInvolvedPersonList();
        Person payer = activity.getPersonPaid();
        if (involvedPersonList != null) {
            boolean isPayerInParticipantList = involvedPersonList.contains(payer);
            for (Person person : involvedPersonList) {
                Manager.getLogger().log(Level.FINEST, Message.LOGGER_SESSION_ACTIVITYCOST_REMOVAL + person.getName());
                person.removeActivityCost(activityId);
            }
            if (!isPayerInParticipantList) {
                Manager.getLogger().log(Level.FINEST, Message.LOGGER_SESSION_ACTIVITYCOST_REMOVAL + payer.getName());
                payer.removeActivityCost(activityId);
            }
        }
    }

    /**
     * Removes an Activity object specified by a numerical identifier that uniquely identifies the activity
     * from the Session. Additionally, removes all ActivityCost objects with the same activityId from all Person
     * objects involved in and paying for the activity.
     *
     * @param activityId An integer that uniquely identifies an activity.
     * @throws InvalidDataException If activityList is empty or
     *                              if activityList does not contain an Activity object with the specified activityId
     */
    public void removeActivity(int activityId) throws InvalidDataException {
        Manager.getLogger().log(Level.FINEST, Message.LOGGER_SESSION_ACTIVITY_REMOVAL + activityId);
        if (activityList.isEmpty()) {
            throw new InvalidDataException(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST);
        }

        Activity deleteTarget = null;
        for (Activity activity : activityList) {
            if (activity.getActivityId() == activityId) {
                deleteTarget = activity;
                break;
            }
        }

        if (deleteTarget == null) {
            throw new InvalidDataException(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST);
        }

        removeActivityCosts(deleteTarget);
        activityList.remove(deleteTarget);
    }

    public void addActivity(Activity activity) {
        assert activity != null : Message.ASSERT_SESSION_ACTIVITY_NULL;
        activityList.add(activity);
        activityList.sort(Activity::compareTo);
    }

    /**
     * Returns a Person object with a name that matches the queried name, ignoring case.
     *
     * @param name A String object that represents a query name used to search for a Person object.
     * @return A Person object in the Session class that has a matching name.
     * @throws InvalidDataException If no Person object in personList matches queried name.
     */
    public Person getPersonByName(String name) throws InvalidDataException {
        assert name != null : Message.ASSERT_SESSION_NAME_NULL;
        for (Person person : personList.getPersonList()) {
            if (person.getName().equalsIgnoreCase(name)) {
                return person;
            }
        }
        throw new InvalidDataException(Message.ERROR_SESSION_PERSON_NOT_IN_LIST);
    }

    /**
     * Returns an ArrayList of Person objects with names that match the provided name list, ignoring case.
     *
     * @param nameList An array of String objects that represent names of people in the session.
     * @return An ArrayList object containing Person objects with matching names.
     * @throws InvalidDataException If any name in nameList does not match a Person object in personList.
     */
    public ArrayList<Person> getPersonListByName(String[] nameList) throws InvalidDataException {
        assert nameList != null && nameList.length != 0 : Message.ASSERT_SESSION_NAME_LIST_EMPTY;
        ArrayList<Person> personList = new ArrayList<>();
        for (String name : nameList) {
            Person newPerson = getPersonByName(name);
            personList.add(newPerson);
        }
        return personList;
    }

    public void addPerson(Person person) {
        personList.addPerson(person);
    }

    /**
     * Returns a String object containing a summary of the state of the member attribute activityList.
     *
     * @return A String object containing a summary of all Activity objects in activityList or
     *         a message stating that the activityList is empty if there are no Activity objects within.
     */
    public String getActivityListSummaryString() {
        if (activityList.isEmpty()) {
            return Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST;
        }

        TableFormatter summaryTable = new TableFormatter(
                ACTIVITY_LIST_COLS[0], ACTIVITY_LIST_COLS[1], ACTIVITY_LIST_COLS[2], ACTIVITY_LIST_COLS[3]
        );
        for (Activity activity : activityList) {
            String id = Integer.toString(activity.getActivityId());
            String name = activity.getActivityName();
            String cost = COST_PREPEND + String.format(COST_FORMATTING, activity.getTotalCost());
            String payer = activity.getPersonPaid().getName();
            summaryTable.addRow(id, name, cost, payer);
        }
        return summaryTable.toString();
    }

    /**
     * Returns a String object containing a summary of the state of the member attribute personList.
     *
     * @return A String object containing a summary of all Person objects in personList or
     *         a message stating that the personList is empty if there are no Person objects within.
     */
    private String getPersonListSummaryString() {
        assert personList != null : Message.ASSERT_SESSION_PERSON_LIST_EMPTY;
        assert personList.getSize() != 0 : Message.ASSERT_SESSION_PERSON_LIST_EMPTY;

        StringBuilder summaryString = new StringBuilder(PERSON_LIST_HEADER);
        for (int i = 0; i < personList.getSize(); i++) {
            String personName = personList.getPerson(i).getName();
            summaryString.append("\n ").append(i + ZERO_INDEXING_OFFSET).append(". ").append(personName);
        }
        return summaryString.toString();
    }

    /**
     * Returns a String object containing a summary of the state of the member attribute group.
     *
     * @return A String object containing the name of the Group object if group is not null or
     *         a message stating that there is no group in the session if group is null.
     */
    private String getGroupSummaryString() {
        if (group == null) {
            return "Group: None";
        } else {
            return "Group: " + group.getGroupName();
        }
    }

    /**
     * Returns an integer to identify whether this Session object should be ordered
     * before or after another Session object when sorted.
     *
     * @param session The specified Session object that this Session object is compared against.
     * @return An integer less than 0 if this Session object's sessionId is smaller than the specified
     *         Session object's sessionId,
     *         an integer greater than 0 if this object's sessionId is larger,
     *         and 0 if both Session objects' sessionIds are numerically equal.
     */
    @Override
    public int compareTo(Session session) {
        assert session != null : Message.ASSERT_SESSION_COMPARED_SESSION_NULL;
        return Integer.compare(sessionId, session.getSessionId());
    }
    
    /**
     * Returns a String object describing the state of the Session object.
     *
     * @return A String object containing a description of the Session object and its member attributes.
     */
    @Override
    public String toString() {
        return "Session Id #" + sessionId + " --\n"
                + "Name: " + sessionName + '\n'
                + "Date: " + dateCreated.format(ParserUtils.DATE_FORMAT) + '\n'
                + getGroupSummaryString() + '\n'
                + getPersonListSummaryString() + '\n'
                + getActivityListSummaryString();
    }
}

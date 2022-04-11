package seedu.splitlah.command;

import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Person;
import seedu.splitlah.data.PersonList;
import seedu.splitlah.data.Profile;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

import java.time.LocalDate;
import java.util.logging.Level;

/**
 * Represents a command which edits an existing Session object from a list of sessions managed by the Profile object.
 *
 * @author Roy
 */
public class SessionEditCommand extends Command {

    private static final String COMMAND_SUCCESS = "The session was edited successfully.";
    private static final String COMMAND_NO_EDITS_MADE = "The session was not edited.";

    private final int sessionId;
    private final String sessionName;
    private final String[] personNames;
    private final LocalDate sessionDate;
    private LocalDate oldSessionDate;

    /**
     * Initializes a SessionEditCommand object.
     *
     * @param sessionId   An integer that uniquely identifies a session.
     * @param sessionName A String object that represents the session name.
     * @param personNames An array of String objects that represents the involved persons for the session.
     * @param date        A LocalDate object that represents the date of the session.
     */
    public SessionEditCommand(int sessionId, String sessionName, String[] personNames, LocalDate date) {
        assert sessionId > 0 : Message.ASSERT_SESSIONEDIT_SESSION_ID_INVALID;
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.personNames = personNames;
        this.sessionDate = date;
    }

    /**
     * Returns the new list of persons if all checks are passed.
     *
     * @param oldPersonList A PersonList object that represents the PersonList of the session to be edited.
     * @return A PersonList object represents the new PersonList for a session.
     * @throws InvalidDataException If there were duplicates in the new personNames,
     *                              if there were invalid names in the new personNames,
     *                              if there were missing existing names in the new personList.
     */
    private PersonList getNewPersonList(PersonList oldPersonList) throws InvalidDataException {
        boolean hasDuplicates = PersonList.hasNameDuplicates(personNames);
        if (hasDuplicates) {
            throw new InvalidDataException(Message.ERROR_PERSONLIST_DUPLICATE_NAME_IN_SESSION);
        }
        PersonList newPersonList = new PersonList(personNames);
        if (personNames.length != newPersonList.getSize()) {
            throw new InvalidDataException(Message.ERROR_PERSONLIST_CONTAINS_INVALID_NAME);
        }
        if (!newPersonList.isSuperset(oldPersonList.getPersonList())) {
            throw new InvalidDataException(Message.ERROR_SESSIONEDIT_INVALID_PERSONLIST);
        }
        return newPersonList;
    }

    /**
     * Returns the new session name if all checks are passed.
     *
     * @param oldSessionName A String that represents the name of the session to be edited.
     * @param profile        A Profile object that contains the list of session.
     * @return A String that represents the new session name.
     * @throws InvalidDataException If the new session name exists within the list of session other than itself.
     */
    private String getNewSessionName(String oldSessionName, Profile profile) throws InvalidDataException {
        boolean isSessionExists = profile.hasSessionName(sessionName);
        boolean hasSameSessionName = oldSessionName.equalsIgnoreCase(sessionName);
        if (!hasSameSessionName && isSessionExists) {
            throw new InvalidDataException(Message.ERROR_PROFILE_DUPLICATE_SESSION);
        }
        return sessionName;
    }

    /**
     * Checks if there were edits to the existing session.
     *
     * @param session A Session object that represents the session to be edited.
     * @return true if any of the editable fields were edited,
     *         false otherwise.
     */
    private boolean hasSessionEdited(Session session) {
        if (sessionName != null && !sessionName.equalsIgnoreCase(session.getSessionName())) {
            return true;
        }
        if (sessionDate != null && !sessionDate.equals(oldSessionDate)) {
            return true;
        }
        if (personNames != null) {
            PersonList newPersonList = new PersonList(personNames);
            return !newPersonList.isSamePersonList(session.getPersonArrayList());
        }
        return false;
    }

    /**
     * Runs the command to edit an existing Session object from the list of sessions managed by a Manager Object.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage object.
     */
    @Override
    public void run(Manager manager) {
        TextUI ui = manager.getUi();
        Profile profile = manager.getProfile();
        Session session;
        PersonList newPersonList = null;
        String newSessionName = null;
        try {
            session = profile.getSession(sessionId);
            if (personNames != null) {
                newPersonList = getNewPersonList(session.getPersonList());
            }
            if (sessionName != null) {
                newSessionName = getNewSessionName(session.getSessionName(), profile);
            }
            if (sessionDate != null) {
                oldSessionDate = session.getDateCreated();
                session.setDateCreated(sessionDate);
            }
        } catch (InvalidDataException invalidDataException) {
            ui.printlnMessage(invalidDataException.getMessage());
            return;
        }
        boolean isSessionEdited = hasSessionEdited(session);
        if (newPersonList != null) {
            for (Person person : newPersonList.getPersonList()) {
                session.addPerson(person);
            }
        }
        if (newSessionName != null) {
            session.setSessionName(newSessionName);
        }
        if (isSessionEdited) {
            ui.printlnMessageWithDivider(COMMAND_SUCCESS + "\n" + session);
        } else {
            ui.printlnMessage(COMMAND_NO_EDITS_MADE);
        }
        manager.saveProfile();
        Manager.getLogger().log(Level.FINEST, Message.LOGGER_SESSIONEDIT_SESSION_EDITED);
    }
}

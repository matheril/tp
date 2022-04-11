package seedu.splitlah.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.command.Command;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;
import seedu.splitlah.ui.Message;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class SessionTest {

    Manager manager = new Manager();
    Session sessionOne;
    Session sessionTwo;
    Session sessionThree;

    private static final int TEST_SESSION_ONE = 1;
    private static final int TEST_SESSION_TWO = 2;
    private static final int TEST_SESSION_THREE = 3;
    private static final int TEST_ACTIVITY_ONE = 1;
    private static final int TEST_ACTIVITY_TWO = 2;
    private static final String CREATE_TEST_SESSION_INPUT_ONE =
            "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
    private static final String CREATE_TEST_SESSION_INPUT_TWO =
            "session /create /n Group lunch /d 17-03-2022 /pl Alice Bob";
    private static final String CREATE_TEST_SESSION_INPUT_THREE =
            "session /create /n Games at home /d 19-03-2022 /gid 1";
    private static final String CREATE_TEST_ACTIVITY_INPUT_ONE =
            "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15";
    private static final String CREATE_TEST_ACTIVITY_INPUT_TWO =
            "activity /create /sid 1 /n Dinner /p Alice /i Bob Charlie /co 30";
    private static final String CREATE_TEST_ACTIVITY_INPUT_THREE =
            "activity /create /sid 3 /n Order in /p Bob /i Bob Charlie /cl 15.49 14.49";
    private static final String CREATE_TEST_GROUP_INPUT =
            "group /create /n Besties /pl Bob Charlie";

    /**
     * Creates a session that is stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        Command createGroupCommand = Parser.getCommand(CREATE_TEST_GROUP_INPUT);
        createGroupCommand.run(manager);
        Command createSessionCommandOne = Parser.getCommand(CREATE_TEST_SESSION_INPUT_ONE);
        Command createSessionCommandTwo = Parser.getCommand(CREATE_TEST_SESSION_INPUT_TWO);
        Command createSessionCommandThree = Parser.getCommand(CREATE_TEST_SESSION_INPUT_THREE);
        createSessionCommandOne.run(manager);
        createSessionCommandTwo.run(manager);
        createSessionCommandThree.run(manager);

        try {
            sessionOne = manager.getProfile().getSession(TEST_SESSION_ONE);
            sessionTwo = manager.getProfile().getSession(TEST_SESSION_TWO);
            sessionThree = manager.getProfile().getSession(TEST_SESSION_THREE);
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    // hasActivity()

    /**
     * Checks if false is returned when hasActivity method is run and no Activity objects exists in the Session object.
     */
    @Test
    void hasActivity_noActivityExists_false() {
        boolean hasActivity = sessionOne.hasActivity(TEST_ACTIVITY_ONE);
        assertFalse(hasActivity);
    }

    /**
     * Checks if false is returned when hasActivity method is run and no Activity object with an activity unique
     * identifier matching the provided identifier is found in the Session object.
     */
    @Test
    void hasActivity_activityWithSpecifiedIdDoesNotExist_false() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);
        boolean hasActivity = sessionOne.hasActivity(TEST_ACTIVITY_TWO);
        assertFalse(hasActivity);
    }

    /**
     * Checks if true is returned when hasActivity method is run and Activity objects with activity unique identifiers
     * matching the provided identifiers are found in the Session object.
     */
    @Test
    void hasActivity_activityExists_true() {
        // Pretesting
        boolean hasActivityOne = sessionOne.hasActivity(TEST_ACTIVITY_ONE);
        boolean hasActivityTwo = sessionOne.hasActivity(TEST_ACTIVITY_TWO);
        assertFalse(hasActivityOne);
        assertFalse(hasActivityTwo);
        
        // 1 activity only
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommandOne.run(manager);
        hasActivityOne = sessionOne.hasActivity(TEST_ACTIVITY_ONE);
        assertTrue(hasActivityOne);
        assertFalse(hasActivityTwo);
        
        // 2 activities
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandTwo.run(manager);
        hasActivityTwo = sessionOne.hasActivity(TEST_ACTIVITY_TWO);
        assertTrue(hasActivityOne);
        assertTrue(hasActivityTwo);
    }
    
    // getActivity()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the getActivity method is
     * called and no Activity objects exists in the Session object.
     */
    @Test
    void getActivity_noActivityExists_InvalidDataExceptionThrown() {
        try {
            Activity activity = sessionOne.getActivity(TEST_ACTIVITY_ONE);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the getActivity method is
     * called and no Activity objects with the specified activity unique identifier exists in the Session object.
     */
    @Test
    void getActivity_activityWithSpecifiedIdDoesNotExist_InvalidDataExceptionThrown() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);

        try {
            Activity activity = sessionOne.getActivity(TEST_ACTIVITY_TWO);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an Activity object is correctly returned when the getActivity method is
     * called and an Activity with the specified activity unique identifier exists in the Session object.
     */
    @Test
    void getActivity_activityExists_ActivityObjectReturned() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);

        try {
            Activity activity = sessionOne.getActivity(TEST_ACTIVITY_ONE);
            assertEquals(TEST_ACTIVITY_ONE, activity.getActivityId());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    // removeActivity()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the removeActivity method is
     * called and no Activity objects exists in the Session object.
     */
    @Test
    void removeActivity_noActivityExists_InvalidDataExceptionThrown() {
        try {
            sessionOne.removeActivity(TEST_ACTIVITY_ONE);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the removeActivity method is
     * called and no Activity objects with the specified activity unique identifier exists in the Session object.
     */
    @Test
    void removeActivity_activityWithSpecifiedIdDoesNotExist_InvalidDataExceptionThrown() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommand.run(manager);

        try {
            sessionOne.removeActivity(TEST_ACTIVITY_TWO);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an Activity object and all ActivityCost objects related it are correctly removed from the
     * Session object when the removeActivity method is called, an Activity object with the specified
     * activity unique identifier exists in the Session object and the payer is also involved in the activity.
     */
    @Test
    void removeActivity_activityExistsAndPayerIsInvolved_activityAndActivityCostRemoved() {
        // Activity 1: Payer is involved
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        // Activity 2: Payer is not involved
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandOne.run(manager);
        createActivityCommandTwo.run(manager);
        
        try {
            sessionOne.removeActivity(TEST_ACTIVITY_ONE);
        } catch (InvalidDataException exception) {
            fail();
        }

        // Check if Activity object still exists (Payer is involved)
        try {
            sessionOne.getActivity(TEST_ACTIVITY_ONE);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }

        // Check if ActivityCost objects still exists (Payer is involved)
        ArrayList<Person> personList = sessionOne.getPersonArrayList();
        for (Person person : personList) {
            try {
                person.removeActivityCost(TEST_ACTIVITY_ONE);
            } catch (InvalidDataException exception) {
                String errorMessage = Message.ERROR_PERSON_ACTIVITY_NOT_FOUND + TEST_ACTIVITY_ONE;
                assertEquals(errorMessage, exception.getMessage());
            }
        }
    }

    /**
     * Checks if an Activity object and all ActivityCost objects related it are correctly removed from the
     * Session object when the removeActivity method is called, an Activity object with the specified
     * activity unique identifier exists in the Session object and the payer is not involved in the activity.
     */
    @Test
    void removeActivity_activityExistsAndPayerIsNotInvolved_activityAndActivityCostRemoved() {
        // Activity 1: Payer is involved
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        // Activity 2: Payer is not involved
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandOne.run(manager);
        createActivityCommandTwo.run(manager);

        try {
            sessionOne.removeActivity(TEST_ACTIVITY_TWO);
        } catch (InvalidDataException exception) {
            fail();
        }

        // Check if Activity object still exists (Payer is not involved)
        try {
            sessionOne.getActivity(TEST_ACTIVITY_TWO);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_ACTIVITY_ID_NOT_IN_LIST, exception.getMessage());
        }

        // Check if ActivityCost objects still exists (Payer is not involved)
        ArrayList<Person> personList = sessionOne.getPersonArrayList();
        for (Person person : personList) {
            try {
                person.removeActivityCost(TEST_ACTIVITY_TWO);
            } catch (InvalidDataException exception) {
                String errorMessage = Message.ERROR_PERSON_ACTIVITY_NOT_FOUND + TEST_ACTIVITY_TWO;
                assertEquals(errorMessage, exception.getMessage());
            }
        }
    }

    // getPersonByName()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown when the getPersonByName method is
     * called and no Person objects with the specified name exists in the Session object.
     */
    @Test
    void getPersonByName_personWithSpecifiedNameDoesNotExist_InvalidDataExceptionThrown() {
        try {
            Person person = sessionOne.getPersonByName("David");
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_PERSON_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if a Person object is correctly returned when the getPersonByName method is
     * called and a Person object with the specified name exists in the Session object.
     */
    @Test
    void getPersonByName_personWithSpecifiedNameExists_PersonObjectReturned() {
        try {
            Person person = sessionOne.getPersonByName("Alice");
            assertEquals("Alice", person.getName());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    // getPersonListByName()

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown 
     * when the getPersonListByName method is called with a String array object containing a single name
     * and no Person objects with the specified name exists in the Session object.
     */
    @Test
    void getPersonListByName_singleInvalidName_InvalidDataExceptionThrown() {
        try {
            String[] nameList = { "David" };
            ArrayList<Person> personList = sessionOne.getPersonListByName(nameList);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_PERSON_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an InvalidDataException with the correct message is properly thrown 
     * when the getPersonListByName method is called with a String array object containing multiple names
     * and no Person objects were found for one of the specified names in the Session object.
     */
    @Test
    void getPersonListByName_multipleNamesWithOneInvalidName_InvalidDataExceptionThrown() {
        try {
            String[] nameList = { "Alice", "Bob", "David" };
            ArrayList<Person> personList = sessionOne.getPersonListByName(nameList);
            fail();
        } catch (InvalidDataException exception) {
            assertEquals(Message.ERROR_SESSION_PERSON_NOT_IN_LIST, exception.getMessage());
        }
    }

    /**
     * Checks if an ArrayList object containing a Person object corresponding to the only specified name is returned 
     * when the getPersonListByName method is called with a String array object containing a single name
     * and a Person object with the only specified name exists in the Session object.
     */
    @Test
    void getPersonListByName_singleValidName_ArrayListReturned() {
        try {
            String[] nameList = { "Alice" };
            ArrayList<Person> personList = sessionOne.getPersonListByName(nameList);
            assertEquals("Alice", personList.get(0).getName());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    /**
     * Checks if an ArrayList object containing Person objects corresponding to the given names is returned 
     * when the getPersonListByName method is called with a String array object containing multiple names
     * and a Person object with a matching name was found in the Session object for each of the given names.
     */
    @Test
    void getPersonListByName_multipleValidNames_ArrayListReturned() {
        try {
            String[] nameList = { "Alice", "Bob", "Charlie" };
            ArrayList<Person> personList = sessionOne.getPersonListByName(nameList);
            assertEquals(3, personList.size());
            assertEquals("Alice", personList.get(0).getName());
            assertEquals("Bob", personList.get(1).getName());
            assertEquals("Charlie", personList.get(2).getName());
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    /**
     * Checks if a String object containing an error message corresponding to an empty activity list in the session
     * is returned when the getActivityListSummaryString method is called and the Session object does not contain
     * any Activity objects.
     */
    @Test
    void getActivityListSummaryString_emptyActivityList_emptyActivityListErrorMessage() {
        String activityListSummaryString = sessionOne.getActivityListSummaryString();
        assertEquals(Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST, activityListSummaryString);
    }

    /**
     * Checks if a String object containing a properly formatted table that summarises the activity list of a session
     * is returned when the getActivityListSummaryString method is called and the Session object contains at least one
     * Activity objects.
     */
    @Test
    void getActivityListSummaryString_populatedActivityList_activityListTable() {
        final String expectedActivityListTable =
                "--------------------------------\n"
                        + "# | Activities | Cost   | Payer \n"
                        + "--------------------------------\n"
                        + "1 | Lunch      | $15.00 | Alice \n"
                        + "2 | Dinner     | $30.00 | Alice \n"
                        + "================================";
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandOne.run(manager);
        createActivityCommandTwo.run(manager);

        String activityListSummaryString = sessionOne.getActivityListSummaryString();
        assertEquals(expectedActivityListTable, activityListSummaryString);
    }

    /**
     * Checks if a negative integer is returned when a Session object with a smaller session unique identifier
     * is compared against a Session object with a larger session unique identifier.
     */
    @Test
    void compareTo_sessionComparedToHasBiggerId_negativeInteger() {
        int comparisonValue = sessionOne.compareTo(sessionTwo);
        assertTrue(comparisonValue < 0);
    }

    /**
     * Checks if a positive integer is returned when a Session object with a larger session unique identifier
     * is compared against a Session object with a smaller session unique identifier.
     */
    @Test
    void compareTo_sessionComparedToHasSmallerId_positiveInteger() {
        int comparisonValue = sessionTwo.compareTo(sessionOne);
        assertTrue(comparisonValue > 0);
    }

    /**
     * Checks if a String object representing the Session object is returned with the correct format
     * when the toString method is called for a Session object without a Group object.
     */
    @Test
    void toString_sessionWithoutGroup_returnsCorrectFormat() {
        String expectedFormat = "Session Id #2 --\n"
                + "Name: Group lunch\n"
                + "Date: 17-03-2022\n"
                + "Group: None\n"
                + "Participants:\n"
                + " 1. Alice\n"
                + " 2. Bob\n"
                + Message.ERROR_SESSION_EMPTY_ACTIVITY_LIST;
        assertEquals(expectedFormat, sessionTwo.toString());
    }

    /**
     * Checks if a String object representing the Session object is returned with the correct format
     * when the toString method is called for a Session object with a Group object.
     */
    @Test
    void toString_sessionWithGroup_returnsCorrectFormat() {
        Command createActivityCommand = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_THREE);
        createActivityCommand.run(manager);
        String expectedFormat = "Session Id #3 --\n"
                + "Name: Games at home\n"
                + "Date: 19-03-2022\n"
                + "Group: Besties\n"
                + "Participants:\n"
                + " 1. Bob\n"
                + " 2. Charlie\n"
                + "--------------------------------\n" 
                + "# | Activities | Cost   | Payer \n"
                + "--------------------------------\n"
                + "1 | Order in   | $29.98 | Bob   \n"
                + "================================";
        assertEquals(expectedFormat, sessionThree.toString());
    }
}

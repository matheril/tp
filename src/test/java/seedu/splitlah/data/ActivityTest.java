package seedu.splitlah.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.splitlah.command.Command;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ActivityTest {

    Manager manager = new Manager();
    Session session;
    Activity activityOne;
    Activity activityTwo;

    private static final int TEST_SESSION = 1;
    private static final int TEST_ACTIVITY_ONE = 1;
    private static final int TEST_ACTIVITY_TWO = 2;
    private static final String CREATE_TEST_SESSION_INPUT =
            "session /create /n Class outing /d 15-02-2022 /pl Alice Bob Charlie";
    private static final String CREATE_TEST_ACTIVITY_INPUT_ONE =
            "activity /create /sid 1 /n Lunch /p Alice /i Alice Bob Charlie /co 15 /gst 7 /sc 10";
    private static final String CREATE_TEST_ACTIVITY_INPUT_TWO =
            "activity /create /sid 1 /n Dinner /p Alice /i Alice Bob Charlie /co 30";
    private static final String ACTIVITY_ONE_STRING =
            "Activity Id #1 --\n"
                    + "Name:                     Lunch\n"
                    + "Id:                       1\n"
                    + "Payer:                    Alice\n"
                    + "GST:                      7.00%\n"
                    + "Service Charge(SC):       10.00%\n"
                    + "Cost(GST & SC inclusive): $17.66\n"
                    + "Involved: \n"
                    + "-------------------------\n"
                    + "# | Name    | Cost Owed \n"
                    + "-------------------------\n"
                    + "1 | Alice   | $5.89     \n"
                    + "2 | Bob     | $5.89     \n"
                    + "3 | Charlie | $5.89     \n"
                    + "=========================";

    /**
     * Creates a session and an activity that are stored and managed by the Manager object.
     */
    @BeforeEach
    void setUp() {
        Command createSessionCommand = Parser.getCommand(CREATE_TEST_SESSION_INPUT);
        createSessionCommand.run(manager);
        Command createActivityCommandOne = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_ONE);
        createActivityCommandOne.run(manager);
        Command createActivityCommandTwo = Parser.getCommand(CREATE_TEST_ACTIVITY_INPUT_TWO);
        createActivityCommandTwo.run(manager);

        try {
            session = manager.getProfile().getSession(TEST_SESSION);
            activityOne = session.getActivity(TEST_ACTIVITY_ONE);
            activityTwo = session.getActivity(TEST_ACTIVITY_TWO);
        } catch (InvalidDataException exception) {
            fail();
        }
    }

    /**
     * Checks if the correct activity unique identifier is returned.
     */
    @Test
    void getActivityId_activityOne_correctActivityId() {
        assertEquals(1, activityOne.getActivityId());
    }

    /**
     * Checks if the correct activity name is returned.
     */
    @Test
    void getActivityName_activityOne_correctActivityName() {
        assertEquals("Lunch", activityOne.getActivityName());
    }

    /**
     * Checks if the correct activity total cost is returned.
     */
    @Test
    void getTotalCost_activityOne_correctActivityTotalCost() {
        assertTrue(Math.abs(17.65 - activityOne.getTotalCost()) < 0.01);
    }

    /**
     * Checks if the correct person who paid for the activity is returned.
     */
    @Test
    void getPersonPaid_activityOne_correctPersonPaid() throws InvalidDataException {
        Person actualPersonPaid = session.getPersonByName("Alice");
        assertEquals(actualPersonPaid, activityOne.getPersonPaid());
    }

    /**
     * Checks if the correct activity gst is returned.
     */
    @Test
    void getGst_activityOne_correctGst() {
        assertEquals(7, activityOne.getGst());
    }

    /**
     * Checks if the correct activity service charge is returned.
     */
    @Test
    void getServiceCharge_activityOne_correctServiceCharge() {
        assertEquals(10, activityOne.getServiceCharge());
    }

    /**
     * Checks if the correct array list of persons involved in the activity is returned.
     */
    @Test
    void getInvolvedPersonList_activityOne_correctInvolvedPersonList() {
        String[] personNames = { "Alice", "Bob","Charlie" };
        PersonList personList = new PersonList(personNames);
        assertEquals(personList.getPersonList(), activityOne.getInvolvedPersonList());
    }

    /**
     * Checks if a negative integer is returned when an Activity object with a smaller activity unique identifier
     * is compared against an Activity object with a larger activity unique identifier.
     */
    @Test
    void compareTo_smallerActivityIdInput_returnsNegative() {
        int compareResults = activityOne.compareTo(activityTwo);
        assertTrue(compareResults < 0);
    }

    /**
     * Checks if a positive integer is returned when an Activity object with a smaller activity unique identifier
     * is compared against an Activity object with a larger activity unique identifier.
     */
    @Test
    void compareTo_smallerActivityIdInput_returnsPositive() {
        int compareResults = activityTwo.compareTo(activityOne);
        assertTrue(compareResults > 0);
    }

    /**
     * Checks if the correct format of the String representing the Activity object is returned.
     */
    @Test
    void toString_activityOne_returnsCorrectFormat() {
        String activityString = activityOne.toString();
        assertEquals(ACTIVITY_ONE_STRING, activityString);
    }
}

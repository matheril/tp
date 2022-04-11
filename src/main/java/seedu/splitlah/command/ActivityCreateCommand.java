package seedu.splitlah.command;

import seedu.splitlah.data.Activity;
import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Person;
import seedu.splitlah.data.Profile;
import seedu.splitlah.data.Session;
import seedu.splitlah.data.PersonList;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

/**
 * Represents a command that creates an Activity object from user input and stores it in the Session object.
 *
 * @author Ivan
 */
public class ActivityCreateCommand extends Command {
  
    private static final String COMMAND_SUCCESS = "The activity was created successfully.\n";

    private final int sessionId;
    private final String activityName;
    private double totalCost;
    private final String payer;
    private final String[] involvedList;
    private double[] costList;
    private final double gst;
    private final double serviceCharge;

    private static final double ZERO_COST_PAID = 0;
    public static final double ZERO_COST_OWED = 0;
    private static final int NO_COST = 0;

    /**
     * Initializes an ActivityCreateCommand object.
     *
     * @param sessionId     An integer that uniquely identifies a session.
     * @param activityName  A String object that represents the Activity object's name.
     * @param totalCost     A double that represents total cost of the activity.
     * @param payer         A String object that represents the name of the person who paid for the activity.
     * @param involvedList  An array of String objects that represents the names of the persons
     *                      who are involved in the activity.
     * @param costList      A double array object that represents the respective costs of
     *                      each person involved in the activity.
     * @param gst           A double that represents the GST percentage to be included for the cost of the activity.
     * @param serviceCharge A double that represents the service charge to be included for the cost of the activity.
     */
    public ActivityCreateCommand(int sessionId, String activityName, double totalCost, String payer,
                                 String[] involvedList, double[] costList, double gst, double serviceCharge) {
        assert sessionId > 0 : Message.ASSERT_ACTIVITYCREATE_SESSION_ID_LESS_THAN_ONE;
        assert activityName != null : Message.ASSERT_ACTIVITYCREATE_ACTIVITY_NAME_MISSING;
        assert payer != null : Message.ASSERT_ACTIVITYCREATE_PAYER_NAME_MISSING;
        assert involvedList != null : Message.ASSERT_ACTIVITYCREATE_INVOLVED_LIST_ARRAY_NULL;
        this.sessionId = sessionId;
        this.activityName = activityName;
        this.totalCost = totalCost;
        this.payer = payer;
        this.involvedList = involvedList;
        this.costList = costList;
        this.gst = gst;
        this.serviceCharge = serviceCharge;
    }

    /**
     * Adds all relevant activity costs to each involved person's list of activity costs.
     *
     * @param involvedPersonList An ArrayList object containing Person objects
     *                           each representing a person involved in the activity.
     * @param personPaid         A Person object representing the person who paid for the activity.
     * @param activityId         An integer that uniquely identifies an activity.
     */
    private void addAllActivityCost(ArrayList<Person> involvedPersonList, Person personPaid, int activityId) {
        boolean hasAddedForPersonPaid = false;
        for (int i = 0; i < involvedPersonList.size(); i++) {
            Person person = involvedPersonList.get(i);
            addCostOwedAndCostPaid(personPaid, activityId, i, person);
            hasAddedForPersonPaid = isPersonPaid(personPaid, hasAddedForPersonPaid, person);
        }
        if (!hasAddedForPersonPaid) {
            personPaid.addActivityCost(activityId, totalCost, ZERO_COST_OWED);
        }
    }

    /**
     * Checks if the Person object currently referred to represents the person who paid for the activity.
     *
     * @param personPaid            A Person object representing the person who paid for the activity.
     * @param hasAddedForPersonPaid A boolean representing whether the activity cost has been added for the person who
     *                              paid for the activity.
     * @param person                A Person object representing the person currently referred to
     *                              among the persons involved.
     * @return true if the Person object currently referred to represents the person who paid for the activity,
     *         hasAddedForPersonPaid otherwise.
     */
    private boolean isPersonPaid(Person personPaid, boolean hasAddedForPersonPaid, Person person) {
        if (person == personPaid) {
            hasAddedForPersonPaid = true;
        }
        return hasAddedForPersonPaid;
    }

    /**
     * Adds cost owed and cost paid to each individual's list of activity costs.
     * Checks if the current person is the person who paid for the activity.
     * If it is, the cost paid is set to the total cost of the activity.
     * Else, the cost paid is set to 0.
     *
     * @param personPaid      A Person object representing the person who paid for the activity.
     * @param activityId      An integer that uniquely identifies an activity.
     * @param indexOfCostOwed An integer representing the index of the cost owed in the list of costs.
     * @param person          A person object representing the person whose costs are added to the
     *                        list of activity costs.
     */
    private void addCostOwedAndCostPaid(Person personPaid, int activityId, int indexOfCostOwed, Person person) {
        if (person == personPaid) {
            person.addActivityCost(activityId, totalCost, costList[indexOfCostOwed]);
        } else {
            person.addActivityCost(activityId, ZERO_COST_PAID, costList[indexOfCostOwed]);
        }
    }

    /**
     * Updates cost and list of costs by adding the extra charges and
     * checks if cost list or cost was provided by the user.
     * If cost was not provided by the user, the list of costs is summed up to get the total cost of the activity.
     * Else, the total cost is distributed evenly.
     */
    private void updateCostAndCostList() {
        boolean isZeroCost = totalCost == NO_COST;
        if (isZeroCost) {
            updateCostListWithExtraCharges();
            calculateTotalCost();
        } else {
            updateCostWithExtraCharges();
            int numberOfPeopleInvolved = involvedList.length;
            costList = distributeCostEvenly(numberOfPeopleInvolved);
        }
    }

    /**
     * Updates cost list by including the extra charges.
     * Extra charges may include GST and service charge.
     * Assumption: GST and service charge are non-negative values.
     */
    private void updateCostListWithExtraCharges() {
        double extraCharges = getExtraCharges();
        for (int i = 0; i < costList.length; i++) {
            costList[i] *= extraCharges;
        }
    }

    /**
     * Updates the total cost of the activity by summing up the costs in the list of costs.
     */
    private void calculateTotalCost() {
        for (double cost : costList) {
            totalCost += cost;
        }
    }

    /**
     * Updates total cost by including the extra charges.
     * Extra charges may include GST and service charge.
     * Assumption: GST and service charge are non-negative values.
     */
    private void updateCostWithExtraCharges() {
        double extraCharges = getExtraCharges();
        totalCost *= extraCharges;
    }

    /**
     * Returns a double representing the extra charges that is to be included in costs of the activity.
     *
     * @return A double representing the extra charges.
     */
    private double getExtraCharges() {
        double gstMultiplier = 1 + gst / 100;
        double serviceChargeMultiplier = 1 + serviceCharge / 100;
        return gstMultiplier * serviceChargeMultiplier;
    }

    /**
     * Returns an array of doubles of the costs that has been distributed evenly
     * among the persons involved in the activity.
     * Divides the total cost by the number of people involved in the activity.
     *
     * @param numberOfPeopleInvolved An integer representing the number of people involved in the activity.
     * @return An array of doubles representing the costs of each person involved in the activity.
     */
    private double[] distributeCostEvenly(int numberOfPeopleInvolved) {
        double dividedCost = totalCost / numberOfPeopleInvolved;
        double[] costList = new double[numberOfPeopleInvolved];
        Arrays.fill(costList, dividedCost);
        return costList;
    }

    /**
     * Runs the command to create an Activity object to be stored in a Session object managed by the Profile object.
     * Gets relevant parameters to create an Activity object.
     * If no errors getting parameters, an Activity object is created and added to the session.
     *
     * @param manager A Manager object that manages the TextUI, Profile and Storage object.
     */
    @Override
    public void run(Manager manager) {
        boolean hasDuplicates = PersonList.hasNameDuplicates(involvedList);
        TextUI ui = manager.getUi();
        if (hasDuplicates) {
            ui.printlnMessage(Message.ERROR_PERSONLIST_DUPLICATE_NAME_IN_ACTIVITY);
            Manager.getLogger().log(Level.FINEST,Message.LOGGER_PERSONLIST_NAME_DUPLICATE_EXISTS_IN_CREATEACTIVITY);
            return;
        }
        try {
            updateCostAndCostList();
            assert costList != null : Message.ASSERT_ACTIVITYCREATE_COST_LIST_ARRAY_NULL;
            assert totalCost > 0 : Message.ASSERT_ACTIVITYCREATE_TOTAL_COST_LESS_THAN_ONE;

            Profile profile = manager.getProfile();
            Session session = profile.getSession(sessionId);
            Person personPaid = session.getPersonByName(payer);
            ArrayList<Person> involvedArrayList = session.getPersonListByName(involvedList);
            int activityId = profile.getNewActivityId();

            addAllActivityCost(involvedArrayList, personPaid, activityId);

            PersonList involvedPersonList = new PersonList(involvedArrayList);
            Activity activity = new Activity(activityId, activityName, totalCost, personPaid, involvedPersonList,
                    gst, serviceCharge);

            session.addActivity(activity);
            manager.saveProfile();
            ui.printlnMessage(COMMAND_SUCCESS + activity);
            Manager.getLogger().log(Level.FINEST,Message.LOGGER_ACTIVITYCREATE_ACTIVITY_ADDED + activityId);
        } catch (InvalidDataException e) {
            ui.printlnMessage(e.getMessage());
            Manager.getLogger().log(Level.FINEST,Message.LOGGER_ACTIVITYCREATE_FAILED_ADDING_ACTIVITY
                    + "\n" + e.getMessage());
        }
    }
}

package seedu.splitlah.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;

import seedu.splitlah.data.Activity;
import seedu.splitlah.data.ActivityCost;
import seedu.splitlah.data.Manager;
import seedu.splitlah.data.Person;
import seedu.splitlah.data.PersonList;
import seedu.splitlah.data.Session;
import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TextUI;

/**
 * Represents a command object that edits an Activity object.
 */
public class ActivityEditCommand extends Command {

    private static final String COMMAND_SUCCESS = "The activity was edited successfully.\n";

    private static final double ZERO_COST_PAID = 0;
    public static final double ZERO_COST_OWED = 0;
    private static final int MISSING_ACTIVITYID = -1;
    private static final int MISSING_SESSIONID = -1;
    private static final double MISSING_TOTALCOST = -1;
    private static final double MISSING_GST = -1;
    private static final double MISSING_SERVICECHARGE = -1;
    private static final double[] MISSING_COSTLIST = null;
    private static final String MISSING_ACTIVITYNAME = null;
    private static final String MISSING_PAYER = null;
    private static final String[] MISSING_INVOLVEDLIST = null;
    private static final int DUMMY_ACTIVITYID = MISSING_ACTIVITYID;
    private static final int MODE_UNSET = -1;
    private static final int MODE_OVERWRITE = 0;
    private static final int MODE_PRESERVE = 1;
    private static final double PERCENT = 0.01;
    private static final double DEFAULT_MULTIPLIER_1 = 1;
    private static final int PARAMETER_SUPPLIED_AND_NECESSARY = 0;
    private static final int PARAMETER_UNNECESSARY = -1;

    private int activityId = MISSING_ACTIVITYID;
    private int sessionId = MISSING_SESSIONID;
    private Activity oldActivity = null;
    private Session session = null;
    private String activityName = MISSING_ACTIVITYNAME;
    private double totalCost = MISSING_TOTALCOST;
    private String payer = MISSING_PAYER;
    private String[] involvedListStringArray = MISSING_INVOLVEDLIST;
    private double[] costList = MISSING_COSTLIST;
    private double editMode = MODE_UNSET;
    private double gst = MISSING_GST;
    private double oldGst = MISSING_GST;
    private double serviceCharge = MISSING_SERVICECHARGE;
    private double oldServiceCharge = MISSING_SERVICECHARGE;
    private ArrayList<Person> involvedListPersonArray = null;

    private boolean isActivityNameSupplied = false;
    private boolean isInvolvedListSupplied = false;
    private boolean isCostListSupplied = false;
    private boolean isPayerSupplied = false;
    private boolean isGstSupplied = false;
    private boolean isScSupplied = false;

    /**
     * Initializes an ActivityEditCommand object.
     *
     * @param sessionId     An integer that uniquely identifies a session.
     * @param activityId    An integer that uniquely identifies a session.
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
    public ActivityEditCommand(int sessionId, int activityId, String activityName, String payer, String[] involvedList,
                               Double totalCost, double[] costList, double gst, double serviceCharge) {
        assert sessionId > 0 : Message.ASSERT_ACTIVITYEDIT_SESSIONID_LESS_THAN_ONE;
        assert activityId > 0 : Message.ASSERT_ACTIVITYEDIT_ACTIVITYID_MISSING;
        this.activityId = activityId;
        this.sessionId = sessionId;
        this.activityName = activityName;
        this.totalCost = totalCost;
        this.payer = payer;
        this.involvedListStringArray = involvedList;
        this.costList = costList;
        this.gst = gst;
        this.serviceCharge = serviceCharge;
    }

    /**
     * Adds all relevant activity costs to each involved person's list of activity costs.
     *
     * @param involvedPersonList An ArrayList object of Person objects
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
     * Updates cost list using the list of involved Person objects.
     */
    private void updateCostListFromActivity() {
        int listLength = involvedListPersonArray.size();
        costList = new double[listLength];
        for (int i = 0; i < listLength; ++i) {
            double costOwedForThisActivity;
            try {
                costOwedForThisActivity = involvedListPersonArray.get(i).getActivityCostOwed(activityId);
            } catch (InvalidDataException exception) {
                assert exception.getMessage().equals(Message.ERROR_PERSON_NO_ACTIVITIES);
                costOwedForThisActivity = 0;
            }
            costList[i] = costOwedForThisActivity;
        }
    }

    /**
     * Updates cost and list of costs, taking into account extra charges.
     */
    private void updateCostAndCostList() {
        if (editMode == MODE_PRESERVE) {
            removeOldExtraChargesFromCostList();
            resetTotalCostToZero();
            updateCostListWithExtraCharges();
            calculateTotalCost();
        } else if (editMode == MODE_OVERWRITE) {
            resetTotalCostToZero();
            updateCostListWithExtraCharges();
            calculateTotalCost();
        }
    }

    private void resetTotalCostToZero() {
        totalCost = 0;
    }

    /**
     * Calculates the old activity's cost list without gst and service charge.
     */
    private void removeOldExtraChargesFromCostList() {
        double oldGstMultiplier = DEFAULT_MULTIPLIER_1 + (oldGst * PERCENT);
        double oldServiceChargeMultiplier = DEFAULT_MULTIPLIER_1 + (oldServiceCharge * PERCENT);
        for (int i = 0; i < costList.length; ++i) {
            costList[i] /= (oldGstMultiplier * oldServiceChargeMultiplier);
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
     * Returns a double representing the extra charges that is to be included in costs of the activity.
     *
     * @return A double representing the extra charges.
     */
    private double getExtraCharges() {
        double gstMultiplier = DEFAULT_MULTIPLIER_1 + (gst * PERCENT);
        double serviceChargeMultiplier = DEFAULT_MULTIPLIER_1 + (serviceCharge * PERCENT);
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
        double[] equallyDistributedCostList = new double[numberOfPeopleInvolved];
        Arrays.fill(equallyDistributedCostList, dividedCost);
        return equallyDistributedCostList;
    }

    /**
     * Updates placeholder activityId values in new ActivityCost objects created from the edited activity to their
     * actual values: the activityId of the edited Activity object.
     *
     * @param session A Session object containing all ActivityCost objects related to the Activity object that is
     *                being edited.
     */
    private void updateDummyActivityIdsInActivityCosts(Session session) {
        ArrayList<Person> involvedPersonList = session.getPersonList().getPersonList();
        for (Person person : involvedPersonList) {
            updateAllDummyActivityIdsInPerson(person);
        }
    }

    /**
     * Updates placeholder activityId values in all ActivityCost objects associated with a Person object to their
     * actual values: the activityId of the edited Activity object.
     *
     * @param person A Person object containing all ActivityCost objects related to the Activity object that is
     *               being edited.
     */
    private void updateAllDummyActivityIdsInPerson(Person person) {
        for (ActivityCost activityCost : person.getActivityCostList()) {
            if (activityCost.getActivityId() == DUMMY_ACTIVITYID) {
                activityCost.setActivityId(activityId);
            }
        }
    }

    /**
     * Checks to ensure that the cost list matches the list of participants.
     *
     * @throws InvalidDataException If there is a mismatch between the list of participants and the cost list.
     */
    private void validateCostListAndInvolvedList() throws InvalidDataException {
        if (involvedListStringArray != MISSING_INVOLVEDLIST) {
            if (involvedListStringArray.length != costList.length) {
                Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYEDIT_FAILED_EDITING_ACTIVITY);
                throw new InvalidDataException(Message.ERROR_ACTIVITYEDIT_INVOLVED_AND_COST_DIFFERENT_LENGTH);
            } else if (PersonList.hasNameDuplicates(involvedListStringArray)) {
                Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYEDIT_FAILED_EDITING_ACTIVITY);
                throw new InvalidDataException(Message.ERROR_ACTIVITYEDIT_DUPLICATE_NAME);
            }
        }
    }

    /**
     * Retrieves details about the activity not provided by the user from the original Activity object. These details
     * will be used to create the edited Activity object.
     *
     * @param oldActivity An Activity object representing the activity to be edited.
     */
    private void retrieveDetailsFromOldActivity(Activity oldActivity) throws InvalidDataException {
        oldGst = oldActivity.getGst();
        oldServiceCharge = oldActivity.getServiceCharge();
        retrieveActivityNameIfNotSupplied(oldActivity);
        retrieveInvolvedListIfNotSupplied(oldActivity);
        involvedListPersonArray = session.getPersonListByName(involvedListStringArray);
        checkIfBothCostAndCostListSupplied();
        if (totalCost == MISSING_TOTALCOST && costList == MISSING_COSTLIST) {
            editMode = MODE_PRESERVE;
            updateCostListFromActivity();
            isCostListSupplied = false;
        } else if (totalCost != MISSING_TOTALCOST) {
            editMode = MODE_OVERWRITE;
            updateCostListFromUserInput();
            isCostListSupplied = true;
        } else {
            editMode = MODE_OVERWRITE;
            isCostListSupplied = true;
        }
        retrievePayerIfNotSupplied(oldActivity);
        retrieveGstIfNotSupplied(oldActivity);
        retrieveServiceChargeIfNotSupplied(oldActivity);
    }

    /**
     * Retrieves service charge from an Activity object, but only if the user did not supply a service charge.
     *
     * @param oldActivity An Activity object representing the old activity to retrieve the service charge from.
     */
    private void retrieveServiceChargeIfNotSupplied(Activity oldActivity) {
        if (serviceCharge == MISSING_SERVICECHARGE) {
            serviceCharge = oldActivity.getServiceCharge();
        } else {
            isScSupplied = true;
        }
    }

    /**
     * Retrieves GST from an Activity object, but only if the user did not supply a GST.
     *
     * @param oldActivity An Activity object representing the old activity to retrieve the GST from.
     */
    private void retrieveGstIfNotSupplied(Activity oldActivity) {
        if (gst == MISSING_GST) {
            gst = oldActivity.getGst();
        } else {
            isGstSupplied = true;
        }
    }

    /**
     * Retrieves payer from an Activity object, but only if the user did not supply a payer.
     *
     * @param oldActivity An Activity object representing the old activity to retrieve the payer from.
     */
    private void retrievePayerIfNotSupplied(Activity oldActivity) {
        if (Objects.equals(payer, MISSING_PAYER)) {
            payer = oldActivity.getPersonPaid().getName();
        } else {
            isPayerSupplied = true;
        }
    }

    /**
     * Checks if both a total cost and cost list are supplied, which is invalid input from the user.
     *
     * @throws InvalidDataException If both a total cost and cost list are supplied by the user.
     */
    private void checkIfBothCostAndCostListSupplied() throws InvalidDataException {
        if (totalCost != MISSING_TOTALCOST && costList != MISSING_COSTLIST) {
            throw new InvalidDataException(Message.ERROR_ACTIVITYEDIT_COSTLIST_AND_COSTOVERALL_SUPPLIED);
        }
    }

    private void retrieveInvolvedListIfNotSupplied(Activity oldActivity) {
        if (involvedListStringArray == MISSING_INVOLVEDLIST) {
            involvedListStringArray = getInvolvedListFromPersonList(oldActivity.getInvolvedPersonList());
        } else {
            isInvolvedListSupplied = true;
        }
    }

    /**
     * Retrieves the activity name from an Activity object, but only if the user did not supply an activity name.
     *
     * @param oldActivity An Activity object representing the old activity to retrieve the activity name from.
     */
    private void retrieveActivityNameIfNotSupplied(Activity oldActivity) {
        if (Objects.equals(activityName, MISSING_ACTIVITYNAME)) {
            activityName = oldActivity.getActivityName();
        } else {
            isActivityNameSupplied = true;
        }
    }

    /**
     * Updates the cost list using the cost specified by the user.
     */
    private void updateCostListFromUserInput() {
        int listLength = involvedListPersonArray.size();
        costList = distributeCostEvenly(listLength);
    }

    /**
     * Extracts a String array object containing the names of the participants from an ArrayList of
     * Person objects.
     *
     * @param involvedPersonList An ArrayList object of Person objects.
     * @return A String array object containing the names of the participants.
     */
    private String[] getInvolvedListFromPersonList(ArrayList<Person> involvedPersonList) {
        int listSize = involvedPersonList.size();
        String[] involvedListStringArray = new String[listSize];
        for (int i = 0; i < listSize; ++i) {
            involvedListStringArray[i] = involvedPersonList.get(i).getName();
        }
        return involvedListStringArray;
    }

    /**
     * Checks if no changes are made to the activity.
     *
     * @param oldActivity An Activity object representing the old activity to compare the edited activity against.
     * @throws InvalidDataException If no changes are made to the activity.
     */
    private void checkIfNoChangesMade(Activity oldActivity) throws InvalidDataException {
        boolean isActivityNameUnnecessary = checkIfActivityNameIsUnnecessary(oldActivity);
        boolean isInvolvedListUnnecessary = checkIfInvolvedListIsUnnecessary(oldActivity);
        boolean isCostListUnnecessary = checkIfCostListIsUnnecessary(oldActivity);
        boolean isPayerUnnecessary = checkIfPayerIsUnnecessary(oldActivity);
        boolean isGstUnnecessary = checkIfGstIsUnnecessary(oldActivity);
        boolean isScUnnecessary = checkIfScIsUnnecessary(oldActivity);

        if (isActivityNameUnnecessary
                && isInvolvedListUnnecessary
                && isCostListUnnecessary
                && isPayerUnnecessary
                && isGstUnnecessary
                && isScUnnecessary) {
            throw new InvalidDataException(Message.ERROR_ACTIVITYEDIT_NO_CHANGE_TO_ACTIVITY);
        }
    }

    /**
     * Checks if the service charge is unnecessary when editing the activity.
     *
     * @param oldActivity An Activity object representing the old activity to be compared against.
     * @return true if the service charge was not supplied by the user, or if it is identical to the old service charge.
     *         false if the service charge was supplied by the user and is different from the old service charge.
     */
    private boolean checkIfScIsUnnecessary(Activity oldActivity) {
        if (!isScSupplied) {
            return true;
        }
        return serviceCharge == oldActivity.getServiceCharge();
    }

    /**
     * Checks if the GST is unnecessary when editing the activity.
     *
     * @param oldActivity An Activity object representing the old activity to be compared against.
     * @return true if the GST was not supplied by the user, or if it is identical to the old GST.
     *         false if the GST was supplied by the user and is different from the old GST.
     */
    private boolean checkIfGstIsUnnecessary(Activity oldActivity) {
        if (!isGstSupplied) {
            return true;
        }
        return gst == oldActivity.getGst();
    }

    /**
     * Checks if the payer is unnecessary when editing the activity.
     *
     * @param oldActivity An Activity object representing the old activity to be compared against.
     * @return true if the payer was not supplied by the user, or if it is identical to the old payer.
     *         false if the payer was supplied by the user and is different from the old payer.
     */
    private boolean checkIfPayerIsUnnecessary(Activity oldActivity) {
        if (!isPayerSupplied) {
            return true;
        }
        Person newPayer = Person.createPersonFromString(payer);
        assert newPayer != null;
        return newPayer.equals(oldActivity.getPersonPaid());
    }

    /**
     * Checks if the cost list or total cost is unnecessary when editing the activity.
     *
     * @param oldActivity An Activity object representing the old activity to be compared against.
     * @return true if the cost list or total cost was not supplied by the user, or if it is identical to the old cost
     *              list.
     *         false if the cost list or total cost was supplied by the user and is different from the old cost list
     *               or total cost.
     */
    private boolean checkIfCostListIsUnnecessary(Activity oldActivity) throws InvalidDataException {
        if (!isCostListSupplied) {
            return true;
        }
        double[] oldCostList = new double[oldActivity.getInvolvedPersonList().size()];
        fillCostList(oldCostList, oldActivity);
        if (costList.length != oldCostList.length) {
            return false;
        }
        for (int i = 0; i < costList.length; ++i) {
            if (costList[i] != oldCostList[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Fills a cost list with the costs from an Activity object.
     *
     * @param oldCostList A double array object representing the cost list to be filled.
     * @param oldActivity An Activity object representing the activity to retrieve costs from.
     * @throws InvalidDataException If a nonexistent unique activity identifier is used when retrieving costs.
     */
    private void fillCostList(double[] oldCostList, Activity oldActivity) throws InvalidDataException {
        ArrayList<Person> oldInvolvedPersonList = oldActivity.getInvolvedPersonList();
        for (int i = 0; i < oldInvolvedPersonList.size(); ++i) {
            oldCostList[i] = oldInvolvedPersonList.get(i).getActivityCostOwed(activityId);
        }
    }

    /**
     * Checks if the list of participants is unnecessary when editing the activity.
     *
     * @param oldActivity An Activity object representing the old activity to be compared against.
     * @return true if the list of participants was not supplied by the user, or if it is identical to the old list of
     *              participants.
     *         false if the list of participants was supplied by the user and is different from the old list of
     *              participants.
     */
    private boolean checkIfInvolvedListIsUnnecessary(Activity oldActivity) {
        if (!isInvolvedListSupplied) {
            return true;
        }
        ArrayList<Person> oldInvolvedList = oldActivity.getInvolvedPersonList();
        if (oldInvolvedList.size() != involvedListPersonArray.size()) {
            return false;
        }
        for (int i = 0; i < involvedListPersonArray.size(); ++i) {
            if (!(oldInvolvedList.get(i).equals(involvedListPersonArray.get(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the activity name is unnecessary when editing the activity.
     *
     * @param oldActivity An Activity object representing the old activity to be compared against.
     * @return true if the activity name was not supplied by the user, or if it is identical to the old activity name.
     *         false if the activity name was supplied by the user and is different from the old activity name.
     */
    private boolean checkIfActivityNameIsUnnecessary(Activity oldActivity) {
        if (!isActivityNameSupplied) {
            return true;
        }
        return Objects.equals(activityName, oldActivity.getActivityName());
    }

    @Override
    public void run(Manager manager) {
        TextUI ui = manager.getUi();
        try {
            session = manager.getProfile().getSession(sessionId);
            oldActivity = session.getActivity(activityId);
            retrieveDetailsFromOldActivity(oldActivity);
            updateCostAndCostList();
            validateCostListAndInvolvedList();
            checkIfNoChangesMade(oldActivity);
            assert costList != null : Message.ASSERT_ACTIVITYEDIT_COST_LIST_ARRAY_NULL;
            Person payerAsPerson = session.getPersonByName(payer);
            addAllActivityCost(involvedListPersonArray, payerAsPerson, DUMMY_ACTIVITYID);
            PersonList involvedPersonList = new PersonList(involvedListPersonArray);
            session.removeActivity(activityId);
            Activity newActivity = new Activity(activityId, activityName, totalCost, payerAsPerson, involvedPersonList,
                    (gst != MISSING_GST ? gst : oldGst),
                    (serviceCharge != MISSING_SERVICECHARGE ? serviceCharge : oldServiceCharge));
            session.addActivity(newActivity);
            updateDummyActivityIdsInActivityCosts(session);
            manager.saveProfile();
            ui.printlnMessage(COMMAND_SUCCESS + newActivity);
            Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYEDIT_ACTIVITY_EDITED);
        } catch (InvalidDataException exception) {
            ui.printlnMessage(exception.getMessage());
        } catch (Exception exception) {
            ui.printlnMessage(exception.getMessage());
            exception.printStackTrace();
        }
    }
}

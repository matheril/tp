package seedu.splitlah.data;

import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;
import seedu.splitlah.ui.TableFormatter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents an activity that was done during a group outing session.
 *
 * @author Ivan
 */
public class Activity implements Serializable, Comparable<Activity> {

    private int activityId;
    private String activityName;
    private double totalCost;
    private Person personPaid;
    private PersonList involvedPersonList;
    private double gst;
    private double serviceCharge;

    private static final int ZERO_INDEXING_OFFSET = 1;
    private static final String[] INVOLVED_PERSON_LIST_COLS = { "#", "Name", "Cost Owed" };

    /**
     * Initializes an Activity object.
     *
     * @param activityId         An integer that uniquely identifies an activity.
     * @param activityName       A String object that represents the activity's name.
     * @param totalCost          A double that represents the total cost spent in the activity.
     * @param personPaid         A Person object representing the person who paid for the activity.
     * @param involvedPersonList An ArrayList object containing Person objects
     *                           each representing a person involved in the activity.
     * @param gst                A double that represents the GST percentage to be included for the cost of the
     *                           activity.
     * @param sc                 A double that represents the service charge to be included for the cost of the
     *                           activity.
     */
    public Activity(int activityId, String activityName, double totalCost, Person personPaid,
                    PersonList involvedPersonList, double gst, double sc) {
        this.activityId = activityId;
        this.activityName = activityName;
        this.totalCost = totalCost;
        this.personPaid = personPaid;
        this.involvedPersonList = involvedPersonList;
        this.gst = gst;
        this.serviceCharge = sc;
    }

    /**
     * Returns an integer representing the activity's unique identifier.
     * Assumption: The identifier number is unique across all activity identifiers in the Profile object.
     *
     * @return An integer representing the activity's identifier.
     */
    public int getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Person getPersonPaid() {
        return personPaid;
    }

    public double getGst() {
        return gst;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public ArrayList<Person> getInvolvedPersonList() {
        return involvedPersonList.getPersonList();
    }

    /**
     * Returns a String object of the persons involved in the activity and their respective costs for the activity.
     *
     * @return A String object representing persons involved and their respective costs owed.
     * @throws InvalidDataException If there is a mismatch between the activity cost owed and the person involved
     *                              in the activity.
     */
    private String getInvolvedListString() throws InvalidDataException {
        assert !involvedPersonList.isEmpty() : Message.ASSERT_ACTIVITY_EMPTY_INVOLVED_PERSON_LIST;

        TableFormatter summaryTable = new TableFormatter(
                INVOLVED_PERSON_LIST_COLS[0],
                INVOLVED_PERSON_LIST_COLS[1],
                INVOLVED_PERSON_LIST_COLS[2]
        );
        for (int i = 1; i <= involvedPersonList.getSize(); i++) {
            String index = Integer.toString(i);
            Person person = involvedPersonList.getPerson(i - ZERO_INDEXING_OFFSET);
            String personName = person.getName();
            Double cost = person.getActivityCostOwed(activityId);
            String costOwed = "$" + String.format("%.2f", cost);
            summaryTable.addRow(index, personName, costOwed);
        }
        return summaryTable.toString();
    }

    /**
     * Returns an integer to identify whether this Activity object should be ordered
     * before or after another Activity object when sorted.
     *
     * @param activity The specified Activity object that this Activity object is compared against.
     * @return An integer less than 0 if this Activity object's activityId is smaller than the specified
     *         Activity object's sessionId,
     *         an integer greater than 0 if this object's activityId is larger,
     *         and 0 if both Activity objects' activityIds are numerically equal.
     */
    @Override
    public int compareTo(Activity activity) {
        return Integer.compare(activityId, activity.getActivityId());
    }

    /**
     * Returns a String which provides the details of the activity, including the name of the activity,
     * the activity's unique identifier, the name of the person who paid for the activity,
     * the total cost of the activity, the persons involved in the activity
     * and their respective costs for the activity.
     *
     * @return A String object representing the details of the activity.
     */
    @Override
    public String toString() {
        try {
            return "Activity Id #" + activityId + " --\n"
                    + "Name:                     " + activityName + '\n'
                    + "Id:                       " + activityId + '\n'
                    + "Payer:                    " + personPaid.getName() + '\n'
                    + "GST:                      " + String.format("%.2f", gst) + "%\n"
                    + "Service Charge(SC):       " + String.format("%.2f", serviceCharge) + "%\n"
                    + "Cost(GST & SC inclusive): $" + String.format("%.2f", totalCost) + '\n'
                    + "Involved: \n"
                    + getInvolvedListString();
        } catch (InvalidDataException e) {
            return Message.ERROR_ACTIVITY_INACCURATE_INVOLVED_LIST;
        }
    }
}

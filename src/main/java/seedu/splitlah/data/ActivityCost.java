package seedu.splitlah.data;

import seedu.splitlah.ui.Message;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * Represents the costs borne by a person for an activity.
 *
 * @author Saurav
 */
public class ActivityCost implements Serializable {

    private int activityId;
    private double costPaid;
    private double costOwed;

    /**
     * Initializes an ActivityCost object.
     * Assumption: Every field is non-negative.
     *
     * @param activityId An integer that uniquely identifies an activity.
     * @param costPaid   A double that represents the cost paid by a Person object.
     * @param costOwed   A double that represents the cost owed by a Person object.
     */
    public ActivityCost(int activityId, double costPaid, double costOwed) {
        Manager.getLogger().log(Level.FINEST, Message.LOGGER_ACTIVITYCOST_CONSTRUCT_WITH_ALL_PARAMS);
        this.activityId = activityId;
        this.costPaid = costPaid;
        this.costOwed = costOwed;
    }

    public int getActivityId() {
        return activityId;
    }

    public double getCostPaid() {
        return costPaid;
    }

    public double getCostOwed() {
        return costOwed;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}

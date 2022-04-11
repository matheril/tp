package seedu.splitlah.data;

import seedu.splitlah.exceptions.InvalidDataException;
import seedu.splitlah.ui.Message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a person participating in an activity during a group outing session.
 * 
 * @author Saurav
 */
public class Person implements Serializable {
    
    private Name name;
    private ArrayList<ActivityCost> activityCostList;

    /**
     * Initializes a Person object.
     *
     * @param name A String object representing the name of the Person object to be created.
     */
    private Person(String name) {
        this.activityCostList = new ArrayList<>();
        this.name = new Name(name);
    }

    /**
     * Constructs a Person object from a String object. If the String object provided is not a valid name,
     * returns null instead.
     *
     * @param name A String object representing a name.
     * @return A Person object if the name provided is valid.
     *         null if the name provided is invalid.
     */
    public static Person createPersonFromString(String name) {
        if (Name.validateName(name)) {
            return new Person(name);
        }
        return null;
    }

    /**
     * Returns the person's name.
     *
     * @return A String object that represents the name of the person.
     */
    public String getName() {
        return name.getNameAsString();
    }

    /**
     * Returns a list of ActivityCost objects representing the activity costs that person participated in.
     *
     * @return An ArrayList object that represents the ActivityCost objects that the person participated in.
     */
    public ArrayList<ActivityCost> getActivityCostList() {
        return activityCostList;
    }

    /**
     * Constructs an ActivityCost object and adds it to the list of ActivityCost objects.
     *
     * @param activityId An integer that uniquely identifies an activity.
     * @param costPaid   A double that represents the cost paid by a Person.
     * @param costOwed   A double that represents the cost owed by a Person.
     */
    public void addActivityCost(int activityId, double costPaid, double costOwed) {
        ActivityCost activityCost = new ActivityCost(activityId, costPaid, costOwed);
        activityCostList.add(activityCost);
    }

    /**
     * Removes an ActivityCost object from the list of ActivityCosts.
     *
     * @param activityId An integer that represents the activityId of the ActivityCost to be removed.
     * @throws InvalidDataException If the activityId is not found.
     */
    public void removeActivityCost(int activityId) throws InvalidDataException {
        if (activityCostList.isEmpty()) {
            throw new InvalidDataException(Message.ERROR_PERSON_NO_ACTIVITIES);
        }
        boolean removed =
                activityCostList.removeIf(activityCost -> activityCost.getActivityId() == activityId);
        if (!removed) {
            throw new InvalidDataException(Message.ERROR_PERSON_ACTIVITY_NOT_FOUND + activityId);
        }
    }

    /**
     * Calculates the amount of money other Persons owe to this Person.
     * In other words, how much money this Person must receive in order to break even.
     *
     * @return A double that represents the sum of all costPaid - sum of all costOwed
     *         in the list of ActivityCosts for this Person.
     */
    public double getTotalCost() {
        double totalCostPaid = 0;
        double totalCostOwed = 0;
        for (ActivityCost i : activityCostList) {
            totalCostPaid += i.getCostPaid();
            totalCostOwed += i.getCostOwed();
        }
        return totalCostPaid - totalCostOwed;
    }

    /**
     * Returns the cost owed by this Person object for an Activity.
     *
     * @param activityId An integer that uniquely identifies an activity.
     * @return A double that represents the cost owed by the Person for this Activity.
     * @throws InvalidDataException If this Person is not participating in any Activities.
     *                              If the activityId is not found.
     */
    public double getActivityCostOwed(int activityId) throws InvalidDataException {
        if (activityCostList.isEmpty()) {
            throw new InvalidDataException(Message.ERROR_PERSON_NO_ACTIVITIES);
        }
        for (ActivityCost activityCost : activityCostList) {
            if (activityCost.getActivityId() == activityId) {
                return activityCost.getCostOwed();
            }
        }
        throw new InvalidDataException(Message.ERROR_PERSON_ACTIVITY_NOT_FOUND + activityId);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object.getClass() == this.getClass())) {
            return false;
        }
        Person person = (Person) object;
        return this.name.getNameAsString().equalsIgnoreCase(person.getName());
    }
}

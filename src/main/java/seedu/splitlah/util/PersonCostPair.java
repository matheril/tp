package seedu.splitlah.util;

import seedu.splitlah.data.Person;

import java.util.Comparator;

/**
 * Represents a total cost that a person must pay or receive to settle the transactions for the session.
 * A negative value represents a debt to pay another person with a positive value.
 * 
 * @author Warren
 */
public class PersonCostPair implements Comparable<PersonCostPair> {

    private Person person;
    private double cost;
    private boolean isProcessed;

    public PersonCostPair(Person person) {
        this.person = person;
        this.cost = person.getTotalCost();
        this.isProcessed = false;
    }

    public Person getPerson() {
        return person;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isProcessed() {
        return isProcessed;
    }

    public void setProcessed(boolean processed) {
        isProcessed = processed;
    }

    /**
     * Returns an integer to identify whether this PersonCostPair object should be ordered
     * before or after another object.
     *
     * @param personCostPair The specified object that this object is comparing to.
     * @return A value less than 0 if this object's cost is smaller than the specified object's cost, 
     *         a value greater than 0 if this object's cost is larger,
     *         and 0 if both objects' costs are numerically equal.
     */
    @Override
    public int compareTo(PersonCostPair personCostPair) {
        return Double.compare(cost, personCostPair.getCost());
    }
}

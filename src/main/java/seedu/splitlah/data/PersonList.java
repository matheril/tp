package seedu.splitlah.data;

import seedu.splitlah.ui.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a list of Person objects.
 *
 * @author Roy
 */
public class PersonList implements Serializable {

    private ArrayList<Person> personList;

    /**
     * Initializes a PersonList object.
     */
    public PersonList() {
        this.personList = new ArrayList<>();
    }

    /**
     * Initializes a PersonList object using an array of String object of names.
     *
     * @param personNames An array of String objects representing names of persons.
     */
    public PersonList(String[] personNames) {
        this.personList = new ArrayList<>();
        for (String name : personNames) {
            Person newPerson = Person.createPersonFromString(name);
            if (newPerson != null) {
                addPerson(newPerson);
            }
        }
    }

    /**
     * Initializes a PersonList object using an ArrayList object of Person objects.
     *
     * @param personList An ArrayList object of Person objects.
     */
    public PersonList(ArrayList<Person> personList) {
        this.personList = personList;
    }

    /**
     * Returns the size of the ArrayList object of Person objects.
     *
     * @return An integer that represents the size of the ArrayList object of Person objects.
     */
    public int getSize() {
        return personList.size();
    }

    /**
     * Checks if ArrayList object of Person objects is empty.
     * @return true if ArrayList object of Person objects is empty,
     *         otherwise false.
     */
    public boolean isEmpty() {
        return personList.isEmpty();
    }

    /**
     * Returns the Person object in the ArrayList object of Person objects with the specified index.
     *
     * @param index An integer representing the index.
     * @return A Person object specified by its index in the ArrayList object.
     */
    public Person getPerson(int index) {
        return personList.get(index);
    }

    /**
     * Adds a Person object into the ArrayList object of Person objects if it does not exist,
     * otherwise Person object is not added.
     *
     * @param person A Person object to be added.
     */
    public void addPerson(Person person) {
        if (!personList.contains(person)) {
            personList.add(person);
        }
    }

    /**
     * Returns the ArrayList object of Person objects.
     *
     * @return An ArrayList object of Person objects.
     */
    public ArrayList<Person> getPersonList() {
        return personList;
    }

    /**
     * Merges an ArrayList object of Person objects with existing ArrayList object of Person objects.
     *
     * @param groupPersonList An ArrayList object of Person objects.
     */
    public void mergeListOfPersons(ArrayList<Person> groupPersonList) {
        for (Person person : groupPersonList) {
            addPerson(person);
        }
    }

    /**
     * Checks if String array object of names has duplicated names.
     *
     * @param personNames An array of String objects of names.
     * @return true if it contains duplicates,
     *         false otherwise.
     */
    public static boolean hasNameDuplicates(String[] personNames) {
        Set<String> nameSet = new HashSet<>();
        for (String name : personNames) {
            String nameToBeAdded = name.toLowerCase();
            if (!nameSet.add(nameToBeAdded)) {
                return true;
            }
        }
        assert nameSet.size() == personNames.length :
                Message.ASSERT_PERSONLIST_NAME_DUPLICATE_EXISTS_BUT_NOT_DETECTED;
        return false;
    }

    /**
     * Checks if the personList attribute is a superset of the ArrayList object of Person objects supplied.
     * Assumption: Method is only called by SessionEditCommand class to verify if new list of Person objects
     *             is superset of the ArrayList object supplied.
     *
     * @param oldList An ArrayList object of Person objects.
     * @return true if the new list of Person objects is a superset of the ArrayList object of Person objects supplied,
     *         false otherwise.
     */
    public boolean isSuperset(ArrayList<Person> oldList) {
        int existCount = 0;
        for (Person person : personList) {
            if (oldList.contains(person)) {
                existCount++;
            }
        }
        return existCount == oldList.size();
    }

    /**
     * Checks if new person list is exactly the same as old person list.
     * Assumption: Function is only called by GroupEditCommand class to verify if new list of Person objects
     *             is the same as the ArrayList object supplied.
     *
     * @param oldList An ArrayList object of Person objects.
     */
    public boolean isSamePersonList(ArrayList<Person> oldList) {
        if (this.getSize() != oldList.size()) {
            return false;
        }
        for (Person person : this.getPersonList()) {
            if (!oldList.contains(person)) {
                return false;
            }
        }
        return true;
    }

}

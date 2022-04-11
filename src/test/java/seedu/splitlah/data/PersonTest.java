package seedu.splitlah.data;

import org.junit.jupiter.api.Test;
import seedu.splitlah.exceptions.InvalidDataException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class PersonTest {

    @Test
    public void createPersonFromString_lowercaseName_returnsNewPerson() {
        Person person = Person.createPersonFromString("sam");
        assertNotNull(person);
    }

    @Test
    public void createPersonFromString_uppercaseName_returnsNewPerson() {
        Person person = Person.createPersonFromString("SAM");
        assertNotNull(person);
    }

    @Test
    public void createPersonFromString_lowercaseAndUppercaseName_returnsNewPerson() {
        Person person = Person.createPersonFromString("SaM");
        assertNotNull(person);
    }

    @Test
    public void createPersonFromString_whitespaceBeforeName_returnsNewPerson() {
        Person person = Person.createPersonFromString("    Sam");
        assertNotNull(person);
        assertEquals("Sam", person.getName());
    }

    @Test
    public void createPersonFromString_whitespaceAfterName_returnsNewPerson() {
        Person person = Person.createPersonFromString("Sam    ");
        assertNotNull(person);
        assertEquals("Sam", person.getName());
    }

    @Test
    public void createPersonFromString_alphanumericName_returnsNull() {
        Person person = Person.createPersonFromString("Sam99");
        assertNull(person);
    }

    @Test
    public void createPersonFromString_numericName_returnsNull() {
        Person person = Person.createPersonFromString("99");
        assertNull(person);
    }

    @Test
    public void addActivityCost_addOneActivityCost_activityCostListIncreasesByOne() {
        Person person = Person.createPersonFromString("John");
        assertNotNull(person);
        person.addActivityCost(1, 10, 20);
        assertEquals(1, person.getActivityCostList().size());
    }

    @Test
    public void removeActivityCost_removeOneActivityCost_activityCostListDecreasesByOne() throws InvalidDataException {
        Person person = Person.createPersonFromString("John");
        assertNotNull(person);
        person.addActivityCost(1, 10, 20);
        assertEquals(1, person.getActivityCostList().size());
        person.removeActivityCost(1);
        assertEquals(0, person.getActivityCostList().size());
    }

    @Test
    public void getTotalCost_noActivites_returnsZero() {
        Person person = Person.createPersonFromString("John");
        assertNotNull(person);
        assertEquals(0, person.getTotalCost());
    }

    @Test
    public void getTotalCost_someActivities_returnsTotalCost() {
        Person person = Person.createPersonFromString("John");
        assertNotNull(person);
        person.addActivityCost(1, 10, 5);
        person.addActivityCost(2, 20, 10);
        assertEquals(15, person.getTotalCost());
    }

    @Test
    public void equals_sameName_returnsTrue() {
        Person john = Person.createPersonFromString("John");
        Person john2 = Person.createPersonFromString("John");
        assertEquals(john, john2);
    }

    @Test
    public void equals_differentName_returnsFalse() {
        Person john = Person.createPersonFromString("John");
        Person sam = Person.createPersonFromString("Sam");
        assertNotEquals(john, sam);
    }
}

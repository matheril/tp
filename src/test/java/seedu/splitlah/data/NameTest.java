package seedu.splitlah.data;

import org.junit.jupiter.api.Test;
import seedu.splitlah.exceptions.InvalidDataException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NameTest {

    @Test
    public void validateName_nameHasNumbers_returnsFalse() {
        assertFalse(Name.validateName("sam99"));
    }

    @Test
    public void validateName_nameHasSymbols_returnsFalse() throws InvalidDataException {
        assertFalse(Name.validateName("s@m."));
    }

    @Test
    public void validateName_nameIsAlphaOnly_returnsTrue() {
        assertTrue(Name.validateName("sam"));
    }
}

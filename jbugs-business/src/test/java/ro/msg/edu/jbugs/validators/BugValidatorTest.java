package ro.msg.edu.jbugs.validators;

import org.junit.Test;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;

import java.sql.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test for the {@link BugValidator} class
 *
 * @author Sebastian Maier
 */
public class BugValidatorTest {

    @Test
    public void validate() {
        Bug bugToTest = new Bug(1, null, null, null,
                null, null, null, null, null, null);

        assertFalse(BugValidator.validate(bugToTest));
        bugToTest.setTitle("");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setDescription("");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setVersion("");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setTargetDate(null);
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setStatus("");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setFixedVersion("");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setFixedVersion("A");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setSeverity("");
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setCREATED_ID(null);
        assertFalse(BugValidator.validate(bugToTest));

        bugToTest.setASSIGNED_ID(null);

        bugToTest.setTitle("Test");
        bugToTest.setDescription("Test");
        bugToTest.setVersion("1.1.1");
        bugToTest.setFixedVersion("1.1.2");
        bugToTest.setTargetDate(new Date(2019, 1, 1));
        bugToTest.setStatus("NEW");
        bugToTest.setSeverity("LOW");
        bugToTest.setASSIGNED_ID(new User());

        assertTrue(BugValidator.validate(bugToTest));

        bugToTest.setVersion("1.1.a");
        bugToTest.setStatus("IN_PROGRESS");
        bugToTest.setSeverity("MEDIUM");

        assertTrue(BugValidator.validate(bugToTest));

        bugToTest.setVersion("1.a.a");
        bugToTest.setStatus("FIXED");
        bugToTest.setSeverity("HIGH");

        assertTrue(BugValidator.validate(bugToTest));

        bugToTest.setVersion("1.a.1");
        bugToTest.setStatus("CLOSED");
        bugToTest.setSeverity("CRITICAL");

        assertTrue(BugValidator.validate(bugToTest));

        bugToTest.setVersion("a.1.a");
        bugToTest.setStatus("REJECTED");
        bugToTest.setSeverity("MEDIUM");

        assertTrue(BugValidator.validate(bugToTest));

        bugToTest.setVersion("9.1");
        bugToTest.setStatus("INFO_NEEDED");
        bugToTest.setSeverity("MEDIUM");

        assertTrue(BugValidator.validate(bugToTest));
    }
}
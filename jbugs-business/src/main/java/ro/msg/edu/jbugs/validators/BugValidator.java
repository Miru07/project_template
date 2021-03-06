package ro.msg.edu.jbugs.validators;

import ro.msg.edu.jbugs.entity.Bug;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Validator class for {@link Bug} class.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
public class BugValidator {
    private BugValidator() {
    }

    /**
     * @param bug is an {@link Bug} object that has been passed over to the method for validation.
     * @return true if the object is valid, false if not.
     */
    public static boolean validate(Bug bug) {
        if (bug.getTitle() != null) {
            if (bug.getTitle().equals("")) return false;
        } else return false;

        if (bug.getDescription() != null) {
            if (bug.getDescription().equals("")) return false;
            if (bug.getDescription().length() > 250) return false;
        } else return false;

        // We're checking this only for new bugs being added to the database
        if (bug.getID() == null || bug.getID() == 0) {
            Date sqlDateToday = new Date(new java.util.Date().getTime());
            LocalDate bugDate = bug.getTargetDate().toLocalDate();
            LocalDate todayDate = sqlDateToday.toLocalDate();
            if (todayDate.getYear() > bugDate.getYear()) return false;
            if (todayDate.getMonth().getValue() > bugDate.getMonth().getValue()) return false;
            if (todayDate.getDayOfYear() > bugDate.getDayOfYear()) return false;
        }

        /* The RegEx matches cases such as:
           1.1.1, 1.2.a etc;
         */
        if (bug.getVersion() != null) {
            if (!bug.getVersion().matches("(^[0-9a-zA-Z]{1,2}[.]{1}[0-9a-zA-Z]{1,2}){1}([.]{1}[0-9a-zA-Z]{0,2}){0,1}")) return false;
        } else return false;

        if (!bug.getFixedVersion().equals("")) {
            if (!bug.getFixedVersion().matches("(^[0-9a-zA-Z]{1,2}[.]{1}[0-9a-zA-Z]{1,2}){1}([.]{1}[0-9a-zA-Z]{0,2}){0,1}")) return false;
        }

        if (bug.getSeverity() != null) {
            if (!validateSeverity(bug.getSeverity())) return false;
        }

        if (bug.getStatus() != null) {
            if (!validateStatus(bug.getStatus())) return false;
        }

        return true;
    }

    private static boolean validateSeverity(String severity) {
        return severity.equals("CRITICAL") || severity.equals("HIGH") || severity.equals("MEDIUM") || severity.equals("LOW");
    }

    private static boolean validateStatus(String status) {
        return status.equals("NEW") || status.equals("IN_PROGRESS") || status.equals("FIXED") || status.equals("CLOSED")
                || status.equals("REJECTED") || status.equals("INFO_NEEDED") || status.equals("OPEN");
    }
}

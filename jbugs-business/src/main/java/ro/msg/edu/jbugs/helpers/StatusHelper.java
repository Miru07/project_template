package ro.msg.edu.jbugs.helpers;

import ro.msg.edu.jbugs.entity.StatusType;

import java.util.Arrays;
import java.util.List;

public class StatusHelper {

    /**
     * Returns a list with statuses in which the old status can be updated.
     * @param statusToUpdate - the status you want to update
     * @return - list of statuses or null if no matching status is found
     */
    public static List<StatusType> getStatusForUpdate(StatusType statusToUpdate){

        if(statusToUpdate.equals(StatusType.OPEN))
            return Arrays.asList(StatusType.IN_PROGRESS, StatusType.REJECTED);

        if(statusToUpdate.equals(StatusType.IN_PROGRESS))
            return Arrays.asList(StatusType.REJECTED, StatusType.INFO_NEEDED, StatusType.FIXED);

        if(statusToUpdate.equals(StatusType.REJECTED))
            return Arrays.asList(StatusType.CLOSED);

        if(statusToUpdate.equals(StatusType.FIXED))
            return Arrays.asList(StatusType.OPEN, StatusType.CLOSED);

        if(statusToUpdate.equals(StatusType.INFO_NEEDED))
            return Arrays.asList(StatusType.IN_PROGRESS);

        return null;
    }
}

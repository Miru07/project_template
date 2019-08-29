package ro.msg.edu.jbugs.helpers;

import ro.msg.edu.jbugs.entity.types.StatusType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatusHelper {

    private StatusHelper(){

    }

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
            return Collections.singletonList(StatusType.CLOSED);

        if(statusToUpdate.equals(StatusType.FIXED))
            return Arrays.asList(StatusType.OPEN, StatusType.CLOSED);

        if(statusToUpdate.equals(StatusType.INFO_NEEDED))
            return Collections.singletonList(StatusType.IN_PROGRESS);

        return Collections.emptyList();
    }
}

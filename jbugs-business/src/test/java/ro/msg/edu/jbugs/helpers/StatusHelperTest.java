package ro.msg.edu.jbugs.helpers;

import org.junit.Test;
import ro.msg.edu.jbugs.entity.types.StatusType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class StatusHelperTest {

    @Test
    public void getStatusForUpdate() {

        List<StatusType> statusTypeList;

        statusTypeList = StatusHelper.getStatusForUpdate(StatusType.OPEN);
        assertEquals( Arrays.asList(StatusType.IN_PROGRESS, StatusType.REJECTED), statusTypeList);

        statusTypeList = StatusHelper.getStatusForUpdate(StatusType.IN_PROGRESS);
        assertEquals(Arrays.asList(StatusType.REJECTED, StatusType.INFO_NEEDED, StatusType.FIXED), statusTypeList);

        statusTypeList = StatusHelper.getStatusForUpdate(StatusType.INFO_NEEDED);
        assertEquals(Collections.singletonList(StatusType.IN_PROGRESS), statusTypeList);

        statusTypeList = StatusHelper.getStatusForUpdate(StatusType.FIXED);
        assertEquals(Arrays.asList(StatusType.OPEN, StatusType.CLOSED), statusTypeList);

        statusTypeList = StatusHelper.getStatusForUpdate(StatusType.REJECTED);
        assertEquals(Collections.singletonList(StatusType.CLOSED), statusTypeList);

        statusTypeList = StatusHelper.getStatusForUpdate(StatusType.CLOSED);
        assertEquals(Collections.emptyList(), statusTypeList);
    }
}
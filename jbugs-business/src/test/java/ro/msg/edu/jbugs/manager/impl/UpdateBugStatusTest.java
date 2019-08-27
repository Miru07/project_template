package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class UpdateBugStatusTest {

    @InjectMocks
    private BugManager bugManager;

    @Mock
    private BugDao bugDao;

    private Bug createBug(String status){
        Bug bug = new Bug();
        bug.setID(1);
        bug.setTitle("bug1");
        bug.setDescription("bug1");
        bug.setVersion("1.1");
        bug.setTargetDate(null);
        bug.setStatus(status);
        bug.setFixedVersion("1.2");
        bug.setSeverity("low");
        bug.setASSIGNED_ID(createUser());
        bug.setCREATED_ID(createUser());

        return bug;
    }

    private User createUser(){

        User user = new User();
        user.setID(1);
        user.setFirstName("test5");
        user.setLastName("test5");
        user.setEmail("test5");
        user.setCounter(1);
        user.setMobileNumber("123456");
        user.setUsername("dinum");
        user.setPassword("a140c0c1eda2def2b830363ba362aa4d7d255c262960544821f556e16661b6ff");
        user.setStatus(1);

        return user;
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatusNonsenseStatus () throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("abchdhfk", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void updateStatusEmptyStringStatus () throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("", 1);
    }

    @Test
    public void updateStatusSuccessOpen1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        when(bugDao.updateBugStatus("IN_PROGRESS", 1)).thenReturn(this.createBug("IN_PROGRESS"));

        when(this.bugDao.updateBugStatus("IN_PROGRESS", 1)).thenReturn(this.createBug("IN_PROGRESS"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);
        //bugManager.updateBugStatus("REJECTED", 1);
    }

    @Test
    public void updateStatusSuccessOpen2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        when(bugDao.updateBugStatus("REJECTED", 1)).thenReturn(this.createBug("REJECTED"));

        when(this.bugDao.updateBugStatus("REJECTED", 1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("REJECTED", 1);
    }

    //Open into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("OPEN", 1);
    }

    //Open into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("FIXED", 1);
    }

    //Open into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("INFO_NEEDED", 1);
    }

    //Open into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("CLOSED", 1);
    }

    @Test
    public void updateStatusSuccessFixed1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        when(bugDao.updateBugStatus("OPEN", 1)).thenReturn(this.createBug("OPEN"));

        when(this.bugDao.updateBugStatus("OPEN", 1)).thenReturn(this.createBug("OPEN"));
        bugManager.updateBugStatus("OPEN", 1);

    }

    @Test
    public void updateStatusSuccessFixed2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(this.createBug("CLOSED"));

        when(this.bugDao.updateBugStatus("CLOSED", 1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("CLOSED", 1);

    }

    //Fixed into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        bugManager.updateBugStatus("FIXED", 1);
    }

    //Fixed into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);
    }

    //Fixed into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        bugManager.updateBugStatus("INFO_NEEDED", 1);
    }

    //Fixed into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);
    }

    @Test
    public void updateStatusSuccessInProgress1() throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        when(bugDao.updateBugStatus("REJECTED", 1)).thenReturn(this.createBug("REJECTED"));

        when(this.bugDao.updateBugStatus("REJECTED", 1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("REJECTED", 1);

    }

    @Test
    public void updateStatusSuccessInProgress2() throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        when(bugDao.updateBugStatus("FIXED", 1)).thenReturn(this.createBug("FIXED"));

        when(this.bugDao.updateBugStatus("FIXED", 1)).thenReturn(this.createBug("FIXED"));
        bugManager.updateBugStatus("FIXED", 1);

    }

    @Test
    public void updateStatusSuccessInProgress3() throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        when(bugDao.updateBugStatus("INFO_NEEDED", 1)).thenReturn(this.createBug("INFO_NEEDED"));

        when(this.bugDao.updateBugStatus("INFO_NEEDED", 1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.updateBugStatus("INFO_NEEDED", 1);

    }
    //In progress into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInProgress1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);
    }

    //In progress into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInProgress2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        bugManager.updateBugStatus("OPEN", 1);
    }

    //In progress into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInProgress3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        bugManager.updateBugStatus("CLOSED", 1);
    }

   @Test
    public void updateStatusSuccessInfoNeeded() throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        when(bugDao.updateBugStatus("IN_PROGRESS", 1)).thenReturn(this.createBug("IN_PROGRESS"));

        when(this.bugDao.updateBugStatus("IN_PROGRESS", 1)).thenReturn(this.createBug("IN_PROGRESS"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);

    }
    //Info needed into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.updateBugStatus("INFO_NEEDED", 1);
    }

    //Info needed into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.updateBugStatus("OPEN", 1);
    }

    //Info needed into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.updateBugStatus("CLOSED", 1);
    }

    //Info needed into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.updateBugStatus("FIXED", 1);
    }

    //Info needed into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded5() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.updateBugStatus("REJECTED", 1);
    }

    //Closed into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("CLOSED", 1);
    }

    //Closed into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("OPEN", 1);
    }

    //Closed into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("REJECTED", 1);
    }

    //Closed into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("FIXED", 1);
    }

    //Closed into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed5() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("INFO_NEEDED", 1);
    }

    //Closed into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed6() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);
    }

    @Test
    public void updateStatusSuccessRejected() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(this.createBug("CLOSED"));

        when(this.bugDao.updateBugStatus("CLOSED", 1)).thenReturn(this.createBug("CLOSED"));
        bugManager.updateBugStatus("CLOSED", 1);
    }

    //Rejected into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("REJECTED", 1);
    }

    //Rejected into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("OPEN", 1);
    }

    //Rejected into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("FIXED", 1);
    }

    //Rejected into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("IN_PROGRESS", 1);
    }

    //Rejected into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected5() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        bugManager.updateBugStatus("INFO_NEEDED", 1);
    }
}
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

import java.sql.Date;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CloseBugTest {

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
        bug.setTargetDate(new Date(2019, 1, 1));
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

    @Test
    public void closeBugSuccess1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("FIXED"));
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(this.createBug("CLOSED"));

        bugManager.closeBug(1);
    }

    @Test
    public void closeBugSuccess2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("REJECTED"));
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(this.createBug("CLOSED"));

        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugError1() throws BusinessException{
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("OPEN"));
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugError2() throws BusinessException{
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("IN_PROGRESS"));
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugError3() throws BusinessException{
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("INFO_NEEDED"));
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugError4() throws BusinessException{
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("CLOSED"));
        bugManager.closeBug(1);
    }

    @Test
    public void closeBugWithoutAssignedSuccess1() throws BusinessException {
        Bug bug = this.createBug("FIXED");
        bug.setASSIGNED_ID(null);
        when(bugDao.getBugByID(1)).thenReturn(bug);
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(bug);
        bugManager.closeBug(1);
    }

    @Test
    public void closeBugWithoutAssignedSuccess2() throws BusinessException {
        Bug bug = this.createBug("REJECTED");
        bug.setASSIGNED_ID(null);
        when(bugDao.getBugByID(1)).thenReturn(bug);
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(bug);
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugWithoutAssignedOpen() throws BusinessException {
        Bug bug = this.createBug("OPEN");
        bug.setASSIGNED_ID(null);
        when(bugDao.getBugByID(1)).thenReturn(bug);
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(bug);
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugWithoutAssignedClosed() throws BusinessException {
        Bug bug = this.createBug("CLOSED");
        bug.setASSIGNED_ID(null);
        when(bugDao.getBugByID(1)).thenReturn(bug);
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(bug);
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugWithoutAssignedInProgress() throws BusinessException {
        Bug bug = this.createBug("IN_PROGRESS");
        bug.setASSIGNED_ID(null);
        when(bugDao.getBugByID(1)).thenReturn(bug);
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(bug);
        bugManager.closeBug(1);
    }

    @Test(expected = BusinessException.class)
    public void closeBugWithoutAssignedInfoNeeded() throws BusinessException {
        Bug bug = this.createBug("INFO_NEEDED");
        bug.setASSIGNED_ID(null);
        when(bugDao.getBugByID(1)).thenReturn(bug);
        when(bugDao.updateBugStatus("CLOSED", 1)).thenReturn(bug);
        bugManager.closeBug(1);
    }


}
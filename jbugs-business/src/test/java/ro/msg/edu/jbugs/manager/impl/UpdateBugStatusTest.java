package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.entity.Bug;
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
        bug.setASSIGNED_ID(null);
        bug.setCREATED_ID(null);

        return bug;
    }

    @Test
    public void updateStatusSuccessOpen() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Open"));
        bugManager.updateBugStatus("In progress", 1);
        bugManager.updateBugStatus("Rejected", 1);
    }

    //Open into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Open"));
        bugManager.updateBugStatus("Open", 1);
    }

    //Open into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Open"));
        bugManager.updateBugStatus("Fixed", 1);
    }

    //Open into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Open"));
        bugManager.updateBugStatus("Info needed", 1);
    }

    //Open into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForOpen4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Open"));
        bugManager.updateBugStatus("Closed", 1);
    }

    @Test
    public void updateStatusSuccessFixed() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Fixed"));
        bugManager.updateBugStatus("Open", 1);
        bugManager.updateBugStatus("Closed", 1);

    }
    //Fixed into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Fixed"));
        bugManager.updateBugStatus("Fixed", 1);
    }

    //Fixed into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Fixed"));
        bugManager.updateBugStatus("In progress", 1);
    }

    //Fixed into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Fixed"));
        bugManager.updateBugStatus("Info needed", 1);
    }

    //Fixed into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForFixed4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Fixed"));
        bugManager.updateBugStatus("In progress", 1);
    }

    @Test
    public void updateStatusSuccessInProgress() throws BusinessException {
        when(bugDao.getBugByID(1)).thenReturn(this.createBug("In progress"));
        bugManager.updateBugStatus("Rejected", 1);
        bugManager.updateBugStatus("Fixed", 1);
        bugManager.updateBugStatus("Info needed", 1);

    }
    //In progress into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInProgress1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("In progress"));
        bugManager.updateBugStatus("In progress", 1);
    }

    //In progress into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInProgress2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("In progress"));
        bugManager.updateBugStatus("Open", 1);
    }

    //In progress into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInProgress3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("In progress"));
        bugManager.updateBugStatus("Closed", 1);
    }

    @Test
    public void updateStatusSuccessInfoNeeded() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Info needed"));
        bugManager.updateBugStatus("In progress", 1);

    }
    //Info needed into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Info needed"));
        bugManager.updateBugStatus("Info needed", 1);
    }

    //Info needed into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Info needed"));
        bugManager.updateBugStatus("Open", 1);
    }

    //Info needed into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Info needed"));
        bugManager.updateBugStatus("Closed", 1);
    }

    //Info needed into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Info needed"));
        bugManager.updateBugStatus("Fixed", 1);
    }

    //Info needed into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForInfoNeeded5() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Info needed"));
        bugManager.updateBugStatus("Rejected", 1);
    }

    //Closed into Closed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Closed"));
        bugManager.updateBugStatus("Closed", 1);
    }

    //Closed into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Closed"));
        bugManager.updateBugStatus("Open", 1);
    }

    //Closed into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Closed"));
        bugManager.updateBugStatus("Rejected", 1);
    }

    //Closed into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Closed"));
        bugManager.updateBugStatus("Fixed", 1);
    }

    //Closed into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed5() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Closed"));
        bugManager.updateBugStatus("Info needed", 1);
    }

    //Closed into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForClosed6() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Closed"));
        bugManager.updateBugStatus("In progress", 1);
    }

    @Test
    public void updateStatusSuccessRejected() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Rejected"));
        bugManager.updateBugStatus("Closed", 1);
    }
    //Rejected into Rejected
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected1() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Rejected"));
        bugManager.updateBugStatus("Rejected", 1);
    }

    //Rejected into Open
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected2() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Rejected"));
        bugManager.updateBugStatus("Open", 1);
    }

    //Rejected into Fixed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected3() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Rejected"));
        bugManager.updateBugStatus("Fixed", 1);
    }

    //Rejected into In progress
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected4() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Rejected"));
        bugManager.updateBugStatus("In progress", 1);
    }

    //Rejected into Info needed
    @Test(expected = BusinessException.class)
    public void updateStatusErrorForRejected5() throws BusinessException {

        when(bugDao.getBugByID(1)).thenReturn(this.createBug("Rejected"));
        bugManager.updateBugStatus("Info needed", 1);
    }
}
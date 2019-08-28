package ro.msg.edu.jbugs.manager.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dao.UserDao;
import ro.msg.edu.jbugs.dto.AttachmentDTO;
import ro.msg.edu.jbugs.dto.BugAttachmentWrapperDTO;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.dto.UserDTO;
import ro.msg.edu.jbugs.dtoEntityMapper.BugDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Test Class for {@link BugManager}
 *
 * @author Sebastian Maier
 */
@RunWith(MockitoJUnitRunner.class)
public class BugManagerTest {

    @InjectMocks
    BugManager bugManager;

    @Mock
    BugDao bugDao;

    @Mock
    UserDao userDao;

    @Mock
    AttachmentDao attachmentDao;

    public BugManagerTest() {
        bugManager = new BugManager();
    }

    public BugAttachmentWrapperDTO createTestObject() {
        UserDTO userDTO = new UserDTO(1, "name", "name", "admin", "admin", 0,
                "email", "077", 1);
        BugDTO bugDTO = new BugDTO(0, "test", "test", "1.1.1",
                new Date(2019, 1, 1), "NEW", "", "LOW",
                userDTO, userDTO);
        String string = "test";
        int len = string.length();
        byte[] attContent = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            attContent[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                    + Character.digit(string.charAt(i + 1), 16));
        }
        AttachmentDTO attachmentDTO = new AttachmentDTO(0, attContent, bugDTO);

        return new BugAttachmentWrapperDTO(bugDTO, attachmentDTO, "token");
    }

    public BugAttachmentWrapperDTO testObjectPersisted() {
        UserDTO userDTO = new UserDTO(1, "name", "name", "admin", "admin", 0,
                "email", "077", 1);
        BugDTO bugDTO = new BugDTO(1, "test", "test", "1.1",
                new Date(2019, 1, 1), "NEW", "", "LOW",
                userDTO, userDTO);

        String string = "test";
        int len = string.length();
        byte[] attContent = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            attContent[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                    + Character.digit(string.charAt(i + 1), 16));
        }
        AttachmentDTO attachmentDTO = new AttachmentDTO(1, attContent, bugDTO);

        return new BugAttachmentWrapperDTO(bugDTO, attachmentDTO, "token");
    }

    public User testObjectUser() {
        return new User(1, 0, "test", "test",
                "077", "test", "test", "test", 1);
    }

    public Bug testObjectBugIDZero() {
        return new Bug(0, "test", "test", "1.1.1", new Date(2019, 1, 1),
                "NEW", "", "LOW", testObjectUser(), testObjectUser());
    }

    public Bug testObjectBug() {
        return new Bug(1, "test", "test", "1.1", new Date(2019, 1, 1),
                "NEW", "", "LOW", testObjectUser(), testObjectUser());
    }

    public Attachment testObjectAttachment() {
        return new Attachment(1, null, testObjectBug());
    }

    public Attachment testObjectAttachmentZeroID() {
        return new Attachment(0, null, testObjectBug());
    }

    @Test(expected = BusinessException.class)
    public void insertBugNullBug() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = createTestObject();
        wrapperDTO.setBug(null);

        bugManager.insertBug(wrapperDTO, 1);
    }

    @Test(expected = BusinessException.class)
    public void insertBugNullAttachment() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = createTestObject();
        wrapperDTO.setAttachment(null);

        bugManager.insertBug(wrapperDTO, 1);
    }

    @Test(expected = BusinessException.class)
    public void insertBugNullToken() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = createTestObject();
        wrapperDTO.setToken(null);

        bugManager.insertBug(wrapperDTO, 1);
    }

    @Test(expected = BusinessException.class)
    public void insertBugNullCreatedUser() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = createTestObject();

        when(userDao.findUser(any())).thenReturn(null);
        bugManager.insertBug(wrapperDTO, 1);
    }

    @Test(expected = BusinessException.class)
    public void insertBugNullAssignedUser() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = createTestObject();

        when(userDao.findUser(any())).thenReturn(null);
        bugManager.insertBug(wrapperDTO, 1);
    }

    @Test(expected = BusinessException.class)
    public void insertBugNullOrZeroID() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = testObjectPersisted();
        Bug bug = testObjectBug();

        when(bugDao.insert(any())).thenReturn(testObjectBugIDZero());
        when(userDao.findUser(any())).thenReturn(testObjectUser());
        BugAttachmentWrapperDTO wrapper = bugManager.insertBug(wrapperDTO, 1);

        Assert.assertEquals(wrapper.getBug().getID(), bug.getID());
    }

    @Test(expected = BusinessException.class)
    public void insertBugAttachmentNullOrZeroID() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = testObjectPersisted();
        Bug bug = testObjectBug();
        Attachment attachment = testObjectAttachmentZeroID();

        when(bugDao.insert(any())).thenReturn(testObjectBug());
        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(attachmentDao.insert(any())).thenReturn(testObjectAttachmentZeroID());
        BugAttachmentWrapperDTO wrapperPersisted = bugManager.insertBug(wrapperDTO, 1);

        Assert.assertNotEquals(wrapperPersisted.getBug().getID(), bug.getID());
        Assert.assertEquals(wrapperPersisted.getAttachment().getID(), attachment.getID());
    }

    @Test
    public void insertBugAttachmentSuccess() throws BusinessException {
        BugAttachmentWrapperDTO wrapperDTO = testObjectPersisted();
        User user = testObjectUser();
        Bug bug = testObjectBug();
        Attachment attachment = testObjectAttachment();

        when(bugDao.insert(any())).thenReturn(bug);
        when(userDao.findUser(any())).thenReturn(user);
        when(attachmentDao.insert(any())).thenReturn(attachment);

        BugAttachmentWrapperDTO wrapperPersisted = bugManager.insertBug(wrapperDTO, 1);

        Assert.assertNotNull(wrapperPersisted.getToken());
        Assert.assertEquals(wrapperPersisted.getToken(), "token");

        Assert.assertNotNull(wrapperPersisted.getBug());
        Assert.assertEquals(wrapperPersisted.getBug().getID(), bug.getID());
        Assert.assertEquals(wrapperPersisted.getBug().getDescription(), bug.getDescription());
        Assert.assertEquals(wrapperPersisted.getBug().getFixedVersion(), bug.getFixedVersion());
        Assert.assertEquals(wrapperPersisted.getBug().getSeverity(), bug.getSeverity());
        Assert.assertEquals(wrapperPersisted.getBug().getStatus(), bug.getStatus());
        Assert.assertEquals(wrapperPersisted.getBug().getTitle(), bug.getTitle());
        Assert.assertEquals(wrapperPersisted.getBug().getVersion(), bug.getVersion());
        Assert.assertEquals(wrapperPersisted.getBug().getCREATED_ID().getId(), bug.getCREATED_ID().getID().intValue());
        Assert.assertEquals(wrapperPersisted.getBug().getASSIGNED_ID().getId(), bug.getASSIGNED_ID().getID().intValue());

        Assert.assertNotNull(wrapperPersisted.getAttachment());
        Assert.assertEquals(wrapperPersisted.getAttachment().getID(), attachment.getID());
        Assert.assertEquals(wrapperPersisted.getAttachment().getAttContent(), attachment.getAttContent());
        Assert.assertEquals(wrapperPersisted.getAttachment().getBugID().getID(), attachment.getBugID().getID());
    }

    // ============== BUG UPDATE ===============

//    public Set<RoleDTO> setRoles(){
//        Set<RoleDTO> newSet = new HashSet<>();
//        newSet.add()
//    }

    public UserDTO testObjectUserDTO() {
        return new UserDTO(1, "test", "test", "test", "test",
                0, "test", "077", 1);
    }

    public BugDTO testObjectBugDTO() {
        return new BugDTO(1, "test", "test", "1.1", new Date(2018, 1, 1), "OPEN",
                "1.2", "LOW", testObjectUserDTO(), testObjectUserDTO());
    }

    @Test(expected = BusinessException.class)
    public void updateBug_requestUserID_BussinessException() throws BusinessException {
        bugManager.updateBug(null, null, null);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_requestUserIDZero_BussinessException() throws BusinessException {
        bugManager.updateBug(0, null, null);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugID_BussinessException() throws BusinessException {
        bugManager.updateBug(1, null, null);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugIDZero_BussinessException() throws BusinessException {
        bugManager.updateBug(1, 0, null);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugToUpdate_BussinessException() throws BusinessException {
        bugManager.updateBug(1, 1, null);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_requestUserIDNotInDatabase_BussinessException() throws BusinessException {
        when(userDao.findUser(any())).thenReturn(null);
        bugManager.updateBug(1, 1, testObjectBugDTO());
    }

    @Test(expected = BusinessException.class)
    public void updateBug_requestUserDoesNotHavePermission_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        bugManager.updateBug(1, 1, testObjectBugDTO());
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugIDNotInDatabase_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(null);

        bugManager.updateBug(1, 1, testObjectBugDTO());
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugToUpdateCreatedUserNull_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        BugDTO bugDTO = testObjectBugDTO();
        bugDTO.setCREATED_ID(null);

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(null);

        bugManager.updateBug(1, 1, bugDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugToUpdateAssigndUserNull_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        BugDTO bugDTO = testObjectBugDTO();
        bugDTO.setASSIGNED_ID(null);

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(null);

        bugManager.updateBug(1, 1, bugDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugToUpdateMappedCreatedUserIDNull_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        BugDTO bugDTO = testObjectBugDTO();
        Bug bug = BugDTOEntityMapper.getBug(bugDTO);

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(bug);
        when(userDao.findUser(any())).thenReturn(null);

        bugManager.updateBug(1, 1, bugDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugToUpdateMappedAssignedUserIDNull_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        BugDTO bugDTO = testObjectBugDTO();
        Bug bug = BugDTOEntityMapper.getBug(bugDTO);

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(bug);
        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.findUser(any())).thenReturn(null);

        bugManager.updateBug(1, 1, bugDTO);
    }

    @Test(expected = BusinessException.class)
    public void updateBug_bugToUpdateMappedNotValid_BussinessException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        BugDTO bugDTO = testObjectBugDTO();
        Bug bug = BugDTOEntityMapper.getBug(bugDTO);
        bug.setStatus("");

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(bug);
        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.findUser(any())).thenReturn(testObjectUser());

        bugManager.updateBug(1, 1, bugDTO);
    }

    @Test
    public void updateBug_bugToUpdateMappedValid_NoException() throws BusinessException {
        List<String> permission = new LinkedList<>();
        permission.add("BUG_MANAGEMENT");

        BugDTO bugDTO = testObjectBugDTO();
        Bug bug = BugDTOEntityMapper.getBugWithUserCreatedAndAssigned(bugDTO);

        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.getPermissionsOfUser(1)).thenReturn(permission);
        when(bugDao.getBugByID(any())).thenReturn(bug);
        when(userDao.findUser(any())).thenReturn(testObjectUser());
        when(userDao.findUser(any())).thenReturn(testObjectUser());

        BugDTO updatedBugDTO = bugManager.updateBug(1, 1, bugDTO);

        Assert.assertEquals(updatedBugDTO.getID().intValue(), bugDTO.getID().intValue());
        Assert.assertEquals(updatedBugDTO.getVersion(), bugDTO.getVersion());
        Assert.assertEquals(updatedBugDTO.getTitle(), bugDTO.getTitle());
        Assert.assertEquals(updatedBugDTO.getStatus(), bugDTO.getStatus());
        Assert.assertEquals(updatedBugDTO.getSeverity(), bugDTO.getSeverity());
        Assert.assertEquals(updatedBugDTO.getFixedVersion(), bugDTO.getFixedVersion());
        Assert.assertEquals(updatedBugDTO.getDescription(), bugDTO.getDescription());
        Assert.assertEquals(updatedBugDTO.getTargetDate(), bugDTO.getTargetDate());
    }



}
package ro.msg.edu.jbugs.manager.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.AttachmentDao;
import ro.msg.edu.jbugs.dtoEntityMapper.AttachmentDTOEntityMapper;
import ro.msg.edu.jbugs.entity.Attachment;
import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttachmentManagerTest {

    @InjectMocks
    AttachmentManager attachmentManager;

    @Mock
    AttachmentDao attachmentDao;

    public AttachmentManagerTest(){
        attachmentManager = new AttachmentManager();
    }

    private Attachment createAttachment(int id){
        Attachment attachment = new Attachment();
        attachment.setID(id);
        attachment.setBugID(createBug());

        String string = "test";
        int len = string.length();
        byte[] attContent = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            attContent[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4)
                    + Character.digit(string.charAt(i + 1), 16));
        }
        attachment.setAttContent(attContent);

        return attachment;
    }

    private Bug createBug(){
        Bug bug = new Bug();
        bug.setID(1);
        bug.setTitle("bug1");
        bug.setDescription("bug1");
        bug.setVersion("1.1");
        bug.setTargetDate(new Date(2019, 1, 1));
        bug.setStatus("OPEN");
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
    public void insertAttachment() {
        Attachment attachment = createAttachment(1);
        when(attachmentDao.insert(any())).thenReturn(attachment);
        attachmentManager.insertAttachment(AttachmentDTOEntityMapper.getAttachmentDTO(createAttachment(1)));
    }

    @Test
    public void getAllAtt() {
        Attachment attachment = createAttachment(1);
        Attachment attachment2 = createAttachment(2);

        List<Attachment> attachmentList = new ArrayList<>();
        attachmentList.add(attachment);
        attachmentList.add(attachment2);
        when(attachmentDao.getAllAtt()).thenReturn(attachmentList);
        Assert.assertEquals(attachmentManager.getAllAtt().size(), 2);
    }

    @Test
    public void deleteAttachment() {
        Attachment attachment = createAttachment(1);
        when(attachmentDao.delete(1)).thenReturn(attachment);
        attachmentManager.deleteAttachment(1);

    }
}
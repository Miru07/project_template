package ro.msg.edu.jbugs.manager.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ro.msg.edu.jbugs.dao.BugDao;
import ro.msg.edu.jbugs.dto.BugDTO;
import ro.msg.edu.jbugs.entity.Bug;

import java.sql.Date;

import static org.mockito.Mockito.when;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@RunWith(MockitoJUnitRunner.class)
public class BugManagerTest {

    @InjectMocks
    BugManager bugManager;

    @Mock
    BugDao bugDao;

    public BugManagerTest() {
        bugManager = new BugManager();
    }

    @Test
    public void insertBug() {
        //TODO: test this
        Bug toTest = new Bug("title", "descr", "1.1", new Date(2019, 1, 1), "new", "", "severe", null, null);
        BugDTO toTestDTO = new BugDTO("title", "descr", "1.1", new Date(2019, 1, 1), "new", "", "severe", null, null);

        when(bugDao.insert(toTest)).thenReturn(toTest);

        //BugDTO mockedInsert = bugManager.insertBug(toTestDTO);

        //assertEquals(toTestDTO.getDescription(),mockedInsert.getDescription());

    }
}
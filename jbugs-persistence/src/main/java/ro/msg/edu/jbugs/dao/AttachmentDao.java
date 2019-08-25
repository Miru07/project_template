package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Attachment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class AttachmentDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public List<Attachment> getAttachmentsFromBugID(Integer bugID){
        Query getAttFromBugQuery = entityManager.createNamedQuery(Attachment.FIND_ATTACHMENT_FROM_BUG_ID, Attachment.class);
        getAttFromBugQuery.setParameter("bug_id", bugID);
        return getAttFromBugQuery.getResultList();
    }
}

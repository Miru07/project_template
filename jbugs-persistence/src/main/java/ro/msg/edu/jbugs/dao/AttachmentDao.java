package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Attachment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Data Access Object class for {@link Attachment} objects.
 * It has direct access to the database and all {@link Attachment} related tables.
 *
 * @author Sebastian Maier
 */
@Stateless
public class AttachmentDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    /**
     * @param attachment is an {@link Attachment} object containing data to be
     *                   persisted inside the database.
     * @return an {@link Attachment} object with a persisted ID.
     */
    public Attachment insert(@NotNull Attachment attachment) {

        entityManager.persist(attachment);
        entityManager.flush();

        return attachment;
    }

    public List<Attachment> getAllAtt(){

        return entityManager.createNamedQuery(Attachment.FIND_ALL_ATT, Attachment.class).getResultList();
    }

    public Attachment delete(int attachmentID){
        Attachment attachment = entityManager.find(Attachment.class, attachmentID);

        entityManager.remove(attachment);

        return attachment;
    }
}

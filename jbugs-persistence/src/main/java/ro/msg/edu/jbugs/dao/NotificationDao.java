package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Notification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Data Access Object class for {@link Notification} objects.
 * It has direct access to the database and all {@link Notification} related tables.
 *
 * @author Mara Corina
 */
@Stateless
public class NotificationDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;


    /**
     * @param notification is an {@link Notification} object containing data to be
     *                     persisted inside the database.
     * @return an {@link Notification} object with a persisted ID.
     */
    public Notification insertNotification(Notification notification){
        entityManager.persist(notification);
        return notification;
    }

    public Integer deleteNotification(Integer userID){

        Query query = entityManager.createNativeQuery("DELETE FROM notifications WHERE user_id=?1");
        query.setParameter(1, userID);

        Integer deleteResult = query.executeUpdate();
        return deleteResult;
    }

}

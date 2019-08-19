package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Notification;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class NotificationDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public void insertNotification(Notification notification){

        entityManager.persist(notification);
    }

    public Integer deleteNotification(Integer userID){

        Query query = entityManager.createNativeQuery("DELETE FROM notifications WHERE user_id=?1");
        query.setParameter(1, userID);

        Integer deleteResult = query.executeUpdate();
        return deleteResult;
    }

}

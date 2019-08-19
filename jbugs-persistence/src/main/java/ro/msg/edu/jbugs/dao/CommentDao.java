package ro.msg.edu.jbugs.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;

@Stateless
public class CommentDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    //delete comments older than one year
    public Integer deleteCommentsOlderThan1Year(){

        LocalDate date = LocalDate.now();

        Query query = entityManager.createNativeQuery("DELETE FROM comments WHERE TIMESTAMPDIFF(MONTH, ?1, date) < -12");
        query.setParameter(1, date);

        return query.executeUpdate();

    }
}

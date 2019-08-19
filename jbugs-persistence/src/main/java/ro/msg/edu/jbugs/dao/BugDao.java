package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Stateless
public class BugDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public Bug find(Integer id) {
        return entityManager.find(Bug.class, id);
    }

    public List<Bug> findBugCreatedBy(User user) {
        Query query = entityManager.createNamedQuery(Bug.FIND_BUGS_CREATED_ID, Bug.class);
        return query.setParameter("var_user_id", user.getID()).getResultList();
    }

    public Bug insert(Bug bug) {

        entityManager.persist(bug);
        entityManager.flush();

        return bug;

    }

    // status = closed daca s-a depasit targetDate
    public Integer updateBugStatus(){

        LocalDate date = LocalDate.now();

        Query query = entityManager.createNativeQuery("UPDATE bugs b\n" +
                "SET b.status = 'Closed'\n" +
                "WHERE TIMESTAMPDIFF(DAY, ?1, b.targetDate) < 0 AND b.status <> 'Closed'");
        query.setParameter(1, date);

        return query.executeUpdate();

    }
}


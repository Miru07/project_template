package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class BugDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public Bug getBugByID(Integer bugID) {
        return entityManager.find(Bug.class, bugID);
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

    public Bug updateBugStatus(String newStatus, int bugID){

        Bug bug = entityManager.find(Bug.class, bugID);
        bug.setStatus(newStatus);

        return bug;
    }

    public List<Bug> getAllBugs()
    {
        return entityManager.createNamedQuery(Bug.FIND_ALL_BUGS, Bug.class).getResultList();
    }
}


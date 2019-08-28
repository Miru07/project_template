package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Bug;
import ro.msg.edu.jbugs.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Data Access Object class for {@link Bug} objects.
 * It has direct access to the database and all {@link Bug} related tables.
 */
@Stateless
public class BugDao {

    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;


    /**
     * @param bugID is a {@link Integer} that contains a bug id
     * @return {@link Bug} object with the id bugID
     * @author Miruna Dinu
     */
    public Bug getBugByID(Integer bugID) {
        return entityManager.find(Bug.class, bugID);
    }

    public List<Bug> findBugCreatedBy(User user) {
        Query query = entityManager.createNamedQuery(Bug.FIND_BUGS_CREATED_ID, Bug.class);
        return query.setParameter("var_user_id", user.getID()).getResultList();
    }

    /**
     * @param bug is an {@link Bug} object that contains the data to be
     *            persisted inside the database.
     * @return {@link Bug} object with persisted ID.
     * @author Sebastian Maier.
     */
    public Bug insert(Bug bug) {

        entityManager.persist(bug);
        entityManager.flush();

        return bug;
    }

    /**
     * @param newStatus is a {@link String} that contains the new status of the bug with the bugID id
     * @param bugID is a {@link Integer} that contains the bug id whose status is to be changed
     * @return {@link Bug} object with the new status
     * @author Miruna Dinu
     */
    public Bug updateBugStatus(String newStatus, int bugID){

        Bug bug = getBugByID(bugID);
        bug.setStatus(newStatus);

        return bug;
    }

    /**
     * @return a list of {@link Bug} objects with all the bugs from the database
     * @author Miruna Dinu
     */
    public List<Bug> getAllBugs() {
        return entityManager.createNamedQuery(Bug.FIND_ALL_BUGS, Bug.class).getResultList();
    }
}


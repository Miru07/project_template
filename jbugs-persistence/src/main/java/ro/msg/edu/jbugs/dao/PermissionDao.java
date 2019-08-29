package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Permission;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * Data Access Object class for {@link Permission} objects.
 * It has direct access to the database and all {@link Permission} related tables.
 * @author Mara Corina
 */
@Stateless
public class PermissionDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;


    /**
     * @param id is an {@link Integer}
     * @return {@link Permission} object
     */
    public Permission findPermission(Integer id) {
        return entityManager.find(Permission.class, id);
    }


    /**
     * Gets the permissions with id in the given set of ids
     *
     * @param ids is a set of {@link Integer} objects
     * @return a list of {@link Permission} objects
     */
    public List<Permission> getPermissionsByIds(Set<Integer> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permission> criteriaQuery =
                criteriaBuilder.createQuery(Permission.class);
        Root<Permission> root = criteriaQuery.from(Permission.class);
        criteriaQuery.select(root)
                .where(root.get("ID")
                        .in(ids));
        TypedQuery<Permission> query = entityManager.createQuery(criteriaQuery);
        List<Permission> results = query.getResultList();

        return results;
    }
}

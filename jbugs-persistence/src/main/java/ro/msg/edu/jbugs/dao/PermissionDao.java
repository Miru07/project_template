package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Permission;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */
@Stateless
public class PermissionDao {
    @PersistenceContext(unitName = "jbugs-persistence")
    private EntityManager entityManager;

    public Permission findPermission(Integer id) {
        return entityManager.find(Permission.class, id);
    }
}

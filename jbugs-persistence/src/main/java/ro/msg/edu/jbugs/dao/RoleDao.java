package ro.msg.edu.jbugs.dao;


import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * Data Access Object class for {@link Role} objects.
 * It has direct access to the database and all {@link Role} related tables.
 * @author Mara Corina
 */
@Stateless
public class RoleDao {
    @PersistenceContext(unitName="jbugs-persistence")
    private EntityManager entityManager;


    /**
     * @param id is an {@link Integer}
     * @return {@link Role} object
     */
    public Role findRole(Integer id) {
        return entityManager.find(Role.class, id);
    }

    /**
     * Gets the role with type equal to the given type
     *
     * @param type is a {@link String}
     * @return a {@link Role} object
     */
    public Role findRoleByType(RoleType type) throws BusinessException {
        try{

            return entityManager.createNamedQuery(Role.QUERY_SELECT_BY_TYPE, Role.class)
                    .setParameter("type", type).getSingleResult();
        }catch (NoResultException e){

            throw new BusinessException("msg-001", "Invalid role type");
        }
    }


    /**
     * Returnes a list of {@link Role} objects with the type in the given set of types
     * from the database
     *
     * @param types is a set of {@link String} objects
     * @return a list of {@link Role} objects
     */
    public List<Role> getRolesByTypes(Set<RoleType> types) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery =
                criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root)
                .where(root.get("type")
                        .in(types));
        TypedQuery<Role> query = entityManager.createQuery(criteriaQuery);
        List<Role> results = query.getResultList();

        return results;
    }
}

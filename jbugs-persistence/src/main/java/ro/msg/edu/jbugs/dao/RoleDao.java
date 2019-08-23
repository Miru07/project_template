package ro.msg.edu.jbugs.dao;


import ro.msg.edu.jbugs.entity.Role;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Document me.
 *
 * @author msg systems AG; User Name.
 * @since 19.1.2
 */

@Stateless
public class RoleDao {
    @PersistenceContext(unitName="jbugs-persistence")
    private EntityManager entityManager;

    public Role findRoleByType(String type) throws BusinessException {

        try{

            return entityManager.createNamedQuery(Role.QUERY_SELECT_BY_TYPE, Role.class)
                    .setParameter("type", type).getSingleResult();
        }catch (NoResultException e){

            throw new BusinessException("msg-001", "Invalid role type");
        }
    }
}

package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.google.common.hash.Hashing.*;
import static ro.msg.edu.jbugs.entity.User.FIND_ALL_USERS;

@Stateless
public class UserDao {

    @PersistenceContext(unitName="jbugs-persistence")
    private EntityManager entityManager;

    public User findUser(Integer id){

        return entityManager.find(User.class, id);
    }

    public User insertUser(User user){

        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    public boolean isUsernameUnique(String username){
        Long occurences = entityManager.createNamedQuery(User.QUERY_CHECK_USERNAME_UNIQUE, Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return occurences == 0;
    }

    public List<User> findAllUsers(){

        return entityManager.createNamedQuery(FIND_ALL_USERS, User.class).getResultList();
    }

    public List<Object[]> findUserBugs() {

        Query query = entityManager.createNativeQuery("select u.first_name, u.last_name, count(b.CREATED_ID) as nrBugs\n" +
                "from users u inner join bugs b on u.ID = b.CREATED_ID\n" +
                "group by CREATED_ID");

        return (List<Object[]>) query.getResultList();
    }

//    public Integer deleteUser(Integer userID){
//
//        Query query = entityManager.createNativeQuery("DELETE FROM users WHERE ID=?1");
//        query.setParameter(1, userID);
//
//        return query.executeUpdate();
//    }

    /**
     ********LOGIN**********
     *
     * @param username
     * @param hashedPassword
     * @return User
     * @throws BusinessException
     */
    public User findByUsernameAndHashedPass(String username, String hashedPassword) throws BusinessException {
        User user;
        try {
            user = this.findUserByUsername(username);

            if(user.getStatus() == User.USER_STATUS_INACTIVE){
                throw new BusinessException("msg", "user is inactive");
            }

            if(!user.getPassword().equals(hashedPassword)){
                if(user.getCounter() >= 4){
                    user.setStatus(User.USER_STATUS_INACTIVE);
                    throw new BusinessException("msg-003", "user was deactivated");
                }
                else{
                    user.setCounter(user.getCounter()+1);
                    throw new BusinessException("msg-002", "incorrect password");
                }
            }
            return user;
        } catch(NoResultException e){
            throw new BusinessException("msg-001", "invalid username");
        }
    }

    /**
     ********LOGIN**********
     *
     * @param username
     * @return User
     * @throws BusinessException
     */
    public User findUserByUsername(String username) throws BusinessException {
        User user;
        try {
            user = entityManager.createNamedQuery(User.QUERY_SELECT_BY_USERNAME, User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            return user;
        } catch(NoResultException e){
            throw new BusinessException("msg-001", "invalid username");
        }
    }
}

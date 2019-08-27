package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.google.common.hash.Hashing.sha256;
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

    private String getHashedPassword(String password){
        return sha256().hashString(password, StandardCharsets.UTF_8).toString();
    }
    /**
     ************************************LOGIN********************************************
     * @param username
     * @param password
     * @return User
     * @throws BusinessException
     */
    public User findByUsernameAndPassword(String username, String password) throws BusinessException {
        User user;
        try {
            user = this.findUserByUsername(username); // could throw exception
            // user.setStatus(User.USER_STATUS_ACTIVE);
            if(user.getStatus() == User.USER_STATUS_INACTIVE){
                throw new BusinessException("LOGIN-002", "User is inactive");
            }
            
            String hashedPassword = this.getHashedPassword(password);

            if(!user.getPassword().equals(hashedPassword)){
                int PASS_MAX_NR_TRIES = 4;
                if(user.getCounter() >= PASS_MAX_NR_TRIES){
                    user.setStatus(User.USER_STATUS_INACTIVE);
                    throw new BusinessException("LOGIN-003", "User was deactivated");
                }
                else{
                    user.setCounter(user.getCounter()+1);
                    throw new BusinessException("LOGIN-004", "Incorrect password");
                }
            }
            user.setCounter(0); // if success, set counter for wrongPass to 0
            return user;
        } catch(BusinessException e){
            throw e;
        }
    }
    /**
     ************************************LOGIN********************************************
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
            throw new BusinessException("LOGIN-001", "Invalid username");
        }
    }
    /**
     * **********************************LOGIN********************not sure if needed just yet
     * @param user
     * @return
     */
    public boolean updateStatusAndCounterOfUserIsSuccessful(User user){
        int linesAffected = entityManager.createNamedQuery(User.QUERY_UPDATE_USER_STATUS_AND_COUNTER, Long.class)
                .setParameter("", user.getStatus())
                .setParameter("", user.getCounter())
                .setParameter("", user.getID())
                .executeUpdate();
        if(linesAffected == 1) {
            return true;
        }
        return false;
    }
    /**
     ************************************LOGIN********************************************
     * @param user
     * @return List<String> // permission type...
     */
    public List<String> getPermissionsOfUser(User user){
        return this.getPermissionsOfUser(user.getID());
    }
    public List<String> getPermissionsOfUser(Integer userId){
        List<String> permissions = entityManager.createNamedQuery(User.QUERY_GET_PERMISSIONS, String.class)
                .setParameter("user_id", userId)
                .getResultList();
        return permissions;
    }
}

package ro.msg.edu.jbugs.dao;

import ro.msg.edu.jbugs.entity.Notification;
import ro.msg.edu.jbugs.entity.User;
import ro.msg.edu.jbugs.entity.types.PermissionType;
import ro.msg.edu.jbugs.entity.types.RoleType;
import ro.msg.edu.jbugs.exceptions.BusinessException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

import static com.google.common.hash.Hashing.sha256;
import static ro.msg.edu.jbugs.entity.User.FIND_ALL_USERS;


/**
 * Data Access Object class for {@link User} objects.
 * It has direct access to the database and all {@link User} related tables.
 *
 * @author Mara Corina
 */
@Stateless
public class UserDao {

    @PersistenceContext(unitName="jbugs-persistence")
    private EntityManager entityManager;


    /**
     * @param id is an {@link Integer}
     * @return {@link User} object
     */
    public User findUser(Integer id){

        return entityManager.find(User.class, id);
    }

    /**
     * @param user is an {@link User} object containing data to be
     *                   persisted inside the database.
     * @return an {@link User} object with a persisted ID.
     */
    public User insertUser(User user){
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }

    /**
     * Checks if the given username is unique in the database
     * @param username is a {@link String}
     * @return {@link Boolean}
     */
    public boolean isUsernameUnique(String username){
        Long occurences = entityManager.createNamedQuery(User.QUERY_CHECK_USERNAME_UNIQUE, Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return occurences == 0;
    }


    /**
     * @return a list of {@link User} objects with all the users from the database
     */
    public List<User> findAllUsers(){
        return entityManager.createNamedQuery(FIND_ALL_USERS, User.class).getResultList();
    }

    /**
     * Hashes the given password
     * @param password is a {@link String}
     * @return {@link String}
     */
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
                } else{
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
     * Returns the list of users with a specific role type
     *
     * @param type is a {@link RoleType} object
     * @return a list of {@link User} objects
     */
    public List<User> findUsersByRoleType(RoleType type) throws BusinessException {
        return entityManager.createNamedQuery(User.QUERY_SELECT_BY_ROLE, User.class)
                .setParameter("type", type)
                .getResultList();
    }

    /**
     * Returns the list of users with a specific role type
     *
     * @param type is a {@link RoleType} object
     * @return a list of {@link User} objects
     */
    public List<User> findUsersByPermissionType(PermissionType type) throws BusinessException {
        return entityManager.createNamedQuery(User.QUERY_SELECT_BY_PERMISSION, User.class)
                .setParameter("type", type)
                .getResultList();
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
    public List<PermissionType> getPermissionsOfUser(User user){
        return this.getPermissionsOfUser(user.getID());
    }

    public List<PermissionType> getPermissionsOfUser(Integer userId) {
        List<PermissionType> permissions = entityManager.createNamedQuery(User.QUERY_GET_PERMISSIONS, PermissionType.class)
                .setParameter("user_id", userId)
                .getResultList();
        return permissions;
    }

    /**
     * @param userId is a {@link Integer} object representing the id of the user in the database
     * @param date   is a {@link Date} object
     * @return a list of {@link Notification} objects for a user sent in a specific day
     */
    public List<Notification> getNotificationsByDay(Integer userId, Date date) {
        List<Notification> notifications = entityManager.createNamedQuery(User.QUERY_GET_USER_DAY_NOTIFICATIONS, Notification.class)
                .setParameter("id", userId)
                .setParameter("day", date)
                .getResultList();
        return notifications;
    }

    /**
     * Returns the list of notifications assigned to the user with the given username
     *
     * @param username is a {@link String} object
     * @return a list of {@link Notification} objects
     */
    public List<Notification> getNotificationsByUsername(String username) {
        List<Notification> notifications = entityManager.createNamedQuery(User.QUERY_GET_USER_NOTIFICATIONS_BY_USERNAME, Notification.class)
                .setParameter("username", username)
                .getResultList();
        return notifications;
    }

    /**
     * Returns the list of notifications assigned to the user with the given id
     * sent after the notification with the id given as parameter
     * @param userId is a {@link Integer} object
     * @param notificationId is a {@link Integer} object
     *
     * @return a list of {@link Notification} objects
     */
    public List<Notification> getNewNotificationsById(Integer userId, Integer notificationId) {
        List<Notification> notifications = entityManager.createNamedQuery(User.QUERY_GET_USER_NOTIFICATIONS_AFTER_ID, Notification.class)
                .setParameter("notificationId", notificationId)
                .setParameter("userId", userId)
                .getResultList();
        return notifications;
    }
}

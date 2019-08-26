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

    public User updateUser(User user) {
        User persistedUser = entityManager.find(User.class, user.getID());

        persistedUser.setCounter(user.getCounter());
        persistedUser.setEmail(user.getEmail());
        persistedUser.setFirstName(user.getFirstName());
        persistedUser.setLastName(user.getLastName());
        persistedUser.setMobileNumber(user.getMobileNumber());
        persistedUser.setPassword(user.getPassword());

        persistedUser.setStatus(user.getStatus());

        persistedUser.setRoles(user.getRoles());

        return persistedUser;
    }

    public boolean isUsernameUnique(String username){

        Query query = entityManager.createNamedQuery(User.CHECK_USERNAME_UNIQUE, Integer.class);
        query.setParameter("username", username);

        Long selectResultLong = (Long) query.getSingleResult();
        int selectResult = selectResultLong.intValue();

        return selectResult == 0;

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

    public User findUserByUsernameAndPassword(String username, String password) throws BusinessException {
        String hashPassword = sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        try{

            return entityManager.createNamedQuery(User.QUERY_SELECT_BY_USERNAME_AND_PASSWWORD, User.class)
                    .setParameter("username", username).setParameter("password", hashPassword).getSingleResult();
        }catch (NoResultException e){

            throw new BusinessException("msg-001", "Invalid username or password");
        }
    }

    public User findUserByUsername(String username) throws BusinessException {

        Query query = entityManager.createNamedQuery(User.GET_USER_BY_USERNAME, User.class);
        query.setParameter("username", username);

        try{

            return (User) query.getSingleResult();
        }
        catch(NoResultException ex){
            throw new BusinessException("msg-002", "No user found");
        }
    }
}

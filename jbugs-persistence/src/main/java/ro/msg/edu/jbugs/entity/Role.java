package ro.msg.edu.jbugs.entity;

import ro.msg.edu.jbugs.entity.types.RoleType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="roles")
@NamedQueries({
    @NamedQuery(name = Role.QUERY_SELECT_BY_TYPE, query = "select r from Role r " +
                "where r.type=:type ")
})
public class Role implements Serializable {

    public static final String QUERY_SELECT_BY_TYPE = "Role.getRoleByType";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer ID;

    @Column(name="type")
    @Enumerated(EnumType.STRING)
    private RoleType type;

    @ManyToMany
    @JoinTable(name="users_roles",
            joinColumns = @JoinColumn(name="role_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))
    private Set<User> users = new HashSet<>();

    @ManyToMany
    @JoinTable(name="roles_permissions",
                joinColumns = @JoinColumn(name="role_id"),
                inverseJoinColumns = @JoinColumn(name="permission_id"))
    private Set<Permission> permissions;

    public Role() {
    }

    public Role(Integer id, RoleType type) {
        this.ID = id;
        this.type = type;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public RoleType getType() {
        return type;
    }

    public void setType(RoleType type) {
        this.type = type;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Role{" +
                "ID=" + ID +
                ", type='" + type + '\'' +
                ", users=" + users +
                ", permissions=" + permissions +
                '}';
    }
}

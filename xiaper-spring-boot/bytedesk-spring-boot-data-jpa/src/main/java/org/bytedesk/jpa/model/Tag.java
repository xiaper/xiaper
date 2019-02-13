package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 标签
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "tag")
public class Tag extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_robot")
    private boolean robot;

    /**
     * 用户标签，相关用户
     */
    @ManyToMany(mappedBy = "tags")
    private Set<User> users = new HashSet<>();

    /**
     * 创建者
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRobot() {
        return robot;
    }

    public void setRobot(boolean robot) {
        this.robot = robot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}

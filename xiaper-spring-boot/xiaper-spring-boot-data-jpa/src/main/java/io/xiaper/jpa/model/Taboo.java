package io.xiaper.jpa.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 过滤敏感词、敏感图片等
 *
 * FIXME: comment仅适用于MySQL，不能用于Oracle
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "taboo")
public class Taboo extends AuditModel {

    private static final long serialVersionUID = 7646396785266316831L;

    /**
     *
     * , columnDefinition="bigint COMMENT '主键，自动生成'"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     *
     * , columnDefinition="varchar(100) COMMENT '唯一标识'"
     */
    @Column(name = "tid", unique = true, nullable = false)
    private String tid;

    /**
     * 标准词
     *
     * , columnDefinition="varchar(255) COMMENT '标准词'"
     */
    @Column(name = "standard")
    private String standard;

    /**
     * 是否同义词
     *
     * , columnDefinition="tinyint(1) COMMENT '是否同义词'"
     */
    @Column(name = "is_synonym")
    private boolean synonym;

    /**
     * 相关
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "taboo_related",
            joinColumns = @JoinColumn(name = "standard_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "taboo_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Taboo> taboos = new HashSet<>();

    /**
     * 总公司/创建者
     *
     * , columnDefinition="bigint COMMENT '创建人'"
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false,
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public boolean isSynonym() {
        return synonym;
    }

    public void setSynonym(boolean synonym) {
        this.synonym = synonym;
    }

    public Set<Taboo> getTaboos() {
        return taboos;
    }

    public void setTaboos(Set<Taboo> taboos) {
        this.taboos = taboos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

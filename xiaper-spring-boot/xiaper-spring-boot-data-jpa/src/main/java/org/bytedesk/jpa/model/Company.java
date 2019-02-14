package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 分公司
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "company")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Company extends AuditModel {

    private static final long serialVersionUID = -15592117178863620L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     *
     * 大学长分公司ID，如：CM0000
     */
    @Column(name = "cid", unique = true, nullable = false)
    private String cid;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 默认头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 描述、介绍
     */
    @Column(name = "description")
    private String description;

    /**
     * 一个分公司可以服务多个城市
     * TODO: 需要事先计算好对应关系
     * 考虑到多个注册管理员账号公用一张表，设计成多对多的关系
     * 一个注册管理员下，一个region仅对应一个company
     */
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "company_region",
            joinColumns = @JoinColumn(name = "company_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "region_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Region> regions = new HashSet<>();

    /**
     * 总公司/创建者
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return Objects.equals(cid, company.cid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cid);
    }
}

package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * 地理位置 经纬度信息
 *
 * FIXME: province / city / area / region 表，需要重新规划
 * {@link Province} {@link City} {@link Area}
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "region")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Region implements Serializable {

    private static final long serialVersionUID = 5792936628801644878L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 代码
     */
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 类型
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 经度
     */
    @Column(name = "lng")
    private String lng;

    /**
     * 维度
     */
    @Column(name = "lat")
    private String lat;

    /**
     * 父节点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",
            foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Region parent;

    /**
     * 来自某一个地区的访客，对应到某一个分公司
     * TODO: 需要事先计算好对应关系
     * 考虑到多个注册管理员账号公用一张表，设计成多对多的关系
     * 一个注册管理员下，一个region仅对应一个company
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "regions", fetch = FetchType.LAZY)
    private Set<Company> companies = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Region getParent() {
        return parent;
    }

    public void setParent(Region parent) {
        this.parent = parent;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Region region = (Region) o;

        return this.code.equals(region.getCode());
    }

    @Override
    public int hashCode() {

        return Objects.hash(code);
    }
}

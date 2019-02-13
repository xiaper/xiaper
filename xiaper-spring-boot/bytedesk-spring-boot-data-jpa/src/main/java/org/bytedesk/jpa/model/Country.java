package org.bytedesk.jpa.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 位置信息：国家
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = -3878811647886318018L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     *
     * 大学长国别ID，如：CN0001
     */
    @Column(name = "cid", unique = true, nullable = false)
    private String cid;

    /**
     *
     */
    @Column(name = "name")
    private String name;

    /**
     *
     */
//    @ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//    @JoinTable(name = "country_workgroup", joinColumns = @JoinColumn(name = "country_id"), inverseJoinColumns = @JoinColumn(name = "workgroup_id"))
//    private Set<WorkGroup> workGroups = new HashSet<>();


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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cid);
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Country)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        return this.getCid().equals(((Country)obj).getCid());
    }
}




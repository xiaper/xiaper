package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色表
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = -2461905686314283608L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="value")
	private String value;

	@Column(name="descriptions")
	private String descriptions;

	/**
	 * 区分是工作组内部成员角色
	 * 还是平台级角色
	 */
	@Column(name = "by_type")
	private String type;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	@ManyToMany(targetEntity = Authority.class,fetch = FetchType.EAGER)
	@JoinTable(name = "role_authority",
			joinColumns = @JoinColumn(name = "role_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
			inverseJoinColumns = @JoinColumn(name = "authority_id",
					foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
	private Set<Authority> authorities = new HashSet<>();


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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
}

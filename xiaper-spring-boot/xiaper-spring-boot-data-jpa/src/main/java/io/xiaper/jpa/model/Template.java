package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;


/**
 * 意见反馈: 模板
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "template")
public class Template extends AuditModel {

    private static final long serialVersionUID = 6442550201794583425L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;





    /**
     * 管理员
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;







}

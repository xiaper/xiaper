package org.bytedesk.jpa.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * 验证用户是否有效，用于远程控制
 * 包括私有部署用户：登录之前首先验证，
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "validations")
public class Validation extends AuditModel {

    private static final long serialVersionUID = 8403671170784889690L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 对应用户的uId，
     * 有可能是私有部署用户的uId，每一家私有部署用户所有客服坐席对应同一个uId，写死在单独部署客服端
     *
     * FIXME: uid为Oracle关键字，故修改为uuid
     */
    @Column(name = "uuid", unique = true)
    private String uid;

    /**
     * 账号类型：SaaS、私有部署
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 有效、过期
     */
    @Column(name = "status")
    private String status;

    /**
     * 是否有效
     */
    @Column(name = "is_valid")
    private boolean valid;

    /**
     *
     */
    @Column(name = "start_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     *
     */
    @Column(name = "end_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    /**
     * 提示语，提示给前端用户
     */
    @Column(name = "message")
    private String message;

    /**
     * 后台备注, 自用
     */
    @JsonIgnore
    @Column(name = "note")
    private String note;

    public Validation() {

    }

    public Validation(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

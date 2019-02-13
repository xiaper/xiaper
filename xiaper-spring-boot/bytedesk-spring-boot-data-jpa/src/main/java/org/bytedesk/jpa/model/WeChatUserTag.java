package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "wechat_usertag")
public class WeChatUserTag extends AuditModel {

    private static final long serialVersionUID = 2045947357759705593L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 标签id，由微信分配
     */
    @Column(name = "tag_id")
    private int tagId;

    /**
     * 标签名，UTF8编码
     */
    @Column(name = "name")
    private String name;

    /**
     * 此标签下粉丝数
     */
    @Column(name = "count")
    private int count;

    /**
     * 所对应的公众号id
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wechat_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WeChat weChat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public WeChat getWeChat() {
        return weChat;
    }

    public void setWeChat(WeChat weChat) {
        this.weChat = weChat;
    }
}

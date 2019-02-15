package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 小程序:
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1453779503&token=00bf7cdb1e3a79297fcc360ee1acd92a2e1893cf&lang=zh_CN
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "mini_program_info")
public class MiniProgramInfo extends AuditModel {

    private static final long serialVersionUID = 8276810537813107816L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "wid", unique = true, nullable = false)
    private String wid;

    /**
     *
     * "MiniProgramInfo": {
     *     "network": {
     *         "RequestDomain":["https://www.qq.com","https://www.qq.com"],
     *         "WsRequestDomain":["wss://www.qq.com","wss://www.qq.com"],
     *         "UploadDomain":["https://www.qq.com","https://www.qq.com"],
     *         "DownloadDomain":["https://www.qq.com","https://www.qq.com"],
     *     },
     *     "categories":[{"first":"资讯","second":"文娱"},{"first":"工具","second":"天气"}],
     *     "visit_status": 0,
     * }
     *
     */

    /**
     * network
     */


    /**
     * categories
     */


    /**
     * visit_status
     */
    @Column(name = "visit_status")
    private int visitStatus;

    /**
     *
     */
    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wechat_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WeChat weChat;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }




    public WeChat getWeChat() {
        return weChat;
    }

    public void setWeChat(WeChat weChat) {
        this.weChat = weChat;
    }
}

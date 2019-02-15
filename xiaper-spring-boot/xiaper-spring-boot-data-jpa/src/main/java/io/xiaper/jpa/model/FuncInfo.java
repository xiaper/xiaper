package io.xiaper.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "func_info")
public class FuncInfo extends AuditModel {

    private static final long serialVersionUID = -7076002926239244439L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
        公众号授权给开发者的权限集列表，ID为1到15时分别代表：
        1.消息管理权限
        2.用户管理权限
        3.帐号服务权限
        4.网页服务权限
        5.微信小店权限
        6.微信多客服权限
        7.群发与通知权限
        8.微信卡券权限
        9.微信扫一扫权限
        10.微信连WIFI权限
        11.素材管理权限
        12.微信摇周边权限
        13.微信门店权限
        14.微信支付权限
        15.自定义菜单权限
    */

    /**
     *
     */
    @Column(name = "func_scope_category_id")
    private int funcScopeCategoryId;

    /**
     *
     */
    @JsonIgnore
    @ManyToMany(mappedBy = "funcInfos")
    private Set<WeChat> weChats = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getFuncScopeCategoryId() {
        return funcScopeCategoryId;
    }

    public void setFuncScopeCategoryId(int funcScopeCategoryId) {
        this.funcScopeCategoryId = funcScopeCategoryId;
    }

    public Set<WeChat> getWeChats() {
        return weChats;
    }

    public void setWeChats(Set<WeChat> weChats) {
        this.weChats = weChats;
    }


}




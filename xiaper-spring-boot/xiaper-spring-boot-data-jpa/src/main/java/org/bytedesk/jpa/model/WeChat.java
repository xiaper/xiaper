package org.bytedesk.jpa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 公众号/小程序 信息
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "wechat")
public class WeChat extends AuditModel {

    private static final long serialVersionUID = 4179527580732869301L;

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
     * 下面为服务器部署、消息推送填写信息
     */

    /**
     * 公众号：微信号
     */
    @Column(name = "wx_number")
    private String wxNumber;

    /**
     * 介绍
     */
    @Column(name = "description")
    private String description;

    /**
     * 微信认证
     */
    @Column(name = "authorize")
    private boolean authorize;

    /**
     * 主体信息
     */
    @Column(name = "company")
    private String company;

    /**
     * 服务类别
     */
    @Column(name = "category")
    private String category;

    /**
     * 原始id
     * 使用 userName 字段
     */

    /**
     * app_id
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * app_secret 密钥
     */
    @Column(name = "app_secret")
    private String appSecret;

    /**
     * URL
     */
    @Column(name = "url")
    private String url;

    /**
     * Token
     */
    @Column(name = "token")
    private String token;

    /**
     * aes
     */
    @Column(name = "aes_key")
    private String aesKey;

    /**
     * 加密方式
     */
    @Column(name = "encode_type")
    private String encodeType;

    /**
     * 数据格式
     */
    @Column(name = "data_type")
    private String dataType;


    /**
     *  下面数据 为授权信息
     */


    /**
     * 授权方昵称
     */
    @Column(name = "nick_name")
    private String nickname;

    /**
     * 授权方头像
     */
    @Column(name = "head_img")
    private String headImg;

    /**
     * 授权方公众号类型，0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    @Column(name = "service_type_info")
    private Integer serviceTypeInfo;

    /**
     * 授权方认证类型，-1代表未认证，0代表微信认证，1代表新浪微博认证，2代表腾讯微博认证，
     * 3代表已资质认证通过但还未通过名称认证，4代表已资质认证通过、还未通过名称认证，
     * 但通过了新浪微博认证，5代表已资质认证通过、还未通过名称认证，但通过了腾讯微博认证
     */
    @Column(name = "verify_type_info")
    private Integer verifyTypeInfo;

    /**
     * 授权方公众号的原始ID
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 公众号的主体名称
     */
    @Column(name = "principal_name")
    private String principalName;

    /**
     * 授权方公众号所设置的微信号，可能为空
     */
    @Column(name = "alias")
    private String alias;

    /**
     * 二维码图片的URL，开发者最好自行也进行保存
     */
    @Column(name = "qrcode_url")
    private String qrcodeUrl;

    /**
     *
     */
    @Column(name = "signature")
    private String signature;

    /**
     * 用以了解以下功能的开通状况（0代表未开通，1代表已开通）：
     * open_store:是否开通微信门店功能
     * open_scan:是否开通微信扫商品功能
     * open_pay:是否开通微信支付功能
     * open_card:是否开通微信卡券功能
     * open_shake:是否开通微信摇一摇功能
     */
    @Column(name = "business_info_open_pay")
    private Integer businessInfoOpenPay;

    @Column(name = "business_info_open_shake")
    private Integer businessInfoOpenShake;

    @Column(name = "business_info_open_scan")
    private Integer businessInfoOpenScan;

    @Column(name = "business_info_open_card")
    private Integer businessInfoOpenCard;

    @Column(name = "business_info_open_store")
    private Integer businessInfoOpenStore;

    /**
     * 授权方appid
     */
    @Column(name = "authorizer_app_id")
    private String authorizerAppId;

    @Column(name = "authorization_code")
    private String authorizationCode;

    @Column(name = "authorization_code_expired")
    private String authorizationCodeExpiredTime;

    @Column(name = "authorizer_access_token")
    private String authorizerAccessToken;

    @Column(name = "authorizer_refresh_token")
    private String authorizerRefreshToken;

    @Column(name = "expires_in")
    private int expiresIn;

    /**
     * 区分公众号、小程序
     */
    @Column(name = "is_mini_program")
    private boolean miniProgram;

    /**
     * 小程序信息
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mini_program_info_id")
    private MiniProgramInfo miniProgramInfo;

    /**
     * 多对多关系
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "wechat_funcinfo",
            joinColumns = @JoinColumn(name = "wechat_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "funcinfo_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<FuncInfo> funcInfos = new HashSet<>();

    /**
     * 所属工作组，默认绑定默认工作组，
     * TODO: 允许客服后台修改
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workgroup_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private WorkGroup workGroup;

    /**
     * 创建者
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id", nullable = false, foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private User user;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getServiceTypeInfo() {
        return serviceTypeInfo;
    }

    public void setServiceTypeInfo(Integer serviceTypeInfo) {
        this.serviceTypeInfo = serviceTypeInfo;
    }

    public Integer getVerifyTypeInfo() {
        return verifyTypeInfo;
    }

    public void setVerifyTypeInfo(Integer verifyTypeInfo) {
        this.verifyTypeInfo = verifyTypeInfo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getBusinessInfoOpenPay() {
        return businessInfoOpenPay;
    }

    public void setBusinessInfoOpenPay(Integer businessInfoOpenPay) {
        this.businessInfoOpenPay = businessInfoOpenPay;
    }

    public Integer getBusinessInfoOpenShake() {
        return businessInfoOpenShake;
    }

    public void setBusinessInfoOpenShake(Integer businessInfoOpenShake) {
        this.businessInfoOpenShake = businessInfoOpenShake;
    }

    public Integer getBusinessInfoOpenScan() {
        return businessInfoOpenScan;
    }

    public void setBusinessInfoOpenScan(Integer businessInfoOpenScan) {
        this.businessInfoOpenScan = businessInfoOpenScan;
    }

    public Integer getBusinessInfoOpenCard() {
        return businessInfoOpenCard;
    }

    public void setBusinessInfoOpenCard(Integer businessInfoOpenCard) {
        this.businessInfoOpenCard = businessInfoOpenCard;
    }

    public Integer getBusinessInfoOpenStore() {
        return businessInfoOpenStore;
    }

    public void setBusinessInfoOpenStore(Integer businessInfoOpenStore) {
        this.businessInfoOpenStore = businessInfoOpenStore;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAuthorizerAppId() {
        return authorizerAppId;
    }

    public void setAuthorizerAppId(String authorizerAppId) {
        this.authorizerAppId = authorizerAppId;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public String getAuthorizationCodeExpiredTime() {
        return authorizationCodeExpiredTime;
    }

    public void setAuthorizationCodeExpiredTime(String authorizationCodeExpiredTime) {
        this.authorizationCodeExpiredTime = authorizationCodeExpiredTime;
    }

    public String getAuthorizerAccessToken() {
        return authorizerAccessToken;
    }

    public void setAuthorizerAccessToken(String authorizerAccessToken) {
        this.authorizerAccessToken = authorizerAccessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getAuthorizerRefreshToken() {
        return authorizerRefreshToken;
    }

    public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
        this.authorizerRefreshToken = authorizerRefreshToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<FuncInfo> getFuncInfos() {
        return funcInfos;
    }

    public void setFuncInfos(Set<FuncInfo> funcInfos) {
        this.funcInfos = funcInfos;
    }

    public boolean isMiniProgram() {
        return miniProgram;
    }

    public void setMiniProgram(boolean miniProgram) {
        this.miniProgram = miniProgram;
    }

    public MiniProgramInfo getMiniProgramInfo() {
        return miniProgramInfo;
    }

    public void setMiniProgramInfo(MiniProgramInfo miniProgramInfo) {
        this.miniProgramInfo = miniProgramInfo;
    }

    public WorkGroup getWorkGroup() {
        return workGroup;
    }

    public void setWorkGroup(WorkGroup workGroup) {
        this.workGroup = workGroup;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }
}




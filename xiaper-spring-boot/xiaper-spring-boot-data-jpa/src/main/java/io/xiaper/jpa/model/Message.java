package io.xiaper.jpa.model;

import io.xiaper.jpa.constant.TypeConsts;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 消息，聊天记录
 * TODO: 重构聊天记录表，主表仅存放必要字段，对于非常用字段放另外表存储, 如：针对微信消息不同类型存储到单独表中
 *
 * @author bytedesk.com
 */
@Entity
@Table(name = "message")
public class  Message extends AuditModel {

    private static final long serialVersionUID = -4213959006803708819L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * 唯一数字id，保证唯一性
     * 替代id
     */
    @Column(name = "mid", unique = true, nullable = false)
    private String mid;

    /**
     * 消息所属工作组wid
     * 访客接入会话之前，用于通知工作组内的相关人员
     */
    @Column(name = "wid")
    private String wid;

    /**
     * 联系人对话Contact的uid
     * 一对一会话接收方的uid
     */
    @Column(name = "cid")
    private String cid;

    /**
     * 群组对话Group的wid
     * 群组会话工作组的wid
     */
    @Column(name = "gid")
    private String gid;

    /**
     * 指定坐席会话消息id：
     * 方便客户端查询消息
     *
     * FIXME: 不存在转接会话的情况下，客户端可以直接通过uid查询，但不适用于转接会话的情况，待修改
     *
     * @Column(name = "appoint_id")
     * private String appointId;
     */

    /**
     * 所属thread
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "thread_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Thread thread;

    /**
     * 接入thread之前，在queue中发送的消息
     *
     * TODO: 删除？thread中也含有一个queue，只保留一个？
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "queue_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Queue queue;

    /**
     * 消息类型
     *
     * TODO：枚举，
     * text/image/voice/video/shortvideo/location/
     * link/event/notification
     */
    @Column(name = "by_type")
    private String type;

    /**
     * 会话类型：访客会话thread、联系人一对一会话contact，群组会话group
     */
    @Column(name = "session_type")
    private String sessionType = TypeConsts.MESSAGE_SESSION_TYPE_WORK_GROUP;

    /**
     * 来源客户端
     */
    @Column(name = "client")
    private String client;

    /**
     * 文本消息
     * FIXME: 长度不够，待调整
     */
    @Column(name = "content", length = 512)
    private String content;

    /**
     * 图片消息
     * 微信pic_url，web版容易引起跨域访问问题，所以要使用image_url
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 存储在自己服务器之后的url
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 文件消息类型：文件url
     * 文件类型通过format标示
     */
    @Column(name = "file_url")
    private String fileUrl;

    /**
     * 语音消息
     * 图片+语音+视频+短视频 公用字段
     */
    @Column(name = "media_id")
    private String mediaId;

    /**
     * 语音格式amr等
     */
    @Column(name = "format")
    private String format;

    /**
     * 语音url
     */
    @Column(name = "voice_url")
    private String voiceUrl;

    /**
     * 语音长度
     */
    @Column(name = "length")
    private int length = 0;

    /**
     * 是否已经播放过
     */
    @Column(name = "is_played")
    private boolean played = false;

    /**
     * 视频消息 & 短视频消息
     *
     */
    @Column(name = "thumb_media_id")
    private String thumbMediaId;

    /**
     * 视频和短视频 url
     */
    @Column(name = "video_or_short_url")
    private String videoOrShortUrl;

    /**
     * 视频和短视频 thumb url
     */
    @Column(name = "video_or_short_thumb_url")
    private String videoOrShortThumbUrl;

    /**
     * 地理位置消息
     */
    @Column(name = "location_x")
    private double locationX;

    @Column(name = "location_y")
    private double locationY;

    @Column(name = "scale")
    private double scale;

    @Column(name = "label")
    private String label;

    /**
     * 链接消息
     */
    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    private String url;

    /**
     * 消息发送状态
     */
    @Column(name = "status")
    private String status;

    /**
     * 问卷相关
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionnaire_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Questionnaire questionnaire;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Company company;

    /**
     *
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "message_workgroup",
            joinColumns = @JoinColumn(name = "message_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "workgroup_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<WorkGroup> workGroups = new HashSet<>();

    /**
     * 智能问答，相关问题
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "message_answer",
            joinColumns = @JoinColumn(name = "message_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "answer_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<Answer> answers = new HashSet<>();

    /**
     * TODO: 标记聊天记录已经删除：不再出现在该用户的消息列表中，而非从数据库中删除
     */
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "message_deleted",
            joinColumns = @JoinColumn(name = "message_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)),
            inverseJoinColumns = @JoinColumn(name = "users_id",
                    foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT)))
    private Set<User> deletedSet = new HashSet<>();

    /**
     * 客户端本地id，暂不存储，用于同步客户端发送消息状态
     */
    @Transient
    private String localId;

    /**
     *
     */
    @Transient
    private Group group;

    /**
     * 发布通知
     */
    @Transient
    private Notice notice;

    /**
     * 前端有使用，暂不能ignore
     * 智能问答 一问一答 答案
     */
    @Transient
    private Answer answer;

    /**
     * 需要传递额外信息，如：会话转接、会话邀请等
     */
    @Transient
    private Transfer transfer;

    @Transient
    private Invite invite;

    @Transient
    private Browse browse;

    /**
     * 前端有使用不能ignore
     */
    @Transient
    private BrowseInvite browseInvite;

    /**
     * webrtc: candidate, sessionDescription
     */
    @Transient
    private Object candidate;

    @Transient
    private Object sessionDescription;

    /**
     * 消息发送者
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }

    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

    public String getVideoOrShortUrl() {
        return videoOrShortUrl;
    }

    public void setVideoOrShortUrl(String videoOrShortUrl) {
        this.videoOrShortUrl = videoOrShortUrl;
    }

    public String getVideoOrShortThumbUrl() {
        return videoOrShortThumbUrl;
    }

    public void setVideoOrShortThumbUrl(String videoOrShortThumbUrl) {
        this.videoOrShortThumbUrl = videoOrShortThumbUrl;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Invite getInvite() {
        return invite;
    }

    public void setInvite(Invite invite) {
        this.invite = invite;
    }

    public Browse getBrowse() {
        return browse;
    }

    public void setBrowse(Browse browse) {
        this.browse = browse;
    }

    public BrowseInvite getBrowseInvite() {
        return browseInvite;
    }

    public void setBrowseInvite(BrowseInvite browseInvite) {
        this.browseInvite = browseInvite;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<WorkGroup> getWorkGroups() {
        return workGroups;
    }

    public void setWorkGroups(Set<WorkGroup> workGroups) {
        this.workGroups = workGroups;
    }

    public Object getCandidate() {
        return candidate;
    }

    public void setCandidate(Object candidate) {
        this.candidate = candidate;
    }

    public Object getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(Object sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public Set<User> getDeletedSet() {
        return deletedSet;
    }

    public void setDeletedSet(Set<User> deletedSet) {
        this.deletedSet = deletedSet;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", mid='" + mid + '\'' +
                ", wid='" + wid + '\'' +
                ", cid='" + cid + '\'' +
                ", gid='" + gid + '\'' +
                ", thread=" + thread +
                ", queue=" + queue +
                ", type='" + type + '\'' +
                ", sessionType='" + sessionType + '\'' +
                ", client='" + client + '\'' +
                ", content='" + content + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", mediaId='" + mediaId + '\'' +
                ", format='" + format + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                ", length=" + length +
                ", played=" + played +
                ", thumbMediaId='" + thumbMediaId + '\'' +
                ", videoOrShortUrl='" + videoOrShortUrl + '\'' +
                ", videoOrShortThumbUrl='" + videoOrShortThumbUrl + '\'' +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", scale=" + scale +
                ", label='" + label + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}


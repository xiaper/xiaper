package io.xiaper.jpa.constant;

/**
 *
 * @author bytedesk.com
 */
public class MqConsts {

    /**
     * RabbitMQ
     */
    public static final String EXCHANGE_DEFAULT_TOPIC_NAME = "amq.topic";

    /**
     * 全平台消息，如：新版本上线、维护公告等
     */
    public static final String QUEUE_PLATFORM_MESSAGE = "byteDesk.from.platform";
    public static final String TOPIC_PLATFORM_MESSAGE = "message.platform.#";

    /**
     * 公司消息
     */
    public static final String QUEUE_COMPANY_MESSAGE = "byteDesk.from.subDomain";
    public static final String TOPIC_COMPANY_MESSAGE = "message.subDomain.#";

    /**
     * 工作组消息队列
     */
    public static final String QUEUE_WORK_GROUP_MESSAGE = "byteDesk.from.workGroup";
    public static final String TOPIC_WORK_GROUP_MESSAGE = "message.workGroup.#";

    /**
     * 群组消息队列
     */
    public static final String QUEUE_GROUP_MESSAGE = "byteDesk.from.group";
    public static final String TOPIC_GROUP_MESSAGE = "message.group.#";

    /**
     * 同事消息队列
     */
    public static final String QUEUE_CONTACT_MESSAGE = "byteDesk.from.contact";
    public static final String TOPIC_CONTACT_MESSAGE = "message.contact.#";

    /**
     * 会话Thread消息队列
     */
    public static final String QUEUE_THREAD_MESSAGE = "byteDesk.from.thread";
    public static final String TOPIC_THREAD_MESSAGE = "message.thread.#";

    /**
     * 用户个人消息队列
     */
    public static final String QUEUE_USER_MESSAGE = "byteDesk.from.user";
    public static final String TOPIC_USER_MESSAGE = "message.user.#";

    /**
     * 接收来自mqtt客户端的消息
     *
     * 注意：来自mqtt的消息只需要所有的spring boot broker都监听一个队列即可，其中一个broker收到消息，处理之后，再广播到rabbitmq上去
     */
    public static final String QUEUE_MQTT_MESSAGE = "byteDesk.from.mqtt.message";
    public static final String TOPIC_MQTT_MESSAGE = "message.mqtt";

    /**
     * mqtt 在线状态
     *
     * 注意：来自mqtt的消息只需要所有的spring boot broker都监听一个队列即可，其中一个broker收到消息，处理之后，再广播到rabbitmq上去
     */
    public static final String QUEUE_MQTT_STATUS = "byteDesk.from.mqtt.status";
    public static final String TOPIC_MQTT_STATUS = "status.mqtt";

    /**
     * mqtt last will 遗嘱
     *
     * 注意：来自mqtt的消息只需要所有的spring boot broker都监听一个队列即可，其中一个broker收到消息，处理之后，再广播到rabbitmq上去
     */
    public static final String QUEUE_MQTT_LAST_WILL = "byteDesk.from.mqtt.lastWill";
    public static final String TOPIC_MQTT_LAST_WILL = "lastWill.mqtt";


    /**
     * 广播thread topic
     */
    public static final String TOPIC_USER_PREFIX = "/topic/user.";
    public static final String TOPIC_THREAD_PREFIX = "/topic/thread.";
    public static final String TOPIC_CONTACT_PREFIX = "/topic/contact.";
    public static final String TOPIC_GROUP_PREFIX = "/topic/group.";
    public static final String TOPIC_WORK_GROUP_PREFIX = "/topic/workGroup.";
    public static final String TOPIC_COMPANY_PREFIX = "/topic/subDomain.";
    public static final String TOPIC_PLATFORM_PREFIX = "/topic/platform.";


}






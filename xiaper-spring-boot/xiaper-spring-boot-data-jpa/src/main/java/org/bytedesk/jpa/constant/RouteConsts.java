package org.bytedesk.jpa.constant;

/**
 *
 * @author bytedesk.com
 */
public class RouteConsts {

    /**
     * 广播给所有客服，客服抢单
     */
    public static final String ROUTE_TYPE_PUBSUB = "pubsub";
    /**
     * 轮询分配
     * Round-robin
     */
    public static final String ROUTE_TYPE_ROBIN = "robin";
    /**
     * 熟客优先，最近会话优先分配
     */
    public static final String ROUTE_TYPE_RECENT = "recent";
    /**
     * 当前最空闲
     */
    public static final String ROUTE_TYPE_LEAST = "least";
    /**
     * TODO: 智能分配，还没有想清楚，待后续完善
     */
    public static final String ROUTE_TYPE_SMART = "smart";
    /**
     * TODO: 根据客服满意度评分等其他因素自动分配
     */
    public static final String ROUTE_TYPE_RATE = "rate";



}

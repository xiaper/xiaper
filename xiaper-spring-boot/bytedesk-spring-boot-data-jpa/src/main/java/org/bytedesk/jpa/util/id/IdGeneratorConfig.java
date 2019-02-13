package org.bytedesk.jpa.util.id;

/**
 * ID生成器的配置接口
 * @author Ivan.Ma
 */
public interface IdGeneratorConfig {

    /**
     * 获取分隔符
     * @return string
     */
    String getSplitString();

    /**
     * 获取初始值
     * @return int
     */
    int getInitial();

    /**
     * 获取ID前缀
     * @return string
     */
    String getPrefix();

    /**
     * 获取滚动间隔, 单位: 秒
     * @return int
     */
    int getRollingInterval();

}

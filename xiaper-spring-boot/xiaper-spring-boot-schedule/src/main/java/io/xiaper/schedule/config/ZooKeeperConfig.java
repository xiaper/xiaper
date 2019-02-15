package io.xiaper.schedule.config;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Zookeeper: https://zookeeper.apache.org/doc/current/index.html
 * Curator: https://curator.apache.org/getting-started.html
 *
 * @author bytedesk.com
 */
@Configuration
public class ZooKeeperConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${zk.url}")
    private String zkUrl;

    @Bean
    public CuratorFramework getCuratorFramework() {

        // 重连策略，当zk不可用的时候尝试重连
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zkUrl, retryPolicy);

        client.getCuratorListenable().addListener((curatorFramework, curatorEvent) -> {

            CuratorEventType type = curatorEvent.getType();
            if (type == CuratorEventType.WATCHED) {

                WatchedEvent watchedEvent = curatorEvent.getWatchedEvent();
                Watcher.Event.EventType eventType = watchedEvent.getType();
                logger.info(eventType + ":" + watchedEvent.getPath());

                // FIXME: java.lang.IllegalArgumentException: Path cannot be null
                // client.checkExists().watched().forPath(watchedEvent.getPath());
            }

        });
        client.start();

        return client;
    }

}

package io.xiaper.jpa.util;

import io.xiaper.jpa.model.Ip;
import io.xiaper.jpa.util.id.DefaultIdGenerator;
import io.xiaper.jpa.util.id.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 *
 * @author bytedesk.com
 */
public class JpaUtil {

    private static final Logger logger = LoggerFactory.getLogger(JpaUtil.class);

    /**
     * ID 生成器
     */
    private static final IdGenerator ID_GENERATOR = new DefaultIdGenerator();


    /**
     * 根据时间戳，生成随机唯一id
     * @return 随机
     */
    public static String randomId() {
        return ID_GENERATOR.next();
    }

    /**
     * UUID
     * @return UUID
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取本机ip
     * @return ip
     */
    public static String ipAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "0.0.0.0";
    }

    /**
     * 获取本机hostname
     * @return hostname
     */
    public static String hostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return "hostname";
    }

    /**
     * 生成nickname
     * @param ip ip
     * @return 生成nickname
     */
    public static String nickname(Ip ip) {

        String nickname = "";
        if (ip.getCountry() == "中国") {
            nickname = ip.getRegion() + ip.getCity();
        } else {
            nickname = ip.getCountry();
        }

        return nickname;
    }


    /**
     * 执行shell命令
     * @param cmd cmd
     * @throws Exception ex
     */
    public static void execLinux(String cmd) throws Exception {

        String[] cmdA = { "/bin/sh", "-c", cmd };
        Process process = Runtime.getRuntime().exec(cmdA);
        process.waitFor();

        // 获取返回结果
        LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream(),"UTF-8"));

        String line;
        while ((line = br.readLine()) != null) {
            logger.info(line);
        }
    }



}


package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.WeChatUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface WeChatUserInfoRepository extends JpaRepository<WeChatUserInfo, Long> {

}

package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.model.WeChat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 *
 * @author bytedesk.com
 */
@Repository
public interface WeChatRepository extends JpaRepository<WeChat, Long> {

    Optional<WeChat> findByWid(String wid);

    Optional<WeChat> findFirstByToken(String token);

    Optional<WeChat> findByUserName(String userName);

    Optional<WeChat> findByAuthorizerAppId(String authorizerAppId);

    void deleteByAuthorizerAppId(String authorizerAppId);

    Page<WeChat> findByUserAndMiniProgram(User user, boolean isMiniProgram, Pageable pageable);
}

package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Status;
import org.bytedesk.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 在线状态
 *
 * @author bytedesk.com
 */
@Repository
public interface StatusRepository extends JpaRepository<Status, Long>, JpaSpecificationExecutor {

    Page<Status> findByUserOrUser_User(User user, User admin, Pageable pageable);

    Optional<Status> findByStatusAndSessionIdAndClientAndUser(String status, String sessionId, String client, User user);

    Optional<Status> findFirstByUserOrderByIdDesc(User user);
}

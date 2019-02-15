package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.LeaveMessage;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WorkGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 留言
 *
 * @author bytedesk.com
 */
@Repository
public interface LeaveMessageRepository extends JpaRepository<LeaveMessage, Long>, JpaSpecificationExecutor {

    Page<LeaveMessage> findByUser(User user, Pageable pageable);

    Optional<LeaveMessage> findByLid(String lid);

    Page<LeaveMessage> findByWorkGroup(WorkGroup workGroup, Pageable pageable);

    Page<LeaveMessage> findByAgent(User user, Pageable pageable);
}

package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Queue;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.model.WorkGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 排队
 *
 * @author bytedesk.com
 */
@Repository
public interface QueueRepository extends JpaRepository<Queue, Long>, JpaSpecificationExecutor {

    Page<Queue> findByAgent_User(User user, Pageable pageable);

    Page<Queue> findByWorkGroup_UsersContainsAndStatus(User user, String status, Pageable pageable);

    List<Queue> findByCreatedAtBetween(Date startAt, Date endAt);

    Optional<Queue> findFirstByVisitorAndWorkGroupAndStatus(User user, WorkGroup workGroup, String status);

    Optional<Queue> findFirstByVisitorAndAgentAndStatus(User user, User agent, String status);

    Optional<Queue> findByQid(String qid);

    List<Queue> findByVisitorAndStatus(User user, String status);

}

package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Rate;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WorkGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface RateRepository extends JpaRepository<Rate, Long>, JpaSpecificationExecutor {

    Page<Rate> findByAgentOrAgent_User(User user1, User user2, Pageable pageable);

    Page<Rate> findByAgent(User user, Pageable pageable);

    Page<Rate> findByUser(User user, Pageable pageable);

    Page<Rate> findByAgent_WorkGroupsContains(WorkGroup workGroup, Pageable pageable);

    Page<Rate> findByThread_WorkGroup_User(User user, Pageable pageable);

    Page<Rate> findByThread_WorkGroup_UsersContains(User user, Pageable pageable);

     Page<Rate> findByThread_AgentsContains(User user, Pageable pageable);

    List<Rate> findByCreatedAtBetween(Date startAt, Date endAt);

}

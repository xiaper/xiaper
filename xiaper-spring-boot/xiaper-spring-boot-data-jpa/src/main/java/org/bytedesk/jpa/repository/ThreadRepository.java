package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Group;
import org.bytedesk.jpa.model.User;
import org.bytedesk.jpa.model.Thread;
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
 *
 * @author bytedesk.com
 */
@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long>, JpaSpecificationExecutor {

    Optional<Thread> findFirstByVisitorAndWorkGroupAndAppointedAndClosed(User visitor, WorkGroup workGroup, boolean appointed, boolean closed);

    Optional<Thread> findFirstByVisitorAndAgentAndAppointedAndClosed(User visitor, User agent, boolean appointed, boolean closed);

    Optional<Thread> findByTid(String tid);

    Optional<Thread> findFirstByContactAndAgent(User contact, User agent);

    Optional<Thread> findFirstByGroup(Group group);

    Page<Thread> findByAgentsContainsAndClosed(User agent, boolean closed, Pageable pageable);

    Page<Thread> findByWorkGroup_UserAndClosedAndAgentNotNull(User user, boolean closed, Pageable pageable);

    Page<Thread> findByWorkGroup_UserAndClosedAndAgentNotNullOrAgentOrAgent_User(User user, boolean closed, User user1, User user2, Pageable pageable);

    Page<Thread> findByWorkGroup_UsersContainsAndClosedAndWorkGroup_AdminAndAgentNotNull(User user, boolean closed, User admin, Pageable pageable);

//    Page<Thread> findByAgent_WorkGroups

    Page<Thread> findByVisitor(User visitor, Pageable pageable);


    /**
     * 某管理员账号下的会话：
     *
     *
     * type: thread or appointed
     * closed: true
     */



    /**
     * 某客服组长管理下的会话
     *
     * type: thread or appointed
     * closed: true
     */


    /**
     * 某客服账号自己的全部会话
     *
     * type: thread or appointed
     * closed: true
     */




    /**
     * 找出某个客服*时间至现在的所有会话
     */
    Page<Thread> findByTimestampAfterAndAgentsContains(Date timestamp, User agent, Pageable pageable);

    /**
     * 查询某个时间点之前，而且是否关闭的会话
     */
    List<Thread> findByTimestampBeforeAndTypeAndClosedOrderByIdAsc(Date timestamp, String type, boolean closed);

    /**
     * 某个时间段内进入的会话thread
     */
    List<Thread> findByCreatedAtBetween(Date startAt, Date endAt);

    /**
     * 所有结束/未结束会话
     */
    List<Thread> findByClosed(boolean close);

    /**
     *
     */
    Long countByAgentsContainsAndClosed(User agent, boolean closed);

    List<Thread> findByVisitorAndClosed(User visitor, boolean closed);

    List<Thread> findByVisitorAndSessionIdAndClosed(User visitor, String sessionId, boolean closed);

    List<Thread> findByAgentAndClosed(User agent, boolean closed);

    /**
     * 查询一对一会话的thread
     */
    Optional<Thread> findByTypeAndAgentsContainsAndAgentsContains(String type, User agent1, User agent2);

    List<Thread> findByAgentAndType(User agent, String type);

    List<Thread> findByGroup_MembersContainsAndType(User user, String type);



}

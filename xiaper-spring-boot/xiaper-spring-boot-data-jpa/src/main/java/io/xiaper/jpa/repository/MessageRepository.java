package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Message;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.Thread;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor {

    Optional<Message> findByMid(String mid);

    Page<Message> findByGid(String gid, Pageable pageable);

    Page<Message> findByCidAndUserOrCidAndUser(String cid1, User user1, String cid2, User user2, Pageable pageable);

    Page<Message> findByThread(Thread thread, Pageable pageable);

    Optional<Message> findFirstByThreadAndTypeOrderByIdDesc(Thread thread, String type);

    Page<Message> findByThread_Visitor(User user, Pageable pageable);

    Page<Message> findByThread_WorkGroup_User(User user, Pageable pageable);


    /**
     *
     */
    @Query(value = "select * from message m left outer join thread t on m.thread_id=t.id where m.id < :id and t.visitor_id = :visitor_id", nativeQuery = true)
    Page<Message> findByIdAndThread_Visitor(@Param("id") Long id, @Param("visitor_id") Long visitor_id, Pageable pageable);


    /**
     *
     */
    @Query(value = "select * from message m where m.id < :id and ( ( m.cid = :cid1 and m.users_id = :user1_id ) or ( m.cid = :cid2 and m.users_id = :user2_id ) )", nativeQuery = true)
    Page<Message> findByIdAndCidAndUserOrCidAndUser(@Param("id") Long id, @Param("cid1") String cid1, @Param("user1_id") Long user1_id,
                                                    @Param("cid2") String cid2, @Param("user2_id") Long user2_id, Pageable pageable);

    /**
     * 加载某条聊天记录之后的数据
     */
    @Query(value = "select * from message m where m.id < :id and m.gid = :gid", nativeQuery = true)
    Page<Message> findByIdAndGid(@Param("id") Long id, @Param("gid") String gid, Pageable pageable);

    /**
     * 某个时间段内的所有消息
     */
    List<Message> findByCreatedAtBetweenOrderByIdAsc(Date startAt, Date endAt);

    Optional<Message> findFirstByThreadOrderByIdDesc(Thread thread);
}

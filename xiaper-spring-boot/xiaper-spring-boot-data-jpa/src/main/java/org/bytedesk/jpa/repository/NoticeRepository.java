package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com on 2018/11/26
 */
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    Optional<Notice> findByNid(String nid);
}

package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Browse;
import org.bytedesk.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface BrowseRepository extends JpaRepository<Browse, Long>, JpaSpecificationExecutor {

    Page<Browse> findByWorkGroup_UserAndActioned(User user, String actioned, Pageable pageable);

    Page<Browse> findByWorkGroup_UserAndActionedNotNull(User user, Pageable pageable);

    Optional<Browse> findFirstBySessionIdAndVisitor(String sessionId, User user);

    Optional<Browse> findByBid(String bid);
}

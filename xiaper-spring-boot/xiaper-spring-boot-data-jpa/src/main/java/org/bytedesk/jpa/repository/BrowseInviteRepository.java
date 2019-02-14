package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.BrowseInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface BrowseInviteRepository extends JpaRepository<BrowseInvite, Long> {


    Optional<BrowseInvite> findByBIid(String biid);
}

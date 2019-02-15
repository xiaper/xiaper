package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    Optional<Invite> findByTIid(String tiid);
}

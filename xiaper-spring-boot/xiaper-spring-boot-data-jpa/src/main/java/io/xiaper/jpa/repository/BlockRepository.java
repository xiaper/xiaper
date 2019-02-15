package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Block;
import io.xiaper.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    Optional<Block> findByBid(String bid);

    Optional<Block> findByBlockedUser(User user);

    Optional<Block> findByUserAndBlockedUser(User user, User blockedUser);

    Page<Block> findByUser(User user, Pageable pageable);
}

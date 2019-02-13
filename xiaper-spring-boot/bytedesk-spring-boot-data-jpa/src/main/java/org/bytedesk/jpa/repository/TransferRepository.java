package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Optional<Transfer> findByTTid(String ttid);
}

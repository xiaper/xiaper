package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface ValidationRepository extends JpaRepository<Validation, Long> {

    Optional<Validation> findByUid(String uId);
}

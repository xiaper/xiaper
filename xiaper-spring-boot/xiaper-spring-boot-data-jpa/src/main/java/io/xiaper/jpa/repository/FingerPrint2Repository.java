package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.FingerPrint2;
import io.xiaper.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface FingerPrint2Repository extends JpaRepository<FingerPrint2, Long>, JpaSpecificationExecutor {

    Optional<FingerPrint2> findFirstByKeyAndVisitor(String key, User visitor);

    List<FingerPrint2> findByVisitor(User visitor);

    Set<FingerPrint2> findByVisitorAndKeyIn(User visitor, Set<String> keys);

    Set<FingerPrint2> findByVisitorAndSystem(User visitor, boolean system);

    Optional<FingerPrint2> findByVisitorAndKeyAndSystem(User visitor, String key, boolean system);

}

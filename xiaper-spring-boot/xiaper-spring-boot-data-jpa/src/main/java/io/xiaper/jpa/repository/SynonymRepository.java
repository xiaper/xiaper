package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Synonym;
import io.xiaper.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Long>, JpaSpecificationExecutor {

    Page<Synonym> findByUserAndSynonym(User user, boolean synonym, Pageable pageable);

    Optional<Synonym> findBySid(String sid);

    List<Synonym> findByUser(User user);
}

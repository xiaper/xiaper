package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Company;
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
public interface CompanyRepository extends JpaRepository<Company, Long>, JpaSpecificationExecutor {

    Optional<Company> findByName(String name);

    Optional<Company> findFirstByNameContaining(String name);

    Optional<Company> findByCid(String cid);

    Page<Company> findByUser(User user, Pageable pageable);
}

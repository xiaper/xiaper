package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author bytedesk.com
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority,Long>, JpaSpecificationExecutor {

}

package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Role;
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
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor {

    Optional<Role> findByValue(String value);

    List<Role> findByType(String type);
}

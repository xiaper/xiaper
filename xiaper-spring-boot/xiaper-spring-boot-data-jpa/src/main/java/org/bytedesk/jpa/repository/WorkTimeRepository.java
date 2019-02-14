package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long>, JpaSpecificationExecutor {

}

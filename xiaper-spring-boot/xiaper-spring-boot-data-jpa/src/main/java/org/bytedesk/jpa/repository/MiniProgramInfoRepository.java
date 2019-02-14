package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.MiniProgramInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface MiniProgramInfoRepository extends JpaRepository<MiniProgramInfo, Long> {

}

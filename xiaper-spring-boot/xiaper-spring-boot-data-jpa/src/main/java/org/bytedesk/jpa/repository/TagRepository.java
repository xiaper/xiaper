package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Tag;
import org.bytedesk.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签
 *
 * @author bytedesk.com
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>, JpaSpecificationExecutor {

    List<Tag> findByUserAndRobot(User user, boolean robot);
}

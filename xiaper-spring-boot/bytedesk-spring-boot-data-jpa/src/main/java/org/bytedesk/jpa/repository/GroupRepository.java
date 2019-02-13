package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Group;
import org.bytedesk.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 群
 *
 * @author bytedesk.com
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByGid(String gid);

    List<Group> findByDismissedAndMembersContains(boolean dismissed, User user);
}


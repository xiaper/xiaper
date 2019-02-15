package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Group;
import io.xiaper.jpa.model.User;
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


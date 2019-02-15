package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Role;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WorkGroup;
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
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {

	 Optional<User> findByEmail(String email);

	 Optional<User> findByUsername(String username);

	 Optional<User> findByRealName(String realName);

	 Optional<User> findByUsernameAndSubDomain(String username, String subDomain);

	 Optional<User> findByUserAndUsername(User user, String username);

	 Optional<User> findByMobile(String mobile);

	 Page<User> findByUser(User user, Pageable pageable);

	 Page<User> findByUserAndConnectionStatusAndAcceptStatus(User user, String connectionStatus, String acceptStatus, Pageable pageable);

	 Page<User> findBySubDomainAndConnectionStatusAndAcceptStatus(String subDomain, String connectionStatus, String acceptStatus, Pageable pageable);

	 List<User> findByWorkGroupsContains(WorkGroup workGroup);

	 List<User> findByRolesContains(Role role);

	 List<User> findByRolesNotContains(Role role);

	 Long countBySubDomain(String subDomain);

	 Optional<User> findByUid(String uid);

	 List<User> findByUser(User user);

	 List<User> findByUserAndRolesNotContains(User user, Role role);

	 List<User> findByUserOrUsername(User user, String username);

	 Page<User> findByUserOrUsername(User user, String username, Pageable pageable);

	 Optional<User> findBySubDomainAndRolesContains(String subDomain, Role role);

	 Page<User> findByUserOrUsernameAndRolesNotContains(User user, String username, Role role, Pageable pageable);

	 Page<User> findBySubDomainAndConnectionStatusAndRolesNotContains(String subDomain, String connectionStatus, Role role, Pageable pageable);


}

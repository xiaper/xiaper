package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Company;
import io.xiaper.jpa.model.User;
import io.xiaper.jpa.model.WorkGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface WorkGroupRepository extends JpaRepository<WorkGroup, Long>, JpaSpecificationExecutor {

    Optional<WorkGroup> findByWid(String wid);

    Optional<WorkGroup> findByUserAndNickname(User user, String nickname);

    Optional<WorkGroup> findByCompanyAndUserAndNickname(Company company, User user, String nickname);

    List<WorkGroup> findByUser(User user);

    Page<WorkGroup> findByUser(User user, Pageable pageable);

    List<WorkGroup> findByUsersContains(User user);

    Set<WorkGroup> findByCompany(Company company);

//    Set<WorkGroup> findByCompanyAndCountriesContains(Company company, Country country);
}

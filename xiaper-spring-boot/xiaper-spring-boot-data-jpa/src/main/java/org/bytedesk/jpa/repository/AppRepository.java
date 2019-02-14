package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.App;
import org.bytedesk.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * 测试:
 *  设置了data-rest前缀之后，通过 http://localhost:8000/apps 可以访问测试rest接口
 * //@RepositoryRestResource(path="apps")
 * @author bytedesk.com
 */
@Repository
public interface AppRepository extends JpaRepository<App, Long>, JpaSpecificationExecutor {

    Optional<App> findByAid(String aid);

    Optional<App> findFirstByUser(User user);

    Page<App> findByUserAndType(User user, String type, Pageable pageable);
}

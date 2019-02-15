package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Category;
import io.xiaper.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * 帮助中心Support：类别
 *
 * @author bytedesk.com
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Set<Category> findByUserAndParent(User user, Category parent);

    Set<Category> findByUserAndTypeAndParent(User user, String type, Category category);

    Optional<Category> findByCid(String cid);

    Set<Category> findByType(String type);

    Set<Category> findByTypeAndUser(String type, User user);
}

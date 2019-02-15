package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Article;
import io.xiaper.jpa.model.Category;
import io.xiaper.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 帮助中心Support：文章
 *
 * @author bytedesk.com
 */
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByUser(User user, Pageable pageable);

//    Page<Article> findByUserAndRecommend(User user, boolean recommend, Pageable pageable);

    List<Article> findByUserAndRecommend(User user, boolean recommend);

    Page<Article> findByCategoriesContains(Category category, Pageable pageable);

    Optional<Article> findByAid(String aid);

    List<Article> findByCategoriesContainsOrderByIdDesc(Category category);

    Page<Article> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}

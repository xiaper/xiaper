package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Article;
import io.xiaper.jpa.model.ArticleRate;
import io.xiaper.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface ArticleRateRepository extends JpaRepository<ArticleRate, Long> {

    Optional<ArticleRate> findByArticleAndUser(Article article, User user);
}

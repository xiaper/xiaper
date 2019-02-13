package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Article;
import org.bytedesk.jpa.model.ArticleRate;
import org.bytedesk.jpa.model.User;
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

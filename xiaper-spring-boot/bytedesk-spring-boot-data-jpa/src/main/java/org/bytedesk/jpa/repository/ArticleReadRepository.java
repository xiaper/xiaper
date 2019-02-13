package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.ArticleRead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface ArticleReadRepository extends JpaRepository<ArticleRead, Long> {

}

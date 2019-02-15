package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.AnswerQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface AnswerQueryRepository extends JpaRepository<AnswerQuery, Long> {

}

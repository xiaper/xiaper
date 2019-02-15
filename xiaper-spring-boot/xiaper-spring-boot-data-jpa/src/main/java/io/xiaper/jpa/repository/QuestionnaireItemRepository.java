package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.QuestionnaireItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface QuestionnaireItemRepository extends JpaRepository<QuestionnaireItem, Long> {

}

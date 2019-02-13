package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.QuestionnaireItemItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface QuestionnaireItemItemRepository extends JpaRepository<QuestionnaireItemItem, Long> {

}

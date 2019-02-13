package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.QuestionnaireAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface QuestionnaireAnswerRepository extends JpaRepository<QuestionnaireAnswer, Long> {

}

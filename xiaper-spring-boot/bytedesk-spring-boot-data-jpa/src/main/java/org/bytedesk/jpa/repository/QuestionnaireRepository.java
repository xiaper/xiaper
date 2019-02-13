package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

}

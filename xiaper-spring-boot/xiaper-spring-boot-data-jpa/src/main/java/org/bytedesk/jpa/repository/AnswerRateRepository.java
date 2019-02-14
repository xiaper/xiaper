package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Answer;
import org.bytedesk.jpa.model.AnswerRate;
import org.bytedesk.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface AnswerRateRepository extends JpaRepository<AnswerRate, Long> {

    Optional<AnswerRate> findByAnswerAndUser(Answer answer, User user);

}

package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Answer;
import io.xiaper.jpa.model.AnswerRate;
import io.xiaper.jpa.model.User;
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

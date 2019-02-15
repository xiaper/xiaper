package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Answer;
import io.xiaper.jpa.model.Category;
import io.xiaper.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor {

    Optional<Answer> findByAid(String aid);

//    Optional<Answer> findFirstByQuestionContainingOrAnswerContaining(String question, String answer);

    /**
     * FIXME: and or 执行顺序有问题，实际执行的是 (user and question) or answer, 而需要的是 user and ( question or answer)
     * @param user
     * @param question
     * @param answer
     * @return
     */
    Optional<Answer> findFirstByUserAndQuestionContainingOrAnswerContaining(User user, String question, String answer);

    Page<Answer> findByUserAndRelated(User user, boolean related, Pageable pageable);

    Page<Answer> findByCategoriesContains(Category category, Pageable pageable);

    Set<Answer> findTop5ByUserAndRelatedOrderByQueryCountDesc(User user, boolean related);
}

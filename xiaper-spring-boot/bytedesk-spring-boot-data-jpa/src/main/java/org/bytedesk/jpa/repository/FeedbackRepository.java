package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bytedesk.com on 2019/1/30
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}

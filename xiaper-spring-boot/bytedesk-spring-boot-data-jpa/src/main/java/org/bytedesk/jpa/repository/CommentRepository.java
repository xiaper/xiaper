package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 帮助中心Support：评论
 *
 * @author bytedesk.com
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}

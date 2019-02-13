package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bytedesk.com on 2019/1/23
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}

package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author bytedesk.com on 2019/1/23
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}

package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByUrl(String url);
}

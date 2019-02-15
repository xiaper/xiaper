package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Taboo;
import io.xiaper.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author bytedesk.com on 2018/12/8
 */
public interface TabooRepository extends JpaRepository<Taboo, Long> {

    Page<Taboo> findByUserAndSynonym(User user, boolean synonym, Pageable pageable);

    Optional<Taboo> findByTid(String tid);

}

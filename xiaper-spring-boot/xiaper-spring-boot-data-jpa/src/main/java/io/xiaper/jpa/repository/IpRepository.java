package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Ip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface IpRepository extends JpaRepository<Ip, Long> {

    Optional<Ip> findByIp(String ip);
}

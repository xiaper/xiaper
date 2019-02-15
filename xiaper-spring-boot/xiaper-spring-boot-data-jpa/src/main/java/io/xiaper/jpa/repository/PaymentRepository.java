package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

package org.bytedesk.jpa.repository;

import org.bytedesk.jpa.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}

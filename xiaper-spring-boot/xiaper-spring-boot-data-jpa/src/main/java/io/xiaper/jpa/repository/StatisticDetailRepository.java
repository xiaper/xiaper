package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.StatisticDetail;
import io.xiaper.jpa.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author bytedesk.com
 */
@Repository
public interface StatisticDetailRepository extends JpaRepository<StatisticDetail, Long>, JpaSpecificationExecutor {

    List<StatisticDetail> findByDateAndTimeTypeAndUser(Date date, String timeType, User user);

    Page<StatisticDetail> findByDateAndTimeTypeAndUser(Date date, String timeType, User user, Pageable pageable);

    Page<StatisticDetail> findByDateAndTimeTypeAndUser_User(Date date, String timeType, User user, Pageable pageable);

    Optional<StatisticDetail> findByDateAndHourAndTimeTypeAndDimensionTypeAndUser(Date date, int hour, String timeType, String dimensionType, User user);
}

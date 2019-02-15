package io.xiaper.jpa.repository;

import io.xiaper.jpa.model.Statistic;
import io.xiaper.jpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 统计数据
 *
 * @author bytedesk.com
 */
@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long>, JpaSpecificationExecutor {

    List<Statistic> findByDateAndTypeAndUser(Date date, String type, User user);

    Optional<Statistic> findByDateAndHourAndTypeAndUser(Date date, int hour, String type, User user);



}

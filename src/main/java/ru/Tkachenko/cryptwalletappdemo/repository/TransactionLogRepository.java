package ru.Tkachenko.cryptwalletappdemo.repository;

import ru.Tkachenko.cryptwalletappdemo.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;


public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
    Long countByDateBetween(Date dateFrom, Date dateTo);
    TransactionLog findTopByOrderByDate();
}

package ru.megalomaniac.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.megalomaniac.securities.model.TradingHistory;

public interface TradingHistoryRepository extends JpaRepository<TradingHistory,Long> {

}

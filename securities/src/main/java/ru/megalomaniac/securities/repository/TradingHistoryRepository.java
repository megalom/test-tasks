package ru.megalomaniac.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.megalomaniac.securities.model.TradingHistory;

import java.util.List;

public interface TradingHistoryRepository extends JpaRepository<TradingHistory,Integer> {
    @Query(value="SELECT id,secid,tradedate,numtrades,open,close " +
            "FROM trading_history t " +
            "LIMIT :limit OFFSET :offset",
            nativeQuery = true
    )
    List<TradingHistory> findAllFromPage(@Param("limit") int limit,@Param("offset") int offset);
}

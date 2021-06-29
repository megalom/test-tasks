package ru.megalomaniac.securities.service;

import org.springframework.data.repository.query.Param;
import ru.megalomaniac.securities.model.TradingHistory;

import java.util.List;

public interface TradingHistoryService {
    public List<TradingHistory> findAll();
    public List<TradingHistory> findAllFromPage(int limit,int offset);
    public TradingHistory findById(int id);
    public void save(TradingHistory tradingHistory);
    public void deleteById(int id);
    public long getCount();

}

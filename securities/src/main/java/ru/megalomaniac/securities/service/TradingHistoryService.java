package ru.megalomaniac.securities.service;

import ru.megalomaniac.securities.model.TradingHistory;

import java.util.List;

public interface TradingHistoryService {
    public List<TradingHistory> findAll();
    public TradingHistory findById(int id);
    public void save(TradingHistory tradingHistory);
    public void deleteById(int id);
}

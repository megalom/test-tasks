package ru.megalomaniac.securities.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.model.TradingHistory;
import ru.megalomaniac.securities.repository.TradingHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TradingHistoryServiceImpl implements TradingHistoryService{
    @Autowired
    TradingHistoryRepository tradingHistoryRepository;

    @Override
    public List<TradingHistory> findAll() {
        return tradingHistoryRepository.findAll();
    }

    @Override
    public TradingHistory findById(long id) {
        Optional<TradingHistory> result = tradingHistoryRepository.findById(id);
        TradingHistory tradingHistory = null;
        if(result.isPresent()){
            tradingHistory = result.get();
        }
        else{
            throw new RuntimeException("trading history id "+id+" not found");
        }
        return tradingHistory;
    }

    @Override
    public void save(TradingHistory tradingHistory) {
        tradingHistoryRepository.save(tradingHistory);
    }

    @Override
    public void deleteById(long id) {
        tradingHistoryRepository.deleteById(id);
    }
}

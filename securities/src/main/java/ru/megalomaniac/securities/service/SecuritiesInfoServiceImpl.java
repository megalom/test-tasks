package ru.megalomaniac.securities.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.repository.SecuritiesInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SecuritiesInfoServiceImpl implements SecuritiesInfoService{

    @Autowired
    SecuritiesInfoRepository securitiesInfoRepository;

    @Override
    public List<SecuritiesInfo> findAll() {
        return securitiesInfoRepository.findAll();
    }

    @Override
    public SecuritiesInfo findById(long id) {
        Optional<SecuritiesInfo> result = securitiesInfoRepository.findById(id);
        SecuritiesInfo securitiesInfo = null;
        if(result.isPresent()){
            securitiesInfo = result.get();
        }
        else{
            throw new RuntimeException("securities info id "+id+" not found");
        }
        return securitiesInfo;
    }

    @Override
    public void save(SecuritiesInfo securitiesInfo) {
        securitiesInfoRepository.save(securitiesInfo);
    }

    @Override
    public void deleteById(long id) {
        securitiesInfoRepository.deleteById(id);
    }
}

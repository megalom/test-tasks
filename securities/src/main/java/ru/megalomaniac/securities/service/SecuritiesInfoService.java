package ru.megalomaniac.securities.service;

import ru.megalomaniac.securities.model.SecuritiesInfo;

import java.util.List;

public interface SecuritiesInfoService {
    public List<SecuritiesInfo> findAll();
    public SecuritiesInfo findById(long id);
    public void save(SecuritiesInfo securitiesInfo);
    public void deleteById(long id);
}

package ru.megalomaniac.securities.service;

import ru.megalomaniac.securities.model.SecuritiesInfo;

import java.util.List;

public interface SecuritiesInfoService{
    public List<SecuritiesInfo> findAll();
    public SecuritiesInfo findById(int id);
    public void save(SecuritiesInfo securitiesInfo);
    public void deleteById(int id);
    Boolean existsBySecid(String secid);
    Boolean existById(int id);
    SecuritiesInfo findBySecid(String secid);
}

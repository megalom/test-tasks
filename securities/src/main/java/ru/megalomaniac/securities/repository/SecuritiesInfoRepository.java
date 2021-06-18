package ru.megalomaniac.securities.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.megalomaniac.securities.model.SecuritiesInfo;

public interface SecuritiesInfoRepository extends JpaRepository<SecuritiesInfo,Long> {

}

package ru.yandex.yandex_backend_java.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.yandex_backend_java.enity.ShopUnitHistory;

import java.util.Date;
import java.util.List;

public interface ShopUnitHistoryRepository extends JpaRepository<ShopUnitHistory, Integer> {
    public void deleteAllByIdIs(String id);
    public List<ShopUnitHistory> findAllByDateBetween(Date date, Date date2);
}

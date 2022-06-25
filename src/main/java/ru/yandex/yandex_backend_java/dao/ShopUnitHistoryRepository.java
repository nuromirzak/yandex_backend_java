package ru.yandex.yandex_backend_java.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.yandex_backend_java.enity.ShopUnitHistory;

public interface ShopUnitHistoryRepository extends JpaRepository<ShopUnitHistory, Integer> {
    public void deleteAllByIdIs(String id);
}

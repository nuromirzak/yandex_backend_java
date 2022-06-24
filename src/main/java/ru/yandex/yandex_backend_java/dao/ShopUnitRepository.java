package ru.yandex.yandex_backend_java.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.yandex_backend_java.enity.ShopUnit;

import java.util.List;

public interface ShopUnitRepository extends JpaRepository<ShopUnit, String> {
    public List<ShopUnit> findAllByParentId(String parentId);
}
package ru.yandex.yandex_backend_java.service;


import ru.yandex.yandex_backend_java.enity.ShopUnit;

import java.util.List;

public interface ShopUnitService {
    public List<ShopUnit> getAllShopUnits();

    public List<ShopUnit> getChildren(String id);

    public void saveShopUnit(ShopUnit shopUnit);

    public void saveShopUnitClone(ShopUnit shopUnit);

    public ShopUnit getShopUnit(String id);

    public void deleteShopUnit(String id);

    public void deleteShopUnitHistory(String id);
}
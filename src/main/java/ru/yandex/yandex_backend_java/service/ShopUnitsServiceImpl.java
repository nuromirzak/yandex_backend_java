package ru.yandex.yandex_backend_java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandex_backend_java.dao.ShopUnitHistoryRepository;
import ru.yandex.yandex_backend_java.dao.ShopUnitRepository;
import ru.yandex.yandex_backend_java.enity.ShopUnit;
import ru.yandex.yandex_backend_java.enity.ShopUnitHistory;

import java.util.List;
import java.util.Optional;

@Service
public class ShopUnitsServiceImpl implements ShopUnitService {
    private final ShopUnitRepository repository;
    private final ShopUnitHistoryRepository repository2;

    public ShopUnitsServiceImpl(@Autowired ShopUnitRepository repository,
                                @Autowired ShopUnitHistoryRepository repository2) {
        this.repository = repository;
        this.repository2 = repository2;
    }

    @Override
    public List<ShopUnit> getAllShopUnits() {
        return repository.findAll();
    }

    @Override
    public List<ShopUnit> getChildren(String id) {
        return repository.findAllByParentId(id);
    }

    @Override
    public void saveShopUnit(ShopUnit shopUnit) {
        repository.save(shopUnit);
    }

    @Override
    public void saveShopUnitClone(ShopUnit shopUnit) {
        ShopUnitHistory clone = new ShopUnitHistory(shopUnit);

        repository2.save(clone);
    }

    @Override
    public ShopUnit getShopUnit(String id) {
        ShopUnit shopUnit = null;

        Optional<ShopUnit> res = repository.findById(id);

        if (res.isPresent()) {
            shopUnit = res.get();
        }

        return shopUnit;
    }

    @Override
    @Transactional
    public void deleteShopUnit(String id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteShopUnitHistory(String id) {
        repository2.deleteAllByIdIs(id);
    }
}

package ru.yandex.yandex_backend_java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.yandex_backend_java.dao.ShopUnitRepository;
import ru.yandex.yandex_backend_java.enity.ShopUnit;

import java.util.List;
import java.util.Optional;

@Service
public class ShopUnitsServiceImpl implements ShopUnitService {
    private final ShopUnitRepository repository;

    @Autowired
    public ShopUnitsServiceImpl(ShopUnitRepository repository) {
        this.repository = repository;
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
    public ShopUnit getShopUnit(String id) {
        ShopUnit shopUnit = null;

        Optional<ShopUnit> res = repository.findById(id);

        if (res.isPresent()) {
            shopUnit = res.get();
        }

        return shopUnit;
    }

    @Override
    public void deleteShopUnit(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}

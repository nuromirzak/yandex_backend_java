package ru.yandex.yandex_backend_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandex_backend_java.enity.ShopUnit;
import ru.yandex.yandex_backend_java.enity.ShopUnitHistory;
import ru.yandex.yandex_backend_java.enity.ShopUnitImportRequest;
import ru.yandex.yandex_backend_java.enity.ShopUnitType;
import ru.yandex.yandex_backend_java.exception_handling.NoSuchShopUnitException;
import ru.yandex.yandex_backend_java.helpers.TimestampUtils;
import ru.yandex.yandex_backend_java.helpers.Validators;
import ru.yandex.yandex_backend_java.service.ShopUnitService;

import java.text.ParseException;
import java.util.*;

@RestController
public class MyRESTController {
    private final ShopUnitService shopUnitService;

    @Autowired
    public MyRESTController(ShopUnitService shopUnitService) {
        this.shopUnitService = shopUnitService;
    }

    @PostMapping("/imports")
    @ResponseStatus(HttpStatus.OK)
    public void importShopUnits(@RequestBody ShopUnitImportRequest request) throws ParseException {
        if (!TimestampUtils.matchesISO8601(request.getUpdateDate())) {
            throw new RuntimeException("Validation Failed");
        }

        for (ShopUnit unit : request.getItems()) {
            unit.setDate(TimestampUtils.stringToDate(request.getUpdateDate()));

            updateParents(unit, request.getUpdateDate(), -1);

            int[] sumAndQuantity = getChildrenPrice(unit);

            unit.setSum(sumAndQuantity[0]);
            unit.setQuantity(sumAndQuantity[1]);

            if (unit.getQuantity() > 0) {
                int price = unit.getSum() / unit.getQuantity();

                unit.setPrice(price);
            }

            updateParents(unit, request.getUpdateDate(), 1);

            shopUnitService.saveShopUnit(unit);
            shopUnitService.saveShopUnitClone(unit);
        }
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShopUnit(@PathVariable String id) throws ParseException {
        if (!Validators.isValidUUID(id)) {
            throw new RuntimeException("Validation Failed");
        }

        ShopUnit shopUnit = shopUnitService.getShopUnit(id);

        if (shopUnit == null) {
            throw new NoSuchShopUnitException("Item not found");
        }

        updateParents(shopUnit, null, -1);
        deleteAllChildren(shopUnit.getId());
    }

    @GetMapping("/nodes/{id}")
    public ShopUnit getNodes(@PathVariable String id) {
        if (!Validators.isValidUUID(id)) {
            throw new RuntimeException("Validation Failed");
        }

        ShopUnit shopUnit = shopUnitService.getShopUnit(id);

        if (shopUnit == null) {
            throw new NoSuchShopUnitException("Item not found");
        }

        flatten(shopUnit);

        return shopUnit;
    }

    @GetMapping("/sales")
    public Map<String, List<ShopUnitHistory>> getHistory(@RequestParam String date) throws ParseException {
        if (!TimestampUtils.matchesISO8601(date)) {
            throw new RuntimeException("Validation Failed");
        }

        Date parsedDate = TimestampUtils.stringToDate(date);

        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDate);
        cal.add(Calendar.DATE, -1);
        Date dateBefore1Days = cal.getTime();

        List<ShopUnitHistory> list = shopUnitService.getRecordsBetween(dateBefore1Days, parsedDate);

        return Map.of("list", list);
    }

    @GetMapping("/node/{id}/statistic")
    public Map<String, List<ShopUnitHistory>> getStatistic(@PathVariable String id, @RequestParam String dateStart, @RequestParam String dateEnd) throws ParseException {
        if (!TimestampUtils.matchesISO8601(dateStart)
                || !TimestampUtils.matchesISO8601(dateEnd)
                || !Validators.isValidUUID(id)) {
            throw new RuntimeException("Validation Failed");
        }

        ShopUnit shopUnit = shopUnitService.getShopUnit(id);

        if (shopUnit == null) {
            throw new NoSuchShopUnitException("Item not found");
        }

        Date parsedDate1 = TimestampUtils.stringToDate(dateStart);
        Date parsedDate2 = TimestampUtils.stringToDate(dateEnd);

        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDate2);
        cal.add(Calendar.SECOND, -1);
        Date newDate = cal.getTime();

        List<ShopUnitHistory> items = shopUnitService.getRecordsBetween(parsedDate1, newDate)
                .stream().filter(o -> o.getId().equals(id)).toList();

        return Map.of("items", items);
    }

    private int[] getChildrenPrice(ShopUnit shopUnit) {
        if (shopUnit.getType() == ShopUnitType.OFFER) {
            return new int[]{shopUnit.getPrice(), 1};
        }

        int[] price = new int[2];

        for (ShopUnit unit : shopUnitService.getChildren(shopUnit.getId())) {
            price[0] += unit.getSum();
            price[1] += unit.getQuantity();
        }

        return price;

    }

    private void updateParents(ShopUnit shopUnit, String date, int operation) throws ParseException {
        if (shopUnit.getParentId() == null) return;

        ShopUnit parent = shopUnitService.getShopUnit(shopUnit.getParentId());

        if (parent == null) {
            return;
        }

        if (operation == 1)
            parent.setDate(TimestampUtils.stringToDate(date));

        parent.setSum(parent.getSum() + shopUnit.getSum() * operation);
        parent.setQuantity(parent.getQuantity() + shopUnit.getQuantity() * operation);

        if (parent.getQuantity() > 0) {
            int price = parent.getSum() / parent.getQuantity();

            parent.setPrice(price);
        }

        shopUnitService.saveShopUnit(parent);
        shopUnitService.saveShopUnitClone(parent);

        updateParents(parent, date, operation);
    }

    private void deleteAllChildren(String id) {
        List<ShopUnit> children = shopUnitService.getChildren(id);

        for (ShopUnit child : children) {
            deleteAllChildren(child.getId());
        }

        shopUnitService.deleteShopUnitHistory(id);
        shopUnitService.deleteShopUnit(id);
    }

    private void flatten(ShopUnit shopUnit) {
        if (shopUnit.getType() == ShopUnitType.OFFER) {
            return;
        }

        shopUnit.setChildren(new ArrayList<>());

        for (ShopUnit su : shopUnitService.getChildren(shopUnit.getId())) {
            flatten(su);
            shopUnit.getChildren().add(su);
        }
    }
}

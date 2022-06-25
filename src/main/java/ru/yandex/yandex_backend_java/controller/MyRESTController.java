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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
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

            updateAllParents(unit, request.getUpdateDate());

            shopUnitService.saveShopUnit(unit);
            shopUnitService.saveShopUnitClone(unit);
        }
    }

    private void updateAllParents(ShopUnit shopUnit, String date) throws ParseException {
        if (shopUnit.getParentId() == null) return;

        ShopUnit unit = shopUnitService.getShopUnit(shopUnit.getParentId());

        if (unit == null) {
            return;
        }

        unit.setDate(TimestampUtils.stringToDate(date));

        shopUnitService.saveShopUnit(unit);
        shopUnitService.saveShopUnitClone(unit);

        updateAllParents(unit, date);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteShopUnit(@PathVariable String id) {
        if (!Validators.isValidUUID(id)) {
            throw new RuntimeException("Validation Failed");
        }

        ShopUnit shopUnit = shopUnitService.getShopUnit(id);

        if (shopUnit == null) {
            throw new NoSuchShopUnitException("Item not found");
        }

        deleteAllChildren(shopUnit.getId());
    }

    private void deleteAllChildren(String id) {
        List<ShopUnit> children = shopUnitService.getChildren(id);

        for (ShopUnit child : children) {
            deleteAllChildren(child.getId());
        }

        shopUnitService.deleteShopUnitHistory(id);
        shopUnitService.deleteShopUnit(id);
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

    private int[] flatten(ShopUnit shopUnit) {
        int[] result = new int[2];

        if (shopUnit.getType() == ShopUnitType.OFFER) {
            result[0] += shopUnit.getPrice();
            result[1] += 1;
        } else {
            shopUnit.setChildren(new ArrayList<>());

            for (ShopUnit su : shopUnitService.getChildren(shopUnit.getId())) {
                int[] result2 = flatten(su);
                result[0] += result2[0];
                result[1] += result2[1];
                shopUnit.getChildren().add(su);
            }
        }

        if (result[0] == 0) {
            shopUnit.setPrice(0);
        } else {
            shopUnit.setPrice(result[0] / result[1]);
        }

        return result;
    }

    @GetMapping("/sales")
    public List<ShopUnitHistory> getHistory(@RequestParam String date) throws ParseException {
        Date parsedDate = TimestampUtils.stringToDate(date);


        Calendar cal = Calendar.getInstance();
        cal.setTime(parsedDate);
        cal.add(Calendar.DATE, -1);
        Date dateBefore1Days = cal.getTime();
        return shopUnitService.getRecordsBetween(dateBefore1Days, parsedDate);
    }
}

package ru.yandex.yandex_backend_java.enity;

import java.util.List;

public class ShopUnitImportRequest {
    private List<ShopUnit> items;
    private String updateDate;

    public List<ShopUnit> getItems() {
        return items;
    }

    public void setItems(List<ShopUnit> items) {
        this.items = items;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}

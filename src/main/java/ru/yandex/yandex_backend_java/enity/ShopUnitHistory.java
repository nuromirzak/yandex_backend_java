package ru.yandex.yandex_backend_java.enity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items_history")
public class ShopUnitHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "revNum")
    @JsonIgnore
    private int revNum;

    @Column(name = "id")
    private String id = String.valueOf(UUID.randomUUID());

    @Column(name = "name")
    private String name;

    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;

    @Column(name = "parentId")
    private String parentId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ShopUnitType type;

    @Column(name = "price")
    private Integer price;

    @Column(name = "sum")
    @JsonIgnore
    private int sum;

    @Column(name = "quantity")
    @JsonIgnore
    private int quantity;

    public ShopUnitHistory() {
    }

    public ShopUnitHistory(ShopUnit shopUnit) {
        this.id = shopUnit.getId();
        this.name = shopUnit.getName();
        this.date = shopUnit.getDate();
        this.parentId = shopUnit.getParentId();
        this.type = shopUnit.getType();
        this.price = shopUnit.getPrice();
    }

    public int getRevNum() {
        return revNum;
    }

    public void setRevNum(int revNum) {
        this.revNum = revNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public ShopUnitType getType() {
        return type;
    }

    public void setType(ShopUnitType type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ShopUnitHistory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parentId='" + parentId + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", sum=" + sum +
                ", quantity=" + quantity +
                '}';
    }
}

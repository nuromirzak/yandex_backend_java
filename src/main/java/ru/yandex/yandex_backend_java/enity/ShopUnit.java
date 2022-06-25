package ru.yandex.yandex_backend_java.enity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.yandex.yandex_backend_java.helpers.TimestampUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "items")
public class ShopUnit {
    @Id
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private String id = String.valueOf(UUID.randomUUID());

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Column(name = "date", columnDefinition = "DATETIME")
    @JsonFormat(pattern = TimestampUtils.pattern)
    private Date date;

    @Column(name = "parentId", columnDefinition = "VARCHAR(36)")
    private String parentId;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private ShopUnitType type;

    @Column(name = "price", columnDefinition = "INT")
    private Integer price;

    @Column(name = "sum", columnDefinition = "INT")
    @JsonIgnore
    private int sum;

    @Column(name = "quantity", columnDefinition = "INT")
    @JsonIgnore
    private int quantity;

    //    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private List<ShopUnit> children;

    public ShopUnit() {
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

    public List<ShopUnit> getChildren() {
        return children;
    }

    public void setChildren(List<ShopUnit> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ShopUnit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parentId='" + parentId + '\'' +
                ", type=" + type +
                ", price=" + price +
                ", sum=" + sum +
                ", quantity=" + quantity +
                ", children=" + children +
                '}';
    }
}

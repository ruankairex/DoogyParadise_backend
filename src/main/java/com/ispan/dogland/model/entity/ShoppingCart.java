package com.ispan.dogland.model.entity;

import com.ispan.dogland.model.entity.product.Product;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Integer shoppingCartId;

    private Integer quantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;

    // 對應 user
    @JoinColumn(name = "user_id")
    @ManyToOne
    private Users users;

    // 對應 product
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;


    public ShoppingCart() {
        this.createdTime = new Date();
    }

    public Integer getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(Integer shoppingCartId) {
        this.shoppingCartId = shoppingCartId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ShoppingCart{");
        sb.append("shoppingCartId=").append(shoppingCartId);
        sb.append(", quantity=").append(quantity);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updatedTime=").append(updatedTime);
        sb.append(", users=").append(users);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}
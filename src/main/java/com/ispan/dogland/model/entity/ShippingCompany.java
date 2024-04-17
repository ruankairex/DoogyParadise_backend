package com.ispan.dogland.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shipping_company")
public class ShippingCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_company_id")
    private Integer shippingCompanyId;

    private String shippingCompanyName;

    private Integer shippingCompanyFreight;

    public ShippingCompany() {
    }

    public ShippingCompany(Integer shippingCompanyId, String shippingCompanyName, Integer shippingCompanyFreight) {
        this.shippingCompanyId = shippingCompanyId;
        this.shippingCompanyName = shippingCompanyName;
        this.shippingCompanyFreight = shippingCompanyFreight;
    }

    public Integer getShippingCompanyId() {
        return shippingCompanyId;
    }

    public void setShippingCompanyId(Integer shippingCompanyId) {
        this.shippingCompanyId = shippingCompanyId;
    }

    public String getShippingCompanyName() {
        return shippingCompanyName;
    }

    public void setShippingCompanyName(String shippingCompanyName) {
        this.shippingCompanyName = shippingCompanyName;
    }

    public Integer getShippingCompanyFreight() {
        return shippingCompanyFreight;
    }

    public void setShippingCompanyFreight(Integer shippingCompanyFreight) {
        this.shippingCompanyFreight = shippingCompanyFreight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ShippingCompany{");
        sb.append("shippingCompanyId=").append(shippingCompanyId);
        sb.append(", shippingCompanyName='").append(shippingCompanyName).append('\'');
        sb.append(", shippingCompanyFreight=").append(shippingCompanyFreight);
        sb.append('}');
        return sb.toString();
    }
}

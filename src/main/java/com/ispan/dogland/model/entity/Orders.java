package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore  //後來加的
    private Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)  //"orders"對應OrderDetail的orders屬性
    private List<OrderDetail> orderDetails;

//    @ManyToOne(cascade = {CascadeType.ALL})
//    @JoinColumn(name = "employee_id")
    private Integer employeeId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderDate;

    private Integer totalPrice;

    private Integer discountPrice;

    private Integer couponPrice;

    private String discountDescription;

    private String couponDescription;

    private Integer paymentMethod;

    private Integer paymentStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date confirmDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date shipDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date logisticsShipDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date logisticsArrivalDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date userReceiveDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date userConfirmDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date orderCancelDate;

//    @ManyToOne
//    @JoinColumn(name = "shipping_company_id")
//    private ShippingCompany shippingCompany;

    private String city;

    private String district;

    private String address;

    private Integer freight;

//    public void add(OrderDetail tmpOrderDetail){
//        if(orderDetail==null){
//            orderDetail = new ArrayList<>();
//        }
//        orderDetail.add(tmpOrderDetail);
//        tmpOrderDetail.setOrder(this);
//    }

    public Orders() {
        // 初始化日期欄位
        this.orderDate = new Date(); // 設定訂單日期為當前日期和時間
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getCouponPrice() {
        return couponPrice;
    }

    public void setCouponPrice(Integer couponPrice) {
        this.couponPrice = couponPrice;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public Date getShipDate() {
        return shipDate;
    }

    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }

    public Date getLogisticsShipDate() {
        return logisticsShipDate;
    }

    public void setLogisticsShipDate(Date logisticsShipDate) {
        this.logisticsShipDate = logisticsShipDate;
    }

    public Date getLogisticsArrivalDate() {
        return logisticsArrivalDate;
    }

    public void setLogisticsArrivalDate(Date logisticsArrivalDate) {
        this.logisticsArrivalDate = logisticsArrivalDate;
    }

    public Date getUserReceiveDate() {
        return userReceiveDate;
    }

    public void setUserReceiveDate(Date userReceiveDate) {
        this.userReceiveDate = userReceiveDate;
    }

    public Date getUserConfirmDate() {
        return userConfirmDate;
    }

    public void setUserConfirmDate(Date userConfirmDate) {
        this.userConfirmDate = userConfirmDate;
    }

    public Date getOrderCancelDate() {
        return orderCancelDate;
    }

    public void setOrderCancelDate(Date orderCancelDate) {
        this.orderCancelDate = orderCancelDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Orders{");
        sb.append("orderId=").append(orderId);
        sb.append(", users=").append(users);
        sb.append(", orderDetails=").append(orderDetails);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", orderDate=").append(orderDate);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", discountPrice=").append(discountPrice);
        sb.append(", couponPrice=").append(couponPrice);
        sb.append(", discountDescription='").append(discountDescription).append('\'');
        sb.append(", couponDescription='").append(couponDescription).append('\'');
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", confirmDate=").append(confirmDate);
        sb.append(", shipDate=").append(shipDate);
        sb.append(", logisticsShipDate=").append(logisticsShipDate);
        sb.append(", logisticsArrivalDate=").append(logisticsArrivalDate);
        sb.append(", userReceiveDate=").append(userReceiveDate);
        sb.append(", userConfirmDate=").append(userConfirmDate);
        sb.append(", orderCancelDate=").append(orderCancelDate);
        sb.append(", city='").append(city).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", freight=").append(freight);
        sb.append('}');
        return sb.toString();
    }

}

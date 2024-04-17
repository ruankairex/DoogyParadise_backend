package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.OrderCancelRepository;
import com.ispan.dogland.model.dao.OrderDetailRepository;
import com.ispan.dogland.model.dao.OrdersRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.product.ProductGalleryRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.ProductDto;
import com.ispan.dogland.model.entity.OrderCancel;
import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductGalleryRepository productGalleryRepository;
    @Autowired
    private OrderCancelRepository orderCancelRepository;

    public List<OrderDetail> findDetailByOrderId(Integer orderId){
        return orderDetailRepository.findByOrderId(orderId);
    }

    public List<ProductDto> getProductsFromOrderDetails(List<Integer> productIds){
        List<Product> pList = new ArrayList<>();
        List<ProductDto> pDtoList = new ArrayList<>();
        Product p = new Product();
        List<String> imgUrls = new ArrayList<>();
        int index = 0;
        System.out.println(productIds);
        for(int i :productIds){
            System.out.println(i);
            for(ProductGallery pgs:productGalleryRepository.findByProductId(i)){
                imgUrls.add(pgs.getImgPath());
            }
            p = productRepository.findSingleByProductId(i);
            pList.add(p);
        }
        for(Product pTmp:pList) {
            ProductDto pDto = new ProductDto();
            BeanUtils.copyProperties(pTmp, pDto);
            pDto.setMainImgPath(imgUrls.get(index));
            pDtoList.add(pDto);
            index++;
        }
        index=0;
        System.out.println(pDtoList);
        return pDtoList;
    }

    public List<Orders> findOrdersByUserId (Integer userId){
        return ordersRepository.findOrdersByUserId(userId);
    }

    public void addOrderCancelCase(Integer orderId){
        Orders o = ordersRepository.findByOrderId(orderId);
        if(o.getPaymentStatus()==1){
            o.setPaymentStatus(6);
        }else{
            o.setPaymentStatus(5);
        }
        o.setOrderCancelDate(new Date());
        ordersRepository.save(o);

        OrderCancel oc = new OrderCancel();
        oc.setIsRead(0);

        OrderCancel oc2 = orderCancelRepository.save(oc);
        oc2.setOrders(o);
        orderCancelRepository.save(oc2);
    }

    public String ecpayCheckout(String price, String url) {

        String uuId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

        AllInOne all = new AllInOne("");

        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuId);
        obj.setMerchantTradeDate("2017/01/01 08:05:23");
        obj.setTotalAmount(price);
        obj.setTradeDesc("test Description");
        obj.setItemName("TestItem");
        obj.setReturnURL("<http://211.23.128.214:5000>");
        obj.setClientBackURL("http://localhost:5173/" + url);
        obj.setNeedExtraPaidInfo("N");
        String form = all.aioCheckOut(obj, null);

        return form;
    }
}


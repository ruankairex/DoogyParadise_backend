package com.ispan.dogland.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.dto.*;
import com.ispan.dogland.model.entity.Comment;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.Collection;
import com.ispan.dogland.model.vo.*;
import com.ispan.dogland.service.ShopService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
import java.util.*;

@RestController
public class ShopController {

    private String allTransactionId;  //用在linepay的transactionId
    @Autowired
    private ShopService shopService;

    //商品列表與商品分頁(增加搜尋功能)
    @GetMapping("/products-keyword/{pageNumber}")
    public Page<ProductDto> findProductByPageWithKeyword(@PathVariable Integer pageNumber,
                                                         @RequestParam(required = false) String keyword) {
        Page<ProductDto> productDtos = shopService.findProductByPageWithKeyword(pageNumber, keyword);
        return productDtos;
    }

    //用類別查詢商品
    @GetMapping("/category/{pageNumber}/{categoryId}")
    public Page<ProductDto> findByCategory(@PathVariable Integer pageNumber,
                                           @PathVariable Integer categoryId) {
        Page<ProductDto> products = shopService.findByCategoryId(pageNumber, categoryId);
        return products;
    }

    //根據productID回傳商品
    @GetMapping("/productPage/{productId}")
    public List<ProductDto> findProductByPageWithKeyword(@PathVariable Integer productId) {
        List<ProductDto> productDtos = shopService.findByProductPage(productId);
        return productDtos;
    }

    ///加入購物車(totalAddToCart)
    @RequestMapping("/totalAddToCart/add/{productId}")
    public ShoppingCart totalAddToCart(@PathVariable Integer productId,
                                       @RequestParam(required = false) Integer quantity,
                                      HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.totalAddToCart(loggedInMember.getUserId(),productId,quantity);
    }

    //拿到購物車的商品
    @RequestMapping("/cart")
    public List<ShoppingCartDto> getCartByMemberId( HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
//        System.out.println(loggedInMember);
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        List<ShoppingCartDto> cartItems = shopService.findShoppingCartByMemberId(loggedInMember.getUserId());
        return cartItems;
    }

    //刪除購物車
    @RequestMapping("/product/delete/{productId}")
    public ShoppingCart deleteProductFromCart(@PathVariable Integer productId, HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.deleteProductFromCart(loggedInMember.getUserId(),productId);
    }

    //結帳後刪除購物車(依照會員ID)
    @RequestMapping("/product/deleteByUser")
    public List<ShoppingCart> deleteCartByUser(HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.deleteCartByUser(loggedInMember.getUserId());
    }

    //加入訂單及訂單明細
    @PostMapping("/order/{totalCartPrice}")
    public void getCartData(@RequestBody List<OderDto> oderDtos,
                            @PathVariable Integer totalCartPrice,
                            HttpSession session) {
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        Orders order = shopService.addOrder(loggedInMember.getUserId(), oderDtos,totalCartPrice);
    }

    //實作加入收藏愛心
    @RequestMapping("/addCollect/{productId}")
    public Collection AddToCollect(@PathVariable Integer productId, HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.addToCollect(loggedInMember.getUserId(),productId);
    }

    //實作刪除收藏愛心
    @RequestMapping("/deleteCollect/{productId}")
    public void DeleteToCollect(@PathVariable Integer productId, HttpSession session){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        shopService.deleteCollection(loggedInMember.getUserId(),productId);
    }

    //實作檢查愛心
    @RequestMapping("/checkCollect/{productId}")
    public int checkCollect(@PathVariable Integer productId, HttpSession session) {
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.checkByCollection(loggedInMember.getUserId(),productId);
    }

    //拿到收藏的商品
    @GetMapping("/collection/{pageNumber}")
    public Page<CollectionDto> getCollectByMemberId(HttpSession session,@PathVariable Integer pageNumber){
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        Page<CollectionDto> collects = shopService.findCollectionByUserId(loggedInMember.getUserId(),pageNumber);
        return collects;
    }

    //實作銷售數量
    @GetMapping("/orderDetail/salesVolume")
    public List<Object[]> sumQuantityByProductId() {
        return shopService.sumQuantityByProductId();
    }

    //實作新增照片(評論)
    @PostMapping("/addComment")
    public Comment addProduct(HttpSession session,
                              @RequestParam("productId") Integer productId,
                              @RequestParam("messageText") String messageText,
                              @RequestParam("ratingValue") Integer ratingValue,
                              @RequestParam("productImage") MultipartFile productImage) {
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        if(loggedInMember == null){
            throw new RuntimeException("未登入錯誤");
        }
        return shopService.addComment(loggedInMember.getUserId(),productId,messageText,ratingValue,productImage);
    }

    //實作找尋評論(商品內頁)
    @GetMapping("/findAllComment/{productId}")
    public List<Object[]> findAllCommentByProductId(@PathVariable Integer productId) {
        return shopService.findAllCommentByProductId(productId);
    }

    @GetMapping("/findAllCommentAndStar/{productId}/{star}")
    public List<Object[]> findCommentWithUserByProductIdAndStar(@PathVariable Integer productId,
                                                                @PathVariable Integer star) {
        return shopService.findCommentWithUserByProductIdAndStar(productId,star);
    }

    //-----------------------------LinePay----------------------------------
    @PostMapping("/linepay/{totalPrice}")
    public String sendlinepay(@PathVariable String totalPrice) {
        CheckoutPaymentRequestForm form = new CheckoutPaymentRequestForm();

        form.setAmount(new BigDecimal(totalPrice));  //Amount=Price*Quantity
        form.setCurrency("TWD");
        form.setOrderId(UUID.randomUUID().toString());  //這個不能一樣(order_id)先用UUID

        ProductPackageForm productPackageForm = new ProductPackageForm();
        productPackageForm.setId(UUID.randomUUID().toString()); //自動生成package_id(先用UUID)
        productPackageForm.setName("dogshop");
        productPackageForm.setAmount(new BigDecimal(totalPrice));   //跟上面的Amount要一樣

        ProductForm productForm = new ProductForm();
        productForm.setId("product_id");
        productForm.setName("Dog Shop");
        productForm.setImageUrl("");
        productForm.setQuantity(new BigDecimal("1"));
        productForm.setPrice(new BigDecimal(totalPrice));

        productPackageForm.setProducts(Arrays.asList(productForm));
        form.setPackages(Arrays.asList(productPackageForm));

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setConfirmUrl("http://localhost:5173/shop/shopPage"); //轉址的網址
        form.setRedirectUrls(redirectUrls);

        String ChannelId="2003912658";
        String ChannelSecret = "b852dee8fbe5117039966646d0ff44e5";
        String requestUri = "/v3/payments/request";
        String nonce = UUID.randomUUID().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(form);
            String signature = ShopService.encrypt(ChannelSecret, ChannelSecret + requestUri + requestBody + nonce);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-LINE-ChannelId", ChannelId);
            headers.set("X-LINE-Authorization-Nonce", nonce);
            headers.set("X-LINE-Authorization", signature);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            String linePayApiUrl = "https://sandbox-api-pay.line.me/v3/payments/request";
            String response = restTemplate.postForObject(linePayApiUrl, requestEntity, String.class);

            JsonNode jsonResponse = objectMapper.readTree(response);
            String paymentUrl = jsonResponse.get("info").get("paymentUrl").get("web").asText();
            String transactionId = jsonResponse.get("info").get("transactionId").asText();
            allTransactionId = transactionId;
            System.out.println("response => " + response);
            System.out.println("transactionId =>" + transactionId);

            return paymentUrl;  // 轉址到獲取的 URL
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null; // 如果發生異常，返回 null 或者其他適當的值
    }

    @PostMapping("/confirm/{totalPrice}")
    public String sendconfirm(@PathVariable String totalPrice) {
        //ConfirmAPI
        String ChannelId="2003912658";  //這個要用自己的
        String ChannelSecret = "b852dee8fbe5117039966646d0ff44e5";  //這個要用自己的
        String requestBody;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ConfirmData confirmData =new ConfirmData();
            confirmData.setAmount(new BigDecimal(totalPrice));
            confirmData.setCurrency("TWD");
            String confirmNonce = UUID.randomUUID().toString();
            String confirmUrl ="/v3/payments/" + allTransactionId + "/confirm";
            requestBody = objectMapper.writeValueAsString(confirmData);

            String confirmSignature = ShopService.encrypt(ChannelSecret, ChannelSecret + confirmUrl + requestBody + confirmNonce);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("X-LINE-ChannelId", ChannelId);
            headers.set("X-LINE-Authorization-Nonce", confirmNonce);
            headers.set("X-LINE-Authorization", confirmSignature);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            String linePayApiUrl = "https://sandbox-api-pay.line.me/v3/payments/" + allTransactionId + "/confirm"; //transactionId
            String response = restTemplate.postForObject(linePayApiUrl, requestEntity, String.class);

            return "sellerok";
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null; // 如果發生異常，返回 null 或者其他適當的值
    }

}

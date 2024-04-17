package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.*;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dto.*;
import com.ispan.dogland.model.entity.*;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShopService {

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CommentRepository commentRepository;

    // 根據頁碼搜尋商品(增加搜尋功能)
    public Page<ProductDto> findProductByPageWithKeyword(Integer pageNumber, String keyword) {
        Page<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.findAllByProductNameContaining(PageRequest.of(pageNumber, 8), keyword);
        } else {
            products = productRepository.findAll(PageRequest.of(pageNumber, 8));
        }
        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pto = new ProductDto();
            BeanUtils.copyProperties(p, pto);
            // 獲取產品的所有圖片路徑列表
            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : p.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            pto.setImgPath(imgPaths);
            return pto;
        });
        return productDtos;
    }

    //根據類別搜尋商品
    public Page<ProductDto> findByCategoryId(Integer pageNumber,Integer categoryId){
        Pageable pageable = PageRequest.of(pageNumber, 8);
        Page<Product> products = productRepository.findByCategoryId(pageable, categoryId);
        Page<ProductDto> productDtos = products.map(p -> {
            ProductDto pto = new ProductDto();
            BeanUtils.copyProperties(p, pto);
            // 獲取產品的所有圖片路徑列表
            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : p.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            pto.setImgPath(imgPaths);
            return pto;
        });
        return productDtos;
    }

    //根據productID回傳商品(productPage)
    public List<ProductDto> findByProductPage(Integer productId) {
        List<Product> products = productRepository.findByProductId(productId);
        List<ProductDto> productDtos = products.stream().map(product -> {
            ProductDto dto = new ProductDto();
            BeanUtils.copyProperties(product, dto);
            // 獲取產品的所有圖片路徑列表
            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : product.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            dto.setImgPath(imgPaths);
            return dto;
        }).collect(Collectors.toList());
        return productDtos;
    }

    //共用版加入購物車
    public ShoppingCart totalAddToCart(Integer userId, Integer productId, Integer quantity) {
        Users user = new Users(userId);
        Product product = new Product(productId);

        ShoppingCart shoppingCartItem = shoppingCartRepository.findByUsersAndProduct(user, product);

        if (shoppingCartItem != null && quantity == null) {
            // 如果購物車項目存在且數量為null，增加一個數量
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + 1);
        }
        if (shoppingCartItem != null && quantity != null) {
            // 如果購物車項目存在且數量不為null，設置新的數量
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + quantity);
        }
        if (shoppingCartItem == null && quantity == null) {
            // 如果購物車項目不存在且數量為null，創建一個新的購物車項目，並將數量設置為1
            shoppingCartItem = new ShoppingCart();
            shoppingCartItem.setUsers(user);
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(1);
        }
        if (shoppingCartItem == null && quantity != null) {
            // 如果購物車項目不存在且數量不為null，創建一個新的購物車項目，並將數量設置為指定的數量
            shoppingCartItem = new ShoppingCart();
            shoppingCartItem.setUsers(user);
            shoppingCartItem.setProduct(product);
            shoppingCartItem.setQuantity(quantity);
        }
        return shoppingCartRepository.save(shoppingCartItem);
    }

    // 根據會員ID取得購物車
    public List<ShoppingCartDto> findShoppingCartByMemberId(Integer userId){
        List<ShoppingCart> shoppingCartItems = shoppingCartRepository.findByUsers(new Users(userId));

        List<ShoppingCartDto> shoppingCartDtos = shoppingCartItems.stream().map(c ->{
            ShoppingCartDto ct =new ShoppingCartDto();
            Product p = c.getProduct();
            BeanUtils.copyProperties(p , ct);
            BeanUtils.copyProperties(c , ct);

            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : p.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            ct.setImgPath(imgPaths);
            return ct;
        }).toList();
        return shoppingCartDtos;
    }

    //刪除指定商品以及指定會員的購物車項目
    public ShoppingCart deleteProductFromCart(Integer userId, Integer productId) {
        Users user = new Users(userId);
        Product product = new Product(productId);

        ShoppingCart shoppingCartItem = shoppingCartRepository.findByUsersAndProduct(user, product);

        if (shoppingCartItem != null) {
            shoppingCartRepository.delete(shoppingCartItem); // 找到了購物車項目，進行刪除
        }
        return null; // 返回被刪除的購物車項目，或者返回null
    }

    //結帳後刪除購物車(依照會員ID)
    public List<ShoppingCart> deleteCartByUser(Integer userId) {
        Users user = new Users(userId);
        List<ShoppingCart> shoppingCartItem = shoppingCartRepository.findByUsers(user);
        // 遍歷購物車項目，並逐個刪除
        for (ShoppingCart cartItem : shoppingCartItem) {
            shoppingCartRepository.delete(cartItem);
        }
        return shoppingCartItem;
    }

    //加入訂單及訂單明細
    public Orders addOrder(Integer userId, List<OderDto> oderDtos,Integer totalCartPrice) {
        Users user = new Users(userId);
        Orders order = new Orders();
        order.setUsers(user);
        order.setTotalPrice(totalCartPrice);
        order.setPaymentStatus(1); //1是已付款
        order.setPaymentMethod(0); //0是信用卡
        order = ordersRepository.save(order);

        for (OderDto oderDto : oderDtos) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(order);
            BeanUtils.copyProperties(oderDto, orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        return order;
    }

    //實作加入收藏愛心
    public Collection addToCollect(Integer userId, Integer productId) {
        Users user = new Users(userId);
        Product product = new Product(productId);
        //尋找有沒有一樣的userId跟productId
        Collection existingCollection = collectionRepository.findByUsersAndProduct(user, product);

        if (existingCollection == null) {
            Collection collection = new Collection();
            collection.setUsers(user);
            collection.setProduct(product);
            collection.setCollect(1);
            return collectionRepository.save(collection);
        //返回是2的話，代表已經有加過了，下面這個其實可以不用，已經用前端判斷處理
        } else {
            Collection newCollection = new Collection();
            newCollection.setUsers(user);
            newCollection.setProduct(product);
            newCollection.setCollect(2);
            return newCollection;
        }
    }

    //實作刪除收藏愛心
    public void deleteCollection(Integer userId, Integer productId) {
        Users user = new Users(userId);
        Product product = new Product(productId);
        //尋找有沒有一樣的userId跟productId
        Collection existingCollection = collectionRepository.findByUsersAndProduct(user, product);
        if(existingCollection!=null) {
            collectionRepository.deleteByUsersAndProduct(user, product);
        //返回一個RuntimeException，下面這個其實可以不用，已經用前端判斷處理
        }else {
            throw new RuntimeException("沒有可刪除的收藏");
        }
    }

    //實作檢查愛心
    public int checkByCollection(Integer userId, Integer productId) {
        Users user = new Users(userId);
        Product product = new Product(productId);
        Collection collection = collectionRepository.findByUsersAndProduct(user, product);
        if (collection == null) {
            return 0;
        } else {
            return collection.getCollect();
        }
    }

    // 根據會員ID取得收藏
    public Page<CollectionDto> findCollectionByUserId(Integer userId, Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, 3);
        Page<Collection> collectionPage = collectionRepository.findByUsers(new Users(userId), pageable);

        return collectionPage.map(c -> {
            CollectionDto collect = new CollectionDto();
            Product p = c.getProduct();
            BeanUtils.copyProperties(p, collect);
            BeanUtils.copyProperties(c, collect);

            List<String> imgPaths = new ArrayList<>();
            for (ProductGallery gallery : p.getProductGalleries()) {
                imgPaths.add(gallery.getImgPath());
            }
            collect.setImgPath(imgPaths);
            return collect;
        });
    }

    //實作銷售數量
    public List<Object[]> sumQuantityByProductId() {
        return orderDetailRepository.sumQuantityByProductId();
    }

    //實作新增照片(評論)
    public Comment addComment(Integer userId,Integer productId,String messageText,Integer ratingValue, MultipartFile file) {
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
            Users user = new Users(userId);
            Product product = new Product(productId);

            Comment comment = new Comment();
            comment.setUsers(user);
            comment.setProduct(product);
            comment.setStar(ratingValue);
            comment.setCommentary(messageText);
            comment.setPhotoPath((String) data.get("url"));
            comment.setStatus("已評論");

            return commentRepository.save(comment);
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    //實作找尋評論(商品內頁)
    public List<Object[]> findAllCommentByProductId(Integer productId) {
        return commentRepository.findCommentWithUserByProductId(productId);
    }

    //實作找尋評論(商品內頁)加star
    public List<Object[]> findCommentWithUserByProductIdAndStar(Integer productId,Integer star) {
        return commentRepository.findCommentWithUserByProductIdAndStar(productId,star);
    }

    //------------------------LinePay------------------------
    //Hmac 簽章
    public static String encrypt(final String keys, final String data) {
        return toBase64String(HmacUtils.getInitializedMac(HmacAlgorithms.HMAC_SHA_256, keys.getBytes()).doFinal(data.getBytes()));
    }
    //Base64
    public static String toBase64String(byte[] bytes) {
        byte[] byteArray = Base64.encodeBase64(bytes);
        return new String(byteArray);
    }

}

package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Comment;
import com.ispan.dogland.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    //尋找(Comment、Users、Product)三張表的欄位資料
//    @Query("SELECT c.commentId, c.star, c.quality, c.commentary, c.photoPath, c.postDate, u.userId, u.lastName, u.firstName FROM Comment c JOIN c.users u")
//    List<Object[]> findAllCommentsWithUserDetails();

    //依照傳進來的ProductId尋找(Comment、Users、Product)三張表的欄位資料
    @Query("SELECT c.commentId, c.star, c.status, c.commentary, c.photoPath, c.postDate, u.userId, u.lastName, u.firstName " +
            "FROM Comment c JOIN c.users u " +
            "WHERE c.product.productId = :productId")
    List<Object[]> findCommentWithUserByProductId(Integer productId);

    //依照傳進來的ProductId跟star尋找(Comment、Users、Product)三張表的欄位資料
    @Query("SELECT c.commentId, c.star, c.status, c.commentary, c.photoPath, c.postDate, u.userId, u.lastName, u.firstName " +
            "FROM Comment c JOIN c.users u " +
            "WHERE c.product.productId = :productId AND c.star = :star")
    List<Object[]> findCommentWithUserByProductIdAndStar(Integer productId, Integer star);

}

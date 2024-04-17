package com.ispan.dogland.model.entity.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "comment_gallery")
public class CommentGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imgId;


    private String imgPath;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "comment_id")
    private ArticleComments comment;

    public CommentGallery() {
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public ArticleComments getComment() {
        return comment;
    }

    public void setComment(ArticleComments comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommentGallery{");
        sb.append("imgId=").append(imgId);
        sb.append(", imgPath='").append(imgPath).append('\'');
        sb.append(", comment=").append(comment);
        sb.append('}');
        return sb.toString();
    }
}

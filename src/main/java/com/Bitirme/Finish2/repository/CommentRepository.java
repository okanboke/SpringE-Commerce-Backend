package com.Bitirme.Finish2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Bitirme.Finish2.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUserIdAndProductId(Long userId, Long productId);    //jpa parametreler gelince database'den ilgili alanı getirecek

    List<Comment> findByUserId(Long userId);                                //sadece userId gelirse...

    List<Comment> findByProductId(Long productId);                          //sadece productId gelirse

    @Query(value = "select 'commented on', c.product_id, u.avatar, u.user_name from "
            + "comment c left join user u on u.id = c.user_id "
            + "where c.product_id in :productIds limit 5", nativeQuery = true) //yorumlardan user aktivity için ürün idlerini çekecek
    List<Object> findUserCommentsByProductId(@Param("productIds") List<Long> productIds);
}

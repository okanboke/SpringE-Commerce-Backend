package com.Bitirme.Finish2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Bitirme.Finish2.entities.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByUserIdAndProductId(Long userId, Long productId);

    List<Like> findByUserId(Long userId);

    List<Like> findByProductId(Long productId);

    @Query(value = "select 'liked', l.product_id, u.avatar, u.user_name from "
            + "p_like l left join user u on u.id = l.user_id "
            + "where l.product_id in :productIds limit 5", nativeQuery = true) //yorumlardan user aktivity için ürün idlerini çekecek
    List<Object> findUserLikesByProductId(@Param("productIds") List<Long> productIds);

}

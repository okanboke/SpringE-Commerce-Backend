package com.Bitirme.Finish2.repository;

import java.util.List;

import com.Bitirme.Finish2.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Bitirme.Finish2.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByUserId(Long userId); //userId ile ürünleri çekme

    @Query(value = "select id from product where user_id = :userId order by create_date desc limit 5 ",
            nativeQuery = true) //alınacak verinin querysi
    List<Long> findTopByUserId(@Param("userId") Long userId); //user aktivity için sadece son 5 product id yi getirecek

    List<Product> findByCategories(Category catgoty);



}

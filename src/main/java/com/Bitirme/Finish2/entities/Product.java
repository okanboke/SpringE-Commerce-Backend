package com.Bitirme.Finish2.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Data;

@Entity
@Table(name="product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id üretecek
    Long id;
                                        // request objesi yaratıldığında EAGER olur
    @ManyToOne(fetch = FetchType.EAGER) //bire çok ilişki uyguluyoruz bir çok ürünün tek bir user'ı olabilir.
    @JoinColumn(name="user_id", nullable = false) //JoinColumn ile tabloları birbirine bağlıyoruz.
    @OnDelete(action = OnDeleteAction.CASCADE) //Bir user silindiğinde onun bütün ürünleri silinmeli.
    User user;


    float price;
    String title;
    String image;
    @Lob
    @Column(columnDefinition="text")
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
            @JoinTable(name="product_category",joinColumns = {
                    @JoinColumn(name="product_id")}
                    ,inverseJoinColumns = {@JoinColumn(name="category_id")})
    Set<Category> categories = new HashSet<Category>();

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
/*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name="categories_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE) //Bir user silindiğinde onun bütün ürünleri silinmeli.

    Category categories;
*/
/*
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
*/

}

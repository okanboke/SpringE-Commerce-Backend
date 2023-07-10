package com.Bitirme.Finish2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="purchase")
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id üretecek
    Long id;
    
    @ManyToOne(fetch = FetchType.LAZY) //bire çok ilişki uyguluyoruz bir çok ürünün tek bir user'ı olabilir.
    @JoinColumn(name="product_id", nullable = false) //JoinColumn ile tabloları birbirine bağlıyoruz.
    @OnDelete(action = OnDeleteAction.CASCADE) //Bir user silindiğinde onun bütün ürünleri silinmeli.
    @JsonIgnore
    Product product;

    @ManyToOne(fetch = FetchType.LAZY) //bire çok ilişki uyguluyoruz bir çok ürünün tek bir user'ı olabilir.
    @JoinColumn(name="user_id", nullable = false) //JoinColumn ile tabloları birbirine bağlıyoruz.
    @OnDelete(action = OnDeleteAction.CASCADE) //Bir user silindiğinde onun bütün ürünleri silinmeli.
    @JsonIgnore
    User user;

}

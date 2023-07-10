package com.Bitirme.Finish2.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="p_like")
@Data
public class Like {

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

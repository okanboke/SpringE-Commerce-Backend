package com.Bitirme.Finish2.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="comment")
@Data
public class Comment {

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

    @Lob
    @Column(columnDefinition="text") //Hibernate in Stringi text olarak algılaması için
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;
}

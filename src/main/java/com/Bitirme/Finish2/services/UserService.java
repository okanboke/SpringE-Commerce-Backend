package com.Bitirme.Finish2.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.Bitirme.Finish2.entities.User;
import com.Bitirme.Finish2.repository.CommentRepository;
import com.Bitirme.Finish2.repository.LikeRepository;
import com.Bitirme.Finish2.repository.ProductRepository;
import com.Bitirme.Finish2.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;
    LikeRepository likeRepository; //user aktivitesi için
    CommentRepository commentRepository; //user aktivitesi için
    ProductRepository productRepository; //user aktivites için

    public UserService(UserRepository userRepository, LikeRepository likeRepository,
                       CommentRepository commentRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.productRepository = productRepository;
    }

    public List<User> getAllUsers() {                   //repository'e bağlanıp userları çeker.
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) { //repository'e bağlanıp user kaydı yapar.
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) { //repository'e gidip aranılan userı getirecek.
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) { //repository'e gidip user update işlemini gerçekleştirir.
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) { //obje var
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            foundUser.setAvatar(newUser.getAvatar()); //user avatarı değştirmek için
            userRepository.save(foundUser);
            return foundUser;
        }else //böyle bir user yok
            return null;
    }

    public void deleteById(Long userId) {
        try {
            userRepository.deleteById(userId);
        }catch(EmptyResultDataAccessException e) { //user zaten yok, db'den empty result gelmiş
            System.out.println("User "+userId+" doesn't exist"); //istersek loglayabiliriz
        }
    }

    public User getOneUserByUserName(String userName) { //database'e gidip böyle bir user var mı yok mu onu kontrol edeceğiz.
        return userRepository.findByUserName(userName);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> productIds = productRepository.findTopByUserId(userId);//productrepositoryde yazılan query ile databaseden istenen veriler
        if(productIds.isEmpty()) //productIds boşsa
            return null;
        List<Object> comments = commentRepository.findUserCommentsByProductId(productIds); //commentler varsa user aktivityi için alacak
        List<Object> likes = likeRepository.findUserLikesByProductId(productIds); //likelar varsa user aktivityi için alacak.
        List<Object> result = new ArrayList<>(); //üstteki iki listeyi birleştiriyoruz
        result.addAll(comments);
        result.addAll(likes);
        return result;

    }
}

package com.Bitirme.Finish2.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Bitirme.Finish2.entities.Like;
import com.Bitirme.Finish2.entities.Product;
import com.Bitirme.Finish2.entities.User;
import com.Bitirme.Finish2.repository.LikeRepository;
import com.Bitirme.Finish2.requests.LikeCreateRequest;
import com.Bitirme.Finish2.responses.LikeResponse;


@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private ProductService productService;

    public LikeService(LikeRepository likeRepository, UserService userService,
                       ProductService productService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> productId) {
        List<Like> list;
        if(userId.isPresent() && productId.isPresent()) {
            list = likeRepository.findByUserIdAndProductId(userId.get(), productId.get());
        }else if(userId.isPresent()) {
            list = likeRepository.findByUserId(userId.get());
        }else if(productId.isPresent()) {
            list = likeRepository.findByProductId(productId.get());
        }else
            list = likeRepository.findAll();
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getOneLikeById(Long LikeId) {
        return likeRepository.findById(LikeId).orElse(null);
    }

    public Like createOneLike(LikeCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId());
        Product product = productService.getOneProductById(request.getProductId());
        if(user != null && product != null) {
            Like likeToSave = new Like();
            likeToSave.setId(request.getId());
            likeToSave.setProduct(product);
            likeToSave.setUser(user);
            return likeRepository.save(likeToSave);
        }else
            return null;
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }


}
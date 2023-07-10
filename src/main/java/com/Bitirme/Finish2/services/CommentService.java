package com.Bitirme.Finish2.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Bitirme.Finish2.entities.Comment;
import com.Bitirme.Finish2.entities.Product;
import com.Bitirme.Finish2.entities.User;
import com.Bitirme.Finish2.repository.CommentRepository;
import com.Bitirme.Finish2.requests.CommentCreateRequest;
import com.Bitirme.Finish2.requests.CommentUpdateRequest;
import com.Bitirme.Finish2.responses.CommentResponse;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private UserService userService;
    private ProductService productService;

    public CommentService(CommentRepository commentRepository, UserService userService, ProductService productService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public List<CommentResponse> getAllCommentsWithParam(Optional<Long> userId, Optional<Long> productId) {
        List<Comment> comments;
        if(userId.isPresent() && productId.isPresent()) {                                       //userId veya productId gelirse commentlere GET isteği yapacak
            comments = commentRepository.findByUserIdAndProductId(userId.get(), productId.get());   //iki parametreyide repositorye gönderiyoruz
        }else if(userId.isPresent()) {                                                          //sadece userId parametresi gelirse
            comments =  commentRepository.findByUserId(userId.get());
        }else if(productId.isPresent()) {                                                           //sadece productId parametresi gelirse
            comments = commentRepository.findByProductId(productId.get());
        }else
            comments = commentRepository.findAll();                                                    //herangi bir parametre gelmezse bütün commentler gelecek
        return comments.stream().map(comment -> new CommentResponse(comment)).collect(Collectors.toList());
    }

    public Comment getOneCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null); //eğer parametre gelirse commentId üzerinden spesifik commenti getirecek
    }

    public Comment createOneComment(CommentCreateRequest request) {
        User user = userService.getOneUserById(request.getUserId()); //Comment oluşturmak için user ve userservice sınıfıyla iletişimde olacak
        Product product = productService.getOneProductById(request.getProductId()); //Comment oluşturmak için product ve productservisiyla iletişimde olacak
        if(user != null && product != null) { //user veya product boş değilse kayıt işlemimizi yapacağız
            Comment commentToSave = new Comment();
            commentToSave.setId(request.getId());
            commentToSave.setProduct(product);
            commentToSave.setUser(user);
            commentToSave.setText(request.getText());
            commentToSave.setCreateDate(new Date()); //yorum atılma zamanı
            return commentRepository.save(commentToSave); //repoya gidip commentToSave'i kaydedecek
        }else
            return null;
    }

    public Comment updateOneCommentById(Long commentId, CommentUpdateRequest request) {
        Optional<Comment> comment = commentRepository.findById(commentId); //optional bir commentId si alıyoruz
        if(comment.isPresent()) { //Comment varsa eğer
            Comment commentToUpdate = comment.get(); //Comment update için get metodu
            commentToUpdate.setText(request.getText()); //DTO dan gelen requestin textiyle update işlemini gerçekleştiriyoruz
            return commentRepository.save(commentToUpdate); //repodan update işlemini gerçekleştiriyoruz
        }else
            return null;
    }

    public void deleteOneCommentById(Long commentId) {
        commentRepository.deleteById(commentId); //deleteById ile repodan ilgili commentId'yi siliyoruz...
    }
}

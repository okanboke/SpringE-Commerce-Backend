package com.Bitirme.Finish2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.Bitirme.Finish2.entities.Comment;
import com.Bitirme.Finish2.requests.CommentCreateRequest;
import com.Bitirme.Finish2.requests.CommentUpdateRequest;
import com.Bitirme.Finish2.responses.CommentResponse;
import com.Bitirme.Finish2.services.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments
            (@RequestParam Optional<Long> userId,
             @RequestParam Optional<Long> productId) {                      //List olarak Commenteri tutacak userId ve productId parametre olarak alacak ve getirecek GET isteği
        return commentService.getAllCommentsWithParam(userId, productId); //bu parametreler gelirse serviste metoda gidecek
    }

    @PostMapping
        public Comment createOneComment(@RequestBody CommentCreateRequest request) { //POST işlemi için DTO classıyla istek yapacak
        return commentService.createOneComment(request); //serviste metoda gidecek...

    }

    @GetMapping("/{commentId}")
    public Comment getOneComment(@PathVariable Long commentId) { //spesifik bir comment getireceğiz commentId üzerinden
        return commentService.getOneCommentById(commentId);     //servisteki metoda gidecek...
    }

    @PutMapping("/{commentId}")
    public Comment updateOneComment(@PathVariable Long commentId,
                                    @RequestBody CommentUpdateRequest request) { //spesifik bir Commenti update edeceğizz PUT işlemi UpdateRequest DTO'sundan update isteği alacağız
        return commentService.updateOneCommentById(commentId, request);
    }

    @DeleteMapping("/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId) { //spesifik bir commenti silme işlemi
        commentService.deleteOneCommentById(commentId); //servise metoda gidecek...
    }
}

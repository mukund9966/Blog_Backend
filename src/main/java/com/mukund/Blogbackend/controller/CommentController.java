package com.mukund.Blogbackend.controller;

import com.mukund.Blogbackend.entity.Comment;
import com.mukund.Blogbackend.payload.Commentdto;
import com.mukund.Blogbackend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @PostMapping("comment/{id}")
    public ResponseEntity<?> createComment(@PathVariable long id, @RequestBody Commentdto commentdto){
        return new ResponseEntity<>(commentService.createComment(id, commentdto), HttpStatus.CREATED);
    }

    @GetMapping("/{postId}/comments")
    public List<Commentdto> getAllCommentsForPost(@PathVariable Long postId) {
        return commentService.getAllCommentsForPost(postId);
    }


    @GetMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<Commentdto> getCommentForPost(@PathVariable Long commentId, @PathVariable Long postId){
        Commentdto commentdto  = commentService.getCommentForPost(postId, commentId);
        return new ResponseEntity<>(commentdto, HttpStatus.OK);
    }

    @PutMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<?> updateCommentForPost(@PathVariable Long commentId, @PathVariable Long postId, @RequestBody Commentdto commentdto){
        Commentdto commentdto1 =  commentService.updateComment(postId, commentId, commentdto);
        return new ResponseEntity<>(commentdto1, HttpStatus.OK);

    }

    @DeleteMapping("/comment/{postId}/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @PathVariable Long postId){
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("deleted", HttpStatus.OK);
    }
}

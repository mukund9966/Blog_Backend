package com.mukund.Blogbackend.service.impl;

import com.mukund.Blogbackend.entity.Comment;
import com.mukund.Blogbackend.entity.Post;
import com.mukund.Blogbackend.exception.BlogAPIException;
import com.mukund.Blogbackend.exception.ResourceNotFoundException;
import com.mukund.Blogbackend.payload.Commentdto;
import com.mukund.Blogbackend.repository.CommentRepository;
import com.mukund.Blogbackend.repository.PostRepository;
import com.mukund.Blogbackend.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    @Override
    public Commentdto createComment(long postId, Commentdto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId ));
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        comment.setPost(post);
       Comment newComment =  commentRepository.save(comment);
       return mapToDto(newComment);


    }

    @Override
    public List<Commentdto> getAllCommentsForPost(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public Commentdto getCommentForPost(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment does not belong to this post");

        }
        return mapToDto(comment);

    }

    @Override
    public Commentdto updateComment(Long postId, Long commentId, Commentdto commentdto) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment not belong to post");

        }

        comment.setName(commentdto.getName());
        comment.setEmail(commentdto.getEmail());
        comment.setBody(commentdto.getBody());

        commentRepository.save(comment);
        return mapToDto(comment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "id", postId));
        Comment comment  = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "id", commentId));
        if(!comment.getPost().getId().equals(postId)){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment not belong to post");

        }

        commentRepository.delete(comment);

    }


    private Commentdto mapToDto(Comment comment){
        Commentdto commentdto  = new Commentdto();
        commentdto.setId(comment.getId());
        commentdto.setBody(comment.getBody());
        commentdto.setName(comment.getName());
        commentdto.setEmail(comment.getEmail());
        return commentdto;
    }
}

package com.mukund.Blogbackend.service;

import com.mukund.Blogbackend.entity.Comment;
import com.mukund.Blogbackend.payload.Commentdto;

import java.util.List;

public interface CommentService {
    Commentdto createComment( long postId, Commentdto commentDto);
    List<Commentdto> getAllCommentsForPost(long postId);

    Commentdto getCommentForPost(Long postId, Long commentId);

    Commentdto updateComment(Long postId, Long commentId, Commentdto commentdto);

    void deleteComment(Long postId, Long commentId);
}

package com.mukund.Blogbackend.service;

import com.mukund.Blogbackend.payload.Postdto;

import java.util.List;

public interface PostService {

    Postdto createPost(Postdto postdto);

    List<Postdto> getAllPost();

    Postdto getPostById(Long id);


    Postdto updatePost(Long id, Postdto postdto);

    String deletePost(Long id);
}

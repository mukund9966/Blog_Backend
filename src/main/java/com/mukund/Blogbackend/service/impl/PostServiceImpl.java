package com.mukund.Blogbackend.service.impl;

import com.mukund.Blogbackend.entity.Post;
import com.mukund.Blogbackend.exception.ResourceNotFoundException;
import com.mukund.Blogbackend.payload.Postdto;
import com.mukund.Blogbackend.repository.PostRepository;
import com.mukund.Blogbackend.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper mapper;

    @Override
    public Postdto createPost(Postdto postdto) {
        Post post = new Post();
        post.setContent(postdto.getContent());
        post.setDescription(postdto.getDescription());
        post.setTitle(postdto.getTitle());
       Post newPost =  postRepository.save(post);


//       convert Post to Dto
Postdto postdto1 = mapToDto(newPost);
        return postdto1;

    }

    @Override
    public List<Postdto> getAllPost() {
List<Post> posts = postRepository.findAll();
return posts.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Postdto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post", "id",id));
        return mapToDto(post);


    }

    @Override
    public Postdto updatePost(Long id, Postdto postdto) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postdto.getTitle());
        post.setDescription(postdto.getDescription());
        post.setContent(postdto.getContent());
        postRepository.save(post);
        return mapToDto(post);
    }

    @Override
    public String deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
        return "deleted";
    }


    //    convert post to postdto
    private Postdto mapToDto(Post post){
        Postdto postResponse  = mapper.map(post, Postdto.class);
//        Postdto postResponse = new Postdto();
//        postResponse.setId(post.getId());
//        postResponse.setTitle(post.getTitle());
//        postResponse.setContent(post.getContent());
//        postResponse.setDescription(post.getDescription());
        return postResponse;
    }
}

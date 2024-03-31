package com.mukund.Blogbackend.controller;

import com.mukund.Blogbackend.entity.Post;
import com.mukund.Blogbackend.payload.Postdto;
import com.mukund.Blogbackend.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@RequestBody Postdto postdto){
    return new ResponseEntity<>(postService.createPost(postdto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllPost(){
        return new ResponseEntity<>(postService.getAllPost(), HttpStatus.OK);
    }

    @GetMapping("getPost/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("updatePost/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Postdto postdto){
        return new ResponseEntity<>(postService.updatePost(id, postdto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        return new ResponseEntity<>(postService.deletePost(id), HttpStatus.OK);
    }
}

package com.mukund.Blogbackend.payload;

import lombok.Data;

@Data
public class Commentdto {
    private Long id;
    private String name;
    private String email;
    private String body;
}

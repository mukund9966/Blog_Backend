package com.mukund.Blogbackend.payload;

import lombok.Data;

@Data
public class Postdto {
    private Long id;
    private String title;
    private String description;
    private String content;


}

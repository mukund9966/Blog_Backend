package com.mukund.Blogbackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registerdto {
    private String name;
    private String username;
    private String email;
    private String password;

}

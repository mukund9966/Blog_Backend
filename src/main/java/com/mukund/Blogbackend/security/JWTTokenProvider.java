package com.mukund.Blogbackend.security;

import com.mukund.Blogbackend.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;


//    generate jwt token

    public String generateToken(Authentication authentication) {
        String userName = authentication.getName();
        Date date = new Date();
        Date exprieDate = new Date(date.getTime() + jwtExpirationDate);


        String token = Jwts.builder().subject(userName).issuedAt(date).expiration(exprieDate).signWith(getKey()).compact();
        return token;
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

//get username from jwt token

    public String getUsername(String token){
      return  Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload().getSubject();
    }

//    validate jwttoken

    public boolean validToken(String token){
        try {
            Jwts.parser().verifyWith(getKey()).build().parse(token);
            return true;

        }
        catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "invalid jwt token");

        }
        catch(ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "expired jwt token");

        }

        catch(UnsupportedJwtException unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "unsupoorted jwt token");
        }

        catch(IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "jwt claims string is empty");
        }

    }

}

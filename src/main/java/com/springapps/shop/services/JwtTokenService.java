package com.springapps.shop.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtTokenService {
//algoritmul de genearea a tokenului
    private Algorithm hmac512;
    //algorituml de verificare a tokenului
    private JWTVerifier verifier;
//imi declar o variabila care indica in milisecunde cate este valabil tokeny=ul
    public static final long JWT_TOKEN_VALIDITY = 22057695L;
    //ca sa prind in java ce am in app properties pentru jwt.secret = mysecret ma folosesc de adonotarea
    //@Value("mysecret") adica numele proprietatii pe care vreau sa o iau din app prop
    public JwtTokenService(@Value("${jwt.secret}") String secret) {
        this.hmac512 =Algorithm.HMAC512(secret);//apelez metoda Algorithm din clasa HMAC512 PE stringul secret
        this.verifier = JWT.require(hmac512).build();//construieste un verificator al tokenului folosinduse de alg hmac512
    }
    //generaez tokenul pe baza userdetailului din baza noastra de date

    public String generateToken (UserDetails userDetails){
        //creez tokenul pentru username - ul meu  pentru o perioda care incepe acum + numarul de milisecunde salvate in validity
        //si pe care il semnez cu sign
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .sign(hmac512);
    }
    //metoda verifica tokenul si va returna tokenul docodificat adica imi va spune
    // al cui este tokenul (getSubject) care in cazul nostru este userul, adica pentru ce user name va fi generat

    public String validateToken (String token){
        return verifier.verify(token).getSubject();
    }
}

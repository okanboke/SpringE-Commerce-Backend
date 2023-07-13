package com.Bitirme.Finish2.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JwtTokenProvider {

    @Value("${finish2.app.secret}")
    private String APP_SECRET;                                                  //token oluşturacağız

    @Value("${finish2.expires.in}")
    private long EXPIRES_IN;                                                    //saniye cinsinden tokenların geçerlilik yitiriyor
                                                                                //application properties'de gerekli atamaları yapıyoruz.

    public String generateJwtToken(Authentication auth) {
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal(); //auth edeceğimiz user
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
        //tokenı HS512 algoritması kullanarak oluşturuyoruz..
    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    Long getUserIdFromJwt(String token) { //tokenı çöz içerisinden userId yi al tokenlarımızın kim olduğunu tanımamız gerekiyor
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token).getBody();
                return Long.parseLong(claims.getSubject());
    }
    boolean validateToken(String token) {                                       //süresi dolmuşmu veya bu token o user'a ait mi kontrolü için...
            try {
                Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token); //parse edebiliyorsak bizim uygulamamızın oluşturduğu bir tokendır kontrol ediyoruz...
                return !isTokenExpired(token);
                } catch (SignatureException e) {
            return false;
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        } catch (UnsupportedJwtException e) {
            return false;
        } catch (IllegalArgumentException e) { //catch bloklarından herhangi birisi gerçekleşirse bu token bize ait değil hatalı...
            return false;
        }
    }

    private boolean isTokenExpired(String token) {                              //token'nın süresi bitmiş mi?
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET)
                .parseClaimsJws(token).getBody().getExpiration();
        return expiration.before(new Date());
    }
}
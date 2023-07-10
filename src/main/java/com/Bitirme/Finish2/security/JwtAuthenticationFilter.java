package com.Bitirme.Finish2.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Bitirme.Finish2.services.UserDetailsServiceImpl;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /*//front-end'den back-end tarafına bir request geldiğinde Spring Security'nin yaptığı çok fazla filtreleme aşaması var
    * doFilterInternal aşamasını Jwt token filtrelemesi için ekliyoruz bir request geldiğinde bu request otorize olmuş mu kontrolü sağlıyoruz
    * değilse bu requesti unotorize olarak geri çevireceğiz...*/

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) { //token doluysa
                Long id = jwtTokenProvider.getUserIdFromJwt(jwtToken);
                UserDetails user = userDetailsService.loadUserById(id);
                if(user != null) { //User'ı auth etmemiz için gerekli
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth); //artık auth edilmiş oldu
                }
            }
        } catch (Exception e) {
            return;
        }
        filterChain.doFilter(request, response);

    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))  //bearer dolu mu geldi kontrolü
            return bearer.substring("Bearer".length() +1); //başındaki boşluk kısmı çıkarıp dönüyoruz
        return null;
    }
}

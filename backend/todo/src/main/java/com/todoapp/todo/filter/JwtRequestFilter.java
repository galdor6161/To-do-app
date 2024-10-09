package com.todoapp.todo.filter;

import com.todoapp.todo.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    // Bağımlılıkları enjekte etmek için constructor
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
                
        // İstekten Authorization başlığını al
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;
                
        // Authorization başlığının Bearer token içerip içermediğini kontrol et
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired", e);
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }
                
        // Kullanıcı adı çıkarıldıysa ve güvenlik bağlamında henüz kimlik doğrulama yapılmadıysa
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Kullanıcı ayrıntılarını kullanıcı ayrıntıları servisinden yükle
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            
            // Token'ı kullanıcı ayrıntılarıyla doğrula
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set the current user in the security context
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        // Filtre zincirine devam et
        chain.doFilter(request, response);
    }
}
